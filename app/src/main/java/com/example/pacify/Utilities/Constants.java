package com.example.pacify.Utilities;

import java.util.regex.Pattern;

public class Constants {
    public static String KEY_EMAIL = "email";
    public static String KEY_PASSWORD = "password";
    public static String IS_LOGGEDIN = "false";
    public static String USER_NAME = "";

    public static String FACEBOOK_EMAIL = "";
    public static String FACEBOOK_NAME = "";
    public static String FACEBOOK_PP = "";



    public static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[a-z])" +         //at least 1 lower case letter
                    "(?=.*[A-Z])" +         //at least 1 upper case letter
                    //"(?=.*[a-zA-Z])" +      //any letter
                    "(?=\\S+$)" +           //no white spaces
                    ".{8,}" +               //at least 8 characters
                    "$");
}