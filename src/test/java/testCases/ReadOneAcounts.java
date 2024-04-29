package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import util.ConfigReader;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class ReadOneAcounts extends GenerateAuthToken {
	
	
	String baseURI;
	String readOneAccountsEndpoint;
	String firstAccountId;

	public ReadOneAcounts() {
		baseURI = ConfigReader.getProperty("baseURI");
		readOneAccountsEndpoint = ConfigReader.getProperty("readOneAccountsEndpoint");

	}
	
	@Test
	public void readOneAccounts() {
		
//		given: all input details -> (baseURI,Header/s,Authorization (basic auth/Bearer token),Payload/Body,QueryParameters)
//		when:  submit api requests-> HttpMethod(Endpoint/Resource)
//		then:  validate response -> (status code, Headers, responseTime, Payload/Body)

		Response response =

	given()
		.baseUri(baseURI)
		.header("Content-Type", "application/json")
		.auth().preemptive().basic("demo1@codefios.com", "abc123")
		.queryParam("account_id", "419")
//		.log().all()
	.when()
		.get(readOneAccountsEndpoint)
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
			
			
//			 	"account_id": "419",
//			    "account_name": "golam sarwar",
//			    "account_number": "1000100101",
//			    "description": "saving account",
//			    "balance": "1200.00",
//			    "contact_person": "kaka"
			
			

			JsonPath jp = new JsonPath(responseBody);

			String accountId = jp.getString("account_id");
			System.out.println("Account ID:" + accountId);
			Assert.assertEquals(accountId, "419");
			
			String accountName = jp.getString("account_name");
			System.out.println("Account Name:" + accountName);
			Assert.assertEquals(accountName, "golam sarwar");
			
			String accountNumber = jp.getString("account_number");
			System.out.println("Account Number:" + accountNumber);
			Assert.assertEquals(accountNumber, "1000100101");
		
//			String accountDescription = jp.getString("account_description");
//			System.out.println("Account Description:" + accountDescription);
//			Assert.assertEquals(accountDescription, "saving account");
//			
//			String accountBalance = jp.getString("account_balance");
//			System.out.println("Account Balance:" + accountBalance);
//			Assert.assertEquals(accountBalance, "1200.00");
//			
//			String accountContactPerson = jp.getString("account_person");
//			System.out.println("Account Person:" + accountContactPerson);
//			Assert.assertEquals(accountContactPerson, "kaka");
			

	}

}
