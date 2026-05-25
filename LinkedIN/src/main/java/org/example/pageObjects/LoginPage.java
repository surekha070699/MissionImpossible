package org.example.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.example.Helpers.SeleniumHelper;

public class LoginPage extends PageBase {
    private final By usernameField = By.id("username");
    private final By passwordField = By.id("password");
    private final By loginButton = By.id("loginBtn");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void enterUsername(String username) {
        SeleniumHelper.findElement(driver, usernameField).clear();
        SeleniumHelper.findElement(driver, usernameField).sendKeys(username);
    }

    public void enterPassword(String password) {
        SeleniumHelper.findElement(driver, passwordField).clear();
        SeleniumHelper.findElement(driver, passwordField).sendKeys(password);
    }

    public void clickLogin() {
        SeleniumHelper.findElement(driver, loginButton).click();
    }

    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }
}
