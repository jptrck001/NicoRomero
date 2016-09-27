package com.adedo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;

public class ViajesFechaActual extends Activity {

	private Time today;
	private JSONArray ja;
	private String data;
	private Vector<String> listaview = new Vector<String>();
	private Item_viajes item;
	private ArrayList<Item_viajes> arraydir = new ArrayList<Item_viajes>();
	private ListView lista;
	
	Handler h0 = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			listaview.clear();
			JSONArray a;
			int cantidad = ja.length();
			try {
				for(int i=0; i<cantidad; i++){
					a = ja.getJSONArray(i);		
					String mailc = a.getString(0);
					int diav = new Integer(a.getString(1)).intValue();
					String mesv = a.getString(2);
					int anov = new Integer(a.getString(3)).intValue();
					int horav = new Integer(a.getString(4)).intValue();
					int lugaresv = new Integer(a.getString(5)).intValue();
					String partidav = a.getString(6);
					String llegadav = a.getString(7);
	
					//cargarlos a la lista y mostrarla
					Item_viajes item = new Item_viajes(mailc, diav, mesv, anov, horav, lugaresv, partidav, llegadav);
					arraydir.add(item);
				}
				listar();
			}catch (JSONException e) {
				e.printStackTrace();
			}
			Toast.makeText(getApplicationContext(), "Los viajes se listaron correctamente...", Toast.LENGTH_LONG).show();
			
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_viajes_fecha_actual);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		today = new Time(Time.getCurrentTimezone());
        today.setToNow();
        
        lista = (ListView) findViewById(R.id.lv_viajes2);
        arraydir = new ArrayList<Item_viajes>();

        final String mes = convertirMes(today.month);
        
        new Thread(new Runnable() {
			@Override
			public void run() {
				ja = null;
				data = httpGetData(Utilities.getUrl(getApplicationContext()) + "/listar_viajes.php?diav=" + today.monthDay + "&mesv=" + mes + "&anov=" + today.year);
				if (data.length()>0){
					try {
						ja = new JSONArray(data);
					} catch (JSONException e) {
						e.printStackTrace();
					}
					h0.sendEmptyMessage(1);
				}
			}
		}).start();
	}
	
	public void atras(View view){
    	Intent i = new Intent(ViajesFechaActual.this, ADedo.class);
        startActivity(i);
	}
	
	public void listar(){
    	// Creo el adapter personalizado
        AdapterDirectivos adapter = new AdapterDirectivos(this, arraydir);

        // Lo aplico
        lista.setAdapter(adapter);
    }
	
	public String convertirMes(int mes){
		mes = mes + 1;
		String nombre = "";
		if (mes == 1)
			nombre = "ene";
		if (mes == 2)
			nombre = "feb";
		if (mes == 3)
			nombre = "mar";
		if (mes == 4)
			nombre = "abr";
		if (mes == 5)
			nombre = "may";
		if (mes == 6)
			nombre = "jun";
		if (mes == 7)
			nombre = "jul";
		if (mes == 8)
			nombre = "ago";
		if (mes == 9)
			nombre = "sep";
		if (mes == 10)
			nombre = "oct";
		if (mes == 11)
			nombre = "nov";
		if (mes == 12)
			nombre = "dic";
		return nombre;
	}
	
	public class AdapterDirectivos extends BaseAdapter {

        protected Activity activity;
        protected ArrayList<Item_viajes> items;

        public AdapterDirectivos(Activity activity, ArrayList<Item_viajes> items) {
            this.activity = activity;
            this.items = items;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int arg0) {
            return items.get(arg0);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            // Generamos una convertView por motivos de eficiencia
            View v = convertView;

            //Asociamos el layout de la lista que hemos creado
            if(convertView == null){
                LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inf.inflate(R.layout.item_lista_viajes_fecha_actual, null);
            }

            // Creamos un objeto directivo
            Item_viajes dir = items.get(position);
            //Rellenamos el nombre
            TextView nombre = (TextView) v.findViewById(R.id.tv_nombre);
            nombre.setText("Mail Conductor: " + dir.getMailc());
            //Rellenamos el cantidad
            TextView fecha = (TextView) v.findViewById(R.id.tv_fecha);
            fecha.setText("Fecha del viaje: " + dir.getDiav() + "/" + dir.getMesv() + "/" + dir.getAnov());
            //Rellenamos el vehiculoc
            TextView hora = (TextView) v.findViewById(R.id.tv_hora);
            hora.setText("Hora de saida: " + dir.getHorav());
            //Rellenamos la hora
            TextView lugares = (TextView) v.findViewById(R.id.tv_lugares);
            lugares.setText("Asientos disponibles: " + dir.getLugaresv());
            //Rellenamos la partida
            TextView partida = (TextView) v.findViewById(R.id.tv_partida);
            partida.setText("Ciudad de partida: " + dir.getPartidav());
            //Rellenamos la llegada
            TextView llegada = (TextView) v.findViewById(R.id.tv_llegada);
            llegada.setText("Ciudad de llegada: " + dir.getLlegadav());
            
            // Retornamos la vista
            return v;
        }
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
