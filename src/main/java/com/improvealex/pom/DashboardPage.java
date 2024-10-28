package com.improvealex.pom;

import com.improvealex.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import java.util.List;

public class DashboardPage extends BasePage {

    private final String DASHBOARD_PAGE_URL = "https://rahulshettyacademy.com/client/dashboard/dash";


    @FindBy(xpath = "//section[@id='products']//div[@class='container']//div[@class='row']/*")
    List<WebElement> productsBox;

    @FindBy(id = "toast-container")
    WebElement productAddedToCartDiv;

    @FindBy(tagName = "ngx-spinner")
    WebElement loadingSpinner;

    @FindBy(xpath = "//button[@routerlink='/dashboard/cart']")
    WebElement shoppingCartButton;

    By productTitle = By.tagName("b");
    By addCartButton = By.xpath(".//button[text()=' Add To Cart']");

    public DashboardPage(WebDriver driver) {
        super(driver);
    }

    public List<WebElement> getProductsBox() {
        waitForElementsToBeVisible(productsBox);
        return productsBox;
    }

    public WebElement getProductBoxByName(String productName) {
        return getProductsBox().stream()
                .filter(p -> getText(p.findElement(productTitle)).equalsIgnoreCase(productName)) //Filter if the product title matches
                .findFirst()
                .orElse(null);
    }

    public void addProductToCart(String productName) {
        click(getProductBoxByName(productName).findElement(addCartButton));
        waitProductAddedToCart();
        waitForSpinnerToDisappear();
    }

    public void waitForSpinnerToDisappear() {
        waitForElementToDisappear(loadingSpinner);
    }

    public void waitProductAddedToCart() {
        waitForElementToBeVisible(productAddedToCartDiv);
    }

    public void goToMyShoppingCart() {
        click(shoppingCartButton);
    }


    public void navigateToPage() {
        driver.get(DASHBOARD_PAGE_URL);
    }

}
