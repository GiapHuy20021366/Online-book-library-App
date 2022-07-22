package com.example.library2.utilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Check {
    public static final int ACCEPT = -1;
    public static final int LENGTH_ERROR = 0;
    public static final int LOSS_UPPERCASE = 1;
    public static final int LOSS_LOWERCASE = 2;
    public static final int LOSS_NUMBER = 3;
    public static final int LOSS_SPECIAL_CHAR = 4;

    public static int isValid(String s) {
        if (s.length() > 15 || s.length() < 8) {
            return LENGTH_ERROR;
        }
        String upperCaseChars = "(.*[A-Z].*)";
        if (!s.matches(upperCaseChars)) {
            return LOSS_UPPERCASE;
        }
        String lowerCaseChars = "(.*[a-z].*)";
        if (!s.matches(lowerCaseChars)) {
            return LOSS_LOWERCASE;
        }
        String numbers = "(.*[0-9].*)";
        if (!s.matches(numbers)) {
            return LOSS_NUMBER;
        }
        String specialChars = "(.*[@,#,$,%].*$)";
        if (!s.matches(specialChars)) {
            return LOSS_SPECIAL_CHAR;
        }
        return ACCEPT;
    }

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static final Pattern VALID_PHONE_REGEX =
            Pattern.compile("\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}", Pattern.CASE_INSENSITIVE);

    public static boolean validateEmail(String email) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();
    }

    public static boolean validatePhone(String phone) {
        Matcher matcher = VALID_PHONE_REGEX.matcher(phone);
        return matcher.find();
    }
}
