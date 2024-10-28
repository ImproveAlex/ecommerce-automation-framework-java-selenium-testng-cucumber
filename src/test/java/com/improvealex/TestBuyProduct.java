package com.improvealex;


import com.improvealex.pom.*;
import com.improvealex.components.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;


public class TestBuyProduct extends BaseTest {

    @Test (dataProvider = "buyOneProductData")
    public void testBuyOneProduct(Map<String, String> credentials) {

        Assert.assertTrue(loginSuccess(credentials.get("email"), credentials.get("password")));
        //Dashboard page logic
        DashboardPage dashboardPage = new DashboardPage(driver.get());
        dashboardPage.addProductToCart(credentials.get("productName"));
        dashboardPage.goToMyShoppingCart();

        //Cart page Logic
        CartPage cartPage = new CartPage(driver.get());
        Assert.assertTrue(cartPage.isProductInMyCart(credentials.get("productName")));
        Assert.assertTrue(cartPage.isProductAvailable(credentials.get("productName")));
        String carturl = cartPage.getCurrentUrl();
        cartPage.goToCheckout();
        cartPage.waitForUrlToChange(carturl);

        //Checkout page logic
        CheckoutPage checkoutPage = new CheckoutPage(driver.get());
        Assert.assertTrue(checkoutPage.isProductInMyCheckout(credentials.get("productName")));
        Assert.assertTrue(checkoutPage.addCoupon("rahulshettyacademy"));
        checkoutPage.setSelectCountry("Spain");
        String checkoutUrl = checkoutPage.getCurrentUrl();
        checkoutPage.clickPlaceOrderButton();
        checkoutPage.waitForUrlToChange(checkoutUrl);

        //Order placed page logic
        OrderPlacedPage orderPlacedPage = new OrderPlacedPage(driver.get());
        Assert.assertTrue(orderPlacedPage.isProductInMyOrder(credentials.get("productName")));
        Assert.assertTrue(orderPlacedPage.isOrderSuccessful());
    }

    @Test (dataProvider = "buyOneProductData", dependsOnMethods = {"testBuyOneProduct"})
    public void validateOneProductHasOrderPlaced(Map<String, String> credentials){
        Assert.assertTrue(loginSuccess(credentials.get("email"), credentials.get("password")));
        MyOrdersPage myOrdersPage = new MyOrdersPage(driver.get());
        myOrdersPage.goToOrders();
        Assert.assertTrue(myOrdersPage.isProductInMyOrders(credentials.get("productName")));

    }

}
