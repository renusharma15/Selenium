package com.myaccess.utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

public class DriverHelper extends CreateWebDriver {

	public WebDriver driver;
	protected int TIMEOUT = 50;
	private LocatorReader commonLocatorReader;
	WebDriverWait wait;

	public void coreSetup(PropertyReader preader, LocatorReader commonlocatorreader) throws Exception {
		init(preader);
		driver = getWebDriver();
		wait = new WebDriverWait(driver, 30);
		commonLocatorReader = commonlocatorreader;
	}

	/*
	 * public Selenium getSelenium(){ return selenium; }
	 */
	
	//z002f13
	public String dateFormatddMMMyyyy(String date) throws ParseException {
		SimpleDateFormat fromUI = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat myFormat = new SimpleDateFormat("dd MMM yyyy");
		String reformattedStr = myFormat.format(fromUI.parse(date));
		return reformattedStr;
	}
//z002f13 visibility to public
	public String getLocator(String locatorpath) throws Exception {
		return commonLocatorReader.getLocator(locatorpath);
	}

	private String getLocator(String... locatorpath) throws Exception {
		return commonLocatorReader.getLocator(locatorpath[0]);
	}

	// Handle locator type
	private By ByLocator(String locator) {
		By result = null;

		if (locator.startsWith("//") || locator.indexOf("/") != -1) {
			result = By.xpath(locator);
		} else if (locator.startsWith("css=")) {
			result = By.cssSelector(locator.replace("css=", ""));
		} else if (locator.startsWith("name=")) {
			result = By.name(locator.replace("name=", ""));
		} else if (locator.startsWith("link=")) {
			result = By.linkText(locator.replace("link=", ""));
		} else if (locator.startsWith("tagName=")) {
			result = By.tagName(locator.replace("tagName=", ""));
		} else if (locator.indexOf("/") != -1) {
			result = By.xpath(locator);
		} else {
			result = By.id(locator);
		}
		return result;
	}

	public WebElement findElement(String locator, String... plocatorpath) throws Exception {
		try {
			String pLocatorPath = plocatorpath.length == 1 ? plocatorpath[0] : null;
			WebElement ele;
			if (pLocatorPath == null) {
				ele = driver.findElement(ByLocator(locator));
			} else {
				String pLocator = getLocator(plocatorpath);
				WebElement context = driver.findElement(ByLocator(pLocator));
				ele = context.findElement(ByLocator(locator));
			}
			return ele;
		} catch (Exception ex) {
			ExecutionLog.log("Error: Failed in DriverHelper getElement " + locator);
			throw ex;
		}
	}
	
	public WebElement findElementByContext(WebElement context, String locatorPath) throws Exception {
		try {
			String locator = getLocator(locatorPath);
			return context.findElement(ByLocator(locator));
		} catch (NoSuchElementException ex) {
			ExecutionLog.log("Error: No Element found" + locatorPath);
			throw ex;
		}
		catch (Exception ex) {
			ExecutionLog.log("Error: Failed in DriverHelper getElement " + locatorPath);
			throw ex;
		}
	}

	private List<WebElement> findElements(String locator, String... plocatorpath) throws Exception {
		try {
			String pLocatorPath = plocatorpath.length == 1 ? plocatorpath[0] : null;
			if (pLocatorPath == null) {
				return driver.findElements(ByLocator(locator));
			} else {
				String pLocator = getLocator(plocatorpath);
				WebElement context = driver.findElement(ByLocator(pLocator));
				return context.findElements(ByLocator(locator));
			}
		} catch (Exception ex) {
			ExecutionLog.log("Error: Failed in DriverHelper getElements " + locator);
			throw ex;
		}
	}
	
	public List<WebElement> getElements(String locatorpath) throws Exception {
		try {
			String locator = getLocator(locatorpath);
			return driver.findElements(ByLocator(locator));
		} catch (Exception ex) {
			ExecutionLog.log("Error: Failed in DriverHelper getElements " + locatorpath);
			throw ex;
		}
	}

	public void implicitWait(int timeout) {
		driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
	}

	public WebElement findElementByLocatorPath(String locatorpath, String... plocatorpath) throws Exception {
		String locator = getLocator(locatorpath);
		return findElement(locator, plocatorpath);
	}

	public List<WebElement> findElementsByLocatorPath(String locatorpath, String... plocatorpath) throws Exception {
		String locator = getLocator(locatorpath);
		return findElements(locator, plocatorpath);
	}
	
