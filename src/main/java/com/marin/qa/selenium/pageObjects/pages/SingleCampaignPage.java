package com.marin.qa.selenium.pageObjects.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class SingleCampaignPage extends AbstractPage {

    private static SingleCampaignPage instance;
    static Logger log = Logger.getLogger(SingleCampaignPage.class);

    /**
     * Private constructor prevents construction outside this class.
     */
    private SingleCampaignPage() {
    }

    public static synchronized SingleCampaignPage getInstance() {

        if (instance == null) {
            instance = new SingleCampaignPage();
        }

        return instance;
    }

    /**
     * Label Element as list of all labels on page
     * 
     * @version 2.00
     * @param locator
     *        as jQuery mapping for Label element
     * @param description
     *        as description for Label element
     * @author mmadhusoodan
     */
    public static enum Label {

        SingleClient(".single_client_name", "Company");
        
        private String locator;
        private String description;

        private Label(String locator, String description) {
            this.locator = locator;
        }

        public String getLocator() {
            return this.locator;
        }

        @Override
        public String toString() {
            return this.description;
        }

    }
    
    public static enum DropDownMenu {

        Show("#operation_table_row_select", "#grid_overlay_operation_table", false, "Show");

        private String locator;
        private String spinner;
        private boolean pageLoad;
        private String description;

        private DropDownMenu(String locator, String spinner, boolean pageLoad, String description) {
            this.locator = locator;
            this.spinner = spinner;
            this.pageLoad = pageLoad;
            this.description = description;
        }

        public String getLocator() {
            return this.locator;
        }

        public String getSpinner() {
            return this.spinner;
        }

        public boolean getPageLoad() {
            return this.pageLoad;
        }

        @Override
        public String toString() {
            return this.description;
        }

    }
    
    /**
     * This Method set to select option in drop down menu
     * 
     * @author mbeider 
     * @param selenium
     * @param menu
     * @param option
     * @return ActivityLogPage
     * 
     */
    public SingleCampaignPage select(WebDriver driver, DropDownMenu menu, String option) {

        String query = "$('select" + menu.getLocator() + "').find('option:contains(\"" + option + "\")').attr('selected',true).change();";
        if (isElementPresent(driver, menu.getLocator()) == true) {
            changeElementBackground(driver, menu.getLocator());
            String retval = (String) ((JavascriptExecutor) driver).executeScript(query);
            removeElementBackground(driver, menu.getLocator());
            log.info("Select \"" + option + "\" option in \"" + menu.toString() + "\" Drop Down Menu in \"" + this.getClass().getSimpleName() + "\"");

            if (menu.getSpinner() != null) {
                waitForElementToDissappear(driver, menu.getSpinner());
            }

            if (menu.getPageLoad()) {
                waitForPageToLoad(driver,LONG_PAGE_TIMEOUT);
            }
        }

        return instance;

    }
    /**
     * Tab Element as list of all buttons on page
     * 
     * @version 2.00
     * @param locator
     *        as jQuery mapping for Tabs element locator
     * @param spinner
     *        as jQuery mapping for spinner
     * @param pageLoad
     *        as jQuery mapping for pageLoad
     * @param description
     *        as description for Tab element
     * @author mmadhusoodan
     */
    public static enum Tab {

        Groups("#sub_navigation_container a:contains(\"Groups\")", "#grid_overlay_group_table", true, "Groups"), 
        Keywords("#sub_navigation_container a:contains(\"Keywords\")", null, true, "Keywords"), 
        Placements("#sub_navigation_container a:contains(\"Placements\")", null, true, "Placements"), 
        ProductTargets("#sub_navigation_container a:contains(\"Product Targets\")", null, true, "Product Targets"),
        Negatives("#sub_navigation_container a:contains(\"Bidding\")", null, true, "Negatives"), 
        Creatives("#sub_navigation_container a:contains(\"Creatives\")", "#grid_overlay_creative_table", true, "Creatives"),
        AdExtensions("#sub_navigation_container a:contains(\"Segments\")", null, true, "Ad Extensions"), 
        Settings("#sub_navigation_container a:contains(\"Settings\")", null, true, "Settings"),
        History("#sub_navigation_container a:contains(\"History\")", null, true, "History");

        private String locator;
        private String spinner;
        private boolean pageLoad;
        private String description;

        private Tab(String locator, String spinner, boolean pageLoad, String description) {
            this.locator = locator;
            this.spinner = spinner;
            this.pageLoad = pageLoad;
            this.description = description;
        }

        public String getLocator() {
            return this.locator;
        }

        public String getSpinner() {
            return this.spinner;
        }

        public boolean getPageLoad() {
            return this.pageLoad;
        }

        @Override
        public String toString() {
            return this.description;
        }

    }

    /**
     * This Method set to click on Tab on Home page
     * 
     * @author mmadhusoodan
     * @param selenium
     * @param tab
     * @return void
     * 
     */
    public void select(WebDriver driver, Tab tab) {
        String query = "$('" + tab.getLocator() + "')[0].click();";
        if (isElementPresent(driver, tab.getLocator()) == true) {
            changeElementBackground(driver, tab.getLocator());
            String retval = (String) ((JavascriptExecutor) driver).executeScript(query);
            log.info(retval);;
            removeElementBackground(driver, tab.getLocator());
            log.info("Select \"" + tab.toString() + "\" Tab in \"" + this.getClass().getSimpleName() + "\"");
    
            if (tab.getPageLoad()) {
                waitForPageToLoad(driver, LONG_PAGE_TIMEOUT);
            }
    
            if (tab.getSpinner() != null) {
                waitForElementToDissappear(driver, tab.getSpinner());
            }
        }
    
    }

    /**
     * Link Element as list of all links on page
     * 
     * @version 2.00
     * @param locator
     *        as jQuery mapping for Tabs element locator
     * @param spinner
     *        as jQuery mapping for spinner
     * @param pageLoad
     *        as jQuery mapping for pageLoad
     * @param description
     *        as description for Link element
     * @author mmadhusoodan
     */
    public static enum Link {

        Admin("#acc_left a:eq(0)", "#grid_overlay_operation_table, #progress_grid_container", true, "Admin"), 
        Reports("#acc_left a:eq(1)", null, false, "Reports"), 
        Help("#acc_left a:eq(2)", null,false, "Help"), 
        Logout("#acc_left a:eq(3)", null, true, "Logout");

        private String locator;
        private String spinner;
        private boolean pageLoad;
        private String description;

        private Link(String locator, String spinner, boolean pageLoad, String description) {
            this.locator = locator;
            this.spinner = spinner;
            this.pageLoad = pageLoad;
            this.description = description;
        }

        public String getLocator() {
            return this.locator;
        }

        public String getSpinner() {
            return this.spinner;
        }

        public boolean getPageLoad() {
            return this.pageLoad;
        }

        @Override
        public String toString() {
            return this.description;
        }

    }

    /**
     * This Method set to click on link on page
     * 
     * @author mmadhusoodan
     * @param selenium
     * @param link
     * @return void
     * 
     */
    public void click(WebDriver driver, Link link) {
        String query = "$('" + link.getLocator() + "')[0].click();";
        if (isElementPresent(driver, link.getLocator()) == true) {
            changeElementBackground(driver, link.getLocator());
            wait(100);
            removeElementBackground(driver, link.getLocator());
            String retval = (String) ((JavascriptExecutor) driver).executeScript(query);
            log.info("Press \"" + link.toString() + "\" Link in \"" + this.getClass().getSimpleName() + "\"");

            if (link.getPageLoad()) {
                waitForPageToLoad(driver, LONG_PAGE_TIMEOUT);
            }

            if (link.getSpinner() != null) {
                waitForElementToDissappear(driver, link.getSpinner());
            }
        }
    }

}
