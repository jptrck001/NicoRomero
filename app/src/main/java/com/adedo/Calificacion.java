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
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RatingBar.OnRatingBarChangeListener;

public class Calificacion extends Activity {
	private RatingBar ratingBar1,ratingBar2,ratingBar3;
	private EditText tv_coment;
	private String mailc;
	
	Handler h1 = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			Toast.makeText(getApplicationContext(), "Su calificación se resgistró correctamente", Toast.LENGTH_SHORT).show();
	        Intent i = new Intent(Calificacion.this, Principal.class);
			i.putExtra("parametro1",mailc);
	        startActivity(i);
		}
	};
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calificacion);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        
        mailc = getIntent().getExtras().getString("parametro1");
        
        ratingBar1 = (RatingBar) findViewById(R.id.rb_viaje);
        ratingBar2 = (RatingBar) findViewById(R.id.rb_resp);
        ratingBar3 = (RatingBar) findViewById(R.id.rb_horarios);
        tv_coment = (EditText) findViewById(R.id.et_calificacion_comentarios);
    }
	
	public void aceptar(View view){
		//que ande este aceptar
		//guardar fotos en BD
		if (tv_coment.getText().toString().equals(""))
			Toast.makeText(getApplicationContext(), "Debe ingresar un comentario", Toast.LENGTH_SHORT).show();
		else{
			new Thread(new Runnable() {		
				@Override
				public void run() {
					httpGetData(Utilities.getUrl(getApplicationContext()) + "/actualizar_calificacion.php?mailc=" + mailc + "&promedioca=" + ((ratingBar1.getRating() + ratingBar2.getRating() + ratingBar3.getRating())/3) + "&comentariosca=" + tv_coment.getText().toString());
					httpGetData(Utilities.getUrl(getApplicationContext()) + "/registro_comentarios.php?mailc=" + mailc + "&comentariosc=" + tv_coment.getText().toString());
					h1.sendEmptyMessage(1);
				}
			}).start();
		}
	}
	
	public void verComentarios(View view){
		Intent i = new Intent(Calificacion.this, Comentarios.class);
        i.putExtra("parametro", mailc);
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
