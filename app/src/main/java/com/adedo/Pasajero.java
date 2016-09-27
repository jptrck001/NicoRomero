package com.adedo;

import java.io.IOException;
import java.util.Date;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

//import com.example.adedo.Chofer.PhotoTaker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import static com.adedo.Chofer.MY_PREFS_NAME;

public class Pasajero extends Activity {

    private ImageButton im_btn;
    private EditText mailp, nombrep, telefonop, direccionp, dnip, facebookp;
    //public static final int REQUEST_IMAGE_CAPTURE = 1;
    //private byte[] imageCache;
    //private Bitmap thumbnail;
    private Context contetx;
    //private String b64Picture;
    private JSONArray ja;
    private String data;
    private CheckBox cb;
    //private byte[] blob;
    private Date c = new Date();
    private int año = 0;
    private Spinner lista, lista2, lista3;
    private String[] datos = {"Día", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
    private String[] datos2 = {"Mes", "ene", "feb", "mar", "abr", "may", "jun", "jul", "ago", "sep", "oct", "nov", "dic"};
    private String[] datos3 = {"Año", "2000", "1999", "1998", "1997", "1996", "1995", "1994", "1993", "1992", "1991", "1990", "1989", "1988", "1987", "1986", "1985", "1984", "1983", "1982", "1981", "1980", "1979", "1978", "1977", "1976", "1975", "1974", "1973", "1972", "1971", "1970", "1969", "1968", "1967", "1966", "1965", "1964", "1963", "1962", "1961", "1960", "1959", "1958", "1957", "1956", "1955", "1954", "1953", "1952", "1951", "1950", "1949", "1948", "1947", "1946", "1945", "1944", "1943", "1942", "1941", "1940", "1939", "1938", "1937", "1936", "1935", "1934", "1933", "1932", "1931", "1930"};

    Handler h = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int cantidad = 0;
            cantidad = ja.length();
            if (cantidad == 0)
                try {
                    verificaciones();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            else
                siguientePantalla();
        }
    };

