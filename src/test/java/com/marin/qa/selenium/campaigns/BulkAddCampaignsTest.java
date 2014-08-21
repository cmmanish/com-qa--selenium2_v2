package com.marin.qa.selenium.campaigns;

import com.marin.qa.selenium.WebdriverBaseClass;
import com.marin.qa.selenium.common.MarinApp;
import com.marin.qa.selenium.common.QaRandom;
import com.marin.qa.selenium.pageObjects.pages.ActivityLogPage;
import com.marin.qa.selenium.pageObjects.pages.BulkAddEditCampaignsPage;
import com.marin.qa.selenium.pageObjects.pages.CampaignSettingsPage;
import com.marin.qa.selenium.pageObjects.pages.CampaignsPage;
import com.marin.qa.selenium.pageObjects.pages.HomePage;
import com.marin.qa.selenium.pageObjects.pages.SingleCampaignPage;
import com.marin.qa.selenium.pageObjects.pages.NewGoogleCampaignPage.CampaignStatus;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;

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


    /*
    Sample Bulk Sheet
    Account	Campaign	Network	Merchant ID	Country of Sale	Campaign Priority	Shopping Channels
    Goog301	active shop app	Shopping	100543509	US	Low	Online
     */
    @Test
    public void T1BulkCreateGoogleShoppingCampaignUS() throws Exception {

        String account = GOOGLE_ACCOUNT;
        String campaignName = random.getRandomString("CampaignName", 5);
        String status = "Active";
        String dailyBudget = "1.11";
        String network = "";
        String merchantId = "100543509";
        String countryofSale = "US";
        String campaignPriority = "High";
        String shoppingChannels = "online";
        String successLabel = "Bulk upload is processing. See the Activity Log for details.";

        String bulkCreateCampaign = "Bulk Create: Google Campaign: "+campaignName+".";
    
        calendar.setTime(Calendar.getInstance().getTime());
        final String startDate = groupFormaterDate.format(calendar.getTime());
        calendar.add(Calendar.MONTH, 1);
        final String endDate = groupFormaterDate.format(calendar.getTime());

        String headers = "Account\tCampaign\tStatus\tStart Date\tCampaign End Date\tDaily Budget\t" +
                "Network\tMerchant ID\tCountry of Sale\tCampaign Priority\tShopping Channels\\n" ;

        String contents = account + TAB + campaignName + TAB + status + TAB + startDate + TAB + endDate + TAB + dailyBudget + TAB +
                network + TAB + merchantId + TAB + countryofSale +  TAB + campaignPriority + TAB + shoppingChannels + END_OF_LINE;

        HomePage homePage = HomePage.getInstance();
        CampaignsPage campaignsPage = CampaignsPage.getInstance();

        ActivityLogPage activityLogPage = ActivityLogPage.getInstance();
        activityLogPage.click(driver, ActivityLogPage.Link.Campaigns);
        BulkAddEditCampaignsPage bulkAddEditCampaignsPage = BulkAddEditCampaignsPage.getInstance();
        bulkAddEditCampaignsPage.type(driver, BulkAddEditCampaignsPage.TextArea.Campaigns, headers + contents);
        bulkAddEditCampaignsPage.click(driver, BulkAddEditCampaignsPage.Button.Process);
//        assertEquals("Couldn't find Success Label", successLabel, campaignsPage.getInfo(driver, CampaignsPage.Label.Success));

        homePage.click(driver, HomePage.Link.Admin);
        String postCount = activityLogPage.getInfo(driver, ActivityLogPage.Label.PostCount);
        while ("0".equalsIgnoreCase(postCount)) {
            homePage.click(driver, HomePage.Link.Admin);
            postCount = activityLogPage.getInfo(driver, ActivityLogPage.Label.PostCount);
        }

        String cartop = activityLogPage.getInfo(driver, ActivityLogPage.Column.ID, ActivityLogPage.Column.Description, bulkCreateCampaign);
        if (cartop.equalsIgnoreCase("")){
            homePage.click(driver, HomePage.Link.Admin);
            cartop = activityLogPage.getInfo(driver, ActivityLogPage.Column.ID, ActivityLogPage.Column.Description, bulkCreateCampaign);
        }
        log.info("cartop is "+cartop);
        //activityLogPage.check(driver, ActivityLogPage.Column.ID, cartop);
        //activityLogPage.click(driver, ActivityLogPage.Button.PostNow);
        
        //Verify the Campaign Settings 
        homePage.select(driver, HomePage.Tab.Campaigns);
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
        assertEquals("Campaign budget in the Settings Page don't match ", dailyBudget, campaignSettingsPage.getInfo(driver, CampaignSettingsPage.TextInput.Budget));
        
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
