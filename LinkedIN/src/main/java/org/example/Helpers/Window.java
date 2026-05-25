package org.example.Helpers;



import lombok.experimental.UtilityClass;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class Window {

    private static final String NEW_TAB = "window.open();";

    public static void refresh(WebDriver driver) {
        driver.navigate().refresh();
        Wait.pageToLoad(driver);
    }

    public static void navigateTo(WebDriver driver, String uri) {
        driver.navigate().to(uri);
        Wait.pageToLoad(driver);
    }

    public static String openNewTab(WebDriver driver) {
        JavaScriptExecutor.execute(driver, NEW_TAB);
        return switchToLastWindow(driver);
    }

    public static void focusWindow(WebDriver driver, String windowName) {
        driver.switchTo().window(windowName);
    }

    public static void closeCurrentWindow(WebDriver driver) {
        driver.close();
    }

    public static void switchToChildWindow(WebDriver driver) {
        switchToLastWindow(driver);
    }

    public static void switchToParentWindow(WebDriver driver) {
        switchToLastWindow(driver);
    }

    public static String switchToLastWindow(WebDriver driver) {
        Set<String> windowHandles =  getWindowHandles(driver);
        List<String> windows =  windowHandles.stream().collect(Collectors.toList());
        String childWindow = windows.get(windowHandles.size() - 1);
        driver.switchTo().window(childWindow);
        Wait.pageToLoad(driver);
        return childWindow;
    }

    public static Set<String> getWindowHandles(WebDriver driver) {
        return driver.getWindowHandles();
    }

    public static String getWindowHandle(WebDriver driver) {
        return driver.getWindowHandle();
    }

    public static void setWindowHandle(WebDriver driver, String handle) {
        driver.switchTo().window(handle);
    }

    public static void navigateBack(WebDriver driver) {
        driver.navigate().back();
        Wait.pageToLoad(driver);
    }

    public static void setWindowSize(WebDriver driver, int width, int height) {
        Dimension dimension = new Dimension(width, height);
        driver.manage().window().setSize(dimension);
    }

    public static void maximizeWindow(WebDriver driver) {
        driver.manage().window().maximize();
    }
}
