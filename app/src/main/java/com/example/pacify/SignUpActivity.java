package com.example.pacify;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.util.Patterns;

public class SignUpActivity extends AppCompatActivity {

    private Button buttonNext;
    private EditText editTextEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        buttonNext = findViewById(R.id.signUpNext_button);
        editTextEmail = findViewById(R.id.signUpEmail_editText);

        buttonNext.setEnabled(false); //The button is initially disabled

        editTextEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //The button is disabled if the input email is not valid
                String emailInput = editTextEmail.getText().toString().trim();
                buttonNext.setEnabled(Patterns.EMAIL_ADDRESS.matcher(emailInput).matches());
            }
            @Override
            public void afterTextChanged(Editable s) {}
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        });

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO(Adham): sign up
            }
        });
    }
}
