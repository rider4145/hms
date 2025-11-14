package health.hms.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import health.hms.abstractcomponents.components;

public class Login extends components {
	
	WebDriver driver;
	public Login(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);	
		
	}
	
	@FindBy(xpath = "//input[@placeholder='Enter your email']") WebElement username;
	@FindBy(xpath = "//input[@type='password' and @placeholder='Enter your password']") WebElement pass;
	@FindBy(css= "button[type='submit']") WebElement login;
	
	
	public void goTo()
	{
		String path = front();
		driver.get(path + "/login");
	}
	public OTP login(String usermail,String passw)
	{
		username.sendKeys(usermail);
		pass.sendKeys(passw);
		login.click();
		OTP a = new OTP(driver);
		return a;
	}

}
