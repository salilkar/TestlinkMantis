package com.apps.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GeLandingPage {

	RemoteWebDriver driver;
	WebDriverWait wait;

	By homePage = By.className("home");
	By menu = By.className("icon-ico_menu_lg");
	By customerSignInBtn = By.xpath("//a[text()='Customer Sign In']");
	By dashBoard = By.xpath("//a[text()='My Dashboard']");

	public GeLandingPage(RemoteWebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(driver, 60);
	}

	public boolean verifyHomeScreen(){
		return driver.findElement(homePage).isDisplayed();
	}

	public void clickMenu(){
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(menu)));
		driver.findElement(menu).isDisplayed();
		driver.findElement(menu).click();
	}

	public boolean isMenu(){	
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(menu)));
		return driver.findElement(menu).isDisplayed();
	}

	public void clickCustomerSignInBtn(){
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(customerSignInBtn)));
		driver.findElement(customerSignInBtn).isDisplayed();
		driver.findElement(customerSignInBtn).click();
	}

	public void clickMyDashbaord() {
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(dashBoard)));
		driver.findElement(dashBoard).isDisplayed();
		driver.findElement(dashBoard).click();
	}

}