    Handler h1 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Toast.makeText(getApplicationContext(), "Resgistró correctamente la informaciÃ³n", 3000).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pasajero);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        contetx = this;
        mailp = (EditText) findViewById(R.id.editText);
        nombrep = (EditText) findViewById(R.id.editText2);
        telefonop = (EditText) findViewById(R.id.editText4);
        direccionp = (EditText) findViewById(R.id.editText3);
        cb = (CheckBox) findViewById(R.id.checkBox);
        dnip = (EditText) findViewById(R.id.editText15);
        facebookp = (EditText) findViewById(R.id.editText16);

        String mail = getIntent().hasExtra("email") ? getIntent().getExtras().getString("email") : "";
        mailp.setText(mail);
        String name = getIntent().hasExtra("name") ? getIntent().getExtras().getString("name") : "";
        nombrep.setText(getIntent().getExtras().getString("name"));

        //spinner1
        lista = (Spinner) findViewById(R.id.spinnerDiap);
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, datos);
        lista.setAdapter(adaptador);
        lista.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }

        });

        //spinner2
        lista2 = (Spinner) findViewById(R.id.spinnerMesp);
        ArrayAdapter<String> adaptador2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, datos2);
        lista2.setAdapter(adaptador2);
        lista2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }

        });

        //spinner3
        lista3 = (Spinner) findViewById(R.id.spinnerAñop);
        ArrayAdapter<String> adaptador3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, datos3);
        lista3.setAdapter(adaptador3);
        lista3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }

        });

        Toast.makeText(getApplicationContext(), "Si ya esta registrado solo ingrese su mail !", 5000).show();

        //im_btn = (ImageButton)findViewById(R.id.im_foto);
        //im_btn.setOnClickListener(new PhotoTaker());
    }
    
    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //muestro la foto    
        if (resultCode != RESULT_CANCELED && requestCode == REQUEST_IMAGE_CAPTURE ){
            // show image
            thumbnail = (Bitmap) data.getExtras().get("data");
            im_btn.setBackgroundColor(Color.rgb(255, 215, 0));
            im_btn.setImageBitmap(thumbnail);
            im_btn.setVisibility(View.VISIBLE);
        }
    }*/

    public void TerminosCondiciones(View view) {
        Intent i = new Intent(Pasajero.this, Terminos.class);
        startActivity(i);
    }

    public void siguiente(View view) {
        /*if (mailp.getText().toString().equals(""))
            Toast.makeText(getApplicationContext(), "Debe ingresar el mail", 3000).show();
        else
            estaRegistrado(mailp.getText().toString());*/

        try {
            verificaciones();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void siguientePantalla() {
        Intent i = new Intent(Pasajero.this, Pasajero_viaje.class);
        i.putExtra("parametro1", mailp.getText().toString());
        startActivity(i);
    }

    public void verificaciones() throws IOException {
        if (nombrep.getText().toString().equals(""))
            Toast.makeText(getApplicationContext(), "Debe ingresar el nombre", 3000).show();
        else if (direccionp.getText().toString().equals(""))
            Toast.makeText(getApplicationContext(), "Debe ingresar la dirección", 3000).show();
        else if (telefonop.getText().toString().equals(""))
            Toast.makeText(getApplicationContext(), "Debe ingresar el teléfono", 3000).show();
        else if (lista.getSelectedItem().equals("Día"))
            Toast.makeText(getApplicationContext(), "Debe ingresar el día de su nacimiento", 3000).show();
        else if (lista2.getSelectedItem().equals("Mes"))
            Toast.makeText(getApplicationContext(), "Debe ingresar el mes de su nacimiento", 3000).show();
        else if (lista3.getSelectedItem().equals("Año"))
            Toast.makeText(getApplicationContext(), "Debe ingresar el año de su nacimiento", 3000).show();
        else {
            año = new Integer(lista3.getSelectedItem().toString()).intValue();
            if (((c.getYear() + 1900) - año) < 18)
                Toast.makeText(getApplicationContext(), "Debe ser mayor de 18 años para poder registrarse", 3000).show();
            else if (dnip.getText().toString().equals(""))
                Toast.makeText(getApplicationContext(), "Debe ingresar su documento de identidad", 3000).show();
            else if (!cb.isChecked())
                Toast.makeText(getApplicationContext(), "Debe aceptar los TÃ©rminos y Condiciones para seguir, antes debe leerlo", 3000).show();
            else {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //httpGetData(Utilities.getUrl(getApplicationContext()) + "/registro_pasajeros.php?mailp=" + mailp.getText() + "&nombrep=" + nombrep.getText() + "&telefonop=" + telefonop.getText() + "&direccionp=" + direccionp.getText() + "&nacimientop=" + lista.getSelectedItem().toString() + "/" + lista2.getSelectedItem().toString() + "/" + lista3.getSelectedItem().toString() + "&dnip=" + dnip.getText() + "&facebookp=" + facebookp.getText());
                        httpGetData(Utilities.getUrl(getApplicationContext()) + "/registro_chofer.php?mailc=" + mailp.getText().toString().replace("\"","") + "&nombrec=" + nombrep.getText() + "&telefonoc=" + telefonop.getText() + "&direccionc=" + direccionp.getText() + "" + "&nacimientoc=" + lista.getSelectedItem().toString() + "/" + lista2.getSelectedItem().toString() + "/" + lista3.getSelectedItem() + "&dnic=" + dnip.getText() + "&facebookc=" + facebookp.getText());
                        h1.sendEmptyMessage(1);
                    }
                }).start();
                Intent i = new Intent(Pasajero.this, Principal.class);
                i.putExtra("email", mailp.getText().toString());
                insertInSharePref();
                startActivity(i);
                finish();
            }
        }
    }

    public void insertInSharePref() {
        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putBoolean("Inserted", true);
        editor.commit();
    }

    public void estaRegistrado(String m) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ja = null;
                data = httpGetData(Utilities.getUrl(getApplicationContext()) + "/consulta_pasajeros.php?mailp=" + mailp.getText());
                if (data.length() > 0) {
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

    public void atras(View view) {
        Intent i = new Intent(Pasajero.this, ADedo.class);
        startActivity(i);
    }
}
