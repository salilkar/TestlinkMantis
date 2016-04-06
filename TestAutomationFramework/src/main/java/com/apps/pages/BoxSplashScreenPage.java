package com.apps.pages;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BoxSplashScreenPage {
	
	WebDriverWait wait;

	public BoxSplashScreenPage(RemoteWebDriver driver) {
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		wait = new WebDriverWait(driver, 40);
	}

	@FindBy(id = "com.box.android:id/tour_container")
	public WebElement splashScreen;

	@FindBy(id = "com.box.android:id/loginButton")
	public WebElement loginBtn;

	@FindBy(id = "com.box.android:id/newToBoxButton")
	public WebElement signUpBtn;

	public boolean isSplashScreenPage() {
		wait.until(ExpectedConditions.visibilityOf(splashScreen));
		return splashScreen.isDisplayed();
	}
	
	public void clickLoginBtn() {
		loginBtn.click();
	}
	
	public void clickSignUpBtn() {
		signUpBtn.click();
	}
}
