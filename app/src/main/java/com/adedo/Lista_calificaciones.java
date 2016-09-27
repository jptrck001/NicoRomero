package com.adedo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

public class Lista_calificaciones extends Activity {

	private ListView lista;
	private JSONArray ja;
	private String data;
	private ArrayList<Item_calif> arraydir = new ArrayList<Item_calif>();
	private Item_calif item;
	
	Handler h = new Handler(){
		@Override
		public void handleMessage(Message msg){
			super.handleMessage(msg);
			int cantidad = 0;
			cantidad = ja.length();
			JSONArray a;
			try {
				for(int i=0; i<cantidad; i++){
					a = ja.getJSONArray(i);
					String nombrec = a.getString(0);
					String autoc = a.getString(1);
					int viajes = new Integer(a.getString(2)).intValue();
					float promedio = new Float(a.getString(3)).floatValue();
					String mailc = a.getString(4);
					
					item = new Item_calif(nombrec, viajes, (promedio/viajes), autoc, mailc);
					arraydir.add(item);
					
					Collections.sort(arraydir, new ThisIsMyThing());
				}
				listar();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	};
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_calificaciones);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        lista = (ListView) findViewById(R.id.lv_calificaciones);
        
        new Thread(new Runnable() {
			@Override
			public void run() {
				ja = null;
				data = httpGetData(Utilities.getUrl(getApplicationContext()) + "/listar_chofer.php?");
				if (data.length()>0){
					try {
						ja = new JSONArray(data);
					} catch (JSONException e) {
						e.printStackTrace();
					}
					h.sendEmptyMessage(1);
				}
			}
		}).start();   
    }
    
    class ThisIsMyThing implements Comparator {
        public int compare(Object o1, Object o2) {
            String s1 = ((Item_calif)o1).getNombre();
            String s2 = ((Item_calif)o2).getNombre();

            return -1 * s1.compareTo(s2);
        }
    }
    
    public void listar(){
    	// Creo el adapter personalizado
        AdapterDirectivos adapter = new AdapterDirectivos(this, arraydir);

        // Lo aplico
        lista.setAdapter(adapter);
        
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            
        	@Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent i = new Intent(Lista_calificaciones.this, Calificacion.class);
                i.putExtra("parametro1", ((Item_calif) lista.getItemAtPosition(position)).getMail());
                startActivity(i);
            }
            
            /*@Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent i = new Intent(Lista_calificaciones.this, Comentarios.class);
                i.putExtra("parametro", ((Item_calif) lista.getItemAtPosition(position)).getMail());
                startActivity(i);
            }*/
        });
    }
    
    public class AdapterDirectivos extends BaseAdapter {

        protected Activity activity;
        protected ArrayList<Item_calif> items;

        public AdapterDirectivos(Activity activity, ArrayList<Item_calif> items) {
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
            final int pos = position;
            
            //Asociamos el layout de la lista que hemos creado
            if(convertView == null){
                LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inf.inflate(R.layout.item_lista_calificaciones, null);
            }

            // Creamos un objeto directivo
            Item_calif dir = items.get(position);
            
            //Rellenamos el nombre
            TextView nombre = (TextView) v.findViewById(R.id.tv_calif_nombre);
            nombre.setText(dir.getNombre());
            //Rellenamos el nombre
            TextView viajes = (TextView) v.findViewById(R.id.tv_calif_viajes);
            viajes.setText("Viajes = " + dir.getViajes());
            //Rellenamos el nombre
            TextView promedio = (TextView) v.findViewById(R.id.tv_calif_promedio);
            promedio.setText("Calificación promedio (de 1 a 5) = " + new Float(dir.getPromedio()).toString());
            //Rellenamos el nombre
            TextView auto = (TextView) v.findViewById(R.id.tv_calif_auto);
            auto.setText("Vehículo = " + dir.getAuto());

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
