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
 * Page Object for the Views sub-menu in ApiDemos.
 * Used for scroll and swipe tests — demonstrates gesture handling.
 */
public class ViewsPage {

    private final AndroidDriver driver;
    private final WebDriverWait wait;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Views']")
    private WebElement viewsHeader;

    @AndroidFindBy(id = "android:id/text1")
    private List<WebElement> viewItems;

    public ViewsPage(AndroidDriver driver, int explicitWaitSeconds) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(explicitWaitSeconds));
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public boolean isViewsPageLoaded() {
        try {
            wait.until(ExpectedConditions.visibilityOf(viewsHeader));
            return viewsHeader.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isItemVisibleInList(String itemText) {
        return viewItems.stream()
                .anyMatch(item -> item.getText().equals(itemText));
    }
}
