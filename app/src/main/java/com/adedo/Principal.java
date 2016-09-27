package com.adedo;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.view.View;

import com.facebook.login.LoginManager;

public class Principal extends Activity {
    private String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if(getIntent().hasExtra("email")) {
            if (getIntent().getExtras().containsKey("email")) {
                email = getIntent().getExtras().getString("email");
            }
        }
    }

    public void chofer(View view){
//        Intent i = new Intent(Principal.this, Chofer.class);
//        startActivity(i);
        Intent i = new Intent(Principal.this, Chofer_viaje.class);
        i.putExtra("parametro1", email);
        startActivity(i);
    }

    public void pasajero(View view){
//        Intent i = new Intent(Principal.this, Pasajero.class);
//        startActivity(i);
        Intent i = new Intent(Principal.this, Pasajero_viaje.class);
        i.putExtra("parametro1", email);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        LoginManager.getInstance().logOut();
        finish();
    }
}
