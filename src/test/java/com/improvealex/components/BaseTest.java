package com.improvealex.components;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.improvealex.pom.LandingPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Properties;



public class BaseTest {
    protected static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    protected Properties properties;

    @BeforeMethod
    public void setUp() throws IOException {
        properties = new Properties();
        FileInputStream inputFile = new FileInputStream("src/main/java/com/improvealex/resources/config.properties");
        properties.load(inputFile);

        String browser = System.getProperty("browser")!= null ? System.getProperty("browser") : properties.getProperty("browser");
        WebDriver webDriver;

        switch (browser.toLowerCase()) {
            case "chrome":
                webDriver = new ChromeDriver();
                break;
            case "firefox":
                webDriver = new FirefoxDriver();
                break;
            case "edge":
                webDriver = new EdgeDriver();
                break;
            default:
                webDriver = new ChromeDriver(); // Default to Chrome if unrecognized
                break;
        }

        driver.set(webDriver); // Set the WebDriver instance for the current thread
        driver.get().manage().window().maximize(); // Maximize the browser window
    }

    @AfterMethod
    public void tearDown() {
        // Close the browser after each test
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove(); // Remove the WebDriver instance from ThreadLocal
        }
    }

    @DataProvider(name = "loginTestCredentialsData")
    public Object[][] loginTestCredentialsData() throws IOException {
        return parseJsonAsMap("C:\\Users\\Alex\\Documents\\Udemy-selenium-java\\ecommerce-automation-framework-java-selenium-testng-cucumber\\src\\test\\java\\com\\improvealex\\resources\\loginTestCredentialsData.json");
    }

    @DataProvider(name = "buyOneProductData")
    public Object[][] buyOneProductData() throws IOException {
        return parseJsonAsMap("C:\\Users\\Alex\\Documents\\Udemy-selenium-java\\ecommerce-automation-framework-java-selenium-testng-cucumber\\src\\test\\java\\com\\improvealex\\resources\\buyOneProductData.json");
    }

    protected Object[][] parseJsonAsMap(String filePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        // Load the JSON file from the specified file path
        File jsonFile = new File(filePath);
        if (!jsonFile.exists()) {
            throw new FileNotFoundException("Resource not found: " + filePath);
        }

        // Parse JSON as a list of maps
        List<Map<String, Object>> dataList = mapper.readValue(jsonFile, new TypeReference<List<Map<String, Object>>>() {});

        // Convert List<Map> to Object[][]
        Object[][] data = new Object[dataList.size()][1];
        for (int i = 0; i < dataList.size(); i++) {
            data[i][0] = dataList.get(i);
        }
        return data;
    }



    // Full login logic
    protected boolean loginSuccess(String email, String password) {
        try {
            LandingPage landingPage = new LandingPage(driver.get());
            landingPage.login(email, password);
            String initialUrl = landingPage.getCurrentUrl();
            if (landingPage.waitForUrlToChange(initialUrl)) {
                return true;
            }
            else return false;

        } catch (Exception e) {
            System.out.println("here");
            System.out.println(e);
            return false;
        }
    }
    protected boolean loginFail(String email, String password) {
        try {
            LandingPage landingPage = new LandingPage(driver.get());
            landingPage.login(email, password);
            return landingPage.isIncorrectEmailOrPasswordBox();

        } catch (Exception e) {
            return false;
        }
    }

    // Method to get the current WebDriver
    public static WebDriver getDriver() {
        return driver.get();
    }
}
