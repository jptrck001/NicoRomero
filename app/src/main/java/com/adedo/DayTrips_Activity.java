package com.adedo;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Rulo-PC on 23/4/2016.
 */
public class DayTrips_Activity extends Activity {

    private RecyclerView trips_day_list;
    private String data;
    private JSONArray ja;
    private Vector<String> listaview = new Vector<String>();
    private ArrayList<Item_viaje> arraydir = new ArrayList<Item_viaje>();
    private static Date day;
    private Set<Item_viaje> tripsSet;
    private Handler h0;
    private String dia, mes, año;
    private TextView publicados;
    private RelativeLayout empty_list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_calendar_day);

        publicados = (TextView) findViewById(R.id.publicados);
        trips_day_list = (RecyclerView) findViewById(R.id.trips_day_list);

        empty_list = (RelativeLayout) findViewById(R.id.empty_list);

        dia = getIntent().getStringExtra("dia");
        mes = ViajeActualPasajero.convertirMes(Integer.valueOf(getIntent().getStringExtra("mes")));
        año = getIntent().getStringExtra("año");

        publicados = (TextView) findViewById(R.id.publicados);


        publicados.setText(publicados.getText() + " " + dia + " de " + mes + " " + año);

        final ArrayList<String> aMailc = new ArrayList<>();

        h0 = new Handler() {
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
                        int idv = new Integer(a.getString(9)).intValue();

                        String comentario, perfil;
                        if (a.length() > 10) {
                            comentario = new String(a.getString(10));
                            perfil = new String(a.getString(11));
                        } else {
                            comentario = "";
                            perfil = "";
                        }

                        if (mailc != null && !mailc.isEmpty()) {
                            if (((lugaresv - lugares_ocupados) > 0 || (lugaresv == 100)) && (!aMailc.contains(mailc + "," + (String.valueOf(hora)))
                            )) {
                                aMailc.add(mailc + "," + (String.valueOf(hora)));
                                Item_viaje item = new Item_viaje(getResources().getDrawable(R.drawable.ic_launcher_dorado),
                                        nombrec, (lugaresv - lugares_ocupados), mailc, lugares_ocupados, facebookc, vehiculoc,
                                        partida, llegada, hora, 0, 0, idv, comentario, perfil);
                                arraydir.add(item);
                            }
                        }
                    }

                    tripsSet = new HashSet<>(arraydir);
                    listar();

                    if (arraydir.size() <= 0) {
                        empty_list.setVisibility(View.VISIBLE);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //Toast.makeText(getApplicationContext(), "Los viajes se listaron correctamente...", Toast.LENGTH_LONG).show();

            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                ja = null;

                //data = httpGetData("http://appandroidcli.esy.es/ADedo/listar_viaje_consulta_chofer_anotado.php?diav=" + dia + "&mesv=" + mes +
                // "&anov=" + año);
                data = httpGetData(Utilities.getUrl(getApplicationContext()) + "/listar_viaje_consulta_chofer_anotado.php?diav=" + dia + "&mesv=" +
                        mes + "&anov=" + año);
                if (data != null) {
                    if (data.length() > 0) {
                        try {
                            ja = new JSONArray(data);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        h0.sendEmptyMessage(1);
                    }
                }
            }
        }).start();

    }

    public void listar() {
        DayTripsAdapter adapter = new DayTripsAdapter(this, tripsSet);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        trips_day_list.setLayoutManager(linearLayoutManager);
        trips_day_list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    // convert inputstream to String
    private static String convertInputStreamToString(HttpResponse response) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        String line = "";
        String result = "";
        while ((line = in.readLine()) != null) { result += line; }

        in.close();
        return result;

    }

    public class GetExample {
        OkHttpClient client = new OkHttpClient();

        @TargetApi(Build.VERSION_CODES.KITKAT)
        String run(String url) throws IOException {
            Request request = new Request.Builder()
                    .url(url)
                    .addHeader("content-type", "text/plain")
                    .addHeader("content-encoding", "utf-8")
                    .build();

            Response response = client.newCall(request).execute();
            return response.body().string();
        }
    }


    public String httpGetData(String mURL) {
        mURL = mURL.replace(" ", "%20");
        GetExample example = new GetExample();
        String response = null;
        try {
            response = example.run(mURL);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
}
