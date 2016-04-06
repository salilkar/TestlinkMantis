package com.apps.parallel.execution.tests;

import java.lang.reflect.Method;

import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.apps.model.ApplicationLibrary;
import com.apps.model.CommonLibrary;
import com.apps.model.ConfigurationLibrary;
import com.apps.model.DataManager;
import com.apps.model.Reports;
import com.apps.pages.GeLandingPage;
import com.apps.pages.GeLoginPage;
import com.apps.prerequisitesParallel.BaseClass;

public class Login extends BaseClass{

	RemoteWebDriver driver;
	GeLoginPage loginPage;
	GeLandingPage landingPage;

	//public WebDriverWait wait;

	CommonLibrary comlib;
	ApplicationLibrary applib;
	Reports reports;
	DataManager datamanager;

	String moduleName = "Login";
	String testCaseName = getClass().getSimpleName();

	public int randomNumber = ( int )( Math.random() * 9999 );

	public void init() {
		comlib = new CommonLibrary();
		applib = new ApplicationLibrary();
		reports = new Reports();
		datamanager = new DataManager();
	}

	@Parameters({"platform","browserName", "remoteURL"})
	@BeforeClass
	public void startUp(String platform, String browserName, String remoteURL) throws Exception {
		init();
		if(datamanager.executionController(testCaseName).equals("Yes")) {
			reports.scriptStartTime = comlib.getCurrentTime();
			reports.createFile(testCaseName);
			driver = comlib.launchApplication(driver, platform, browserName, remoteURL);
			Thread.sleep(2000);
		} else {
			System.out.println(testCaseName + " execution is skipped");
			ConfigurationLibrary.skipCount++;
			throw new SkipException(testCaseName + " execution is skipped");
		}
	}

	@BeforeMethod
	public void beforeTest(Method methodName) throws Exception {
		reports.writeTestName(methodName.getName());
	}

	@ Test()
	public void loginTest() throws Exception {
		try {

			loginPage = new GeLoginPage(driver);
			landingPage = new GeLandingPage(driver);

			try {
				Assert.assertTrue(landingPage.verifyHomeScreen());
				reports.writeIntoFile(driver, testCaseName,  "Validate Landing Page", "Launch the URL", "Landing Page is displayed", reports.pass, "", comlib.getCurrentTime());

			}catch(Error e)
			{
				reports.writeIntoFile(driver, testCaseName,  "Validate Landing Page", "Launch the URL", "Landing Page is not displayed", reports.fail, e.getMessage(), comlib.getCurrentTime());
				System.out.println("Register button validation failed. Reason:" +e.getMessage());
				Assert.fail();
			}  

			try {

				landingPage.clickMenu();
				landingPage.clickCustomerSignInBtn();
				landingPage.clickMyDashbaord();
				
				Thread.sleep(2000);

				Assert.assertTrue(loginPage.isLoginPage());
				reports.writeIntoFile(driver, testCaseName,  "Validate Login Page", "click on Menu --> Customer Sign In --> My Dashboard", "Login Page is displayed", reports.pass, "", comlib.getCurrentTime());

			}
			catch(Error e)
			{
				reports.writeIntoFile(driver, testCaseName,  "Validate Login Page", "click on Menu --> Customer Sign In --> My Dashboard", "Login Page is not displayed", reports.fail, e.getMessage(), comlib.getCurrentTime());
				System.out.println("Register button validation failed. Reason:" +e.getMessage());
				Assert.fail();
			}

			try {

				loginPage.loginToApp("vedesh", "iam05PRASAD");

				Assert.assertTrue(landingPage.isMenu());
				reports.writeIntoFile(driver, testCaseName,  "Validate Home Page", "Login to the application", "Home Page is displayed", reports.pass, "", comlib.getCurrentTime());

			}
			catch(Error e)
			{
				reports.writeIntoFile(driver, testCaseName,  "Validate Home Page", "Login to the application", "Home Page is not displayed", reports.fail, e.getMessage(), comlib.getCurrentTime());
				System.out.println("Register button validation failed. Reason:" +e.getMessage());
				Assert.fail();
			}


		} catch(Exception e) {
			reports.writeIntoFile(driver, testCaseName, "Exception", "Tried performing action using Android driver", "Exception occured", reports.fail, e.getMessage(), comlib.getCurrentTime());
			Assert.fail("validation failed");
		}
	}

	@AfterMethod
	public void afterTest() throws Exception {
		comlib.afterTestRun();
	}

	@AfterClass
	public void tearDown() throws Exception {
		comlib.afterScript(reports, moduleName, testCaseName);
		driver.quit();
		System.out.println(testCaseName + " execution completed.");

	}
}