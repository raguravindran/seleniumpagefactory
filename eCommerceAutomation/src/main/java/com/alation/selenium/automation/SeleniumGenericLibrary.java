package com.alation.selenium.automation;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


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
	public static ExtentReporter extentReport;

	public void init() {
		selectBrowser(_testBrowser);
		getUrl(_url);
		String log4jConfigPath = "log4j.properties";
		PropertyConfigurator.configure(log4jConfigPath);
	}

	private void selectBrowser(String testBrowser) {
		if("firefox".equalsIgnoreCase(testBrowser)) {
			System.setProperty("webdriver.gecko.driver", GECKO_DRIVER);
			driver = new FirefoxDriver();
		} else if("chrome".equalsIgnoreCase(testBrowser)) {
			System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_DIRECTORY);
			driver = new ChromeDriver();
		}
	}

	public void getUrl(String url) {
		driver.get(url);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	public void waitForElementAndClick(By by) {
		try{
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			WebElement Element = this.driver.findElement(by);
	
			if ((Element != null) && (Element.isEnabled())) {
				Element.click();
			}
		}
		catch(WebDriverException ex) {
			outputMessage("Failed to click on element with xpath" + by.toString() + " hence trying with Javascript");
		}
	}
	
/*	private void waitUntilElementFoundBy(By by) throws Exception {
		int maxtime = 0;
		WebElement ele = null;
		do {
			Thread.sleep(MAX_SLEEP_TIME);
			maxtime++;
			try {
				ele = this.driver.findElement(by);
			}
			catch(Exception e) {
				continue;
			}
		} while(ele != null && maxtime < 5) ;
	}*/

	/**
	 * Encapsulate the output of messages so that a change to use log4j would be possible
	 *
	 * @param message_ The message to output
	 */
	protected void outputMessage( String message_ )
	{
		System.out.println( message_ );
	}
}