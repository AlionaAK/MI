package com.example.domain.util.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserValidator {

    private static final String PHONE_NUMBER_REGEX = "^(\\s*)?(\\+)?([- _():=+]?\\d[- _():=+]?){10,14}(\\s*)?$";

    public static boolean validatePhone(String phoneNumber) {
        return phoneNumber.matches(PHONE_NUMBER_REGEX);
    }


}