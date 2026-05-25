package org.example.config;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.Arrays;


public class Firefox implements SeleniumWebdriver {

    private final DriverDataModel driverDataModel;

    public Firefox() {
        this.driverDataModel = DriverDataModel.getInstance();
    }

    @Override
    public WebDriver getDriver() {
        FirefoxOptions options = new FirefoxOptions();
      //  System.setProperty("browserName", setCapabilities().getBrowserName());
        return new FirefoxDriver(options);
    }

    @Override
    public MutableCapabilities setCapabilities() {
        FirefoxOptions options = new FirefoxOptions();
        String[] arguments = driverDataModel.getFireFoxCapabilities();
        if (arguments != null && arguments.length > 0) {
            options.addArguments(Arrays.asList(arguments));
        }
        return options;
    }
}

