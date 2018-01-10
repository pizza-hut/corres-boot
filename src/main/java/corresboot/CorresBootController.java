package corresboot;

//import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;

@RestController
public class CorresBootController {
	public static final Logger logger = LoggerFactory.getLogger(CorresBootController.class);
	
	@PostMapping("/corres/job/submit")
	public String submit(@RequestBody CorresModel corresModel) {
		//ideally param should be person_id, list of policy_id, project_id
		// the main logic will be in the service class where it does retrieve+merge to Tango's format
		logger.info("Submitting Corres job is initiated...");
		String result;
		result = "Submitting Corres job is initiated...";
		
		try {
			//print out couple of things from requestbody
			System.out.println(corresModel.getPartyID());
			System.out.println(corresModel.getProjectID());
			
			//hardcoding casexml data
			String caseXml = "<shiporder><orderid>O12345</orderid><orderperson>John Smith</orderperson>";
			caseXml = caseXml + "<shipto><contactid>C1234</contactid><name>Martin Freeman</name>";
			caseXml = caseXml + "<address>Langgt 23</address><city>4000 Stavanger</city><country>Norway</country>";
			caseXml = caseXml + "<email>soon_lung_pong@manulife.com</email><mobile>+6596975117</mobile>";
			caseXml = caseXml + "<delivery><sms>true</sms><email>true</email><post>true</post></delivery></shipto>";
			caseXml = caseXml + "<item><title>Empire Strikes Back</title><note>Special Edition</note><quantity>3</quantity><price>12.90</price></item>";
			caseXml = caseXml + "<item><title>Eat your heart</title><quantity>10</quantity><price>19.90</price></item></shiporder>";
			corresModel.setCaseXML(caseXml);
			CorresService corresService = new CorresService();
			result = (corresService.sendPostURLParams(corresModel));
			System.out.println(result);
			
			result=corresService.submitProductionJob(corresModel);
			System.out.println(result);
			
		} catch (Exception e) {
			logger.error(e.toString());
			result = e.toString();
		}
		
		return result;
	}
	
	@RequestMapping("corres/job/status")
	public String getStatus() {
		//ideally param should be job_id
		logger.info("Get corres status is initiated...");
		String status;
		status = "status is OK";
		return status;
	}
	
	@RequestMapping("corres/test")
	public String getTestResult() {
		CorresService corresService = new CorresService();
		String response="";
		try {
			response = corresService.getUsingRestTemplate(); 
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		System.out.println(response);
		return response;
	}
	
	
}
