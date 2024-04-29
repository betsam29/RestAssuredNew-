package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import util.ConfigReader;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class ReadAllAcounts extends GenerateAuthToken {
	
	
	String baseURI;
	String readAllAccountsEndpoint;
	String firstAccountId;

	public ReadAllAcounts() {
		baseURI = ConfigReader.getProperty("baseURI");
		readAllAccountsEndpoint = ConfigReader.getProperty("readAllAccountsEndpoint");

	}
	
	@Test
	public void readAllAccounts() {

		Response response =

	given()
		.baseUri(baseURI)
		.header("Content-Type", "application/json")
		.header("Authorization", "Bearer "+ generateAuthToken())
//		.log().all()
	.when()
		.get(readAllAccountsEndpoint)
	.then()
//		.log().all()
		.extract().response();
	

		int statusCode = response.getStatusCode();
		System.out.println("Status Code:" + statusCode);
		Assert.assertEquals(statusCode, 200, "Status Code are not maching");

		String resresponsegetHeaderContentType = response.getHeader("content-Type");
		System.out.println("resresponsegetHeaderContentType");
		Assert.assertEquals(resresponsegetHeaderContentType, "application/json", "Status Content-Type are not maching");

		long responseTimeInMiliseconds = response.getTimeIn(TimeUnit.MILLISECONDS);
		System.out.println("response Time in Miliseconds:" + responseTimeInMiliseconds);

		if (responseTimeInMiliseconds < 2000) {
			System.out.println("Rsponse Time is within range.");
		} else {
			System.out.println("Rsponse Time is out of range.");

		}
			
			String responseBody = response.getBody().asString();
//			System.out.println("Response Body:" + responseBody);

			JsonPath jp = new JsonPath(responseBody);

			firstAccountId = jp.getString("records[0].account_id");
			System.out.println("Frist Account ID:" + firstAccountId);
			
			if(firstAccountId !=null) {
			System.out.println("First account id is NOT null");
				
			} else {
			System.out.println("First account id is  NULL");
			}

	}

}