	public List<WebElement> waitAndFindElements(String locatorpath, String... plocatorpath) throws Exception {
		String locator = getLocator(locatorpath);
		String pLocator = getLocator(plocatorpath[0]);
		waitForElementVisible(pLocator, TIMEOUT);
		return findElements(locator, plocatorpath);
	}

	public Boolean isElementPresent(String locator, String... plocatorpath) {
		try {
			findElement(locator, plocatorpath);
			return true;

		} catch (Exception ex) {
			ExecutionLog.log("Error: Failed in DriverHelper isElementPresent " + locator);
			return false;
		}
	}

	public boolean isElementEnabled(String locator, String... plocatorpath) throws Exception {
		boolean result = false;
		try {
			WebElement ele = findElement(locator, plocatorpath);
			if (ele.isEnabled()) {
				result = true;
			}
			return result;
		} catch (Exception ex) {
			ExecutionLog.log("Error: Failed in DriverHelper isEnabled for " + locator);
			return result;
		}
	}
	
	public boolean isElementDisplayed(String locator, String... plocatorpath) throws Exception {
		boolean result = false;
		try {
			WebElement ele = findElement(locator, plocatorpath);
			if (ele.isDisplayed()) {
				result = true;
			}
			return result;
		} catch (Exception ex) {
			//System.out.println("Error: Failed in DriverHelper isEnabled for ");
			ExecutionLog.log("Error: Failed in DriverHelper isEnabled for " + locator);
			return result;
		}
	}
	
