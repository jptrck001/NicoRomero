package com.adedo;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.view.View;

import com.facebook.Profile;
import com.facebook.login.LoginManager;

import static com.adedo.Constants.ProfileUrl;
import static com.adedo.Constants.fbProfileUrl;
import static com.adedo.Constants.gPlusProfileUrl;

public class Principal extends Activity {

    private String email = "";
    private String fecabookProfile = "";
    private String gmailUrl = "";

    private boolean inserted;

    String name, first_name, last_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if (getIntent().hasExtra("email")) {
            if (getIntent().getExtras().containsKey("email")) {
                email = getIntent().getExtras().getString("email");
            }
        }

        if (getIntent().hasExtra(fbProfileUrl)) {
            if (getIntent().getExtras().containsKey(fbProfileUrl)) {
                fecabookProfile = getIntent().getExtras().getString(fbProfileUrl);
            }
        }

        if (getIntent().hasExtra(gPlusProfileUrl)) {
            if (getIntent().getExtras().containsKey(gPlusProfileUrl)) {
                gmailUrl = getIntent().getExtras().getString(gPlusProfileUrl);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void retrieveFromPref() {
        SharedPreferences prefs = getSharedPreferences(Chofer.MY_PREFS_NAME, MODE_PRIVATE);
        inserted = prefs.getBoolean(prefs.getString("email", "") + "-Inserted", false);
        name = prefs.getString("name", "");
        first_name = prefs.getString("first_name", "");
        last_name = prefs.getString("last_name", "");

        //String facebookPrifle = Profile.getCurrentProfile().getLinkUri().toString();
        SharedPreferences settings = getSharedPreferences(Chofer.MY_PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = settings.edit();

        if(!fecabookProfile.isEmpty()) {
            prefEditor.putString(ProfileUrl, fecabookProfile);
            prefEditor.commit();
        }else if(!gmailUrl.isEmpty()){
            prefEditor.putString(ProfileUrl, gmailUrl);
            prefEditor.commit();
        }

    }

    public void chofer(View view) {

        retrieveFromPref();
        if (!inserted) {

            Intent i = new Intent(Principal.this, Chofer.class);
            i.putExtra("email", email);
            i.putExtra("name", name);
            i.putExtra("first_name", first_name);
            i.putExtra("last_name", last_name);
            startActivity(i);
            finish();
        } else {
            Intent i = new Intent(Principal.this, Chofer_viaje.class);
            i.putExtra("parametro1", email);
            startActivity(i);
        }
    }

    public void pasajero(View view) {

        retrieveFromPref();
        if (!inserted) {
            Intent i = new Intent(Principal.this, Pasajero.class);
            i.putExtra("email", email);
            i.putExtra("name", name);
            i.putExtra("first_name", first_name);
            i.putExtra("last_name", last_name);
            startActivity(i);
            finish();
        } else {
            Intent i = new Intent(Principal.this, Pasajero_viaje.class);
            i.putExtra("parametro1", email);
            startActivity(i);
        }
    }

    @Override
    public void onBackPressed() {
        LoginManager.getInstance().logOut();
        finish();
    }
}
