package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import util.ConfigReader;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class CreateOneAcountAsMap extends GenerateAuthToken {
	
	
	String baseURI;
	String createOneAccountEndpoint;
	String createAccountBodyFilePath;
	String readAllAccountsEndpoint;
	String firstAccountId;
	String readOneAccountsEndpoint;
	Map<String,String> createPayloadMap;

	public CreateOneAcountAsMap() {
		baseURI = ConfigReader.getProperty("baseURI");
		createOneAccountEndpoint = ConfigReader.getProperty("createOneAccountEndpoint");
		createAccountBodyFilePath = "src/main/java/data/createAccountBody.json";
		
		createPayloadMap = new HashMap<String,String>();
		
		readAllAccountsEndpoint = ConfigReader.getProperty("readAllAccountsEndpoint");
		readOneAccountsEndpoint = ConfigReader.getProperty("readOneAccountsEndpoint");

	}
	
	
	public Map<String,String> createAccountBodyMap(){
		createPayloadMap.putIfAbsent("account_name", "BS Techfios account 111");
		createPayloadMap.putIfAbsent("description", "Test description 10");
		createPayloadMap.putIfAbsent("balance", "100.22");
		createPayloadMap.putIfAbsent("account_number", "67");
		createPayloadMap.putIfAbsent("contact_person", "Betty");
		return createPayloadMap;
		
	}
	
	@Test(priority=1)
	public void createOneAccount() {

		Response response =

	given()
		.baseUri(baseURI)
		.header("Content-Type", "application/json")
		.header("Authorization", "Bearer "+ bearerToken)
		.body(createAccountBodyMap())
		.log().all()
	.when()
		.post(createOneAccountEndpoint)

	.then()
		.log().all()
		.extract().response();
	

		int statusCode = response.getStatusCode();
		System.out.println("Status Code:" + statusCode);
		Assert.assertEquals(statusCode, 201, "Status Code are not maching");

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
	public void readAllAccounts() {

		Response response =

	given()
		.baseUri(baseURI)
		.header("Content-Type", "application/json")
		.header("Authorization", "Bearer "+ bearerToken)
//		.log().all()
	.when()
		.get(readAllAccountsEndpoint)
	.then()
//		.log().all()
		.extract().response();
	
			
			String responseBody = response.getBody().asString();
//			System.out.println("Response Body:" + responseBody);

			JsonPath jp = new JsonPath(responseBody);

			firstAccountId = jp.getString("records[0].account_id");
			System.out.println("Frist Account ID:" + firstAccountId);	

	}
	
	
	@Test (priority=3)
	public void readOneAccount() {

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
			
			
			
//			File expectedResponseBody = new File(createAccountBodyFilePath);
//			JsonPath jp2 = new JsonPath(expectedResponseBody);
			
			String expectedAccountName = createAccountBodyMap().get("account_name");
			System.out.println("Expected Name:" + expectedAccountName);

			
			String expectedAccountDescription = createAccountBodyMap().get("description");
			System.out.println("Expected Description:" + expectedAccountDescription);
			
			String expectedAccountBalance = createAccountBodyMap().get("balance");
			System.out.println("Expected Balance:" + expectedAccountBalance);
			
			String expectedAccountNumber = createAccountBodyMap().get("account_number");
			System.out.println("Expected Number:" + expectedAccountNumber);
			
			String expectedAccountContactPerson = createAccountBodyMap().get("account_person");
			System.out.println("Expected Person:" + expectedAccountContactPerson);
			
			Assert.assertEquals(actualAccountName, expectedAccountName);
			Assert.assertEquals(actualAccountDescription, expectedAccountDescription);
			Assert.assertEquals(actualAccountBalance, expectedAccountBalance);
			Assert.assertEquals(actualAccountNumber, expectedAccountNumber);
			Assert.assertEquals(actualAccountContactPerson, expectedAccountContactPerson);
			
			

}
}
