package com.apps.rest.api;

import static com.jayway.restassured.RestAssured.given;

import java.lang.reflect.Method;

import org.json.JSONException;
import org.json.JSONObject;
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
import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

public class PostApi {

	CommonLibrary comlib = new CommonLibrary();
	ApplicationLibrary applib = new ApplicationLibrary();
	Reports reports = new Reports();
	DataManager datamanager = new DataManager();
	WebDriver driver=null;
	String testCaseName = getClass().getSimpleName();
	String moduleName="restapi Post method";
	
	@BeforeClass
	public void startUp() throws Exception {
		driver = comlib.initialize(driver, testCaseName, datamanager, comlib, reports);
	}
	
	@BeforeMethod
	public void beforeTest(Method methodName) throws Exception {
		reports.writeTestName(methodName.getName());
	}
	
	//@Test
	public void httpPost() throws JSONException,InterruptedException{		
		
		String APIUrl = "http://172.16.17.146:8080/addNewUser";
		String APIBody = "{\"login\" : \"lukaszroslonek\", \"www\" : \"www.google.com\"}";
		Response response=null;
		String data=null;
		
		RequestSpecBuilder builder = new RequestSpecBuilder(); 
		builder.setBody(APIBody);
		builder.setContentType("application/json; charset=UTF-8");
		RequestSpecification requestSpec = builder.build();
		try {
			
			response = given().authentication().preemptive().basic("","").spec(requestSpec).when().post(APIUrl);
			JSONObject JSONResponseBody = new JSONObject(response.body().asString());
			data=JSONResponseBody.toString();
			String result = JSONResponseBody.getString("message");
			
			System.out.println("The result is :"+result);
			Assert.assertEquals(result, "User created successfully");

			reports.writeIntoFile(driver, testCaseName,"AddNewUser",APIUrl,response.getStatusCode()+"  "+response.getStatusLine(), reports.pass,
					data, comlib.getCurrentTime());
		
		} catch (Exception e) {
			reports.writeIntoFile(driver, testCaseName,"AddNewUser",APIUrl,response.getStatusCode()+"  "+response.getStatusLine(), reports.fail,
					data, comlib.getCurrentTime());
		}
	}
	
	
	@Test
	public void httpPost1() throws JSONException,InterruptedException{
		
		String APIUrl = "https://cbdrest-gateway-sldev.mozido.com/logon";
		String APIBody = "{\"tenantName\" : \"CBD\", \"credentialType\" : \"phone\", \"securityElementType\" : \"password\","
				+ " \"login\" : \"971555686606\", \"pass\" : \"Word1Pass\"}";
		Response response=null;
		String data=null;
		
		RequestSpecBuilder builder = new RequestSpecBuilder(); 
		builder.setBody(APIBody);
		builder.setContentType("application/json; charset=UTF-8");
		RequestSpecification requestSpec = builder.build();
		try {
			response = given().authentication().preemptive().basic("","").spec(requestSpec).when().post(APIUrl);
			JSONObject JSONResponseBody = new JSONObject(response.body().asString());
			System.out.println(JSONResponseBody);
			data=JSONResponseBody.toString();
			
			int result=response.getStatusCode();
			System.out.println(result);
			
			Assert.assertTrue((response.getStatusCode()==200), "The Webservice is not working fine.");	
			
			reports.writeIntoFile(driver, testCaseName,"Logon",APIUrl,response.getStatusCode()+"  "+response.getStatusLine(), reports.pass,
			 data, comlib.getCurrentTime());
		} catch (Exception e) {
			System.out.println(e);
			reports.writeIntoFile(driver, testCaseName,"Logon",APIUrl,response.getStatusCode()+"  "+response.getStatusLine(), reports.fail,
					 data, comlib.getCurrentTime());
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