package com.adedo;

import java.io.IOException;
import java.util.ArrayList;

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
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Comentarios extends Activity {

	private ListView lista;
	private JSONArray ja;
	private String data;
	private ArrayList<String> arraydir = new ArrayList<String>();
	private String mailc;
	
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
					String comentarioc = a.getString(0);
					arraydir.add(comentarioc);
				}
				listar();
				Toast.makeText(getApplicationContext(), "Se listaron correctamente los comentarios", Toast.LENGTH_SHORT).show();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comentarios);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mailc = getIntent().getExtras().getString("parametro");
        
        lista = (ListView) findViewById(R.id.lv_comentarios);
        
        new Thread(new Runnable() {
			@Override
			public void run() {
				ja = null;
				data = httpGetData(Utilities.getUrl(getApplicationContext()) + "/listar_comentarios_chofer.php?mailc=" + mailc);
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
    
    public void listar(){
    	AdapterDirectivos adapter = new AdapterDirectivos(this, arraydir);
    	lista.setAdapter(adapter);
    }
    
    public class AdapterDirectivos extends BaseAdapter {

        protected Activity activity;
        protected ArrayList<String> items;

        public AdapterDirectivos(Activity activity, ArrayList<String> items) {
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
                v = inf.inflate(R.layout.item_comentarios, null);
            }
            
            //Rellenamos el nombre
            TextView nombre = (TextView) v.findViewById(R.id.tv_coment);
            nombre.setText(items.get(position));
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
