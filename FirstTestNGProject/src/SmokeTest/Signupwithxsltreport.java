package SmokeTest;

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class Signupwithxsltreport

{
	 public String baseUrl = "https://app.sentiovr.com/signup";
	    String driverPath = "C:\\chromedriver.exe";
	    public String Msg;
	    public WebDriver driver; 
	    public String Error_Message;
	    public String orgname;
	    
	    ExtentReports report;
	    ExtentTest    logger;
	    	    
	    @BeforeTest
	      public void launchBrowser() 
	      
	    {    	 
	    	report=new ExtentReports("C:\\Reports\\Signup_new1.html");
	    	
	    	
	    	System.out.println("launching Chrome browser"); 
	    	
	    	
	    	System.setProperty("webdriver.chrome.driver","C:\\chromedriver.exe");
	    	  driver = new ChromeDriver();
	    	  driver.get(baseUrl);
	    	  driver.manage().window().maximize();
	    	  driver.manage().timeouts().implicitlyWait(40,TimeUnit.SECONDS);
	    }
	    
	    @Test(dataProvider="testdata") 
	   
public void signup(String name,String email,String password,String confirm,String organization,String Isvalid,String Value) throws InterruptedException 

{
	    	logger=report.startTest(Value,Isvalid);
	    	logger.log(LogStatus.INFO,"Browser started");
	    	
	    // Input name, Email,password, conform password and organization for Sign up
	    	  driver.findElement(By.id("name")).sendKeys(name);
	    	  driver.findElement(By.id("email")).sendKeys(email);
	    	  driver.findElement(By.id("password")).sendKeys(password);
	    	  driver.findElement(By.id("confirm")).sendKeys(confirm);
	    	  driver.findElement(By.id("organisation")).sendKeys(organization);	  
	    	    
	    	  //Thread.sleep(5000);
	    	  	    	  
	    	  // Check Submit Button Status to validate input for Sign up  
    WebElement SignupButton = driver.findElement(By.xpath("//*[@id=\"ui-login\"]/div/div/md-card/md-content/form/button"));
	    	  
	  if(SignupButton.isEnabled())
        {
		  System.out.print("\n SignUp Button is enabled. Take your action.");
	    	  	  
	    	  driver.findElement(By.xpath("//*[@id=\"ui-login\"]/div/div/md-card/md-content/form/button")).click();	    			 
	    	  driver.manage().timeouts().implicitlyWait(60,TimeUnit.SECONDS);
			  
	    	  // Check Pop Up Message after Clicking Sign up Button
	    	  
	    	  Msg = driver.findElement(By.cssSelector("div.md-toast-content")).getText();
	    	  driver.manage().timeouts().implicitlyWait(60,TimeUnit.SECONDS);	    	  
	    	
	    	  // Conditions to verify whether signup is Successful or Unsuccessful with error message
	  	  
	    	 if (Msg.equals("Sign up complete"))
	    	 {
	    		 
	    		    		 
	    		 if(Isvalid.equals("Pass"))
	    		 {
	    		 logger.log(LogStatus.PASS, Msg);
	    		 }
	    		 
	    		 else
	    		 {
	    			 logger.log(LogStatus.FAIL, Msg); 
	    		 }
	    		 
	    	 }
	        
	       else 
        	 { 
	    		  
	   	        if(Isvalid.equals("Fail"))
		     	 {
		           logger.log(LogStatus.PASS, Msg);
		         }
		    		 
		        else
		         {
		    	   logger.log(LogStatus.FAIL, Msg); 
		         }
		    		 
	    	}
	  	    	  
	   }
	    	  
///////////////////////Else condition of Sign up Button Status///////////////////////
  else
    {
	    	  
	 String msg = driver.findElement(By.cssSelector("span.ng-scope")).getText();
	 if(Isvalid.equals("Fail"))
		{
	    	logger.log(LogStatus.PASS,msg);
		}
		    		 
	  else
	   {
		  logger.log(LogStatus.FAIL, msg); 
		}
	    		  
     }
	       	  driver.get(baseUrl);
	    	  driver.manage().timeouts().implicitlyWait(60,TimeUnit.SECONDS);
	    	 
	    	 report.endTest(logger);
	    	 report.flush();
	 
  }
	      	  
	   	   
	
	   /*private void waitForElementPresent(By cssSelector) {
			// TODO Auto-generated method stub
			
		}*/

	@DataProvider(name = "testdata")
    public Object [] [] readExcel() throws BiffException, IOException 
	   {

	      File src=new File("E:\\SentioVR\\Signup.xls");

	        Workbook w = Workbook.getWorkbook(src);
	        Sheet s = w.getSheet("Sheet1");
	        int rows = s.getRows();
	        int columns = s.getColumns();

	     String inputData [] [] = new String [rows] [columns];
	     for (int i=1; i<rows; i++)
	   {
	     for (int j=0; j<columns; j++)
	     {
	         Cell c = s.getCell(j, i);
	         inputData [i][j] = c.getContents();
	         System.out.println(inputData[i][j]);
	     }
	     
	   }
	     
	   return inputData;
	   }
	   
/*	   
@AfterMethod
	   	   public void tearDown1()
	   {
	   	  
	  driver.quit();
	   }  */
}

