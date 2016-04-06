package com.apps.mantis;



import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;



public class ImageManipulation {
	
	/**
	 * Encodes the byte array into base64 string
	 * @param imageByteArray - byte array
	 * @return String a {@link java.lang.String}
	 */
	public static String encodeImage(byte[] imageByteArray){		
		return Base64.encodeBase64URLSafeString(imageByteArray);		
	}

	/**
	 * Decodes the base64 string into byte array
	 * @param imageDataString - a {@link java.lang.String} 
	 * @return byte array
	 */
	public static byte[] decodeImage(String imageDataString) {		
		return Base64.decodeBase64(imageDataString);
	}

	public static String getscreenshot(WebDriver driver) throws Exception {
		String filename="ErrorScreenShot";
		Thread.sleep(3000);
		String filePath = System.getProperty("user.dir");
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		String destFile=filePath+"\\"+"errorImages"+"\\"+filename+GetCurrentTimeStamp().replace(":","_").replace(".","_")+".png";
		FileUtils.copyFile(scrFile, new File(destFile));
		File file=new File(destFile);
		FileInputStream imageInFile = new FileInputStream(file);
		byte imageData[] = new byte[(int)file.length()];
		imageInFile.read(imageData);
		String imageDataString = encodeImage(imageData);
		imageInFile.close();
		return imageDataString;
	}

	public static void CreateFileWithTimeStamp(String filename) {
		String filePath = System.getProperty("user.dir");
		File file = new File(filePath + "\\" + filename+GetCurrentTimeStamp().replace(":","_").replace(".","_")+".png");
		try {
			if (!file.exists()) {
				file.createNewFile();
				System.out.println("File is created; file name is " + file.getName());
			} else {
				System.out.println("File already exist");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	// Get current system time
	public static String GetCurrentTimeStamp() {
		SimpleDateFormat sdfDate = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss.SSS");// dd/MM/yyyy
		Date now = new Date();
		String strDate = sdfDate.format(now);
		return strDate;
	}
	// Get Current Host Name
	public static String GetCurrentTestHostName() throws UnknownHostException {
		InetAddress localMachine = InetAddress.getLocalHost();
		String hostName = localMachine.getHostName();
		return hostName;
	}
	// Get Current User Name
	public static String GetCurrentTestUserName() {
		return System.getProperty("user.name");
	}

}
