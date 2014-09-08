package com.marin.qa.selenium;

import com.marin.qa.selenium.campaigns.BulkAddCampaignsTest;
import com.marin.qa.selenium.campaigns.SingleCampaignsTest;
import com.marin.qa.selenium.campaigns.SingleCreateCampaignsTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(Suite.class)
@SuiteClasses({
        SingleCampaignsTest.class,
        SingleCreateCampaignsTest.class,
        BulkAddCampaignsTest.class
})
public class CampaignTestSuite {

}   