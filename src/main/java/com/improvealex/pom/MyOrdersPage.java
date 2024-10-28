package com.improvealex.pom;

import com.improvealex.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class MyOrdersPage extends BasePage {

    @FindBy(xpath = "//tbody/*")
    List<WebElement> myOrderedProductsBox;

    By myOrderProductTitle = By.xpath(".//td[2]");


    public MyOrdersPage(WebDriver driver) {
        super(driver);
    }

    public List<WebElement> getMyOrderedProductsBox() {
        waitForElementsToBeVisible(myOrderedProductsBox);
        return myOrderedProductsBox;
    }

    public boolean isProductInMyOrders(String productName) {

        return getMyOrderedProductsBox().stream()
                .anyMatch(p -> getText(p.findElement(myOrderProductTitle)).equalsIgnoreCase(productName)); // Check if any product title matches
    }

    /*public boolean isOrderSuccessful() {
        return getText(thankYouForYourOrderBox).equalsIgnoreCase("Thankyou for the order.");
    }

     */

}
