package com.hellofresh.test;

import org.apache.http.HttpResponse;
import org.apache.log4j.Logger;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import com.google.gson.Gson;
import com.hellofresh.services.ServiceClass;
import com.hellofresh.utils.TestUtils;

public class BaseTest {

	protected HttpResponse response;
	protected TestUtils testUtil;
	protected ServiceClass service;
	public static Logger LOGGER;
	protected Gson gson = new Gson();

	public BaseTest() {
		LOGGER = Logger.getLogger("HelloFresh.APIProject");
		service = new ServiceClass();
		testUtil = new TestUtils();
	}

	public static Logger getLogger() {
		return LOGGER;
	}

	@BeforeTest
	public void setup() {
		LOGGER.info("Starting HelloFresh API tests");
	}

	@BeforeMethod
	public void beforeMethod(ITestResult result) {
		LOGGER.info("Started Test: " + result.getMethod().getMethodName());
	}

	@AfterMethod
	public void afterMethod(ITestResult result) {
		LOGGER.info("Completed Test: " + result.getMethod().getMethodName());
	}

	@AfterTest
	public void teardown() {
		LOGGER.info("Test execution of HelloFresh API tests is complete");
	}
}
