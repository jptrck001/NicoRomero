package com.adedo;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.util.Log;
import android.view.View;
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
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import static com.adedo.Constants.ProfileUrl;
import static com.adedo.Constants.fbProfileUrl;
import static com.adedo.Constants.gPlusProfileUrl;


public class ADedo extends Activity {


    //Facebook
    CallbackManager callbackManager;
    GraphRequest request;
    private boolean inserted = false;
    private static final int RC_SIGN_IN = 007;
    private SignInButton btnSignIn;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); ;
        AppEventsLogger.activateApp(this);
        setContentView(R.layout.activity_adedo);

        btnSignIn = (SignInButton) findViewById(R.id.btn_sign_in);

        retrieveFromPref();
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        initializeFacebook();
        initGooglePlus();

    }

    private void initGooglePlus() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        // Customizing G+ button
        btnSignIn.setSize(SignInButton.SIZE_STANDARD);
        btnSignIn.setScopes(gso.getScopeArray());

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleSignIn();
            }
        });
    }

    private void googleSignIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
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

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (inserted) {
            if (opr.isDone()) {
                GoogleSignInResult result = opr.get();
                handleSignInResult(result);
            } else {
                opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                    @Override
                    public void onResult(GoogleSignInResult googleSignInResult) {
                        handleSignInResult(googleSignInResult);
                    }
                });
            }

            if (isLoggedIn()) {
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
        } else {
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    handleSignInResult(googleSignInResult);
                }
            });
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
                                    if (Profile.getCurrentProfile() == null) {
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
                                    } else {
                                        facebookPrifle = Profile.getCurrentProfile().getLinkUri().toString();
                                        Log.v("facebook - profile", Profile.getCurrentProfile().getFirstName());
                                    }

                                    goToProfile(name, email, first_name, last_name, facebookPrifle, true);

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

    private void goToProfile(String name, String email, String first_name, String last_name, String profileUrl, boolean fromFace) {
        SharedPreferences settings = getSharedPreferences(Chofer.MY_PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = settings.edit();
        prefEditor.putString("email", email);
        prefEditor.putString("name", name);
        prefEditor.putString("first_name", first_name);
        prefEditor.putString("last_name", last_name);
        prefEditor.putString(ProfileUrl, profileUrl);
        prefEditor.commit();

        Intent i = new Intent(ADedo.this, Principal.class);
        i.putExtra("email", email);
        i.putExtra("name", name);
        i.putExtra("first_name", first_name);
        i.putExtra("last_name", last_name);

        if(fromFace){
            i.putExtra(fbProfileUrl, profileUrl);
        }else{
            i.putExtra(gPlusProfileUrl, profileUrl);
        }

        startActivity(i);
        finish();
    }

    private void handleSignInResult(GoogleSignInResult result) {

        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            String personName = acct.getDisplayName();

            String personPhotoUrl = "";
            if(acct.getPhotoUrl() != null) {
                personPhotoUrl = acct.getPhotoUrl().toString();
            }
            String email = acct.getEmail();

            goToProfile(personName, email, personName, "", personPhotoUrl, false);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        LoginManager.getInstance().logOut();
    }

}
