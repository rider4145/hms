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
	
	@FindBy(css = "ul.flex.flex-nowrap.w-full") WebElement menu;
	@FindBy(css = "a.block.w-full, ul.flex.flex-nowrap.w-full li.menu-item > button > span") List<WebElement> menuItems;
//	@FindBy(css = "ul.flex.flex-nowrap.w-full li.menu-item > button > span") List<WebElement> menuItems; - only 20 menus
	
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
		try {
			pageload(10);
			wait.until(ExpectedConditions.visibilityOfAllElements(menuItems));			// Wait for menu items to be visible
			
			// Find the target menu item
			WebElement targetItem = menuItems.stream().filter(el -> el.getText().trim().equalsIgnoreCase(item.trim())).findFirst()
					.orElseThrow(() -> new RuntimeException("Menu item not found: " + item));
						
			// Scroll horizontally in the container to make the item visible
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript(
				"arguments[0].scrollLeft = arguments[1].offsetLeft - arguments[0].offsetWidth / 2;",menu, targetItem);
			
			Thread.sleep(500); 			// Wait for scroll to complete
			
			// Ensure element is visible
			js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center', inline: 'center'});", targetItem);
			
			Thread.sleep(300);
			elemclick(targetItem);			// Wait for click ability
			targetItem.click();	
			System.out.println("Clicked menu item: " + item);
		
		} 
		catch (Exception e){
			System.err.println("Error in scrollMenuHorizontally: " + e.getMessage());
			throw new RuntimeException("Failed to click menu item: " + item, e);
		}
	}
}
