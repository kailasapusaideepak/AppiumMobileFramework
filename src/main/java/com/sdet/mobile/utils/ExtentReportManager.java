package com.sdet.mobile.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

/**
 * Singleton Extent Reports manager.
 * Generates a rich HTML report in /reports/ExtentReport.html after each run.
 */
public class ExtentReportManager {

    private static ExtentReports extentReports;
    private static final ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    public static ExtentReports getInstance() {
        if (extentReports == null) {
            ExtentSparkReporter sparkReporter =
                    new ExtentSparkReporter("reports/ExtentReport.html");
            sparkReporter.config().setTheme(Theme.DARK);
            sparkReporter.config().setDocumentTitle("Mobile Automation Report");
            sparkReporter.config().setReportName("Appium Android Test Results");

            extentReports = new ExtentReports();
            extentReports.attachReporter(sparkReporter);
            extentReports.setSystemInfo("OS", System.getProperty("os.name"));
            extentReports.setSystemInfo("Java Version", System.getProperty("java.version"));
            extentReports.setSystemInfo("Device", ConfigReader.get("device.name"));
        }
        return extentReports;
    }

    public static ExtentTest getTest() {
        return extentTest.get();
    }

    public static void setTest(ExtentTest test) {
        extentTest.set(test);
    }
}