	public void waitForElementVisible(String locator) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(ByLocator(locator)));
	}

	public void acceptAlert() {
		Alert alert = driver.switchTo().alert();
		alert.accept();
	}

	public boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			driver.switchTo().alert().accept();
			return true;
		}// try
		catch (Exception e) {
			return false;
		}// catch
	}

	public void WaitForElementPresent(String locator, int timeout, String... plocatorpath) throws Exception {

		for (int i = 0; i < timeout; i++) {
			if (isElementPresent(locator, plocatorpath)) {
				break;
			}
			Thread.sleep(500);
		}
	}

	public void WaitForElementEnabled(String locator, int timeout, String... plocatorpath) throws Exception {
		WaitForElementPresent(locator, timeout, plocatorpath);
		for (int i = 0; i < timeout; i++) {
			if (isElementEnabled(locator, plocatorpath)) {
				break;
			}
		}
	}

	public void WaitForElementNotEnabled(String locator, int timeout, String... plocatorpath) throws Exception {
		WaitForElementPresent(locator, timeout, plocatorpath);
		for (int i = 0; i < timeout; i++) {
			if (!isElementEnabled(locator, plocatorpath)) {
				break;
			}
		}
	}

	public void waitForElementVisible(String locator, int timeout, String... plocatorpath) throws Exception {
		WaitForElementPresent(locator, timeout, plocatorpath);
		for (int i = 0; i < timeout; i++) {
			if (isElementDisplayed(locator, plocatorpath)) {
				break;
			}
		}
	}

	public void WaitForElementNotVisible(String locator, int timeout, String... plocatorpath) throws Exception {
		WaitForElementPresent(locator, timeout, plocatorpath);
		for (int i = 0; i < timeout; i++) {
			if (!isElementDisplayed(locator, plocatorpath)) {
				break;
			}
		}
	}

	public void mouseOver(String locatorpath, String... plocatorpath) throws Exception {
		String locator = getLocator(locatorpath);
		WaitForElementPresent(locator, TIMEOUT, plocatorpath);
		// find Assignments menu
		WebElement el = findElement(locator, plocatorpath);

		// build and perform the mouseOver with Advanced User Interactions API
		Actions builder = new Actions(driver);
		builder.moveToElement(el).build().perform();
	}

	public void waitAndClick(String locatorpath, String... plocatorpath) throws Exception {
		String locator = getLocator(locatorpath);
		String methodName = getMethodName(3);
		try {
			waitForElementVisible(locator, TIMEOUT, plocatorpath);
			WebElement el = findElement(locator, plocatorpath);
			el.click();
		} catch (StaleElementReferenceException ex) {
			ExecutionLog.log("Warning: StaleElementReferenceException in DriverHlper waitAndClick for" + locatorpath);
			waitAndClick(locatorpath, plocatorpath);
		}
		catch(ElementNotVisibleException ex) {
			if(!methodName.equals("waitAndClick")) {
				Thread.sleep(10000);
				ExecutionLog.log("Warning: ElementNotVisibleException in DriverHlper waitAndClick for" + locatorpath);
				waitAndClick(locatorpath, plocatorpath);
			}
			else {
				throw ex;
			}
		}
		catch(WebDriverException ex) {
			if(ex.getMessage().contains("Other element would receive the click")) {
				if(!methodName.equals("waitAndClick")) {
					Thread.sleep(10000);
					ExecutionLog.log("Warning: Other element would receive the click for" + locatorpath);
					waitAndClick(locatorpath, plocatorpath);
				}
			}
			else {
				throw ex;
			}
		}
	}
	public boolean verifyElement(String locatorpath, String... plocatorpath) throws Exception {
		String locator = getLocator(locatorpath);
		try {
			waitForElementVisible(locator, TIMEOUT, plocatorpath);
			WebElement el = findElement(locator, plocatorpath);
			return true;
		} catch (StaleElementReferenceException ex) {
			ExecutionLog.log("Warning: StaleElementReferenceException in DriverHlper waitAndClick for" + locatorpath);
			//waitAndClick(locatorpath, plocatorpath);
			return false;
		}
		catch(ElementNotVisibleException ex) {
			return false;
		}
		catch(WebDriverException ex) {
			return false;
		}
	}

	public void click(String locatorpath, String... plocatorpath) throws Exception {
		try {
			WebElement el = findElementByLocatorPath(locatorpath, plocatorpath);
			el.click();
		} catch (StaleElementReferenceException ex) {
			ExecutionLog.log("Warning: StaleElementReferenceException in DriverHlper click for" + locatorpath);
			click(locatorpath, plocatorpath);
		}

	}
	
	public void clickAndWait(String locatorpath, int... arg) throws Exception {
		int delay = arg.length == 1 ? arg[0] : 0;
		String locator = getLocator(locatorpath);
		waitForElementVisible(locator, TIMEOUT);
		click(locatorpath);
		if (delay != 0) {
			Thread.sleep(delay);
		}
	}

	public void clickOnWhenEnabled(String locatorpath, int... arg) throws Exception {
		int delay = arg.length == 1 ? arg[0] : 0;
		String locator = getLocator(locatorpath);
		WaitForElementEnabled(locator, TIMEOUT);
		click(locatorpath);
		if (delay != 0) {
			Thread.sleep(delay);
		}
	}

	public void clickOnWhenVisible(String locatorpath, int... arg) throws Exception {
		int delay = arg.length == 1 ? arg[0] : 0;
		String locator = getLocator(locatorpath);
		waitForElementVisible(locator, TIMEOUT);
		click(locatorpath);
		if (delay != 0) {
			Thread.sleep(delay);
		}
	}

	public void waitAndDoubleClick(String locatorpath, String... plocatorpath) throws Exception {
		String locator = getLocator(locatorpath);
		WaitForElementPresent(locator, TIMEOUT, plocatorpath);
		Assert.assertTrue(isElementPresent(locator));
		WebElement el = findElement(locator, plocatorpath);
		Actions action = new Actions(driver);
		action.doubleClick(el).perform();
	}

	public void doubleClick(String locatorpath, String... plocatorpath) throws Exception {
		WebElement el = findElementByLocatorPath(locatorpath, plocatorpath);
		Actions action = new Actions(driver);
		action.doubleClick(el).perform();
	}

	public void rightClick(String locatorpath, String... plocatorpath) throws Exception {
		WebElement el = findElementByLocatorPath(locatorpath, plocatorpath);
		Actions action = new Actions(driver);
		action.contextClick(el).build().perform();
	}

	public String waitAndGetValue(String locatorpath, String... plocatorpath) throws Exception {
		String locator = getLocator(locatorpath);
		waitForElementVisible(locator, TIMEOUT, plocatorpath);
		WebElement ele = findElement(locator, plocatorpath);
		return ele.getAttribute("value");
	}

	public String getValue(String locatorpath, String... plocatorpath) throws Exception {
		WebElement ele = findElementByLocatorPath(locatorpath, plocatorpath);
		return ele.getAttribute("value");
	}

	public void waitAndSendKeys(String locatorpath, String value, String... plocatorpath) throws Exception {
		String locator = getLocator(locatorpath);
		waitForElementVisible(locator, TIMEOUT, plocatorpath);
		WebElement el = findElement(locator, plocatorpath);
		// el.clear();
		el.sendKeys(value);
	}

	public void sendKeys(String locatorpath, String value, String... plocatorpath) throws Exception {
		WebElement el = findElementByLocatorPath(locatorpath, plocatorpath);
		// el.clear();
		el.sendKeys(value);
	}

	public void sendkeysAndWait(String locatorpath, String text, int... arg) throws Exception {
		int delay = arg.length == 1 ? arg[0] : 0;
		String locator = getLocator(locatorpath);
		waitForElementVisible(locator, TIMEOUT);
		sendKeys(locatorpath, text);
		if (delay != 0) {
			Thread.sleep(delay);
		}
	}

	public void waitAndSelectFrame(String locatorpath, String... plocatorpath) throws Exception {
		String locator = getLocator(locatorpath);
		WaitForElementPresent(locator, TIMEOUT, plocatorpath);
		driver.switchTo().frame(locator);
	}

	public void selectFrame(String locatorpath) throws Exception {
		String locator = getLocator(locatorpath);
		driver.switchTo().frame(locator);
	}

	public void waitAndSelectDropDown(String locatorpath, String targetValue, String... plocatorpath) throws Exception {
		String locator = getLocator(locatorpath);
		waitForElementVisible(locator, TIMEOUT, plocatorpath);
		new Select(findElement(locator, plocatorpath)).selectByVisibleText(targetValue);
	}

	public void selectDropDown(String locatorpath, String targetValue, String... plocatorpath) throws Exception {
		new Select(findElementByLocatorPath(locatorpath, plocatorpath)).selectByVisibleText(targetValue);
	}

	public boolean isTextPresent(String locatorpath, String str, String... plocatorpath) throws Exception {
		String message = findElementByLocatorPath(locatorpath, plocatorpath).getText();
		if (message.contains(str)) {
			return true;
		} else {
			return false;
		}
	}

	public String waitAndGetText(String locatorpath, String... plocatorpath) throws Exception {
		String locator = getLocator(locatorpath);
		waitForElementVisible(locator, TIMEOUT, plocatorpath);
		return findElement(locator, plocatorpath).getText();
	}

	public String getText(String locatorpath, String... plocatorpath) throws Exception {
		return findElementByLocatorPath(locatorpath, plocatorpath).getText();
	}

	public void scrollIntoView(String locatorpath) throws Exception {
		WebElement elem = findElementByLocatorPath(locatorpath);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", new Object[] { elem });
	}

	public String getAttributeValue(String locatorpath, String AttributeName, String... plocatorpath) throws Exception {
		WebElement ele = findElementByLocatorPath(locatorpath, plocatorpath);
		return ele.getAttribute(AttributeName);
	}

	public boolean isDisplayed(String locatorpath, String... plocatorpath) throws Exception {
		boolean result = false;
		if (findElementByLocatorPath(locatorpath, plocatorpath).isDisplayed()) {
			result = true;
		}
		return result;
	}

	public void waitForSpinnerInvisible(String locatorpath) throws Exception {
		while (isDisplayed(locatorpath)) {
			Thread.sleep(2000);
		}
	}
	//z002f13
	public void pressCtrlPlusAOnElement(String locatorpath, String... plocatorpath) throws Exception {
		findElementByLocatorPath(locatorpath, plocatorpath).sendKeys(Keys.CONTROL + "a");
	}

	public void pressEnterOnElement(String locatorpath, String... plocatorpath) throws Exception {
		findElementByLocatorPath(locatorpath, plocatorpath).sendKeys(Keys.ENTER);

	}

	public void pressCtrlPlusXOnElement(String locatorpath, String... plocatorpath) throws Exception {
		findElementByLocatorPath(locatorpath, plocatorpath).sendKeys(Keys.CONTROL + "x");
	}

	public void pressCtrlPlusVOnElement(String locatorpath, String... plocatorpath) throws Exception {
		findElementByLocatorPath(locatorpath, plocatorpath).sendKeys(Keys.CONTROL + "v");
	}

	public void pressCtrlPlusCOnElement(String locatorpath, String... plocatorpath) throws Exception {
		findElementByLocatorPath(locatorpath, plocatorpath).sendKeys(Keys.CONTROL + "c");
	}

	public void pressBackspaceInacell(String locatorpath, String... plocatorpath) throws Exception {
		findElementByLocatorPath(locatorpath, plocatorpath).sendKeys(Keys.BACK_SPACE);
	}

	public void pressEscapekey(String locatorpath, String... plocatorpath) throws Exception {
		findElementByLocatorPath(locatorpath, plocatorpath).sendKeys(Keys.ESCAPE);
	}

	public void pressRightarrow(String locatorpath, String... plocatorpath) throws Exception {
		findElementByLocatorPath(locatorpath, plocatorpath).sendKeys(Keys.ARROW_RIGHT);
	}

	public void pressLeftarrow(String locatorpath, String... plocatorpath) throws Exception {
		findElementByLocatorPath(locatorpath, plocatorpath).sendKeys(Keys.ARROW_LEFT);
	}

	public void pressUparrow(String locatorpath, String... plocatorpath) throws Exception {
		findElementByLocatorPath(locatorpath, plocatorpath).sendKeys(Keys.ARROW_UP);
	}

	public void pressDownarrow(String locatorpath, String... plocatorpath) throws Exception {
		findElementByLocatorPath(locatorpath, plocatorpath).sendKeys(Keys.ARROW_DOWN);

	}

	public void pressTabKey(String locatorpath, String... plocatorpath) throws Exception {
		findElementByLocatorPath(locatorpath, plocatorpath).sendKeys(Keys.TAB);
	}

	public void pressHomekey(String locatorpath, String... plocatorpath) throws Exception {
		findElementByLocatorPath(locatorpath, plocatorpath).sendKeys(Keys.HOME);
	}

	public void pressEndKey(String locatorpath, String... plocatorpath) throws Exception {
		findElementByLocatorPath(locatorpath, plocatorpath).sendKeys(Keys.END);
	}

	public void pressPageUpKey(String locatorpath, String... plocatorpath) throws Exception {
		findElementByLocatorPath(locatorpath, plocatorpath).sendKeys(Keys.PAGE_UP);
	}

	public void pressPageDownKey(String locatorpath, String... plocatorpath) throws Exception {
		findElementByLocatorPath(locatorpath, plocatorpath).sendKeys(Keys.PAGE_DOWN);
	}

	public void pressShiftPlusTabKey(String locatorpath, String... plocatorpath) throws Exception {
		findElementByLocatorPath(locatorpath, plocatorpath).sendKeys(Keys.SHIFT, Keys.TAB);
	}

	public void pressControlPlusHomeKey(String locatorpath, String... plocatorpath) throws Exception {
		findElementByLocatorPath(locatorpath, plocatorpath).sendKeys(Keys.CONTROL, Keys.HOME);
	}

	public void pressControlPlussEndKey(String locatorpath, String... plocatorpath) throws Exception {
		findElementByLocatorPath(locatorpath, plocatorpath).sendKeys(Keys.CONTROL, Keys.END);
	}

	public void pressshift(String locatorpath, String... plocatorpath) throws Exception {
		findElementByLocatorPath(locatorpath, plocatorpath).sendKeys(Keys.SHIFT);
	}

	public void pressShiftPlusUparrow(String locatorpath, String... plocatorpath) throws Exception {
		findElementByLocatorPath(locatorpath, plocatorpath).sendKeys(Keys.SHIFT, Keys.UP);
	}

	public void pressShiftPlusDownArrowKey(String locatorpath, String... plocatorpath) throws Exception {
		findElementByLocatorPath(locatorpath, plocatorpath).sendKeys(Keys.SHIFT, Keys.ARROW_DOWN);
	}

	public void pressShiftPlusLeftarrow(String locatorpath, String... plocatorpath) throws Exception {
		findElementByLocatorPath(locatorpath, plocatorpath).sendKeys(Keys.SHIFT, Keys.ARROW_LEFT);
	}

	public void pressShiftPlusRightarrow(String locatorpath, String... plocatorpath) throws Exception {
		findElementByLocatorPath(locatorpath, plocatorpath).sendKeys(Keys.SHIFT, Keys.ARROW_RIGHT);

	}

	public void pressShiftPlusHomeKey(String locatorpath, String... plocatorpath) throws Exception {
		findElementByLocatorPath(locatorpath, plocatorpath).sendKeys(Keys.SHIFT, Keys.HOME);
	}

	public void pressShiftPlusEndKey(String locatorpath, String... plocatorpath) throws Exception {
		findElementByLocatorPath(locatorpath, plocatorpath).sendKeys(Keys.SHIFT, Keys.END);

	}

	public void pressShiftPlusPageUpKey(String locatorpath, String... plocatorpath) throws Exception {
		findElementByLocatorPath(locatorpath, plocatorpath).sendKeys(Keys.SHIFT, Keys.PAGE_UP, Keys.PAGE_UP);
	}

	public void pressShiftPlusPageDownKey(String locatorpath, String... plocatorpath) throws Exception {
		findElementByLocatorPath(locatorpath, plocatorpath).sendKeys(Keys.SHIFT, Keys.PAGE_DOWN, Keys.PAGE_DOWN);

	}

	public void pressShiftPlusControlPlusLeftArrow(String locatorpath, String... plocatorpath) throws Exception {
		findElementByLocatorPath(locatorpath, plocatorpath).sendKeys(Keys.SHIFT, Keys.CONTROL, Keys.LEFT);
	}

	public void pressShiftPlusControlPlusRightArrow(String locatorpath, String... plocatorpath) throws Exception {
		findElementByLocatorPath(locatorpath, plocatorpath).sendKeys(Keys.SHIFT, Keys.CONTROL, Keys.RIGHT);
	}

	public void pressShiftPlusControlPlusHomeKey(String locatorpath, String... plocatorpath) throws Exception {
		findElementByLocatorPath(locatorpath, plocatorpath).sendKeys(Keys.SHIFT, Keys.CONTROL, Keys.HOME);
	}

	public void pressShiftPlusControlPlusEndKey(String locatorpath, String... plocatorpath) throws Exception {
		findElementByLocatorPath(locatorpath, plocatorpath).sendKeys(Keys.SHIFT, Keys.CONTROL, Keys.END);
	}

	public void pressShiftPlusControlPlusAltPlusHome(String locatorpath, String... plocatorpath) throws Exception {
		findElementByLocatorPath(locatorpath, plocatorpath).sendKeys(Keys.SHIFT, Keys.CONTROL, Keys.ALT, Keys.HOME);
	}

	public void pressShiftPlusControlPlusAltPlusEnd(String locatorpath, String... plocatorpath) throws Exception {
		findElementByLocatorPath(locatorpath, plocatorpath).sendKeys(Keys.SHIFT, Keys.CONTROL, Keys.ALT, Keys.END);
	}

	public void pressShiftPlusClickKey(String locatorpath, String... plocatorpath) throws Exception {
		Actions act = new Actions(driver);
		act.keyDown(Keys.SHIFT).click(findElementByLocatorPath(locatorpath, plocatorpath)).keyUp(Keys.SHIFT).perform();

	}

	public void pressDeleteKey(String locatorpath, String... plocatorpath) throws Exception {
		findElementByLocatorPath(locatorpath, plocatorpath).sendKeys(Keys.DELETE);
	}

	public void ClickAndDragMouseKey(String sourcelocpath, String targetlocpath) throws Exception {
		Actions act = new Actions(driver);
		WebElement SourceElement = findElementByLocatorPath(sourcelocpath);
		WebElement TargetElement = findElementByLocatorPath(targetlocpath);
		act.clickAndHold(SourceElement).moveToElement(TargetElement).release(TargetElement).build().perform();
	}

	public void ClickandControlAndDragMouseKey(String sourcelocpath, String targetlocpath) throws Exception {
		Actions act = new Actions(driver);
		WebElement SourceElement = findElementByLocatorPath(sourcelocpath);
		WebElement TargetElement = findElementByLocatorPath(targetlocpath);
		act.keyDown(Keys.CONTROL).clickAndHold(SourceElement).moveToElement(TargetElement).release(TargetElement).keyUp(Keys.CONTROL).build()
				.perform();

	}

	public void ClickandShiftAndDragMouseKey(String sourcelocpath, String targetlocpath) throws Exception {
		Actions act = new Actions(driver);
		WebElement SourceElement = findElementByLocatorPath(sourcelocpath);
		WebElement TargetElement = findElementByLocatorPath(targetlocpath);
		act.keyDown(Keys.SHIFT).clickAndHold(SourceElement).moveToElement(TargetElement).release(TargetElement).keyUp(Keys.SHIFT).build().perform();

	}

	public void ClickandShiftPlusControlAndDragMouseKey(String sourcelocpath, String targetlocpath) throws Exception {
		Actions act = new Actions(driver);
		WebElement SourceElement = findElementByLocatorPath(sourcelocpath);
		WebElement TargetElement = findElementByLocatorPath(targetlocpath);
		act.keyDown(Keys.SHIFT).keyDown(Keys.CONTROL).clickAndHold(SourceElement).moveToElement(TargetElement).release(TargetElement)
				.keyUp(Keys.CONTROL).keyUp(Keys.SHIFT).build().perform();

	}

	// method for destroying the range of cells and pressing ESC key

	public void destoyselectionrange(String locatorpath, String... plocatorpath) throws Exception {
		WebElement ele = findElementByLocatorPath(locatorpath, plocatorpath);
		ele.sendKeys(Keys.SHIFT, Keys.ARROW_DOWN, Keys.ARROW_DOWN);
		ele.sendKeys(Keys.ESCAPE);
	}

	// Verifying the quarter cells

	public void assertSelectedQuarterCell(String locatorpath, String... plocatorpath) throws Exception {
		Assert.assertTrue(findElementByLocatorPath(locatorpath, plocatorpath).isDisplayed(), "Quarter row's are not selected");
	}

	// Select the quarter cells and then delete the values

	public void selectdelete(String locatorpath, String... plocatorpath) throws Exception {
		WebElement ele = findElementByLocatorPath(locatorpath, plocatorpath);
		ele.sendKeys(Keys.SHIFT, Keys.ARROW_DOWN, Keys.ARROW_DOWN);
		ele.sendKeys(Keys.DELETE);
	}

	public void selectDropDownByTag(String locatorpath, String targetValue, String... plocatorpath) throws Exception {
		String locator = getLocator(locatorpath);
		WaitForElementPresent(locator, TIMEOUT, plocatorpath);
		new Select(findElement(locator, plocatorpath)).selectByValue(targetValue);

	}

	public void deselectValueInList(String locatorpath, String... plocatorpath) throws Exception {
		String locator = getLocator(locatorpath);
		WaitForElementPresent(locator, TIMEOUT, plocatorpath);
		new Select(findElement(locator, plocatorpath)).deselectAll();

	}

	// Verifying the selected cell till total using RGB value

	public void assertHeaderTotalCell(String locatorpath, String targetValue, String... plocatorpath) throws Exception {
		WebElement ele = findElementByLocatorPath(locatorpath, plocatorpath);
		Assert.assertTrue(ele.isDisplayed(), "Total row's are not selected");
		ele.sendKeys(Keys.ESCAPE);
	}

	// Verifying the selected from locked cell using RGB value

	public void assertLockedCellSelection(String locatorpath, String targetValue, String... plocatorpath) throws Exception {
		WebElement ele = findElementByLocatorPath(locatorpath, plocatorpath);
		Assert.assertTrue(ele.isDisplayed(), "Total row's are not selected");
		ele.sendKeys(Keys.ESCAPE);
	}

	public boolean isCheckBoxSelected(String locatorpath, String targetValue, String... plocatorpath) throws Exception {
		WebElement ele = findElementByLocatorPath(locatorpath, plocatorpath);
		if (ele.isSelected()) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isDisabled(String locatorpath, String targetValue, String... plocatorpath) throws Exception {
		WebElement ele = findElementByLocatorPath(locatorpath, plocatorpath);
		boolean result = false;
		if (ele.isEnabled()) {
			result = true;
		}
		return result;
	}

	public WebElement isElementLoaded(String locatorpath, String... plocatorpath) throws Exception {
		WebElement ele = findElementByLocatorPath(locatorpath, plocatorpath);
		WebDriverWait wait = new WebDriverWait(driver, TIMEOUT);
		WebElement element = wait.until(ExpectedConditions.visibilityOf(ele));
		return element;
	}

	public void switchWindows(String mainWindowHandler, int... arg) throws Exception {
		int delay = arg.length == 1 ? arg[0] : 0;
		String subWindowHandler = null;
		Set<String> handles = driver.getWindowHandles(); // get all window
															// handles
		for (String handle : handles) {
			if (mainWindowHandler.compareToIgnoreCase(handle) != 0) {
				subWindowHandler = handle;
				break;
			}
		}
		if (subWindowHandler != null) {
			driver.switchTo().window(subWindowHandler);
			ExecutionLog.log("switched windows");
		} else {
			ExecutionLog.log("Failed to switch windows, No other windows than main window found.");
		}
		Thread.sleep(delay);
	}

	public void selectRadioButton(String locatorpath, int... arg) throws Exception {
		int delay = arg.length == 1 ? arg[0] : 0;
		String locator = getLocator(locatorpath);
		WaitForElementPresent(locator, TIMEOUT);
		Assert.assertTrue(isElementPresent(locator));
		WebElement radioButton = findElement(locator);
		if (!radioButton.isSelected()) {
			radioButton.click();
		}
		if (delay != 0) {
			Thread.sleep(delay);
		}
	}

	// Capturing screenshot on failure
	public void captureScreenshot(String fileName) {
		String screenshotName = getFileName(fileName);
		FileOutputStream out = null;
		try {
			out = new FileOutputStream("screenshots//" + screenshotName + ".jpg");
			byte[] screenshotBytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
			out.write(screenshotBytes);
			out.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*String path = getPath();
		String screen = "file://" + path + "/screenshots/" + screenshotName + ".jpg";
		Reporter.log("<a href= '" + screen + "'target='_blank' >" + screenshotName + "</a>");*/
	}

	// Draws a rectangle over an element, still in development
	public void captureScreenshot(String fileName, WebElement el) {
		try {
			String screenshotName = this.getFileName(fileName);
			File out = new File("screenshots//" + screenshotName + ".jpg");
			byte[] screenshotBytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

			Dimension d = el.getSize();
			Point p = el.getLocation();
			System.out.println("Point: " + p);
			System.out.println("Dimension: " + d);

			drawRect(screenshotBytes, out, p.x, p.y, d.height, d.width);

			String path = getPath();
			String screen = "file://" + path + "/screenshots/" + screenshotName + ".png";
			Reporter.log("<a href= '" + screen + "'target='_blank' >" + screenshotName + "</a>");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void drawRect(byte[] screenshotBytes, File out, int x, int y, int height, int width) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new ByteArrayInputStream(screenshotBytes));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Graphics2D g = img.createGraphics();
		g.setColor(Color.RED);
		g.drawRect(x, y, width, height);
		try {
			if (ImageIO.write(img, "png", out)) {
				System.out.println("saved.. " + out);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void dragAndDrop(String loc_SourceElement, String loc_TargetElement) {
		Actions act = new Actions(driver);
		WebElement SourceElement = driver.findElement(ByLocator(loc_SourceElement));
		WebElement TargetElement = driver.findElement(ByLocator(loc_TargetElement));
		act.clickAndHold(SourceElement).moveToElement(TargetElement).pause(2000).release(TargetElement).build().perform();
	}

	public void dragAndDrop(String loc_SourceElement, String loc_TargetElement, String DashbaordTab) throws Exception {
		Actions act = new Actions(driver);
		WebElement SourceElement = driver.findElement(ByLocator(loc_SourceElement));
		WebElement TargetElement = driver.findElement(ByLocator(loc_TargetElement));
		WebElement SupportElement = driver.findElement(ByLocator(DashbaordTab));
		act.clickAndHold(SourceElement).moveToElement(SupportElement).pause(2000).moveToElement(TargetElement).pause(2000).release(TargetElement)
				.build().perform();
		
	}
	
	// Creating file name
	public String getFileName(String file) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		String fileName = file + dateFormat.format(cal.getTime());
		return fileName;
	}
	
	public boolean executeScript(String script) {
		return (boolean) ((JavascriptExecutor)driver).executeScript(script);
	}
	//Locator should be Id.
	public boolean isElementPresentThroughJS(String locatorpath) throws Exception {
		Thread.sleep(8000);
		String locator = getLocator(locatorpath);
		String script = "var ele = document.getElementById('"+ locator +"'); if (ele) { return true;} else {return false;}";
		return executeScript(script);
	}
	
	public Boolean isElementPresentByContext(WebElement context, String locatorPath) throws Exception {
		try {
			String locator = getLocator(locatorPath);
			context.findElement(ByLocator(locator));
			return true;
		} catch (Exception ex) {
			ExecutionLog.log("Error: Failed in DriverHelper isElementPresentByContext: " + locatorPath);
			return false;
		}
	}
	
	public String getMethodName(final int depth) {
		final StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		return ste[depth].getMethodName();
	}

}
