package com.marin.qa.selenium.campaigns;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import com.marin.qa.selenium.WebdriverBaseClass;
import com.marin.qa.selenium.Util.QaRandom;
import com.marin.qa.selenium.common.MarinApp;
import com.marin.qa.selenium.pageObjects.bubble.Filter;
import com.marin.qa.selenium.pageObjects.pages.CampaignSettingsPage;
import com.marin.qa.selenium.pageObjects.pages.CampaignsPage;
import com.marin.qa.selenium.pageObjects.pages.HomePage;
import com.marin.qa.selenium.pageObjects.pages.NewCampaignPage;
import com.marin.qa.selenium.pageObjects.pages.NewGoogleCampaignPage;
import com.marin.qa.selenium.pageObjects.pages.NewGoogleCampaignPage.CampaignStatus;
import com.marin.qa.selenium.pageObjects.pages.NewGoogleCampaignPage.CampaignType;
import com.marin.qa.selenium.pageObjects.pages.NewGoogleCampaignPage.CountryOfSale;
import com.marin.qa.selenium.pageObjects.pages.SingleCampaignPage;

public class SingleCreateCampaignsTest extends WebdriverBaseClass {

    public static Logger log = Logger.getLogger(SingleCreateCampaignsTest.class);
    public QaRandom random = QaRandom.getInstance();

    public static WebDriver driver = MarinApp.getApp();

    public SingleCreateCampaignsTest() {
        log.info("===========================================");
        log.info("Now Running SingleCreateCampaignsTest Suite");
        log.info("===========================================");
    }

    @BeforeClass
    public static void testSetUp() {
        log.info("<--------- Start Setup Test --------->");
        loginSuccessful(driver);
        clearAllPendingChanges(driver);
        log.info("<--------- End Setup Test --------->");
    }

    @AfterClass
    public static void testCleanup() {
        log.info("<--------- Start Logout Test --------->");
        clearAllPendingChanges(driver);
        logoutSuccessful(driver);
        driver.close();
        log.info("<--------- End Logout Test --------->");
    }

    @After
    public void RunAfterEachTest() {
        log.info("Running RunAfterEachTest()");
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

        verifyAndPostCartop(driver, singleCreateCampaign);

        homePage.select(driver, HomePage.Tab.Campaigns);
        log.info("go to Campaign settings and verify the settings ");
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

        log.info("End: T1SingleCreateGoogleShoppingCampaignNonUS()");
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

    }

    @Test
    public void T2SingleCreateGoogleShoppingCampaignUS() throws Exception {

        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        log.info("Start: T2SingleCreateGoogleShoppingCampaignUS");

        String campaignName = random.getRandomStringWithPrefix("CampaignNameT2", 5);
        String merchantId = "100543509";
        String countryOfSale = "United States";
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

        newGoogleCampaignsPage.type(driver, NewGoogleCampaignPage.TextInput.MerchantId, merchantId);
        newGoogleCampaignsPage.type(driver, NewGoogleCampaignPage.TextInput.Budget, budget);

        newGoogleCampaignsPage.clickButton(driver, NewGoogleCampaignPage.Button.Save);

        verifyAndPostCartop(driver, singleCreateCampaign);

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

        assertEquals("Campaign MerchantId in the Settings Page don't match ", merchantId, campaignSettingsPage.getInfo(driver, CampaignSettingsPage.Label.MerchantId));
        assertEquals("Campaign Country Of Sale in the Settings Page don't match ", countryOfSale, campaignSettingsPage.getInfo(driver, CampaignSettingsPage.Label.CountryOfSale));

        assertEquals("Campaign budget in the Settings Page don't match ", budget, campaignSettingsPage.getInfo(driver, CampaignSettingsPage.TextInput.Budget));

        log.info("T2SingleCreateGoogleShoppingCampaignUSShoppingChannelBoth");
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }

