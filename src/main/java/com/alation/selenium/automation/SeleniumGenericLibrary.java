package com.alation.selenium.automation;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;

/**
 * @author Saravana Raguram Ravindran
 * @date 02/01/2018
 * 
 * Parent for all the classes, initializes the driver, handles the common functionalities that are needed for the framework.
 */

public class SeleniumGenericLibrary  {
	public WebDriver driver;
	private String _testBrowser = "firefox";
	protected static final long MAX_SLEEP_TIME = 500;
	protected static final long MAX_DRIVER_WAIT = 5000;
	//landing page
	private String _url = "http://amazon.com";
	//gecko driver for firefox
	private static final String GECKO_DRIVER = System.getProperty("user.dir") + "/drivers/geckodriver.exe";
	private static final String CHROME_DRIVER_DIRECTORY = System.getProperty("user.dir") + "/drivers/chromedriver.exe";
	private static final String OS_TYPE = System.getProperty("os.name").toLowerCase();
	public static ExtentReporter extentReport;
	//logger
	public static final Logger log = Logger.getLogger(SeleniumGenericLibrary.class.getName());

	public void init() {
		selectBrowser(_testBrowser);
		getUrl(_url);
		String log4jConfigPath = "log4j.properties";
		PropertyConfigurator.configure(log4jConfigPath);
	}

	/**
	 * Based on the browser that is sent the driver is initialized
	 * @param testBrowser_ - firefox or chrome
	 */
	private void selectBrowser(String testBrowser_) {
		if("firefox".equalsIgnoreCase(testBrowser_)) {
			//gecko driver is initialized for firefox
			if(OS_TYPE.indexOf("win") >= 0){
				System.setProperty("webdriver.gecko.driver", GECKO_DRIVER);
				driver = new FirefoxDriver();
			} else {
				System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "/drivers/geckodriver");
				driver = new FirefoxDriver();
			}
		} 
		else if("chrome".equalsIgnoreCase(testBrowser_)) {
			//chrome driver is initialized
			System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_DIRECTORY);
			driver = new ChromeDriver();
		}
	}

	/**
	 * Tests starts from here. driver is initialized and launches the URL that is passed.
	 * @param url_ - entry url
	 */
	public void getUrl(String url_) {
		driver.get(url_);
		//maximize the window
		driver.manage().window().maximize();
		//wait until elements to be available
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
	}

	/**
	 * given element driver will explicitly wait for element
	 * @param element_ - takes element that needs to be clicked
	 */
	public void waitForElementAndClick(WebElement element_) throws Exception {
		try{
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.visibilityOf(element_));
			if ((element_ != null) && (element_.isEnabled())) {
				outputMessage("Element is visible clicking! - " + element_.toString());
				element_.click();
			}
		}
		catch(WebDriverException ex) {
			outputMessage("Failed to click on element");
		}
	}
	
	/**
	 * Wrapper method to get text on element
	 * @param element_ - takes WebElement as argument 
	 * @return
	 * @throws Exception
	 */
	public String getTextForElements(WebElement element_) throws Exception {
		isWebElementDisplayedOnPage(element_);
		return element_.getText();
	}

	/**
	 * Common method handles the only checking of the element on page and returns true or false
	 * @param element_ - takes webelement as a parameter
	 * @return
	 * @throws Exception
	 */
	protected boolean isWebElementDisplayedOnPage(WebElement element_) throws Exception {
		try {
			outputMessage("waiting to click on element.. - " + element_.toString());
			waitUntilElementFound(element_);
			return true;
		} catch(Exception e) {
			return false;
		}
	}

	protected boolean isWebElementDisplayedOnPage(By by) throws Exception {
		return isWebElementDisplayedOnPage(driver.findElement(by));
	}
	/**
	 * uses thread sleep and maxtime do{..}while loop to wait for the element to be found
	 * @param element_ - takes webelement as parameter
	 * @throws Exception
	 */
	protected void waitUntilElementFound(WebElement element_) throws Exception {
		int maxtime = 0;
		String eleName = null;
		do {
			Thread.sleep(MAX_SLEEP_TIME);
			maxtime++;
			try {
				eleName = element_.getTagName();
				log.info("Waiting for element to appear on page -> " + element_.toString());
			}
			catch(Exception e) {
				log.error("Element was not found, trying again -> " + element_.toString());
				throw new ElementNotVisibleException(element_.toString());
			}
		} while(eleName != null && maxtime < 5) ;
	}

	/**
	 * any log messages within wrapper or common library can use this method
	 * @param message_ The message to output
	 */
	protected void outputMessage( String message_ )
	{
		log.info( message_ );
	}

	//quits from driver and ends the browser session
	@AfterClass(alwaysRun = true)
	public void quitDriver() {
		if(driver !=null) {
			driver.quit();
		}
	}
}