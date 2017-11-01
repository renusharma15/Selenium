package firsttestingpackage;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class test {
	 public String baseUrl = "https://sentiovr.com";
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
	   public void SignUp() {
	   
	   driver.findElement(By.xpath("html/body/header/section/div/div/div[2]/div/a")).click();
	   driver.manage().timeouts().implicitlyWait(80,TimeUnit.SECONDS);
	   
	   System.out.println(driver.getTitle());
	   Assert.assertEquals(driver.findElement(By.xpath(".//*[@id='ui-login']/div/div/md-card/md-toolbar/div[2]/h1")).getText(), "Sign up");
	   System.out.println("SignUp with signup button");
	  // driver.get(baseUrl);
	   driver.navigate().back();
	   System.out.println("Test Pass");
	   }
	   
	   @AfterMethod
	   
	   public void tearDown()
	   {
	   	  
	  driver.quit();
	   }   	  

	      }