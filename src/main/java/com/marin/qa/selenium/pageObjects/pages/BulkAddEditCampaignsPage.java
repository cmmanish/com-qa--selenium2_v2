package com.marin.qa.selenium.pageObjects.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class BulkAddEditCampaignsPage extends AbstractPage {

    private static BulkAddEditCampaignsPage instance;
    public static Logger log = Logger.getLogger(BulkAddEditCampaignsPage.class);
    public final String POST_NOW_ID = "postNowText";
    public final String PROGRESS_GRID_CONTAINER = "#progress_grid_container";

    /**
     * Private constructor prevents construction outside this class.
     */
    private BulkAddEditCampaignsPage() {
    }

    public static synchronized BulkAddEditCampaignsPage getInstance() {

        if (instance == null) {
            instance = new BulkAddEditCampaignsPage();
        }

        return instance;
    }

    /**
     * Label Element as list of all labels on page
     *

     * @author mmadhusoodan
     * @version 2.00
     */
    public static enum Label {

        Alert(".bad", "Alert"),
        Success("#campaign_table_flash","Bulk upload is processing. See the Activity Log for details.");

        private String locator;
        private String description;

        private Label(String locator, String description) {
            this.locator = locator;
            this.description = description;
        }

        public String getLocator() {
            return this.locator;
        }

        @Override
        public String toString() {
            return this.description;
        }

    }

    String getInfo(WebDriver driver, Label label){

        String query = "return $('" + label.getLocator() + "').text().trim();";
        //Get Label text
        String retval = (String) ((JavascriptExecutor) driver).executeScript(query);
        return retval;

    }

    /**
     * Link Element as list of all links on page
     *

     * @author mmadhusoodan
     * @version 2.00
     */
    public static enum Link {

        Campaigns("#left_related_tasks a:eq(0)", null, true, "Campaigns"),
        Groups("#left_related_tasks a:eq(1)", null, true, "Groups"),
        Keywords("#left_related_tasks a:eq(2)", null, true, "Keywords"),
        Creatives("#left_related_tasks a:eq(3)", null, true, "Creatives"),
        Placements("#left_related_tasks a:eq(4)", null, true, "Placements"),
        ProductTargets("#left_related_tasks a:eq(5)", null, true, "Product Targets"),
        Revenue("#left_related_tasks a:eq(6)", null, true, "Revenue");

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
     * Radio Element as list of all radio buttons on page
     *

     * @author mmadhusoodan
     * @version 2.00
     */
    public static enum Radio {

        ToBeSent("[name=\"post_to_publisher_options\"]", "PENDING", null, false, "Upload changes in \"To be sent\" status"),
        Held("[name=\"post_to_publisher_options\"]", "PAUSED", null, false, "Upload changes in \"Held\" status");

        private String locator;
        private String value;
        private String spinner;
        private boolean pageLoad;
        private String description;

        private Radio(String locator, String value, String spinner, boolean pageLoad, String description) {
            this.locator = locator;
            this.value = value;
            this.spinner = spinner;
            this.pageLoad = pageLoad;
            this.description = description;
        }

        public String getLocator() {
            return this.locator;
        }

        public String getValue() {
            return this.value;
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
     * DropDownMenu Element as list of drop down menus on page
     *

     * @author mmadhusoodan
     * @version 2.00
     */
    public static enum DropDownMenu {

        PublisherAccount("[name=\"publisher_account_id\"]", null, false, "Publisher Account");

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
    
    public BulkAddEditCampaignsPage select (WebDriver driver, DropDownMenu menu, String option){

        String query = "$('" + menu.getLocator() + "').find('option:contains(\"" + option + "\")').attr('selected',true).change();";
        waitForDropDownElementToBePopulated(driver, menu.getLocator());

        if (isElementPresent(driver, menu.getLocator())) {
            changeElementBackground(driver, menu.getLocator());
            ((JavascriptExecutor) driver).executeScript(query);
            waitForAjaxRequestDone(driver, AJAX_TIMEOUT);
            removeElementBackground(driver, menu.getLocator());
            waitForPageToLoad(driver,LONG_PAGE_TIMEOUT);
            log.info("Select \"" + option + "\" option in \"" + menu.toString() + "\" Drop Down Menu in \"" + this.getClass().getSimpleName() + "\"");
        }
        wait(500);
        return instance;
    }
    
    /**
     * Text Input Element as list of all text inputs on page
     *

     * @author mmadhusoodan
     * @version 2.00
     */
    public static enum TextArea {

        Campaigns("#upload_text_area", "Campaigns");

        private String locator;
        private String description;

        private TextArea(String locator, String description) {
            this.locator = locator;
            this.description = description;
        }

        public String getLocator() {
            return this.locator;
        }

        @Override
        public String toString() {
            return this.description;
        }

    }
    
    public BulkAddEditCampaignsPage type(WebDriver driver, TextArea input, String text) throws Exception {
    
        String query = "$('" + input.getLocator() + "').val('" + text + "');";
        if (isElementPresent(driver, input.getLocator())) {
            changeElementBackground(driver, input.getLocator());
            String retval = (String) ((JavascriptExecutor) driver).executeScript(query);
            wait(100);
            removeElementBackground(driver, input.getLocator());
            log.info("Type \"" + text + "\" in \"" + input.toString() + "\" Text Input in \"" + this.getClass().getSimpleName() + "\"");
        }
        return instance;
    }

    /**
     * Button Element as list of all buttons on page
     *

     * @author mmadhusoodan
     * @version 2.00
     */
    public static enum Button {

        Process("[name=\"process\"]", "#spin_span", true, "Process"),
        ProcessError("[name=\"process\"]", "#spin_span", true, "Process"),
        Cancel("[name=\"cancel\"]", null, false, "Cancel");

        private String locator;
        private String spinner;
        private boolean pageLoad;
        private String description;

        private Button(String locator, String spinner, boolean pageLoad, String description) {
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
    
    public BulkAddEditCampaignsPage click(WebDriver driver, Button button) {

        String query = "$('" + button.getLocator() + "').click();";
        if (isElementPresent(driver, button.getLocator())) {
            changeElementBackground(driver, button.getLocator());
            String retval = (String) ((JavascriptExecutor) driver).executeScript(query);
            wait(100);
            removeElementBackground(driver, button.getLocator());
            log.info("Click " + button.toString() + " button in " + this.getClass().getSimpleName() + "\"");
        }
        
        waitForSpinnerToDissappear(driver, PROGRESS_GRID_CONTAINER);
        
        if(button.getPageLoad()){
            waitForPageToLoad(driver,LONG_PAGE_TIMEOUT);
        }       

       // waitForElementToBeAppear(driver, ACTION_SUCCESS);
        waitForElementToBeAppear(driver, ActivityLogPage.Button.PostChange.toString());
        return instance;
    }
}


