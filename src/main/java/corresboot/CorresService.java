package corresboot;

//import java.io.BufferedReader;
//import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
//import java.io.FileInputStream;
//import java.io.InputStreamReader;

/*
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.springframework.http.client.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpRequest;
*/

import java.net.HttpURLConnection;
import java.net.URL;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;



public class CorresService {

	public String submitProductionJob(CorresModel corresModel) throws Exception {
				
		//get ticket from Tango
		//hardcoding URL and parameters
		String ticketURL = "http://manulife.elixirchina.com/tango/api/tickets";
		String ticketParameters = "username=soon&password=12345";
		String ticket = null;
		String result="";
		
		NodeList ticketNode = doHTTPPost(ticketURL, ticketParameters);
		if (ticketNode !=null && ticketNode.getLength() > 0) {
			ticket = ticketNode.item(0).getFirstChild().getNodeValue();
		} else {
			return "error";
		}
		
		if (ticket !=null) {
			//hardcoding URL and some parameters
			String productionURL ="http://manulife.elixirchina.com/tango/api/production/jobs";
			String productionParameters = "tango-ticket="+ticket+"&";
			productionParameters = productionParameters+"project-name=ShippingOrder&";
			productionParameters = productionParameters+"data-file-contents="+corresModel.getCaseXML()+"&";
			productionParameters = productionParameters+"client-app=CUI&";
			String searchpaths = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?> <resources> <resource isRecursive=\"true\" path=\"Letters\" type=\"folder\"/> <resource isRecursive=\"true\" path=\"Letters_Resources\" type=\"folder\"/> <resource isRecursive=\"true\" path=\"BUI_Shared\" type=\"folder\"/> <resource isRecursive=\"true\" path=\"Shared\" type=\"folder\"/> </resources>";
			productionParameters = productionParameters+"search-paths="+searchpaths+"&";
			productionParameters = productionParameters+"site-id=164956&";
			productionParameters = productionParameters+"repoPath=Projects/01Exercise/ShippingOrder";
			
			System.out.println(productionParameters);
			
			NodeList productionNode = doHTTPPost(productionURL, productionParameters);
			if (productionNode != null && productionNode.getLength() > 0) {
				result=productionNode.toString();
			} else {
				return "error";
			}			
		}		
		return result;
	}
	
	//return NodeList because tango responses in xml
	public NodeList doHTTPPost(String url, String parameters) throws Exception {
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();		
		con.setRequestMethod("POST");
		con.setDoOutput(true);
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(parameters);
		wr.flush();
		wr.close();
		
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = builderFactory.newDocumentBuilder();
		Document document = builder.parse(con.getInputStream());
		NodeList nodeList = document.getChildNodes();		
		return nodeList;
	}
		
	public String sendPostURLParams(CorresModel corresModel) throws Exception {
		//this.corresModel = corresModel;
		//String result = "";
		
		System.out.println("CorresService starting...");
		//hardcode url, ideal - read from config
		String url = null;
		url = "http://manulife.elixirchina.com/tango/api/tickets";
		URL obj = new URL(url);
		System.out.println(obj.getPath());
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		//con.setRequestProperty(USER_AGENT, USER_AGENT);
		con.setRequestMethod("POST");
				
		//hardcode urlParameters for time being, ideal - read from config
		String urlParameters = null;
		urlParameters= "username=soon&password=12345";

		System.out.println("\nSending 'POST' request to URL :" + url);
		System.out.println("Post Parameters : " + urlParameters);
				
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();
				
		System.out.println(con.toString());
		System.out.println(con.getResponseMessage());
		int responseCode = con.getResponseCode();

		System.out.println("Response Code :" + responseCode);
		
		/*
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));		
		String inputLine;
		StringBuffer response = new StringBuffer();
		
		if (responseCode==201) {
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}		
			in.close();
		}
		*/
		
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = builderFactory.newDocumentBuilder();
		Document document = builder.parse(con.getInputStream());
		NodeList nodeList = document.getChildNodes();
		String ticket = null;
		if (nodeList !=null && nodeList.getLength() > 0) {
			ticket = nodeList.item(0).getFirstChild().getNodeValue();
		}
		
		if (ticket!=null) {
			System.out.println("Tango ticket : " +ticket);
		} else {
			System.out.println("Tango ticket : No ticket");
		}
		
		//result = response.toString();
		//System.out.println(result);
				
		return ticket;
				
	}
	
	public String getUsingRestTemplate() throws Exception {
		
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://gturnquist-quoters.cfapps.io/api/random";
		ResponseEntity<String> responseEntity=restTemplate.getForEntity(url, String.class);
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode root = mapper.readTree(responseEntity.getBody());
		//JsonNode name = root.path("IndividualCustomer");
		JsonNode quote = root.path("value").path("quote");
		
		
		System.out.println("responseentity "+responseEntity.toString());
		System.out.println("responseentitybody "+responseEntity.getBody());
		System.out.println("root elements "+root.elements());
		System.out.println("quote "+quote.textValue());
		return quote.toString();
		
	}

}
