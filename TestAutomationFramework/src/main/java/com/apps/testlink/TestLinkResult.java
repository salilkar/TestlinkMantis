package com.apps.testlink;

import org.openqa.selenium.WebDriver;
import org.testng.Reporter;

import com.apps.mantis.ImageManipulation;
import com.apps.model.ConfigFileReader;

import testlink.api.java.client.TestLinkAPIClient;
import testlink.api.java.client.TestLinkAPIException;
import testlink.api.java.client.TestLinkAPIHelper;
import testlink.api.java.client.TestLinkAPIResults;


public class TestLinkResult {

	// ******************
	// TESTLINK DATA
	// ******************
	private static String DEVKEY = null;
	private static String TESTLINK_URL = null;
	private static String TESTLINK_PROJECT = null;
	private static String TESTLINK_PLAN = null;
	private static String TESTLINK_BUILD = null;

	public TestLinkResult(){
		/**
		 * Key for connection with the Testlink
		 */
		DEVKEY=ConfigFileReader.getProperty("TESTLINK_KEY");
		if(!ConfigFileReader.getProperty("TESTLINK_KEY").isEmpty()){
			DEVKEY=ConfigFileReader.getProperty("TESTLINK_KEY");
		}else{
			//Logger.ERROR(Level.ERROR,"TESTLINK KEY is not defined in the Project.properties file.");
			Reporter.log("TESTLINK_KEY is not defined in the Project.properties file.", 2,
					true);
		}

		/**
		 * API XML-RPC TestLink
		 */
		if(!ConfigFileReader.getProperty("TESTLINK_URL").isEmpty()){
			TESTLINK_URL=ConfigFileReader.getProperty("TESTLINK_URL");
		}else{
			Reporter.log("TESTLINK_URL is not defined in the Project.properties file.", 2,
					true);
		}
		/**
		 * Project in TestLink
		 */
		if(!ConfigFileReader.getProperty("TESTLINK_PROJECT").isEmpty()){
			TESTLINK_PROJECT=ConfigFileReader.getProperty("TESTLINK_PROJECT");
		}else{
			Reporter.log("PROJECT_TESTLINK is not defined in the Project.properties file.", 2,
					true);
		}

		/**
		 * Test Plan TestLink
		 */
		if(!ConfigFileReader.getProperty("TESTLINK_PLAN").isEmpty()){
			TESTLINK_PLAN=ConfigFileReader.getProperty("TESTLINK_PLAN");
		}else{
			Reporter.log("TESTLINK_PLAN is not defined in the Project.properties file.", 2,
					true);
		}

		/**
		 * Build TestLink
		 */
		if(!ConfigFileReader.getProperty("TESTLINK_BUILD").isEmpty()){
			TESTLINK_BUILD=ConfigFileReader.getProperty("TESTLINK_BUILD");
		}else{
			Reporter.log("TESTLINK_BUILD is not defined in the Project.properties file.", 2,
					true);
		}

	}

	public void reportTestCaseResult( String testCase,String notes, String result, Integer bugID) throws TestLinkAPIException {
		TestLinkAPIClient testlinkAPIClient = new TestLinkAPIClient(DEVKEY, TESTLINK_URL);
		if (bugID != null) {
			Integer projectID = TestLinkAPIHelper.getProjectID(testlinkAPIClient, TESTLINK_PROJECT);
			Integer testPlanID = TestLinkAPIHelper.getPlanID(testlinkAPIClient, projectID, TESTLINK_PLAN);
			Integer testCaseID = TestLinkAPIHelper.getTestCaseID(testlinkAPIClient, projectID, testCase);
			Integer buildID = TestLinkAPIHelper.getBuildID(testlinkAPIClient, testPlanID,TESTLINK_BUILD);
			testlinkAPIClient.reportTestCaseResult(testPlanID, testCaseID, buildID, bugID, false, notes, result);        	
		}
		/*
		else {
			String passNotes="Executed Successfully";
			testlinkAPIClient.reportTestCaseResult(TESTLINK_PROJECT, TESTLINK_PLAN, testCase, TESTLINK_BUILD, passNotes, TestLinkAPIResults.TEST_PASSED);
		}*/
	}

	public void reportResult(String Testcase) throws TestLinkAPIException{
		TestLinkAPIClient api=new TestLinkAPIClient(DEVKEY, TESTLINK_URL);
		String Notes="Executed Successfully";
		api.reportTestCaseResult(TESTLINK_PROJECT, TESTLINK_PLAN, Testcase, TESTLINK_BUILD, Notes, TestLinkAPIResults.TEST_PASSED);
	}

	/***TestLink Reporting****/
	public boolean error;
	public String msgError;
	public String evidencialError;
	public String result=null;
	public String notes=null;

	@SuppressWarnings("unused")
	public void reportError(WebDriver driver, Throwable e, String summary) {	
		error = true;
		result = TestLinkAPIResults.TEST_FAILED;
		notes=summary;
		msgError = e.getMessage();
		e.printStackTrace();

		try {
			evidencialError =ImageManipulation.getscreenshot(driver);
		} catch (Exception e1) {
			e1.printStackTrace();
		}


	}


}
