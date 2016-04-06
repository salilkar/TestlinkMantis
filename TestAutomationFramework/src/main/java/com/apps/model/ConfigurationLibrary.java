package com.apps.model;

public class ConfigurationLibrary {

	public static final String projectName = "Automation Results";
	public static final String deviceName = "1132d0b";
	public static final String apiLevel = "5.0";
	
	public static final String AppiumAppfileName = "box.apk"; //"box.ipa"
	public static final String packageName = "com.box.android";
	public static final String activityName = "com.box.android.activities.login.SplashScreenActivity";
	
	/* Fonebooth Details */
	public static final String foneboothAppfileName = "box.apk"; //"box.ipa"
	public static final String foneboothEmailID = "vedesh.kumar@techendeavour.com";
	public static final String foneboothSessionKey = "j5dy4stx2qvm28ww647cygw3";
	public static final String foneboothPlatform = "android"; //"ios"
	
	/*iOS Device Details*/
	public static String device_name = "";
	public static String platform_version = "";
	public static String bundleid = "";
	
	//Web Appl Detials
	public static String URL = "http://vetaui-appv1-genpact.run.aws-usw02-pr.ice.predix.io/#/home";
	public static String browser = "firefox";

	public static final String summaryResultPath = "src//main//java//com//apps//result";
	public static final String detailResultPath =  "src//main//java//com//apps//result//detailResult";
	public static final String videoPath = "src//main//java//com//apps//result//videos";
	public static final String imagePath = "src//main//java//com//apps//result//images";
	public static final String resultDirectory = "src//..//..//..//..//results";
	public static final String latestDirectory = "src//..//..//..//..//latest";

	public static int failCount = 0;
	public static int passCount = 0;
	public static int skipCount = 0;

	public static int failPercentage = 0;
	public static int passPercentage = 0;

	public static String executionStartTime = null;
	public static String executionEndTime = null;

	public static String xlpath_controller = "src//main//java//com//apps//controller//Controller.xls";
	public static String xlsheet_controller = "ControllerSheet";
	public static String xlDataTable_controller = "ControllerData";
	
	public static String xlgridSheet_controller = "GridControllerSheet";
	public static String xlGridDataTable_controller = "GridControllerData";

	public static String xlpath_login = "src//main//java//com//apps//input//data//Login.xls";
	public static String xlsheet_login = "ValidData";
	public static String xlsheet_login02 = "ValidData1";
	public static String xlDataTable_login = "DataTable";

	public static int testPass = 0;
	public static int testFail = 0;

}

