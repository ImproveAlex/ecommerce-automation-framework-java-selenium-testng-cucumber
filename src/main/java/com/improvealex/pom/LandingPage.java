package com.improvealex.pom;

import com.improvealex.base.BasePage;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LandingPage extends BasePage {
    private final String LANDING_PAGE_URL = "https://rahulshettyacademy.com/client/";
    // Page elements
    @FindBy(id = "userEmail")
    private WebElement userEmailInputBox;

    @FindBy(id = "userPassword")
    private WebElement userPasswordInputBox;

    @FindBy(id = "login")
    private WebElement loginButton;

    @FindBy (xpath = "//div[@aria-label = 'Incorrect email or password.']")
    private  WebElement incorrectEmailOrPasswordBox;

    public LandingPage(WebDriver driver) {
        super(driver); //Send driver to Parent class BasePage
    }

    public void enterEmail(String email) {
        sendKeys(userEmailInputBox, email);
    }

    public void enterPassword(String password) {
        sendKeys(userPasswordInputBox, password);
    }

    public void clickLoginButton() {
        click(loginButton);
    }

    public boolean isIncorrectEmailOrPasswordBox(){
        try {
            waitForElementToBeVisible(incorrectEmailOrPasswordBox);
            return getText(incorrectEmailOrPasswordBox).contains("Incorrect email or password.");
        } catch (TimeoutException e) {
            return false;
        }

    }

    public void navigateToLandingPage() {
        navigateTo(LANDING_PAGE_URL);
    }

    // Combined login method
    public void login(String email, String password) {
        navigateToLandingPage();
        enterEmail(email);
        enterPassword(password);
        clickLoginButton();
    }
}

