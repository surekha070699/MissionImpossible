package org.example.pageObjects;

import org.openqa.selenium.WebDriver;
import org.example.utils.Assertion;

public abstract class PageBase {
    protected WebDriver driver;
    protected Assertion assertion;

    protected PageBase(WebDriver driver) {
        this.driver = driver;
        assertion = new Assertion();
    }
}

