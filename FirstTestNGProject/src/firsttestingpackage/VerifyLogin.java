package firsttestingpackage;
//import static org.junit.Assert.assertTrue;

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
public class VerifyLogin {
	 public String baseUrl = "https://sentiovr.com/login";
	    String driverPath = "C:\\chromedriver.exe";
	    public String Msg;
	    public WebDriver driver; 
	    public String Error_Message;
	    
	  /*  @BeforeTest
	      public void launchBrowser() {
	    	 System.out.println("launching Chrome browser"); 
	    	   System.setProperty("webdriver.chrome.driver","C:\\chromedriver.exe");
	    	   driver = new ChromeDriver();
	    	  driver.get(baseUrl);
	    	  driver.manage().window().maximize();
	    	  driver.manage().timeouts().implicitlyWait(40,TimeUnit.SECONDS);
	      }*/
	    
	  @Test(priority = 1)
		   public void VerifyForgotPassword () throws InterruptedException {
			   
			  Thread.sleep(5000);
		 	  driver.findElement(By.xpath(".//*[@id='ui-login']/div/div/md-card/md-content/form/div[2]/md-input-container[2]/a")).click(); 
		 	  driver.manage().timeouts().implicitlyWait(80,TimeUnit.SECONDS);
		 	  
		 	 WebElement h3Element = driver.findElement(By.xpath("//*[@id='ui-login']/div/div/md-card/md-toolbar/div[2]/h1"));
		 	 System.out.println(h3Element.getText());
		 	 driver.manage().timeouts().implicitlyWait(40,TimeUnit.SECONDS);
		 	 Assert.assertEquals(driver.findElement(By.xpath("//*[@id='ui-login']/div/div/md-card/md-toolbar/div[2]/h1")).getText(),"Forgot your password?");
		 	driver.manage().timeouts().implicitlyWait(40,TimeUnit.SECONDS); 
	    }
	    
	    /*@Test(priority = 2)
		   public void VerifyDonthaveaccount () throws InterruptedException {
	    	 driver.get(baseUrl);
		 	  driver.findElement(By.xpath(".//*[@id='ui-login']/div/div/md-card/md-content/form/a")).click();
		 	  driver.manage().timeouts().implicitlyWait(80,TimeUnit.SECONDS);
		 	 String URL = driver.getCurrentUrl();
		 	 Assert.assertEquals(URL, "sentiovr.com/signup" );  	  
		 	  
		   }
	    */
	    @Test(dataProvider="testdata")
	    public void Login(String email,String password) throws InterruptedException 
	    {
	    	 System.out.println("launching Chrome browser"); 
	    	   System.setProperty("webdriver.chrome.driver","C:\\chromedriver.exe");
	    	   driver = new ChromeDriver();
	    	   driver.manage().window().maximize();
	    	   driver.get(baseUrl);
	    	   driver.manage().timeouts().implicitlyWait(40,TimeUnit.SECONDS);
	    	
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
	    		 System.out.println("Login Pass");
	    	  }
	          
	    	  else if (Msg.equals("Please check your email and password. Try again")){
	    			  System.out.println("Incorrect User Name or Password");  
	    		  }
	    	       
	    	  else 
	    	    { 
	    		  System.out.println("Login fail"); 
	    	    }
	  	    	  
	    	  }
	    	  
	    	  //Else condition of LoginButton Status
	    	  else
	    	  {
	    	   System.out.print("\n Invalid User Name or Password, Login Button is disabled.");
	    	  }
	    }
	    
	      	  
	   @AfterMethod
	   
	   public void tearDown()
	   {
	   	  
	  driver.quit();
	   }  	     


	    @DataProvider(name="testdata")
	    public Object[][] TestDataFeed(){
	     
	    // Create object array with 2 rows and 2 column- first parameter is row and second is //column
	    Object [][] SentioVR=new Object[9][2];
	     
	    // Enter data to row  & column 
	    SentioVR[0][0]="zoya@xyz.com";
	    SentioVR[0][1]="12345678";
	    
	    SentioVR[1][0]="zoya@xyz.com";
	    SentioVR[1][1]="123456789";
	   
	    SentioVR[2][0]="poya@xyz.com";
	    SentioVR[2][1]="123456789";
	    
	    SentioVR[3][0]="poya@xyz.com";
	    SentioVR[3][1]="12345678";
	    
	       
	    SentioVR[4][0]="";
	    SentioVR[4][1]="123456789";
	    
	    SentioVR[5][0]="zoya@xyz.com";
	    SentioVR[5][1]="";
	    
	    SentioVR[6][0]="";
	    SentioVR[6][1]="";
	    
	    SentioVR[7][0]="zoya@xyz";
	    SentioVR[7][1]="12345678";
	    
	    SentioVR[8][0]="zoya@xyz";
	    SentioVR[8][1]="12345678";
	    
	    
// return arrayobject to testscript
	    return SentioVR;
	    }
	    
}
 

	    