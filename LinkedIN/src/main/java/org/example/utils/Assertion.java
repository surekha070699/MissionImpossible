package org.example.utils;

import org.springframework.stereotype.Component;
import org.testng.Assert;

import java.util.List;
import java.util.Map;

@Component
public class Assertion {

    private static final String ASSERTION_NOT_EQUAL_MESSAGE = "Actual value [%s] is not equal to expected value [%s]";
    private static final String ASSERTION_EQUAL_MESSAGE = "Actual value [%s] is equal to expected value [%s]";
    public static final String RECORDS_FOUND = "Records found";

    public void fail(String message) {
        Assert.fail(message);
    }

    public void assertTrue(boolean condition, String customMessage) {
        Assert.assertTrue(condition, "'" + customMessage + "'");
    }

    public void assertFalse(boolean condition, String customMessage) {
        Assert.assertFalse(condition, "'" + customMessage + "'");
    }

    public void assertEquals(int actual, int expected) {
        String message = String.format(ASSERTION_NOT_EQUAL_MESSAGE, actual, expected);
        Assert.assertEquals(actual, expected, message + "\n");
    }

    public void assertEquals(String actual, String expected) {
        String message = String.format(ASSERTION_NOT_EQUAL_MESSAGE, actual, expected);
        Assert.assertEquals(actual, expected, message + "\n");
    }

    public void assertEquals(boolean actual, boolean expected) {
        String message = String.format(ASSERTION_NOT_EQUAL_MESSAGE, actual, expected);
        Assert.assertEquals(actual, expected, message + "\n");
    }

    public void assertEquals(int actual, int expected, String message) {
        Assert.assertEquals(actual, expected, message + "\n");
    }

    public void assertEquals(double actual, double expected, String message) {
        Assert.assertEquals(actual, expected, message + "\n");
    }

    public void assertEquals(String actual, String expected, String message) {
        Assert.assertEquals(actual, expected, message + "\n");
    }

    public void assertEquals(boolean actual, boolean expected, String message) {
        Assert.assertEquals(actual, expected, message + "\n");
    }

    public void assertNotEquals(int actual, int expected) {
        String message = String.format(ASSERTION_EQUAL_MESSAGE, actual, expected);
        Assert.assertNotEquals(actual, expected, message + "\n");
    }

    public void assertNotEquals(String actual, String expected) {
        String message = String.format(ASSERTION_EQUAL_MESSAGE, actual, expected);
        Assert.assertNotEquals(actual, expected, message + "\n");
    }

    public void assertNotEquals(boolean actual, boolean expected) {
        String message = String.format(ASSERTION_EQUAL_MESSAGE, actual, expected);
        Assert.assertNotEquals(actual, expected, message + "\n");
    }

    public void assertNotEquals(int actual, int expected, String message) {
        Assert.assertNotEquals(actual, expected, message + "\n");
    }

    public void assertNotEquals(String actual, String expected, String message) {
        Assert.assertNotEquals(actual, expected, message + "\n");
    }

    public void assertNotEquals(boolean actual, boolean expected, String message) {
        Assert.assertNotEquals(actual, expected, message + "\n");
    }

    public void assertNotNull(String actual) {
        Assert.assertNotNull(actual, "Value is null" + "\n");
    }

    public void assertNull(String actual, String message) {
        Assert.assertNull(actual, message + "\n");
    }

    public void assertEquals(List<String> actual, List<String> expected) {
        String message = String.format(ASSERTION_NOT_EQUAL_MESSAGE, actual, expected);
        Assert.assertEquals(actual, expected, message + "\n");
    }

    public void assertEquals(Map<?, ?> actual, Map<?, ?> expected) {
        String message = String.format(ASSERTION_NOT_EQUAL_MESSAGE, actual, expected);
        Assert.assertEquals(actual, expected, message + "\n");
    }
}

