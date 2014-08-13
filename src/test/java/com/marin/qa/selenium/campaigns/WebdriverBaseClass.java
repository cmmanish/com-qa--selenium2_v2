package com.marin.qa.selenium.campaigns;


import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.marin.qa.selenium.common.MarinApp;
import com.marin.qa.selenium.pageObjects.pages.ActivityLogPage;
import com.marin.qa.selenium.pageObjects.pages.HomePage;
import com.marin.qa.selenium.pageObjects.pages.LoginPage;

public abstract class WebdriverBaseClass {

    static Logger log = Logger.getLogger(WebdriverBaseClass.class);

    public static WebDriver driver = MarinApp.getApp();

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

    protected static String LOGIN = "auto_orca@marinsoftware.com";
    protected static String PASSWORD = "marin2007";
   
    protected static Calendar calendar = Calendar.getInstance();
    protected SimpleDateFormat groupFormaterDate = new SimpleDateFormat("M/d/yy");
    protected SimpleDateFormat groupFormaterTime = new SimpleDateFormat("h:mm a");


    protected static String pageLoadError = "Problem loading page";
    
    public static void LoginSuccessful() {
        log.info("<--------- Start Login Test --------->");
        LoginPage loginPage = LoginPage.getInstance();
        loginPage.login(driver, LOGIN, PASSWORD);
        log.info("<--------- End Login Test --------->");
    }

    public static void clearAllPendingChanges(WebDriver selenium) {

        HomePage homePage = HomePage.getInstance();
        homePage.click(driver, HomePage.Link.Admin);
        ActivityLogPage activityLogPage = ActivityLogPage.getInstance();
        activityLogPage.select(driver, ActivityLogPage.DropDownMenu.Show, "150");
        activityLogPage.check(driver, ActivityLogPage.Column.CheckBox);
        activityLogPage.click(driver, ActivityLogPage.Button.Cancel);
        activityLogPage.select(driver, ActivityLogPage.DropDownMenu.Show, "20");
    }

    public static void logoutSuccessful(){

        HomePage homePage = HomePage.getInstance();
        homePage.click(driver, HomePage.Link.Admin);
        homePage.click(driver, HomePage.Link.Logout);

    }
}
