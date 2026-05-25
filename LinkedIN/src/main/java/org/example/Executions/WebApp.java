package org.example.Executions;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import java.lang.reflect.Constructor;
import org.example.Helpers.Window;
import org.example.Helpers.Alert;
import org.example.pageObjects.Pages;

public class WebApp {
    private TestConfiguration config;
    protected static String mainWindow;
    private String secondaryWindow;

    public WebApp(TestConfiguration config) {
        this.config = config;
    }

    public <T> Object openPage(Pages pageName, Class<T> type) {
        return getPage(pageName, type);
    }

    public <T> Object openFilteredPage(String pageName, Class<T> type) {
        return getFilteredPage(pageName, type);
    }

    public <T> Object getPage(Pages pageName, Class<T> type) {
        WebDriver driver = WebAppBase.getChromeDriver();
        Object object = null;
        if (mainWindow == null) {
            mainWindow = driver.getWindowHandle();
        } else {
            Window.focusWindow(driver, WebApp.mainWindow);
        }
        try {
            Class<?> aClass = Class.forName(type.getName());
            Constructor<?> constructor = aClass.getConstructor(WebDriver.class);
            driver.get(config.getBaseUri() + pageName.toString());
            Alert.acceptIfAlert(driver);
            object = constructor.newInstance(driver);
        } catch (Exception e) {
            Assert.fail("Test case terminated because of: " + e.getMessage());
        }
        return object;
    }

    public <T> Object getFilteredPage(String pageName, Class<T> type) {
        WebDriver driver = WebAppBase.getChromeDriver();
        Object object = null;
        if (mainWindow == null) {
            mainWindow = driver.getWindowHandle();
        } else {
            Window.focusWindow(driver, WebApp.mainWindow);
        }
        try {
            Class<?> aClass = Class.forName(type.getName());
            Constructor<?> constructor = aClass.getConstructor(WebDriver.class);
            driver.get(config.getBaseUri() + pageName);
            Alert.acceptIfAlert(driver);
            object = constructor.newInstance(driver);
        } catch (Exception e) {
            Assert.fail("Test case terminated because of: " + e.getMessage());
        }
        return object;
    }
}
