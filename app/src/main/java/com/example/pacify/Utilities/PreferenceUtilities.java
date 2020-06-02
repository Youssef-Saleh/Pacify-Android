package com.example.pacify.Utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Saves and gets saved parameters, but it will be removed in
 * the next version.
 */
public class PreferenceUtilities {

    public static void saveEmail(String email, Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(Constants.KEY_EMAIL, email);
        prefsEditor.apply();
    }

    public static String getEmail(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(Constants.KEY_EMAIL, null);
    }

    public static void savePassword(String password, Context context) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(Constants.KEY_PASSWORD, password);
        editor.apply();
    }

    public static String getPassword(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(Constants.KEY_PASSWORD, null);
    }

    public static void saveState(String login_state, Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(Constants.IS_LOGGEDIN, login_state);
        prefsEditor.apply();
    }

    public static String getState(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(Constants.IS_LOGGEDIN, null);
    }

    public static void saveUserName(String username, Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(Constants.USER_NAME, username);
        prefsEditor.apply();
    }

    public static String getUserName(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(Constants.USER_NAME, null);
    }

    public static void saveFacebookName(String f_name, Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(Constants.FACEBOOK_NAME, f_name);
        prefsEditor.apply();
    }

    public static String getFacebookName(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(Constants.FACEBOOK_NAME, null);
    }

    public static void saveFacebookEmail(String f_email, Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(Constants.FACEBOOK_EMAIL, f_email);
        prefsEditor.apply();
    }

    public static String getFacebookEmail(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(Constants.FACEBOOK_EMAIL, null);
    }

    public static void saveFacebookPP(String f_pp, Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(Constants.FACEBOOK_PP, f_pp);
        prefsEditor.apply();
    }

    public static String getFacebookPP(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(Constants.FACEBOOK_PP, null);
    }
}
