package com.marin.qa.selenium.pageObjects.pages;

import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class LoginPage extends AbstractPage {

    private static LoginPage instance;
    protected final Logger log = Logger.getLogger(LoginPage.class);
    public static final String company = "\u00a9 Marin Software";

    /**
     * Private constructor prevents construction outside this class.
     */
    private LoginPage() {
    }

    public static synchronized LoginPage getInstance() {

        if (instance == null) {
            instance = new LoginPage();
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

        Company(".company", "Company"), 
        BuildNumber(".buildNumber", "Build Number"), 
        Error(".bad", "Error");

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
     * Text Input Element as list of all text inputs on page
     * 
     * @version 2.00
     * @param locator
     *        as jQuery mapping for text input element
     * @param description
     *        as description for Input Element element
     * @author mmadhusoodan
     */
    public static enum TextInput {

        Email("[name=\"email\"]", "Email"), 
        Password("[name=\"saveable_password\"]", "Password");

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

    /**
     * Button Element as list of all buttons on page
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

        Signin(".log_in", null, true, "Signin");

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

    /**
     * This Method set to type value in text input
     * 
     * @author mmadhusoodan
     * @param selenium
     * @param input
     * @param text
     * @return LoginPage
     * 
     */

    public LoginPage setText(WebDriver driver, TextInput input, String text) {

        String query = "$('" + input.getLocator() + "').val('" + text + "').trigger('keyup');";
        if (isElementPresent(driver, input.getLocator()) == true) {
            changeElementBackground(driver, input.getLocator());
            String retval = (String) ((JavascriptExecutor) driver).executeScript(query);
            removeElementBackground(driver, input.getLocator());
            log.info("Type \"" + text + "\" in \"" + input.toString() + "\" Text Input in \"" + this.getClass().getSimpleName() + "\"");
        }

        return instance;

    }

    /**
     * This Method set to click on button on page
     * 
     * @author mmadhusoodan
     * @param selenium
     * @param button
     * @return LoginPage
     * 
     */
    public LoginPage clickButton(WebDriver driver, Button button) {

        String query = "$('" + button.getLocator() + "').click();";
        if (isElementPresent(driver, button.getLocator())) {
            changeElementBackground(driver, button.getLocator());
            String retval = (String) ((JavascriptExecutor) driver).executeScript(query);
            removeElementBackground(driver, button.getLocator());
            log.info("Press \"" + button.toString() + "\" Button in \"" + this.getClass().getSimpleName() + "\"");
        }
        else {
            //element not present
        }

        return instance;

    }

    /**
     * This method set to return Label text
     * 
     * @author mmadhusoodan
     * @param selenium
     * @param label
     * @return String
     * 
     */
    public String getInfo(WebDriver driver, Label label) {

        // Get Label text
        String query = "$('" + label.getLocator() + "').text()";
        String retval = (String) ((JavascriptExecutor) driver).executeScript(query);
        return retval;

    }

    /**
     * This method set to Login to application
     * 
     * @author mmadhusoodan
     * @param selenium
     * @param email
     * @param password
     * @throws Exception
     * @return void
     * 
     */
    public void login(WebDriver driver, String email, String password) {

        // Type email, password and click on "Sign in" button
        waitForElementToBeAppear(driver, TextInput.Email.getLocator());
        waitForElementToBeAppear(driver, Button.Signin.getLocator());
        setText(driver, TextInput.Email, email);
        setText(driver, TextInput.Password, password);
        clickButton(driver, Button.Signin);
    }

}
