package com.alation.selenium.automation;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * @author Saravana Raguram Ravindran
 * @date 02/01/2018
 * 
 * BookPage serves as the object factory for books page. which has all the page elements that are respective to books.
 */

public class BookPageObjectFactory extends SeleniumGenericLibrary{
	WebDriver driver;
	private static final String IN_STOCK_TEXT = "In Stock.";
	protected final String HEADER_PRODUCT_PRICE_LOCATOR = ".//span[contains(@class, 'header-price')]";

	//xpath
	//list of webelements

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

	@FindBy(id = "acrPopover")
	public WebElement acrPopover;

	@FindBy(id = "histogramTable")
	public WebElement reviewPopover;

	@FindBy(id = "checkout-button")
	public WebElement kindleCheckoutBtn;

	@FindBy(id = "add-to-cart-button")
	public WebElement paperBackCheckoutBtn;
	
	@FindBy(id = "averageCustomerReviews")
	public WebElement avgRatingMetrics;
	
	@FindBy(id = "availability")
	public WebElement stockAvailability;

	@FindBy(id = "productTitle")
	public WebElement productTitle;

	public BookPageObjectFactory(WebDriver driver) {
		this.driver = driver; 
		PageFactory.initElements(driver, this);
	}
	
	/**
	 * checks if prodcut title is available on the book page
	 * @return
	 * @throws Exception
	 */
	public boolean isProductTitleDisplayed() throws Exception {
		return isWebElementDisplayedOnPage(productTitle);
	}

	/**
	 * checks if the add to cart button is displayed on the page.
	 * @return
	 * @throws Exception
	 */
	public boolean isAddToCartButtonDisplayed() throws Exception {
		return isWebElementDisplayedOnPage(paperBackCheckoutBtn);
	}
	
	/**
	 * checks if the add to cart button is displayed on the page.
	 * @return
	 * @throws Exception
	 */
	public boolean isCheckoutButtonDisplayed() throws Exception {
		return isWebElementDisplayedOnPage(paperBackCheckoutBtn);
	}

	/**
	 * checks if the add wishlist option is displayed on the page.
	 * @return
	 * @throws Exception
	 */
	public boolean isAddToWishlistDisplayed() throws Exception {
		return isWebElementDisplayedOnPage(addToWishListBtn);
	}
	
	/**
	 * checks if the book has rating and logs the rating for the product
	 * @return
	 * @throws Exception
	 */
	public boolean isBookAverageRatingDisplayed() throws Exception {
		return isWebElementDisplayedOnPage(avgRatingMetrics);
	}

	/**
	 * checks if the price for the passed edition is displayed/available on the page
	 * @param edition - takes in the edition Kindle, paperback etc
	 * @return
	 * @throws Exception
	 */
	public boolean checkIfEditionPriceDisplayed(String edition) throws Exception {
		for(WebElement e : editionsTab) {
			if(e.getText().contains(edition)) {
				e.click();
				String price = driver.findElement(By.xpath(HEADER_PRODUCT_PRICE_LOCATOR)).getText();
				outputMessage("Price for " + edition + " is - " + price);
				return true;
			}
		}
		return false;
	}

	public boolean hoverOnElement() {
		boolean hoverd = false;
		Actions act = new Actions(driver);
		act.moveToElement(acrPopover);
		act.perform();
		try {
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.visibilityOf(reviewPopover));
			if(reviewPopover != null && reviewPopover.isDisplayed()) {
				hoverd = true;
			}
		} catch (Exception e) {
			hoverd = false;
			log.info("Error" + e);
		}
		return hoverd;
	}
	/**
	}
	 * Check if the product is in stock, verify if the "In Stock." element is present on the page.
	 * @return
	 * @throws Exception
	 */
	public boolean isProductInStock() throws Exception {
		boolean stockAvail = false;
		boolean bool = isWebElementDisplayedOnPage(stockAvailability);
		if(bool) {
			if(stockAvailability.getText().equalsIgnoreCase(IN_STOCK_TEXT)) {
				stockAvail = true;
				outputMessage("Product in Stock");
			} else {
				outputMessage("Product not in Stock.");
			}
		}
		return stockAvail;
	}


}
