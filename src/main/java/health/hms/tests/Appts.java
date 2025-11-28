package health.hms.tests;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.By;
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
	
	String today = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	
	@FindBy(xpath = "//a[@href='/OPD/patientlist']") WebElement patientlist;
	WebElement calendar = driver.findElement(By.xpath("//button[normalize-space()='" + today + "']"));
	@FindBy(id = "«r75»-form-item") WebElement search;
	@FindBy(css = "button[value='on']") WebElement checkbox;
	@FindBy(css = "button[class='inline-flex items-center justify-center']") WebElement create_appt;
		
	@Test
	public void Appt1() 
	{
		scrollmenu("OPD");
		patientlist.click();
		System.out.println(today);
		calendar.click();
		search.sendKeys("MRN000413");
		checkbox.click();
		create_appt.click();
		
	}
}
