package com.apps.rest.api;
import static com.jayway.restassured.RestAssured.get;

import java.lang.reflect.Method;

import org.json.JSONArray;
import org.json.JSONException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.apps.model.ApplicationLibrary;
import com.apps.model.CommonLibrary;
import com.apps.model.DataManager;
import com.apps.model.Reports;
import com.jayway.restassured.response.Response;

public class GetApi {
	CommonLibrary comlib = new CommonLibrary();
	ApplicationLibrary applib = new ApplicationLibrary();
	Reports reports = new Reports();
	DataManager datamanager = new DataManager();
	String testCaseName = getClass().getSimpleName();
	String moduleName="restapi get method";
	WebDriver driver=null;
	Response response =null;
	
	@BeforeClass
	public void startUp() throws Exception {
		driver = comlib.initialize(driver, testCaseName, datamanager, comlib, reports);
	}
	
	@BeforeMethod
	public void beforeTest(Method methodName) throws Exception {
		reports.writeTestName(methodName.getName());
	}
	
		@Test
		public void getRequestFindCapital() throws JSONException {
		
			try{
			//make get request to fetch capital of norway
			response = get("http://restcountries.eu/rest/v1/name/norway");
			
			//Fetching response in JSON
			JSONArray jsonResponse = new JSONArray(response.asString());
			
			//Fetching value of capital parameter
			String capital = jsonResponse.getJSONObject(0).getString("capital");
	
			//Asserting that capital of Norway is Oslo
			Assert.assertEquals(capital, "Oslo");
			
			Assert.assertTrue((response.getStatusCode()==200), "The Webservice is not working fine.");
			
			reports.writeIntoFile(driver, testCaseName, "user created success", "tried performing thru restapi","user is added", reports.pass,
					response.contentType()+" "+response.getStatusLine()+" "+response.getStatusCode(), comlib.getCurrentTime());	

		} catch (Exception e) {
			System.out.println(e);
			reports.writeIntoFile(driver, testCaseName, "user created success", "tried performing thru restapi","user is added", reports.fail,
					response.contentType()+" "+response.getStatusLine()+" "+response.getStatusCode(), comlib.getCurrentTime());
		}
	}
	
		//@Test
		public void getUserDetails() throws JSONException {
		
			try{
			response = get("http://172.16.17.146:8080/api/userDetails/test");
			
			//Fetching response in JSON
			JSONArray jsonResponse = new JSONArray(response.asString());
			System.out.println("...."+jsonResponse);
			
			String name = jsonResponse.getJSONObject(0).getString("firstName");
	
			Assert.assertEquals(name, "Steve");
			
			Assert.assertTrue((response.getStatusCode()==200), "The Webservice is not working fine.");
			
			reports.writeIntoFile(driver, testCaseName, "user created success", "tried performing thru restapi","user is added", reports.pass,
					response.contentType()+" "+response.getStatusLine()+" "+response.getStatusCode(), comlib.getCurrentTime());	

		} catch (Exception e) {
			System.out.println(e);
			reports.writeIntoFile(driver, testCaseName, "user created success", "tried performing thru restapi","user is added", reports.fail,
					response.contentType()+" "+response.getStatusLine()+" "+response.getStatusCode(), comlib.getCurrentTime());
		}
	}
		
		@AfterMethod
		public void afterTest() throws Exception {
			comlib.afterTestRun();
		}

		@AfterClass
		public void tearDown() throws Exception {
			driver.quit();
			comlib.quit(moduleName, testCaseName, datamanager, comlib, reports);
		
		}	
}
