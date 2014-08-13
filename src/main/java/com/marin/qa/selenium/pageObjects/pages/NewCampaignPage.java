package com.marin.qa.selenium.pageObjects.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import com.thoughtworks.selenium.Selenium;

public class NewCampaignPage extends AbstractPage {

    static Logger log = Logger.getLogger(NewCampaignPage.class);
    private static NewCampaignPage instance;

    /**
     * Private constructor prevents construction outside this class.
     */
    private NewCampaignPage() {
    }

    public static synchronized NewCampaignPage getInstance() {

        if (instance == null) {
            instance = new NewCampaignPage();
        }

        return instance;
    }

    /**
     * Link Element as list of all links on page
     * 
     * @version 2.00
     * @param locator
     *        as jQuery mapping for Button element locator
     * @param spinner
     *        as jQuery mapping for spinner
     * @param pageLoad
     *        as jQuery mapping for pageLoad
     * @param description
     *        as description for Button element
     * @author mmadhusoodan
     */
    public static enum Button {

        NextStep("[name=\"next\"]", null, true, "Next Step"), 
        Cancel("[name=\"cancel\"]", null, true, "Cancel");

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
            return description;
        }
    }

    /**
     * This Method set to click on Button on page
     *
     * @author mbeider
     * @param selenium
     * @param button
     * @return CampaignsPage
     *
     */
    public NewCampaignPage clickButton(WebDriver driver, Button button) {

        String query = "$('" + button.getLocator() + "')[0].click();";

        if (isElementPresent(driver, button.getLocator())) {
            changeElementBackground(driver, button.getLocator());
            String retval = (String) ((JavascriptExecutor) driver).executeScript(query);
            log.info(retval);
            removeElementBackground(driver, button.getLocator());
            log.info("Press \"" + button.toString() + "\" Button in \"" + this.getClass().getSimpleName() + "\"");

            wait(300);

            if (button.getSpinner() != null) {
                waitForElementToDissappear(driver, button.getSpinner());
            }

            if (button.getPageLoad()) {
                waitForPageToLoad(driver, LONG_PAGE_TIMEOUT);
            }
        }
        return instance;
    }

    public static enum TextInput {

        CampaignName("[name=\"campaignName\"]", "Campaign Name");

        private String locator;
        private String description;

        private TextInput(String locator, String description) {
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

    public NewCampaignPage typeInput(WebDriver driver, TextInput input, String text) {

        String query = "$('" + input.getLocator() + "').val('" + text + "').trigger('keyup');";
        if (isElementPresent(driver, input.getLocator()) == true) {
            changeElementBackground(driver, input.getLocator());
            String retval = (String) ((JavascriptExecutor) driver).executeScript(query);
            wait(100);
            removeElementBackground(driver, input.getLocator());
            log.info("Type \"" + text + "\" in \"" + input.toString() + "\" Text Input in \"" + this.getClass().getSimpleName() + "\"");
        }

        return instance;
    }

    public static enum DropDownMenu {

        PublisherAccount("[name=\"publisherClientAccountId\"]", null, false, "Publisher Account");

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

    public NewCampaignPage select(WebDriver driver, DropDownMenu menu, String option) throws Exception {

        String query = "$('" + menu.getLocator() + "').find('option:contains(\"" + option + "\")').attr('selected',true).change();";
        waitForDropDownElementToBePopulated(driver, menu.getLocator());

        if (isElementPresent(driver, menu.getLocator()) == true) {
            changeElementBackground(driver, menu.getLocator());
            ((JavascriptExecutor) driver).executeScript(query);
            waitForAjaxRequestDone(driver, AJAX_TIMEOUT);
            removeElementBackground(driver, menu.getLocator());
            log.info("Select \"" + option + "\" option in \"" + menu.toString() + "\" Drop Down Menu in \"" + this.getClass().getSimpleName() + "\"");
        }

        return instance;

    }

}
