package com.example.pacify;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.example.pacify.Utilities.PreferenceUtilities;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    private Button signUp_button;
    private Button login_button;
    private LoginButton contWithFacebook_button;
    private long backPressedTime;
    private Toast backToast;

    CallbackManager callbackManager;
    AccessToken accessToken;
    AccessTokenTracker accessTokenTracker;
    boolean isLoggedIn;

    private String facebook_name;
    private String facebook_email;
    private String facebook_profilePicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            if (PreferenceUtilities.getState(this).equals("true")) {
                Intent intent = new Intent(MainActivity.this, NavigationActivity.class);
                startActivity(intent);
            }
        }
        catch(Exception e){
            PreferenceUtilities.saveState("false", this);
            PreferenceUtilities.saveEmail("", this);
            PreferenceUtilities.savePassword("", this);
        }

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
                accessToken = AccessToken.getCurrentAccessToken();
                if (accessToken != null) {
                    //saveFacebookData();
                    loginWithFacebook();
                    Intent intent = new Intent(MainActivity.this, NavigationActivity.class);
                    startActivity(intent);
                    return;
                }
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        contWithFacebook_button = findViewById(R.id.login_forget_password_button);
        contWithFacebook_button.setPermissions("email");
        contWithFacebook_button.setLoginText("CONTINUE WITH FACEBOOK");
        contWithFacebook_button.setLogoutText("LOG OUT");
        callbackManager = CallbackManager.Factory.create();

        contWithFacebook_button.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                accessToken = loginResult.getAccessToken();
                useLoginInformation(accessToken);
                login_button.setText("Log in with facebook");
            }
            @Override
            public void onCancel() {
            }
            @Override
            public void onError(FacebookException exception) {
            }
        });

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken,
                                                       AccessToken currentAccessToken) {
                if (currentAccessToken == null) {
                    login_button.setText("LOG IN");
                }
            }
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onStart() {
        super.onStart();
        /*
        accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken != null) {
            loadFacebookData();
            loginWithFacebook();
        }
        */
    }

    private boolean loginWithFacebook(){
        //TODO(Adham): Send name, email and profile picture
        return true;
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

    private void useLoginInformation(AccessToken accessToken) {
        /**
         Creating the GraphRequest to fetch user details
         1st Param - AccessToken
         2nd Param - Callback (which will be invoked once the request is successful)
         **/
        GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
            //OnCompleted is invoked once the GraphRequest is successful
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    facebook_name = object.getString("name");
                    facebook_email = object.getString("email");
                    facebook_profilePicture = object.getJSONObject("picture").getJSONObject("data").getString("url");
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

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2500 > System.currentTimeMillis()) {
            backToast.cancel();
            this.finishAffinity();
            return;
        } else {
            backToast = Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }
}
