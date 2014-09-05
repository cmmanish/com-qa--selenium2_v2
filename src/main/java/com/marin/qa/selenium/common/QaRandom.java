package com.marin.qa.selenium.common;

import java.util.Arrays;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * This class generates random data for use in testing.
 * 
 * @author mmadhusoodan
 * 
 */
public class QaRandom {
    Random randomGenerator = new Random();
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
    public String getRandomStringWithPrefix(String prefix, int length) {

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
     * Generates a random alphanumeric string.
     *
     * @param length
     * @return random string
     */
    public String getRandomInteger(int length) {

        return RandomStringUtils.randomNumeric(length);
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
    
    /**<p> This method set to return random array of unique elements with exception
     *<p> @param array original
     *<p> @param arrayCount max length of array 
     *<p> @return String[]
     *<p> @author mmadhusoodan 
     **/
    public String[] getRandomArrayWithException(String[] array, int arrayCount, String ... exception){
        
        String[] newArray = new String[arrayCount];
        if(array.length == arrayCount)
            return array;
        for (int index = 0; index < arrayCount; index++) {

            String selectedElement = array[randomGenerator.nextInt(array.length - 1)];

            while (Arrays.asList(newArray).contains(selectedElement) == true || Arrays.asList(exception).contains(selectedElement) == true) 
                selectedElement = array[randomGenerator.nextInt(array.length - 1)];
            
            newArray[index] = selectedElement;
        }
        return newArray;
    }
    
    /**
     * <p> This method set to return random array of unique elements
     * <p> @param array original
     * <p> @param arrayCount max length of array
     * <p> @return String[]
     * <p> @author mmadhusoodan
     **/
    public String getRandomElement(String[] array) {

        return array[randomGenerator.nextInt(array.length - 1)];

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
            
}
