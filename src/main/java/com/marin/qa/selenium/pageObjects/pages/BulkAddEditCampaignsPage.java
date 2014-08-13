package com.marin.qa.selenium.pageObjects.pages;

import org.apache.log4j.Logger;
import com.thoughtworks.selenium.Selenium;

public class BulkAddEditCampaignsPage extends AbstractPage {

	private static BulkAddEditCampaignsPage instance;
	static Logger log = Logger.getLogger(BulkAddEditCampaignsPage.class);
	public final String POST_NOW_ID = "postNowText";
	public final String PROGRESS_GRID_CONTAINER = "#progress_grid_container";
	
	/**
	  * Private constructor prevents construction outside this class.
	*/
	private BulkAddEditCampaignsPage(){}
	 
	public static synchronized BulkAddEditCampaignsPage getInstance(){
		
		if (instance == null){
			instance = new BulkAddEditCampaignsPage();
		}
	 
		return instance;
	}

	/**
	 * Label Element as list of all labels on page
	 * @version 2.00
	 * @param locator as jQuery mapping for Label element
	 * @param description as description for Label element
	 * @author mmadhusoodan
	 */
	public static enum Label {
		
		Alert(".bad", "Alert");

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
	 * Link Element as list of all links on page
	 * @version 2.00
	 * @param locator as jQuery mapping for Tabs element locator 
	 * @param spinner as jQuery mapping for spinner 
	 * @param pageLoad as jQuery mapping for pageLoad 
	 * @param description as description for Link element
	 * @author mmadhusoodan
	 */
	public static enum Link {
		
		Campaigns("#left_related_tasks a:eq(0)",  null, true, "Campaigns"),
		Groups("#left_related_tasks a:eq(1)", null, true, "Groups"),
		Keywords("#left_related_tasks a:eq(2)", null, true, "Keywords"),
		Creatives("#left_related_tasks a:eq(3)",  null, true, "Creatives"),
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
	 * @version 2.00
	 * @param locator as jQuery mapping for Radio Button element locator 
	 * @param value as jQuery mapping for radio button value
	 * @param spinner as jQuery mapping for spinner 
	 * @param pageLoad as jQuery mapping for pageLoad 
	 * @param description as description for Radio button element
	 * @author mmadhusoodan
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
	 * @version 2.00
	 * @param locator as jQuery mapping for DropDown Menu element locator 
	 * @param spinner as jQuery mapping for spinner 
	 * @param pageLoad as jQuery mapping for pageLoad 
	 * @param description as description for DropDownMenu element
	 * @author mmadhusoodan
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

	/**
	 * Text Input Element as list of all text inputs on page
	 * @version 2.00
	 * @param locator as jQuery mapping for text input element
	 * @param description as description for Text Input element
	 * @author mmadhusoodan
	 */
	public static enum TextArea {
		
		Campaigns("[name=\"user_bulk\"]", "Campaigns");

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

	/**
	 * Button Element as list of all buttons on page
	 * @version 2.00
	 * @param locator as jQuery mapping for Button element locator 
	 * @param spinner as jQuery mapping for spinner 
	 * @param pageLoad as jQuery mapping for pageLoad 
	 * @param description as description for Button element
	 * @author mmadhusoodan
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

}
