package com.marin.qa.selenium;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.marin.qa.selenium.pageObjects.bubble.Filter;
import com.marin.qa.selenium.pageObjects.pages.ActivityLogPage;
import com.marin.qa.selenium.pageObjects.pages.CampaignsPage;
import com.marin.qa.selenium.pageObjects.pages.HomePage;
import com.marin.qa.selenium.pageObjects.pages.LoginPage;

public abstract class WebdriverBaseClass {

    final private static Logger log = Logger.getLogger(WebdriverBaseClass.class);

    protected static final String PUBLISHER = "Google";
    protected static final String GOOGLE_ACCOUNT = "Goog301";
    protected static final String UNICODE_DOT = " \u2022 ";
    protected static final String UNICODE_DOT_SMALL = " \u00B7 ";
    protected static final String TAB = "\t";
    protected static final String END_OF_LINE = "\\n";
    protected static final String SLASH_N = "\n";
    protected static final Character slash_n = '\n';
    protected static final String SEMICOLON_SPACE = "; ";
    protected static final String COMMA_SPACE = ", ";
    
    protected static final String[] CampaignPriority = {"Low", "Medium", "High"};
    
    protected static final String[] Countries = {"Brazil", "India", "Italy", "United States", "United Kingdom", "Canada"};
    
    protected static String LOGIN = "auto_orca@marinsoftware.com";
    protected static String PASSWORD = "marin2007";
   
    protected static Calendar calendar = Calendar.getInstance();
    protected SimpleDateFormat groupFormaterDate = new SimpleDateFormat("M/d/yy");
    protected SimpleDateFormat groupFormaterTime = new SimpleDateFormat("h:mm a");


    protected static String pageLoadError = "Problem loading page";
    
    public static void loginSuccessful(WebDriver driver) {
        log.info("<--------- Start Login Test --------->");
        log.info("===========================================");
        log.info("Now Running loginSuccessful Test");
        log.info("===========================================");
        LoginPage loginPage = LoginPage.getInstance();
        loginPage.login(driver, LOGIN, PASSWORD);
        log.info("<--------- End Login Test --------->");
    }

    public static void clearAllPendingChanges(WebDriver driver) {

        log.info("===========================================");
        log.info("Now Running clearAllPendingChanges Test");
        log.info("===========================================");
        HomePage homePage = HomePage.getInstance();
        homePage.click(driver, HomePage.Link.Admin);
        ActivityLogPage activityLogPage = ActivityLogPage.getInstance();
        activityLogPage.select(driver, ActivityLogPage.DropDownMenu.Show, "150");
        activityLogPage.check(driver, ActivityLogPage.Column.CheckBox);
        activityLogPage.click(driver, ActivityLogPage.Button.Cancel);
        activityLogPage.select(driver, ActivityLogPage.DropDownMenu.Show, "20");
    }

    public static void logoutSuccessful(WebDriver driver){

        log.info("===========================================");
        log.info("Now Running logoutSuccessful Test");
        log.info("===========================================");
        HomePage homePage = HomePage.getInstance();
        homePage.click(driver, HomePage.Link.Admin);
        homePage.click(driver, HomePage.Link.Logout);

    }

    public void verifyAndPostCartop(WebDriver driver, String description) {

        log.info("===========================================");
        log.info("Now Running verifyAndPostCartop Test");
        log.info("===========================================");

        log.info("go to activity log and verify the Cartops");
        HomePage homePage = HomePage.getInstance();
        homePage.click(driver, HomePage.Link.Admin);
        ActivityLogPage activityLogPage = ActivityLogPage.getInstance();
        String postCount = activityLogPage.getInfo(driver, ActivityLogPage.Label.PostCount);

        while ("0".equalsIgnoreCase(postCount)) {
            homePage.click(driver, HomePage.Link.Admin);
            postCount = activityLogPage.getInfo(driver, ActivityLogPage.Label.PostCount);
        }

        String cartop = activityLogPage.getInfo(driver, ActivityLogPage.Column.ID, ActivityLogPage.Column.Description, description);
        if ("".equalsIgnoreCase(cartop)) {
            homePage.click(driver, HomePage.Link.Admin);
            cartop = activityLogPage.getInfo(driver, ActivityLogPage.Column.ID, ActivityLogPage.Column.Description, description);
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
    }
    
    public void deleteCampaigns(WebDriver driver, String campaignName){

        log.info("Go to campaigns page, filter the campaign and Delete all of them");
        HomePage homePage = HomePage.getInstance();
        homePage.select(driver, HomePage.Tab.Campaigns);

        CampaignsPage campaignsPage = CampaignsPage.getInstance();
        campaignsPage.select(driver, CampaignsPage.DropDownMenu.Views, CampaignsPage.CAMPAIGN_VIEW);
        Filter filter = Filter.getInstance();
        filter.apply(driver, Filter.Column.Campaign, Filter.Menu.Contains, campaignName);

        //get the campaign Count
    }

}
