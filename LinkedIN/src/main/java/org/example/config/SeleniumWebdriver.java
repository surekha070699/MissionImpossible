package org.example.config;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;

public interface SeleniumWebdriver {
    WebDriver getDriver();
    MutableCapabilities setCapabilities();
}
