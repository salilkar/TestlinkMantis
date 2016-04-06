package com.apps.testScripts;

import java.lang.reflect.Method;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.apps.model.ApplicationLibrary;
import com.apps.model.CommonLibrary;
import com.apps.model.ConfigurationLibrary;
import com.apps.model.DataManager;
import com.apps.model.Reports;
import com.apps.pages.PredixHomePage;

public class SpecificWebBrowser {
	
	String moduleName = "Technical Modelling";
	String testCaseName = getClass().getSimpleName();

	CommonLibrary comlib = new CommonLibrary();
	ApplicationLibrary applib = new ApplicationLibrary();
	Reports reports = new Reports();
	DataManager datamanager = new DataManager();
	PredixHomePage home;
	WebDriver driver;

	public void init() {
		comlib = new CommonLibrary();
		applib = new ApplicationLibrary();
		reports = new Reports();
		datamanager = new DataManager();
	}

	@BeforeClass
	public void startUp() throws Exception {
		init();
		if(datamanager.executionController(testCaseName).equals("Yes")) {
			reports.scriptStartTime = comlib.getCurrentTime();
			reports.createFile(testCaseName);
			driver = comlib.launchBrowser(driver, ConfigurationLibrary.browser);
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

	@Test()
	public void backToCustometListTest() {
		try {
			
			home = new PredixHomePage(driver);

			try {
				Assert.assertTrue(home.isHomePage());
				reports.writeIntoFile(driver, testCaseName, "Validate Home Page", "Login with valid credentials",
						"Home page is displayed", reports.pass, "", comlib.getCurrentTime());
			} catch (Error e) {
				reports.writeIntoFile(driver, testCaseName, "Validate Home Page", "Login with valid credentials",
						"Home page is not displayed", reports.fail, e.getMessage(), comlib.getCurrentTime());
			}

			home.clickTechnicalModButton();
			home.firstCustomerButton();
			home.clickOpenButton();

			try {
				Assert.assertTrue(home.isfleetParameterList());
				reports.writeIntoFile(driver, testCaseName, "Validate Fleet Parameter Page ",
						"Login with valid credentials", "Fleet Parameter Page is displayed", reports.pass, "",
						comlib.getCurrentTime());
			} catch (Error e) {
				reports.writeIntoFile(driver, testCaseName, "Validate Fleet Parameter Page",
						"Login with valid credentials", "Fleet Parameter Page not displayed", reports.fail,
						e.getMessage(), comlib.getCurrentTime());
			}

			home.clickBackButton();
			
			try {
				Assert.assertTrue(home.isCustomerList());
				reports.writeIntoFile(driver, testCaseName, "Validate Customer list ", "Login with valid credentials",
							"Navigated back to Customer List", reports.pass, "", comlib.getCurrentTime());
				} catch (Error e) {
					reports.writeIntoFile(driver, testCaseName, "Validate Customer List", "Login with valid credentials",
							"Navigation to Customer list failed", reports.fail, e.getMessage(), comlib.getCurrentTime());
				}
			
		} catch (Exception e) {
			reports.writeIntoFile(driver, testCaseName, "Exception", "Tried performing action using webdriver",
					"Exception occured", reports.fail, e.getMessage(), comlib.getCurrentTime());
			Assert.fail("Exception occured please check logs");
		}
	}

	@AfterMethod
	public void afterTest() throws Exception {
		comlib.afterTestRun();
	}

	@AfterClass
	public void tearDown() throws Exception {
		driver.quit();
		comlib.quit(moduleName, testCaseName, datamanager, comlib, reports);
	}
}
