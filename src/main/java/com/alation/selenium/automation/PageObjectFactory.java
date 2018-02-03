package com.alation.selenium.automation;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * @author Saravana Raguram Ravindran
 * @date 02/01/2018
 */

public class PageObjectFactory {
	WebDriver driver;
	protected final String HEADER_PRODUCT_PRICE_LOCATOR = ".//span[contains(@class, 'header-price')]";

	//search category
	@FindBy(id = "searchDropdownBox")
	public WebElement categorySelection;

	//xpath
	//list of webelements
	@FindAll(@FindBy(xpath = ".//select[@id='searchDropdownBox']/option"))
	public List<WebElement> dropdownOptions;

	@FindBy(xpath = ".//h2[contains(@class, 'a-text-normal')][1]/parent::*")
	public WebElement firstSearchResultLink;

	@FindBy(xpath = ".//div[contains(@class, 'navFooterLogoLine')]/a/div")
	public WebElement footerLogoLink;

	@FindBy(xpath = ".//input[@type = 'submit']")
	public WebElement searchButton;

	@FindBy(xpath = HEADER_PRODUCT_PRICE_LOCATOR)
	public WebElement priceOfProduct;

	@FindAll(@FindBy(css = "ul[id=\"mediaTabs_tabSet\"] li a"))
	public List<WebElement> editionsTab;
	
	@FindBy(id = "acrCustomerReviewLink")
	public WebElement totalCustomerReviews;

	@FindBy(css = "div[id=\"usedOfferAccordionRow\"] a")
	public WebElement usedBuyLink;

	@FindBy(css = "div[id=\"newOfferAccordionRow\"] a")
	public WebElement newBuyLink;

	@FindBy(id = "add-to-wishlist-button-submit")
	public WebElement addToWishListBtn;
	
	@FindBy(id = "ebooksProductTitle")
	public WebElement ebooksProductTitleId;
	
	@FindBy(id = "checkout-button")
	public WebElement kindleCheckoutBtn;

	@FindBy(id = "add-to-cart-button")
	public WebElement paperBackCheckoutBtn;
	
	@FindBy(id = "averageCustomerReviews")
	public WebElement avgRatingMetrics;
	
	@FindBy(id = "availability")
	public WebElement stockAvailability;

	@FindBy(id = "twotabsearchtextbox")
	public WebElement searchBar;

	@FindBy(id = "productTitle")
	public WebElement productTitle;

	@FindBy(id = "pagnNextLink")
	public WebElement nextPageLink;

	public PageObjectFactory(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
}
