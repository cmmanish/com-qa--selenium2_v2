package com.marin.qa.selenium.campaigns;

import com.marin.qa.selenium.WebdriverBaseClass;
import com.marin.qa.selenium.common.MarinApp;
import com.marin.qa.selenium.common.QaRandom;
import com.marin.qa.selenium.pageObjects.pages.HomePage;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

public class BuildAcceptanceTest extends WebdriverBaseClass {

    public static WebDriver driver = MarinApp.getApp();
    final private static Logger log = Logger.getLogger(BuildAcceptanceTest.class);
    public QaRandom random = QaRandom.getInstance();

    public BuildAcceptanceTest() {
        log.info("-----------------------------------------");
        log.info("- Now Running BuildAcceptanceTest Suite -");
        log.info("-----------------------------------------");
    }

    @BeforeClass
    public static void testSetUp() {
        log.info("Now Running BuildAcceptanceTest Suite");
        log.info("<--------- Start Setup Test --------->");
        loginSuccessful(driver);
        clearAllPendingChanges(driver);
        log.info("<--------- End Setup Test --------->");
    }

    @AfterClass
    public static void cleanup() {
        log.info("<--------- Start Logout Test --------->");
        clearAllPendingChanges(driver);
        logoutSuccessful(driver);
        driver.close();
        log.info("<--------- End Logout Test --------->");
    }

    @After
    public void RunAfterEachTest() {
        log.info("-----------------------------------------");
        log.info("- Now Running RunAfterEachTest -");
        log.info("-----------------------------------------");
        HomePage homePage = HomePage.getInstance();
        homePage.click(driver, HomePage.Link.Admin);
    }

    @Test
    public void E1testSingleCreateGoogleShoppingCampaignUS() throws Exception {
        log.info("E1");
    }

    @Test
    public void E2testSingleCreateGoogleShoppingCampaignNonUS() throws Exception {
        log.info("E2");
    }
}
