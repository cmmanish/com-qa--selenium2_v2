package com.marin.qa.selenium.common;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * This class generates random data for use in testing.
 * 
 * @author mmadhusoodan
 * 
 */
public class QaRandom {

    private static QaRandom instance;

    /**
     * Private constructor prevents construction outside this class.
     */
    private QaRandom() {
    }

    public static synchronized QaRandom getInstance() {

        if (instance == null) {
            instance = new QaRandom();
        }

        return instance;
    }

    /**
     * Generates a random alphanumeric string.
     * 
     * @param prefix
     * @param postfix
     * @param length
     * @return random string
     */
    public String getRandomString(String prefix, String postfix, int length) {

        if (prefix == null)
            try {
                throw new Exception("Prefix cannot be null.");
            }
            catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        if (postfix == null)
            try {
                throw new Exception("Postifx cannot be null.");
            }
            catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        return prefix + RandomStringUtils.randomAlphanumeric(length) + postfix;
    }

    /**
     * Generates a random alphanumeric string.
     * 
     * @param prefix
     * @param length
     * @return random string
     */
    public String getRandomString(String prefix, int length) {

        return prefix +"_"+ RandomStringUtils.randomAlphanumeric(length);
    }

    /**
     * Generates a random alphanumeric string.
     * 
     * @param length
     * @return random string
     */
    public String getRandomString(int length) {

        return RandomStringUtils.randomAlphanumeric(length);
    }

    /**
     * Generates a repeating string in the format 123456789012345...
     * Note that this starts at 1 but does add a 0 after 9 per requirements.
     * 
     * @param length
     * @return sequential string
     */
    public String getSequentialNumericString(int length) {

        StringBuilder sb = new StringBuilder();

        while (sb.length() < length) {
            for (int i = 1; i < 11; i++) {

                if (sb.length() == length)
                    break;

                int value = i;

                if (value == 10)
                    value = 0;

                sb.append(value);
            }

        }

        return sb.toString();
    }
}
