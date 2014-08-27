package com.marin.qa.selenium.pageObjects.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import com.thoughtworks.selenium.Selenium;

public class AdminPage extends AbstractPage {

	private static AdminPage instance;
	static Logger log = Logger.getLogger(AdminPage.class);

	/**
	  * Private constructor prevents construction outside this class.
	*/
	private AdminPage(){}

	public static synchronized AdminPage getInstance(){

		if (instance == null){
			instance = new AdminPage();
		}

		return instance;
	}

	/**
	 * Tab Element as list of all tabs on page

	 * @author Michael Beider
	 */
	public static enum Tab {
		
		ActivityLog("#setup_action_activitylog", "#grid_overlay_operation_table, #progress_grid_container", true, "Activity Log"),
		Revenue("#setup_action_revenue_diagnostics", null, true, "Revenue"),
		Dimensions("#setup_action_dimensions", null, true, "Dimensions"),
		CustomColumns("#setup_action_custom_columns", null, true, "Custom Columns"),
		Clients("#setup_action_list_clients", null, true, "Clients"),
		Users("#setup_action_list_users", null, true, "Users"),
		Expansion("#setup_action_keyword_gen", null, true, "Expansion"),
		ABTesting("#setup_action_ab_testing", null, true, "A/B Testing"),
		Budget("#setup_action_budget", null, true, "Budget"),
		Templates("#templates_action_target_template_listing", null, true, "Templates");

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

	public void select(WebDriver driver, Tab tab) {

	    String query = "$('" + tab.getLocator() + "')[0].click();";
		if (isElementPresent(driver, tab.getLocator())) {
			changeElementBackground(driver, tab.getLocator());
			wait(100);
			removeElementBackground(driver, tab.getLocator());
			((JavascriptExecutor) driver).executeScript(query);
			log.info("Select \"" + tab.toString() + "\" Tab in \"" + this.getClass().getSimpleName() + "\"");

            if (tab.getSpinner() != null) {
                waitForSpinnerToDissappear(driver, tab.getSpinner());
            }
			if (tab.getPageLoad()) {
				waitForPageToLoad(driver, LONG_PAGE_TIMEOUT);
			}



		}
	}
}
