package com.apps.preSetupScriptsFonebooth;

import org.openqa.selenium.remote.RemoteWebDriver;

public class AppiumConnection {

	static AppiumConnection instance;

	RemoteWebDriver driver = null;

	private AppiumConnection() {
		// to enforce singleton
	}

	public static AppiumConnection getInstance() {
		if (instance == null)
			instance = new AppiumConnection();

		return instance;
	}

	public RemoteWebDriver getDriver() {
		return driver;
	}

	public void setDriver(RemoteWebDriver driver) {
		this.driver = driver;
	}

}
