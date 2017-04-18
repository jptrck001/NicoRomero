package com.adedo;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;


public class ADedo extends Activity {


    //Facebook
    CallbackManager callbackManager;
    GraphRequest request;
    private String radioChecked = "";
    private boolean inserted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); ;
        AppEventsLogger.activateApp(this);
        setContentView(R.layout.activity_adedo);

        retrieveFromPref();
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        initializeFacebook();

    }

    public boolean isLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }


    public void retrieveFromPref() {
        SharedPreferences prefs = getSharedPreferences(Chofer.MY_PREFS_NAME, MODE_PRIVATE);
        inserted = prefs.getBoolean(prefs.getString("email", "") + "-Inserted", false);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (isLoggedIn() && inserted) {
            Intent i = new Intent(ADedo.this, Principal.class);
            SharedPreferences prefs = getSharedPreferences(Chofer.MY_PREFS_NAME, MODE_PRIVATE);

            String email = prefs.getString("email", "");
            String name = prefs.getString("name", "");
            String first_name = prefs.getString("first_name", "");
            String last_name = prefs.getString("last_name", "");
            if (!(email.isEmpty() || name.isEmpty() || first_name.isEmpty() || last_name.isEmpty())) {
                i.putExtra("email", email);
                i.putExtra("name", name);
                i.putExtra("first_name", first_name);
                i.putExtra("last_name", last_name);
                startActivity(i);
                finish();
            }
        }

    }

    private void initializeFacebook() {
        callbackManager = CallbackManager.Factory.create();


        final LoginButton loginButton = (LoginButton) findViewById(R.id.signin_button_fb);
        loginButton.setText("Login");
        loginButton.setReadPermissions(Arrays.asList("public_profile, email"));

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            private ProfileTracker mProfileTracker;

            @Override
            public void onSuccess(LoginResult loginResult) {

                request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {
                                // Application code
                                try {
                                    String email = (object.has("email")) ? object.getString("email") : "";
                                    String name = (object.has("name")) ? object.getString("name") : "";
                                    String first_name = (object.has("first_name")) ? object.getString("first_name") : "";
                                    String last_name = (object.has("last_name")) ? object.getString("last_name") : "";

                                    String facebookPrifle = "";
                                    if(Profile.getCurrentProfile() == null) {
                                        mProfileTracker = new ProfileTracker() {
                                            @Override
                                            protected void onCurrentProfileChanged(Profile profile, Profile profile2) {
                                                // profile2 is the new profile
                                                Log.v("facebook - profile", profile2.getFirstName());
                                                mProfileTracker.stopTracking();
                                            }
                                        };
                                        // no need to call startTracking() on mProfileTracker
                                        // because it is called by its constructor, internally.
                                    }
                                    else {
                                        facebookPrifle = Profile.getCurrentProfile().getLinkUri().toString();
                                        Log.v("facebook - profile",  Profile.getCurrentProfile().getFirstName());
                                    }


                                    SharedPreferences settings = getSharedPreferences(Chofer.MY_PREFS_NAME, MODE_PRIVATE);
                                    SharedPreferences.Editor prefEditor = settings.edit();
                                    prefEditor.putString("email", email);
                                    prefEditor.putString("name", name);
                                    prefEditor.putString("first_name", first_name);
                                    prefEditor.putString("last_name", last_name);
                                    prefEditor.putString("facebookPrifle", facebookPrifle);
                                    prefEditor.commit();

                                    Intent i = new Intent(ADedo.this, Principal.class);
                                    i.putExtra("email", email);
                                    i.putExtra("name", name);
                                    i.putExtra("first_name", first_name);
                                    i.putExtra("last_name", last_name);
                                    startActivity(i);
                                    finish();

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender, birthday,first_name,last_name");
                request.setParameters(parameters);
                request.executeAsync();

            }

            @Override
            public void onCancel() {
                int i = 0;
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(), "Error de conexión", Toast.LENGTH_SHORT);
            }

        });
    }

    private void fetchProfile() {
        GraphRequest request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        // this is where you should have the profile
                        Log.v("fetched info", object.toString());
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link"); //write the fields you need
        request.setParameters(parameters);
        request.executeAsync();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        LoginManager.getInstance().logOut();
    }

}
