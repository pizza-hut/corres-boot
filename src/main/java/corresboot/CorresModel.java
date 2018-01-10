package corresboot;

import java.util.ArrayList;
import java.util.List;

public class CorresModel {
	private String projectID;
	private String caseXML;
	private String jobID;
	private String partyID;
	private String siteID;
	private List<String> listOfPolicyIDs = new ArrayList<String>();
		
	public void setProjectID(String projectID) {
		this.projectID = projectID;
	}
	
	public String getProjectID() {
		return this.projectID;
	}
	
	public void setCaseXML(String caseXML) {
		this.caseXML = caseXML;
	}
	
	public String getCaseXML() {
		return this.caseXML;
	}
	
	public void setJobID(String jobID) {
		this.jobID = jobID;
	}
	
	public String getJobID() {
		return this.jobID;
	}
	
	public String getSiteID() {
		return this.siteID;
	}
	
	public void setSiteID(String siteID) {
		this.siteID = siteID;
	}
	
	public String getPartyID() {
		return this.partyID;
	}
	
	public void setPartyID(String partyID) {
		this.partyID = partyID;
	}
	
	public List<String> getListOfPartyIDs() {
		return this.listOfPolicyIDs;
	}
	
	public void setListOfPartyIDs(List<String> listOfPartyIDs) {
		this.listOfPolicyIDs = listOfPartyIDs;
	}

}
