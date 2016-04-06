package com.apps.businessObjects;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BoxRegistrationPage {
	
	WebDriverWait wait;

	public BoxRegistrationPage(RemoteWebDriver driver) {
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		wait = new WebDriverWait(driver, 40);
	}
	
	@FindBy(xpath = "//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.RelativeLayout[1]/android.view.View[1]")
	public WebElement registrationPage;

	@FindBy(xpath = "//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.RelativeLayout[1]/android.view.View[1]/android.view.View[2]/android.widget.EditText[1]")
	public WebElement emailAddress;

	@FindBy(xpath = "//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.RelativeLayout[1]/android.view.View[1]/android.view.View[3]/android.widget.EditText[1]")
	public WebElement createPassword;

	@FindBy(xpath = "//android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.RelativeLayout[1]/android.view.View[1]/android.view.View[5]/android.widget.Button[1]")
	public WebElement signUpBtn;

	public boolean isRegistrationScreenPage() {
		wait.until(ExpectedConditions.visibilityOf(emailAddress));
		return emailAddress.isDisplayed();
	}
	
	public void enterEmailAddress(String EmailAddress) {
		wait.until(ExpectedConditions.visibilityOf(emailAddress));
		emailAddress.sendKeys(EmailAddress);
	}
	
	public void createNewPassword(String newPassword) {
		wait.until(ExpectedConditions.visibilityOf(createPassword));
		createPassword.sendKeys(newPassword);
	}
	
	public void clickSignUpBtn() {
		wait.until(ExpectedConditions.visibilityOf(signUpBtn));
		signUpBtn.click();
	}
	
	public void createNewUser(String emailAddress, String passWord) {
		enterEmailAddress(emailAddress);
		createNewPassword(passWord);
		clickSignUpBtn();
	}
}
