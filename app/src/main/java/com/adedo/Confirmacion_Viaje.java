package com.adedo;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Confirmacion_Viaje extends Activity {

    private TextView info;
    private String mailc,dia,mes,ano,hora,partida,llegada,cantidad="";
    
    Handler h1 = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			Toast.makeText(getApplicationContext(), "El viaje se registro correctamente", Toast.LENGTH_SHORT).show();
		}
	};
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmacion__viaje);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		Bundle bundle = getIntent().getExtras();

        dia = bundle.getString("parametro1");
        mes = bundle.getString("parametro2");
        ano = bundle.getString("parametro3");
        hora = bundle.getString("parametro4");
        partida = bundle.getString("parametro5");
        llegada = bundle.getString("parametro6");
        cantidad = String.valueOf(bundle.getInt("parametro7"));
        mailc = bundle.getString("parametro8");
        
        info = (TextView) findViewById(R.id.tv_informacion);

		String infoToShow = "El " + dia + " de " + mes + ", año " + ano + " a las " + hora + " horas, de " + partida + " a " + llegada;
		if(cantidad != null && Integer.valueOf(cantidad) != 100){
			infoToShow = infoToShow + ", con " + cantidad + " lugares disponibles.";
		}
		info.setText(infoToShow);
    }

    public void aceptar(View view){
    	new Thread(new Runnable() {		
			@Override
			public void run() {	
				httpGetData(Utilities.getUrl(getApplicationContext()) + "/registro_viaje.php?mailc=" + mailc + "&diav=" + dia + "&mesv=" + mes + "&anov=" + ano + "&horav=" + hora + "&lugaresv=" + cantidad + "&partidav=" + partida + "&llegadav=" + llegada);
				httpGetData(Utilities.getUrl(getApplicationContext()) + "/registro_anotado.php?mailc=" + mailc + "&cantOcupadoa=" + 0);
				h1.sendEmptyMessage(1);
			}
		}).start();

        Intent i = new Intent(Confirmacion_Viaje.this, Principal.class);
		i.putExtra("email",mailc);
        startActivity(i);
    }
    
    public void atras(View view){
    	Intent i = new Intent(Confirmacion_Viaje.this, Principal.class);
        startActivity(i);
	}
    
    public String httpGetData(String mURL){
		String response="";
		mURL= mURL.replace(" ", "%20");
		HttpClient httpclient = new  DefaultHttpClient();
		HttpGet httpget = new HttpGet(mURL);
		ResponseHandler<String> responsehandler = new BasicResponseHandler();
		try {
			response = httpclient.execute(httpget, responsehandler);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}
}
