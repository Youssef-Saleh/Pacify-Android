package com.example.pacify.Forget_Password;

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
import com.example.pacify.LoginActivity;
import com.example.pacify.NavigationActivity;
import com.example.pacify.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ForgetPasswordActivity extends AppCompatActivity {

    public String VerCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        openForgetPasswordStep1Fragment();
    }

    public void GenerateRandChars_ForgetPass(int size){
        VerCode = CommonFunctions.GenerateRandChars(size);
    }

    public void SendEmailRequest(final String mail){
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
                                Toast.makeText(ForgetPasswordActivity.this, "Email " +
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
                        Toast.makeText(ForgetPasswordActivity.this, "An Error occurred," +
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

    public boolean RecoverPasswordRequest(final String newPass){
        RequestQueue queue = Volley.newRequestQueue(this);
        final boolean[] successful = new boolean[1];
        String url = Constants.RECOVER_PASSWORD;
        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // response
                        try {
                            if(response.getString("changed").equals("successful")){
                                Toast.makeText(ForgetPasswordActivity.this, "Password " +
                                        "changed successfully", Toast.LENGTH_SHORT).show();
                                successful[0] = true;
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
                        Toast.makeText(ForgetPasswordActivity.this, "An Error occurred," +
                                " please try again", Toast.LENGTH_SHORT).show();
                        successful[0] = false;
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("password", newPass);

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

    public void openLoginActivity(){
        Intent intent = new Intent(ForgetPasswordActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    public void openForgetPasswordStep1Fragment(){
        CommonFunctions.hideKeyboard(this);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_forgetPassword, new ForgetPasswordStep1_Fragment())
                .commit();
    }

    public void openForgetPasswordStep2Fragment(){
        CommonFunctions.hideKeyboard(this);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_forgetPassword, new ForgetPasswordStep2_Fragment())
                .commit();
    }

    public void openForgetPasswordStep3Fragment(){
        CommonFunctions.hideKeyboard(this);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_forgetPassword, new ForgetPasswordStep3_Fragment())
                .commit();
    }

}
