package health.hms.tests;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
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
	String dates = LocalDate.now().format(DateTimeFormatter.ofPattern("d"));
	
	@FindBy(xpath = "//a[@href='/OPD/patientlist']") WebElement patientlist;
	@FindBy(xpath = "//input[@placeholder='Search by name/MRN']") WebElement search;
	@FindBy(css = "button[value='on']") WebElement checkbox;
	@FindBy(xpath = "//button[text()='Create Appointment']") WebElement create_appt;
	@FindBy(xpath = "//div[@class='flex items-center justify-start w-[70vw]']") WebElement opd_patients;
	
	@FindBy(xpath = "//*[text()= 'Cardiology']") WebElement spec;
//	driver.findElement(By.xpath("Cardiology"))
	@FindBy(xpath = "(//button[@role='combobox'])[2]") WebElement doc;
			
	public WebElement getCalendar(boolean fulldate)
	{
        // This is executed only when the method is called, and 'driver' is non-null
		if(fulldate)
		{
			return driver.findElement(By.xpath("//button[normalize-space()='" + today + "']"));
		}
		else
		{
			return driver.findElement(By.xpath("//button[text()='" + dates + "' and @aria-selected='true']"));
		}
    }
	
	@Test
	public void Appt1() 
	{
		scrollmenu("OPD");
		patientlist.click();
		System.out.println(today);
		getCalendar(true).click();
		getCalendar(false).click();
		opd_patients.click();
		search.click();
		search.sendKeys("MRN000413");
		checkbox.click();
		create_appt.click();	
	}
	
	@Test
	public void RC()
	{
		Appt1();
		spec.click();
		doc.click();
	}
}
