package com.hellofresh.services;

import org.apache.http.HttpResponse;

import com.hellofresh.modals.BookingModal;
import com.hellofresh.utils.LoggerUtils;

public class ServiceClass extends BaseService {

	HttpResponse response;
	String url;
	private static final LoggerUtils LOGGER = new LoggerUtils(ServiceClass.class);

	public HttpResponse getAllBookings(String uri) throws Exception {
		url = propertyUtils.getProperty("baseUri") + uri + "/";
		LOGGER.info("Calling GET request by passing URI");
		this.response = client().sendGET(url);
		LOGGER.info("GET call is succesful");
		
		return response;
	}

	public HttpResponse getBookingbyId(String uri, int bookingId) throws Exception {
		url = propertyUtils.getProperty("baseUri") + uri + "/" + bookingId;
		LOGGER.info("Calling GET request by passing URI and booking Id");
		this.response = client().sendGET(url);
		LOGGER.info("GET call is succesful");

		return response;
	}

	public HttpResponse createBooking(String uri, BookingModal modal) throws Exception {
		url = propertyUtils.getProperty("baseUri") + "/" + uri + "/";
		LOGGER.info("Calling POST request by passing URI and BOOKING modal");
		this.response = client().sendPOST(url, modal);
		LOGGER.info("POST call is succesful");
		return response;
	}

	public HttpResponse updateBooking(String uri, BookingModal modal, int bookingId) throws Exception {
		url = propertyUtils.getProperty("baseUri") + "/" + uri + "/" + bookingId;
		LOGGER.info("Calling POST request by passing URI and BOOKING modal");
		this.response = client().sendPUT(url, modal);
		LOGGER.info("PUT call is succesful");
		return response;
	}

	public HttpResponse deletBooking(String uri, BookingModal modal, int bookingId) throws Exception {
		// Arrange
		url = propertyUtils.getProperty("baseUri") + "/" + uri + "/" + bookingId;
		LOGGER.info("Calling POST request by passing URI and BOOKING modal");
		this.response = client().sendDELETE(url);
		LOGGER.info("DELETE call is succesful");

		return response;
	}

}
