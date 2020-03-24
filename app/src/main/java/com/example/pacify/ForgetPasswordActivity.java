package com.example.pacify;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ForgetPasswordActivity extends AppCompatActivity {

    private Button getLink;
    private EditText emailOrUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        getLink = (Button) findViewById(R.id.GetLink_button);
        emailOrUsername = (EditText) findViewById(R.id.EmailOrUsername_ForgetPass_text);

        getLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateEmailOrUsername()) {
                    //TODO: Send email
                }
            }
        });
    }

    public boolean validateEmailOrUsername() {
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
}
