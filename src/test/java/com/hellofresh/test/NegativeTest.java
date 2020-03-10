package com.hellofresh.test;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.hellofresh.modals.BookingDatesModal;
import com.hellofresh.modals.BookingModal;
import com.hellofresh.modals.ResponseEntityModal;
import com.hellofresh.utils.DeserizalizeUtil;
import com.hellofresh.utils.Response;
import com.hellofresh.utils.TestUtils;

public class NegativeTest extends BaseTest {

	@Test(description = "Create duplicate booking for the same date")
	public void TC05_POST_CreateBookinDuplicateForSameDate() throws Exception {

		// Arrange
		String uri = "/booking";
		LOGGER.info("End point set as: " + uri);
		DeserizalizeUtil<BookingModal> deserzialize = new DeserizalizeUtil<BookingModal>(BookingModal.class);

		BookingModal requestmodal = deserzialize.deserzializeJSONFiletoModal("BookingRequestData.json");
		LOGGER.info("Deserializing the JSON file to BookingModal");

		// Act
		response = service.createBooking(uri, requestmodal);

		// Assert
		testUtil.checkStatusCode(response, Response.CREATED.getCode());

		LOGGER.info("POST call made to create a second booking with same details");
		response = service.createBooking(uri, requestmodal);
		testUtil.checkStatusCode(response, Response.ERROR.getCode());
	}

	@Test(description = "Calling incorrect end-point")
	public void TC06_GET_IncorrectUriTest() throws Exception {

		// Arrange
		String invalidUri = "/bookinggggg";
		int bookingId = 1;

		// Act
		response = service.getBookingbyId(invalidUri, bookingId);
		testUtil.checkStatusCode(response, Response.NOT_FOUND.getCode());

		// Assert
		String responseBody = testUtil.getResponseBody(response);
		DeserizalizeUtil<ResponseEntityModal> deserzialize = new DeserizalizeUtil<ResponseEntityModal>(
				ResponseEntityModal.class);
		ResponseEntityModal errorModal = deserzialize.deserzializeResponseEntityFiletoModal("404Error_DataModal.json");
		LOGGER.info("Deserializing the JSON file to 404Error_DataModal");
		Assert.assertTrue(
				testUtil.performModaltoModalComparisonWithCustomization(gson.toJson(errorModal), responseBody),
				"Validating Modal to Modal comparison");
		LOGGER.info("Modal to Modal comparison is sucessful");
		// Assertion by field name
		Assert.assertEquals(errorModal.getError(), "Not Found", "Validating error : ");
		Assert.assertEquals(errorModal.getMessage(), "No message available", "Validating error message: ");
	}

	@Test(description = "Create booking when check-in date is greater than check-out date")
	public void TC07_POST_CheckinDateGreaterThanCheckoutDate() throws Exception {

		// Arrange
		String uri = "/booking";
		String checkindate = "2020-04-14";
		String checkoutdate = "2020-04-10";
		LOGGER.info("End point set as: " + uri);
		DeserizalizeUtil<BookingModal> deserzialize = new DeserizalizeUtil<BookingModal>(BookingModal.class);

		BookingModal requestmodal = deserzialize.deserzializeJSONFiletoModal("BookingRequestData.json");
		LOGGER.info("Deserializing the JSON file to BookingModal");
		requestmodal.getBookingdates().setCheckin(checkindate);
		requestmodal.getBookingdates().setCheckout(checkoutdate);
		LOGGER.info("Check-out date modified to be less than Check-in date");
		// Act
		response = service.createBooking(uri, requestmodal);

		// Assert
		testUtil.checkStatusCode(response, Response.ERROR.getCode());
	}

	@Test(priority = 2, dataProvider = "createIncorrectBooking", dataProviderClass = TestUtils.class)
	public void TC08_POST_CreateBookingWithMissingData(Map<String, String> data) throws Exception {

		// Arrange
		LOGGER.info("Starting TEST STEP: " + data.get("TC_Name"));
		String uri = "/booking";
		LOGGER.info("End point set as: " + uri);
		BookingModal requestModal = new BookingModal();
		BookingDatesModal dateModal = new BookingDatesModal();
		requestModal.setBookingid(Integer.parseInt(data.get("bookingid")));
		requestModal.setFirstname(data.get("firstname"));
		requestModal.setLastname(data.get("lastname"));
		requestModal.setEmail(data.get("email"));
		requestModal.setPhone(data.get("phone"));
		requestModal.setRoomid(Integer.parseInt(data.get("roomid")));
		requestModal.setDepositpaid(Boolean.valueOf(data.get("depositpaid")));
		dateModal.setCheckin(data.get("checkindate"));
		dateModal.setCheckout(data.get("checkoutdate"));
		requestModal.setBookingdates(dateModal);
		LOGGER.info("Building of POST request modal is completed");
		// Act
		LOGGER.info("Make POST call to create a booking");
		response = service.createBooking(uri, requestModal);

		// Assert
		switch (data.get("TC_Name")) {
		case "FirstName_Missing":
			LOGGER.info("Validating for case: " + data.get("TC_Name"));
			testUtil.checkStatusCode(response, Response.BAD_REQUEST.getCode());
			break;

		case "LastName_SpecialCharacters":
			testUtil.checkStatusCode(response, Response.CREATED.getCode());
			break;

		case "Email_Incorrect_Format":
			testUtil.checkStatusCode(response, Response.BAD_REQUEST.getCode());
			break;

		case "BookingId_Float":
			testUtil.checkStatusCode(response, Response.BAD_REQUEST.getCode());
			break;

		case "RoomId_Zero":
			testUtil.checkStatusCode(response, Response.BAD_REQUEST.getCode());
			break;

		case "PhonNumber_LessThan8":
			testUtil.checkStatusCode(response, Response.BAD_REQUEST.getCode());
			break;

		case "CheckInOut_WithTime":
			testUtil.checkStatusCode(response, Response.CREATED.getCode());
			break;

		case "CheckInTime_Incorrect":
			testUtil.checkStatusCode(response, Response.CREATED.getCode());
			break;

		case "DepositPaid_Blank":
			testUtil.checkStatusCode(response, Response.ERROR.getCode());
			break;

		}
		LOGGER.info("Completed TEST STEP: " + data.get("TC_Name"));

	}
}
