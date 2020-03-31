package com.example.pacify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.pacify.Utilities.PreferenceUtilities;


public class LoginActivity extends AppCompatActivity {

    private Button buttonForgetPassword;
    private Button buttonLogin;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewErrorMsg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        buttonLogin = findViewById(R.id.login_button);
        buttonForgetPassword = findViewById(R.id.login_forget_password_button);
        editTextEmail = findViewById(R.id.Email_logIn_text);
        editTextPassword = findViewById(R.id.password_login_editText);
        textViewErrorMsg = findViewById(R.id.login_ErrorMsg);

        buttonLogin.setEnabled(false); //initially disable log in button
        textViewErrorMsg.setVisibility(View.GONE); //initially hide error msg

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CheckLoginCredentials()) {
                    Login();
                } else{
                    textViewErrorMsg.setVisibility(View.VISIBLE);
                }
            }
        });

        buttonForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this
                                            ,ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });

        editTextEmail.addTextChangedListener(loginTextWatcher);
        editTextPassword.addTextChangedListener(loginTextWatcher);
    }

    private TextWatcher loginTextWatcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String emailInput = editTextEmail.getText().toString().trim();
            String passwordInput = editTextPassword.getText().toString();

            textViewErrorMsg.setVisibility(View.GONE);
            buttonLogin.setEnabled(Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()
                                     && !passwordInput.isEmpty());
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
        @Override
        public void afterTextChanged(Editable s) { }
    };


    private boolean CheckLoginCredentials(){
        String emailInput = editTextEmail.getText().toString().trim();
        String passwordInput = editTextPassword.getText().toString();

        //TODO(Adham): Send email and password and wait for response
        return true;
    }

    private void SaveUserData(){
        String emailInput = editTextEmail.getText().toString().trim();
        String passwordInput = editTextPassword.getText().toString();
        PreferenceUtilities.saveEmail(emailInput, this);
        PreferenceUtilities.savePassword(passwordInput, this);
        PreferenceUtilities.saveState("true",this);
        PreferenceUtilities.saveUserName("Adham",this);
    }

    private void Login(){
        SaveUserData();
        Intent intent = new Intent(LoginActivity.this
                                    , NavigationActivity.class);
        startActivity(intent);
    }
}
