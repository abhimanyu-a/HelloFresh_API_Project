package com.hellofresh.test;

import java.util.Map;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.hellofresh.modals.BookingDatesModal;
import com.hellofresh.modals.BookingModal;
import com.hellofresh.modals.CreatedBookingModal;
import com.hellofresh.modals.GetAllBookingsModal;
import com.hellofresh.utils.DeserizalizeUtil;
import com.hellofresh.utils.LoggerUtils;
import com.hellofresh.utils.Response;
import com.hellofresh.utils.TestUtils;

public class DriverTest extends BaseTest {

	private static final LoggerUtils LOGGER = new LoggerUtils(DriverTest.class);

	@Test(priority = 0, enabled = true, description = "Test that at least 1 existing booking is returned in the response")
	public void TC01_GET_GetAllBookingsTest() throws Exception {

		// Arrange
		String uri = "/booking";
		DeserizalizeUtil<GetAllBookingsModal> deserialize = new DeserizalizeUtil<GetAllBookingsModal>(
				GetAllBookingsModal.class);
		LOGGER.info("End point set as: " + uri);

		// Act
		// Make get call to retrieve all bookings
		response = service.getAllBookings(uri);
		testUtil.checkStatusCode(response, Response.OK.getCode());
		// Convert response to modal
		GetAllBookingsModal resModal = deserialize.deserzializeStringtoModal(testUtil.getResponseBody(response));
		LOGGER.info("Converted response body to required modal");
		//Assert
		// Use that modal for assertion
		Assert.assertTrue(resModal.getBookings().stream().count() > 0, "validating number of existing bookings");
	}

	@Test(priority = 1, enabled = true, description = "Test that the data returned for an existing booking matches")
	public void TC02_GET_RetrieveBookingById() throws Exception {

		// Arrange
		String uri = "/booking";
		int bookingId = 1;
		DeserizalizeUtil<BookingModal> deserialize = new DeserizalizeUtil<BookingModal>(BookingModal.class);
		LOGGER.info("End point set as: " + uri);
		LOGGER.info("Testing for Booking Id: " + bookingId);
		BookingModal expectedModal = deserialize.deserzializeJSONFiletoModal("ExistingBookingResponseData.json");

		// Act
		// Make GET call to retrieve booking details by Id
		response = service.getBookingbyId(uri, bookingId);
		LOGGER.info("GET call has been made");
		
		// Assert
		testUtil.checkStatusCode(response, Response.OK.getCode());
		String responseBody =testUtil.getResponseBody(response);
		testUtil.performModaltoModalComparison(responseBody, gson.toJson(expectedModal));
		BookingModal resModal = deserialize.deserzializeStringtoModal(responseBody);
		LOGGER.info("Request modal and response modal do match");

		Assert.assertEquals(resModal.getBookingid(), 1, "Validating booking Id: ");
		Assert.assertEquals(resModal.getFirstname(), "James", "Validating first name: ");
		Assert.assertEquals(resModal.getBookingdates().getCheckin(), "2020-02-01", "Validating check-in date: ");
	}

	@Test(priority = 2, enabled = true, dataProvider = "createNewBooking", dataProviderClass = TestUtils.class)
	public void TC03_POST_CreateBooking(Map<String, String> data) throws Exception {

		// Arrange
		String uri = "/booking";
		LOGGER.info("End point set as: " + uri);
		DeserizalizeUtil<CreatedBookingModal> deserialize = new DeserizalizeUtil<CreatedBookingModal>(CreatedBookingModal.class);
		BookingModal reqModal = new BookingModal();
		BookingDatesModal dateModal = new BookingDatesModal();
		reqModal.setBookingid(Integer.parseInt(data.get("bookingid")));
		reqModal.setFirstname(data.get("firstname"));
		reqModal.setLastname(data.get("lastname"));
		reqModal.setEmail(data.get("email"));
		reqModal.setPhone(data.get("phone"));
		reqModal.setRoomid(Integer.parseInt(data.get("roomid")));
		reqModal.setDepositpaid(Boolean.valueOf(data.get("depositpaid")));
		dateModal.setCheckin(data.get("checkindate"));
		dateModal.setCheckout(data.get("checkoutdate"));
		reqModal.setBookingdates(dateModal);
		LOGGER.info("Building of POST request modal is completed");
		// Act
		LOGGER.info("Make POST call to create a booking");
		response = service.createBooking(uri, reqModal);
		testUtil.checkStatusCode(response, Response.CREATED.getCode());
		
		CreatedBookingModal resModal = deserialize.deserzializeStringtoModal(testUtil.getResponseBody(response));
		LOGGER.info("Response body has been converted to required modal");
		int bookingid = resModal.getBookingid();

		// Assert
		testUtil.checkStatusCode(response, Response.CREATED.getCode());
		Assert.assertEquals(resModal.getBooking().getFirstname(), data.get("firstname"),
				"Validating first name for the newly created booking");

		// The below logic makes a GET call to retrieve booking details from recently
		// created BookingId
		response = service.getBookingbyId(uri, bookingid);
		testUtil.checkStatusCode(response, Response.OK.getCode());
		LOGGER.info("New booking created");
	}

	@AfterMethod
	public void afterMethod(ITestResult result) {
		if (result.getStatus() == ITestResult.FAILURE) {
			LOGGER.error("Test case " + result.getMethod().getMethodName() + " has FAILED: "
					+ result.getThrowable().getMessage());
		} else {
			LOGGER.info("PASSED: " + result.getMethod().getMethodName());
		}
	}

}
