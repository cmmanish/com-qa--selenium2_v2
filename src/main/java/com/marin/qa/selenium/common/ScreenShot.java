package com.marin.qa.selenium.common;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import java.text.SimpleDateFormat;

public class ScreenShot {
    static Logger log = Logger.getLogger(ScreenShot.class);

    private WebDriver driver;
    private final String IMAGE_SUFFIX = ".png";
    private Class<?> testSuiteClass;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("HH-mm-ss-SSS");

    /*
     * Since this object requires a driver object, I don't want to create it without one. So create a private no
     * argument constructor to block direct creation.
     */
    @SuppressWarnings("unused")
    private ScreenShot() {

    }

}