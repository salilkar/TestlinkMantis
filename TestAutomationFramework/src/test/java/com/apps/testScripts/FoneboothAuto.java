package com.apps.testScripts;

import java.lang.reflect.Method;

import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.apps.model.ApplicationLibrary;
import com.apps.model.CommonLibrary;
import com.apps.model.ConfigurationLibrary;
import com.apps.model.DataManager;
import com.apps.model.Reports;
import com.apps.pages.HomePage;
import com.apps.pages.LoginPage;
import com.apps.pages.SplashScreenPage;
import com.apps.preSetupScriptsFonebooth.StartEndExecution;

public class FoneboothAuto extends StartEndExecution{
	
	RemoteWebDriver driver;
	SplashScreenPage splashPage;
	LoginPage loginPage;
	HomePage homePage;

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

	@BeforeClass
	public void startUp() throws Exception {
		init();
		if(datamanager.executionController(testCaseName).equals("Yes")) {
			reports.scriptStartTime = comlib.getCurrentTime();
			reports.createFile(testCaseName);
			driver = comlib.launchAndroidApplicationinFB(driver);
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

	@DataProvider(name="validLogin")
	public Object[][] readData() throws Exception {
		String[][] retObjArr = datamanager.getTableArray(ConfigurationLibrary.xlpath_login, ConfigurationLibrary.xlsheet_login, ConfigurationLibrary.xlDataTable_login);
		return retObjArr;
	}

	@ Test(dataProvider = "validLogin")
	public void loginTest(String username, String password) throws Exception {

		try {
			
			splashPage = new SplashScreenPage(driver);
			loginPage = new LoginPage(driver);
			homePage = new HomePage(driver);

			try {
				Assert.assertTrue(splashPage.isSplashScreenPage());
				reports.writeIntoFile(driver, testCaseName,  "Validate Splash Screen", "Launch the App", "Splash Screen is displayed", reports.pass, "", comlib.getCurrentTime());
			}catch(Error e)
			{
				reports.writeIntoFile(driver, testCaseName,  "Validate Splash Screen", "Launch the App", "Splash Screen is not displayed", reports.fail, e.getMessage(), comlib.getCurrentTime());
				System.out.println("Register button validation failed. Reason:" +e.getMessage());
				Assert.fail();
			} 

			try {
				splashPage.clickLoginBtn();
				
				Assert.assertTrue(loginPage.isLoginPage());
				reports.writeIntoFile(driver, testCaseName,  "Validate Login Screen", "Click on Login button", "Login Screen is displayed", reports.pass, "", comlib.getCurrentTime());
			}catch(Error e)
			{
				reports.writeIntoFile(driver, testCaseName,  "Validate Login Screen", "Click on Login button", "Login Screen is not displayed", reports.fail, e.getMessage(), comlib.getCurrentTime());
				System.out.println("Register button validation failed. Reason:" +e.getMessage());
				Assert.fail();
			} 
			
			try {
				loginPage.loginToApp(username, password);

				Assert.assertTrue(homePage.isHomePage());
				reports.writeIntoFile(driver, testCaseName,  "Validate Home Screen", "login to the app", "Home Screen is displayed", reports.pass, "", comlib.getCurrentTime());
			}catch(Error e)
			{
				reports.writeIntoFile(driver, testCaseName,  "Validate Home Screen", "login to the app", "Home Screen is not displayed", reports.fail, e.getMessage(), comlib.getCurrentTime());
				System.out.println("Register button validation failed. Reason:" +e.getMessage());
				Assert.fail();
			} 
			
			try {
				homePage.clickMenuBar();
				homePage.selectOption("Settings");
				
				Assert.assertTrue(homePage.isLogoutBtn());
				reports.writeIntoFile(driver, testCaseName,  "Validate Logout option", "Click Menu --> Settings", "Logout option is displayed", reports.pass, "", comlib.getCurrentTime());
			}catch(Error e)
			{
				reports.writeIntoFile(driver, testCaseName,  "Validate Logout option", "Click Menu --> Settings", "Logout option is not displayed", reports.fail, e.getMessage(), comlib.getCurrentTime());
				System.out.println("Register button validation failed. Reason:" +e.getMessage());
				Assert.fail();
			} 
			
			try {
				homePage.clickLogout();
				Thread.sleep(2000);
				
				Assert.assertTrue(splashPage.isSplashScreenPage());
				reports.writeIntoFile(driver, testCaseName,  "Validate Splash Screen ", "Click logout btn", "Splash Screen is displayed", reports.pass, "", comlib.getCurrentTime());
			}catch(Error e)
			{
				reports.writeIntoFile(driver, testCaseName,  "Validate Splash Screen ", "Click logout btn", "Splash Screen is not displayed", reports.fail, e.getMessage(), comlib.getCurrentTime());
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
