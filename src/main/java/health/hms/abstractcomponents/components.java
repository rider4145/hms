package health.hms.abstractcomponents;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class components {
	
	WebDriver driver;
	WebDriverWait wait;
	public components(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	}
	
//	@FindBy(css = "ul.flex.flex-nowrap.w-full") WebElement menu;
//	@FindBy(css = "a.block.w-full, ul.flex.flex-nowrap.w-full li.menu-item > button > span") List<WebElement> menuItems;
//	@FindBy(css = "div.overflow-x-auto") WebElement scroll;
	
	@FindBy(css = "ul.flex.flex-nowrap.w-full li.menu-item > button > span") List<WebElement> menuItems;
	
	public String front()
	{
		return "http://192.168.1.111:5005";
	}

	public String back()
	{
		return "http://192.168.1.111:2999";
	}
	public void visiElement(By findBy)
	{
		wait.until(ExpectedConditions.visibilityOfElementLocated(findBy));
	}
	public void elemclick(WebElement ele)
	{
         wait.until(ExpectedConditions.elementToBeClickable(ele));
	}
	public void pageload(int time)
	{
		WebDriverWait pageLoadWait = new WebDriverWait(driver, Duration.ofSeconds(time));    
		pageLoadWait.until((ExpectedCondition<Boolean>) wd ->((JavascriptExecutor) wd)
        		.executeScript("return document.readyState").equals("complete"));			// Wait until document.readyState is 'complete'
	}
	public void scrollmenu(String item)
	{
		pageload(10);
		// iterates each menu item and compare with the item provided using stream 
		 WebElement targetItem = menuItems.stream().filter(el -> el.getText().equalsIgnoreCase(item)).findFirst()
				 .orElseThrow(() -> new RuntimeException("Menu item not found: " + item));
		 
		 // Scroll horizontally inside the menu container
//		 JavascriptExecutor js = (JavascriptExecutor) driver;
//		    js.executeScript(
//		        "arguments[0].scrollLeft = arguments[1].offsetLeft - arguments[0].offsetLeft;",
//		        scroll, targetItem
//		    );
		    
		    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", targetItem);
                System.out.println(targetItem);
                elemclick(targetItem);					// Wait until the item is found
                targetItem.click();
    }       

}
