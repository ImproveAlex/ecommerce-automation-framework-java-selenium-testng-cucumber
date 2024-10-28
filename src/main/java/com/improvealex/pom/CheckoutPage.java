package com.improvealex.pom;

import com.improvealex.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class CheckoutPage extends BasePage {

    @FindBy(xpath = "//div[@class='col-md-5']/*")
    List<WebElement> myCheckboxProductsBox;

    @FindBy(xpath = "//input[@placeholder='Select Country']")
    WebElement selectCountryInput;

    @FindBy(xpath = "//section[@class='ta-results list-group ng-star-inserted']//button")
    List<WebElement> listFoundCounties;

    @FindBy(xpath = "//a[@class='btnn action__submit ng-star-inserted']")
    WebElement placeOrderButton;

    @FindBy (xpath = "//input[@name='coupon']")
    WebElement couponInputBox;

    @FindBy (xpath = "//button[normalize-space()='Apply Coupon']")
    WebElement applyCouponButton;

    @FindBy (css = ".mt-1.ng-star-inserted")
    WebElement couponAppliedBox;

    @FindBy(tagName = "ngx-spinner")
    WebElement loadingSpinner;

    By myCheckoutProductTitle = By.xpath(".//div[@class = 'item__title']");

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public List<WebElement> getMyCheckoutProductsBox() {
        waitForElementsToBeVisible(myCheckboxProductsBox);
        return myCheckboxProductsBox;
    }

    public boolean isProductInMyCheckout(String productName) {
        return getMyCheckoutProductsBox().stream()
                .anyMatch(p -> getText(p.findElement(myCheckoutProductTitle)).equalsIgnoreCase(productName)); // Check if any product title matches
    }

    public void waitForSpinnerToDisappear() {
        waitForElementToDisappear(loadingSpinner);
    }

    public boolean addCoupon(String coupon){
        sendKeys(couponInputBox, coupon);
        click(applyCouponButton);
        waitForSpinnerToDisappear();
        waitForElementToBeVisible(couponAppliedBox);
        return getText(couponAppliedBox).equalsIgnoreCase("* Coupon Applied");
    }

    public void setSelectCountry (String country){
        sendKeys(selectCountryInput, country);
        waitForElementsToBeVisible(listFoundCounties);
        WebElement myCounty = listFoundCounties.stream()
                .filter(w-> getText(w.findElement(By.tagName("span"))).contains(country))
                .map(w->w.findElement(By.tagName("span")))
                .findFirst()
                .orElse(null);
        click(myCounty);
    }

    public void clickPlaceOrderButton(){
        click(placeOrderButton);
    }



}
