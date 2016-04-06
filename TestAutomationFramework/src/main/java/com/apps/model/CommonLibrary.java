package com.apps.model;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;

import java.io.File;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.SkipException;

import com.apps.preSetupScriptsFonebooth.StartEndExecution;

public class CommonLibrary {

	public void initialize(String testCaseName, DataManager datamanager, CommonLibrary comlib, Reports reports)
			throws Exception {

		if (datamanager.executionController(testCaseName).equals("Yes")) {
			reports.scriptStartTime = comlib.getCurrentTime();
			reports.createFile(testCaseName);
		} else {
			System.out.println(testCaseName + " execution is skipped");
			ConfigurationLibrary.skipCount++;
			throw new SkipException(testCaseName + " execution is skipped");
		}

	}
	
	public WebDriver initialize(WebDriver driver, String testCaseName, DataManager datamanager, CommonLibrary comlib, Reports reports)
			throws Exception {

		if (datamanager.executionController(testCaseName).equals("Yes")) {
			reports.scriptStartTime = comlib.getCurrentTime();
			reports.createFile(testCaseName);
			driver = new FirefoxDriver();
			driver.manage().window().maximize();
			driver.get(ConfigurationLibrary.URL);
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		} else {
			System.out.println(testCaseName + " execution is skipped");
			ConfigurationLibrary.skipCount++;
			throw new SkipException(testCaseName + " execution is skipped");
		}

		return driver;

	}

	public void quit(String moduleName, String testCaseName, DataManager datamanager, CommonLibrary comlib,
			Reports reports) throws Exception {

		comlib.afterScript(reports, moduleName, testCaseName);

		System.out.println(testCaseName + " execution completed.");

	}


	/*
	 * Random numbers
	 */

	public int random() {

		int Maxnumber = 2000000000;
		int Minnumber = 1000000000;
		Random rn = new Random();
		int n = Maxnumber - Minnumber + 1;
		int i = rn.nextInt() % n;
		int randomNum = Minnumber + i;
		return randomNum;
	}
	/*
	 * Random Number for Specified range
	 */

	public int randomSpec(int Maxnumber, int Minnumber) {

		Random rn = new Random();
		int n = Maxnumber - Minnumber + 1;
		int i = rn.nextInt() % n;
		int randomNum = Minnumber + i;
		return randomNum;
	}

	/*
	 * Select an item form Dropdown.
	 * 
	 */
	public void select(WebDriver driver, String ID, String filter) {

		Select dropdown = new Select(driver.findElement(By.id(ID)));
		dropdown.selectByVisibleText(filter);

	}

	// public void SetUp(DataManager datamanager, CommonLibrary comlib, Reports
	// reports, WebDriver driver,
	// String testCaseName) throws Exception {
	//
	// if (datamanager.executionController(testCaseName).equals("Yes")) {
	// reports.scriptStartTime = comlib.getCurrentTime();
	// reports.creatFile(testCaseName);
	// driver = comlib.launchBrowser(driver, ConfigurationLibrary.browser);
	// Thread.sleep(500);
	//
	// } else {
	// System.out.println(testCaseName + " execution is skipped");
	// ConfigurationLibrary.skipCount++;
	// throw new SkipException(testCaseName + " execution is skipped");
	// }
	// }

	/*
	 * Find element on UI
	 */
	public boolean isElementPresent(WebDriver driver, By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	/*
	 * Capture screenshots
	 */
	public static void captureScreenshot(WebDriver driver, String ImageName) throws Exception {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile, new File(ConfigurationLibrary.imagePath + "//" + ImageName + ".jpg"));
	}

	/*
	 * Test case pass report
	 */
	public void pass(Reports reports, String moduleName, String testCaseName) throws Exception {
		ConfigurationLibrary.passCount = ConfigurationLibrary.passCount + 1;
		reports.summaryReport(moduleName, testCaseName, getExecutionTime(reports.scriptStartTime, getCurrentTime()),
				reports.pass);
		reports.closeFile();
	}

