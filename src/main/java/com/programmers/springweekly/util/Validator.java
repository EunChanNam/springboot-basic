package com.programmers.springweekly.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    private static final Logger logger = LoggerFactory.getLogger(Validator.class);
    private static final Pattern pattern = Pattern.compile("[0-9]+");
    private static final String IS_NOT_A_NUMBER = "Is not a number";
    private static final String A_NUMBER_THAT_IS_OUT_OF_RANGE = "A number that is out of range";
    private static final int PERCENT_MAX = 100;
    private static final int PERCENT_MIN = 0;

    public static void fixedAmountValidate(String fixedAmount){
        isNumber(fixedAmount);
    }

    public static void percentValidate(String percent){
        isNumber(percent);
        isValidRange(percent);
    }

    private static void isNumber(String number){
       Matcher match = pattern.matcher(number);

       if(!match.matches()){
           throw new IllegalArgumentException(IS_NOT_A_NUMBER);
       }
    }

    private static void isValidRange(String inputPercent){
        int percent = Integer.parseInt(inputPercent);

        if(percent > PERCENT_MAX || percent < PERCENT_MIN){
            throw new IllegalArgumentException(A_NUMBER_THAT_IS_OUT_OF_RANGE);
        }
    }
}
