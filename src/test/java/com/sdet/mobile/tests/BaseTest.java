package com.sdet.mobile.tests;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.sdet.mobile.utils.ConfigReader;
import com.sdet.mobile.utils.ExtentReportManager;
import com.sdet.mobile.utils.ScreenshotUtil;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.time.Duration;

/**
 * All test classes extend this.
 * Handles: driver init, extent report lifecycle, screenshot on failure, teardown.
 */
public class BaseTest {

    protected AndroidDriver driver;

    @BeforeSuite
    public void initReport() {
        ExtentReportManager.getInstance();
    }

    @BeforeMethod
    public void setUp(Method method) throws MalformedURLException {
        // --- Extent Report: create test entry ---
        ExtentTest test = ExtentReportManager.getInstance()
                .createTest(method.getName());
        ExtentReportManager.setTest(test);

        // --- Appium Capabilities (Appium 2.x style — no DesiredCapabilities) ---
        UiAutomator2Options options = new UiAutomator2Options();
        options.setDeviceName(ConfigReader.get("device.name"));
        options.setPlatformName(ConfigReader.get("platform.name"));
        options.setAutomationName("UiAutomator2");

        // Resolve APK path relative to project root
        String appPath = Paths.get(ConfigReader.get("app.path"))
                .toAbsolutePath().toString();
        options.setApp(appPath);

        options.setAppPackage(ConfigReader.get("app.package"));
        options.setAppActivity(ConfigReader.get("app.activity"));
        options.setNewCommandTimeout(Duration.ofSeconds(60));
        options.setNoReset(false); // fresh state each test

        URL appiumUrl = new URL(ConfigReader.get("appium.server.url"));
        driver = new AndroidDriver(appiumUrl, options);

        driver.manage().timeouts()
                .implicitlyWait(Duration.ofSeconds(ConfigReader.getInt("implicit.wait.seconds")));

        ExtentReportManager.getTest().log(Status.INFO, "App launched on device: "
                + ConfigReader.get("device.name"));
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        ExtentTest test = ExtentReportManager.getTest();

        if (result.getStatus() == ITestResult.FAILURE) {
            // Screenshot on failure
            String screenshotPath = ScreenshotUtil.capture(driver, result.getName());
            if (screenshotPath != null && test != null) {
                test.fail("Test FAILED — screenshot: " + screenshotPath);
                test.fail(result.getThrowable());
            }
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            if (test != null) test.pass("Test PASSED");
        } else {
            if (test != null) test.skip("Test SKIPPED");
        }

        if (driver != null) {
            driver.quit();
        }
    }

    @AfterSuite
    public void flushReport() {
        ExtentReportManager.getInstance().flush();
        System.out.println("\n✅ Extent Report generated: reports/ExtentReport.html\n");
    }
}
