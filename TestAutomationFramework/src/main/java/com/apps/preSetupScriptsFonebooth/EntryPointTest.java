package com.apps.preSetupScriptsFonebooth;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.apps.model.ConfigurationLibrary;
import com.ssts.pcloudy.ConnectError;
import com.ssts.pcloudy.Connector;
import com.ssts.pcloudy.dto.booking.BookingDtoDevice;
import com.ssts.pcloudy.dto.device.MobileDevice;
import com.ssts.pcloudy.dto.file.PDriveFileDTO;


public class EntryPointTest {

	public FetchData test() throws Exception {

		File classpathRoot = new File(System.getProperty("user.dir"));
		File appDir = new File(classpathRoot, "/app");
		File appPath = new File(appDir, ConfigurationLibrary.foneboothAppfileName);

		System.out.println(appPath);

		Connector pCloudyCONNECTOR = new Connector(
				"https://fonebooth.techendeavour.com/api");
		// UserAuthenticationoverpCloudy
		String authToken = pCloudyCONNECTOR.authenticateUser(
				ConfigurationLibrary.foneboothEmailID, ConfigurationLibrary.foneboothSessionKey);
		List<MobileDevice> selectedDevices = new ArrayList<MobileDevice>();
		// GetDevicesfrompCloudy
		MobileDevice[] devices = pCloudyCONNECTOR.getDevices(authToken, 5,
				ConfigurationLibrary.foneboothPlatform, true);
		for (int i = 1; i <= devices.length; i++) {
			MobileDevice device = devices[i - 1];
			String onlineStatus = "Busy";
			if (device.available)
				onlineStatus = "Available";
			System.out.println(i + "). [" + onlineStatus + "]"
					+ String.format("%s-" + 40 + "s", "  " + device.full_name)
					+ " v" + device.version);
		}
		System.out.println("Select An Available Device:");
		// Getinputfromconsole
		//int select = new Scanner(System.in).nextInt();
		//MobileDevice choosenDevice = devices[select - 1];
		MobileDevice choosenDevice = devices[1 - 1];
		if (choosenDevice.available == false)
			throw new ConnectError("Device not available for booking.");
		selectedDevices.add(choosenDevice);
		// BooktheselecteddeviceinpCloudy
		BookingDtoDevice[] bookedDevicesIDs = pCloudyCONNECTOR
				.bookDevicesForAppium(authToken, selectedDevices, 5,
						"friendlySessionName");
		System.out.println("Device booked successfully");

		PDriveFileDTO alreadyUploadedApp = pCloudyCONNECTOR
				.getAvailableAppIfUploaded(authToken, appPath.getName());

		if (alreadyUploadedApp == null) {
			// UploadapkinpCloudy
			alreadyUploadedApp = pCloudyCONNECTOR.uploadApp(authToken,
					appPath);
			System.out.println("app file uploaded successfully");
		}
		pCloudyCONNECTOR.initAppiumHubForApp(authToken, alreadyUploadedApp);

		URL endpoint = pCloudyCONNECTOR.getAppiumEndpoint(authToken);

		System.out.println(endpoint);

		System.out.println("Results Folder:" + pCloudyCONNECTOR.getAppiumReportFolder(authToken));

		System.out.println(bookedDevicesIDs[0]);

		System.out.println(" ----------------------- ");
		System.out.println("Connected to Appium Endpoint successfully.");
		System.out.println(" ----------------------- ");

		FetchData sample = new FetchData();
		sample.setBookedDevicesID(bookedDevicesIDs[0]);
		sample.setEndpoint(endpoint);
		return sample;
	}
}
