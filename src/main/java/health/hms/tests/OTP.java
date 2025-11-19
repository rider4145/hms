package health.hms.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import health.hms.abstractcomponents.components;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class OTP extends components{

	WebDriver driver;
	public  OTP(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);	
		
	}
	String path = front();
	String path1 = back();
	String mobile;
	String type ;
	public void OTPpage()
	{

	        String url = path + "/iamMasters/api/getStaffById?id=15";

	        Response response = RestAssured
	                .given()
	                .relaxedHTTPSValidation()  								// because you used --insecure in curl
	                .header("Authorization", "Bearer <Token>")
	                .header("Accept", "application/json")
	        .get(url);

	        response.asString();											// get full response
	        mobile = response.jsonPath().getString("mobile");				// Extract mobile number from JSON
	        type = response.jsonPath().getString("type");
	 }
	
	public void getOTP()
	{
	        String url = path1 + "/mobileApp/api/sendOtp";

	        String requestBody = "{ \"mobileNo\": \"" + mobile + "\", \"type\": \"" + type + "\" }";		// JSON body same as your curl --data-raw

	        Response response = RestAssured
	                .given()
	                .relaxedHTTPSValidation()   // same as curl --insecure
	                .header("Content-Type", "application/json")
	                .body(requestBody)
	                .post(url);
	       response.asString();												// get API response
	       String mobile = response.jsonPath().getString("mobileNo");		// Try extracting mobile number
	       String otp = response.jsonPath().getString("otp");				// Try extracting OTP if it exists in response
	    
	}

	
}
