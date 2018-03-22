package com.alation.selenium.automation;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Interaction;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import java.util.List;
import java.util.Set;

/**
 * Created by saravanaraguramravindran on 3/21/18.
 */
public class HomePageObjectFactory extends SeleniumGenericLibrary {

    public By resultsListInPage = By.cssSelector("ul[id='s-results-list-atf'] li.s-result-item");

    @FindAll(@FindBy(xpath = ".//select[@id='searchDropdownBox']/option"))
    public List<WebElement> dropdownOptions;

    @FindBy(id = "twotabsearchtextbox")
    public WebElement searchBar;

    //search category
    @FindBy(id = "searchDropdownBox")
    public WebElement categorySelection;

    @FindBy(id = "pagnNextLink")
    public WebElement nextPageLink;

    @FindBy(id = "pagnPrevString")
    public WebElement prevPageTextStr;

    @FindBy(id = "pagnNextString")
    public WebElement nextPageTextStr;

    @FindBy(id = "pagnPrevLink")
    public WebElement prevPageLink;

    @FindBy(id="s-result-count")
    public WebElement resultsCountText;

    public HomePageObjectFactory(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public int countResultsInSearchPage() throws Exception{
        int count = 0;
        boolean bool = isWebElementDisplayedOnPage(resultsListInPage);
        if(bool) {
            List<WebElement> list = driver.findElements(resultsListInPage);
            count = list.size();
        }
        return count;
    }

    public boolean isNextPageLinkIsDisplayedOnPage() throws Exception{
        return isWebElementDisplayedOnPage(nextPageLink);
    }

    public boolean isNextPageStringIsDisplayedOnPage() throws Exception{
        return isWebElementDisplayedOnPage(nextPageTextStr);
    }

    public boolean isPrevPageLinkIsDisplayedOnPage() throws Exception{
        return isWebElementDisplayedOnPage(prevPageLink);
    }

    public boolean isPrevPageStringIsDisplayedOnPage() throws Exception{
        return isWebElementDisplayedOnPage(prevPageTextStr);
    }

    public int getActualPageResultsSize() throws Exception {
        String resultsCount = getTextForElements(resultsCountText);
        String[] splitText = resultsCount.split(" ");
        int pageCountStart = Integer.parseInt(splitText[0].split("-")[0]);
        int pageCountEnd = Integer.parseInt(splitText[0].split("-")[1]);

        return (pageCountEnd - pageCountStart) + 1;
    }
}
