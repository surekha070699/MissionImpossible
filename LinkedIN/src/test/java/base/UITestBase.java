package base;

import org.example.Executions.TestConfiguration;
import org.example.Executions.WebApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.openqa.selenium.WebDriver;
import org.example.Executions.WebAppBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Listeners;

@ContextConfiguration(classes = TestConfiguration.class)

public class UITestBase extends AbstractTestNGSpringContextTests {
    protected WebDriver driver;
    @Autowired
    protected WebApp webApp;
    @Autowired
    protected TestConfiguration config;

    @BeforeMethod
    public void setUpUITestBase() {
        driver = WebAppBase.getChromeDriver();
    }

    @AfterMethod
    public void tearDownUITestBase() {
        WebAppBase.quitDriver();
    }
}
