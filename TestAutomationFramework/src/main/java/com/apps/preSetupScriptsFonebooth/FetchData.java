package com.apps.preSetupScriptsFonebooth;

import java.net.URL;

import com.ssts.pcloudy.dto.booking.BookingDtoDevice;

public class FetchData {
	BookingDtoDevice bookedDevicesID;
	URL endpoint;

	public BookingDtoDevice getBookedDevicesID() {
		return bookedDevicesID;
	}
	public void setBookedDevicesID(BookingDtoDevice bookedDevicesIDs) {
		this.bookedDevicesID = bookedDevicesIDs;
	}
	public URL getEndpoint() {
		return endpoint;
	}
	public void setEndpoint(URL endpoint) {
		this.endpoint = endpoint;
	}



}
