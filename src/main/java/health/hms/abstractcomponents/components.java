package health.hms.abstractcomponents;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class components {
	
	WebDriver driver;
	public components(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	public String front()
	{
		String path = "http://192.168.1.111:5005";
		return path;
	}

	public String back()
	{
		String path1 = "http://192.168.1.111:2999";
		return path1;
	}
	
}
