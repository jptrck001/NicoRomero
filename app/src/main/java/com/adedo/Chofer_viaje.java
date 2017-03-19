package com.adedo;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.view.View;

public class Chofer_viaje extends Activity {

	private String mailc;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chofer_viaje);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        
        mailc = getIntent().getExtras().getString("parametro1");
	}
	
	public void crear(View view){
    	Intent i = new Intent(Chofer_viaje.this, Viaje_chofer.class);
        i.putExtra("parametro1", mailc);
        startActivity(i);
	}
	
	public void listar(View view){
		Intent i = new Intent(Chofer_viaje.this, Calendar_activity.class);
        startActivity(i);
	}
}
