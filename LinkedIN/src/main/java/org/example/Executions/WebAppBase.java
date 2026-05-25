package org.example.Executions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.Helpers.Window;
import org.example.config.DriverManager;
import org.example.config.Chrome;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.NoSuchWindowException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class WebAppBase {

    private WebAppBase() {
        throw new AssertionError("Instantiating utility class.");
    }

    private static final Logger LOGGER = LogManager.getLogger(WebAppBase.class);
    private static WebDriver driver;

    public static WebDriver getChromeDriver() {
        if (driver == null) {
            LOGGER.info("Initializing WebDriver");
            driver = DriverManager.getLocalDriver(new Chrome());
            return driver;
        }
        return driver;
    }

    public static void quitDriver() {
        if (driver != null) {
            LOGGER.info("Quiting Driver");
            driver.quit();
        }
    }

    public static Map<String, String> getCookies() {
        Map<String, String> cookieMap = new HashMap<>();
        Set<Cookie> cookies = getChromeDriver().manage().getCookies();
        for (Cookie cookie : cookies)
            cookieMap.put(cookie.getName(), cookie.getValue());
        return cookieMap;
    }

    public static void closeChildWindows() {
        long endTime = System.currentTimeMillis() + 20000; // 20 seconds
        while (System.currentTimeMillis() < endTime) {
            if (attemptToCloseChildWindows()) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException exception) {
                    throw new IllegalStateException("Thread was interrupted while closing child windows", exception);
                }
                refocusMainWindow();
            }
        }
    }

    private static boolean attemptToCloseChildWindows() {
        Set<String> windowHandles = Window.getWindowHandles(driver);
        boolean closedAny = false;
        if (WebApp.mainWindow == null) {
            return false;
        }
        for (String handle : windowHandles) {
            if (handle.equalsIgnoreCase(WebApp.mainWindow)) continue;
            closedAny |= closeWindow(handle);
        }
        return closedAny;
    }

    private static boolean closeWindow(String handle) {
        try {
            driver.switchTo().window(handle);
            driver.close();
            LOGGER.info("Closed child window: {}", handle);
            return true;
        } catch (NoSuchWindowException e) {
            LOGGER.warn("Window already closed or not found: {}", handle);
        } catch (Exception e) {
            LOGGER.error("Error closing window: {} - {}", handle, e.getMessage());
        }
        return false;
    }

    private static void refocusMainWindow() {
        try {
            Window.focusWindow(driver, WebApp.mainWindow);
        } catch (NoSuchWindowException e) {
            LOGGER.warn("Main window not found when refocusing. Setting mainWindow to null.");
            WebApp.mainWindow = null;
        }
    }

    public static void focusMainScreen() {
        Window.focusWindow(driver, WebApp.mainWindow);
    }
}
