package test.client;

import javax.activation.DataHandler;
import javax.xml.soap.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by vive on 9/6/16.
 */
public class createSoapAttachment {

	public static void main(String[] args) {

		try

		{
			SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
			SOAPConnection connection = soapConnectionFactory.createConnection();

			MessageFactory factory = MessageFactory.newInstance();
			SOAPMessage message = factory.createMessage();
			SOAPHeader header = message.getSOAPHeader();
			header.detachNode();

			SOAPFactory soapFactory = SOAPFactory.newInstance();

			SOAPBody body = message.getSOAPBody();
			Name listingElementName = soapFactory
					.createName("propertyListing", "realProperty", "http://schemas.realhouses.com/listingSubmission");
			SOAPBodyElement listingElement = body.addBodyElement(listingElementName);
			Name attname = soapFactory.createName("id");
			listingElement.addAttribute(attname, "property_1234");
			SOAPElement listingAgency = listingElement.addChildElement("listingAgency");
			listingAgency.addTextNode("Really Nice Homes, Inc");
			SOAPElement listingType = listingElement.addChildElement("listingType");
			listingType.addTextNode("add");
			SOAPElement propertyAddress = listingElement.addChildElement("propertyAddress");
			SOAPElement street = propertyAddress.addChildElement("street");
			street.addTextNode("1234 Main St");
			SOAPElement city = propertyAddress.addChildElement("city");
			city.addTextNode("Pleasantville");
			SOAPElement state = propertyAddress.addChildElement("state");
			state.addTextNode("CA");
			SOAPElement zip = propertyAddress.addChildElement("zip");
			zip.addTextNode("94521");
			SOAPElement listPrice = listingElement.addChildElement("listPrice");
			listPrice.addTextNode("25000");

			String frontImageID = "property1234_front_jpeg@realhouses.com";
			SOAPElement frontImRef = listingElement.addChildElement("frontImage");
			Name hrefAttName = soapFactory.createName("href");
			frontImRef.addAttribute(hrefAttName, frontImageID);
			String interiorID = "property1234_interior_jpeg@realhouses.com";
			SOAPElement interiorImRef = listingElement.addChildElement("interiorImage");
			interiorImRef.addAttribute(hrefAttName, interiorID);


			/*AttachmentPart attachemtAdd = message.createAttachmentPart();


			String stringContent = "Update address for Sunny Skies " +
			                       "Inc., to 10 Upbeat Street, Pleasant Grove, CA 95439";

			attachemtAdd.setContent(stringContent, "text/plain");
			attachemtAdd.setContentId("update_address");

			message.addAttachmentPart(attachemtAdd);*/


			URL url = new URL("file:///home/vive/Downloads/Sample Invoice Test Set/SampleInvoice-ConformantCreditNote.xml");
			DataHandler dataHandler = new DataHandler(url);
			AttachmentPart att = message.createAttachmentPart(dataHandler);
			att.setContentId(frontImageID);
			message.addAttachmentPart(att);


			URL url1 = new URL("file:///home/vive/Downloads/Sample Invoice Test Set/SampleResponse-ConformantResponse.xml");
			DataHandler dataHandler1 = new DataHandler(url1);
			AttachmentPart att1 = message.createAttachmentPart(dataHandler1);
			att1.setContentId(interiorID);
			message.addAttachmentPart(att1);

			/*URL url2 = new URL("file:///home/vive/Pictures/Screenshot from 2016-06-05 23:28:18.png");
			Image im = Toolkit.getDefaultToolkit().createImage(url2);
			AttachmentPart att2 = message.createAttachmentPart(im, "image/jpeg");
			att2.setContentId(interiorID);
			message.addAttachmentPart(att2);*/

			//message.writeTo(System.out);

			//System.out.println(message);

			FileOutputStream fOut = new FileOutputStream("SoapMessageWithAttachment.xml");

			message.writeTo(fOut);

			System.out.println();

			System.out.println("SOAP msg created");

			System.out.printf("Number of attachments: %d%n", message.countAttachments());
			// remove the attachment and verify
			//message.removeAllAttachments();
			System.out.printf("Number of attachments: %d%n", message.countAttachments());


/*
			URL endpoint =
				new URL("http://localhost:3456");
			SOAPMessage response = connection.call(message, endpoint);*/

			//System.out.println(response);

			java.util.Iterator iterator = message.getAttachments();
			while (iterator.hasNext()) {
				try {
					//get next attachment
					AttachmentPart attachment = (AttachmentPart) iterator.next();
					String id = attachment.getContentId();
					String type = attachment.getContentType();
					System.out.println("Attachment " + id +
					                 " has content type " + type);
					if (type == "image/png") {
						Object content = attachment.getContent();
						System.out.println("Attachment " + "contains:\n" + content);

					}
				} catch (SOAPException se) {
					System.out.println(se.getMessage());
				}

			}
		} catch (SOAPException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

