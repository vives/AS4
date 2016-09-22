package test.client;

import javax.xml.soap.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.StringTokenizer;

/**
 * Created by vive on 9/6/16.
 */
public class SoapServer {
	final static int serverPort = 3456;
	// server port number

	public static void main(String args[]) {
		java.net.ServerSocket sock = null;                              // original server socket
		java.net.Socket clientSocket = null;                      // socket created by accept
		java.io.PrintWriter pw = null;                              // socket output stream
		java.io.BufferedReader br = null;                              // socket input stream

		try {
			sock = new java.net.ServerSocket(serverPort);               // create socket and bind to port
			System.out.println("waiting for client to connect");
			clientSocket = sock.accept();                               // wait for client to connect
			System.out.println("client has connected");
			pw = new java.io.PrintWriter(clientSocket.getOutputStream(), true);
			br = new java.io.BufferedReader(new java.io.InputStreamReader(clientSocket.getInputStream()));

			InputStream is = new ByteArrayInputStream(br.readLine().getBytes());

			String msg = br.readLine();
			// read msg from client
			System.out.println("Message from the client :" + msg);

			/*SOAPMessage message = MessageFactory.newInstance().createMessage(null, is);
			System.out.println("Message from the client :" + message);
			java.util.Iterator iterator = message.getAttachments();
			while (iterator.hasNext()) {
				try {
					//get next attachment
					AttachmentPart attachment = (AttachmentPart) iterator.next();
					String id = attachment.getContentId();
					String type = attachment.getContentType();
					System.out.print("Attachment " + id +
					                 " has content type " + type);
					if (type == "text/plain") {
						Object content = attachment.getContent();
						*//*System.out.println("Attachment " +
						                   "contains:\n" + content);*//*

						pw.println("Attachment " +
						           "contains:\n" + content);
					}
				} catch (SOAPException se) {
					System.out.println(se.getMessage());
				}*/

				pw.println("Got it!");// send msg to client

				pw.close();                                                 // close everything
				br.close();
				clientSocket.close();
				sock.close();
			} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
