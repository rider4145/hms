package health.hms.tests;

import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v142.network.Network;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

import java.util.Optional;

import health.hms.abstractcomponents.components;

public class OTP extends components {

	private WebDriver driver;
    private ChromeDriver chromeDriver; // class-level ChromeDriver
    private DevTools devTools;         // class-level DevTools
	public OTP(WebDriver driver) {
		super(driver);
		this.driver = driver;
		this.chromeDriver = (ChromeDriver) driver; // cast once here
		PageFactory.initElements(driver, this);		
	}
	
    String jsonResponse;
    String otp;
    @FindBy(xpath = "//input[@placeholder='Enter 4-digit OTP']") WebElement Otp;
    @FindBy(css = "button[type='submit']") WebElement submit;
    
    public void NetworkInterceptor() 
    {
        devTools = chromeDriver.getDevTools();
        devTools.createSession();
    }	

    public void enableNetworkLogging() 
    {
    
    	NetworkInterceptor();
        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty()));

        devTools.addListener(Network.responseReceived(), response -> {
            String url = response.getResponse().getUrl();

            // Filter API calls you want to capture
            if (url.contains("/sendOtp")) {
                System.out.println("API URL is working");

                try {
                	jsonResponse = devTools.send(Network.getResponseBody(response.getRequestId())).getBody();
//                    System.out.println("Response Body: " + jsonResponse);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
    public Appts OTPpage()
    {
    	enableNetworkLogging();
        // ---------- EXPLICIT WAIT FOR API RESPONSE ----------
        WebDriverWait wait = new WebDriverWait(chromeDriver, Duration.ofSeconds(10));
        wait.until((ExpectedCondition<Boolean>) driver -> jsonResponse != null);
        System.out.println("✅ API response received: " + jsonResponse);
        
    	try {
    	      JSONObject obj = new JSONObject(jsonResponse);			// converting the response into json object

    	   // Check root level
    	   if (obj.has("otp")) {
               otp = String.valueOf(obj.get("otp"));  // Convert to String
    	   }

           // Check nested 'data' object
           if (obj.has("data")) {
           JSONObject dataObj = obj.getJSONObject("data");
    	   if (dataObj.has("otp")) {
    		   otp = String.valueOf(dataObj.get("otp"));  // Convert numeric OTP to String
    	   }
    	   System.out.println(otp);
    	 }
           Otp.sendKeys(otp);
           submit.click();
           
    	 // Could add recursive search if needed
    	 } catch (Exception e) 
    	{	
    		 e.printStackTrace();	
    	}		
    	Appts ap = new Appts(driver);
    	return ap;
    }
}
