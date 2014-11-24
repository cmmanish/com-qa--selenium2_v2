package com.marin.qa.selenium.Util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by mmadhusoodan on 11/24/14.
 */
public class QaCalendar {

    private SimpleDateFormat mmddyyyyyHMMAFormat = new SimpleDateFormat("MM-dd-yyyy-h:mm a");
    private Calendar calendar = Calendar.getInstance();

    private static QaCalendar instance;

 /*   private SimpleDateFormat cw_formater_date = new SimpleDateFormat("M/d/yyyy");
    private SimpleDateFormat cw_formater_time = new SimpleDateFormat("h:mm a");
    private SimpleDateFormat camp_formater_date = new SimpleDateFormat("M/d/yy");
    private SimpleDateFormat camp_formater_time = new SimpleDateFormat("h:mm a");
    private SimpleDateFormat group_formater_date = new SimpleDateFormat("M/d/yy");
    private SimpleDateFormat group_formater_time = new SimpleDateFormat("h:mm a"); */

    private QaCalendar() {
    };

    public static synchronized QaCalendar getInstance() {

        if (instance == null) {
            instance = new QaCalendar();
        }

        return instance;
    }

    public String getCaptureTime(){

       final String captureDate = mmddyyyyyHMMAFormat.format(calendar.getTime());
       return captureDate;
    }


}
