package com.marin.qa.selenium.pageObjects.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class HomePage extends AbstractPage {

    private static HomePage instance;
    static Logger log = Logger.getLogger(HomePage.class);

    /**
     * Private constructor prevents construction outside this class.
     */
    private HomePage() {
    }

    public static synchronized HomePage getInstance() {

        if (instance == null) {
            instance = new HomePage();
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
    
    /**
     * TextPillow Element as list of all TextPillows on page
     * 
     * @version 2.00
     * @param id
     *        as jQuery mapping for element
     * @param dropDownInputId
     *        as jQuery mapping for text input element
     * @param dropDownClassName
     *        as jQuery mapping for drop down menu element
     * @author mmadhusoodan
     */
    public static enum DropDownMenu {

        Client("#client_dd_0_input", "#client_dd_0", ".dropdown_item", "client_name");

        private String id;
        private String dropDownInputId;
        private String dropDownClassName;
        private String attributeName;

        private DropDownMenu(String id, String dropDownInputId, String dropDownClassName, String attributeName) {

            this.id = id;
            this.dropDownInputId = dropDownInputId;
            this.dropDownClassName = dropDownClassName;
            this.attributeName = attributeName;
        }

        public String getId() {
            return this.id;
        }

        public String getDropDownInputId() {
            return this.dropDownInputId;
        }

        public String getDropDownClassName() {
            return this.dropDownClassName;
        }

        public String getAttributeName() {
            return this.attributeName;
        }
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

        Home("#tabs_container a:contains(\"Home\")", null, true, "Home"), 
        Channels("#tabs_container a:contains(\"Channels\")", null, true, "Channels"), 
        Visitors("#tabs_container a:contains(\"Visitors\")", null, true, "Visitors"),
        Campaigns("#tabs_container a:contains(\"Campaigns\")", "#grid_overlay_campaign_table", true, "Campaigns"), 
        Groups("#tabs_container a:contains(\"Groups\")", "#grid_overlay_group_table", true, "Groups"), 
        Keywords("#tabs_container a:contains(\"Keywords\")", null, true, "Keywords"), 
        Creatives("#tabs_container a:contains(\"Creatives\")", "#grid_overlay_creative_table", true, "Creatives"),
        Placements("#tabs_container a:contains(\"Placements\")", null, true, "Placements"), 
        ProductTargets("#tabs_container a:contains(\"Product Targets\")", null, true, "Product Targets"),
        Bidding("#tabs_container a:contains(\"Bidding\")", null, true, "Bidding"), 
        Dimensions("#tabs_container a:contains(\"Dimensions\")", null, true, "Dimensions"), 
        Segments("#tabs_container a:contains(\"Segments\")", null, true, "Segments"), 
        Dashboard("#sub_navigation_container a:contains(\"Dashboard\")", null, true, "Dashboard"),
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
        Logout("#top_right_logout_link", null, false, "Logout");

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
        if (isElementPresent(driver, link.getLocator())) {
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
