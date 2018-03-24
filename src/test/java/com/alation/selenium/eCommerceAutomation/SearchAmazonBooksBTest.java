package com.alation.selenium.eCommerceAutomation;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.alation.selenium.automation.BasePageClass;
import com.alation.selenium.automation.TestConstants;
import com.alation.selenium.automation.TestConstants.Priority;

/**
 * @author Saravana Raguram Ravindran
 * @date 02/01/2018
 */

public class SearchAmazonBooksBTest extends BasePageClass {

	public static final Logger log = Logger.getLogger(SearchAmazonBooksBTest.class.getName());
	private static final String CLAZZ = "SearchAmazonBooksBTest";
	private static String SEARCH_TEXT = "data catalog"; 
	private boolean title = false;
	private boolean kinEdition = false;
	private boolean paperEdition = false;
	private boolean kinPrice = false;
	private boolean paperPrice = false;
	private boolean rating = false;
	private boolean addToCart = false;
	private boolean isAvailable = false;
	private boolean otherSellers = false;

	@BeforeClass
	public void setUp() throws Exception {
		//TODO: any setup needed should go here
		//example: login to page, navigating to home. etc
		navigateToHomePage();
	}

	@Test( groups = "somke")
	public void homepageSearchResultsTest() throws Exception {
		searchForItemWithCategorySelection(TestConstants.Categories.BOOKS, SEARCH_TEXT);
        int noOfResults = homePage.countResultsInSearchPage();
        log.info("Total number of search results " + noOfResults);
        Assert.assertEquals(noOfResults, homePage.getActualPageResultsSize());
		boolean nextLink = homePage.isNextPageLinkIsDisplayedOnPage();
		boolean nextString = homePage.isNextPageStringIsDisplayedOnPage();
		if(!nextLink && nextString) {
			log.info("Next link is disabled. !");
		}
		boolean prevLink = homePage.isPrevPageLinkIsDisplayedOnPage();
		boolean prevString = homePage.isPrevPageStringIsDisplayedOnPage();
		if(!prevLink && prevString) {
            log.info("Previous page link is disabled. !");
        }
	}

	//Group names are useful when running particular group li
	@Test( groups = { "smoke", CLAZZ, Priority.P1 })
	public void verifyProductTitleTest() throws Exception {
		searchAndNavigateToFirstResult(TestConstants.Categories.BOOKS, SEARCH_TEXT);
		String titleOnPage = getTextForElements(bookPage.productTitle);
		log.info("Test case run for product - " + titleOnPage);
		title = bookPage.isProductTitleDisplayed();
		extentReport.assertTrue(title, "Book title was found", "Did not Find the title");
		rating = bookPage.isBookAverageRatingDisplayed();
		String noOfCustReviews = getTextForElements(bookPage.totalCustomerReviews);
		log.info("Total number of reviews displayed - " + noOfCustReviews);
		extentReport.assertTrue(rating, "Customer Rating is displayed on Page", "Customer Rating is not displayed on Page");
		boolean hoverd = bookPage.hoverOnElement();
		extentReport.assertTrue(hoverd, "hover was succeesful", "failed to hover on the element");
		isAvailable = bookPage.isProductInStock();
		extentReport.assertTrue(isAvailable, "Product in Stock", "Product not in stock");
	}

	@Test( groups = { "functional", CLAZZ, Priority.P2 }, dependsOnMethods = "verifyProductTitleTest")
	public void bookKindleEditionDetailsTest() throws Exception {
		if(isAvailable) {
			addToCart = bookPage.isAddToCartButtonDisplayed();
			extentReport.assertTrue(addToCart, "Add to Cart button is present", "Add to Cart button is missing");
		}
		addToCart = bookPage.isAddToWishlistDisplayed();
		extentReport.assertTrue(addToCart, "Add to Cart button is present", "Add to Cart button is missing");
		paperEdition = checkForEditions(TestConstants.Books.PAPER_BK_EDITN);
		if(paperEdition) {
			paperPrice = bookPage.checkIfEditionPriceDisplayed(TestConstants.Books.PAPER_BK_EDITN);
			extentReport.assertTrue(paperPrice, "Paperback edition is present and Price is shown", "Paperback edition is present but doesnt show price");
		}
		kinEdition = checkForEditions(TestConstants.Books.KINDLE_EDITN);
		if(kinEdition) {
			kinPrice = bookPage.checkIfEditionPriceDisplayed(TestConstants.Books.KINDLE_EDITN);
			extentReport.assertTrue(kinPrice, "Kindle Edition is present and Price is shown", "Kindle Edition is present but doesnt show price");
		}
		otherSellers = checkForEditions(TestConstants.Books.OTHERS);
		extentReport.assertTrue(otherSellers, "Other Sellers tab is present", "Did not Find other sellers tab");
	}
}
