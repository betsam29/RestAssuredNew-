package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import util.ConfigReader;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class DeleteOneAcount extends GenerateAuthToken {
	
	
	String baseURI;
	String deleteOneAccountEndpoint;
	String updateAccountBodyFilePath;
	String readAllAccountsEndpoint;
	String firstAccountId;
	String readOneAccountsEndpoint;
	String deleteAccountId;

	public DeleteOneAcount() {
		baseURI = ConfigReader.getProperty("baseURI");
		deleteOneAccountEndpoint = ConfigReader.getProperty("deleteOneAccountEndpoint");
		updateAccountBodyFilePath = "src/main/java/data/updateAccountBody.json";
		readOneAccountsEndpoint = ConfigReader.getProperty("readOneAccountsEndpoint");
		deleteAccountId = "425";

	}
	
	@Test(priority=1)
	public void deleteOneAccount() {

		Response response;
 
				
	if(deleteOneAccountEndpoint != null) {
		
		response =			
	given()
		.baseUri(baseURI)
		.header("Content-Type", "application/json")
		.header("Authorization", "Bearer "+ bearerToken)
		.queryParam("account_Id", deleteAccountId)
		.log().all()
	.when()
		.delete(deleteOneAccountEndpoint)

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
			Assert.assertEquals(message, "Account deleted successfully.");
	}
	
	
	@Test (priority=2)
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
	
			
		int statusCode = response.getStatusCode();
		System.out.println("Status Code:" + statusCode);
		Assert.assertEquals(statusCode, 404, "Status Code are not maching");
		
			String actualResponseBody = response.getBody().asString();
			JsonPath jp = new JsonPath(actualResponseBody);

			String actualMassage = jp.getString("message");
			System.out.println("Actual Name:" + actualMassage);
					
			
			Assert.assertEquals(actualMassage, "No Record Found");
//			
//			
			

}
}
