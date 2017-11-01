
package firsttestingpackage;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class LoginPageFPandSignup {
	 public String baseUrl = "https://sentiovr.com/login";
	    String driverPath = "C:\\chromedriver.exe";
	    public WebDriver driver ; 
	    
	    @BeforeTest
	      public void launchBrowser() {
	    	 System.out.println("launching Chrome browser"); 
	    	   System.setProperty("webdriver.chrome.driver","C:\\chromedriver.exe");
	    	   driver = new ChromeDriver();
	    	  driver.get(baseUrl);
	    	  driver.manage().window().maximize();
	    	  driver.manage().timeouts().implicitlyWait(40,TimeUnit.SECONDS);
	    }	  

	   

	  
		    
		  @Test(priority = 0)
			   public void VerifyForgotPassword () throws InterruptedException {
				   
				 Thread.sleep(5000);
			 	  driver.findElement(By.xpath("//*[@id=\"ui-login\"]/div/div/md-card/md-content/form/div[2]/md-input-container[2]/a")).click(); 
			 	  driver.manage().timeouts().implicitlyWait(80,TimeUnit.SECONDS);
			 	 String URL = driver.getCurrentUrl();
			 	 Assert.assertEquals(URL, "https://sentiovr.com/forgot" );  
			 	//WebElement h3Element = driver.findElement(By.xpath(".//*[@id='ui-login']/div/div/md-card/md-toolbar/div[2]/h1"));
			 	 //System.out.println(h3Element.getText());
			 	// driver.manage().timeouts().implicitlyWait(40,TimeUnit.SECONDS);
			 	// Assert.assertEquals(driver.findElement(By.xpath("//*[@id='ui-login']/div/div/md-card/md-toolbar/div[2]/h1")).getText(),"Forgot your password?");
			 	//driver.manage().timeouts().implicitlyWait(40,TimeUnit.SECONDS); 
		    }
		    
		    @Test(priority = 1)
			   public void VerifyDonthaveaccount () throws InterruptedException {
		    	Thread.sleep(5000);
		    	 driver.get(baseUrl);
			 	  driver.findElement(By.xpath(".//*[@id='ui-login']/div/div/md-card/md-content/form/a")).click();
			 	  driver.manage().timeouts().implicitlyWait(80,TimeUnit.SECONDS);
			 	 String URL = driver.getCurrentUrl();
			 	 Assert.assertEquals(URL, "https://sentiovr.com/signup" );  	  
			 	  
			   }
		    
		  /* @AfterMethod
		   	   public void tearDown1()
		   {
		   	  
		  driver.close();
		   }  */
	      }