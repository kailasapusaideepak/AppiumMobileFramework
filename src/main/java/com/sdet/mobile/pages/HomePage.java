package com.sdet.mobile.pages;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Page Object for ApiDemos home screen.
 * Demonstrates list interaction — a common mobile testing scenario.
 */
public class HomePage {

    private final AndroidDriver driver;
    private final WebDriverWait wait;

    // The main list of API categories on the home screen
    @AndroidFindBy(id = "android:id/text1")
    private List<WebElement> menuItems;

    // The title of the screen
    @AndroidFindBy(xpath = "//android.widget.TextView[@text='API Demos']")
    private WebElement pageTitle;

    public HomePage(AndroidDriver driver, int explicitWaitSeconds) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(explicitWaitSeconds));
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public boolean isHomePageDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(pageTitle));
            return pageTitle.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public int getMenuItemCount() {
        wait.until(ExpectedConditions.visibilityOfAllElements(menuItems));
        return menuItems.size();
    }

    public void clickMenuItemByText(String text) {
        wait.until(ExpectedConditions.visibilityOfAllElements(menuItems));
        menuItems.stream()
                .filter(item -> item.getText().equals(text))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Menu item not found: " + text))
                .click();
    }

    public String getFirstMenuItemText() {
        wait.until(ExpectedConditions.visibilityOfAllElements(menuItems));
        return menuItems.get(0).getText();
    }
}
