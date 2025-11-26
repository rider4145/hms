package health.hms.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import health.hms.abstractcomponents.components;

public class Appts extends components {

	WebDriver driver;
	public  Appts(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);	
		
	}
	
	@FindBy(xpath = "//a[@href='/OPD/patientlist']") WebElement patientlist;
	@FindBy(xpath = "//button[normalize-space()='Clear']") WebElement calendar;
	@FindBy(id = "«r75»-form-item") WebElement search;
	@FindBy(css = "button[value='on']") WebElement checkbox;
	@FindBy(css = "button[class='inline-flex items-center justify-center']") WebElement create_appt;
	
	@Test
	public void Appt1() 
	{
		scrollmenu("opd");
		patientlist.click();
		calendar.click();
		search.sendKeys("MRN000413");
		checkbox.click();
		create_appt.click();
		
	}
}
