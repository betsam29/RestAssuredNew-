package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import util.ConfigReader;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class GenerateAuthToken {
	String baseURI;
	String authEndPoint;
	String authBodyFilePath;
	String readAllAccountsEndpoint;
	String GenerateAuthToken;
	
	
	public static String bearerToken;

	public GenerateAuthToken() {
		baseURI = ConfigReader.getProperty("baseURI");
		GenerateAuthToken = ConfigReader.getProperty("GenerateAuthToken");
		authBodyFilePath = "src\\main\\java\\data\\authBody.json";
		generateAuthToken();

	}

	
	public String generateAuthToken() {

		/*
		 * Rest Assured is a set of library witch will help us to automate the rest
		 * API!!!
		 * 
		 * 
		 * given: all input details ->(baseURI,Header/s,Authorization,Payload/Body,QueryParameters) 
		 * when: submitapi requests-> HttpMethod(Endpoint/Resource) 
		 * then: validate response ->(status code, Headers, responseTime, Payload/Body)
		 * 
		 * baseURI=https://qa.codefios.com/api 
		 * https://qa.codefios.com/api /user/login
		 * Headers: 
		 * "Content-Type" = "application/json"
			payload/body= 
			{ 
				"username":"admin",
				"password": "123456" 
			} 
			statusCode=201 
			response= 
			{ 
			"access_token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOiIyIiwidXNlcm5hbWUiOiJhZG1pbiIsIkFQSV9USU1FIjoxNzA1MjQ1NjYyfQ.qUeFzu-fkB4ZkYhQNqV7cmNCahLYQWLZcQEa3asVMBU",
		 * "status": true, 
		 * "message": "Login success!", 
		 * "token_expire_time": 86400 }
		 */

		Response response =

	given()
		.baseUri(baseURI)
		.header("Content-Type", "application/json")
		.body(new File(authBodyFilePath))
		.log().all()
	.when()
		.post(GenerateAuthToken)
	.then()
		.log().all()
		.extract().response();
	

		int statusCode = response.getStatusCode();
		System.out.println("Status Code:" + statusCode);
		Assert.assertEquals(statusCode, 201, "Status Code are not maching");

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
		System.out.println("Response Body:" + responseBody);

		JsonPath jp = new JsonPath(responseBody);

		bearerToken = jp.getString("access_token");
		System.out.println("Bearer Token:" + bearerToken);
		return bearerToken;

	}

}
