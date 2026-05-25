package Tests;

import base.UITestBase;
import org.example.Executions.WebApp;
import org.example.Executions.TestConfiguration;
import org.example.pageObjects.LoginPage;
import org.example.pageObjects.Pages;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

public class LoginTests extends UITestBase {

    @Test
    public void loginWithValidCredentials() {
        LoginPage loginPage = (LoginPage) webApp.getPage(Pages.LOGIN, LoginPage.class);
        String username = config.getUserName();
        String password = config.getPassword();
        loginPage.login(username, password);
        // Add assertion for successful login, e.g., check for home page element or URL
        // Example: Assert.assertTrue(driver.getCurrentUrl().contains("home"));
    }
}
