package com.example.pacify;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ForgetPasswordActivity extends AppCompatActivity {

    private Button buttonGetLink;
    private EditText editTextEmailOrUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        buttonGetLink = findViewById(R.id.GetLink_button);
        editTextEmailOrUsername = findViewById(R.id.EmailOrUsername_ForgetPass_text);

        buttonGetLink.setEnabled(false);

        buttonGetLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Send email
            }
        });

        editTextEmailOrUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String emailOrUsername_text = editTextEmailOrUsername.getText().toString().trim();

                buttonGetLink.setEnabled(Patterns.EMAIL_ADDRESS.matcher(emailOrUsername_text).matches());
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void afterTextChanged(Editable s) { }
        });
    }
}
