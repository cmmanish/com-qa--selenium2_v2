package com.marin.qa.selenium.Util;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;

import java.io.File;
import java.io.IOException;

public class QaScreenshot {

    static Logger log = Logger.getLogger(QaScreenshot.class);
    private QaCalendar calendar = QaCalendar.getInstance();
    private String captureDate = calendar.getCaptureTime();
    private String path = "screenshots/" + captureDate + "/";
    private static QaScreenshot screenShotInstance;

    private QaScreenshot() {
    };

    public static QaScreenshot getScreenShots() {
        if (screenShotInstance == null) {
            screenShotInstance = new QaScreenshot();
        }
        return screenShotInstance;
    }

    /*
    * Since this object requires a driver object, I don't want to create it without one. So create a private no
    * argument constructor to block direct creation.
    */
    public String capture(WebDriver driver) {

        try{
            WebDriver augmentedDriver = new Augmenter().augment(driver);
            File source = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.FILE);

            path = path + source.getName();
            FileUtils.copyFile(source, new File(path));
        }

        catch( IOException e ){
            path = "Failed to capture screenshot: " + e.getMessage();
        }
        return path;
    }

}