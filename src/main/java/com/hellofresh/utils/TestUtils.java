package com.hellofresh.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.json.JSONException;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.comparator.CustomComparator;
import org.testng.Assert;
import org.testng.annotations.DataProvider;

public class TestUtils {

	private static final LoggerUtils LOGGER = new LoggerUtils(TestUtils.class);
	private static final DataFormatter dataFormatter = new DataFormatter();
	private static final String FILE = "src/test/resources/Datasheet.xlsx";
	private static final String FILE_TC_8 = "src/test/resources/DataSheet_MissingFields.xlsx";
	private static final String SHEET_NAME = "Master";
	private static final String SHEET_NAME_TC_8 = "Data";

	// This method verifies the http response status returned
	public void checkStatusCode(HttpResponse res, int statusCode) {

		try {
			Assert.assertEquals(res.getStatusLine().getStatusCode(), statusCode, "HTTP Response Status Check Failed!");
			LOGGER.info("Http Response Status code is as expected : " + statusCode);
		} catch (AssertionError e) {

			LOGGER.fail("API Response Http Status expected was [" + res.getStatusLine().getStatusCode()
					+ "] and actual is [" + statusCode + "]");
		}
	}

	// This method extracts the response body from HttpResponse
	public String getResponseBody(HttpResponse response) {
		String resBody = null;
		try {
			HttpEntity resEntity = response.getEntity();
			resBody = EntityUtils.toString(resEntity);
			LOGGER.info("Reponse body extracted");
			return resBody;
		} catch (ParseException | IOException e) {
			LOGGER.error("Unable to extract response body: " + e.toString());
		}

		return resBody;
	}

	public boolean performModaltoModalComparison(String expectedJSON, String responseBody) {
		try {
			JSONAssert.assertEquals(expectedJSON, responseBody, true);
			LOGGER.info("Modal to Modal comparison is successful");
			return true;
		} catch (JSONException e) {
			LOGGER.error("Modal to modal comparison failed: " + e.toString());
		}
		return false;
	}

	public boolean performModaltoModalComparisonWithCustomization(String expectedJSON, String responseBody) {
		try {
			JSONAssert.assertEquals(expectedJSON, responseBody,
					new CustomComparator(JSONCompareMode.LENIENT, new Customization("timestamp", (o1, o2) -> true)));

			LOGGER.info("Modal to Modal comparison is successful");
			return true;
		} catch (JSONException e) {
			LOGGER.error("Modal to modal comparison failed: " + e.toString());
		}
		return false;

	}

	// provides test data for test TC03_POST_CreateNewBooking
	@DataProvider(name = "createNewBooking")
	public Object[][] getData() throws IOException, InvalidFormatException {
		Workbook workbook = WorkbookFactory.create(new File(FILE));
		Sheet sheet = workbook.getSheet(SHEET_NAME);
		Iterable<Row> rows = sheet::rowIterator;
		List<Map<String, String>> results = new ArrayList<>();
		boolean header = true;
		List<String> keys = null;
		for (Row row : rows) {
			List<String> values = getValuesInEachRow(row);
			if (header) {
				header = false;
				keys = values;
				continue;
			}
			results.add(transform(keys, values));
		}
		return asTwoDimensionalArray(results);
	}

	private static Object[][] asTwoDimensionalArray(List<Map<String, String>> interimResults) {
		Object[][] results = new Object[interimResults.size()][1];
		int index = 0;
		for (Map<String, String> interimResult : interimResults) {
			results[index++] = new Object[] { interimResult };
		}
		return results;
	}

	private static Map<String, String> transform(List<String> names, List<String> values) {
		Map<String, String> results = new HashMap<>();
		for (int i = 0; i < names.size(); i++) {
			String key = names.get(i);
			String value = values.get(i);
			results.put(key, value);
		}
		return results;
	}

	private static List<String> getValuesInEachRow(Row row) {
		List<String> data = new ArrayList<>();
		Iterable<Cell> columns = row::cellIterator;
		for (Cell column : columns) {
			data.add(dataFormatter.formatCellValue(column));
		}
		return data;
	}

	// provides test data for test TC08_POST_CreateBookingWithMissingData
	@DataProvider(name = "createIncorrectBooking")
	public Object[][] getDataTable() throws IOException {
		Workbook workbook = null;
		try {
			workbook = WorkbookFactory.create(new File(FILE_TC_8));
		} catch (EncryptedDocumentException | IOException e) {
			// TODO Auto-generated catch block
			workbook.close();
			e.printStackTrace();
		}
		Sheet sheet = workbook.getSheet(SHEET_NAME_TC_8);
		Iterable<Row> rows = sheet::rowIterator;
		List<Map<String, String>> results = new ArrayList<>();
		boolean header = true;
		List<String> keys = null;
		for (Row row : rows) {
			List<String> values = getValuesInEachRow_2(row);
			if (header) {
				header = false;
				keys = values;
				continue;
			}
			results.add(transform_2(keys, values));
		}
		workbook.close();
		return asTwoDimensionalArray_2(results);
	}

	private static Object[][] asTwoDimensionalArray_2(List<Map<String, String>> interimResults) {
		Object[][] results = new Object[interimResults.size()][1];
		int index = 0;
		for (Map<String, String> interimResult : interimResults) {
			results[index++] = new Object[] { interimResult };
		}
		return results;
	}

	private static Map<String, String> transform_2(List<String> names, List<String> values) {
		Map<String, String> results = new HashMap<>();
		for (int i = 0; i < names.size(); i++) {
			String key = names.get(i);
			String value = values.get(i);
			results.put(key, value);
		}
		return results;
	}

	private static List<String> getValuesInEachRow_2(Row row) {
		List<String> data = new ArrayList<>();
		Iterable<Cell> columns = row::cellIterator;
		for (Cell column : columns) {
			data.add(dataFormatter.formatCellValue(column));
		}
		return data;
	}
}