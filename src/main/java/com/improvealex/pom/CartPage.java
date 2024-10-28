package com.improvealex.pom;

import com.improvealex.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;
import java.util.List;

public class CartPage extends BasePage {

    private final String DASHBOARD_PAGE_URL = "https://rahulshettyacademy.com/client/dashboard/dash";

    @FindBy(xpath = "//div[@class='cart']/*")
    List<WebElement> myCartProductsBox;

    @FindBy(xpath = "//button[normalize-space()='Checkout']")
    WebElement checkoutButton;

    By myCartProductTitle = By.tagName("h3");
    By productStockStatus = By.xpath(".//p[@class ='stockStatus']");
    public CartPage(WebDriver driver) {
        super(driver);
    }

    public List<WebElement> getMyCartProductsBox() {
        waitForElementsToBeVisible(myCartProductsBox);
        return myCartProductsBox;
    }

    public boolean isProductInMyCart(String productName) {
        return getMyCartProductsBox().stream()
                .anyMatch(p -> getText(p.findElement(myCartProductTitle)).equalsIgnoreCase(productName)); // Check if any product title matches
    }
    public boolean isProductAvailable(String productName) {
        if (isProductInMyCart(productName)){
            return getMyCartProductsBox().stream()
                    .anyMatch(p -> getText(p.findElement(productStockStatus)).equalsIgnoreCase("In Stock")); // Check if any product title matches

        }
        else{
            return false;
        }
    }
    public void goToCheckout() {
        click(checkoutButton);
    }


    public void navigateToPage() {
        driver.get(DASHBOARD_PAGE_URL);
    }

}
