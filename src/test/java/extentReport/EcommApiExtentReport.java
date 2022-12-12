package extentReport;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class EcommApiExtentReport {
	
	static ExtentReports extent;

	public static ExtentReports getEcommApiReport() {
		
		String path = System.getProperty("user.dir") + "\\reports\\BookingReport.html";

		ExtentSparkReporter spark = new ExtentSparkReporter(path);
		spark.config().setReportName("Booking Api Automation");
		spark.config().setDocumentTitle("Booking Test Result");
		spark.config().setTheme(Theme.DARK);

		extent = new ExtentReports();
		extent.attachReporter(spark);
		extent.setSystemInfo("QA", "Tanveer");
		return extent;

	}


}