	/*
	 * Test case fail report
	 */
	public void fail(Reports reports, String moduleName, String tcName) throws Exception {
		ConfigurationLibrary.failCount = ConfigurationLibrary.failCount + 1;
		reports.summaryReport(moduleName, tcName, getExecutionTime(reports.scriptStartTime, getCurrentTime()),
				reports.fail);
		reports.closeFile();
	}

	/*
	 * System current date and time
	 */
	public String getCurrentTime() {
		Calendar cd = Calendar.getInstance();
		SimpleDateFormat datefor = new SimpleDateFormat("dd-MMM-yyyy HH-mm-ss");
		String date = datefor.format(cd.getTime());
		return date;
	}

	/*
	 * System current date and time
	 */
	public String getCurrentDate() {
		DateFormat datefor = new SimpleDateFormat("MM/dd/yyyy");
		Date dates = new Date();
		String date = datefor.format(dates);
		return date;
	}

	/*
	 * Method for after method
	 */
	public void afterTestRun() {
		if (Reports.testStatus) {
			Reports.passedTests++;
		} else {
			Reports.failedTests++;
		}
		Reports.testStatus = true;
	}

	/*
	 * Method for after class.
	 */
	public void afterScript(Reports reports, String moduleName, String testCaseName) throws Exception {
		// stopRecording();
		reports.executionHealthReport(moduleName);
		if (Reports.failedTests > 0) {
			fail(reports, moduleName, testCaseName);
		} else {
			pass(reports, moduleName, testCaseName);
		}
		Reports.failedTests = 0;
		Reports.passedTests = 0;
	}

	/*
	 * WebDriver wait
	 */

	public void ExplicitWait(WebDriver driver, By by) {
		WebDriverWait wait = new WebDriverWait(driver, 50);
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
	}

	/*
	 * Patient ID (Random)
	 */

	public String PatientId() {
		Random num = new Random();
		String patId = "PAT" + num.nextInt(111 * 123);
		return patId;
	}

	/*
	 * To verify if element is disabled
	 */

	public boolean isElementDisabled(WebDriver driver, By by) {
		WebElement element = driver.findElement(by);
		String text = element.getAttribute("class");
		if (text.contains("disabled"))
			return true;
		else
			return false;
	}

	/*
	 * To verify if element is checked
	 */

	public boolean isElementChecked(WebDriver driver, By by) {
		WebElement element = driver.findElement(by);
		String text = element.getAttribute("class");
		if (text.contains("checked"))
			return true;
		else
			return false;
	}

	/*
	 * Download csv file
	 */

	public void download(WebDriver driver, String downloadID, String expectedText) throws Exception {

		String filePath = System.getProperty("user.dir") + "/downloadedFiles";

		FileUtils.cleanDirectory(new File(filePath));

		driver.findElement(By.id(downloadID)).click();
		Thread.sleep(1000);

		File folder = new File(filePath);
		File[] listOfFiles = folder.listFiles();
		String actualFileName = listOfFiles[0].getName();

		Thread.sleep(1000);

		String expectedFileName = expectedText;

		Assert.assertTrue(actualFileName.contains(expectedFileName), "File mismatch");
	}

	/*
	 * Get execution time
	 */
	@SuppressWarnings("deprecation")
	public static String getExecutionTime(String executionStartTime, String executionEndTime) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH-mm-ss");
		SimpleDateFormat timeFor = new SimpleDateFormat("HH:mm:ss");

		Date endTime = sdf.parse(executionEndTime);
		Date startTime = sdf.parse(executionStartTime);
		endTime.setHours(endTime.getHours() - startTime.getHours());
		endTime.setMinutes(endTime.getMinutes() - startTime.getMinutes());
		endTime.setSeconds(endTime.getSeconds() - startTime.getSeconds());

