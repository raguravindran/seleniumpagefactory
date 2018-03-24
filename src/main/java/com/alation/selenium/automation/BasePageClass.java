package com.alation.selenium.automation;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

/**
 * @author Saravana Raguram Ravindran
 * @date 02/01/2018
 * 
 * This class serves as the parent class for all test classes. 
 * SeleniumGenericLibrary is the super class for all the classes, which executes certain commonalities in the test framework.
 * Like logging, common selenium actions etc.
 */

public class BasePageClass extends SeleniumGenericLibrary{

	public BookPageObjectFactory bookPage;
	public HomePageObjectFactory homePage;
	public static final Logger log = Logger.getLogger(BasePageClass.class.getName());

	public void navigateToHomePage() {
		init();
		bookPage = new BookPageObjectFactory(driver);
		homePage = new HomePageObjectFactory(driver);
	}

	/**
	 * search for item with category selection
	 * @param category - the category
	 * @param searchText - text to be searched for
	 * @throws Exception
	 */
	public void searchForItemWithCategorySelection(String category, String searchText) throws Exception {
		homePage.categorySelection.click();
		for(WebElement e : homePage.dropdownOptions) {
			if(e.getText().equalsIgnoreCase(category)) {
				e.click();
			}
		}
		String _srchBarText = homePage.searchBar.getText();
		if( _srchBarText.trim() != null ) {
			log.debug("SearchBar contains text, clearing the searchbar before typing.");
			homePage.searchBar.clear();
		}
		homePage.searchBar.sendKeys(searchText);
		homePage.searchBar.sendKeys(Keys.ENTER);
	}

	/**
	 * Main method which searches and navigates to the first results
	 * @param category - the category
	 * @param searchText - text to be searched for
	 * @throws Exception
	 */
	public void searchAndNavigateToFirstResult(String category, String searchText) throws Exception {
		searchForItemWithCategorySelection(category, searchText);
		waitForElementAndClick(bookPage.firstSearchResultLink);
	}

	/**
	 * Check with available editions
	 * @param edition - paperback or kindle
	 * @return
	 */
	public boolean checkForEditions(String edition) {
		for(WebElement e : bookPage.editionsTab) {
			if(e.getText().contains(edition)) {
				return true;
			}
		}
		return false;
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