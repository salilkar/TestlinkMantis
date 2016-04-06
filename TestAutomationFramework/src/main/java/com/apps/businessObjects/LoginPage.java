package com.apps.businessObjects;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
	
	WebDriverWait wait;

	public LoginPage(RemoteWebDriver driver) {
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		wait = new WebDriverWait(driver, 40);
	}
	
	@FindBy(id = "android:id/progress")
	public By loading;
	
	@FindBy(id = "com.box.android:id/oauthview")
	public WebElement loginScreen;
	
	@FindBy(xpath = "//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.RelativeLayout[1]/android.view.View[1]/android.view.View[1]/android.widget.EditText[1]")
	public WebElement username;
	
	@FindBy(xpath = "//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.RelativeLayout[1]/android.view.View[1]/android.view.View[2]/android.widget.EditText[1]")
	public WebElement password;
	
	@FindBy(xpath = "//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.RelativeLayout[1]/android.view.View[1]/android.view.View[3]/android.widget.Button[1]")
	public WebElement loginButton;
	
	@FindBy(id = "com.box.android:id/btnCancel")
	public WebElement dismissBtn;

	public boolean isLoginPage() {
		wait.until(ExpectedConditions.visibilityOf(loginScreen));
		return loginScreen.isDisplayed();
	}
	
	public void enterUsername(String Username) {
		wait.until(ExpectedConditions.visibilityOf(username));
		username.sendKeys(Username);
	}
	
	public void enterPassword(String Password) {
		wait.until(ExpectedConditions.visibilityOf(password));
		password.sendKeys(Password);
	}
	
	public void clickLogin() {
		wait.until(ExpectedConditions.visibilityOf(loginButton));
		loginButton.click();
	}
	
	public void loginToApp(String username, String passWord) {
		enterUsername(username);
		enterPassword(passWord);
		clickLogin();
	}
}
