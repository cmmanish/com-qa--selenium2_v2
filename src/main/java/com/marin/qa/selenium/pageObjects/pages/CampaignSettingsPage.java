package com.marin.qa.selenium.pageObjects.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class CampaignSettingsPage extends AbstractPage {

    static Logger log = Logger.getLogger(CampaignSettingsPage.class);
    private static CampaignSettingsPage instance;
    
    private static CampaignsPage CampaignsPage ; 

    /**
     * Private constructor prevents construction outside this class.
     */
    private CampaignSettingsPage() {
    }

    public static synchronized CampaignSettingsPage getInstance() {

        if (instance == null) {
            instance = new CampaignSettingsPage();
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
    
    public String getInfo(WebDriver driver, Label label) {

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
        Resync(".settings_resync_button", "Resync"),
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
    public CampaignsPage clickButton(WebDriver driver, Button button) {

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
        SearchPartners("#campaignSearchPartner", null, false, "Search Partners"),
        DisplaySelect("#campaignDisplayNetwork", null, false, "Display Select"),
        KeywordMatching("#keywordMatchingOnInput", null, false, "Keyword Matching");
    
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
     * This method set to return status of Radio Button 
     * 
     * @author mmadhusoodan 
     * @param selenium
     * @param radio
     * @return boolean
     * 
     */
    public boolean isChecked(WebDriver driver, Checkbox checkbox) {
    
        //Get Radio status
        String query = "return $('" + checkbox.getLocator() + "').is(':checked');";
        boolean retval = (Boolean) ((JavascriptExecutor) driver).executeScript(query);
        return retval;
    }

    public static enum DropDownMenu {
    
        PublisherAccount("#jsAddPublisherClientAcct", null, false, "Publisher\\Account"),
        Status("[name=\"status\"]", null, false, "Publisher\\Account"),
        CountryOfSale("#countryOfSaleSelection", null, false, "Publisher\\Account"),
        CampaignType("#campaignType", null, false, "Publisher\\Account"),
        CampaignPriority("#jsAddPublisherClientAcct", null, false, "Publisher\\Account");
    
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

    public CampaignSettingsPage select(WebDriver driver, DropDownMenu menu, String option) throws Exception {
    
        String query = "$('select" + menu.getLocator() + "').find('option:contains(\"" + option + "\")').attr('selected',true).change();";
        waitForDropDownElementToBePopulated(driver, menu.getLocator());
    
        if (isElementPresent(driver, menu.getLocator()) == true) {
            changeElementBackground(driver, menu.getLocator());
            String retval = (String) ((JavascriptExecutor) driver).executeScript(query);
            waitForAjaxRequestDone(driver, AJAX_TIMEOUT);
            removeElementBackground(driver, menu.getLocator());
            log.info("Select \"" + option + "\" option in \"" + menu.toString() + "\" Drop Down Menu in \"" + this.getClass().getSimpleName() + "\"");
        }
    
        return instance;
    
    }
    
    /**
     * This method set to return DropDown Menu 
     * 
     * @author mmadhusoodan 
     * @param selenium
     * @param menu
     * @return String
     * 
     */
    public String getSelected(WebDriver driver, DropDownMenu menu) {
    
        String info = null;
        String query = "return $('" + menu.getLocator() + " option:selected').text();";
        waitForDropDownElementToBePopulated(driver, menu.getLocator());

        //Get DropDownMenu text
        if(isElementPresent(driver, menu.getLocator())){
            changeElementBackground(driver, menu.getLocator());
            info = (String) ((JavascriptExecutor) driver).executeScript(query);
            removeElementBackground(driver, menu.getLocator());
            log.info("Value of \"" + menu.toString() + "\" DropDownMenu is \"" + info + "\" in \"" + this.getClass().getSimpleName() + "\"");
        }
        
        return info;
    }
    
    
    public static enum TextInput {

        CampaignName("[name=\"campaignName\"]", "Campaign Name"),
        MobileBidAdjustmentPercentage("[name=\"mobileBidModifier\"]", "Mobile Bid Adjustment"),
        StateDate("[name=\"startDate\"]", "State Date"),
        EndDate("[name=\"endDate\"]", "End Date"),
        Budget("#dayBudget", "Budget"),
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

    public CampaignSettingsPage type(WebDriver driver, TextInput input, String text) {

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
    
    public String getInfo(WebDriver driver, TextInput input) {

        String query = "return $('" + input.getLocator() + "').val().trim();";
        //Get text
        String retval = (String) ((JavascriptExecutor) driver).executeScript(query);
        return retval;
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
    
    public CampaignSettingsPage check(WebDriver driver, Radio radio) {

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
    
    /**
     * This method set to return status of Radio Button 
     * 
     * @author mmadhusoodan 
     * @param selenium
     * @param radio
     * @return boolean
     * 
     */
    public boolean isChecked(WebDriver driver, Radio radio) {
    
        //Get Radio status
        String query = "return $('" + radio.getLocator() + "').is(':checked');";
        String retval = (String) ((JavascriptExecutor) driver).executeScript(query);
        return Boolean.parseBoolean(query);
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
    

}
