package com.apps.mantis;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.mantisbt.connect.IMCSession;
import org.mantisbt.connect.MCException;
import org.mantisbt.connect.axis.MCSession;
import org.testng.Reporter;

import com.apps.model.ConfigFileReader;

public class ConnectMantis {

	private static String MANTIS_URL = null;
	private static String MANTIS_USER = null;
	private static String MANTIS_PWD = null;

	private static ConnectMantis instance = null;
	private static IMCSession session= null;


	/*** Creates an instance of connection with the Mantis
          *throws MalformedURLException the URL Mantis API is wrong
          *throws MCException if an error occurs in connection with the Mantis
          **/
	public ConnectMantis() throws MalformedURLException, MCException {
		//****************
		//MANTIS // DATA
		//****************

		/**
		 * Place the connection API with Mantis
		 */
		MANTIS_URL=ConfigFileReader.getProperty("MANTIS_URL");
		if(!ConfigFileReader.getProperty("MANTIS_URL").isEmpty()){
			MANTIS_URL=ConfigFileReader.getProperty("MANTIS_URL");
		}else{
			Reporter.log("MANTIS_URL is not defined in the Project.properties file.", 2,true);
		}

		/**
		 * User Name for connection
		 */
		MANTIS_USER=ConfigFileReader.getProperty("MANTIS_USER");
		if(!ConfigFileReader.getProperty("MANTIS_USER").isEmpty()){
			MANTIS_USER=ConfigFileReader.getProperty("MANTIS_USER");
		}else{
			Reporter.log("MANTIS_USER is not defined in the Project.properties file.", 2,true);
		}
		/**
		 * User password for connection
		 */
		MANTIS_PWD=ConfigFileReader.getProperty("MANTIS_PWD");
		if(!ConfigFileReader.getProperty("MANTIS_PWD").isEmpty()){
			MANTIS_PWD=ConfigFileReader.getProperty("MANTIS_PWD");
		}else{
			Reporter.log("MANTIS_PWD is not defined in the Project.properties file.", 2,true);
		}

		URL url = new URL(MANTIS_URL);
		session = new MCSession(url, MANTIS_USER, MANTIS_PWD);
	}

	/*** Returns a new instance or existing instance of the connection with the Mantis
          *return Instance of connection Mantis
          ***/
	public static ConnectMantis getInstance() {
		if (instance == null) {
			try {
				instance = new ConnectMantis();
			} catch (MalformedURLException ex) {
				Logger.getLogger(ConnectMantis.class.getName()).log(Level.SEVERE, null, ex);
			} catch (MCException ex) {
				Logger.getLogger(ConnectMantis.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		return instance;
	}

	/*** Take the instance to the connection with the Mantis. If not exist, it creates a new instance
          *return Session Mantis
          *throws MalformedURLException the URL Mantis API is wrong
          *throws MCException if an error occurs in connection with the Mantis
          ****/
	public static IMCSession getsession() throws MalformedURLException, MCException {
		if (session == null) {
			getInstance();
		}
		return session;
	}	
}
