package com.orangehrm.utilities;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class APIUtility {

	public static Response sendGetRequest(String endPoint) {
		return RestAssured.get(endPoint);
	}
	
	public static Response sendPostRequest(String endPoint, String payLoad) {
		return RestAssured.given().header("Content-Type", "application/json").body(payLoad).post();
	}
	
	public static boolean validStatusCode(Response response, int expectedStatusCode) {
		return response.getStatusCode() == expectedStatusCode;
	}
	
	public static String getJsonValue(Response response, String value) {
		return response.jsonPath().getString(value);
	}
	
}
