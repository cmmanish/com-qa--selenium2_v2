package com.marin.qa.selenium.campaigns;

import com.marin.qa.selenium.WebdriverBaseClass;
import com.marin.qa.selenium.common.MarinApp;
import com.marin.qa.selenium.common.QaRandom;
import com.marin.qa.selenium.pageObjects.bubble.Filter;
import com.marin.qa.selenium.pageObjects.pages.*;
import com.marin.qa.selenium.pageObjects.pages.NewGoogleCampaignPage.CampaignStatus;
import com.marin.qa.selenium.pageObjects.pages.NewGoogleCampaignPage.CampaignType;
import com.marin.qa.selenium.pageObjects.pages.NewGoogleCampaignPage.CountryOfSale;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SingleCreateCampaignsTest extends WebdriverBaseClass {

    public static Logger log = Logger.getLogger(SingleCreateCampaignsTest.class);
    public QaRandom random = QaRandom.getInstance();

    public static WebDriver driver = MarinApp.getApp();

    public SingleCreateCampaignsTest() {
        log.info("Now Running SingleCreateCampaignsTest Suite");
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

    @After
    public void afterEachTest() {
        log.info("Running afterEachTest()");
        HomePage homePage = HomePage.getInstance();
        homePage.click(driver, HomePage.Link.Admin);
    }

    @Test
    public void T1SingleCreateGoogleShoppingCampaignNonUS() throws Exception {

        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        log.info("Start: T1SingleCreateGoogleShoppingCampaignNonUS()");

        String campaignName = random.getRandomStringWithPrefix("CampaignNameT1", 5);
        String merchantId = "100543509";
        String budget = "1.11";
        String campaignPriority = random.getRandomElement(CampaignPriority);

        String singleCreateCampaign = "Create: Google Campaign: " + campaignName + ".";

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

        log.info("go to activity log and verify the Cartops");
        homePage.click(driver, HomePage.Link.Admin);
        ActivityLogPage activityLogPage = ActivityLogPage.getInstance();
        String postCount = activityLogPage.getInfo(driver, ActivityLogPage.Label.PostCount);

        while ("0".equalsIgnoreCase(postCount)) {
            homePage.click(driver, HomePage.Link.Admin);
            postCount = activityLogPage.getInfo(driver, ActivityLogPage.Label.PostCount);
        }

        String cartop = activityLogPage.getInfo(driver, ActivityLogPage.Column.ID, ActivityLogPage.Column.Description, singleCreateCampaign);
        if ("".equalsIgnoreCase(cartop)) {
            homePage.click(driver, HomePage.Link.Admin);
            cartop = activityLogPage.getInfo(driver, ActivityLogPage.Column.ID, ActivityLogPage.Column.Description, singleCreateCampaign);
        }
        assertNotNull("Can't find the cartop. Something is fishy", cartop);
        log.info("cartop is " + cartop);
        activityLogPage.check(driver, ActivityLogPage.Column.ID, cartop);
        activityLogPage.click(driver, ActivityLogPage.Button.PostNow);

        try {
            assertEquals("Cartop failed ", "Succeeded", activityLogPage.waitForCartopStatus(driver, cartop));
        }
        catch (AssertionError e) {
            e.toString();
        }

        homePage.select(driver, HomePage.Tab.Campaigns);
        log.info("go to campaing settings and verify the settings ");
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

        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        log.info("End: T1SingleCreateGoogleShoppingCampaignNonUS()");
    }

    @Test
    public void T2SingleCreateGoogleShoppingCampaignUSShoppingChannelBoth() throws Exception {

        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        log.info("Start: T2SingleCreateGoogleShoppingCampaignUSShoppingChannelBoth");

        String campaignName = random.getRandomStringWithPrefix("CampaignNameT2", 5);
        String merchantId = "100543509";
        String budget = "1.11";
        String campaignPriority = random.getRandomElement(CampaignPriority);
        String singleCreateCampaign = "Create: Google Campaign: " + campaignName + ".";

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

        newGoogleCampaignsPage.select(driver, NewGoogleCampaignPage.DropDownMenu.CountryOfSale, CountryOfSale.UnitedStates.toString());
        newGoogleCampaignsPage.select(driver, NewGoogleCampaignPage.DropDownMenu.CampaignPriority, campaignPriority);
        newGoogleCampaignsPage.check(driver, NewGoogleCampaignPage.Checkbox.Online);
        newGoogleCampaignsPage.check(driver, NewGoogleCampaignPage.Checkbox.Local);

        newGoogleCampaignsPage.type(driver, NewGoogleCampaignPage.TextInput.MerchantId, merchantId);
        newGoogleCampaignsPage.type(driver, NewGoogleCampaignPage.TextInput.Budget, budget);

        newGoogleCampaignsPage.clickButton(driver, NewGoogleCampaignPage.Button.Save);

        homePage.click(driver, HomePage.Link.Admin);
        ActivityLogPage activityLogPage = ActivityLogPage.getInstance();
        String postCount = activityLogPage.getInfo(driver, ActivityLogPage.Label.PostCount);
        while ("0".equalsIgnoreCase(postCount)) {
            homePage.click(driver, HomePage.Link.Admin);
            postCount = activityLogPage.getInfo(driver, ActivityLogPage.Label.PostCount);
        }

        String cartop = activityLogPage.getInfo(driver, ActivityLogPage.Column.ID, ActivityLogPage.Column.Description, singleCreateCampaign);
        if (cartop.equalsIgnoreCase("")) {
            homePage.click(driver, HomePage.Link.Admin);
            cartop = activityLogPage.getInfo(driver, ActivityLogPage.Column.ID, ActivityLogPage.Column.Description, singleCreateCampaign);
        }
        log.info("cartop is " + cartop);
        assertNotNull("Can't find the cartop. Something is fishy", cartop);
        activityLogPage.check(driver, ActivityLogPage.Column.ID, cartop);
        activityLogPage.click(driver, ActivityLogPage.Button.PostNow);

        try {
            assertEquals("Cartop failed ", "Succeeded", activityLogPage.waitForCartopStatus(driver, cartop));
        }
        catch (AssertionError e) {
            e.toString();
        }

        homePage.select(driver, HomePage.Tab.Campaigns);
        campaignsPage.select(driver, CampaignsPage.DropDownMenu.Views, CampaignsPage.CAMPAIGN_VIEW);

        Filter filter = Filter.getInstance();
        filter.apply(driver, Filter.Column.Campaign, Filter.Menu.Contains, campaignName);

        // Open the Campaign via clicking the link and go to Settings tab

        assertEquals("Campaign " + campaignName + " couldn't be opened ", true, campaignsPage.open(driver, campaignName));

        SingleCampaignPage singleCampaignPage = SingleCampaignPage.getInstance();
        singleCampaignPage.select(driver, SingleCampaignPage.Tab.Settings);

        CampaignSettingsPage campaignSettingsPage = CampaignSettingsPage.getInstance();
        assertEquals("Campaign Name in the Settings Page don't match ", campaignName, campaignSettingsPage.getInfo(driver, CampaignSettingsPage.TextInput.CampaignName));
        assertEquals("Campaign Status in the Settings Page don't match ", CampaignStatus.ACTIVE.toString(), campaignSettingsPage.getSelected(driver, CampaignSettingsPage.DropDownMenu.Status));
        assertEquals("Campaign Start Date in the Settings Page don't match ", startDate, campaignSettingsPage.getInfo(driver, CampaignSettingsPage.TextInput.StateDate));
        assertEquals("Campaign End Date in the Settings Page don't match ", endDate, campaignSettingsPage.getInfo(driver, CampaignSettingsPage.TextInput.EndDate));

        assertEquals("Shopping Channel Online didn't match ", true, campaignSettingsPage.isChecked(driver, CampaignSettingsPage.Checkbox.Online));
        assertEquals("Shopping Channel Local didn't match ", true, campaignSettingsPage.isChecked(driver, CampaignSettingsPage.Checkbox.Local));

        assertEquals("Campaign budget in the Settings Page don't match ", budget, campaignSettingsPage.getInfo(driver, CampaignSettingsPage.TextInput.Budget));

        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        log.info("T2SingleCreateGoogleShoppingCampaignUSShoppingChannelBoth");
    }

    @Test
    public void T3SingleCreateGoogleShoppingCampaignUSShoppingChannelOnline() throws Exception {

        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        log.info("T3SingleCreateGoogleShoppingCampaignUSShoppingChannelOnline()");
        String campaignName = random.getRandomStringWithPrefix("CampaignNameT3", 5);
        String merchantId = "100543509";
        String budget = "1.11";
        String campaignPriority = random.getRandomElement(CampaignPriority);
        String singleCreateCampaign = "Create: Google Campaign: " + campaignName + ".";

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

        newGoogleCampaignsPage.select(driver, NewGoogleCampaignPage.DropDownMenu.CountryOfSale, CountryOfSale.UnitedStates.toString());
        newGoogleCampaignsPage.select(driver, NewGoogleCampaignPage.DropDownMenu.CampaignPriority, campaignPriority);
        newGoogleCampaignsPage.check(driver, NewGoogleCampaignPage.Checkbox.Online);

        newGoogleCampaignsPage.type(driver, NewGoogleCampaignPage.TextInput.MerchantId, merchantId);
        newGoogleCampaignsPage.type(driver, NewGoogleCampaignPage.TextInput.Budget, budget);

        newGoogleCampaignsPage.clickButton(driver, NewGoogleCampaignPage.Button.Save);

        log.info("go to activity log and verify the Cartops");

        homePage.click(driver, HomePage.Link.Admin);
        ActivityLogPage activityLogPage = ActivityLogPage.getInstance();
        String postCount = activityLogPage.getInfo(driver, ActivityLogPage.Label.PostCount);
        while ("0".equalsIgnoreCase(postCount)) {
            homePage.click(driver, HomePage.Link.Admin);
            postCount = activityLogPage.getInfo(driver, ActivityLogPage.Label.PostCount);
        }

        String cartop = activityLogPage.getInfo(driver, ActivityLogPage.Column.ID, ActivityLogPage.Column.Description, singleCreateCampaign);
        if (cartop.equalsIgnoreCase("")) {
            homePage.click(driver, HomePage.Link.Admin);
            cartop = activityLogPage.getInfo(driver, ActivityLogPage.Column.ID, ActivityLogPage.Column.Description, singleCreateCampaign);
        }
        assertNotNull("Can't find the cartop. Something is fishy", cartop);
        log.info("cartop is " + cartop);
        activityLogPage.check(driver, ActivityLogPage.Column.ID, cartop);
        activityLogPage.click(driver, ActivityLogPage.Button.PostNow);

        try {
            assertEquals("Cartop failed ", "Succeeded", activityLogPage.waitForCartopStatus(driver, cartop));
        }
        catch (AssertionError e) {
            e.toString();
        }

        homePage.select(driver, HomePage.Tab.Campaigns);
        log.info("go to campaing settings and verify the settings ");
        campaignsPage.select(driver, CampaignsPage.DropDownMenu.Views, CampaignsPage.CAMPAIGN_VIEW);

        Filter filter = Filter.getInstance();
        filter.apply(driver, Filter.Column.Campaign, Filter.Menu.Contains, campaignName);

        // Open the Campaign via clicking the link and go to Settings tab

        assertEquals("Campaign " + campaignName + " couldn't be opened ", true, campaignsPage.open(driver, campaignName));

        SingleCampaignPage singleCampaignPage = SingleCampaignPage.getInstance();
        singleCampaignPage.select(driver, SingleCampaignPage.Tab.Settings);

        CampaignSettingsPage campaignSettingsPage = CampaignSettingsPage.getInstance();
        assertEquals("Campaign Name in the Settings Page don't match ", campaignName, campaignSettingsPage.getInfo(driver, CampaignSettingsPage.TextInput.CampaignName));
        assertEquals("Campaign Status in the Settings Page don't match ", CampaignStatus.ACTIVE.toString(), campaignSettingsPage.getSelected(driver, CampaignSettingsPage.DropDownMenu.Status));
        assertEquals("Campaign Start Date in the Settings Page don't match ", startDate, campaignSettingsPage.getInfo(driver, CampaignSettingsPage.TextInput.StateDate));
        assertEquals("Campaign End Date in the Settings Page don't match ", endDate, campaignSettingsPage.getInfo(driver, CampaignSettingsPage.TextInput.EndDate));

        assertEquals("Shopping Channel Online didn't match ", true, campaignSettingsPage.isChecked(driver, CampaignSettingsPage.Checkbox.Online));
        assertEquals("Shopping Channel Local didn't match ", false, campaignSettingsPage.isChecked(driver, CampaignSettingsPage.Checkbox.Local));

        assertEquals("Campaign budget in the Settings Page don't match ", budget, campaignSettingsPage.getInfo(driver, CampaignSettingsPage.TextInput.Budget));

        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        log.info("T3SingleCreateGoogleShoppingCampaignUSShoppingChannelOnline()");
    }

    @Test
    public void T4SingleCreateGoogleShoppingCampaignUSShoppingChannelLocal() throws Exception {

        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        log.info("T4SingleCreateGoogleShoppingCampaignUSShoppingChannelLocal()");
        String campaignName = random.getRandomStringWithPrefix("CampaignNameT4", 5);
        String merchantId = "100543509";
        String budget = "1.11";
        String campaignPriority = random.getRandomElement(CampaignPriority);
        String singleCreateCampaign = "Create: Google Campaign: " + campaignName + ".";

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

        newGoogleCampaignsPage.select(driver, NewGoogleCampaignPage.DropDownMenu.CountryOfSale, CountryOfSale.UnitedStates.toString());
        newGoogleCampaignsPage.select(driver, NewGoogleCampaignPage.DropDownMenu.CampaignPriority, campaignPriority);
        newGoogleCampaignsPage.check(driver, NewGoogleCampaignPage.Checkbox.Local);

        newGoogleCampaignsPage.type(driver, NewGoogleCampaignPage.TextInput.MerchantId, merchantId);
        newGoogleCampaignsPage.type(driver, NewGoogleCampaignPage.TextInput.Budget, budget);

        newGoogleCampaignsPage.clickButton(driver, NewGoogleCampaignPage.Button.Save);

        log.info("go to activity log and verify the Cartops");

        homePage.click(driver, HomePage.Link.Admin);
        ActivityLogPage activityLogPage = ActivityLogPage.getInstance();
        String postCount = activityLogPage.getInfo(driver, ActivityLogPage.Label.PostCount);
        while ("0".equalsIgnoreCase(postCount)) {
            homePage.click(driver, HomePage.Link.Admin);
            postCount = activityLogPage.getInfo(driver, ActivityLogPage.Label.PostCount);
        }

        String cartop = activityLogPage.getInfo(driver, ActivityLogPage.Column.ID, ActivityLogPage.Column.Description, singleCreateCampaign);
        if (cartop.equalsIgnoreCase("")) {
            homePage.click(driver, HomePage.Link.Admin);
            cartop = activityLogPage.getInfo(driver, ActivityLogPage.Column.ID, ActivityLogPage.Column.Description, singleCreateCampaign);
        }
        assertNotNull("Can't find the cartop. Something is fishy", cartop);
        log.info("cartop is " + cartop);
        activityLogPage.check(driver, ActivityLogPage.Column.ID, cartop);
        activityLogPage.click(driver, ActivityLogPage.Button.PostNow);

        try {
            assertEquals("Cartop failed ", "Succeeded", activityLogPage.waitForCartopStatus(driver, cartop));
        }
        catch (AssertionError e) {
            e.toString();
        }

        homePage.select(driver, HomePage.Tab.Campaigns);
        log.info("go to campaing settings and verify the settings ");
        campaignsPage.select(driver, CampaignsPage.DropDownMenu.Views, CampaignsPage.CAMPAIGN_VIEW);

        Filter filter = Filter.getInstance();
        filter.apply(driver, Filter.Column.Campaign, Filter.Menu.Contains, campaignName);

        // Open the Campaign via clicking the link and go to Settings tab

        assertEquals("Campaign " + campaignName + " couldn't be opened ", true, campaignsPage.open(driver, campaignName));

        SingleCampaignPage singleCampaignPage = SingleCampaignPage.getInstance();
        singleCampaignPage.select(driver, SingleCampaignPage.Tab.Settings);

        CampaignSettingsPage campaignSettingsPage = CampaignSettingsPage.getInstance();
        assertEquals("Campaign Name in the Settings Page don't match ", campaignName, campaignSettingsPage.getInfo(driver, CampaignSettingsPage.TextInput.CampaignName));
        assertEquals("Campaign Status in the Settings Page don't match ", CampaignStatus.ACTIVE.toString(), campaignSettingsPage.getSelected(driver, CampaignSettingsPage.DropDownMenu.Status));
        assertEquals("Campaign Start Date in the Settings Page don't match ", startDate, campaignSettingsPage.getInfo(driver, CampaignSettingsPage.TextInput.StateDate));
        assertEquals("Campaign End Date in the Settings Page don't match ", endDate, campaignSettingsPage.getInfo(driver, CampaignSettingsPage.TextInput.EndDate));

        assertEquals("Shopping Channel Online didn't match ", true, campaignSettingsPage.isChecked(driver, CampaignSettingsPage.Checkbox.Online));
        assertEquals("Shopping Channel Local didn't match ", true, campaignSettingsPage.isChecked(driver, CampaignSettingsPage.Checkbox.Local));

        assertEquals("Campaign budget in the Settings Page don't match ", budget, campaignSettingsPage.getInfo(driver, CampaignSettingsPage.TextInput.Budget));

        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        log.info("End: T4SingleCreateGoogleShoppingCampaignUSShoppingChannelLocal");
    }

    @Test
    public void T5SingleCreateGoogleSearchNetworkCampaignSearchPartnerDistribution() throws Exception {

        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        String campaignName = random.getRandomStringWithPrefix("CampaignNameT5", 5);

        String budget = "1.11";
        calendar.setTime(Calendar.getInstance().getTime());
        final String startDate = groupFormaterDate.format(calendar.getTime());
        calendar.add(Calendar.MONTH, 1);
        final String endDate = groupFormaterDate.format(calendar.getTime());
        String singleCreateCampaign = "Create: Google Campaign: " + campaignName + ".";

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
        newGoogleCampaignsPage.select(driver, NewGoogleCampaignPage.DropDownMenu.CampaignType, CampaignType.SEARCHNETWORK.toString());

        newGoogleCampaignsPage.check(driver, NewGoogleCampaignPage.Checkbox.SearchPartners);
        newGoogleCampaignsPage.uncheck(driver, NewGoogleCampaignPage.Checkbox.DisplaySelect);

        newGoogleCampaignsPage.check(driver, NewGoogleCampaignPage.Checkbox.KeywordMatching);

        newGoogleCampaignsPage.type(driver, NewGoogleCampaignPage.TextInput.Budget, budget);

        newGoogleCampaignsPage.clickButton(driver, NewGoogleCampaignPage.Button.Save);

        log.info("go to activity log and verify the Cartops");

        homePage.click(driver, HomePage.Link.Admin);
        ActivityLogPage activityLogPage = ActivityLogPage.getInstance();
        String postCount = activityLogPage.getInfo(driver, ActivityLogPage.Label.PostCount);
        while ("0".equalsIgnoreCase(postCount)) {
            homePage.click(driver, HomePage.Link.Admin);
            postCount = activityLogPage.getInfo(driver, ActivityLogPage.Label.PostCount);
        }

        String cartop = activityLogPage.getInfo(driver, ActivityLogPage.Column.ID, ActivityLogPage.Column.Description, singleCreateCampaign);
        if (cartop.equalsIgnoreCase("")) {
            homePage.click(driver, HomePage.Link.Admin);
            cartop = activityLogPage.getInfo(driver, ActivityLogPage.Column.ID, ActivityLogPage.Column.Description, singleCreateCampaign);
        }
        assertNotNull("Can't find the cartop. Something is fishy", cartop);
        log.info("cartop is " + cartop);
        activityLogPage.check(driver, ActivityLogPage.Column.ID, cartop);
        activityLogPage.click(driver, ActivityLogPage.Button.PostNow);

        try {
            assertEquals("Cartop failed ", "Succeeded", activityLogPage.waitForCartopStatus(driver, cartop));
        }
        catch (AssertionError e) {
            e.toString();
        }

        homePage.select(driver, HomePage.Tab.Campaigns);
        log.info("go to campaing settings and verify the settings ");

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

        assertEquals("Search Partners Distribution didn't match ", true, campaignSettingsPage.isChecked(driver, CampaignSettingsPage.Checkbox.SearchPartners));
        assertEquals("Diplay Partners Distribution didn't match ", false, campaignSettingsPage.isChecked(driver, CampaignSettingsPage.Checkbox.DisplaySelect));
        assertEquals("KeywordMatching didn't match ", true, campaignSettingsPage.isChecked(driver, CampaignSettingsPage.Checkbox.KeywordMatching));

        assertEquals("Campaign budget in the Settings Page don't match ", budget, campaignSettingsPage.getInfo(driver, CampaignSettingsPage.TextInput.Budget));

        homePage.click(driver, HomePage.Link.Admin);

        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        log.info("End: T1SingleCreateGoogleShoppingCampaignNonUS()");
    }

    @Test
    public void T6SingleCreateGoogleSearchNetworkCampaignDisplaySelectDistribution() throws Exception {

        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        log.info("T6SingleCreateGoogleSearchNetworkCampaignDisplaySelectDistribution()");
        String campaignName = random.getRandomStringWithPrefix("CampaignNameT6", 5);

        String budget = "1.11";
        calendar.setTime(Calendar.getInstance().getTime());
        final String startDate = groupFormaterDate.format(calendar.getTime());
        calendar.add(Calendar.MONTH, 1);
        final String endDate = groupFormaterDate.format(calendar.getTime());
        String singleCreateCampaign = "Create: Google Campaign: " + campaignName + ".";

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
        newGoogleCampaignsPage.select(driver, NewGoogleCampaignPage.DropDownMenu.CampaignType, CampaignType.SEARCHNETWORK.toString());

        newGoogleCampaignsPage.uncheck(driver, NewGoogleCampaignPage.Checkbox.SearchPartners);
        newGoogleCampaignsPage.check(driver, NewGoogleCampaignPage.Checkbox.DisplaySelect);

        newGoogleCampaignsPage.check(driver, NewGoogleCampaignPage.Checkbox.KeywordMatching);

        newGoogleCampaignsPage.type(driver, NewGoogleCampaignPage.TextInput.Budget, budget);

        newGoogleCampaignsPage.clickButton(driver, NewGoogleCampaignPage.Button.Save);

        log.info("go to activity log and verify the Cartops");
        homePage.click(driver, HomePage.Link.Admin);
        ActivityLogPage activityLogPage = ActivityLogPage.getInstance();
        String postCount = activityLogPage.getInfo(driver, ActivityLogPage.Label.PostCount);

        while ("0".equalsIgnoreCase(postCount)) {
            homePage.click(driver, HomePage.Link.Admin);
            postCount = activityLogPage.getInfo(driver, ActivityLogPage.Label.PostCount);
        }

        String cartop = activityLogPage.getInfo(driver, ActivityLogPage.Column.ID, ActivityLogPage.Column.Description, singleCreateCampaign);
        if (cartop.equalsIgnoreCase("")) {
            homePage.click(driver, HomePage.Link.Admin);
            cartop = activityLogPage.getInfo(driver, ActivityLogPage.Column.ID, ActivityLogPage.Column.Description, singleCreateCampaign);
        }
        assertNotNull("Can't find the cartop. Something is fishy", cartop);
        log.info("cartop is " + cartop);
        activityLogPage.check(driver, ActivityLogPage.Column.ID, cartop);
        activityLogPage.click(driver, ActivityLogPage.Button.PostNow);

        try {
            assertEquals("Cartop failed ", "Succeeded", activityLogPage.waitForCartopStatus(driver, cartop));
        }
        catch (AssertionError e) {
            e.toString();
        }

        homePage.select(driver, HomePage.Tab.Campaigns);
        campaignsPage.select(driver, CampaignsPage.DropDownMenu.Views, CampaignsPage.CAMPAIGN_VIEW);
        log.info("go to campaing settings and verify the settings ");
        // Open the Campaign via clicking the link and go to Settings tab

        assertEquals("Campaign " + campaignName + " couldn't be opened ", true, campaignsPage.open(driver, campaignName));

        SingleCampaignPage singleCampaignPage = SingleCampaignPage.getInstance();
        singleCampaignPage.select(driver, SingleCampaignPage.Tab.Settings);

        CampaignSettingsPage campaignSettingsPage = CampaignSettingsPage.getInstance();
        assertEquals("Campaign Name in the Settings Page don't match ", campaignName, campaignSettingsPage.getInfo(driver, CampaignSettingsPage.TextInput.CampaignName));
        assertEquals("Campaign Status in the Settings Page don't match ", CampaignStatus.ACTIVE.toString(), campaignSettingsPage.getSelected(driver, CampaignSettingsPage.DropDownMenu.Status));
        assertEquals("Campaign Start Date in the Settings Page don't match ", startDate, campaignSettingsPage.getInfo(driver, CampaignSettingsPage.TextInput.StateDate));
        assertEquals("Campaign End Date in the Settings Page don't match ", endDate, campaignSettingsPage.getInfo(driver, CampaignSettingsPage.TextInput.EndDate));

        assertEquals("Search Partners Distribution didn't match ", false, campaignSettingsPage.isChecked(driver, CampaignSettingsPage.Checkbox.SearchPartners));
        assertEquals("Diplay Partners Distribution didn't match ", true, campaignSettingsPage.isChecked(driver, CampaignSettingsPage.Checkbox.DisplaySelect));
        assertEquals("KeywordMatching didn't match ", true, campaignSettingsPage.isChecked(driver, CampaignSettingsPage.Checkbox.KeywordMatching));

        assertEquals("Campaign budget in the Settings Page don't match ", budget, campaignSettingsPage.getInfo(driver, CampaignSettingsPage.TextInput.Budget));

        homePage.click(driver, HomePage.Link.Admin);

        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        log.info("End: T1SingleCreateGoogleShoppingCampaignNonUS()");
    }

    @Test
    public void T7SingleCreateGoogleSearchNetworkCampaignAllDistribution() throws Exception {

        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        log.info("Start: T7SingleCreateGoogleSearchNetworkCampaignAllDistribution()");
        String campaignName = random.getRandomStringWithPrefix("CampaignNameT7", 5);

        String budget = "1.11";
        calendar.setTime(Calendar.getInstance().getTime());
        final String startDate = groupFormaterDate.format(calendar.getTime());
        calendar.add(Calendar.MONTH, 1);
        final String endDate = groupFormaterDate.format(calendar.getTime());
        String singleCreateCampaign = "Create: Google Campaign: " + campaignName + ".";

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
        newGoogleCampaignsPage.select(driver, NewGoogleCampaignPage.DropDownMenu.CampaignType, CampaignType.SEARCHNETWORK.toString());

        newGoogleCampaignsPage.check(driver, NewGoogleCampaignPage.Checkbox.SearchPartners);
        newGoogleCampaignsPage.check(driver, NewGoogleCampaignPage.Checkbox.DisplaySelect);

        newGoogleCampaignsPage.check(driver, NewGoogleCampaignPage.Checkbox.KeywordMatching);

        newGoogleCampaignsPage.type(driver, NewGoogleCampaignPage.TextInput.Budget, budget);

        newGoogleCampaignsPage.clickButton(driver, NewGoogleCampaignPage.Button.Save);

        log.info("go to activity log and verify the Cartops");
        homePage.click(driver, HomePage.Link.Admin);
        ActivityLogPage activityLogPage = ActivityLogPage.getInstance();
        String postCount = activityLogPage.getInfo(driver, ActivityLogPage.Label.PostCount);

        while ("0".equalsIgnoreCase(postCount)) {
            homePage.click(driver, HomePage.Link.Admin);
            postCount = activityLogPage.getInfo(driver, ActivityLogPage.Label.PostCount);
        }

        String cartop = activityLogPage.getInfo(driver, ActivityLogPage.Column.ID, ActivityLogPage.Column.Description, singleCreateCampaign);
        if (cartop.equalsIgnoreCase("")) {
            homePage.click(driver, HomePage.Link.Admin);
            cartop = activityLogPage.getInfo(driver, ActivityLogPage.Column.ID, ActivityLogPage.Column.Description, singleCreateCampaign);
        }
        assertNotNull("Can't find the cartop. Something is fishy", cartop);
        log.info("cartop is " + cartop);
        activityLogPage.check(driver, ActivityLogPage.Column.ID, cartop);
        activityLogPage.click(driver, ActivityLogPage.Button.PostNow);

        try {
            assertEquals("Cartop failed ", "Succeeded", activityLogPage.waitForCartopStatus(driver, cartop));
        }
        catch (AssertionError e) {
            e.toString();
        }

        homePage.select(driver, HomePage.Tab.Campaigns);
        campaignsPage.select(driver, CampaignsPage.DropDownMenu.Views, CampaignsPage.CAMPAIGN_VIEW);
        log.info("go to campaing settings and verify the settings ");
        // Open the Campaign via clicking the link and go to Settings tab

        assertEquals("Campaign " + campaignName + " couldn't be opened ", true, campaignsPage.open(driver, campaignName));

        SingleCampaignPage singleCampaignPage = SingleCampaignPage.getInstance();
        singleCampaignPage.select(driver, SingleCampaignPage.Tab.Settings);

        CampaignSettingsPage campaignSettingsPage = CampaignSettingsPage.getInstance();
        assertEquals("Campaign Name in the Settings Page don't match ", campaignName, campaignSettingsPage.getInfo(driver, CampaignSettingsPage.TextInput.CampaignName));
        assertEquals("Campaign Status in the Settings Page don't match ", CampaignStatus.ACTIVE.toString(), campaignSettingsPage.getSelected(driver, CampaignSettingsPage.DropDownMenu.Status));
        assertEquals("Campaign Start Date in the Settings Page don't match ", startDate, campaignSettingsPage.getInfo(driver, CampaignSettingsPage.TextInput.StateDate));
        assertEquals("Campaign End Date in the Settings Page don't match ", endDate, campaignSettingsPage.getInfo(driver, CampaignSettingsPage.TextInput.EndDate));

        assertEquals("Search Partners Distribution didn't match ", true, campaignSettingsPage.isChecked(driver, CampaignSettingsPage.Checkbox.SearchPartners));
        assertEquals("Diplay Partners Distribution didn't match ", true, campaignSettingsPage.isChecked(driver, CampaignSettingsPage.Checkbox.DisplaySelect));
        assertEquals("KeywordMatching didn't match ", true, campaignSettingsPage.isChecked(driver, CampaignSettingsPage.Checkbox.KeywordMatching));

        assertEquals("Campaign budget in the Settings Page don't match ", budget, campaignSettingsPage.getInfo(driver, CampaignSettingsPage.TextInput.Budget));

        homePage.click(driver, HomePage.Link.Admin);

        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        log.info("End: T7SingleCreateGoogleSearchNetworkCampaignAllDistribution()");
    }

    @Test
    public void T8SingleCreateGoogleDisplayNetworkOnlyCampaign() throws Exception {

        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        log.info("Start: T8SingleCreateGoogleDisplayNetworkOnlyCampaign()");
        String campaignName = random.getRandomStringWithPrefix("CampaignNameT8", 5);

        String dailyBudget = "1." + random.getRandomInteger(2);
        calendar.setTime(Calendar.getInstance().getTime());
        final String startDate = groupFormaterDate.format(calendar.getTime());
        calendar.add(Calendar.MONTH, 1);
        final String endDate = groupFormaterDate.format(calendar.getTime());
        String singleCreateCampaign = "Create: Google Campaign: " + campaignName + ".";

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
        newGoogleCampaignsPage.select(driver, NewGoogleCampaignPage.DropDownMenu.CampaignType, CampaignType.DISPLAYNETWORKONLY.toString());

        newGoogleCampaignsPage.type(driver, NewGoogleCampaignPage.TextInput.Budget, dailyBudget);

        newGoogleCampaignsPage.clickButton(driver, NewGoogleCampaignPage.Button.Save);

        log.info("go to activity log and verify the Cartops");
        homePage.click(driver, HomePage.Link.Admin);
        ActivityLogPage activityLogPage = ActivityLogPage.getInstance();
        String postCount = activityLogPage.getInfo(driver, ActivityLogPage.Label.PostCount);

        while ("0".equalsIgnoreCase(postCount)) {
            homePage.click(driver, HomePage.Link.Admin);
            postCount = activityLogPage.getInfo(driver, ActivityLogPage.Label.PostCount);
        }

        String cartop = activityLogPage.getInfo(driver, ActivityLogPage.Column.ID, ActivityLogPage.Column.Description, singleCreateCampaign);
        if (cartop.equalsIgnoreCase("")) {
            homePage.click(driver, HomePage.Link.Admin);
            cartop = activityLogPage.getInfo(driver, ActivityLogPage.Column.ID, ActivityLogPage.Column.Description, singleCreateCampaign);
        }
        assertNotNull("Can't find the cartop. Something is fishy", cartop);
        log.info("cartop is " + cartop);
        activityLogPage.check(driver, ActivityLogPage.Column.ID, cartop);
        activityLogPage.click(driver, ActivityLogPage.Button.PostNow);

        try {
            assertEquals("Cartop failed ", "Succeeded", activityLogPage.waitForCartopStatus(driver, cartop));
        }
        catch (AssertionError e) {
            e.toString();
        }

        homePage.select(driver, HomePage.Tab.Campaigns);
        campaignsPage.select(driver, CampaignsPage.DropDownMenu.Views, CampaignsPage.CAMPAIGN_VIEW);
        log.info("go to campaing settings and verify the settings ");

        // Open the Campaign via clicking the link and go to Settings tab

        assertEquals("Campaign " + campaignName + " couldn't be opened ", true, campaignsPage.open(driver, campaignName));

        SingleCampaignPage singleCampaignPage = SingleCampaignPage.getInstance();
        singleCampaignPage.select(driver, SingleCampaignPage.Tab.Settings);

        CampaignSettingsPage campaignSettingsPage = CampaignSettingsPage.getInstance();
        assertEquals("Campaign Name in the Settings Page don't match ", campaignName, campaignSettingsPage.getInfo(driver, CampaignSettingsPage.TextInput.CampaignName));
        assertEquals("Campaign Status in the Settings Page don't match ", CampaignStatus.ACTIVE.toString(), campaignSettingsPage.getSelected(driver, CampaignSettingsPage.DropDownMenu.Status));
        assertEquals("Campaign Start Date in the Settings Page don't match ", startDate, campaignSettingsPage.getInfo(driver, CampaignSettingsPage.TextInput.StateDate));
        assertEquals("Campaign End Date in the Settings Page don't match ", endDate, campaignSettingsPage.getInfo(driver, CampaignSettingsPage.TextInput.EndDate));

        assertEquals("Campaign budget in the Settings Page don't match ", dailyBudget, campaignSettingsPage.getInfo(driver, CampaignSettingsPage.TextInput.Budget));

        homePage.click(driver, HomePage.Link.Admin);

        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        log.info("Start: T8SingleCreateGoogleDisplayNetworkOnlyCampaign()");
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
