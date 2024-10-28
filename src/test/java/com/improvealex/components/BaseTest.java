package com.improvealex.components;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.improvealex.pom.LandingPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
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

        String browser = System.getProperty("browser") != null ? System.getProperty("browser") : properties.getProperty("browser");
        boolean headless = Boolean.parseBoolean(System.getProperty("headless", properties.getProperty("headless", "false")));
        String windowSize = System.getProperty("windowSize", properties.getProperty("windowSize", "maximize"));
        WebDriver webDriver;

        switch (browser.toLowerCase()) {
            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();
                if (headless) chromeOptions.addArguments("--headless=new");
                if (!windowSize.equals("maximize")) {
                    chromeOptions.addArguments("--window-size=" + windowSize);
                }
                webDriver = new ChromeDriver(chromeOptions);
                break;

            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (headless) firefoxOptions.addArguments("--headless");
                if (!windowSize.equals("maximize")) {
                    firefoxOptions.addArguments("--width=" + windowSize.split(",")[0]);
                    firefoxOptions.addArguments("--height=" + windowSize.split(",")[1]);
                }
                webDriver = new FirefoxDriver(firefoxOptions);
                break;

            case "edge":
                EdgeOptions edgeOptions = new EdgeOptions();
                if (headless) edgeOptions.addArguments("--headless");
                if (!windowSize.equals("maximize")) {
                    edgeOptions.addArguments("--window-size=" + windowSize);
                }
                webDriver = new EdgeDriver(edgeOptions);
                break;

            default:
                ChromeOptions defaultOptions = new ChromeOptions();
                if (headless) defaultOptions.addArguments("--headless=new");
                if (!windowSize.equals("maximize")) {
                    defaultOptions.addArguments("--window-size=" + windowSize);
                }
                webDriver = new ChromeDriver(defaultOptions); // Default to Chrome if unrecognized
                break;
        }

        driver.set(webDriver); // Set the WebDriver instance for the current thread

        // Manage window based on the 'windowSize' configuration
        if (windowSize.equals("maximize")) {
            driver.get().manage().window().maximize();
        }

        // Add any additional setup if necessary, like setting a default URL
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
