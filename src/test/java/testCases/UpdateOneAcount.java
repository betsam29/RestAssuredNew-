package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import util.ConfigReader;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class UpdateOneAcount extends GenerateAuthToken {
	
	
	String baseURI;
	String updateOneAccountEndpoint;
	String createOneAccountEndpoint;
	String updateAccountBodyFilePath;
	String readAllAccountsEndpoint;
	String firstAccountId;
	String readOneAccountsEndpoint;

	public UpdateOneAcount() {
		baseURI = ConfigReader.getProperty("baseURI");
		updateOneAccountEndpoint = ConfigReader.getProperty("updateOneAccountsEndpoint");
		updateAccountBodyFilePath = "src/main/java/data/updateAccountBody.json";
		readOneAccountsEndpoint = ConfigReader.getProperty("readOneAccountsEndpoint");

	}
	
	@Test(priority=1)
	public void updateOneAccount() {

		Response response;
 
				
	if(updateOneAccountEndpoint != null) {
		
		response =			
	given()
		.baseUri(baseURI)
		.header("Content-Type", "application/json")
		.header("Authorization", "Bearer "+ bearerToken)
		.body(new File(updateAccountBodyFilePath))
		.log().all()
	.when()
		.put(updateOneAccountEndpoint)

	.then()
		.log().all()
		.extract().response();
	}else {
		throw new IllegalArgumentException("Missing updateOneAccountEndpoint ");
	}

		int statusCode = response.getStatusCode();
		System.out.println("Status Code:" + statusCode);
		Assert.assertEquals(statusCode, 200, "Status Code are not maching");

		String resresponsegetHeaderContentType = response.getHeader("content-Type");
		System.out.println("resresponsegetHeaderContentType");
		Assert.assertEquals(resresponsegetHeaderContentType, "application/json", "Status Content-Type are not maching");

		
			String responseBody = response.getBody().asString();
//			System.out.println("Response Body:" + responseBody);
			
			JsonPath jp = new JsonPath(responseBody);

			String message = jp.getString("message");
			System.out.println("Message:" + message);
			Assert.assertEquals(message, "Account created successfully.");
	}
	
	
	@Test (priority=2)
	public void readOneAccount() {
		
		File expectedResponseBody = new File(updateAccountBodyFilePath);
		JsonPath jp2 = new JsonPath(expectedResponseBody);
		
		String expectedAccountId = jp2.getString("account_id");
		System.out.println("Expected Name:" + expectedAccountId);
		
		String expectedAccountName = jp2.getString("account_name");
		System.out.println("Expected Name:" + expectedAccountName);

		String expectedAccountDescription = jp2.getString("description");
		System.out.println("Expected Description:" + expectedAccountDescription);
		
		String expectedAccountBalance = jp2.getString("balance");
		System.out.println("Expected Balance:" + expectedAccountBalance);
		
		String expectedAccountNumber = jp2.getString("account_number");
		System.out.println("Expected Number:" + expectedAccountNumber);
		
		String expectedAccountContactPerson = jp2.getString("account_person");
		System.out.println("Expected Person:" + expectedAccountContactPerson);
		
		

		Response response =

	given()
		.baseUri(baseURI)
		.header("Content-Type", "application/json")
		.auth().preemptive().basic("demo1@codefios.com", "abc123")
		.queryParam("account_id", firstAccountId)
//		.log().all()
	.when()
		.get(readOneAccountsEndpoint)
	.then()
//		.log().all()
		.extract().response();
	
			
			String actualResponseBody = response.getBody().asString();
//			System.out.println("Response Body:" + responseBody);
			

			JsonPath jp = new JsonPath(actualResponseBody);

			String actualAccountName = jp.getString("account_name");
			System.out.println("Actual Name:" + actualAccountName);
			
			
			String actualAccountDescription = jp.getString("description");
			System.out.println("Actual Description:" + actualAccountDescription);
			
			String actualAccountBalance = jp.getString("balance");
			System.out.println("Actual Balance:" + actualAccountBalance);
			
			String actualAccountNumber = jp.getString("account_number");
			System.out.println("Actual Number:" + actualAccountNumber);
			
			String actualAccountContactPerson = jp.getString("account_person");
			System.out.println("Actual Person:" + actualAccountContactPerson);
			
			
			
			
			
//			Assert.assertEquals(actualAccountName, expectedAccountName);
//			Assert.assertEquals(actualAccountDescription, expectedAccountDescription);
//			Assert.assertEquals(actualAccountBalance, expectedAccountBalance);
//			Assert.assertEquals(actualAccountNumber, expectedAccountNumber);
//			Assert.assertEquals(actualAccountContactPerson, expectedAccountContactPerson);
//			
			

}
}
