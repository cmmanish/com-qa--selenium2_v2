package com.marin.qa.selenium.pageObjects.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class ActivityLogPage extends AbstractPage {

    public static Logger log = Logger.getLogger(ActivityLogPage.class);
    private static ActivityLogPage instance;

    /**
     * Private constructor prevents construction outside this class.
     */
    private ActivityLogPage() {
    }

    public static synchronized ActivityLogPage getInstance() {

        if (instance == null) {
            instance = new ActivityLogPage();
        }

        return instance;
    }

    /**
     * Label Element as list of all labels on bubble
     *
     * @author mmadhusoodan
     * @version 2.00
     */
    public static enum Label {

        PostCount("#postCount", "PostCount"), 
        ReportLabel(".good", "ReportLabel"), 
        NoDataAlert(".dataGridNotice", "No Data Alert");

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

    /**
     * This method set to return Label text
     */

    public String getInfo(WebDriver driver, Label label) {

        String query = "return $('" + label.getLocator() + "').text().trim();";
        // Get Label text
        String retval = (String) ((JavascriptExecutor) driver).executeScript(query);
        return retval;
    }

    /**
     * DropDownMenu Element as list of all drop down menus on page
     *
     * @author mmadhusoodan
     * @version 2.00
     *          <p/>
     *          as description for DropDownMenu element
     */
    public static enum DropDownMenu {

        Show("#operation_table_row_select", "#grid_overlay_operation_table", true, "Show");

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
     * @return ActivityLogPage
     */
    public ActivityLogPage select(WebDriver driver, DropDownMenu menu, String option) {

        String query = "$('select" + menu.getLocator() + "').find('option:contains(\"" + option + "\")').attr('selected',true).change();";
        if (isElementPresent(driver, menu.getLocator())) {
            changeElementBackground(driver, menu.getLocator());
            String retval = (String) ((JavascriptExecutor) driver).executeScript(query);
            removeElementBackground(driver, menu.getLocator());
            log.info("Select \"" + option + "\" option in \"" + menu.toString() + "\" Drop Down Menu in \"" + this.getClass().getSimpleName() + "\"");

            if (menu.getSpinner() != null) {
                waitForSpinnerToDissappear(driver, menu.getSpinner());
            }

            if (menu.getPageLoad()) {
                waitForPageToLoad(driver, LONG_PAGE_TIMEOUT);
            }
        }
        wait(1500);
        return instance;

    }

    /**
     * Link Element as list of all links on page
     *
     * @author Michael Beider
     * @version 2.00
     *          <p/>
     *          as description for Link element
     */
    public static enum Link {

        Campaigns("#bulk_upload_links_container a:contains(\"Campaigns\")", null, true, "Campaigns"), Groups("#bulk_upload_links_container a:contains(\"Groups\")", null, true, "Groups"), Keywords(
                "#bulk_upload_links_container a:contains(\"Keywords\")", null, true, "Keywords"), NegativeKeywords("#bulk_upload_links_container a:contains(\"NegativeKeywords\")", null, true,
                "NegativeKeywords"), Creatives("#bulk_upload_links_container a:contains(\"Creatives\")", null, true, "Creatives"), Placements(
                "#bulk_upload_links_container a:contains(\"Placements\")", null, true, "Placements"), Sitelinks("#bulk_upload_links_container a:contains(\"Sitelinks\")", null, true, "Sitelinks"), ProductGroups(
                "#bulk_upload_links_container a:contains(\"Product Groups\")", null, true, "Product Groups"), Folders("#bulk_upload_links_container a:contains(\"Folders\")", null, true, "Folders"), Revenue(
                "#bulk_upload_links_container a:contains(\"Revenue\")", null, true, "Revenue"), CreateReport(".jsReportButton", null, true, "Revenue");

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
     * @param link
     * @return void
     * @author mmadhusoodan
     */

    public void click(WebDriver driver, Link link) {
        String query = "$('" + link.getLocator() + "')[0].click();";
        if (isElementPresent(driver, link.getLocator())) {
            changeElementBackground(driver, link.getLocator());
            String retval = (String) ((JavascriptExecutor) driver).executeScript(query);
            removeElementBackground(driver, link.getLocator());
        }
        if (link.getPageLoad()) {
            waitForPageToLoad(driver, LONG_PAGE_TIMEOUT);
        }

        if (link.getSpinner() != null) {
            waitForElementToDissappear(driver, link.getSpinner());
        }

    }

    /**
     * Label Element as list of all labels on bubble
     *
     * @author mmadhusoodan
     * @version 2.00
     *          <p/>
     *          as description for Label element
     */
    public static enum Column {

        CheckBox("#left_table input#id_gridCheckboxAll:checkbox", "#left_table tbody input[name=\"id[]\"]:checkbox", "CheckBox"), ID("#left_table #th_id_display a.sort_link",
                "#left_table tbody td:nth-child(2)", "ID"), Publisher("#right_table #th_publisher a.sort_link", "#right_table tbody td:nth-child(1)", "Publisher"), Description(
                "#right_table #th_description a.sort_link", "#right_table tbody td:nth-child(2)", "Description"), CreationDate("#right_table #th_creation_date a.sort_link",
                "#right_table tbody td:nth-child(3)", "Creation Date"), User("#right_table #th_user a.sort_link", "#right_table tbody td:nth-child(4)", "User"), Status(
                "#right_table #th_activity_log_status a.sort_link", "#right_table tbody td:nth-child(5)", "Status");

        private String tableLocator = "#operation_table ";
        private String headerLocator;
        private String cellLocator;
        private String description;

        private Column(String headerLocator, String cellLocator, String description) {
            this.headerLocator = headerLocator;
            this.cellLocator = cellLocator;
            this.description = description;
        }

        public String getHeaderLocator() {
            return this.tableLocator + this.headerLocator;
        }

        public String getCellLocator() {
            return this.tableLocator + this.cellLocator;
        }

        @Override
        public String toString() {
            return this.description;
        }

    }

    /**
     * This Method set to return information from any column by row index
     */
    public String getInfo(WebDriver driver, Column column, int index) {

        // This Method set to get Info from column based on index
        String query = "return $('" + column.getCellLocator() + "').eq(" + index + ").text();";
        changeElementBackground(driver, column.getCellLocator() + ":eq(" + index + ")");
        String info = (String) ((JavascriptExecutor) driver).executeScript(query);
        removeElementBackground(driver, column.getCellLocator() + ":eq(" + index + ")");
        return info;
    }

    /**
     * This Method set to return information from searched Column by any column information
     */
    public String getInfo(WebDriver driver, Column searchedColumn, Column givenColumn, String option) {

        boolean flag = false;
        int row;
        String info = "";
        String query = "return $('" + Column.ID.getCellLocator() + "').size();";
        // waitForAjaxRequestDone(selenium, AJAX_TIMEOUT);

        // Row count based on "ID" column since it's always present on Activity Log Grid
        long retval = (Long) ((JavascriptExecutor) driver).executeScript(query);

        // long l = Long.parseLong(retval);
        int rowNumber = (int) retval;

        // Main purpose of the loop to find index of row that contains Column information
        for (row = 0; row < rowNumber; row++) {
            String temp = getInfo(driver, givenColumn, row);
            if (option.equalsIgnoreCase(temp) == true) {
                flag = true;
                break;
            }
        }

        // This Method set to get info from searched column
        if (flag) {
            info = getInfo(driver, searchedColumn, row);
        }
        else {
            log.info("Can't get information from \"" + searchedColumn + "\" Column with information \"" + option + "\" in \"" + givenColumn.toString() + "\" Column in \""
                    + this.getClass().getSimpleName() + "\"");
        }

        return info;

    }

    /**
     * This Method set to check CarpOp check box by information in any column
     */
    public boolean check(WebDriver driver, Column column, String option) {

        int rowNumber = 0;
        int row = 0;
        boolean flag = false;
        String query = "return $('" + Column.ID.getCellLocator() + "').size();";
        waitForAjaxRequestDone(driver, AJAX_TIMEOUT);

        // Row count based on "ID" column since it's always present on Activity Log Grid
        long retval = (Long) ((JavascriptExecutor) driver).executeScript(query);

        // long l = Long.parseLong(retval);
        rowNumber = (int) retval;
        // Main purpose of the loop to find index of row that contains Column information
        for (row = 0; row < rowNumber; row++) {
            String info = getInfo(driver, column, row);
            if (option.equalsIgnoreCase(info)) {
                flag = true;
                break;
            }
        }

        // This Method set to check row check box by row index
        if (flag) {
            check(driver, row);
            log.info("Check CheckBox of CarpOp with \"" + option + "\" information in \"" + column.toString() + "\" Column in \"" + this.getClass().getSimpleName() + "\"");
        }
        else {
            log.info("Can't find CheckBox of CarpOp with \"" + option + "\" information in \"" + column.toString() + "\" Column in \"" + this.getClass().getSimpleName() + "\"");
        }

        return flag;

    }

    /**
     * This Method set to check row check box by row index
     */
    public void check(WebDriver driver, int index) {

        String query = "$('" + Column.CheckBox.getCellLocator() + "').eq(" + index + ").trigger('click').change();";
        // This Method set to check row check box by row index
        String re = (String) ((JavascriptExecutor) driver).executeScript(query);

    }

    /**
     * Button Element as list of all buttons on page
     *
     * @version 2.00
     */
    public static enum Button {

        PostNow("#setup_action_sendcartop", "#grid_overlay_operation_table, #progress_grid_container", false, "Post Now"), 
        Hold("#setup_action_pausecartop", null, false, "Hold"), 
        Cancel(
                "#setup_action_cancel", null, false, "Cancel"), 
                PostChange("#postChangeText", null, false, "Change"), 
                Post("##postNowText", null, false, "Post"), 
                PostAllNow(
                "#setup_action_postallnow", null, false, "Post All Now");

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

        public String getId() {
            return this.locator.replace("#", "id=");
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
     */

    public void click(WebDriver driver, Button button) {
        String query = "$('" + button.getLocator() + "')[0].click();";
        if (isElementPresent(driver, button.getLocator())) {
            changeElementBackground(driver, button.getLocator());
            String retval = (String) ((JavascriptExecutor) driver).executeScript(query);
            removeElementBackground(driver, button.getLocator());
        }
        if (button.getPageLoad()) {
            waitForPageToLoad(driver, LONG_PAGE_TIMEOUT);
        }

        if (button.getSpinner() != null) {
            waitForElementToDissappear(driver, button.getSpinner());
        }

    }

    public ActivityLogPage check(WebDriver driver, Column checkbox) {

        String query = "$('" + checkbox.getCellLocator() + "').trigger('click').change();";
        if (isElementPresent(driver, checkbox.getCellLocator())) {
            ((JavascriptExecutor) driver).executeScript(query);
            waitForAjaxRequestDone(driver, AJAX_TIMEOUT);
            log.info("Click on \"" + checkbox.toString() + "\" CheckBox in \"" + this.getClass().getSimpleName() + "\"");
        }

        return instance;
    }

    public void waitForPostCount(WebDriver driver){

        String postCount = getInfo(driver, ActivityLogPage.Label.PostCount);
        int tryCount = 0;
        while ("0".equalsIgnoreCase(postCount) || tryCount==5) {
            wait(500);
            AdminPage adminPage = AdminPage.getInstance();
            adminPage.select(driver, AdminPage.Tab.ActivityLog);
            postCount = getInfo(driver, ActivityLogPage.Label.PostCount);
            tryCount++;
        }
    }

    public String waitForCartopStatus(WebDriver driver, String cartop) {

        boolean  found = false;
        if (cartop == null) {
            log.info("Cartop sent was Null = " + cartop);
            return null;
        }
        String status = "-1";
        ActivityLogPage activityLogPage = ActivityLogPage.getInstance();
        AdminPage adminPage = AdminPage.getInstance();
        while (!found) {
            adminPage.select(driver, AdminPage.Tab.ActivityLog);
            status = activityLogPage.getInfo(driver, ActivityLogPage.Column.Status, ActivityLogPage.Column.ID, cartop);
            log.info("CartOp # " + cartop + " got \"" + status + "\" status in \"ActivityLogPage\"");
            log.info(status);
            if ("Succeeded".equalsIgnoreCase(status) || 
                    "Failed".equalsIgnoreCase(status) || 
                        "Cancelled".equalsIgnoreCase(status) || 
                            "Error - To be resent".equalsIgnoreCase(status) ||
                                "Held".equalsIgnoreCase(status)) {
                                    found = true;
                                  //  return status;
            }
            else {
                log.info("Going to sleep for 3 sec ");
                sleep(THREE_SECONDS); found = false;
            }
        }
        return status;

    }
}
