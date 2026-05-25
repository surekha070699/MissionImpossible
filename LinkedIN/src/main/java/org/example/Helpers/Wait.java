package org.example.Helpers;



import lombok.experimental.UtilityClass;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.constants.TimeOut;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.example.Helpers.SeleniumHelper.findElement;
import static org.example.Helpers.SeleniumHelper.waitAndClick;
import static org.example.constants.TimeOut.DEFAULT_POLL_TIMEOUT_MS;

@UtilityClass
public class Wait {

    private static final Logger LOGGER = LogManager.getLogger(Wait.class);

    private final String RELOADING_PAGE_UNTIL_PRESENCE_OF_ELEMENT_MESSAGE = "Reloading page until the presence of element : %s, ";
    private final String WAITING_FOR_LOCATOR_MESSAGE = "Waiting for locator: %s, attribute: %s, not to be null";

    public static void wait(WebDriver driver, ExpectedCondition<?> expectedCondition) {
        getWebDriverWait(driver).until(expectedCondition);
    }

    public static void wait(WebDriver driver, int timeOutInSeconds, int pollTimeInMilliSeconds, ExpectedCondition<?> expectedCondition) {
        getWebDriverWait(driver, timeOutInSeconds, pollTimeInMilliSeconds).until(expectedCondition);
    }

    private static ExpectedCondition<Boolean> pageReady() {
        return driver -> {
            boolean result = false;
            String readyState = "";
            try {
                readyState = (String) JavaScriptExecutor.execute(driver, "return document.readyState;");
            } catch (WebDriverException e) {
                LOGGER.info(String.format("Caught: %s", e));
            }

            if ("complete".equals(readyState)) {
                result = true;
            }
            return result;
        };
    }

    public static void reloadUntilPresenceOfElement(WebDriver driver, final By tabNameLocator, final By locator) {
        LOGGER.info(String.format(RELOADING_PAGE_UNTIL_PRESENCE_OF_ELEMENT_MESSAGE, locator));
        getWebDriverWait(driver).until((ExpectedCondition<Object>) input -> {
            boolean flag = SeleniumHelper.isElementPresent(driver, locator);
            if (!flag)
                Window.refresh(driver);
            Alert.acceptIfAlert(driver);
            waitAndClick(driver, tabNameLocator);
            return flag;
        });
    }

    public static void reloadUntilPresenceOfElement(WebDriver driver, final By locator, int timeoutInSeconds) {
        LOGGER.info("Reloading page until the presence of element : {} till: {} ", locator, timeoutInSeconds);
        getWebDriverWait(driver, timeoutInSeconds, DEFAULT_POLL_TIMEOUT_MS).until((ExpectedCondition<Object>) input -> {
            boolean flag = SeleniumHelper.isElementPresent(driver, locator);
            if (!flag)
                Window.refresh(driver);
            return flag;
        });
    }

    public static WebDriverWait getWebDriverWait(WebDriver driver) {
        return new WebDriverWait(driver, Duration.ofSeconds(TimeOut.DEFAULT_NAVIGATION_TIMEOUT_SEC), Duration.ofMillis(TimeOut.DEFAULT_POLL_TIMEOUT_MS));
    }

