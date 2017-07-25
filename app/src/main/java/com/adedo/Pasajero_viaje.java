package com.adedo;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.view.Menu;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.NativeExpressAdView;

public class Pasajero_viaje extends Activity {

	NativeExpressAdView mAdView;
	private String mailp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pasajero_viaje);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		mAdView = (NativeExpressAdView) findViewById(R.id.adView_pasajero);
		AdRequest request = new AdRequest.Builder().build();
		mAdView.loadAd(request);

		mailp = getIntent().getExtras().getString("parametro1");
	}

	public void buscar(View view){
    	Intent i = new Intent(Pasajero_viaje.this, CrearViajePasajero.class);
        i.putExtra("parametro1", mailp);
        startActivity(i);
	}
	
	public void listado(View view){
    	//Intent i = new Intent(Pasajero_viaje.this, ViajeActualPasajero.class);
        //startActivity(i);
		Intent i = new Intent(Pasajero_viaje.this, Calendar_activity.class);
		startActivity(i);
	}
}
