package org.example.Helpers;


import lombok.experimental.UtilityClass;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.example.Helpers.SeleniumHelper.findElement;
import static org.example.Helpers.SeleniumHelper.waitAndClick;

@UtilityClass
public class DropDown {

    private static final Logger LOGGER = LogManager.getLogger(DropDown.class);

    public static Select getSelectObject(WebDriver driver, final By locator) {
        LOGGER.info("Crating dropdown object with locator {}", locator);
        return new Select(findElement(driver, locator));
    }

    public static Select getSelectObject(WebElement element) {
        LOGGER.info("Crating dropdown object for element {}", element);
        return new Select(element);
    }

    public static void selectByValue(WebDriver driver, final By locator, String value) {
        LOGGER.info("Selecting dropdown by value {}", value);
        getSelectObject(driver, locator).selectByValue(value);
    }

    public static void selectByIndex(WebDriver driver, final By locator, int index) {
        LOGGER.info("Selecting dropdown by index {}", index);
        getSelectObject(driver, locator).selectByIndex(index);
    }

    public static void selectsByVisibleText(WebDriver driver, final By locator, String text) {
        LOGGER.info("Selecting dropdown by visible text {}", text);
        getSelectObject(driver, locator).selectByVisibleText(text);
    }

    public static void selectsByVisibleText(WebElement element, String text) {
        LOGGER.info("Selecting dropdown by visible text {}", text);
        getSelectObject(element).selectByVisibleText(text);
    }

    public static String getSelectedValue(WebDriver driver, final By locator) {
        String selectedOption = getSelectObject(driver, locator).getFirstSelectedOption().getText();
        LOGGER.info("Selected drop down value is {}", selectedOption);
        return selectedOption;
    }

    public static String getSelectedValue(WebDriver driver, final By locator, String attribute) {
        String selectedOption = getSelectObject(driver, locator).getFirstSelectedOption().getAttribute(attribute);
        LOGGER.info("Selected drop down value is {}", selectedOption);
        return selectedOption;
    }

    public static List<String> getAllValues(WebDriver driver, final By locator) {
        List<WebElement> options = getSelectObject(driver, locator).getOptions();
        List<String> allOptions = options.stream().map(option -> option.getText()).collect(Collectors.toList());
        LOGGER.info("All the drop down values are {}", allOptions);
        return allOptions;
    }

    public static List<WebElement> getOptions(WebDriver driver, final By locator) {
        List<WebElement> options = getSelectObject(driver, locator).getOptions();
        List<String> allOptions = options.stream().map(WebElement::getText).collect(Collectors.toList());
        LOGGER.info("All the drop down values are {}", allOptions);
        return options;
    }

    public static int getDropDownListSize(WebDriver driver, final By locator) {
        int size = getSelectObject(driver, locator).getOptions().size();
        LOGGER.info(" Dropdown size is {}", size);
        return size;
    }

    public static void selectAll(WebDriver driver, final By locator) {
        LOGGER.info("Selecting all the options in the dropdown by locator {}", locator);
        Select dropdown = getSelectObject(driver, locator);
        if (dropdown.isMultiple()) {
            List<WebElement> options = dropdown.getOptions();
            for (WebElement option : options) {
                String text = option.getText();
                dropdown.selectByVisibleText(text);
                LOGGER.info("Selected '{}' from dropdown", text);
            }
        } else {
            throw new UnsupportedOperationException(locator + " --> DropDown does not support multi select operation");
        }
    }

    public static boolean doesContains(WebDriver driver, final By locator, String option) {
        LOGGER.info("checking for the availability of: {}", option);
        List<WebElement> container = getSelectObject(driver, locator).getOptions();
        for (WebElement element : container)
            try {
                if (element.getText().equalsIgnoreCase(option))
                    return true;
            } catch (StaleElementReferenceException exception) {
                return false;
            }
        return false;
    }

    public static boolean verifyPresenceOfValue(WebDriver driver, final By locator, String value) {
        LOGGER.info("verifying the absence of value [{}] in dropdown [{}]", value, locator);
        List<WebElement> container = getSelectObject(driver, locator).getOptions();
        List<String> values = container.stream().map(WebElement::getText).collect(Collectors.toList());
        return values.contains(value);
    }

    public static boolean verifyPresenceOfValue(WebDriver driver, final By locator, List<String> values) {
        LOGGER.info("verifying the absence of values [{}] in dropdown [{}]", values, locator);
        List<WebElement> container = getSelectObject(driver, locator).getOptions();
        ArrayList<String> obtainedValues = (ArrayList<String>) container.stream().map(WebElement::getText).collect(Collectors.toList());
        LOGGER.info("Obtained values from dropdown: [{}]", obtainedValues);
        return obtainedValues.containsAll(values);
    }

    public static void removeAllSelectedValues(WebDriver driver, WebElement dropdownElement, By removeLocator) {
        Select dropdown = new Select(dropdownElement);
        try {
            List<WebElement> options = dropdown.getOptions();
            for (WebElement option : options) {
                String text = option.getText();
                dropdown.selectByVisibleText(text);
                waitAndClick(driver, removeLocator);
                LOGGER.info("Removing '{}' from dropdown", text);
            }
        } catch (UnsupportedOperationException exception) {
            LOGGER.info(String.format("--> DropDown does not support multi select remove operation %s", dropdownElement));
        }
    }

    public static List<String> getAllSelectedValues(WebElement dropdownElement) {
        List<String> selectedValues = new ArrayList<>();
        Select dropdown = new Select(dropdownElement);
        try {
            List<WebElement> options = dropdown.getOptions();
            for (WebElement option : options) {
                selectedValues.add(option.getText());
            }
        } catch (UnsupportedOperationException exception) {
            LOGGER.info(String.format("--> DropDown does not support multi select operation %s", dropdownElement));
        }
        return selectedValues;
    }

    public static String getIndexOfSelectedValue(WebDriver driver, final By locator, String status) {
        List<String> statusOptions = DropDown.getAllValues(driver, locator);
        String statusIndex = String.valueOf(statusOptions.indexOf(status));
        LOGGER.info("Index of selected value is '{}' from dropdown", statusIndex);
        return statusIndex;
    }
}
