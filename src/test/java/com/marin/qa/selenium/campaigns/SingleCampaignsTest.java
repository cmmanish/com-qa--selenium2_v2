package com.marin.qa.selenium.campaigns;

import com.marin.qa.selenium.WebdriverBaseClass;
import com.marin.qa.selenium.common.MarinApp;
import com.marin.qa.selenium.common.QaRandom;
import com.marin.qa.selenium.pageObjects.pages.*;
import com.marin.qa.selenium.pageObjects.pages.NewGoogleCampaignPage.CampaignPriority;
import com.marin.qa.selenium.pageObjects.pages.NewGoogleCampaignPage.CampaignStatus;
import com.marin.qa.selenium.pageObjects.pages.NewGoogleCampaignPage.CampaignType;
import com.marin.qa.selenium.pageObjects.pages.NewGoogleCampaignPage.CountryOfSale;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;





public class SingleCampaignsTest extends WebdriverBaseClass {

    public static Logger log = Logger.getLogger(SingleCampaignsTest.class);
    public static WebDriver driver = MarinApp.getApp();
    public QaRandom random = QaRandom.getInstance();


    public SingleCampaignsTest(){

        log.info("Now Running SingleCampaignsTest Suite");
    }

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
        logoutSuccessful(driver);
        driver.close();
        log.info("<--------- End Logout Test --------->");
    }

    @Test
    public void T1SingleCreateGoogleShoppingCampaignNonUS() throws Exception {

        String campaignName = random.getRandomStringWithPrefix("CampaignName", 5);

        String merchantId = "100543509";
        String budget = "1.11";
        String successLabel = "Campaign successfully created. See Activity Log for details.";
        String campaignPriority = random.getRandomElement(CampaignPriority);
        calendar.setTime(Calendar.getInstance().getTime());
        final String startDate = groupFormaterDate.format(calendar.getTime());
        calendar.add(Calendar.MONTH, 1);
        final String endDate = groupFormaterDate.format(calendar.getTime());

        HomePage homePage = HomePage.getInstance();
        homePage.select(driver, HomePage.Tab.Campaigns);
        CampaignsPage campaignsPage = CampaignsPage.getInstance();
        campaignsPage.click(driver, CampaignsPage.Button.Create);

        NewCampaignPage newCampaignsPage = NewCampaignPage.getInstance();
        newCampaignsPage.typeInput(driver, NewCampaignPage.TextInput.CampaignName, campaignName);
        newCampaignsPage.select(driver, NewCampaignPage.DropDownMenu.PublisherAccount, PUBLISHER + UNICODE_DOT_SMALL + GOOGLE_ACCOUNT);
        newCampaignsPage.clickButton(driver, NewCampaignPage.Button.NextStep);

        NewGoogleCampaignPage newGoogleCampaignsPage = NewGoogleCampaignPage.getInstance();

        newGoogleCampaignsPage.type(driver, NewGoogleCampaignPage.TextInput.StateDate, startDate);
        newGoogleCampaignsPage.check(driver, NewGoogleCampaignPage.Radio.SetEndDateYes);

        newGoogleCampaignsPage.type(driver, NewGoogleCampaignPage.TextInput.EndDate, endDate);
        newGoogleCampaignsPage.select(driver, NewGoogleCampaignPage.DropDownMenu.Status, CampaignStatus.ACTIVE.toString());
        newGoogleCampaignsPage.select(driver, NewGoogleCampaignPage.DropDownMenu.CampaignType, CampaignType.SHOPPING.toString());

        newGoogleCampaignsPage.select(driver, NewGoogleCampaignPage.DropDownMenu.CountryOfSale, CountryOfSale.India.toString());
        newGoogleCampaignsPage.select(driver, NewGoogleCampaignPage.DropDownMenu.CampaignPriority, campaignPriority);

        newGoogleCampaignsPage.type(driver, NewGoogleCampaignPage.TextInput.MerchantId, merchantId);
        newGoogleCampaignsPage.type(driver, NewGoogleCampaignPage.TextInput.Budget, budget);

        newGoogleCampaignsPage.clickButton(driver, NewGoogleCampaignPage.Button.Save);

        assertEquals("Campaign not created Something went wrong ", successLabel, campaignsPage.getInfo(driver, CampaignsPage.Label.Success));

        campaignsPage.select(driver, CampaignsPage.DropDownMenu.Views, CampaignsPage.CAMPAIGN_VIEW);

        // Open the Campaign via clicking the link and go to Settings tab

        assertEquals("Campaign " + campaignName + " couldn't be opened ", true, campaignsPage.open(driver, campaignName));

        SingleCampaignPage singleCampaignPage = SingleCampaignPage.getInstance();
        singleCampaignPage.select(driver, SingleCampaignPage.Tab.Settings);

        CampaignSettingsPage campaignSettingsPage = CampaignSettingsPage.getInstance();
        assertEquals("Campaign Name in the Settings Page don't match ", campaignName, campaignSettingsPage.getInfo(driver, CampaignSettingsPage.TextInput.CampaignName));
        assertEquals("Campaign Status in the Settings Page don't match ", CampaignStatus.ACTIVE.toString(), campaignSettingsPage.getSelected(driver, CampaignSettingsPage.DropDownMenu.Status));
        assertEquals("Campaign Start Date in the Settings Page don't match ", startDate, campaignSettingsPage.getInfo(driver, CampaignSettingsPage.TextInput.StateDate));
        assertEquals("Campaign End Date in the Settings Page don't match ", endDate, campaignSettingsPage.getInfo(driver, CampaignSettingsPage.TextInput.EndDate));
        assertEquals("Campaign budget in the Settings Page don't match ", budget, campaignSettingsPage.getInfo(driver, CampaignSettingsPage.TextInput.Budget));

        homePage.click(driver, HomePage.Link.Admin);
        ActivityLogPage activityLog = ActivityLogPage.getInstance();

        String postCount = activityLog.getInfo(driver, ActivityLogPage.Label.PostCount);
        while ("0".equalsIgnoreCase(postCount)) {
            homePage.click(driver, HomePage.Link.Admin);
            postCount = activityLog.getInfo(driver, ActivityLogPage.Label.PostCount);
        }

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
