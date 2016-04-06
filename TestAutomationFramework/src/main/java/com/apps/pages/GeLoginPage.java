package com.apps.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GeLoginPage {

	RemoteWebDriver driver;
	WebDriverWait wait;

	By userID = By.name("USER");
	By password = By.name("PASSWORD");
	By signIn = By.xpath("//button[contains(@type,'submit')]");

	public GeLoginPage(RemoteWebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(driver, 60);
	}

	public boolean isLoginPage() {
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(userID)));
		return driver.findElement(userID).isDisplayed();
	}

	//Set user name in textbox

	public void setUserName(String strUserName){
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(userID)));
		driver.findElement(userID).sendKeys(strUserName);
	}

	//Set password in password textbox

	public void setPassword(String strPassword){
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(password)));
		driver.findElement(password).sendKeys(strPassword);
	}

	//Click on login button

	public void clickLogin(){
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(signIn)));
		driver.findElement(signIn).click();
	}


	public void loginToApp(String username, String passWord) {
		this.setUserName(username);
		this.setPassword(passWord);
		this.clickLogin();
	}


}
