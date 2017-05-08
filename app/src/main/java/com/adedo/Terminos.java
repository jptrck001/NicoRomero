package com.adedo;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;

public class Terminos extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_terminos);
	}

	public void anterior(View view){
    	finish();
    }

}
