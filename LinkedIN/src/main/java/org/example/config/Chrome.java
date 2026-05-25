package org.example.config;



import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Arrays;

public class Chrome implements SeleniumWebdriver {

    private final DriverDataModel driverDataModel;

    public Chrome() {
        this.driverDataModel = DriverDataModel.getInstance();
    }

    @Override
    public WebDriver getDriver() {
        ChromeOptions options = new ChromeOptions();
        return new ChromeDriver(options);
    }

    @Override
    public MutableCapabilities setCapabilities() {
       ChromeOptions options = new ChromeOptions();
//        String[] arguments = driverDataModel.getChromeCapabilities();
//        if (arguments != null && arguments.length > 0) {
//            options.addArguments(Arrays.asList(arguments));
//        }
        return options;
    }
}