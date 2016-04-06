package com.apps.prerequisitesParallel;


import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.apps.model.CommonLibrary;
import com.apps.model.ConfigurationLibrary;
import com.apps.model.Reports;


public class BaseClass {

	@BeforeSuite
	public void startTime() throws Exception { 
		CommonLibrary commonLibrary = new CommonLibrary();
		ConfigurationLibrary.executionStartTime = commonLibrary.getCurrentTime();
		Reports.deleteReportFolder();
		Reports.deleteLatestReportFolder();
	}
	
	@AfterSuite
	public void endTime() throws Exception {
		CommonLibrary commonLibrary = new CommonLibrary();
		Reports reports = new Reports();
		ConfigurationLibrary.executionEndTime = commonLibrary.getCurrentTime();
		System.out.println("End Time "+ ConfigurationLibrary.executionEndTime);

		reports.writingSummaryReport();
	}
}