    @Test
    public void T3SingleCreateGoogleSearchNetworkCampaignSearchPartnerDistribution() throws Exception {

        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        String campaignName = random.getRandomStringWithPrefix("CampaignNameT3", 5);

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

        newGoogleCampaignsPage.check(driver, NewGoogleCampaignPage.Checkbox.KeywordMatching);

        newGoogleCampaignsPage.type(driver, NewGoogleCampaignPage.TextInput.Budget, budget);

        newGoogleCampaignsPage.clickButton(driver, NewGoogleCampaignPage.Button.Save);

        verifyAndPostCartop(driver, singleCreateCampaign);

        homePage.select(driver, HomePage.Tab.Campaigns);
        log.info("go to campaign settings and verify the settings ");

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
        assertEquals("Display Partners Distribution didn't match ", false, campaignSettingsPage.isChecked(driver, CampaignSettingsPage.Checkbox.DisplaySelect));
        assertEquals("KeywordMatching didn't match ", true, campaignSettingsPage.isChecked(driver, CampaignSettingsPage.Checkbox.KeywordMatching));

        assertEquals("Campaign budget in the Settings Page don't match ", budget, campaignSettingsPage.getInfo(driver, CampaignSettingsPage.TextInput.Budget));

        homePage.click(driver, HomePage.Link.Admin);

        log.info("End: T1SingleCreateGoogleShoppingCampaignNonUS()");
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }

    @Test
    public void T4SingleCreateGoogleSearchNetworkCampaignDisplaySelectDistribution() throws Exception {

        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        log.info("T4SingleCreateGoogleSearchNetworkCampaignDisplaySelectDistribution()");
        String campaignName = random.getRandomStringWithPrefix("CampaignNameT4", 5);

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

        verifyAndPostCartop(driver, singleCreateCampaign);

        homePage.select(driver, HomePage.Tab.Campaigns);
        campaignsPage.select(driver, CampaignsPage.DropDownMenu.Views, CampaignsPage.CAMPAIGN_VIEW);
        log.info("go to Campaign settings and verify the settings ");
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
        assertEquals("Display Partners Distribution didn't match ", true, campaignSettingsPage.isChecked(driver, CampaignSettingsPage.Checkbox.DisplaySelect));
        assertEquals("KeywordMatching didn't match ", true, campaignSettingsPage.isChecked(driver, CampaignSettingsPage.Checkbox.KeywordMatching));

        assertEquals("Campaign budget in the Settings Page don't match ", budget, campaignSettingsPage.getInfo(driver, CampaignSettingsPage.TextInput.Budget));

        homePage.click(driver, HomePage.Link.Admin);

        log.info("End: T4SingleCreateGoogleShoppingCampaignNonUS()");
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }

    @Test
    public void T5SingleCreateGoogleSearchNetworkCampaignAllDistribution() throws Exception {

        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        log.info("Start: T5SingleCreateGoogleSearchNetworkCampaignAllDistribution()");
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
        newGoogleCampaignsPage.check(driver, NewGoogleCampaignPage.Checkbox.DisplaySelect);
        newGoogleCampaignsPage.check(driver, NewGoogleCampaignPage.Checkbox.KeywordMatching);

        newGoogleCampaignsPage.type(driver, NewGoogleCampaignPage.TextInput.Budget, budget);

        newGoogleCampaignsPage.clickButton(driver, NewGoogleCampaignPage.Button.Save);

        verifyAndPostCartop(driver, singleCreateCampaign);

        homePage.select(driver, HomePage.Tab.Campaigns);
        campaignsPage.select(driver, CampaignsPage.DropDownMenu.Views, CampaignsPage.CAMPAIGN_VIEW);
        log.info("go to Campaign settings and verify the settings ");
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
        assertEquals("Display Partners Distribution didn't match ", true, campaignSettingsPage.isChecked(driver, CampaignSettingsPage.Checkbox.DisplaySelect));
        assertEquals("KeywordMatching didn't match ", true, campaignSettingsPage.isChecked(driver, CampaignSettingsPage.Checkbox.KeywordMatching));

        assertEquals("Campaign budget in the Settings Page don't match ", budget, campaignSettingsPage.getInfo(driver, CampaignSettingsPage.TextInput.Budget));

        homePage.click(driver, HomePage.Link.Admin);

        log.info("End: T5SingleCreateGoogleSearchNetworkCampaignAllDistribution()");
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }

    @Test
    public void T6SingleCreateGoogleDisplayNetworkOnlyCampaign() throws Exception {

        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        log.info("Start: T6SingleCreateGoogleDisplayNetworkOnlyCampaign()");
        String campaignName = random.getRandomStringWithPrefix("CampaignNameT6", 5);

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

        verifyAndPostCartop(driver, singleCreateCampaign);

        homePage.select(driver, HomePage.Tab.Campaigns);
        campaignsPage.select(driver, CampaignsPage.DropDownMenu.Views, CampaignsPage.CAMPAIGN_VIEW);
        log.info("go to Campaign settings and verify the settings ");

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

        log.info("Start: T6SingleCreateGoogleDisplayNetworkOnlyCampaign()");
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }

}
