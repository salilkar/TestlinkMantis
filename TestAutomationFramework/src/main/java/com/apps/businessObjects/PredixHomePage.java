package com.apps.businessObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PredixHomePage {
	
	WebDriver driver;
	WebDriverWait wait;
	
	public PredixHomePage(WebDriver driver) {
		PageFactory.initElements(driver, this);
		this.driver = driver;
		wait = new WebDriverWait(driver, 20);
		}
	
	@FindBy(xpath = "//div[@id='menu']//a[contains(text(),'Home')]")
	private WebElement homeButton;

	@FindBy(xpath = "//body[contains(@class,'container-fluid ng-scope')]")
	private WebElement homePage;

	@FindBy(xpath = "//div[contains(@class,'navbar-collapse collapse sidebar-navbar-collapse')]//..//div[contains(text(),'Technical Modelling')]")
	private WebElement technicalModButton;

	@FindBy(xpath = "(//ul[contains(@class,'list-group scroll-left mar-0 pad-0')])[1]")
	private WebElement cutomerListText;
	
	@FindBy(xpath = "(//li[contains(@class,'list-group-item list-group-item-default ng-binding ng-scope')])[1]")
	private WebElement firstCustomerButton;

	@FindBy(xpath = "(//ul[contains(@class,'list-group scroll-left mar-0 pad-0')])[2]")
	private WebElement contactsListText;
	
	@FindBy(xpath = "(//li[contains(@class,'list-group-item list-group-item-default ng-scope')])[1]")
	private WebElement contactListButton;

	@FindBy(xpath = "//a[contains(text(),'Open')]")
	private WebElement openButton;
	
	@FindBy(xpath = "(//div[contains(@class,'col-md-12 pad-0')])")
	private WebElement fleetParameterText;
	
	@FindBy(xpath = "//a[contains(@class,'btn btn-primary btn-sm pull-left')]")
	private WebElement backButton;
	
	public boolean isHomePage() {
		wait.until(ExpectedConditions.visibilityOf(homePage));
		return homePage.isDisplayed();
	}

	public boolean isCustomerList() {
		wait.until(ExpectedConditions.visibilityOf(cutomerListText));
		return cutomerListText.isDisplayed();
	}
	
	public boolean isContactdList() {
		wait.until(ExpectedConditions.visibilityOf(contactsListText));
		return contactsListText.isDisplayed();
	}
	
	public boolean isfleetParameterList() {
		wait.until(ExpectedConditions.visibilityOf(fleetParameterText));
		return fleetParameterText.isDisplayed();
	}

	public void clickTechnicalModButton() {
		wait.until(ExpectedConditions.visibilityOf(technicalModButton));
		technicalModButton.isDisplayed();
		technicalModButton.click();
	}

	public void clickHomeButton() {
		wait.until(ExpectedConditions.visibilityOf(homeButton));
		homeButton.isDisplayed();
		homeButton.click();
	}
	
	public void firstCustomerButton() {
		wait.until(ExpectedConditions.visibilityOf(firstCustomerButton));
		firstCustomerButton.isDisplayed();
		firstCustomerButton.click();
	}
	
	public void contactListButton() {
		wait.until(ExpectedConditions.visibilityOf(contactListButton));
		contactListButton.isDisplayed();
		contactListButton.click();
	}
	
	public void clickOpenButton() {
		wait.until(ExpectedConditions.visibilityOf(openButton));
		openButton.isDisplayed();
		System.out.println("Button is present");
		openButton.click();
	}
	
	public void clickBackButton() {
		wait.until(ExpectedConditions.visibilityOf(backButton));
		backButton.isDisplayed();
		backButton.click();
	}
	
	
}