		String date = timeFor.format(endTime);
		return date;
	}

	// Browser setup
	public WebDriver launchBrowser(WebDriver driver, String browserName) throws Exception {

		browserName = browserName.toLowerCase();
		switch (browserName) {

		case "chrome":
			String chromeDriverFilePath = System.getProperty("user.dir")+"/drivers/chromedriver" ;
			System.setProperty("webdriver.chrome.driver", chromeDriverFilePath);
			driver = new ChromeDriver();
			break;

		case "firefox":
			driver = new FirefoxDriver();
			break;

		case "ie":
			String IEDriverFilePath = System.getProperty("user.dir")+"/drivers/IEDriver";
			System.setProperty("webdriver.ie.driver", IEDriverFilePath);
			driver = new InternetExplorerDriver();

			break;

		default:
			System.out.println("Incorrect browser value");
			break;
		}

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();

		driver.get(ConfigurationLibrary.URL);
		return driver;
	}

	/*
	 * Launch application in android device.
	 */
	public AppiumDriver<MobileElement> launchAndroidApplication(AppiumDriver<MobileElement> driver) throws Exception {

		File classpathRoot = new File(System.getProperty("user.dir"));
		File appDir = new File(classpathRoot, "/app");
		File app = new File(appDir, ConfigurationLibrary.AppiumAppfileName);
		//
		//		AppiumDriverLocalService appiumDriverLocalService;
		//		
		//		AppiumServiceBuilder builder = new AppiumServiceBuilder().withAppiumJS(new File("/usr/local/lib/node_modules/appium/bin/appium.js"))
		//				 .withArgument(GeneralServerFlag.APP, app.getAbsolutePath()).withArgument(GeneralServerFlag.LOG_LEVEL, "info").usingAnyFreePort(); /*and so on*/;
		//				         appiumDriverLocalService = builder.build();
		//				         appiumDriverLocalService.start();


		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("deviceName", ConfigurationLibrary.deviceName);
		capabilities.setCapability("appium-version", "1.4.0.0");
		capabilities.setCapability("platformName", "android");
		capabilities.setCapability("udid",ConfigurationLibrary.deviceName);
		capabilities.setCapability("platformVersion", ConfigurationLibrary.apiLevel);
		capabilities.setCapability("app", app.getAbsolutePath());
		capabilities.setCapability("appPackage", ConfigurationLibrary.packageName);
		capabilities.setCapability("appActivity", ConfigurationLibrary.activityName);
		driver = new AndroidDriver<MobileElement>(new URL("http://127.0.0.1:4723/wd/hub"),capabilities);
		//driver = new AndroidDriver<MobileElement>(appiumDriverLocalService.getUrl(),capabilities);
		driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
		return driver;
	}


	/*
	 * Launch application in android device.
	 */
	public AppiumDriver<MobileElement> launchiOSApplication(AppiumDriver<MobileElement> driver) throws Exception {

		File classpathRoot = new File(System.getProperty("user.dir"));
		File appDir = new File(classpathRoot, "/app");
		File app = new File(appDir, ConfigurationLibrary.AppiumAppfileName);


		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("platformName", "iOS"); //Platform name either iOS/Android
		capabilities.setCapability("platformVersion", ConfigurationLibrary.platform_version); //Platform Version is mobile device OS version
		capabilities.setCapability("deviceName", ConfigurationLibrary.device_name); //Emulator OR Simulator OR Device name which is currently being used for automation
		capabilities.setCapability("app", app.getAbsolutePath()); //This the app path where actually the .apk OR .ipa file is located in local or remote location
		capabilities.setCapability("bundleId", ConfigurationLibrary.bundleid);
		capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 20);
		driver = new IOSDriver<MobileElement>(new URL("http://127.0.0.1:4723/wd/hub"),capabilities);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		return driver;

	}
	
	/*
	 * Parallel Execution
	 */

	@SuppressWarnings({ "static-access", "rawtypes" })
	public RemoteWebDriver launchApplication(RemoteWebDriver driver, String platform, String browser, String remoteURL) throws Exception {
		DesiredCapabilities caps = null;
		 
		if (platform.equalsIgnoreCase("ANY") && browser.equalsIgnoreCase("firefox")) {
			caps = new DesiredCapabilities().firefox();
			caps.setBrowserName("firefox");
			driver = new RemoteWebDriver(new URL(remoteURL), caps);
			
			driver.manage().window().maximize();
			driver.get("http://vetaui-appv1-genpact.run.aws-usw02-pr.ice.predix.io/#/home");
		}
		
		if (platform.equalsIgnoreCase("ANY") && browser.equalsIgnoreCase("chrome")) {
			caps = new DesiredCapabilities().chrome();
			caps.setBrowserName("chrome");
			driver = new RemoteWebDriver(new URL(remoteURL), caps);
			
			driver.manage().window().maximize();
			driver.get("http://vetaui-appv1-genpact.run.aws-usw02-pr.ice.predix.io/#/home");
		}
		
		if (platform.equalsIgnoreCase("Android") && browser.equalsIgnoreCase("chrome")) {
			File classpathRoot = new File(System.getProperty("user.dir"));
			File appDir = new File(classpathRoot, "/app");
			File app = new File(appDir, ConfigurationLibrary.AppiumAppfileName);
			
			caps = new DesiredCapabilities();
			caps.setCapability("deviceName", ConfigurationLibrary.deviceName);
			caps.setCapability("appium-version", "1.4.0.0");
			caps.setCapability("platformName", "android");
			caps.setCapability("udid",ConfigurationLibrary.deviceName);
			caps.setCapability("platformVersion", ConfigurationLibrary.apiLevel);
			caps.setCapability("app", app.getAbsolutePath());
			caps.setCapability("appPackage", ConfigurationLibrary.packageName);
			caps.setCapability("appActivity", ConfigurationLibrary.activityName);
			driver = new AndroidDriver(new URL(remoteURL), caps);
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		}
		
		if (platform.equalsIgnoreCase("MAC") && browser.equalsIgnoreCase("safari")) {
			caps = new DesiredCapabilities();
			caps.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone 6 Plus");
			caps.setCapability(MobileCapabilityType.BROWSER_NAME, "safari");
			caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, "8.4");
			driver = new IOSDriver(new URL(remoteURL), caps);
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.get("https://www.gepower.com/");
		}
		
		return driver;
		  
	}
	
	/*
	 * Launch application in fonebooth android device.
	 */
	public  RemoteWebDriver launchAndroidApplicationinFB(RemoteWebDriver driver) throws Exception {

		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("newCommandTimeout", 600);
		capabilities.setCapability("launchTimeout", 90000);

		capabilities.setCapability("deviceName", StartEndExecution.bookingDeviceID.capabilities.deviceName);
		capabilities.setCapability("browserName", StartEndExecution.bookingDeviceID.capabilities.deviceName);
		capabilities.setCapability("platformName", "Android");

		capabilities.setCapability("appActivity", ConfigurationLibrary.activityName);
		capabilities.setCapability("appPackage", ConfigurationLibrary.packageName);
		capabilities.setCapability("fullReset", true);

		driver = new RemoteWebDriver(StartEndExecution.endPoint, capabilities);
		return driver;
	}
	
	/*
	 * Launch application in fonebooth iOS device.
	 */
	public  RemoteWebDriver launchiOSApplicationinFB(RemoteWebDriver driver) throws Exception {

		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("newCommandTimeout", 600);
		capabilities.setCapability("launchTimeout", 90000);

		capabilities.setCapability("deviceName", StartEndExecution.bookingDeviceID.capabilities.deviceName);
		capabilities.setCapability("browserName", StartEndExecution.bookingDeviceID.capabilities.deviceName);
		capabilities.setCapability("platformName", "Android");

		capabilities.setCapability("appActivity", ConfigurationLibrary.activityName);
		capabilities.setCapability("appPackage", ConfigurationLibrary.packageName);
		capabilities.setCapability("fullReset", true);

		driver = new RemoteWebDriver(StartEndExecution.endPoint, capabilities);
		return driver;
	}

}
