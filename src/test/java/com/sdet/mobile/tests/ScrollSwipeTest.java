package com.sdet.mobile.tests;

import com.aventstack.extentreports.Status;
import com.sdet.mobile.pages.HomePage;
import com.sdet.mobile.utils.ConfigReader;
import com.sdet.mobile.utils.ExtentReportManager;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

/**
 * Demonstrates scroll/swipe using W3C Actions API.
 *
 * WHY THIS MATTERS FOR INTERVIEWS:
 * Appium 2.x deprecated TouchAction and MultiTouchAction.
 * Senior SDETs use W3C Actions — this test proves you know the difference.
 */
public class ScrollSwipeTest extends BaseTest {

    @Test(description = "Scroll down on home screen using W3C Actions API")
    public void testScrollDownOnHomeScreen() {
        ExtentReportManager.getTest().log(Status.INFO,
                "Performing scroll down using W3C Actions API");

        HomePage homePage = new HomePage(driver,
                ConfigReader.getInt("explicit.wait.seconds"));

        Assert.assertTrue(homePage.isHomePageDisplayed(),
                "Home page should be visible before scroll");

        // Get screen dimensions
        Dimension size = driver.manage().window().getSize();
        int screenWidth  = size.getWidth();
        int screenHeight = size.getHeight();

        // Define scroll: start at 70% height, end at 30% height (scroll down)
        int startX = screenWidth / 2;
        int startY = (int) (screenHeight * 0.70);
        int endY   = (int) (screenHeight * 0.30);

        // W3C Actions — the correct Appium 2.x approach
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 1)
                .addAction(finger.createPointerMove(Duration.ZERO,
                        PointerInput.Origin.viewport(), startX, startY))
                .addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(finger.createPointerMove(Duration.ofMillis(800),
                        PointerInput.Origin.viewport(), startX, endY))
                .addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(List.of(swipe));

        ExtentReportManager.getTest().log(Status.INFO,
                "Scroll performed from y=" + startY + " to y=" + endY);

        // Verify app didn't crash after scroll
        Assert.assertNotNull(driver.currentActivity(),
                "App crashed or became unresponsive after scroll");

        ExtentReportManager.getTest().pass(
                "Scroll completed successfully — app still responsive");
    }

    @Test(description = "Swipe left gesture simulation")
    public void testSwipeLeft() {
        ExtentReportManager.getTest().log(Status.INFO,
                "Performing swipe left using W3C Actions API");

        Dimension size = driver.manage().window().getSize();
        int screenWidth  = size.getWidth();
        int screenHeight = size.getHeight();

        int startX = (int) (screenWidth * 0.80);
        int endX   = (int) (screenWidth * 0.20);
        int midY   = screenHeight / 2;

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipeLeft = new Sequence(finger, 1)
                .addAction(finger.createPointerMove(Duration.ZERO,
                        PointerInput.Origin.viewport(), startX, midY))
                .addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(finger.createPointerMove(Duration.ofMillis(600),
                        PointerInput.Origin.viewport(), endX, midY))
                .addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(List.of(swipeLeft));

        ExtentReportManager.getTest().log(Status.INFO,
                "Swipe left performed from x=" + startX + " to x=" + endX);

        Assert.assertNotNull(driver.currentActivity(),
                "App became unresponsive after swipe left");

        ExtentReportManager.getTest().pass("Swipe left completed successfully");
    }
}
