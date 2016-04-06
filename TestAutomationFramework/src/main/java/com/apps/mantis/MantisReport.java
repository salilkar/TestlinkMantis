package com.apps.mantis;

import java.net.MalformedURLException;
import java.util.Date;

import org.apache.commons.codec.binary.Base64;
import org.mantisbt.connect.IMCSession;
import org.mantisbt.connect.MCException;
import org.mantisbt.connect.model.IProject;
import org.mantisbt.connect.model.Issue;
import org.mantisbt.connect.model.MCAttribute;
import org.testng.Reporter;

import com.apps.model.ConfigFileReader;

public class MantisReport {
	private static String MANTIS_PROJECT = null;
	private static String MANTIS_CATEGORY=null;

	public MantisReport(){
		/**
		 * Constants for the defect report
		 */
		if(!ConfigFileReader.getProperty("MANTIS_PROJECT").isEmpty()){
			MANTIS_PROJECT=ConfigFileReader.getProperty("MANTIS_PROJECT");
		}else{
			Reporter.log("MANTIS_PROJECT is not defined in the Project.properties file.", 2,true);
		}
		if(!ConfigFileReader.getProperty("MANTIS_CATEGORY").isEmpty()){
			MANTIS_CATEGORY=ConfigFileReader.getProperty("MANTIS_CATEGORY");
		}else{
			Reporter.log("MANTIS_CATEGORY is not defined in the Project.properties file.", 2,true);
		}

	}

	public String reportIssue(String summary, String description, String additionalInfo, String evidence, String fileName) {
		IMCSession session = null;
		//String file = fileName + ".png";
		String bugID = null;

		try {

			session = ConnectMantis.getsession();

			IProject project = session.getProject(MANTIS_PROJECT);
			Issue issue = new Issue();

			issue.setProject(new MCAttribute(project.getId(), project.getName()));

			issue.setAdditionalInformation(null);
			issue.setOs(System.getProperty("os.name"));
			issue.setOsBuild(System.getProperty("os.version"));
			issue.setPlatform(System.getProperty("os.arch"));
			issue.setSeverity(new MCAttribute(70, "crash"));
			issue.setReproducibility(new MCAttribute(10, "always"));

			issue.setSummary(summary +" "+ new Date());
			issue.setDescription(description);
			issue.setCategory(MANTIS_CATEGORY);
			issue.setPriority(new MCAttribute(40, "high"));
			issue.setAdditionalInformation(additionalInfo);

			long id = session.addIssue(issue);     
			session.addIssueAttachment(id, fileName, "image/png", Base64.decodeBase64(evidence));

			bugID = String.valueOf(id);

		} catch (MalformedURLException e) {
			System.err.println("Error in the Mantis access URL");
			e.printStackTrace();
		} catch (MCException e) {
			System.err.println("Error in communication with the Mantis");
			e.printStackTrace();
		}

		return bugID;
	}
}
