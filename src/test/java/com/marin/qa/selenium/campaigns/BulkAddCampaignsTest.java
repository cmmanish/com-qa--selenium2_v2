package com.marin.qa.selenium.campaigns;

import com.marin.qa.selenium.WebdriverBaseClass;
import com.marin.qa.selenium.common.MarinApp;
import com.marin.qa.selenium.common.QaRandom;
import com.marin.qa.selenium.pageObjects.pages.HomePage;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

public class BulkAddCampaignsTest extends WebdriverBaseClass {

    final String CAMPAIGN_VIEW = "150CampaignsAllButDeleted";
    public static Logger log = Logger.getLogger(BulkAddCampaignsTest.class);
    public static WebDriver driver = MarinApp.getApp();
    public QaRandom random = QaRandom.getInstance();

    @BeforeClass
    public static void testSetUp() {
        log.info("<--------- Start Setup Test --------->");
        loginSuccessful(driver);
        clearAllPendingChanges(driver);
        log.info("<--------- End Setup Test --------->");
    }

    @AfterClass
    public static void cleanup() {
        log.info("<--------- Start Logout Test --------->");
        clearAllPendingChanges(driver);
        HomePage homePage = HomePage.getInstance();
        homePage.click(driver, HomePage.Link.Admin);
        homePage.click(driver, HomePage.Link.Logout);
        driver.close();
        log.info("<--------- End Logout Test --------->");
    }

    @Test
    public void T1BulkCreateGoogleShoppingCampaignNonUS() throws Exception {


    }

    @Test
    public void T2SingleCreateGoogleShoppingCampaignUSShoppingChannelBoth() throws Exception {


    }

    public void testLogout() {
        log.info("<--------- Start Logout Test --------->");
        HomePage homePage = HomePage.getInstance();
        clearAllPendingChanges(driver);
        homePage.click(driver, HomePage.Link.Admin);
        homePage.click(driver, HomePage.Link.Logout);
        driver.close();
        log.info("<--------- End Login Test --------->");
    }

}
