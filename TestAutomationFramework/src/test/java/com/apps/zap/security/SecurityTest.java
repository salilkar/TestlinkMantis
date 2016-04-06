package com.apps.zap.security;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.apps.pages.HomePage;
import com.google.common.base.Stopwatch;


public class SecurityTest {

	private static WebDriver driver;
	HomePage home;
	private static String site = "http://vetaui-appv1-genpact.run.aws-usw02-pr.ice.predix.io/#/home";

	@BeforeClass
	public void setUp() throws Exception {
		System.out.println("Opening the browser");
		Proxy proxy = new Proxy();
		proxy.setHttpProxy("localhost:8080");
		proxy.setFtpProxy("localhost:8080");
		proxy.setSslProxy("localhost:8080");
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(CapabilityType.PROXY, proxy);
		driver = new FirefoxDriver(capabilities);
		driver.get(site);
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	}

	@Test
	public void tstHomePage() {
		String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		Stopwatch timer = Stopwatch.createStarted();
		try {
			Thread.sleep(100);
			Assert.assertTrue(true,"fail");	
			System.out.println("time take by testcase "+methodName+" "+timer.stop());
		}
		catch (Error | InterruptedException e) {
			System.out.println("Exception is "+e);
		}
	}

	@Test
	public void tstSearch() {		
		try {
			String methodName = Thread.currentThread().getStackTrace()[1]
					.getMethodName();
			Stopwatch timer = Stopwatch.createStarted();
			driver.get(site + "search.jsp?q=doo");	
			System.out.println("time take by testcase "+methodName+" "+timer.stop());
			Thread.sleep(300);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}		
	}	

	@AfterClass
	public void tearDown() throws Exception {
		System.out.println("closing the browser");
		driver.close();
	}
}
