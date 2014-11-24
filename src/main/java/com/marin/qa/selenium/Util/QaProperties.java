package com.marin.qa.selenium.Util;

import java.io.FileInputStream;
import java.io.File;
import java.util.Properties;
import java.net.InetAddress;

public class QaProperties { // singleton
    private static QaProperties globalsInstance;

    private QaProperties() {
    };

    private static String HOME_DIR = "/Users/mmadhusoodan/Google Drive/workspace/com-qa-selenium2_v2";

    public static QaProperties getGlobals() {
        if (globalsInstance == null) {
            globalsInstance = new QaProperties();

        }
        return globalsInstance;
    }

    public static Properties getProperty() {
        Properties props = new Properties();
        try {

            FileInputStream in;
            String address = "/src/main/resources/local.properties";
            address = HOME_DIR + address;
            System.out.println("Looking for property file: " + address);

            File fileProps = new File(address);

            if (fileProps.exists()) {
                in = new FileInputStream(fileProps);
            }
            else {
                in = new FileInputStream(new File("local.properties"));
            }

            props.load(in);
            in.close();
            return props;
        }
        catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public static Properties props = getProperty();

    public static String getIPAddress() {
        String address = "";
        try {
            InetAddress in = InetAddress.getLocalHost();
            address = in.getHostAddress();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return address;
    }

    public static String getBrowser() {
        return props.getProperty("marin.browser");
    }
    
    public static String getAppURL() {
        return props.getProperty("app.url");
    }
    
    public static boolean isFirefox(){
        return "firefox".equals(getBrowser());
           
    }
    
    public static boolean isChrome() {
        return "chrome".equals(getBrowser());
    }
    
    public static boolean isIE() {
        return "ie".equals(getBrowser());
    }

    public static boolean isSafari() {
        return "safari".equals(getBrowser());
    }
    public static boolean isHtmlUnit() {
        return "htmlUnit".equals(getBrowser());
    }

}
