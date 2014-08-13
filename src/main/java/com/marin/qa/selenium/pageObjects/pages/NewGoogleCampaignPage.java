package com.marin.qa.selenium.pageObjects.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class NewGoogleCampaignPage extends AbstractPage {

    static Logger log = Logger.getLogger(NewGoogleCampaignPage.class);
    private static NewGoogleCampaignPage instance;
    
    private static CampaignsPage CampaignsPage ; 

    /**
     * Private constructor prevents construction outside this class.
     */
    private NewGoogleCampaignPage() {
    }

    public static synchronized NewGoogleCampaignPage getInstance() {

        if (instance == null) {
            instance = new NewGoogleCampaignPage();
        }

        return instance;
    }

    /**
     * Link Element as list of all links on page
     * 
     * @version 2.00
     * @param locator
     *        as jQuery mapping for Label element locator
     * @param spinner
     *        as jQuery mapping for spinner
     * @param pageLoad
     *        as jQuery mapping for pageLoad
     * @param description
     *        as description for Label element
     * @author mmadhusoodan
     */
    public static enum Label {

        SaveError("#bad", "Success");
        
        private String locator;
          private String description;

        private Label(String locator, String description) {
            this.locator = locator;
            this.description = description;

        }

        public String getLocator() {
            return this.locator;
        }

        public String getId() {
            return this.locator.replace("#", "id=");
        }

        @Override
        public String toString() {
            return description;
        }
    }
    
    public String getInfo(WebDriver driver, Label label) throws Exception {

        String query = "$('" + label.getLocator() + "').text().trim();";
        //Get Label text
        String retval = (String) ((JavascriptExecutor) driver).executeScript(query);
        return retval;
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

        Save("[name=\"save\"]", "Save"),
        Cancel("[name=\"cancel\"]", "Cancel"),
        AdScheduling("[name=\"editBidAdj\"]", "Ad Scheduling"),
        AdvancedSettings("[name=\"advSettings\"]", "Advanced Settings"),
        Geotarget("[name=\"geotarget\"]", "Geo Targeting"),
        languagetarget("[name=\"languagetarget\"]", "Language targeting");
        
        private String locator;
        private String spinner;
        private boolean pageLoad;
        private String description;

        private Button(String locator, String description) {
            this.locator = locator;
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
     * @author mmadhusoodan
     * @param selenium
     * @param button
     * @return CampaignsPage
     *
     */
    public CampaignsPage clickButton(WebDriver driver, Button button) throws Exception {

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
        return CampaignsPage;
    }
    
    public static enum Checkbox {
        
        Online("#shoppingChannelOnline", null, false, "Online"),
        Local("#shoppingChannelLocal", null, false, "Local"),
        SearchPartners("[name=\"distributionNetwork\"]", null, false, "Search Partners"),
        DisplaySelect("[name=\"distributionContent\"]", null, false, "Display Select"),
        KeywordMatching("[name=\"keywordMatchingOnInput\"]", null, false, "Keyword Matching");
        
        private String locator;
        private String spinner;
        private boolean pageLoad;
        private String description;
    
        private Checkbox(String locator, String spinner, boolean pageLoad, String description) {
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
     *  This Method set to check the check box selection
     *
     * @author mmadhusoodan
     * @param selenium
     * @param category
     * @return NewGoogleCampaignPage
     *
     */
    public NewGoogleCampaignPage check(WebDriver driver, Checkbox checkbox) throws Exception {
        
        String query = "$('" + checkbox.getLocator() + "').prop('checked',true);";
        if(isElementPresent(driver, checkbox.getLocator())){
            ((JavascriptExecutor) driver).executeScript(query);
            waitForAjaxRequestDone(driver, AJAX_TIMEOUT);
            log.info("Check on \"" + checkbox.toString() + "\" CheckBox in \"" + this.getClass().getSimpleName() + "\"");
        }

        return instance;
    }
    
    /**
     *  This Method set to clean check box selection
     *
     * @author mmadhusoodan
     * @param selenium
     * @param category
     * @return NewGoogleCampaignPage
     *
     */
    public NewGoogleCampaignPage uncheck(WebDriver driver, Checkbox checkbox) throws Exception{

        String query = "$('" + checkbox.getLocator() + "').prop('checked',false);";
        if(isElementPresent(driver, checkbox.getLocator())){
            String retval = (String) ((JavascriptExecutor) driver).executeScript(query);
            log.info(retval);
            wait(100);
            waitForAjaxRequestDone(driver, AJAX_TIMEOUT);
            log.info("Uncheck on \"" + checkbox.toString() + "\" CheckBox in \"" + this.getClass().getSimpleName() + "\"");
        }
        return instance;

    }
    
    /**
     * This method set to return status of Radio Button
     *
     * @author mmadhusoodan
     * @param selenium
     * @param radio
     * @return boolean
     *
     */
    public boolean isChecked(WebDriver driver, Checkbox checkbox) throws Exception {

        //Get Checkbox status
        String query = "return $('" + checkbox.getLocator() + "').is(':checked')";
        boolean ret = (Boolean) ((JavascriptExecutor) driver).executeScript(query);
        return ret;
    }
    
    public static enum DropDownMenu {
    
        PublisherAccount("[name=\"publisherClientAccountId\"]", null, false, "Publisher Account"),
        Status("[name=\"status\"]", null, false, "Status"),
        CountryOfSale(".country_of_sale", null, false, "Country of Sale"),
        CampaignType(".campaign_type", null, false, "Campaign Type"),
        CampaignPriority(".campaign_priority", null, false, "Campaign Priority");
    
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

    public NewGoogleCampaignPage select(WebDriver driver, DropDownMenu menu, String option) throws Exception {
    
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

    public static enum TextInput {

        CampaignName("[name=\"campaignName\"]", "Campaign Name"),
        MobileBidAdjustmentPercentage("[name=\"mobileBidModifier\"]", "Mobile Bid Adjustment"),
        StateDate("[name=\"startDate\"]", "State Date"),
        EndDate("[name=\"endDate\"]", "End Date"),
        Budget("[name=\"dailyBudget\"]", "Budget"),
        MerchantId("[name=\"merchantId\"]", "Merchant ID");
   
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

    public NewGoogleCampaignPage type(WebDriver driver, TextInput input, String text) throws Exception {

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
    
    public static enum Radio {
        
        Off("[name=\"mobileBidAdjustmentOverride\"][value=\"OFF\"]", null, false, "Mobile Bid Adjustment OFF"),
        On("[name=\"mobileBidAdjustmentOverride\"][value=\"ON\"]", null, false, "Mobile Bid Adjustment ON"),
        SetEndDateYes("[name=\"endDateOn\"][value=\"on\"]", null, false, "Set End Date Yes"),
        SetEnddDateNo("[name=\"endDateOn\"][value=\"off\"]", null, false, "Set End Date No");
        
    
        private String locator;
        private String spinner;
        private boolean pageLoad;
        private String description;
    
        private Radio(String locator, String spinner, boolean pageLoad, String description) {
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
    
    public NewGoogleCampaignPage check(WebDriver driver, Radio radio) throws Exception {

        String query = "$('" + radio.getLocator() + "').click();";
        if (isElementPresent(driver, radio.getLocator()) == true) {
            changeElementBackground(driver, radio.getLocator());
            String retval = (String) ((JavascriptExecutor) driver).executeScript(query);
            wait(100);
            removeElementBackground(driver, radio.getLocator());
            log.info("click " + radio.toString() + this.getClass().getSimpleName() + "\"");
        }

        return instance;
    }
    
    public static enum CountryOfSale{
        
        Brazil("Brazil"),
        India("India"),
        Italy("Italy"),
        UnitedStates("United States"),
        UnitedKingdom("United Kingdom");
           
        private String value;
        
        private CountryOfSale(String value){
           this.value = value;
           
       } 
       
       @Override
       public String toString() {
           return this.value;
       }
        
    }
    
    public static enum CampaignPriority{
        
        Low("Low"),
        Medium("Medium"),
        High("High");
           
        private String value;
        
        private CampaignPriority(String value){
           this.value = value;
           
       } 
       
       @Override
       public String toString() {
           return this.value;
       }
        
    }

    public static enum CampaignStatus{
        
        ACTIVE("Active"),
        PAUSED("Paused");
           
        private String value;
        
        private CampaignStatus(String value){
           this.value = value;
           
       } 
       
       @Override
       public String toString() {
           return this.value;
       }
        
    }

    public static enum CampaignType{
        
        SHOPPING("Shopping"),
        SEARCHNETWORK("Search Network"),
        DISPLAYNETWORKONLY("Display Network Only");
           
        private String value;
        
        private CampaignType(String value){
           this.value = value;
           
       } 
       
       @Override
       public String toString() {
           return this.value;
       }
        
    }
    

}
