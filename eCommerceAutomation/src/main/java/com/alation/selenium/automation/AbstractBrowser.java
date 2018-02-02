package com.alation.selenium.automation;

import java.lang.reflect.Method;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

public class AbstractBrowser extends SeleniumGenericLibrary{
	
	PageObjectFactory pom;

	public void navigateToHomePage() {
		init();
		pom = new PageObjectFactory(driver);
	}
	
	//search for item with category selection
	public void searchForItemWithCategorySelection(String selection, String searchText) throws Exception {
		pom.categorySelection.click();
		for(WebElement e : pom.dropdownOptions) {
			if(e.getText().equalsIgnoreCase(selection)) {
				e.click();
			}
		}
		String _srchBarText = pom.searchBar.getText();
		if( _srchBarText.trim() != null )
			pom.searchBar.clear();
		pom.searchBar.sendKeys(searchText);
		pom.searchBar.sendKeys(Keys.ENTER);
	}
	
	public void searchAndNavigateToFirstResult(String selection, String searchText) throws Exception {
		searchForItemWithCategorySelection(selection, searchText);
		waitForElementAndClick(pom.firstSearchResultLink);
	}
	
	public boolean findElementIfVisibleOnPage(By by) {
		return false;
	}
	
	@AfterClass(alwaysRun = true)
	public void quitDriver() {
		driver.quit();
	}
	
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