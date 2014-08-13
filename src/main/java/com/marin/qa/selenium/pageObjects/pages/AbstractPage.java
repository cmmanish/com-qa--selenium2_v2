package com.marin.qa.selenium.pageObjects.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.thoughtworks.selenium.SeleniumException;

public abstract class AbstractPage {

    static Logger log = Logger.getLogger(AbstractPage.class);
    public static final long LONG_PAGE_TIMEOUT = 121000;
    public static final String ELEMENT_TIMEOUT = "65000";
    public static final String AJAX_TIMEOUT = "40000";
    public static final String JQUERY_TIMEOUT = "6000";
    public static final String SPINNER_TIMEOUT = "110000";
    public static final String TASK_ID = "#tasksText";
    public static final String POST_NOW_ID = "#postNowText";
    public static final String POST_CHANGE_ID = "#postChangeText";
    public static final String ACCOUNT_SUCCESS = ".good a:contains(\"Account information successfully updated.\")";
    public static final String ACTION_SUCCESS = ".good a:contains(\"Activity Log\")";
    public static final String ACTION_QUEUED = ".good li:contains(\"Action has been queued up to be posted to publishers\")";
    public static final String BUBBLE_CONTAINER = "#bubble_container";
    public static final String PROGRESS_GRID_CONTAINER = "#progress_grid_container";
    public static final String SAVE_VIEW_CONTAINER_ID = "#addRemoveView_container_contents";
    public static final String PROCESSING_GRID_CONTRAINER = "#progress_grid_container";
    
    synchronized public static void wait(int n) {

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
     * This method waits for an Jquery loaded
     * Requires current selenium object to work with.
     *
     * @param selenium
     *        The selenium instances currently in work.
     */

    public void waitForJQuery(WebDriver driver) {

        try {
            (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver d) {
                    JavascriptExecutor js = (JavascriptExecutor) d;
                    return (Boolean) js.executeScript("return $.active == 0");
                }
            });
        }

        catch (Exception e) {
            log.error("Failed to load jQuery on page in " + JQUERY_TIMEOUT + " msec");
        }

    }
    
    public void waitForAjaxRequestDone(final WebDriver driver, final String timeout) {

        waitForJQuery(driver);

            try {
                (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver d) {
                        JavascriptExecutor js = (JavascriptExecutor) d;
                        return (Boolean) js.executeScript("return $$.active == 0");
                    }
                });
           
        }
        catch (Exception e) {
            log.error("Failed to wait for no AJAX activity on page in " + timeout + " msec");
        }
    }

    /**
     * This method waits for page to be loaded.
     * Requires current selenium object to work with.
     *
     * @param selenium
     *        The selenium instances currently in work.
     */
    public void waitForPageToLoad(final WebDriver driver, final long timeout) {

        try {

            WebDriverWait wait = new WebDriverWait(driver, timeout);
            //wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("top_right_logout_link")));

        }
        catch (Exception e) {
            log.error("Failed to wait for page to be loaded in " + timeout + " msec");
        }
    }

    public void waitForElementToBeAppear(WebDriver driver, String locator) {

        final String query = "return $('" + locator + "').length > 0";
        waitForJQuery(driver);

        try {
            (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver d) {
                    JavascriptExecutor js = (JavascriptExecutor) d;
                    return (Boolean) js.executeScript(query);
                }
            });
        }

        catch (Exception e) {
            log.error("Failed to load jQuery on page in " + JQUERY_TIMEOUT + " msec");
        }
    }

    /**
     * This method waits for an element not to be visible.
     * 
     * Requires current selenium object to work with.
     *
     * @param selenium
     *        The selenium instances currently in work.
     * @param locator
     *        The Element locator.
     */
    public void waitForSpinnerToDissappear(WebDriver driver, String locator) {
        String query = "return $('" + locator + "').length == 1;";
        //wait for the spinner do dissappear i.e length == 0 
        try {
            boolean retval = (Boolean) ((JavascriptExecutor) driver).executeScript(query);
            while (retval){
                wait(300);
                retval = (Boolean) ((JavascriptExecutor) driver).executeScript(query);
            }
            
        }
        catch (Exception e) {
            log.error("Failed to wait for element visible on page in " + ELEMENT_TIMEOUT + " msec");
        }

    }
    
    /**
     * This method waits for an element not to be visible.
     * 
     * Requires current selenium object to work with.
     *
     * @param selenium
     *        The selenium instances currently in work.
     * @param locator
     *        The Element locator.
     */
    public void waitForElementToDissappear(WebDriver driver, String locator) {
        final String query = "return $('" + locator + "').length == 0;";
        //wait for the spinner do dissappear i.e length == 0 
        try {
            (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver d) {
                    JavascriptExecutor js = (JavascriptExecutor) d;
                    return (Boolean) js.executeScript(query);
                }
            });
        }
        catch (Exception e) {
            log.error("Failed to load jQuery on page in " + JQUERY_TIMEOUT + " msec");
        }
    }
    
    public boolean waitForDropDownElementToBePopulated(WebDriver driver, String locator) {
        final String query = "return $('" + locator + "').children().length > 1";
        final boolean retval = false;
        waitForJQuery(driver);

        try {
            (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver d) {
                    JavascriptExecutor js = (JavascriptExecutor) d;
                    return (Boolean) js.executeScript(query);
                }
            });
        }

        catch (Exception e) {
            log.error("Failed to load jQuery on page in " + JQUERY_TIMEOUT + " msec");
        }
        return retval;
    }

    /**
     * Verifies that the element is somewhere on the page.
     * 
     * @param selenium
     * @param locator
     * @return true if the element is present, false otherwise
     */
    public boolean isElementPresent(final WebDriver driver, final String locator) {

        String query = "return $('" + locator + "').length > 0";
        waitForJQuery(driver);
        JavascriptExecutor jse;
        boolean retval = false;
        try {
            if (driver instanceof JavascriptExecutor) {
                jse = (JavascriptExecutor) driver;
                retval = (Boolean) jse.executeScript(query);
            }
            return retval;
        }
        catch (SeleniumException se) {
            log.error("Failed to confirm presents of element on page");
            return false;
        }
    }

    /**
     * This Method set to change element background
     * 
     * @author mmadhusoodan
     * @param selenium
     * @param locator
     * @return void
     * 
     */
    public void changeElementBackground(WebDriver driver, String locator) {

        waitForJQuery(driver);
        String query = "$('" + locator + "').css('border', '2px solid red');";

        try {
            ((JavascriptExecutor) driver).executeScript(query);

        }
        catch (Exception e) {
            log.error("Failed to change border of element on page");
        }

    }

    /**
     * This Method set to remove element background by id
     * 
     * @author mmadhusoodan
     * @param selenium
     * @param locator
     * @return void
     * 
     */
    public void removeElementBackground(WebDriver driver, String locator) {
        waitForJQuery(driver);
        String query = "$('" + locator + "').css('border', '');";

        try {
            ((JavascriptExecutor) driver).executeScript(query);

        }
        catch (Exception e) {
            log.error("Failed to change border of element on page");
        }

    }

}
