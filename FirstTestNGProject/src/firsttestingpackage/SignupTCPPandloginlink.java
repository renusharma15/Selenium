package firsttestingpackage;

import java.util.ArrayList;
import java.util.Set;
//import java.util.ArrayList;
//import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class SignupTCPPandloginlink {
	 public String baseUrl = "https://sentiovr.com/signup";
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
		   public void verifyTCandPP() throws InterruptedException {
			   
			  Thread.sleep(5000);
		 	  driver.findElement(By.xpath(".//*[@id='ui-login']/div/div/md-card/md-content/form/div[3]/small/a[1]")).click(); 
		 	  driver.manage().timeouts().implicitlyWait(80,TimeUnit.SECONDS);
		 	  driver.findElement(By.xpath(".//*[@id='ui-login']/div/div/md-card/md-content/form/div[3]/small/a[2]")).click();
		 	  driver.manage().timeouts().implicitlyWait(80,TimeUnit.SECONDS);
		 	  
		 	  String Parentwindow=driver.getWindowHandle();
		 	  Set<String> allWindows=driver.getWindowHandles();
		 	  ArrayList<String> tabs=new ArrayList<>(allWindows);
		 	    
		 	  driver.switchTo().window(tabs.get(2));
		 	  System.out.println(driver.getTitle());
		 	  Assert.assertEquals(driver.getTitle(), "SentioVR: Terms of Service - Google Docs");
		 	  driver.close();
		 	  
		 	  driver.switchTo().window(tabs.get(1));
		 	  System.out.println(driver.getTitle());
		 	  Assert.assertEquals(driver.getTitle(), "SentioVR: Privacy policy - Google Docs");
		 	  driver.close();
		 	  driver.switchTo().window(Parentwindow);
		 	  
		   }
		   
		   @Test(priority = 1)	
		   public void alreadyhaveaaccount() throws InterruptedException
		   { 
			   driver.findElement(By.xpath("//*[@id='ui-login']/div/div/md-card/md-content/form/a")).click();
			   driver.manage().timeouts().implicitlyWait(80,TimeUnit.SECONDS);
			   String URL = driver.getCurrentUrl();
			 	 Assert.assertEquals(URL,"https://sentiovr.com/login"); 
			   System.out.println("login page");
			   // driver.get(baseUrl);
			    driver.navigate().back();
			    System.out.println("Test Pass");
		   }
}