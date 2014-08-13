package com.marin.qa.selenium.pageObjects.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class CampaignsPage extends AbstractPage{

	static Logger log = Logger.getLogger(CampaignsPage.class);
	private static CampaignsPage instance;

	/**
	  * Private constructor prevents construction outside this class.
	*/
	private CampaignsPage(){}

	public static synchronized CampaignsPage getInstance(){

		if (instance == null){
			instance = new CampaignsPage();
		}

		return instance;
	}
	
	public final static String CAMPAIGN_VIEW = "150CampaignsAllButDeleted";
	
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

        Success("#campaign_table_flash", "Success"),
        Cancel("[name=\"cancel\"]", "Cancel");
        
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

        String query = "return $('" + label.getLocator() + "').text().trim();";
        //Get Label text
        String retval = (String) ((JavascriptExecutor) driver).executeScript(query);
        return retval;
    }
	
	/**
     * Link Element as list of all links on page
     * @version 2.00
     * @param locator as jQuery mapping for Button element locator 
     * @param spinner as jQuery mapping for spinner 
     * @param pageLoad as jQuery mapping for pageLoad 
     * @param description as description for Button element
     * @author mmadhusoodan
     */
	public static enum Button {
	    
	    Pause("#campaigns_action_pause", null, true, "Pause"),
	    Resume("#campaigns_action_resume", null, true, "Resume"),
	    Schedule("#campaigns_action_scheduledactions", null, true, "Schedule"),
	    Create("#campaigns_action_add", null, true, "Create"),
	    Edit("#campaigns_action_edit", null, true, "Edit"),
	    Delete("#campaigns_action_delete", null, true, "Delete"),
	    Copy("#clonerButtonTop", null, true, "Copy"),
	    Sync("#campaigns_action_multiedit-resync`", null, true, "Sync"),
	    AddToFolder("#campaigns_action_addtofolder", null, true, "AddToFolder"),
	    Chart("#chartButton", null, true, "Chart");

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
    public CampaignsPage click(WebDriver driver, Button button) {

        String query = "$('" + button.getLocator() + "')[0].click();";
        
        if (isElementPresent(driver, button.getLocator()) == true) {
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
	/**
     * Link Element as list of all links on page
     * @version 2.00
     * @param locator as jQuery mapping for Link element locator 
     * @param spinner as jQuery mapping for spinner 
     * @param pageLoad as jQuery mapping for pageLoad 
     * @param description as description for Link element
     * @author mmadhusoodan
     */
    public static enum Link {
        
        SaveView("#viewButtons a:contains(\"Save\")", null, true, "Save View"),
        RemoveView("#viewButtons a:contains(\"Remove\")", null, true, "Remove View"),
        BulkAddEditCampaigns("#campaigns_action_pause", null, true, "Remove View"),
        UploadRevenue("#campaigns_action_pause", null, true, "Remove View"),
        ManageAccounts("#campaigns_action_pause", null, true, "Remove View");
        
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
    public CampaignsPage click(WebDriver driver, Link link) {

        String query = "$('" + link.getLocator() + "')[0].click();";
        if (isElementPresent(driver, link.getLocator()) == true) {
            changeElementBackground(driver, link.getLocator());
            String retval = (String) ((JavascriptExecutor) driver).executeScript(query);
            log.info(retval);
            removeElementBackground(driver, link.getLocator());
            log.info("Press \"" + link.toString() + "\" Button in \"" + this.getClass().getSimpleName() + "\"");

            wait(300);
            
            if (link.getSpinner() != null) {
                waitForElementToDissappear(driver, link.getSpinner());
            }

            if (link.getPageLoad()) {
                waitForPageToLoad(driver, LONG_PAGE_TIMEOUT);
            }
        }
        return instance;
    }
    
    
    /**
     * This Method set to open Campaign details by column information
     *
     * @author mbeider
     * @param selenium
     * @param column
     * @param option
     * @return boolean
     *
     */
    public boolean open(WebDriver driver, String campaignName) {

        String query = "return $('#left_table a:contains("+campaignName+")')[0].click()"; 
        String element = "#left_table a:contains("+campaignName+")";
        boolean flag = false;
        waitForElementToBeAppear(driver, element);
            
        if (isElementPresent(driver, element)){
            changeElementBackground(driver, element);
            String retval = (String) ((JavascriptExecutor) driver).executeScript(query);
            log.info(" "+retval);
            flag = true;
        }
        
        return flag;
   }
    
    //$('#left_table a:contains("adwordsTruffle")')[0].click()
    
    /**
     * DropDownMenu Element as list of all drop down menus on page
     * 
     * @version 2.00
     * @param locator
     *        as jQuery mapping for DropDown Menu element locator
     * @param spinner
     *        as jQuery mapping for spinner
     * @param pageLoad
     *        as jQuery mapping for pageLoad
     * @param description
     *        as description for DropDownMenu element
     * @author mmadhusoodan
     */
    public static enum DropDownMenu {

        Show("#campaign_table_row_select", "#grid_overlay_operation_table", false, "Show"),
        Views("#view_select", "#grid_overlay_operation_table", false, "View Select");
 
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
    public CampaignsPage select(WebDriver driver, DropDownMenu menu, String option) {

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

}
