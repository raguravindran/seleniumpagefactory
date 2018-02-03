package com.alation.selenium.automation;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

/**
 * @author Saravana Raguram Ravindran
 * @date 02/01/2018
 */

public class AbstractBrowser extends SeleniumGenericLibrary{

	public PageObjectFactory pom;
	private static final String IN_STOCK_TEXT = "In Stock.";
	public static final Logger log = Logger.getLogger(AbstractBrowser.class.getName());

	public void navigateToHomePage() {
		init();
		pom = new PageObjectFactory(driver);
	}

	/**
	 * search for item with category selection
	 * @param category - the category
	 * @param searchText - text to be searched for
	 * @throws Exception
	 */
	private void searchForItemWithCategorySelection(String category, String searchText) throws Exception {
		pom.categorySelection.click();
		for(WebElement e : pom.dropdownOptions) {
			if(e.getText().equalsIgnoreCase(category)) {
				e.click();
			}
		}
		String _srchBarText = pom.searchBar.getText();
		if( _srchBarText.trim() != null ) {
			log.debug("SearchBar contains text, clearing the searchbar before typing.");
			pom.searchBar.clear();
		}
		pom.searchBar.sendKeys(searchText);
		pom.searchBar.sendKeys(Keys.ENTER);
	}

	/**
	 * Main method which searches and navigates to the first results
	 * @param category - the category
	 * @param searchText - text to be searched for
	 * @throws Exception
	 */
	public void searchAndNavigateToFirstResult(String category, String searchText) throws Exception {
		searchForItemWithCategorySelection(category, searchText);
		waitForElementAndClick(pom.firstSearchResultLink);
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
	 * checks if prodcut title is available on the book page
	 * @return
	 * @throws Exception
	 */
	public boolean isProductTitleDisplayed() throws Exception {
		return isWebElementDisplayedOnPage(pom.productTitle);
	}

	/**
	 * checks if the add to cart button is displayed on the page.
	 * @return
	 * @throws Exception
	 */
	public boolean isAddToCartButtonDisplayed() throws Exception {
		return isWebElementDisplayedOnPage(pom.paperBackCheckoutBtn);
	}
	
	/**
	 * checks if the add to cart button is displayed on the page.
	 * @return
	 * @throws Exception
	 */
	public boolean isCheckoutButtonDisplayed() throws Exception {
		return isWebElementDisplayedOnPage(pom.paperBackCheckoutBtn);
	}

	/**
	 * checks if the add wishlist option is displayed on the page.
	 * @return
	 * @throws Exception
	 */
	public boolean isAddToWishlistDisplayed() throws Exception {
		return isWebElementDisplayedOnPage(pom.addToWishListBtn);
	}
	
	/**
	 * checks if the book has rating and logs the rating for the product
	 * @return
	 * @throws Exception
	 */
	public boolean isBookAverageRatingDisplayed() throws Exception {
		return isWebElementDisplayedOnPage(pom.avgRatingMetrics);
	}

	/**
	 * checks if the price for the passed edition is displayed/available on the page
	 * @param edition - takes in the edition Kindle, paperback etc
	 * @return
	 * @throws Exception
	 */
	public boolean checkIfEditionPriceDisplayed(String edition) throws Exception {
		for(WebElement e : pom.editionsTab) {
			if(e.getText().contains(edition)) {
				e.click();
				String price = driver.findElement(By.xpath(pom.HEADER_PRODUCT_PRICE_LOCATOR)).getText();
				outputMessage("Price for " + edition + " is - " + price);
				return true;
			}
		}
		return false;
	}

	/**
	 * Check if the product is in stock, verify if the "In Stock." element is present on the page.
	 * @return
	 * @throws Exception
	 */
	public boolean isProductInStock() throws Exception {
		boolean stockAvail = false;
		boolean bool = isWebElementDisplayedOnPage(pom.stockAvailability);
		if(bool) {
			if(pom.stockAvailability.getText().equalsIgnoreCase(IN_STOCK_TEXT)) {
				stockAvail = true;
				outputMessage("Product in Stock");
			} else {
				outputMessage("Product not in Stock.");
			}
		}
		return stockAvail;
	}

	/**
	 * Check with available editions
	 * @param edition - paperback or kindle
	 * @return
	 */
	public boolean checkForEditions(String edition) {
		for(WebElement e : pom.editionsTab) {
			if(e.getText().contains(edition)) {
				return true;
			}
		}
		return false;
	}

	//quits from driver and ends the browser session
	@AfterClass(alwaysRun = true)
	public void quitDriver() {
		driver.quit();
	}

	/**
	 * Extent report gets initialized with the test sutie
	 * @throws Exception
	 */
	@BeforeSuite(alwaysRun = true)
	public void initialiseSuite() throws Exception {
		extentReport = new ExtentReporter("eCommerceAutomationReport");
	}

	@BeforeMethod(alwaysRun = true)
	public void initMethod(Method method) {
		System.out.println("Running test:" + method.getName());
		String packageName = this.getClass().getPackage().getName();
		String directParent = packageName.substring(1 + packageName.lastIndexOf("."));
		extentReport.initializeLoggerBeforeClass(
				directParent + "." + this.getClass().getSimpleName() + "." + method.getName() );
	}

	@AfterMethod(alwaysRun = true)
	public void printReport(Method method, ITestResult result) {
		extentReport.PrintReport(result);
	}

	@AfterSuite(alwaysRun = true)
	public void tearDown() {
		extentReport.zipReport();
	}
}