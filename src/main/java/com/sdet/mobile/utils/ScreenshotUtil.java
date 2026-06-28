package com.sdet.mobile.utils;

import io.appium.java_client.android.AndroidDriver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Captures screenshots on test failure.
 * Saved to /screenshots/<TestName>_<timestamp>.png
 */
public class ScreenshotUtil {

    private static final String SCREENSHOT_DIR = "screenshots/";
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

    public static String capture(AndroidDriver driver, String testName) {
        String timestamp = LocalDateTime.now().format(FORMATTER);
        String fileName = SCREENSHOT_DIR + testName + "_" + timestamp + ".png";

        try {
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File destFile = new File(fileName);
            FileUtils.copyFile(srcFile, destFile);
            System.out.println("Screenshot saved: " + fileName);
            return destFile.getAbsolutePath();
        } catch (IOException e) {
            System.err.println("Failed to capture screenshot: " + e.getMessage());
            return null;
        }
    }
}
