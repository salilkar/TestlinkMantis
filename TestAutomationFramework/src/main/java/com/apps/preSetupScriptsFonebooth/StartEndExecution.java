package com.apps.preSetupScriptsFonebooth;

import java.net.URL;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.apps.model.CommonLibrary;
import com.apps.model.ConfigurationLibrary;
import com.apps.model.Reports;
import com.ssts.pcloudy.dto.booking.BookingDtoDevice;

public class StartEndExecution {
	
	public static BookingDtoDevice bookingDeviceID;
	public static URL endPoint;

	@BeforeSuite
	public void startTime() throws Exception { 
		CommonLibrary commonLibrary = new CommonLibrary();
		ConfigurationLibrary.executionStartTime = commonLibrary.getCurrentTime();
		Reports.deleteReportFolder();
		Reports.deleteLatestReportFolder();
		
		EntryPointTest entry = new EntryPointTest();
		FetchData sample = entry.test();
		bookingDeviceID = sample.getBookedDevicesID();
		System.out.println(bookingDeviceID);
		endPoint = sample.getEndpoint();
		System.out.println(endPoint);
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
