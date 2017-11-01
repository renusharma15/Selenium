package firsttestingpackage;

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
//import org.testng.annotations.AfterMethod;
//import org.testng.annotations.BeforeTest;
//import org.testng.annotations.DataProvider;
//import org.testng.annotations.Test;
import org.testng.annotations.*;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class SignUpTestExcel {
	 public String baseUrl = "https://dev.sentiovr.com/signup";
	    String driverPath = "C:\\chromedriver.exe";
	    public String Msg;
	    public WebDriver driver; 
	    public String Error_Message;
	    public String orgname;
	    	    
	    @BeforeTest
	      public void launchBrowser() {
	    	 System.out.println("launching Chrome browser"); 
	    	   System.setProperty("webdriver.chrome.driver","C:\\chromedriver.exe");
	    	   driver = new ChromeDriver();
	    	  driver.get(baseUrl);
	    	  driver.manage().window().maximize();
	    	  driver.manage().timeouts().implicitlyWait(40,TimeUnit.SECONDS);
	      }
	    
	    @Test(dataProvider="testdata") 
	   
	    public void signup(String name,String email,String password,String confirm,String organization) throws InterruptedException 
	    	 {
	    	
	    // Input name, Email,password, conform password and organization for Sign up
	    	  driver.findElement(By.id("name")).sendKeys(name);
	    	  driver.findElement(By.id("email")).sendKeys(email);
	    	  driver.findElement(By.id("password")).sendKeys(password);
	    	  driver.findElement(By.id("confirm")).sendKeys(confirm);
	    	  driver.findElement(By.id("organisation")).sendKeys(organization);	  
	    	    //orgname = driver.findElement(By.id("organisation")).getText();	  
	    	    //System.out.println(orgname);
	    	  //Thread.sleep(5000);
	    	  	    	  
	    	  // Check Submit Button Status to validate input for Login  
	    	 WebElement SignupButton = driver.findElement(By.xpath("//*[@id=\"ui-login\"]/div/div/md-card/md-content/form/button"));
	    	  
	    	  if(SignupButton.isEnabled())
	    	  {
	    	   System.out.print("\n SignUp Button is enabled. Take your action.");
	    	  // driver.manage().timeouts().implicitlyWait(40,TimeUnit.SECONDS);
	  
	    	  driver.findElement(By.xpath("//*[@id=\"ui-login\"]/div/div/md-card/md-content/form/button")).click();	    			 
	    	  driver.manage().timeouts().implicitlyWait(40,TimeUnit.SECONDS);
			  
	    	  // Check Pop Up Message after Clicking Login Button
	    	  Msg = driver.findElement(By.cssSelector("div.md-toast-content")).getText();
	    	  System.out.println(Msg);
	    	  
	    	 // Conditions to verify whether signup is Successful or Unsuccessful with error message
	  	  
	    	 if (Msg.equals("Sign up complete")){
	    		 System.out.println("Signup Pass");
	    	  }
	          
	    	  else if (Msg.equals("The email has already been taken."))
	    	  {
	    			  System.out.println("Email already taken");  
	    	  }
	    	       
	    	  else 
	    	    { 
	    		  System.out.println("sign up fail"); 
	    	    }
	  	    	  
	    	  }
	    	  
	    	  //Else condition of LoginButton Status
	    	  else
	    	  {
	    	   System.out.print("\n Invalid User atamped , Sign Up Button is disabled.");
	    	  }
	    
	    	  driver.get(baseUrl);
	    	  driver.manage().timeouts().implicitlyWait(40,TimeUnit.SECONDS);
	    	 }
	      	  
	   	   
	
	   @DataProvider(name = "testdata")
	   public Object [] [] readExcel() throws BiffException, IOException  {

	   File src=new File("E:\\SentioVR\\Signup.xls");

	   Workbook w = Workbook.getWorkbook(src);
	   Sheet s = w.getSheet("Sheet1");
	   int rows = s.getRows();
	   int columns = s.getColumns();

	   String inputData [] [] = new String [rows] [columns];
	   for (int i=0; i<rows; i++)
	   {
	    for (int j=0; j<columns; j++){
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

