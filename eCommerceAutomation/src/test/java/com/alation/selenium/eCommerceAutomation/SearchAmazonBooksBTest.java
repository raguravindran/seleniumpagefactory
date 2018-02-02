package com.alation.selenium.eCommerceAutomation;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.alation.selenium.automation.AbstractBrowser;
import com.alation.selenium.automation.PageObjectFactory;

public class SearchAmazonBooksBTest extends AbstractBrowser{

	PageObjectFactory pom;
	
	@BeforeClass
	public void setUp() {
	
	}
	@Test
	public void mainTest() throws Exception {
		navigateToHomePage();
		searchAndNavigateToFirstResult("Books", "data catalog");
		searchAndNavigateToFirstResult("Apps & Games", "counter strike");
	}
}
