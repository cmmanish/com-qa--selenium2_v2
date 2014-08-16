package com.marin.qa.selenium;

import com.marin.qa.selenium.campaigns.SingleCampaignsTest;
import com.marin.qa.selenium.campaigns.BuildAcceptanceTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;



@RunWith(Suite.class)
@SuiteClasses({  BuildAcceptanceTest.class, SingleCampaignsTest.class })
public class CampaignTests {

}   