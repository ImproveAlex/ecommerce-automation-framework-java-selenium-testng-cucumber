package com.improvealex.pom;

import com.improvealex.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class OrderPlacedPage extends BasePage {

    @FindBy(xpath = "//div[@class='ng-star-inserted']/*")
    List<WebElement> myOrderedProductsBox;

    @FindBy(tagName = "h1")
    WebElement thankYouForYourOrderBox;

    By myOrderProductTitle = By.xpath(".//div[@class = 'title']");


    public OrderPlacedPage(WebDriver driver) {
        super(driver);
    }

    public List<WebElement> getMyOrderedProductsBox() {
        waitForElementsToBeVisible(myOrderedProductsBox);
        return myOrderedProductsBox;
    }

    public boolean isProductInMyOrder(String productName) {
        return getMyOrderedProductsBox().stream()
                .anyMatch(p -> getText(p.findElement(myOrderProductTitle)).equalsIgnoreCase(productName)); // Check if any product title matches
    }

    public boolean isOrderSuccessful() {
        return getText(thankYouForYourOrderBox).equalsIgnoreCase("Thankyou for the order.");
    }

}
