package com.marin.qa.selenium.common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ChromeDriverTests  {

    public static void main(String[] args) {
        // Create a new instance of the Firefox driver
        // Notice that the remainder of the code relies on the interface,
        // not the implementation.
    	System.setProperty("webdriver.chrome.driver", "/Users/mmadhusoodan/bin/chromedriver");

        try {
            WebDriver driver = new ChromeDriver();

            driver.navigate().to("http://www.google.com");

            System.out.println("Page title is: " + driver.getTitle());

            // Google's search is rendered dynamically with JavaScript.
            // Wait for the page to load, timeout after 10 seconds
            (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver d) {
                    return d.getTitle().toLowerCase().startsWith("cheese!");
                }
            });

            // Should see: "cheese! - Google Search"
            System.out.println("Page title is: " + driver.getTitle());

            //Close the browser
            driver.quit();
        } catch(Exception e){

        }
    }

}