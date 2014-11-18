package com.marin.qa.selenium.common;

import static org.junit.Assume.assumeTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class SafariDriverTests {

  private WebDriver driver = null;

  private static boolean isSupportedPlatform() {
    Platform current = Platform.getCurrent();
    return Platform.MAC.is(current) || Platform.WINDOWS.is(current);
  }

  @Before
  public void createDriver() {
    assumeTrue(isSupportedPlatform());
    driver = new SafariDriver();
  }

  @After
  public void quitDriver() {
    driver.quit();
  }

  @Test
  public void shouldBeAbleToPerformAGoogleSearch() {
    driver.get("http://www.google.com");
    driver.findElement(By.name("q")).sendKeys("webdriver");
    driver.findElement(By.name("btnG")).click();
    new WebDriverWait(driver, 3)
        .until(ExpectedConditions.titleIs("webdriver - Google Search"));
  }
}