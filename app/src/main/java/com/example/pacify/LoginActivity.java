package com.example.pacify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.pacify.Utilities.PreferenceUtilities;

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

    private Button buttonForgetPassword;
    private Button buttonLogin;
    private EditText editTextEmailOrUsername;
    private EditText editTextPassword;
    private TextView textViewErrorMsg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        buttonLogin = findViewById(R.id.login_button);
        buttonForgetPassword = findViewById(R.id.facebookLogin_button);
        editTextEmailOrUsername = findViewById(R.id.EmailOrUsername_logIn_text);
        editTextPassword = findViewById(R.id.password_login_editText);
        textViewErrorMsg = findViewById(R.id.login_ErrorMsg);

        buttonLogin.setEnabled(false); //initially disable log in button
        textViewErrorMsg.setVisibility(View.GONE); //initially hide error msg

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CheckLoginParameters()) {
                    Login();
                } else{
                    textViewErrorMsg.setVisibility(View.VISIBLE);
                }
            }
        });

        buttonForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });

        editTextEmailOrUsername.addTextChangedListener(loginTextWatcher);
        editTextPassword.addTextChangedListener(loginTextWatcher);
    }

    private TextWatcher loginTextWatcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String usernameInput = editTextEmailOrUsername.getText().toString().trim();
            String passwordInput = editTextPassword.getText().toString();

            textViewErrorMsg.setVisibility(View.GONE);
            buttonLogin.setEnabled(!usernameInput.isEmpty() && !passwordInput.isEmpty());
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
        @Override
        public void afterTextChanged(Editable s) { }
    };


    private boolean CheckLoginParameters(){
        String usernameInput = editTextEmailOrUsername.getText().toString().trim();
        String passwordInput = editTextPassword.getText().toString();

        //TODO(Adham): Send email and password and wait for response
        if(usernameInput.equals("username") && passwordInput.equals("password")) {
            return true;
        } else{
            return false;
        }
    }

    private void SaveUserData(){
        String usernameInput = editTextEmailOrUsername.getText().toString().trim();
        String passwordInput = editTextPassword.getText().toString();
        PreferenceUtilities.saveEmail(usernameInput, this);
        PreferenceUtilities.savePassword(passwordInput, this);
        PreferenceUtilities.saveState("true",this);
        PreferenceUtilities.saveUserName("Adham",this);
    }

    private void Login(){
        SaveUserData();
        Intent intent = new Intent(LoginActivity.this, NavigationActivity.class);
        startActivity(intent);
    }
}
