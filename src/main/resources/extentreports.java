import java.text.SimpleDateFormat;
import java.util.Date;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class extentreports {
	
	public static ExtentReports reports;
	public static ExtentSparkReporter report;
	public static String path1;

		public static ExtentReports reports_config()					// by declaring as static, we can access this method without declaring the object
		{
			reports = new ExtentReports();
	        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());			// used to fetch the date and time
	        path1 = System.getProperty("user.dir")+"//reports//Execution" + timestamp;		// added time and date to differentiate the reports
	        String path = path1 +"//index.html";													  
			report = new ExtentSparkReporter(path);
			report.config().setReportName("Automation Reports - Web");
			report.config().setDocumentTitle("Reports");
			reports.attachReporter(report);
			reports.setSystemInfo("Tester", "Rahul N");
			reports.setSystemInfo("Role", "Software QA Engineer");
			reports.setSystemInfo("Project_Name", "HMS");
			return reports;
		}

}
