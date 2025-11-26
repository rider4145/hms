package health.hms;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import health.hms.tests.Login;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Base {

	public WebDriver driver;
	public Login l ;
	public WebDriver initalize() throws IOException
	{
		// properties class is used to read the global properties from resources package
		Properties prop = new Properties();
		FileInputStream files1 = new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\resources\\GlobalData.properties");
		prop.load(files1);								// once the property is set, we can get the data from global properties
		
		String name = prop.getProperty("browser");		// browser is extracted from global properties using getproperty()
		
		if(name.equalsIgnoreCase("chrome"))
		{
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();									

		}
		else if(name.equalsIgnoreCase("firefox"))
		{
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();											
		}
		else if(name.equalsIgnoreCase("edge"))
		{
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();										
		}
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));		
		driver.manage().window().maximize();
		return driver;

	}
	
	// Launch the web app which is important to login
	@BeforeMethod(alwaysRun=true)
	public Login launchapp() throws IOException 
	{
		driver = initalize();
		l = new Login(driver);
		l.goTo();
		return l;
		
	}
	
//	@AfterMethod(alwaysRun=true)
	public void teardown()
	{
		driver.close();
	}
	
	//  Get the json and convert into string, send the json body to respective methods using HashMap list
	public List<HashMap<String, String>> getJsonData(String path) throws IOException
	{
		// read json to string and we need to specify in what encoding format we are trying to write the string
		// standard encoding format (StandardCharsets.UTF_8) should be passed, so that the code will be depricated
		String json = FileUtils.readFileToString(new File(System.getProperty("user.dir") +"\\src\\test\\java\\learning\\framework\\data\\Purchaseorder.json"),
				StandardCharsets.UTF_8);
		
		
		// Another way of passing the file path instead of hardcoding the data
		String json1 = FileUtils.readFileToString(new File(path),StandardCharsets.UTF_8);
		
		
		//convert string to hashmap	-> get the new dependencies jackson datbind
		ObjectMapper mapper = new ObjectMapper();
		// reading the data from the hashmap object
		List<HashMap<String,String >>  data = mapper.readValue(json, new TypeReference<List<HashMap<String, String>>>(){
			
		});
		// data list will be having the values of 2 hashmap stored in the json file  {map,map1}
		return data;
	}
	
	// to take the screen shot of failed testcases
//	public String screenshot(String testcase,WebDriver driver ) throws IOException								// getting the failed testcase name and stored in testcase variable
//	{
//		TakesScreenshot ts = (TakesScreenshot)driver;
//		File src = ts.getScreenshotAs(OutputType.FILE);
////		String shot = System.getProperty("user.dir")+ "//reports//" + testcase +".png";			// passing the failed testcase name in the testcase variable
//		String shot = framework.resources.extent_reports.path1+ "//" + testcase +".png";		// saving the photo in same report folder, using the same folder as report and saving the data
//		File file = new File(shot);						// convert the screenshot from string to file 			
//		FileUtils.copyFile(src,file);					// it is a package used to store the files or image
//		
//		return shot;									// sending back the path to respective failed testcase
//	}
	
}