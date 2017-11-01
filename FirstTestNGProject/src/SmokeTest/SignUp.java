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
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;

public class SignUp {

	public String baseUrl = "https://dev.sentiovr.com/signup";
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
		htmlreporter = new ExtentHtmlReporter("C:\\Reports\\Signup1.html");
		report.attachReporter(htmlreporter);
		

		System.out.println("launching Chrome browser");

		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get(baseUrl);
		driver.manage().window().maximize();
		wait = new WebDriverWait(driver, 80);
	}

	@Test(dataProvider = "testdata")
	public void signupSuccess(String name, String email, String password,
			String confirm, String organization, String status, String condition)
			throws InterruptedException
			

	{
		test=report.createTest(condition);
		

		// Input name, Email,password, conform password and organization for
		// Sign up
		driver.findElement(By.id("name")).sendKeys(name);
		driver.findElement(By.id("email")).sendKeys(email);
		driver.findElement(By.id("password")).sendKeys(password);
		driver.findElement(By.id("confirm")).sendKeys(confirm);
		driver.findElement(By.id("organisation")).sendKeys(organization);

		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

		// Check Submit Button Status to validate input for Sign up
		WebElement SignupButton = driver
				.findElement(By
						.xpath("//*[@id='ui-login']/div/div[2]/div[2]/div/md-card/md-content/form/button"));

		if (SignupButton.isEnabled()) {
			System.out.print("\n SignUp Button is enabled. Take your action.");

			driver.findElement(By.xpath("//*[@id='ui-login']/div/div[2]/div[2]/div/md-card/md-content/form/button")).click();
			
			
			// Check Pop Up Message after Clicking Sign up Button
			

			Msg = waitAndFindElementByCssSelector(".md-toast-content").getText();
			driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

			// Conditions to verify whether signup is Successful or Unsuccessful
			// with error message

			if (Msg.equals("Sign up complete")) {

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

		// /////////////////////Else condition of Sign up Button
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

	private void waitForElementPresent(By cssSelector) {
		// TODO Auto-generated method stub
	}

	@DataProvider(name = "testdata")
	public Object[][] readExcel() throws BiffException, IOException {

		File src = new File("E:\\SentioVR\\Signup1.xls");

		Workbook w = Workbook.getWorkbook(src);
		Sheet s = w.getSheet("Sheet2");
		int rows = s.getRows();
		int columns = s.getColumns();

		String inputData[][] = new String[rows][columns];
		for (int i = 1; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				Cell c = s.getCell(j, i);
				inputData[i][j] = c.getContents();
				System.out.println(inputData[i][j]);
			}

		}

		return inputData;
	}

	/*
	 * @AfterMethod public void tearDown1() {
	 * 
	 * driver.quit(); }
	 */
}
