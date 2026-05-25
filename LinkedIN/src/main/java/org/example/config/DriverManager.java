package org.example.config;

import org.openqa.selenium.WebDriver;

public class DriverManager  {
    public static WebDriver getLocalDriver(SeleniumWebdriver webdriver){
        return webdriver.getDriver();
    }
}
