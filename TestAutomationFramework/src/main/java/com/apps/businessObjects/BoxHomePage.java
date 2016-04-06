package com.apps.businessObjects;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BoxHomePage {
	
	WebDriverWait wait;
	RemoteWebDriver driver;

	public BoxHomePage(RemoteWebDriver driver) {
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		this.driver = driver;
		wait = new WebDriverWait(driver, 40);
	}
	
	@FindBy(id = "com.box.android:id/mainContainer")
	public WebElement homePage;
	
	@FindBy(id = "android:id/home")
	public WebElement menu;
	
	@FindBy(id = "com.box.android:id/folder_upload_create_file_group")
	public WebElement addItems;
	
	@FindBy(xpath = "//android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/android.widget.RelativeLayout[1]/android.widget.RelativeLayout[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.LinearLayout[1]/android.widget.LinearLayout[1]/android.widget.ListView[1]/android.widget.TextView[5]")
	public WebElement addPhotoVideo;
	
	@FindBy(id = "com.box.android:id/btnUploadFrom")
	public WebElement uploadFrom;
	
	@FindBy(id = "com.box.android:id/btnPhoto")
	public WebElement uploadPicOption;
	
	@FindBy(xpath = "//android.view.View[1]/android.widget.FrameLayout[2]/android.view.View[1]/android.widget.LinearLayout[1]/android.widget.FrameLayout[1]/android.widget.FrameLayout[1]/android.widget.ListView[1]/android.widget.LinearLayout[1]")
	public WebElement selectPic;
	
	@FindBy(id = "com.box.android:id/selectCurrentFolderButton")
	public WebElement uploadBtn;
	
	@FindBy(id = "com.box.android:id/btnOverwrite")
	public WebElement uploadAsNewVersion;
	
	@FindBy(id = "com.box.android:id/itemNameText")
	public WebElement settingsOption;
	
	@FindBy(id = "com.box.android:id/btnLogout")
	public WebElement logoutBtn;
	
	@FindBy(id = "com.box.android:id/bottomMask")
	public WebElement bottomMask;
	
	@FindBy(id = "com.box.android:id/btnOK")
	public WebElement logoutOK;
	
	public boolean isHomePage() {
		wait.until(ExpectedConditions.visibilityOf(bottomMask));
		bottomMask.click();
		bottomMask.click();
		wait.until(ExpectedConditions.visibilityOf(homePage));
		return homePage.isDisplayed();
	}
	
	public void clickMenuBar() {
		wait.until(ExpectedConditions.visibilityOf(menu));
		menu.click();
	}
	
	@SuppressWarnings("rawtypes")
	public void selectOption(String text) {
		wait.until(ExpectedConditions.visibilityOf(menu));
		((AndroidDriver) driver).scrollToExact(text).click();
	}
	
	public boolean isLogoutBtn() {
		wait.until(ExpectedConditions.visibilityOf(logoutBtn));
		return logoutBtn.isDisplayed();
	} 
	
	public void clickLogout() {
		wait.until(ExpectedConditions.visibilityOf(logoutBtn));
		logoutBtn.click();
		
		wait.until(ExpectedConditions.visibilityOf(logoutOK));
		logoutOK.click();
	}
	
	public void uploadPhoto() {
		wait.until(ExpectedConditions.visibilityOf(addItems));
		addItems.click();
		
		wait.until(ExpectedConditions.visibilityOf(addPhotoVideo));
		addPhotoVideo.click();
		
		wait.until(ExpectedConditions.visibilityOf(uploadFrom));
		uploadFrom.click();
		
		wait.until(ExpectedConditions.visibilityOf(uploadPicOption));
		uploadPicOption.click();
		
		wait.until(ExpectedConditions.visibilityOf(selectPic));
		selectPic.click();
		
		wait.until(ExpectedConditions.visibilityOf(uploadBtn));
		uploadBtn.click();
		
		if(uploadAsNewVersion.isDisplayed()) {
			uploadAsNewVersion.click();
		}
		
	}
}
