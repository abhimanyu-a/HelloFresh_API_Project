## HttpClient_APIAutomation_Project
The purpose of this project is to automate testing of the RESTful API's for a Booking application

## Overview
The API automation suite uses httclient to communicate with the RESTful services and TestNG framework to provide a platform to run the tests.
Httpclient is the native library used for API testing. It is faster compared to other open source frameworks since it has no wrappers.

Some of the key features of the API framework are:

- Modal to modal comparison
- User specific field level assertions
- Config file to control the API time out and other header properties
- Logging using Log4j
- Data driven tests from excel
- Test status code, status message and every field of the response body
- Multi-threading and parallel execution using concurrent dictionary

## Project Summary

The test cases **DriverTest.java** and and **NegativeTest.java** are placed in the src/test/java location inside the com.hellofresh.test package.
The POJO modals are present in the location src/main/java with the package com.hellofresh.modals. The config file is presnt in src/main/resources folder.
The http client managemenet and initialization is inside the src/main/java folder within the package com.hellofresh.client.
The logs are placed under the root folder in a folder logs.
The **TestNG** native reports can be accessed from root -> test-output-> HelloFresh_PositiveTest.html

## Test Cases 
For all of the below test cases the STATUS CODE, STATUS MESSAGE and Response body is compared. For instance, for the test case to create 
a booking, Once the POST API is called, the booking ID is fetched from the response and the same bookign ID is used to call the GET Booking by ID API. Finally, both the POST response and 
GET response modals are comapred.

- Test Case1: Get All Bookings 
- Test Case2: Get Booking by ID
- Test Case3: Create a Booking and verify whther booking is successful

**Extra Test Cases**
- Test Case4: Create duplciate booking for the same date
- Test Case5: Calling incorrect end-point
- Test Case5: Create booking when check-in date is greater than check-out date
- Test Case 6: CreateBookingWithMissingData

<img width="960" alt="sheetHF" src="https://user-images.githubusercontent.com/54126870/76314976-cb94dc80-62fd-11ea-9d8d-2b94327903ff.PNG">




# How to run the project
Option 1: TestNG
- Step 1: Go the root of the folder 
- Step2: Right click on the testng.xml file and select run as testng suite.

Option 1: Maven
- Step 1: Go the root of the folder 
- Step2: Open power shell or CMD window
- Step3: Run the following commands:
  
  ```
  mvn install
  mvn test
  ```
