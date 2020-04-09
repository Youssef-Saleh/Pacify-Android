package com.example.pacify.SignUp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.pacify.MainActivity;
import com.example.pacify.R;


public class SignUpActivity extends AppCompatActivity {

    public String signUp_email;
    public String signUp_password;
    public int signUp_dob_day;
    public int signUp_dob_month;
    public int signUp_dob_year;
    public String signUp_gender;
    public String signUp_name;
    public String signUp_phone_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        openSignUpEmailFragment();
    }

    public void openSignUpEmailFragment(){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_signUp, new SignUp_Email_Fragment())
                .commit();
    }

    public void openSignUpPasswordFragment(){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_signUp, new SignUp_Password_Fragment())
                .commit();
    }

    public void openSignUpDobFragment(){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_signUp, new SignUp_DoB_Fragment())
                .commit();
    }

    public void openSignUpPhoneNumberFragment(){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_signUp, new SignUp_PhoneNumber_Fragment())
                .commit();
    }

    public void openSignUpGenderFragment(){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_signUp, new SignUp_Gender_Fragment())
                .commit();
    }

    public void openSignUpNameFragment(){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_signUp, new SignUp_Name_Fragment())
                .commit();
    }

    public void backToMainMenu(){
        Intent in = new Intent(SignUpActivity.this, MainActivity.class);
        startActivity(in);
    }

    public void hideKeyboard(Activity activity) {
        /**
         * Source: https://stackoverflow.com/questions/1109022/close-hide-android-soft-keyboard
         */
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void createAccount(){
        //TODO(Adham): Create account

    }
}
