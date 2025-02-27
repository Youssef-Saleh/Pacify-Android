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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.pacify.CommonFunctions;
import com.example.pacify.Constants;
import com.example.pacify.Forget_Password.ForgetPasswordActivity;
import com.example.pacify.MainActivity;
import com.example.pacify.NavigationActivity;
import com.example.pacify.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class SignUpActivity extends AppCompatActivity {

    public String signUp_email;
    public String signUp_password;
    public int signUp_dob_day;
    public int signUp_dob_month;
    public int signUp_dob_year;
    public String signUp_dob;
    public String signUp_gender;
    public String signUp_name;
    public String signUp_phone_num;
    public String VerCode;
    public boolean successful = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        openSignUpEmailFragment();
    }

    /**
     * It uses all its public values that was changed through out
     * the signing up process then send a POST request to the server
     */
    public void createAccount(){


        RequestQueue queue = Volley.newRequestQueue(this);
        String url = Constants.SIGNUP_URL;
        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // response
                        Toast.makeText(SignUpActivity.this, "Now you can login with" +
                                " your new account", Toast.LENGTH_SHORT).show();
                        successful = true;

                        try {
                            successful = response.getString("signup").equals("successful");
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
                        Toast.makeText(SignUpActivity.this, "An Error occurred," +
                                " Please try again. ", Toast.LENGTH_SHORT).show();
                        successful = true;
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("email", signUp_email);
                params.put("password", signUp_password);
                params.put("name", signUp_name);
                params.put("gender", signUp_gender);
                params.put("phone_num", signUp_phone_num);
                params.put("dob", signUp_dob);
                params.put("type", "free");

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

    public void openSignUpVerificationFragment(){
        VerCode = CommonFunctions.GenerateRandChars(6);
        SendEmailRequest(signUp_email);
        CommonFunctions.hideKeyboard(this);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_signUp, new SignUp_VerifyEmail_Fragment())
                .commit();
    }

    private void SendEmailRequest(final String mail){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = Constants.SEND_EMAIL;
        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // response
                        try {
                            if(response.getString("sent").equals("successful")){
                                Toast.makeText(SignUpActivity.this, "Email " +
                                        "sent successfully", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(SignUpActivity.this, "An Error occurred," +
                                " no email is sent", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("email", mail);
                params.put("VerCode", VerCode);

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
    }

    public void logTheUserOn(){
        CommonFunctions.hideKeyboard(this);
        if(successful){
            Intent in = new Intent(SignUpActivity.this, NavigationActivity.class);
            startActivity(in);
        }
        else {
            Intent in = new Intent(SignUpActivity.this, MainActivity.class);
            startActivity(in);
        }
    }
}
