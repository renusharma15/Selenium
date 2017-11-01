package firsttestingpackage;
//import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class FirstTestNGFile2 {
	 public String baseUrl = "https://app.sentiovr.com/";
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
  public void averifyHomepageTitle() {
	    
  String expectedTitle = "Sentio - VR";
  String actualTitle = driver.getTitle();
  Assert.assertEquals(actualTitle, expectedTitle);
  }
  
  @Test(priority = 4)
  public void verifyFAQandblog() throws InterruptedException {
	  driver.findElement(By.xpath(".//*[@id='bs-navbar-collapse-1']/ul[2]/li[1]/a")).click(); 
	  driver.manage().timeouts().implicitlyWait(40,TimeUnit.SECONDS);
	  driver.findElement(By.xpath(".//*[@id='bs-navbar-collapse-1']/ul[2]/li[2]/a")).click();
	  driver.manage().timeouts().implicitlyWait(40,TimeUnit.SECONDS);
	  
	  String Parentwindow=driver.getWindowHandle();
	  Set<String> allWindows=driver.getWindowHandles();
	  ArrayList<String> tabs=new ArrayList<>(allWindows);
	    
	  driver.switchTo().window(tabs.get(2));
	  System.out.println(driver.getTitle());
	  Assert.assertEquals(driver.getTitle(), "SentioVR");
	  driver.close();
	  
	  driver.switchTo().window(tabs.get(1));
	  System.out.println(driver.getTitle());
	  Assert.assertEquals(driver.getTitle(), "Architecture – Solutionario – Medium");
	  driver.close();
	  driver.switchTo().window(Parentwindow);
	  
  }
  
  @Test(priority = 6)
  public void verifySocial() throws InterruptedException {
	  Thread.sleep(3000);
	  driver.findElement(By.xpath("/html/body/footer/div/div/div[3]/ul/li[1]/a/i")).click();
	  driver.manage().timeouts().implicitlyWait(40,TimeUnit.SECONDS);
	  driver.findElement(By.xpath("/html/body/footer/div/div/div[3]/ul/li[2]/a/i")).click(); 
	  driver.manage().timeouts().implicitlyWait(40,TimeUnit.SECONDS);
	  driver.findElement(By.xpath("/html/body/footer/div/div/div[3]/ul/li[3]/a/i")).click();
	  driver.manage().timeouts().implicitlyWait(40,TimeUnit.SECONDS);
	  String Parentwindow=driver.getWindowHandle();
	  Set<String> allWindows=driver.getWindowHandles();
	  ArrayList<String> tabs=new ArrayList<>(allWindows);
	  driver.switchTo().window(tabs.get(1));
	  System.out.println(driver.getTitle());
	  //Assert.assertEquals(driver.getTitle(), "Solutionario | LinkedIn");
	
	  //Thread.sleep(3000);
	  driver.close();
	  
	  driver.switchTo().window(tabs.get(3));
	  System.out.println(driver.getTitle());
	  Assert.assertEquals(driver.getTitle(), "SentioVR (@sentiovr) | Twitter");
	  //Thread.sleep(3000);
	  driver.close();
	  
	  driver.switchTo().window(tabs.get(2));
	  System.out.println(driver.getTitle());
	  Assert.assertEquals(driver.getTitle(), "Solutionario - Home | Facebook");
	  //Thread.sleep(3000);
	  driver.close();
	  
	  driver.switchTo().window(Parentwindow);
  } 
  
  
   
  @Test(priority = 3)
  public void Contact() {
  JavascriptExecutor js=(JavascriptExecutor)driver;
  js.executeScript("window.scrollBy(0,100)");
  driver.manage().timeouts().implicitlyWait(40,TimeUnit.SECONDS);
  driver.findElement(By.xpath(".//*[@id='bs-navbar-collapse-1']/ul[1]/li[3]/a")).click();
  Assert.assertEquals(driver.findElement(By.xpath(".//*[@id='contact']/div[1]/header/h2")).getText(), "Contact Us");
  }
  
   
  @Test(priority = 1)
  public void Features() {
	  JavascriptExecutor js=(JavascriptExecutor)driver;
	  js.executeScript("window.scrollBy(0,100)");
	  driver.manage().timeouts().implicitlyWait(40,TimeUnit.SECONDS);
	  driver.findElement(By.xpath(".//*[@id='bs-navbar-collapse-1']/ul[1]/li[1]/a")).click();
	  Assert.assertEquals(driver.findElement(By.xpath(".//*[@id='features']/div/header/h2")).getText(), "How it Works?");
	  }
	  
 @Test(priority = 2)
 public void Pricing() {
	 JavascriptExecutor js=(JavascriptExecutor)driver;
	  js.executeScript("window.scrollBy(0,100)");
	  driver.manage().timeouts().implicitlyWait(40,TimeUnit.SECONDS);
	  driver.findElement(By.xpath(".//*[@id='bs-navbar-collapse-1']/ul[1]/li[2]/a")).click();
	  Assert.assertEquals(driver.findElement(By.xpath(".//*[@id='pricing']/div/header/h2")).getText(), "Plans");
	  }
	  
  @AfterTest
  public void terminateBrowser(){
      driver.close();
  	  
	    }
  
  
  @Test(priority = 5)
  public void verifyTCandPP() throws InterruptedException {
	  driver.findElement(By.xpath("html/body/footer/div/div/div[1]/p/a[1]")).click(); 
	  driver.manage().timeouts().implicitlyWait(80,TimeUnit.SECONDS);
	  driver.findElement(By.xpath("html/body/footer/div/div/div[1]/p/a[2]")).click();
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
  
  @Test(priority = 7)
  public void Login() {
  
  driver.findElement(By.xpath("//*[@id=\"bs-navbar-collapse-1\"]/ul[2]/li[3]/a")).click();
  driver.manage().timeouts().implicitlyWait(80,TimeUnit.SECONDS);
 
  System.out.println(driver.getTitle());
  Assert.assertEquals(driver.findElement(By.xpath("//*[@id=\"ui-login\"]/div/div/md-card/md-toolbar/div[2]/h1")).getText(), "Login");
  System.out.println("login page");
 // driver.get(baseUrl);
  driver.navigate().back();
  System.out.println("Test Pass");
  }
  
   /* @Test(priority = 8)
  public void SignUp() {
  
  driver.findElement(By.xpath("html/body/header/section/div/div/div[2]/div/a")).click();
  driver.manage().timeouts().implicitlyWait(80,TimeUnit.SECONDS);
  
  System.out.println(driver.getTitle());
  Assert.assertEquals(driver.findElement(By.xpath(".//*[@id='ui-login']/div/div/md-card/md-toolbar/div[2]/h1")).getText(), "Sign up");
  System.out.println("SignUp with signup button");
 // driver.get(baseUrl);
  driver.navigate().back();
  System.out.println("Test Pass");
  }*/
  
 /* @Test(priority = 9)
  public void Signup() throws InterruptedException{
	  
	  Thread.sleep(5000);
   System.out.println("singup click");

   driver.findElement(By.xpath("/html/body/header/section/div/div/div[2]/div/a")).click();
  driver.manage().timeouts().implicitlyWait(80,TimeUnit.SECONDS);
  
  System.out.println("sentiovr sign up page");
  String URL = driver.getCurrentUrl();
  Assert.assertEquals(URL, "https://sentiovr.com/signup" );
  
  System.out.println(URL);
  System.out.println("SignUp with sign up button");

   driver.navigate().back();
  System.out.println("Test Pass");
  }*/
  
  
  @Test(priority = 8)
  public void JoinUs() {
  
 driver.findElement(By.xpath("//*[@id=\"bs-navbar-collapse-1\"]/ul[2]/li[4]/a")).click();
  driver.manage().timeouts().implicitlyWait(80,TimeUnit.SECONDS);
  
  System.out.println(driver.getTitle());
  Assert.assertEquals(driver.findElement(By.xpath("//*[@id=\"ui-login\"]/div/div/md-card/md-toolbar/div[2]/h1")).getText(), "Sign up");
  System.out.println("SignUp with joinus button");
  //driver.get(baseUrl);
  driver.navigate().back();
  System.out.println("Test Pass");
  }
  
  
}
