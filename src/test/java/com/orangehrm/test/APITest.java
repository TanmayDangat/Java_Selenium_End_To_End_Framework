package com.orangehrm.test;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.orangehrm.utilities.APIUtility;
import com.orangehrm.utilities.ExtentManager;
import com.orangehrm.utilities.RetryAnalyzer;

import io.restassured.response.Response;

public class APITest {
	
	@Test()
	public void verifyGetUserAPI() {
		
		SoftAssert softAssert = new SoftAssert();
		
		String endPoint = "https://jsonplaceholder.typicode.com/users/1";
		ExtentManager.logStep("API Endpoint " + endPoint);
		
		ExtentManager.logStep("Sending GET request to the API");
		Response response = APIUtility.sendGetRequest(endPoint);
		
		ExtentManager.logStep("Validating API Response status code");
		boolean isStatusCodeValid = APIUtility.validStatusCode(response, 200);
		
		softAssert.assertTrue(isStatusCodeValid, "Status code is not Expected");
		
		if (isStatusCodeValid) {
			ExtentManager.logStepValidationAPI("Status Code Validation passed! ");
			
		} else {
			ExtentManager.logFailureAPI("Status code Validation Failed");
		}
		
		ExtentManager.logStep("Validating response body for username");
		String userName = APIUtility.getJsonValue(response, "username");
		boolean isUserNameValid = "Bret".equals(userName);
		softAssert.assertTrue(isUserNameValid, "Username is not valid");
		
		if (isUserNameValid) {
			ExtentManager.logStepValidationAPI("Username Validation passed! ");
		} else {
			ExtentManager.logFailureAPI("Username Validation Failed");
		}
		
		ExtentManager.logStep("Validating response body for email");
		String userEmail = APIUtility.getJsonValue(response, "email");
		boolean isEmailValid = "Sincere@april.biz".equals(userEmail);
		softAssert.assertTrue(isEmailValid, "Email is not valid");
		
		if (isEmailValid) {
			ExtentManager.logStepValidationAPI("Email Validation passed! ");
		} else {
			ExtentManager.logFailureAPI("Email Validation Failed");
		}
		
		softAssert.assertAll();
	}
	
}
