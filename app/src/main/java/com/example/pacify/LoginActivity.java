package com.example.pacify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.regex.Pattern;


public class LoginActivity extends AppCompatActivity {

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    //"(?=.*[a-zA-Z])" +      //any letter
                    //"(?=.*[@#$%^&+=])" +    //at least 1 special character
                    //"(?=\\S+$)" +           //no white spaces
                    ".{8,}" +               //at least 8 characters
                    "$");

    private Button forgetPassword;
    private Button login;
    private EditText emailOrUsername;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = (Button) findViewById(R.id.login_button);
        forgetPassword = (Button) findViewById(R.id.facebookLogin_button);
        emailOrUsername = (EditText) findViewById(R.id.EmailOrUsername_logIn_text);
        password = (EditText) findViewById(R.id.password_login_editText);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateEmailOrUsername() & validatePassword()) {
                    //TODO: Send Email & password
                    Intent intent = new Intent(LoginActivity.this,NavigationActivity.class);
                    startActivity(intent);
                }
            }
        });

        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    public boolean validateEmailOrUsername(){
        String Input = emailOrUsername.getText().toString().trim();
        if (Input.isEmpty()) {
            emailOrUsername.setError("Field can't be empty");
            return false;
            //} else if (!Patterns.EMAIL_ADDRESS.matcher(Input).matches()) {
            //    emailOrUsername.setError("Please enter a valid email address");
            //    return false;
        } else {
            emailOrUsername.setError(null);
            return true;
        }
    }
    public boolean validatePassword(){
        String Input = password.getText().toString().trim();
        if (Input.isEmpty()) {
            password.setError("Field can't be empty");
            return false;
        //} else if (!PASSWORD_PATTERN.matcher(Input).matches()) {
        //    password.setError("Please enter a valid password");
        //    return false;
        } else {
            password.setError(null);
            return true;
        }
    }
}
