package com.alation.selenium.automation;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PageObjectFactory {
	WebDriver driver;
	
	protected By firstSearchResultLink = By.xpath(".//h2[contains(@class, 'a-text-normal')][1]/parent::*");
	protected By footerLogoLink = By.xpath(".//div[contains(@class, 'navFooterLogoLine')]/a/div");
	protected By isbnFeatureElement = By.id("isbn_feature_div");
	//search category
	@FindBy(id = "searchDropdownBox")
	WebElement categorySelection;
	
	//list of webelements
	@FindAll(@FindBy(xpath = ".//select[@id='searchDropdownBox']/option"))
	List<WebElement> dropdownOptions;
	
	@FindBy(xpath = ".//input[@type = 'submit']")
	WebElement searchButton;
	
	@FindBy(id = "add-to-cart-button")
	WebElement addToCartBtn;
	
	@FindBy(id = "mediaTabs_tabSet")
	WebElement mediaTabSet;
	
	@FindBy(id = "add-to-wishlist-button-submit")
	WebElement addToWishListBtn;
	
	@FindBy(id = "byline")
	WebElement booksByDiv;
	
	@FindBy(id = "twotabsearchtextbox")
	WebElement searchBar;
	
	@FindBy(id = "productTitle")
	WebElement productTitle;
	
	@FindBy(id = "pagnNextLink")
	WebElement nextPageLink;
	
	public PageObjectFactory(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
}
