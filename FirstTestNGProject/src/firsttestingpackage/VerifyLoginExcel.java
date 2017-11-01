package firsttestingpackage;
//import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Driver;
import java.util.ArrayList;
import java.util.Set;
//import java.util.ArrayList;
//import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.*;
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

public class VerifyLoginExcel {
	 public String baseUrl = "https://sentiovr.com/login";
	    String driverPath = "C:\\chromedriver.exe";
	    public String Msg;
	    public WebDriver driver; 
	    public String Error_Message;
	    ExtentReports report;
	    ExtentTest    logger;
	    
	   

@DataProvider(name = "testdata")
public Object [] [] readExcel() throws BiffException, IOException  {

File src=new File("E:\\SentioVR\\credential.xls");

Workbook w = Workbook.getWorkbook(src);
Sheet s = w.getSheet("Sheet1");
int rows = s.getRows();
int columns = s.getColumns();

String inputData [] [] = new String [rows] [columns];
for (int i=0; i<rows; i++){
 for (int j=0; j<columns; j++){
 Cell c = s.getCell(j, i);
 inputData [i][j] = c.getContents();
 System.out.println(inputData[i][j]);
}
}
return inputData;
}

  
	    @Test(dataProvider="testdata")
	    public void Login(String email,String password) throws InterruptedException 
	    {
	    	report=new ExtentReports("C:\\Reports\\TestLogin.html");
	    	
	    	System.out.println("launching Chrome browser"); 
	    	   System.setProperty("webdriver.chrome.driver","C:\\chromedriver.exe");
	    	   driver = new ChromeDriver();
	    	   driver.manage().window().maximize();
	    	   driver.get(baseUrl);
	    	   driver.manage().timeouts().implicitlyWait(40,TimeUnit.SECONDS);
	    	  
	    	   logger=report.startTest(email);
		    	logger.log(LogStatus.INFO,"Browser started");
	    	   
	    	  // Input Email and Password for Login
	    	  driver.findElement(By.name("email")).sendKeys(email);
	    	  driver.findElement(By.name("password")).sendKeys(password);
	    	  	  
	    	  Thread.sleep(5000);
	    	  	    	  
	    	  // Check Submit Button Status to validate input for Login  
	    	 WebElement LoginButton = driver.findElement(By.xpath("//button[@type='submit']"));
	    	  
	    	  if(LoginButton.isEnabled())
	    	  {
	    	   System.out.print("\n Login Button is enabled. Take your action.");
	    	  // driver.manage().timeouts().implicitlyWait(40,TimeUnit.SECONDS);
   	  
	    	  driver.findElement(By.xpath("//button[@type='submit']")).click();	    			 
	    	  driver.manage().timeouts().implicitlyWait(40,TimeUnit.SECONDS);
			  
	    	  // Check Pop Up Message after Clicking Login Button
	    	  Msg = driver.findElement(By.cssSelector("div.md-toast-content")).getText();
	    	  System.out.println(Msg);
	    	  
	    	 // Conditions to verify whether Login is Successful or Unsuccessful with error message
	  	  
	    	 if (Msg.equals("Login successful")){
	    		 logger.log(LogStatus.PASS, Msg);
	    	  }
	          
	    	  else if (Msg.equals("Please check your email and password. Try again")){
	    		  logger.log(LogStatus.FAIL, Msg ); 
	    		  }
	    	       
	    	  else 
	    	    { 
	    		  logger.log(LogStatus.FAIL, Msg);
	    	    }
	  	    	  
	    	  }
	    	  
	    	  //Else condition of LoginButton Status
	    	  else
	    	  {
	    		  logger.log(LogStatus.FAIL, "Invalid User atamped Login Button is disabled");
	    	  }
	    }
	    
	      	  
	   @AfterMethod
	   
	   public void tearDown()
	   {
	   	  
	  driver.quit();
	   }  	     


	 /*   @DataProvider(name="testdata")
	    public Object[][] TestDataFeed() 
	    	
	    	
	    	
	    	
	    	
	    ExcelDataConfig config=new ExcelDataConfig("E:\\Selenium Testing\\Eclipse\\eclipse-java-oxygen-R-win32-x86_64\\eclipse\\FirstTestNGProject\\TestFolder\\credential.xls");	
	    	int rows=config.getRowCount(0);
	    	
	    // Create object array with 2 rows and 2 column- first parameter is row and second is //column
	    Object[][] data=new Object[rows][2];
	     for(int i=0;i<rows;i++)
	     {
	    	data[i][0]=config.getData(0, i, 0);
	    	data[i][1]=config.getData(0, i, 1);	 
	     }
	    
// return arrayobject to testscript
	    return data;
	    }*/
	    
}
 

	    