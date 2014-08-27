package com.marin.qa.selenium.pageObjects.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.thoughtworks.selenium.SeleniumException;

public abstract class AbstractPage {

    public static Logger log = Logger.getLogger(AbstractPage.class);
    public static final long LONG_PAGE_TIMEOUT = 121000;
    public static final long TWO_SECONDS = 2000;
    public static final long FIVE_SECONDS = 5000;
    public static final String ELEMENT_TIMEOUT = "65000";
    public static final String AJAX_TIMEOUT = "40000";
    public static final String JQUERY_TIMEOUT = "6000";
    public static final String POST_NOW_ID = "#postNowText";
    public static final String POST_CHANGE_ID = "#postChangeText";

    public static final String ACTION_SUCCESS = ".good a:contains(\"Activity Log\")";


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
     * 
     *        The selenium instances currently in work.
     */

    public void waitForJQuery(WebDriver driver) {

        try {
            (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver d) {
                    JavascriptExecutor js = (JavascriptExecutor) d;
                    return (Boolean) js.executeScript("return jQuery.active == 0");
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
     * 
     *        wait for document.readyState to be complete
     */
    public void waitForPageToLoad(final WebDriver driver, final long timeout) {

        ExpectedCondition<Boolean> expectation = new
                ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver driver) {
                        return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
                    }
                };

        Wait<WebDriver> wait = new WebDriverWait(driver,30);
        try {
            wait.until(expectation);
        } catch(Throwable error) {
            log.info("Timeout waiting for Page Load Request to complete.");
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
     * 
     *        The selenium instances currently in work.
     * @param locator
     *        The Element locator.
     */
    public void waitForSpinnerToDissappear(WebDriver driver, String locator) {
        String query = "return $('" + locator + "').length == 1;";
        //wait for the spinner do dissappear i.e length == 1
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
     * 
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
     * 
     * @param locator
     * @return true if the element is present, false otherwise
     */
    public boolean isElementPresent(final WebDriver driver, final String locator) {

        String query = "return $('" + locator + "').length > 0";
       // waitForJQuery(driver);
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
     * 
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
     * 
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
    
    public void sleep(long time) {

        try {
            Thread.sleep(time);
        }
        catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
