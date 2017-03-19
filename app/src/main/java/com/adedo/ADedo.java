package com.adedo;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.view.View;
import android.widget.RelativeLayout;
import com.facebook.appevents.AppEventsLogger;


public class ADedo extends Activity {

    private RelativeLayout rv_start_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); ;
        AppEventsLogger.activateApp(this);
        setContentView(R.layout.activity_adedo);

        rv_start_btn = (RelativeLayout) findViewById(R.id.rv_start_btn);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        rv_start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ADedo.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

}
