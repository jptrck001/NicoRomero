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

public class Chofer extends Activity {

    public final static String MY_PREFS_NAME = "AdedoPref";

    private ImageButton im_btn;
    private EditText mailc, nombrec, telefonoc, direccionc, dni, facebook, autoc;
    private Context contetx;
    private JSONArray ja;
    private String data;
    private CheckBox cb;
    private Date c = new Date();
    private int año = 0;
    private Spinner lista, lista2, lista3;
    private String[] datos = {"Día", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18",
            "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
    private String[] datos2 = {"Mes", "ene", "feb", "mar", "abr", "may", "jun", "jul", "ago", "sep", "oct", "nov", "dic"};
    private String[] datos3 = {"Año", "2000", "1999", "1998", "1997", "1996", "1995", "1994", "1993", "1992", "1991", "1990", "1989", "1988",
            "1987", "1986", "1985", "1984", "1983", "1982", "1981", "1980", "1979", "1978", "1977", "1976", "1975", "1974", "1973", "1972", "1971",
            "1970", "1969", "1968", "1967", "1966", "1965", "1964", "1963", "1962", "1961", "1960", "1959", "1958", "1957", "1956", "1955", "1954",
            "1953", "1952", "1951", "1950", "1949", "1948", "1947", "1946", "1945", "1944", "1943", "1942", "1941", "1940", "1939", "1938", "1937",
            "1936", "1935", "1934", "1933", "1932", "1931", "1930"};

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
            new Thread(new Runnable() {
                @Override
                public void run() {
                    httpGetData(Utilities.getUrl(getApplicationContext()) + "/registro_calificaciones.php?mailc=" + mailc.getText() +
                            "&promedioca=0&comentariosca=" + "comentarios");
                }
            }).start();
            Toast.makeText(getApplicationContext(), "Resgistró correctamente la información", 3000).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chofer);


        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        contetx = this;
        mailc = (EditText) findViewById(R.id.editText);
        nombrec = (EditText) findViewById(R.id.editText2);
        telefonoc = (EditText) findViewById(R.id.editText4);
        direccionc = (EditText) findViewById(R.id.editText3);
        autoc = (EditText) findViewById(R.id.editText5);
        cb = (CheckBox) findViewById(R.id.checkBox1);
        dni = (EditText) findViewById(R.id.editText15);
        facebook = (EditText) findViewById(R.id.editText16);

        String mail = getIntent().hasExtra("email") ? getIntent().getExtras().getString("email") : "";
        mailc.setText(mail);
        String name = getIntent().hasExtra("name") ? getIntent().getExtras().getString("name") : "";
        nombrec.setText(getIntent().getExtras().getString("name"));

        //spinner1
        lista = (Spinner) findViewById(R.id.spinnerDia);
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
        lista2 = (Spinner) findViewById(R.id.spinnerMes);
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
        lista3 = (Spinner) findViewById(R.id.spinnerAño);
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

        //im_btn = (ImageButton)findViewById(R.id.ImageButton);
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
        Intent i = new Intent(Chofer.this, Terminos.class);
        startActivity(i);
    }

    public void estaRegistrado(String m) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ja = null;
                data = httpGetData(Utilities.getUrl(getApplicationContext()) + "/consulta_chofer.php?mailc=" + mailc.getText());
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

    public void atras(View view) {
        Intent i = new Intent(Chofer.this, ADedo.class);
        i.putExtra("parametro1", mailc.getText().toString());
        startActivity(i);
    }

    public void siguiente(View view) {
        /*if (mailc.getText().toString().equals(""))
            Toast.makeText(getApplicationContext(), "Debe ingresar el mail", Toast.LENGTH_SHORT).show();
        else
            estaRegistrado(mailc.getText().toString());*/
        try {
            verificaciones();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void siguientePantalla() {
        Intent i = new Intent(Chofer.this, Chofer_viaje.class);
        i.putExtra("parametro1", mailc.getText().toString());
        startActivity(i);
    }

    public void verificaciones() throws IOException {
        if (nombrec.getText().toString().equals(""))
            Toast.makeText(getApplicationContext(), "Debe ingresar el nombre", Toast.LENGTH_SHORT).show();
        else if (direccionc.getText().toString().equals(""))
            Toast.makeText(getApplicationContext(), "Debe ingresar la dirección", Toast.LENGTH_SHORT).show();
        else if (telefonoc.getText().toString().equals(""))
            Toast.makeText(getApplicationContext(), "Debe ingresar el teléfono", Toast.LENGTH_SHORT).show();
        else if (lista.getSelectedItem().equals("Día"))
            Toast.makeText(getApplicationContext(), "Debe ingresar el día de su nacimiento", Toast.LENGTH_SHORT).show();
        else if (lista2.getSelectedItem().equals("Mes"))
            Toast.makeText(getApplicationContext(), "Debe ingresar el mes de su nacimiento", Toast.LENGTH_SHORT).show();
        else if (lista3.getSelectedItem().equals("Año"))
            Toast.makeText(getApplicationContext(), "Debe ingresar el año de su nacimiento", Toast.LENGTH_SHORT).show();
        else {
            año = new Integer(lista3.getSelectedItem().toString()).intValue();
            if (((c.getYear() + 1900) - año) < 18)
                Toast.makeText(getApplicationContext(), "Debe ser mayor de 18 años para poder registrarse", Toast.LENGTH_SHORT).show();
            else if (dni.getText().toString().equals(""))
                Toast.makeText(getApplicationContext(), "Debe ingresar su documento de identidad", Toast.LENGTH_SHORT).show();
            else if (autoc.getText().toString().equals(""))
                Toast.makeText(getApplicationContext(), "Debe ingresar el modelo del auto", Toast.LENGTH_SHORT).show();
            else if (!cb.isChecked())
                Toast.makeText(getApplicationContext(), "Debe aceptar los Términos y Condiciones para seguir, antes debe leerlo", Toast.LENGTH_SHORT)
                     .show();
            else {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        httpGetData(Utilities.getUrl(getApplicationContext()) + "/registro_chofer.php?mailc=" + mailc.getText()
                                                                                                                     .toString()
                                                                                                                     .replace("\"", "") +
                                "&nombrec=" + nombrec
                                .getText() + "&telefonoc=" + telefonoc.getText() + "&direccionc=" + direccionc.getText() + "&autoc=" + autoc
                                .getText() + "&nacimientoc=" + lista
                                .getSelectedItem()
                                .toString() + "/" + lista2.getSelectedItem()
                                                          .toString() + "/" + lista3.getSelectedItem() + "&dnic=" + dni.getText() + "&facebookc=" +
                                facebook
                                .getText());
                        h1.sendEmptyMessage(1);
                    }
                }).start();
                Intent i = new Intent(Chofer.this, Principal.class);
                i.putExtra("email", mailc.getText().toString());
                //                insertInSharePref();
                startActivity(i);
                finish();
            }
        }
    }

    public void insertInSharePref() {
        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putBoolean(mailc.getText().toString() + "-Inserted", true);
        editor.commit();
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

        insertInSharePref();

        return response;
    }
    
    /*class PhotoTaker implements Button.OnClickListener
    {
        @Override
        public void onClick(View v) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }*/
}
