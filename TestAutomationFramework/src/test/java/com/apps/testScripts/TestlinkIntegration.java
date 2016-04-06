package com.apps.testScripts;



import java.lang.reflect.Method;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import testlink.api.java.client.TestLinkAPIResults;

import com.apps.mantis.ImageManipulation;
import com.apps.mantis.MantisReport;
import com.apps.model.ApplicationLibrary;
import com.apps.model.CommonLibrary;
import com.apps.model.DataManager;
import com.apps.model.Reports;
import com.apps.pages.HomePage;
import com.apps.testlink.TestLinkResult;

public class TestlinkIntegration {

	String moduleName = "Technical Modelling";
	String testCaseName = getClass().getSimpleName();

	CommonLibrary comlib = new CommonLibrary();
	ApplicationLibrary applib = new ApplicationLibrary();
	Reports reports = new Reports();
	DataManager datamanager = new DataManager();
	TestLinkResult testlinkResult=new TestLinkResult();
	MantisReport mantisReport=new MantisReport();
	HomePage home;
	WebDriver driver;

	/**Testlink Constants**/	
	String TESTLINK_TC_NAME = "Verify Login";
	/***TestLink Reporting****/
	boolean error;
	String msgError;
	String screenShot;
	String result=null;
	String notes=null;

	@BeforeClass
	public void startUp() throws Exception {
		driver = comlib.initialize(driver, testCaseName, datamanager, comlib, reports);
	}
	@BeforeMethod
	public void beforeTest(Method methodName) throws Exception {
		reports.writeTestName(methodName.getName());
	}
	@Test
	public void test(ITestContext context) throws Exception {
		try{
			//home = new HomePage(driver);
			//Assert.assertTrue(!home.isHomePage());
			Assert.assertEquals(true, false);
			reports.writeIntoFile(driver, testCaseName, "Validate Home Page", "Login with valid credentials",
					"Home page is displayed", reports.pass, "", comlib.getCurrentTime());
		} catch (Error e) {
			reports.writeIntoFile(driver, testCaseName, "Validate Home Page", "Login with valid credentials",
					"Home page is not displayed", reports.fail, e.getMessage(), comlib.getCurrentTime());
			reportError(e);
		}

		finally{
			if(error){
				String bugID = mantisReport.reportIssue("Error in Verify Login Test", "Error in some validation or validation",msgError,screenShot,"AutomationFailure");
				testlinkResult.reportTestCaseResult(TESTLINK_TC_NAME, msgError, result, Integer.parseInt(bugID));
			}else{
				result=TestLinkAPIResults.TEST_PASSED;
				notes="Executed successfully";
				testlinkResult.reportResult(TESTLINK_TC_NAME);
			}
		}		
	}
	@AfterMethod
	public void afterTest() throws Exception {
		comlib.afterTestRun();
	}

	@AfterClass
	public void tearDown() throws Exception {
		comlib.quit(moduleName, testCaseName, datamanager, comlib, reports);
	}

	private void reportError(Throwable e) {	
		error = true;
		result = TestLinkAPIResults.TEST_FAILED;
		notes="Execution failed";
		msgError = e.getMessage();
		e.printStackTrace();

		try {
			screenShot =ImageManipulation.getscreenshot(driver);
		} catch (Exception e1) {
			e1.printStackTrace();
		}


	}

}


