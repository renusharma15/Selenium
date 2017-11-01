package com.myaccess.utils;

import java.io.File;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

//import com.IdentityAndAccess.locator.*;
//import com.adaptiveinsight.Locator.*;

public class CreateWebDriver {

	enum WebBrowser {
		Firefox, IE, Chrome
	}

	private WebDriver driver = null;	
	private String ieDriverPath = "//thirdpartyjars//selenium//drivers//IEDriverServer.exe";
	private String chromeDriverPath = "//thirdpartyjars//selenium//drivers//chromedriver.exe";

	public void init(PropertyReader preader) throws Exception {
		String driverType = preader.getProperty("BROWSER");
		//String url = propertyReader.readApplicationFile("URL", propertiesFilePath);

		// Check if desired browser is Firefox
		if (WebBrowser.Firefox.toString().equals(driverType)) {
			// FirefoxProfile firefoxProfile = new FirefoxProfile();
			// driver = new FirefoxDriver(firefoxProfile);
			/*
			 * DesiredCapabilities dc=new DesiredCapabilities();
			 * dc.setCapability
			 * (CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR,UnexpectedAlertBehaviour
			 * .ACCEPT); driver =new FirefoxDriver(dc);
			 */
			ProfilesIni profile = new ProfilesIni();

			FirefoxProfile myprofile = profile.getProfile("default");

			driver = new FirefoxDriver(myprofile);
		}

		// Check if desired browser is Internet Explorer
		else if (WebBrowser.IE.toString().equals(driverType)) {
			// Set property for IEDriverServer
			String path = getPath();
			File file = new File(path + ieDriverPath);
			System.setProperty("webdriver.ie.driver", file.getAbsolutePath());

			// Accept all SSL Certificates
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);

			// Create driver object
			driver = new InternetExplorerDriver();
		}

		// Check if desired browser is Chrome
		else if (WebBrowser.Chrome.toString().equals(driverType)) {
			String path = getPath();
			System.setProperty("webdriver.chrome.driver", path
					+ chromeDriverPath);
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			ChromeOptions options = new ChromeOptions();
			options.addArguments("test-type");
			capabilities
					.setCapability("chrome.binary", path + chromeDriverPath);
			capabilities.setCapability(ChromeOptions.CAPABILITY, options);
			driver = new ChromeDriver(options);
		}
		// If browser type is not matched, consider it as Firefox
		else {
			FirefoxProfile firefoxProfile = new FirefoxProfile();
			driver = new FirefoxDriver(firefoxProfile);
		}

		// Maximize window
		driver.manage().window().maximize();

		// Delete cookies
		driver.manage().deleteAllCookies();
		// driver.navigate().to(url);

	}
	
	/*public void setCoreWebDriver(WebDriver webdriver) {
		driver = webdriver;
	}*/


	/*
	 * @AfterTest public void afterMainMethod() {
	 * 
	 * //driver.quit(); }
	 */

	public WebDriver getWebDriver() {
		return driver;
	}

	// Get absolute path
	public String getPath() {
		String path = "";
		File file = new File("");
		String absolutePathOfFirstFile = file.getAbsolutePath();
		path = absolutePathOfFirstFile.replaceAll("\\\\+", "/");
		return path;
	}

	// This allows the URL to be passed in from a Command line.
	public void setupUrlParm(PropertyReader propertyReader) throws Exception {

		String driverType, url;
		driverType = propertyReader.getProperty("BROWSER");
		// Pulls the URL from a CMD Line
		// url=propertyReader.readApplicationFile("URL",filepath);
		url = System.getProperty("URL");

		// Check if desired browser is Firefox
		if (WebBrowser.Firefox.toString().equals(driverType)) {

			FirefoxProfile firefoxProfile = new FirefoxProfile();
			firefoxProfile
					.setPreference(
							"browser.helperApps.neverAsk.saveToDisk",
							"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;"// MIME
																								// types
																								// Of
																								// MS
																								// Excel
																								// File.
									+ "application/pdf;" // MIME types Of PDF
															// File.
									+ "application/vnd.openxmlformats-officedocument.wordprocessingml.document;" // MIME
																													// types
																													// Of
																													// MS
																													// doc
																													// File.
									+ "text/plain;" // MIME types Of text File.
									+ "text/csv"); // MIME types Of CSV File.

			// 0 = desktop, 1 = default download folder , 2 = user defined
			// location.
			firefoxProfile.setPreference("browser.download.folderList", 1);

			// driver = new FirefoxDriver(firefoxProfile);
			// DesiredCapabilities dc=new DesiredCapabilities();
			// dc.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR,UnexpectedAlertBehaviour.ACCEPT);

			driver = new FirefoxDriver(firefoxProfile);
		}

		// Check if desired browser is Internet Explorer
		else if (WebBrowser.IE.toString().equals(driverType)) {
			// Set property for IEDriverServer
			String path = getPath();
			File file = new File(path + ieDriverPath);
			System.setProperty("webdriver.ie.driver", file.getAbsolutePath());

			// Accept all SSL Certificates
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);

			// Create driver object
			driver = new InternetExplorerDriver();
		}

		// Check if desired browser is Chrome
		else if (WebBrowser.Chrome.toString().equals(driverType)) {
			String path = getPath();

			System.setProperty("webdriver.chrome.driver", path
					+ chromeDriverPath);
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			ChromeOptions options = new ChromeOptions();
			options.addArguments("test-type");
			capabilities
					.setCapability("chrome.binary", path + chromeDriverPath);
			capabilities.setCapability(ChromeOptions.CAPABILITY, options);
			driver = new ChromeDriver(options);
		}
		// If browser type is not matched, consider it as Firefox
		else {
			FirefoxProfile firefoxProfile = new FirefoxProfile();
			driver = new FirefoxDriver(firefoxProfile);
		}

		// Maximize window
		driver.manage().window().maximize();

		// Delete cookies
		driver.manage().deleteAllCookies();
		// driver.navigate().to(url);
	}
}
