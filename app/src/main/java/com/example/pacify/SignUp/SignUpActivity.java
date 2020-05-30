package com.example.pacify.SignUp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pacify.CommonFunctions;
import com.example.pacify.Constants;
import com.example.pacify.MainActivity;
import com.example.pacify.R;

import java.util.HashMap;
import java.util.Map;


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


    public void createAccount(){
        /**
         * It uses all its public values that was changed through out
         * the signing up process then send a POST request to the server
         */
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = Constants.SIGNUP_URL;
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Toast.makeText(SignUpActivity.this, "Now you can login with" +
                                " your new account", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Toast.makeText(SignUpActivity.this, "An Error occurred," +
                                " Please try again. ", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("email", signUp_email);
                params.put("password", signUp_password);
                params.put("nickname", signUp_name);
                params.put("gender", signUp_gender);
                params.put("phone", signUp_phone_num);
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                params.put("Accept","application/json");
                return params;
            }
        };
        queue.add(postRequest);
    }

    public void openSignUpEmailFragment(){
        CommonFunctions.hideKeyboard(this);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_signUp, new SignUp_Email_Fragment())
                .commit();
    }

    public void openSignUpPasswordFragment(){
        CommonFunctions.hideKeyboard(this);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_signUp, new SignUp_Password_Fragment())
                .commit();
    }

    public void openSignUpDobFragment(){
        CommonFunctions.hideKeyboard(this);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_signUp, new SignUp_DoB_Fragment())
                .commit();
    }

    public void openSignUpPhoneNumberFragment(){
        CommonFunctions.hideKeyboard(this);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_signUp, new SignUp_PhoneNumber_Fragment())
                .commit();
    }

    public void openSignUpGenderFragment(){
        CommonFunctions.hideKeyboard(this);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_signUp, new SignUp_Gender_Fragment())
                .commit();
    }

    public void openSignUpNameFragment(){
        CommonFunctions.hideKeyboard(this);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_signUp, new SignUp_Name_Fragment())
                .commit();
    }

    public void backToMainMenu(){
        CommonFunctions.hideKeyboard(this);
        Intent in = new Intent(SignUpActivity.this, MainActivity.class);
        startActivity(in);
    }
}
