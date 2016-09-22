package test.client;

import java.io.FileOutputStream;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

public class CreateSOAPMessage {
			/**
			 16
			 * @param args
			17
			 */
	public static void main(String[] args) {
		try{
			MessageFactory factory = MessageFactory.newInstance();

			SOAPMessage soapMsg = factory.createMessage();

			SOAPPart part = soapMsg.getSOAPPart();

			SOAPEnvelope envelope = part.getEnvelope();

			SOAPHeader header = envelope.getHeader();

			SOAPBody body = envelope.getBody();
			header.addTextNode("Training Details");

			SOAPBodyElement element = body.addBodyElement(envelope.createName("JAVA", "training", "http://JitendraZaa.com/blog"));

			element.addChildElement("WS").addTextNode("Training on Web service");

			SOAPBodyElement element1 = body.addBodyElement(envelope.createName("JAVA", "training", "http://JitendraZaa.com/blog"));

			element1.addChildElement("Spring").addTextNode("Training on Spring 3.0");

			soapMsg.writeTo(System.out);

			FileOutputStream fOut = new FileOutputStream("SoapMessage.xml");

			soapMsg.writeTo(fOut);

			System.out.println();

			System.out.println("SOAP msg created");

		}catch(Exception e){

			e.printStackTrace();

		}
	}
}

