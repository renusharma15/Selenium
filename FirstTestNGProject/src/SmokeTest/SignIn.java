package SmokeTest;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.gargoylesoftware.htmlunit.ElementNotFoundException;

public class SignIn {

	public String baseUrl = "https://dev.sentiovr.com/login";
	String driverPath = "C:\\chromedriver.exe";
	public String Msg;
	public WebDriver driver;
	public String Error_Message;
	public String orgname;
	WebDriverWait wait;
	
	ExtentReports report;
	ExtentHtmlReporter htmlreporter;
	ExtentTest test;

	@BeforeTest
	public void launchBrowser() throws InterruptedException

	{
		report=new ExtentReports();
		htmlreporter = new ExtentHtmlReporter("C:\\Reports\\Login.html");
		report.attachReporter(htmlreporter);
		    
	    
		System.out.println("launching Chrome browser");

		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get(baseUrl);
		driver.manage().window().maximize();
		
		wait = new WebDriverWait(driver, 80);
		
	}

	@Test(dataProvider = "testdata")
	public void LoginSuccess(String email,String password, String status, String condition)
			throws InterruptedException
			

	{
		test=report.createTest(condition);
		
		
		driver.findElement(By.id("email")).sendKeys(email);
		driver.findElement(By.id("password")).sendKeys(password);

		

		// Check Submit Button Status to validate input for Sign up
		WebElement LoginButton = driver
				.findElement(By
						.xpath("//button[@type='submit']"));

		if (LoginButton.isEnabled()) {
			System.out.print("\n Signin Button is enabled. Take your action.");

			driver.findElement(
					By.xpath("//button[@type='submit']"))
					.click();

			// Check Pop Up Message after Clicking Sign up Button
			

			Msg = waitAndFindElementByCssSelector(".md-toast-content").getText();
			driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);

			// Conditions to verify whether signIn is Successful or Unsuccessful
			// with error message

			if (Msg.equals("Login successful")) {

				if (status.equals("Pass")) {
					test.pass(Msg);
				}

				else {
					test.fail(Msg);
				}

			}

			else {

				if (status.equals("Fail")) {
					test.pass(Msg);
				}

				else {
					test.fail(Msg);
				
				}

			}

		}

		// /////////////////////Else condition of Sign in Button
		// Status///////////////////////
		else {

			String msg = driver.findElement(By.cssSelector("span.ng-scope"))
					.getText();
			if (status.equals("Fail")) {
				test.pass(msg);
			
			}

			else {
				test.fail(msg);
			
			}

		}
		driver.get(baseUrl);
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

		
		report.flush();
		
	}

	private WebElement waitAndFindElementByCssSelector(String locator) {
		// TODO Auto-generated method stub
		
		try {
			waitForElementVisible(locator);
			return driver.findElement(By.cssSelector(locator));
			
		} catch (ElementNotFoundException e) {
			// TODO: handle exception
		}
		return null;
		
	}
	
	
	public void waitForElementVisible(String locator) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(locator)));
	}

	

	
	@DataProvider(name = "testdata")
	public Object[][] readExcel() throws BiffException, IOException {

		File src = new File("E:\\SentioVR\\credential.xls");

		Workbook w = Workbook.getWorkbook(src);
		Sheet s = w.getSheet("Sheet1");
		int rows = s.getRows();
		int columns = s.getColumns();

		String inputData[][] = new String[rows][columns];
		for (int i = 1; i <rows; i++) {
			for (int j = 0; j < columns; j++) {
				Cell c = s.getCell(j, i);
				inputData[i][j] = c.getContents();
				System.out.println(inputData[i][j]);
			}

		}

		return inputData;
	}

	/*@AfterMethod
	public void setTestResult(ITestResult result)
	{
		if(result.getStatus() == ITestResult.FAILURE) {
			test.fail(result.getTestName());
			test.fail(result.getThrowable());
		}
		else if(result.getStatus() == ITestResult.SUCCESS) {
			test.pass(result.getTestName());
		}
		else if(result.getStatus() == ITestResult.SKIP) {
			test.skip("Test Case:" + result.getTestName() + "has been skipped");
		}
	}
	/*
	 * @AfterMethod public void tearDown1() {
	 * 
	 * driver.quit(); }
	 */
}
