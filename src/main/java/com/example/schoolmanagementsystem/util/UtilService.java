package com.example.schoolmanagementsystem.util;

import java.util.regex.Pattern;

public class UtilService {

    public static boolean emailValidation(String email) {
        return Pattern.matches(Constants.EMAIL_PATTERN, email);
    }

    public static boolean phoneNoValidation(String phone) {
        return Pattern.matches(Constants.PHONE_REGEX, phone);
    }
}
