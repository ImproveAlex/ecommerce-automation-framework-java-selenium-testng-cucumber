package com.improvealex;
import com.improvealex.components.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;


public class TestLogin extends BaseTest {
    @Test(dataProvider = "loginTestCredentialsData")
    public void incorrectEmailOrPassword(Map<String, String> credentials) {
        if (credentials.get("expectedResult").equals("true")){
            Assert.assertTrue(loginSuccess(credentials.get("email"), credentials.get("password")));
        }
        else {
            Assert.assertTrue(loginFail(credentials.get("email"), credentials.get("password")));
        }
    }
    @Test
    public void happyLogin() {
        Assert.assertTrue(loginSuccess("lukeskywalker1@gmail.com","LukeSkywalker@1"));
    }
}