    public static WebDriverWait getWebDriverWait(WebDriver driver, int timeOutInSeconds, int pollTimeInMilliSeconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(timeOutInSeconds), Duration.ofMillis(pollTimeInMilliSeconds));
    }

    public static void pageToLoad(WebDriver driver) {
        getWebDriverWait(driver, 600, 60000).until(pageReady());
    }

    public static WebElement elementToBeClickable(WebDriver driver, final By locator) {
        LOGGER.info("Waiting for locator: {}, to be clickable ...", locator);
        return getWebDriverWait(driver).until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static WebElement elementToBeClickableAfterCustomTimeout(WebDriver driver, final By locator, int timeoutInSeconds) {
        LOGGER.info("Waiting for locator: {}, to be clickable ...", locator);
        return getWebDriverWait(driver, timeoutInSeconds, DEFAULT_POLL_TIMEOUT_MS).until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static WebElement visibilityOfElementLocated(WebDriver driver, final By locator) {
        LOGGER.info("Waiting for locator: {}, visibility ...", locator);
        return getWebDriverWait(driver).until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static WebElement visibilityOfElementLocated(WebDriver driver, WebElement element) {
        LOGGER.info("Waiting for WebElement: {}, visibility ...", element);
        return getWebDriverWait(driver).until(ExpectedConditions.visibilityOf(element));
    }

    public static WebElement presenceOfElementLocated(WebDriver driver, final By locator) {
        LOGGER.info("Waiting for presence of locator:  {} ...", locator);
        return getWebDriverWait(driver).until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public static Boolean refreshOfElementLocated(WebDriver driver, final WebElement locator) {
        LOGGER.info("Waiting for presence of locator:  {} ...", locator);
        return getWebDriverWait(driver).until(ExpectedConditions.refreshed(ExpectedConditions.stalenessOf(locator)));
    }

    public static void textToBePresentInElementLocated(WebDriver driver, final By locator, final String text) {
        LOGGER.info("Waiting for the text to br present in the locator: {} ...", locator);
        getWebDriverWait(driver).until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
    }

    public static void invisibilityOfElementWithText(WebDriver driver, final By locator, final String text) {
        LOGGER.info("Waiting for the text to be invisible in the locator {} ...", locator);
        getWebDriverWait(driver).until(ExpectedConditions.invisibilityOfElementWithText(locator, text));
    }

    public static void invisibilityOfElement(WebDriver driver, final By locator) {
        LOGGER.info("Waiting for the text to be invisible in the locator {} ...", locator);
        getWebDriverWait(driver).until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public static void attributeToBe(WebDriver driver, final By locator, final String attribute, final String value) {
        LOGGER.info("Waiting for locator: {}, attribute: {}, value to be: {} ...", locator, attribute, value);
        getWebDriverWait(driver).until(ExpectedConditions.attributeToBe(locator, attribute, value));
    }

    public static void attributeToBe(WebDriver driver, WebElement element, final String attribute, final String value) {
        LOGGER.info("Waiting for locator: {}, attribute: {}, value to be: {} ...", element, attribute, value);
        getWebDriverWait(driver).until(ExpectedConditions.attributeToBe(element, attribute, value));
    }

    public static void waitForAlert(WebDriver driver) {
        LOGGER.info("Waiting for Alert");
        getWebDriverWait(driver).until(ExpectedConditions.alertIsPresent());
    }

    public static void waitForElementToBeSelected(WebDriver driver, final By locator, final String value) {
        LOGGER.info("Waiting for locator: {}, value to be selected: {} ...", locator, value);
        getWebDriverWait(driver).until(ExpectedConditions.textToBe(locator, value));
    }

    public static void attributeContains(WebDriver driver, final By locator, final String attribute, final String value) {
        LOGGER.info("Waiting for locator: {}, attribute: {}, to contain value: {} ...", locator, attribute, value);
        getWebDriverWait(driver).until(ExpectedConditions.attributeContains(locator, attribute, value));
    }

    public static boolean isElementPresent(WebDriver driver, final By locator) {
        LOGGER.info("Checking for the presence of locator {}", locator);
        try {
            getWebDriverWait(driver).until((ExpectedCondition<Object>) input -> {
                try {
                    findElement(driver, locator).isDisplayed();
                } catch (StaleElementReferenceException | NoSuchElementException e) {
                    return false;
                }
                return true;
            });
        } catch (TimeoutException e) {
            return false;
        }
        return true;
    }

    public static void attributeNotToBeNull(WebDriver driver, final By locator, final String attribute) {
        LOGGER.info(String.format(WAITING_FOR_LOCATOR_MESSAGE, locator, attribute));
        getWebDriverWait(driver).until((ExpectedCondition<Object>) input -> !(SeleniumHelper.getAttribute(driver, locator, attribute).isEmpty()));
    }

    public static void attributeNotToBeNull(WebDriver driver, int timeOutInSeconds, int pollTimeInMilliSeconds, final By locator, final String attribute) {
        LOGGER.info(String.format(WAITING_FOR_LOCATOR_MESSAGE, locator, attribute));
        getWebDriverWait(driver, timeOutInSeconds, pollTimeInMilliSeconds).until((ExpectedCondition<Object>) input -> !(SeleniumHelper.getAttribute(driver, locator, attribute).isEmpty()));
    }

    public static void verifyAttributeIsNotNull(WebDriver driver, final By locator, final String attribute) {
        LOGGER.info(String.format(WAITING_FOR_LOCATOR_MESSAGE, locator, attribute));
        getWebDriverWait(driver).until((ExpectedCondition<Object>) input -> (SeleniumHelper.getAttribute(driver, locator, attribute) != null));
    }

    public static void reloadUntilPresenceOfElement(WebDriver driver, final By locator) {
        LOGGER.info(String.format(RELOADING_PAGE_UNTIL_PRESENCE_OF_ELEMENT_MESSAGE, locator));
        getWebDriverWait(driver).until((ExpectedCondition<Object>) input -> {
            boolean flag = SeleniumHelper.isElementPresent(driver, locator);
            if (!flag)
                Window.refresh(driver);
            return flag;
        });
    }

    public static void reloadUntilVisibilityOfElement(WebDriver driver, final By locator) {
        LOGGER.info("Reloading page until the visibility of element : {}, ", locator);
        getWebDriverWait(driver).until((ExpectedCondition<Object>) input -> {
            boolean isVisible = SeleniumHelper.isElementVisible(driver, locator);
            if (!isVisible)
                Window.refresh(driver);
            return isVisible;
        });
    }

    public static void waitForSlushBucketToLoad(WebDriver driver, Select dropdown) {
        getWebDriverWait(driver).until((ExpectedCondition<Object>) input -> {
            String firstOption = dropdown.getOptions().get(0).getText();
            if (firstOption.equals("--None--") || firstOption.equals("Loading") || firstOption.isEmpty())
                return false;
            else
                LOGGER.info("Waiting for slush bucket to Load...");
            return true;
        });
    }

    public static void waitForSlushBucketToPopulate(WebDriver driver, Select dropdown) {
        getWebDriverWait(driver).until((ExpectedCondition<Object>) input -> {
            List<WebElement> firstOption = dropdown.getOptions();
            if (firstOption.isEmpty() || firstOption.get(0).getText().equals("--None--") || firstOption.get(0).getText().equals("Loading") || firstOption.get(0).getText().isEmpty())
                return false;
            else
                LOGGER.info("Waiting until the slush bucket populating...");
            return true;
        });
    }

    public static void reloadUntilTextTobe(WebDriver driver, final By locator, String expectedText) {
        LOGGER.info(String.format(RELOADING_PAGE_UNTIL_PRESENCE_OF_ELEMENT_MESSAGE, locator));
        getWebDriverWait(driver).until((ExpectedCondition<Object>) input -> {
            boolean flag = false;
            if (findElement(driver, locator).getText().equals(expectedText))
                flag = true;
            else
                Window.refresh(driver);
            return flag;
        });
    }

    public static void windowHandleCountToBe(WebDriver driver, int limit) {
        LOGGER.info("Waiting for window count to be : {}, ", limit);
        getWebDriverWait(driver).until((ExpectedCondition<Object>) input -> Window.getWindowHandles(driver).size() == limit);
    }

    public static void tillDropDownContains(WebDriver driver, final By locator, String option) {
        LOGGER.info("Waiting for the dropdown to load");
        getWebDriverWait(driver).until((ExpectedCondition<Object>) input -> DropDown.doesContains(driver, locator, option));
    }

    public static void tillDropDownSizeIsGreaterThan(WebDriver driver, final By locator, int size) {
        LOGGER.info("Waiting for the dropdown to have size greater than : {}", size);
        getWebDriverWait(driver).until((ExpectedCondition<Object>) input -> DropDown.getDropDownListSize(driver, locator) > size);
    }


    public static void reloadTillDropDownVisibleTextToBe(WebDriver driver, final By locator, String visibleText) {
        LOGGER.info("Waiting for the dropdown value to be: {}", visibleText);
        getWebDriverWait(driver).until((ExpectedCondition<Object>) input -> {
            if (DropDown.getSelectedValue(driver, locator).equals(visibleText)) {
                return true;
            } else {
                Window.refresh(driver);
                return false;
            }
        });
    }

    public static void reloadUntilElementValueTobe(WebDriver driver, final By locator, String expectedText) {
        LOGGER.info("Reloading page until locator [{}] value to be [{}]", locator, expectedText);
        Wait.getWebDriverWait(driver, TimeOut.MAX_TIMEOUT_IN_SEC, DEFAULT_POLL_TIMEOUT_MS).until((ExpectedCondition<Object>) input -> {
            if (JavaScriptExecutor.getElementValue(driver, findElement(driver, locator)).equals(expectedText))
                return true;
            else
                Window.refresh(driver);
            return false;
        });
    }

    public static void reloadUntilDropdownValueTobe(WebDriver driver, final By locator, String expectedText) {
        LOGGER.info("Reloading page until dropdown locator [{}] value to be [{}]", locator, expectedText);
        getWebDriverWait(driver).until((ExpectedCondition<Object>) input -> {
            if (JavaScriptExecutor.getElementValue(driver, findElement(driver, locator)).equals(expectedText))
                return true;
            else
                Window.refresh(driver);
            return false;
        });
    }

    public static void reloadUntilTextContains(WebDriver driver, final By locator, String expectedText) {
        LOGGER.info(String.format(RELOADING_PAGE_UNTIL_PRESENCE_OF_ELEMENT_MESSAGE, locator));
        getWebDriverWait(driver).until((ExpectedCondition<Object>) input -> {
            if (findElement(driver, locator).getText().contains(expectedText))
                return true;
            else
                Window.refresh(driver);
            return false;
        });
    }

    public static void reloadWithTimeIntervalUntilTextContains(WebDriver driver, final By locator, String expectedText, int timeoutInSeconds) {
        LOGGER.info(String.format(RELOADING_PAGE_UNTIL_PRESENCE_OF_ELEMENT_MESSAGE, locator));
        getWebDriverWait(driver, timeoutInSeconds, DEFAULT_POLL_TIMEOUT_MS).until((ExpectedCondition<Object>) input -> {
            if (findElement(driver, locator).getText().contains(expectedText))
                return true;
            else
                Window.refresh(driver);
            return false;
        });
    }

    public static void reloadUntilDropdownValueTobe(WebDriver driver, final By locator, String expectedText, int timeoutInSeconds) {
        LOGGER.info("Reloading page until dropdown locator [{}] value to be [{}]", locator, expectedText);
        getWebDriverWait(driver, timeoutInSeconds, DEFAULT_POLL_TIMEOUT_MS).until((ExpectedCondition<Object>) input -> {
            if (DropDown.getSelectedValue(driver, locator).equals(expectedText))
                return true;
            else
                Window.refresh(driver);
            return false;
        });
    }

    public static void reloadUntilCheckBoxDisabled(WebDriver driver, final By locator) {
        LOGGER.info("Reloading page until the presence of element : {}", locator);
        getWebDriverWait(driver).until((ExpectedCondition<Object>) input -> {
            boolean flag = false;
            if (SeleniumHelper.isSelected(driver, locator)) {
                Window.refresh(driver);
            } else {
                flag = true;
            }
            return flag;
        });
    }

    public static void reloadUntilAttributeNotNull(WebDriver driver, final By locator, final String attribute) {
        LOGGER.info("Reloading page until locator: {}, attribute: {}, not to be null", locator, attribute);
        getWebDriverWait(driver).until((ExpectedCondition<Object>) input -> {
            boolean flag = false;
            if ((SeleniumHelper.getAttribute(driver, locator, attribute).isEmpty())) {
                Window.refresh(driver);
            } else
                flag = true;
            return flag;
        });
    }

    public static void reloadUntilValueSelected(WebDriver driver, final By locator, final String attribute, final String text) {
        LOGGER.info("Reloading page until  attribute: {}, is: {} ", attribute, text);
        getWebDriverWait(driver, TimeOut.MAX_TIMEOUT_IN_SEC, DEFAULT_POLL_TIMEOUT_MS).until((ExpectedCondition<Object>) input -> {
            boolean flag = false;
            if ((!SeleniumHelper.getAttribute(driver, locator, attribute).equalsIgnoreCase(text))) {
                Window.refresh(driver);
            } else
                flag = true;
            return flag;
        });
    }



    public static void refreshUntilRelatedListElementAppears(WebDriver driver, final By locator, String relatedListHamburgerMenu, final By refreshList) {
        LOGGER.info(String.format(RELOADING_PAGE_UNTIL_PRESENCE_OF_ELEMENT_MESSAGE, locator));
        getWebDriverWait(driver).until((ExpectedCondition<Object>) input -> {
            boolean flag = SeleniumHelper.isElementPresent(driver, locator);
            if (!flag) {
                waitAndClick(driver, By.xpath(relatedListHamburgerMenu));
                waitAndClick(driver, refreshList);
            }
            return flag;
        });
    }

    public static void refreshUntilRelatedListElementAppears(WebDriver driver, final By locator, String relatedListHamburgerMenu, final By refreshList, int timeoutInSeconds) {
        LOGGER.info(String.format(RELOADING_PAGE_UNTIL_PRESENCE_OF_ELEMENT_MESSAGE, locator));
        getWebDriverWait(driver, timeoutInSeconds, DEFAULT_POLL_TIMEOUT_MS).until((ExpectedCondition<Object>) input -> {
            boolean flag = SeleniumHelper.isElementPresent(driver, locator);
            if (!flag) {
                waitAndClick(driver, By.xpath(relatedListHamburgerMenu));
                waitAndClick(driver, refreshList);
            }
            return flag;
        });
    }

}