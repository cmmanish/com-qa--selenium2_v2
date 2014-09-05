package com.marin.qa.selenium.campaigns;

import java.util.Arrays;
import java.util.Random;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.marin.qa.selenium.common.QaRandom;

public class BAT {

    public static Logger log = Logger.getLogger(BAT.class);

    Random randomGenerator = new Random();
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
   
   public String getRandomElementWithException(String[] array, String exception){
       
       int max = array.length;
       String randElement = "";
       
       for (int index = 0; index < max; index++) {
           randElement = array[randomGenerator.nextInt(array.length)];
           if (exception == randElement)
               randElement = array[randomGenerator.nextInt(array.length)];
       }
       return randElement;
   }

    @Test
    public void E1testSingleCreateGoogleShoppingCampaignUS() throws Exception {
        log.info("E1");
        String[] array = {"High", "Med" , "Low"} ;
        log.info(getRandomElementWithException(array, "High"));
        log.info(getRandomElementWithException(array, "Med"));
        log.info(getRandomElementWithException(array, "Low"));
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
