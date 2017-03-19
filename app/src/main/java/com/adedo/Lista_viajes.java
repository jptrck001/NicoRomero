package com.adedo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import android.net.NetworkInfo;

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

public class Lista_viajes extends Activity {

    private String mailp, dia, mes, ano = "";
    private JSONArray ja, ja2, ja3;
    private String data, data2, data3;
    private Vector<String> listaview = new Vector<String>();
    private Item_viaje item;
    private ArrayList<Item_viaje> arraydir = new ArrayList<Item_viaje>();
    private ListView lista;
    private TextView fecha;

    private static String mc = "";

    Handler h0 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            listaview.clear();
            JSONArray a;
            int cantidad = ja.length();
            try {
                for (int i = 0; i < cantidad; i++) {
                    a = ja.getJSONArray(i);
                    String mailc = a.getString(0);
                    String nombrec = a.getString(1);
                    int lugaresv = new Integer(a.getString(2)).intValue();
                    int lugares_ocupados = new Integer(a.getString(3)).intValue();
                    String facebookc = a.getString(4);
                    String vehiculoc = a.getString(5);
                    String partida = a.getString(6);
                    String llegada = a.getString(7);
                    int hora = new Integer(a.getString(8)).intValue();
                    float promedio = new Float(a.getString(9)).floatValue();
                    int viajes = new Integer(a.getString(10)).intValue();
                    int idv = new Integer(a.getString(11)).intValue();

                    String comment = a.getString(11);
                    String perfil = a.getString(11);

                    if ((lugaresv - lugares_ocupados) > 0) {
                        item = new Item_viaje(getResources().getDrawable(R.drawable.ic_launcher_dorado), nombrec,
                                (lugaresv - lugares_ocupados), mailc, lugares_ocupados, facebookc, vehiculoc, partida,
                                llegada, hora, promedio, viajes, idv, comment, perfil);
                        arraydir.add(item);
                    }
                }
                listar();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Toast.makeText(getApplicationContext(), "Listó correctamente la información", Toast.LENGTH_SHORT).show();
        }
    };

    Handler h1 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    ja3 = null;
                    data3 = httpGetData(Utilities.getUrl(getApplicationContext()) + "/consulta_pasajeros.php?mailp=" + mailp);
                    if (data3.length() > 0) {
                        try {
                            ja3 = new JSONArray(data3);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        h2.sendEmptyMessage(1);
                    }
                }
            }).start();
        }
    };

    Handler h2 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            listaview.clear();
            JSONArray a;
            int cantidad = ja3.length();
            try {
                a = ja3.getJSONArray(0);
                String nombrep = a.getString(0);
                int telp = new Integer(a.getString(1)).intValue();
                int dnip = new Integer(a.getString(2)).intValue();
                String facebookp = a.getString(3);
                String fechaNacp = a.getString(4);

                Toast.makeText(getApplicationContext(), "Viaje elegido", Toast.LENGTH_SHORT).show();
                sendFeedback(nombrep, telp, dnip, facebookp, fechaNacp);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_viajes);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        dia = getIntent().getExtras().getString("parametro1");
        mes = getIntent().getExtras().getString("parametro2");
        ano = getIntent().getExtras().getString("parametro3");
        mailp = getIntent().getExtras().getString("parametro4");

        fecha = (TextView) findViewById(R.id.textView4);
        fecha.setText("Conductores disponibles para: " + dia + " de " + mes + " de " + ano);
        lista = (ListView) findViewById(R.id.lv_viajes);
        arraydir = new ArrayList<Item_viaje>();

        new Thread(new Runnable() {
            @Override
            public void run() {
                ja = null;
                data = httpGetData(Utilities.getUrl(getApplicationContext()) + "/listar_viaje_consulta_chofer_anotado.php?diav=" + dia + "&mesv=" +
                        mes + "&anov=" + ano);
                if (data.length() > 0) {
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

    public void atras(View view) {
        Intent i = new Intent(Lista_viajes.this, ADedo.class);
        startActivity(i);
    }

    public void listar() {
        // Creo el adapter personalizado
        AdapterDirectivos adapter = new AdapterDirectivos(this, arraydir);

        // Lo aplico
        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                    long id) {

                final Item_viaje item = (Item_viaje) lista.getItemAtPosition(position);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if ((item.getCantOcupadoa() + 1) <= (item.getCantidad_asientos_disponibles() + item.getCantOcupadoa())) {
                            httpGetData(Utilities.getUrl(getApplicationContext()) + "/actualizacion_anotado.php?mailc=" + item.getMailc() +
									"&cantOcupadoa=" + (
                                    item.getCantOcupadoa() + 1) + "&idv=" + item.getIdv());
                            mc = item.getMailc();
                            h1.sendEmptyMessage(1);
                        }
                    }
                }).start();
                Intent i = new Intent(Lista_viajes.this, Principal.class);
                i.putExtra("parametro1", mailp);
                startActivity(i);
            }
        });
    }

    private void sendFeedback(String nombrep, int telp, int dnip, String facebookp, String fechaNacp) {

        // Checks if the device is connected to the Internet.
        if (isDeviceConnected()) {

            // Set the action to be performed
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);

            // E-mail addresses that should be delivered to.
            sendIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {mc});

            // Set the subject line of a message
            sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Una persona quiere compartir tu viaje");

            // Set the subject line of a message
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Hola! Me gustaría compartir un viaje con vos. Si estás interesado, mis datos son: Nombre y " +
					"Apellido: " + nombrep + ", Teléfono: " + telp + ", D.N.I.: " + dnip + ", Facebook: " + facebookp + ", Fecha de Nacimiento: " +
					fechaNacp + "\n\nA Dedo recomienda a sus usuarios constatar la veracidad de la información de personas a través de la página " +
					"web: www.buscardatos.com");

            // Set the data type of the message
            sendIntent.setType("plain/text");
            startActivity(Intent.createChooser(sendIntent, "Enviando Mail..."));

        } else
            Toast.makeText(getApplicationContext(), "El dispositivo no está conectado a la Internet", Toast.LENGTH_LONG).show();
    }

    private boolean isDeviceConnected() {

        final ConnectivityManager connectManager = (ConnectivityManager) getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        return (
                connectManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                              .getState() == NetworkInfo.State.CONNECTED || connectManager
                        .getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED);
    }

    public class AdapterDirectivos extends BaseAdapter {

        protected Activity activity;
        protected ArrayList<Item_viaje> items;

        public AdapterDirectivos(Activity activity, ArrayList<Item_viaje> items) {
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
            if (convertView == null) {
                LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inf.inflate(R.layout.item_lista_viajes, null);
            }

            // Creamos un objeto directivo
            Item_viaje dir = items.get(position);
            //Rellenamos la fotografía
            ImageView foto = (ImageView) v.findViewById(R.id.imageView_viajes);
            foto.setImageDrawable(dir.getFoto());
            //Rellenamos el nombre
            TextView nombre = (TextView) v.findViewById(R.id.tv_viaje);
            nombre.setText(dir.getNombre());
            //Rellenamos el cantidad
            TextView canti_disp = (TextView) v.findViewById(R.id.tv_cant);
            canti_disp.setText("" + dir.getCantidad_asientos_disponibles());
            //Rellenamos el facebookc
            TextView facebookc = (TextView) v.findViewById(R.id.textView10);
            facebookc.setText("Facebook: " + dir.getFacebookc());
            //Rellenamos el vehiculoc
            TextView vehiculoc = (TextView) v.findViewById(R.id.textView11);
            vehiculoc.setText("Vehículo: " + dir.getVehiculoc());
            //Rellenamos la partida
            TextView partida = (TextView) v.findViewById(R.id.textView12);
            partida.setText("Ciudad de partida: " + dir.getPartida());
            //Rellenamos la llegada
            TextView llegada = (TextView) v.findViewById(R.id.textView13);
            llegada.setText("Ciudad de llegada: " + dir.getLlegada());
            //Rellenamos la hora
            TextView hora = (TextView) v.findViewById(R.id.textView14);
            hora.setText("Hora de salida: " + dir.getHora() + ":00");
            /*//Rellenamos el promedio
            RatingBar promedio = (RatingBar) v.findViewById(R.id.ratingBar1);
            promedio.setRating(dir.getPromedio()/dir.getViajes());
            promedio.setIsIndicator(true);
            promedio.setEnabled(false);*/

            // Retornamos la vista
            return v;
        }
    }

    public String httpGetData(String mURL) {
        String response = "";
        mURL = mURL.replace(" ", "%20");
        HttpClient httpclient = new DefaultHttpClient();
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
