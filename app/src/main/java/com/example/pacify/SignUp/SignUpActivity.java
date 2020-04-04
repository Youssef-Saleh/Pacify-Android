package com.example.pacify.SignUp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

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

    public void createAccount(){
        //TODO(Adham): Create account
    }

    public void backToMainMenu(){
        Intent in = new Intent(SignUpActivity.this, MainActivity.class);
        startActivity(in);
    }
}
