package com.example.pacify;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.Random;

public class CommonFunctions {

    /**
     * Hides the keyboard
     * @param activity is the activity where the keyboard is shown
     * Source: https://stackoverflow.com/a/17789187/12963182
     */
    public static void hideKeyboard(Activity activity) {

        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * Generates a random string from 'allChars' String
     * @param size define the number of characters of the random string
     * Acknowledgment to https://stackoverflow.com/a/20536597/12963182
     */
    public static String GenerateRandChars(int size){
        String allChars = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        StringBuilder rString = new StringBuilder();
        Random rand = new Random();

        for(int i=0; i<size;i++){
            int index = (int) (rand.nextFloat() * allChars.length());
            rString.append(allChars.charAt(index));
        }
        return rString.toString();
    }


}
