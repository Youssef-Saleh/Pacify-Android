package com.example.pacify;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.pacify.Forget_Password.ForgetPasswordActivity;
import com.example.pacify.Utilities.PreferenceUtilities;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


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
                        , ForgetPasswordActivity.class);
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
        /**
         * Check login credentials and get user type
         */
        String emailInput = editTextEmail.getText().toString().trim();
        String passwordInput = editTextPassword.getText().toString();


        //return SendLoginRequest();
        SendLoginRequest();

        return true;
    }

    private boolean SendLoginRequest(){
        RequestQueue queue = Volley.newRequestQueue(this);
        final boolean[] successful = new boolean[1];
        String url = Constants.LOGIN;
        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // response
                        try {
                            if(response.getString("login").equals("successful")){
                                //Toast.makeText(LoginActivity.this, "Email " +
                                //        "sent successfully", Toast.LENGTH_SHORT).show();
                                successful[0] = true;
                            }
                            else{
                                Toast.makeText(LoginActivity.this, "Wrong email or " +
                                        "password", Toast.LENGTH_SHORT).show();
                                successful[0] = false;
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Toast.makeText(LoginActivity.this, "An Error occurred," +
                                " please try again", Toast.LENGTH_SHORT).show();
                        successful[0] = false;
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("email", editTextEmail.getText().toString().trim());
                params.put("password", editTextPassword.getText().toString());

                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/json");
                return params;
            }
        };
        queue.add(postRequest);
        return successful[0];
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
        if(!editTextEmail.getText().toString().trim().equals("artist@pacify.com")) {
            Intent intent = new Intent(LoginActivity.this, NavigationActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("username", "Dummy");
            intent.putExtras(bundle);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        else{
            Intent intent = new Intent(LoginActivity.this, ArtistActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("username", "Dummy");
            intent.putExtras(bundle);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
