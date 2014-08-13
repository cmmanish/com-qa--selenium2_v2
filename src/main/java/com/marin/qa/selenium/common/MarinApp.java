package com.marin.qa.selenium.common;

import org.apache.log4j.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.Properties;

public class MarinApp {

    static Logger log = Logger.getLogger(MarinApp.class);

    private ScreenShot screenShot = null;
    public static WebDriver driver = null;
    public static Properties props = null;

    protected static String LOGIN = "auto_orca@marinsoftware.com";
    protected static String PASSWORD = "marin2007";
    protected static String pageLoadError = "Problem loading page";
    protected static String needToBeOnVPN = "Online Advertising Management Platform | Marin Software";

    public MarinApp() {
    }

    public static WebDriver getApp() {

        setupBrowser();
        launchApp();
        return driver;
    }

    private static void setupBrowser() {

        if (QaProperties.isFirefox()) {
            driver = new FirefoxDriver();
        }
        else if (QaProperties.isChrome()) {
            driver = new ChromeDriver();
        }

        int width = 1920;
        int height = 1080;
        driver.manage().window().setSize(new Dimension(width, height));
        log.info("Set Browser Dimensions to " + width + "x" + height + ".");
        log.info("Using browser driver: " + driver.getClass().getName());

    }

    private static void launchApp() {

        String appURL = QaProperties.getAppURL();
        driver.navigate().to(appURL);

        if (pageLoadError.equalsIgnoreCase(driver.getTitle())) {

            log.info(pageLoadError);
            driver.close();
        }
        else if (needToBeOnVPN.equalsIgnoreCase(driver.getTitle())) {
            log.info("need To Be On VPN");
            driver.close();
        }
        else {
            driver.getTitle();
            log.info("Launching ...  " + appURL);
        }

    }

}
