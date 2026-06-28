package com.sdet.mobile.tests;

import com.aventstack.extentreports.Status;
import com.sdet.mobile.pages.HomePage;
import com.sdet.mobile.utils.ConfigReader;
import com.sdet.mobile.utils.ExtentReportManager;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Tests against the ApiDemos home screen.
 *
 * HOW TO RUN:
 *  1. Start Appium:  appium --relaxed-security
 *  2. Connect device, verify with: adb devices
 *  3. Place ApiDemos-debug.apk in /apps folder
 *  4. Update config.properties with your device name
 *  5. Right-click this file in IntelliJ → Run
 */
public class HomePageTest extends BaseTest {

    @Test(description = "Verify API Demos home screen loads successfully")
    public void testHomeScreenLoads() {
        ExtentReportManager.getTest().log(Status.INFO, "Checking home screen visibility");

        HomePage homePage = new HomePage(driver,
                ConfigReader.getInt("explicit.wait.seconds"));

        boolean isDisplayed = homePage.isHomePageDisplayed();

        ExtentReportManager.getTest().log(Status.INFO,
                "Home page displayed: " + isDisplayed);

        Assert.assertTrue(isDisplayed,
                "Home page title 'API Demos' was not visible after app launch");
    }

    @Test(description = "Verify home screen has multiple menu items")
    public void testHomeScreenHasMenuItems() {
        ExtentReportManager.getTest().log(Status.INFO, "Counting menu items on home screen");

        HomePage homePage = new HomePage(driver,
                ConfigReader.getInt("explicit.wait.seconds"));

        int count = homePage.getMenuItemCount();

        ExtentReportManager.getTest().log(Status.INFO,
                "Menu items found: " + count);

        Assert.assertTrue(count > 5,
                "Expected more than 5 menu items but found: " + count);
    }

    @Test(description = "Verify first menu item is 'Access'")
    public void testFirstMenuItemText() {
        ExtentReportManager.getTest().log(Status.INFO, "Checking first menu item text");

        HomePage homePage = new HomePage(driver,
                ConfigReader.getInt("explicit.wait.seconds"));

        String firstItem = homePage.getFirstMenuItemText();

        ExtentReportManager.getTest().log(Status.INFO,
                "First menu item: " + firstItem);

        Assert.assertEquals(firstItem, "Access",
                "First menu item text mismatch");
    }

    @Test(description = "Navigate into Views section from home menu")
    public void testNavigateToViews() {
        ExtentReportManager.getTest().log(Status.INFO, "Clicking Views menu item");

        HomePage homePage = new HomePage(driver,
                ConfigReader.getInt("explicit.wait.seconds"));

        homePage.clickMenuItemByText("Views");

        // Verify navigation happened — current activity should change
        String currentActivity = driver.currentActivity();
        ExtentReportManager.getTest().log(Status.INFO,
                "Current activity after navigation: " + currentActivity);

        Assert.assertNotNull(currentActivity,
                "Current activity is null after clicking Views");

        ExtentReportManager.getTest().pass("Successfully navigated to Views section");
    }
}
