package org.wso2.carbon.connector;

import org.apache.synapse.MessageContext;
import org.wso2.carbon.connector.core.AbstractConnector;
import org.wso2.carbon.connector.core.Connector;
import org.wso2.carbon.connector.core.util.ConnectorUtils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class SendMessage extends AbstractConnector implements Connector {
    public void connect(MessageContext messageContext) {

        try {
            String message = (String) ConnectorUtils.lookupTemplateParamater(messageContext,
                    As4Constants.MESSAGE);
            String host = (String) messageContext.getProperty(As4Constants.HOST);
            int port = Integer.parseInt((String) messageContext.getProperty(As4Constants.PORT));
            sendMessage(messageContext, message, host, port);
        } catch (NumberFormatException e) {
            handleException("The port number does not contain a parsable integer", e, messageContext);
        }
    }

    public void sendMessage(MessageContext messageContext, String message, String host, int port) {
        try {
            Socket socket = new Socket(host, port);
            clientHandler(messageContext, socket, message);
        } catch (IOException e) {
            handleException("Couldn't create Socket", e, messageContext);
        }

    }

    public void clientHandler(MessageContext messageContext, Socket connection, String message) throws IOException {


        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        try {
            oos = new ObjectOutputStream(connection.getOutputStream());
            log.info("Sending request to Socket Server");
            oos.writeObject(message);

            //read the server response message
            ois = new ObjectInputStream(connection.getInputStream());
            String response = (String) ois.readObject();
            log.info(response);
        } catch (IOException e) {
            handleException("An exception occurred in sending the message", e, messageContext);
        } catch (ClassNotFoundException e) {
            handleException("An exception occurred in reading the server message", e, messageContext);
        } finally {
            if (ois != null) {
                ois.close();
            }
            if (oos != null) {
                oos.close();
            }
        }
    }
}
