package com.improvealex.components;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ReportManager {

    // Method to initialize and return an ExtentReports object
    public static ExtentReports getReportObject() {
        // Define the path where the report will be saved
        String reportPath = System.getProperty("user.dir") + "/reports/index.html";

        // Set up the ExtentSparkReporter with the report path
        ExtentSparkReporter reporter = new ExtentSparkReporter(reportPath);
        reporter.config().setReportName("Ecommerce Automation Report");
        reporter.config().setDocumentTitle("TEST RESULTS");

        // Initialize ExtentReports and attach the reporter
        ExtentReports extent = new ExtentReports();
        extent.attachReporter(reporter);

        // Add system or environment information to the report
        extent.setSystemInfo("Tester", "ImproveEu");
        extent.setSystemInfo("Environment", "QA");

        return extent;
    }
}