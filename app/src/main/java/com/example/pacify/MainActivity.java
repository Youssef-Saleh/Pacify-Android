package com.example.pacify;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.pacify.SignUp.SignUpActivity;
import com.example.pacify.Utilities.PreferenceUtilities;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Adham Mahmoud
 * @version 1
 * This class (Activity) is the first then appear when the app is opened
 * it shows sign up, continue with Facebook and log in options
 */
public class MainActivity extends AppCompatActivity {

    private Button signUp_button;
    private Button login_button;
    private LoginButton contWithFacebook_button;
    private long backPressedTime;
    private Toast backToast;

    CallbackManager callbackManager;
    AccessToken accessToken;

    private String facebook_name;
    private String facebook_email;
    private String facebook_profilePicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signUp_button = findViewById(R.id.signUp_button);
        signUp_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        login_button = findViewById(R.id.login_button);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        contWithFacebook_button = findViewById(R.id.login_forget_password_button);
        contWithFacebook_button.setPermissions("email");
        contWithFacebook_button.setLoginText("CONTINUE WITH FACEBOOK");
        contWithFacebook_button.setLogoutText("LOGGING IN...");
        callbackManager = CallbackManager.Factory.create();

        contWithFacebook_button.registerCallback(callbackManager, new FacebookCallback<LoginResult>(){
            /**
             * If logging in with facebook was successful, log him in.
             * And if the data was not received yet, Cancel operation
             */
            @Override
            public void onSuccess(LoginResult loginResult) {

                accessToken = loginResult.getAccessToken();
                useLoginInformation(accessToken);
                contWithFacebook_button.setEnabled(false);
                login_button.setEnabled(false);
                signUp_button.setEnabled(false);

                new Handler().postDelayed(new Runnable() {
                    /**
                     * Making some delay to give some time until
                     * the data are retrieved from facebook
                     */
                    @Override
                    public void run() {
                        if (facebook_name == null){
                            LoginManager.getInstance().logOut();
                            afterFbLoginFail();
                            return;
                        }
                        loginWithFacebook();
                        Intent in = new Intent(MainActivity.this
                                , NavigationActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("fb_username", facebook_name);
                        in.putExtras(bundle);
                        startActivity(in);
                    }
                }, 1500);
            }
            @Override
            public void onCancel() {
            }
            @Override
            public void onError(FacebookException exception) {
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * If the app was closed and the user opens it, check if was
     * logged in by facebook, and if yes take him to home page
     */
    @Override
    public void onStart() {
        super.onStart();

        accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken != null) {
            useLoginInformation(accessToken);
            contWithFacebook_button.setEnabled(false);
            login_button.setEnabled(false);
            signUp_button.setEnabled(false);

            new Handler().postDelayed(new Runnable() {
                    /**
                     * Making some delay to give some time until
                     * the data are retrieved from facebook
                     */
                    @Override
                    public void run() {
                        if(facebook_name == null){
                            LoginManager.getInstance().logOut();
                            afterFbLoginFail();
                            return;
                        }
                        loginWithFacebook();
                        Intent in = new Intent(MainActivity.this
                                , NavigationActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("fb_username", facebook_name);
                        in.putExtras(bundle);
                        startActivity(in);
                    }
            }, 1500);
        }
    }

    /**
     * Data should be sent to the server from here
     */
    private boolean loginWithFacebook(){
        //Should send name, email and profile picture
        return true;
    }

    /**
     * Enabling the buttons and telling the user that the login
     * with facebook failed.
     */
    private void afterFbLoginFail(){
        contWithFacebook_button.setEnabled(true);
        login_button.setEnabled(true);
        signUp_button.setEnabled(true);

        Toast.makeText(getBaseContext(), "Login failed\nTry again"
                , Toast.LENGTH_SHORT).show();
    }

    private void saveFacebookData(){
        PreferenceUtilities.saveFacebookPP(facebook_profilePicture,MainActivity.this);
        PreferenceUtilities.saveFacebookEmail(facebook_email,MainActivity.this);
        PreferenceUtilities.saveFacebookName(facebook_name,MainActivity.this);
    }

    private void loadFacebookData(){
        facebook_name = PreferenceUtilities.getFacebookName(this);
        facebook_email = PreferenceUtilities.getFacebookEmail(this);
        facebook_profilePicture = PreferenceUtilities.getFacebookPP(this);
    }

    /**
     * Creating the GraphRequest to fetch user details
     * @param accessToken
     * Source: https://androidclarified.com/android-facebook-login-example/
     */
    private void useLoginInformation(AccessToken accessToken) {
        GraphRequest request = GraphRequest.newMeRequest(accessToken
                , new GraphRequest.GraphJSONObjectCallback() {
            //OnCompleted is invoked once the GraphRequest is successful
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    facebook_name = object.getString("name");
                    facebook_email = object.getString("email");
                    facebook_profilePicture = object.getJSONObject("picture").getJSONObject("data")
                            .getString("url");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        // We set parameters to the GraphRequest using a Bundle.
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,picture.width(200)");
        request.setParameters(parameters);
        // Initiate the GraphRequest
        request.executeAsync();
    }

    /**
     * Close the app after pressing back twice in 2.5 sec.
     */
    @Override
    public void onBackPressed() {
        if (backPressedTime + 2500 > System.currentTimeMillis()) {
            backToast.cancel();
            this.finishAffinity();
            return;
        } else {
            backToast = Toast.makeText(getBaseContext(), "Press back again to exit"
                    , Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }
}
