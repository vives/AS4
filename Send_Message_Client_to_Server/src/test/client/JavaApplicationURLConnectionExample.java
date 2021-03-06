package test.client;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class JavaApplicationURLConnectionExample {

	public static void main(String[] args) throws MalformedURLException, IOException {
		String urlString =
				"http://localhost:8080";
		URL urlForInfWebSvc = new URL(urlString);
		URLConnection UrlConnInfWebSvc = urlForInfWebSvc.openConnection();
		HttpURLConnection httpUrlConnInfWebSvc = (HttpURLConnection) UrlConnInfWebSvc;
		httpUrlConnInfWebSvc.setDoOutput(true);
		httpUrlConnInfWebSvc.setDoInput(true);
		httpUrlConnInfWebSvc.setAllowUserInteraction(true);
		httpUrlConnInfWebSvc.setRequestMethod("POST");
		httpUrlConnInfWebSvc.setRequestProperty("Host", "localhost");
		httpUrlConnInfWebSvc.setRequestProperty("Content-Type","application/soap+xml; charset=utf-8");
		OutputStreamWriter infWebSvcReqWriter = new OutputStreamWriter(httpUrlConnInfWebSvc.getOutputStream());
		String infWebSvcRequestMessage =
				"<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
				"<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"" +
				"  xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"" +
				"  xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">" +
				"  <soap12:Body>" +
				"    <GetTableEntryListRequest xmlns=\"blackbaud_appfx_server_bizops\">" +
				"      <IncludeInactive>true</IncludeInactive>" +
				"      <RefreshCache>true</RefreshCache>" +
				"    </GetTableEntryListRequest>" +
				"  </soap12:Body>" +
				"</soap12:Envelope>";

		infWebSvcReqWriter.write(infWebSvcRequestMessage);
		infWebSvcReqWriter.flush();
		BufferedReader infWebSvcReplyReader = new BufferedReader(new InputStreamReader(httpUrlConnInfWebSvc.getInputStream()));
		String line;
		String infWebSvcReplyString = "";
		while ((line = infWebSvcReplyReader.readLine()) != null) {
			infWebSvcReplyString = infWebSvcReplyString.concat(line);
		}
		infWebSvcReqWriter.close();
		infWebSvcReplyReader.close();
		httpUrlConnInfWebSvc.disconnect();
		System.out.println(infWebSvcReplyString);
	}
}