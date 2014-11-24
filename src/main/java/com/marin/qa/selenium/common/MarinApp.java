package com.marin.qa.selenium.common;

import com.marin.qa.selenium.Util.QaProperties;
import com.marin.qa.selenium.Util.QaScreenshot;
import org.apache.log4j.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.util.Properties;

public class MarinApp {

	static Logger log = Logger.getLogger(MarinApp.class);

	public static WebDriver driver = null;
	public static Properties props = null;
    public static int WIDTH = 1920;
    public static int HEIGHT = 1080;

	protected static String LOGIN = "auto_orca@marinsoftware.com";
	protected static String PASSWORD = "marin2007";
	protected static String PAGE_LOAD_ERROR = "Problem loading page";
	protected static String NEED_TO_ON_VPN = "Online Advertising Management Platform | Marin Software";

	public MarinApp() {
	}

	private static boolean isSupportedPlatform() {
		Platform current = Platform.getCurrent();
		return Platform.MAC.is(current) || Platform.WINDOWS.is(current);
	}

	public static WebDriver getApp() {

		setupBrowser();
		launchApp();
		return driver;
	}

    //Factory Method Pattern
	private static void setupBrowser() {

		try {
			if (QaProperties.isFirefox()) {
                log.info("===========================================");
                log.info("Using Firefox Browser");
                log.info("===========================================");
				driver = new FirefoxDriver();

			} else if (QaProperties.isChrome()) {
                log.info("===========================================");
                log.info("Using Chrome Browser");
                log.info("===========================================");
				driver = new ChromeDriver();
			}

			else if (QaProperties.isSafari()) {
                log.info("===========================================");
                log.info("Using Safari Browser");
                log.info("===========================================");
                driver = new SafariDriver();
			}
            else if (QaProperties.isIE()) {
                log.info("===========================================");
                log.info("Using Internet Explorer Browser");
                log.info("===========================================");
                driver = new InternetExplorerDriver();
            }

		} catch (Exception e) {
			e.printStackTrace();
		}

        driver.manage().window().setSize(new Dimension(WIDTH, HEIGHT));
        log.info("Set Browser Dimensions to " + WIDTH + "x" + HEIGHT + ".");
        log.info("Using browser driver: " + driver.getClass().getName());

	}

	private static void launchApp() {

		String appURL = QaProperties.getAppURL();
		driver.navigate().to(appURL);

		if (PAGE_LOAD_ERROR.equalsIgnoreCase(driver.getTitle())) {
            log.info("===========================================");
            log.info("Host not reachable");
            log.info("===========================================");
            driver.close();
            driver.quit();
            System.exit(0);
		}
        else if (NEED_TO_ON_VPN.equalsIgnoreCase(driver.getTitle())) {
            log.info("===========================================");
            log.info("You need To Be On VPN");
            log.info("===========================================");
            driver.close();
            driver.quit();
            System.exit(0);
		}
        else {
			driver.getTitle();
            log.info("===========================================");
            log.info("Launching ...  " + appURL);
            log.info("===========================================");

		}

	}

}