package com.marin.qa.selenium.pageObjects.bubble;

import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

/**
 * General class describe grid bubble
 *
 * @author mmadhusoodan
 *
 */

public class Bubble {

    static Logger log = Logger.getLogger(Bubble.class);
    public static final String LONG_PAGE_TIMEOUT = "150000";
    public static final String ELEMENT_TIMEOUT = "150000";
    public static final String AJAX_TIMEOUT = "40000";
    public static final String JQUERY_TIMEOUT = "6000";
    public static final String SPINNER_TIMEOUT = "40000";
    public static final String BUBBLE_TIMEOUT = "10000";
    public static final String BUBBLE_CONTAINER = "#bubble_container";

    public static final String TASK_ID = "#tasksText";
    public static final String POST_NOW_ID = "#postNowText";
    public static final String POST_CHANGE_ID = "#postChangeText";
    public static final String ACCOUNT_SUCCESS = ".good a:contains(\"Account information successfully updated.\")";
    public static final String ACTION_SUCCESS = ".good a:contains(\"Activity Log\")";
    public static final String ACTION_QUEUED = ".good li:contains(\"Action has been queued up to be posted to publishers\")";

    public void wait(int n) {

        try {
            Thread.sleep(n, 0);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        catch (IllegalMonitorStateException e) {
            e.printStackTrace();
        }

    }

    /**
     * This method set to return page title
     *
     * @author mbeider
     * @param selenium
     * @throws Exception
     * @return Page title
     *
     */
    public String getTitle(WebDriver driver) throws Exception {

        String query = "$.document.title;";
        String ret = "";
        try {
            ret = (String) ((JavascriptExecutor) driver).executeScript(query);
        }
        catch (WebDriverException se) {
            log.error("Failed to confirm presents of element on bubble");

        }
        return ret;

    }

    /**
     * This method waits for an ajax request to complete.
     * Requires current selenium object to work with.
     *
     * @param selenium
     *        The selenium instances currently in work.
     * @param timeout
     *        The maximum amount of time to wait in milliseconds.
     */

    public void waitForAjaxRequestDone(final WebDriver driver, final String timeout) {

        String query = "$.active == 0";
        try {
            ((JavascriptExecutor) driver).executeScript(query);
        }
        catch (WebDriverException se) {
            log.error("Failed to confirm presents of element on bubble");

        }

    }

    /**
     * This method waits for bubble to appear.
     *
     * @param selenium
     */

    public void waitForBubble(final WebDriver driver) {

        String query = "$('" + BUBBLE_CONTAINER + "').is(':visible') == true;";
        try {
            ((JavascriptExecutor) driver).executeScript(query);
        }
        catch (WebDriverException se) {
            log.error("Failed to confirm presents of element on bubble");

        }

    }

    /**
     * This method waits for bubble to disappear.
     *
     * @param selenium
     */

    public void waitForNoBubble(final WebDriver driver) {

        String query = "$('" + BUBBLE_CONTAINER + "').is(':hidden') == true;";
        try {
            ((JavascriptExecutor) driver).executeScript(query);
        }
        catch (WebDriverException se) {
            log.error("Failed to confirm presents of element on bubble");

        }

    }

    /**
     * This method waits for an element present.
     * 
     * Requires current selenium object to work with.
     *
     * @param selenium
     *        The selenium instances currently in work.
     * @param locator
     *        The Element locator.
     */
    public void waitForElementToBePresent(WebDriver driver, String locator) {

        String query = "$('" + locator + "').length > 0;";
        try {
            ((JavascriptExecutor) driver).executeScript(query);
        }
        catch (WebDriverException se) {
            log.error("Failed to confirm presents of element on bubble");

        }

    }

    /**
     * This method waits for an element disappear.
     * Requires current selenium object to work with.
     *
     * @param selenium
     *        The selenium instances currently in work.
     * @param locator
     *        The Element locator.
     */
    public void waitForElementNotToBePresent(WebDriver driver, String locator) {

        String query = "$('" + locator + "').length == 0;";
        try {
            ((JavascriptExecutor) driver).executeScript(query);
        }
        catch (WebDriverException se) {
            log.error("Failed to confirm presents of element on bubble");

        }

    }

    /**
     * This method waits for an element.
     * Requires current selenium object to work with.
     *
     * @param selenium
     *        The selenium instances currently in work.
     * @param locator
     *        The Element locator.
     */
    public void waitForElementToBeVisible(WebDriver driver, String locator) {

        String query = "$('" + locator + "').is(':visible') == true;";
        try {
            ((JavascriptExecutor) driver).executeScript(query);
        }
        catch (WebDriverException se) {
            log.error("Failed to confirm presents of element on bubble");

        }

    }

    /**
     * This method waits for an Drop Down Menu element to be populated
     * 
     * Requires current selenium object to work with.
     *
     * @param selenium
     *        The selenium instances currently in work.
     * @param locator
     *        The Element locator.
     */
    public void waitForDropDownElementToBePopulated(WebDriver driver, String locator) {

        String query = "$('" + locator + "').children().length > 1";
        try {
            ((JavascriptExecutor) driver).executeScript(query);
        }
        catch (WebDriverException se) {
            log.error("Failed to confirm presents of element on bubble");

        }

    }

    /**
     * This method waits for spinner run to complete.
     *
     * @param selenium
     * @param locator
     * @param by
     */
    public void waitForElementNotToBeVisible(final WebDriver driver, final String locator) {

        String query = "$('" + locator + "').is(':visible') == false;";
        try {
            ((JavascriptExecutor) driver).executeScript(query);
        }
        catch (WebDriverException se) {
            log.error("Failed to confirm presents of element on bubble");

        }

    }

    /**
     * This method waits for an element enable.
     * 
     * Requires current selenium object to work with.
     *
     * @param selenium
     *        The selenium instances currently in work.
     * @param locator
     *        The Element locator.
     */
    public void waitForElementToBeEnable(WebDriver driver, String locator) {

        String query = "$('" + locator + "').is(':enabled') == true;";
        try {
            ((JavascriptExecutor) driver).executeScript(query);
        }
        catch (WebDriverException se) {
            log.error("Failed to confirm presents of element on bubble");

        }

    }

    /**
     * Verifies that the element is somewhere on the bubble.
     *
     * @param selenium
     * @param locator
     * @return true if the element is present, false otherwise
     */
    public boolean isElementPresent(final WebDriver driver, final String locator) {

        String query = "return $('" + locator + "').length > 0;";
        try {
            return (Boolean) ((JavascriptExecutor) driver).executeScript(query);
        }
        catch (WebDriverException se) {
            log.error("Failed to confirm presents of element on bubble");
            return false;
        }
    }

    /**
     * Verifies that the element is visible on the bubble.
     *
     * @param selenium
     * @param locator
     * @return true if the element is visible, false otherwise
     */
    public boolean isElementVisible(final WebDriver driver, final String locator) {

        String query = "$('" + locator + "').is(':visible');";
        try {
            return (Boolean) ((JavascriptExecutor) driver).executeScript(query);
        }
        catch (WebDriverException se) {
            log.error("Failed to confirm presents of element on bubble");
            return false;
        }

    }

    /**
     * Verifies that the element is enabled on the bubble.
     *
     * @param selenium
     * @param locator
     * @return true if the element is visible, false otherwise
     */
    public boolean isElementEnable(final WebDriver driver, final String locator) {

        String query = "return $('" + locator + "').is(':enabled');";
        try {
            return (Boolean) ((JavascriptExecutor) driver).executeScript(query);
        }
        catch (WebDriverException se) {
            log.error("Failed to confirm presents of element on bubble");
            return false;
        }
    }

    /**
     * Verifies that the element is somewhere on the page.
     *
     * @param selenium
     * @param locator
     * @return true if the element is present, false otherwise
     */
    public boolean isElementNoPresent(final WebDriver driver, final String locator) {

        return !isElementPresent(driver, locator);

    }

    /**
     * This Method set to change element background
     *
     * @author mbeider
     * @param selenium
     * @param locator
     * @return void
     *
     */
    public void changeElementBackground(WebDriver driver, String locator) {

        String query = "$('" + locator + "').css(\"border\", \"2px solid red\" );";
        try {
            ((JavascriptExecutor) driver).executeScript(query);
        }
        catch (WebDriverException se) {
            log.error("Failed to confirm presents of element on bubble");

        }

    }

    /**
     * This Method set to remove element background by id
     *
     * @author mbeider
     * @param selenium
     * @param locator
     * @return void
     *
     */
    public void removeElementBackground(WebDriver driver, String locator) {

        String query = "$('" + locator + "').css(\"border\", \"\" );";
        try {
            ((JavascriptExecutor) driver).executeScript(query);
        }
        catch (WebDriverException se) {
            log.error("Failed to confirm presents of element on bubble");

        }

    }

}