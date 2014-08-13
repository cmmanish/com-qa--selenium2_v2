package com.marin.qa.selenium;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.marin.qa.selenium.campaigns.BuildAcceptanceTest;

@RunWith(Suite.class)
@SuiteClasses({ BuildAcceptanceTest.class })
public class CampaignTests {

}   