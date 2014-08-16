package com.marin.qa.selenium.campaigns;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class BAT {

    public static Logger log = Logger.getLogger(BAT.class);

    @BeforeClass
    public static void testSetUp() {
        log.info("<--------- Start Setup Test --------->");
        log.info("<--------- End Setup Test --------->");
    }

   @AfterClass
    public static void cleanup() {
        log.info("<--------- Start Logout Test --------->");
        log.info("<--------- End Logout Test --------->");
    }

    @Test
    public void E1testSingleCreateGoogleShoppingCampaignUS() throws Exception {
        log.info("E1");
    }

    @Test
    public void E2testSingleCreateGoogleShoppingCampaignNonUS() throws Exception {
        log.info("E2");
    }

    @Test
    public void E3testSingleCreateGoogleShoppingCampaignNonUS() throws Exception {
        log.info("E3");
    }

    @Test
    public void E4testSingleCreateGoogleShoppingCampaignNonUS() throws Exception {
        log.info("E4");
    }

}
