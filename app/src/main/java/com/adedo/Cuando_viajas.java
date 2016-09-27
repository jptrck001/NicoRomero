package com.adedo;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.Spinner;
import android.widget.Toast;

public class Cuando_viajas extends Activity {

	private Spinner lista,lista2,lista3;
	private String[] datos = {"Día", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
	private String[] datos2 = {"Mes", "ene", "feb", "mar", "abr", "may", "jun", "jul", "ago", "sep", "oct", "nov", "dic"};
	private String[] datos3 = {"Año", "2015", "2016"};
	//private String[] datos4 = {"Hora", "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24"};
	private String mailp;
	private CalendarView cv;
	private int dia, mes, año;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuando_viajas);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        
        mailp = getIntent().getExtras().getString("parametro1");
        
        //spinner1
        lista = (Spinner) findViewById(R.id.spinner1);
		ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, datos);
		lista.setAdapter(adaptador);
		lista.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
			
		});
		
		//spinner2
		lista2 = (Spinner) findViewById(R.id.spinner2);
		ArrayAdapter<String> adaptador2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, datos2);
		lista2.setAdapter(adaptador2);
		lista2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
			
		});
		
		//spinner3
		lista3 = (Spinner) findViewById(R.id.spinner3);
		ArrayAdapter<String> adaptador3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, datos3);
		lista3.setAdapter(adaptador3);
		lista3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
			
		});
		
    }

    public void siguiente(View view){
    	if (lista.getSelectedItem().equals("Día"))
    		Toast.makeText(getApplicationContext(), "Debe elegir un día", Toast.LENGTH_SHORT).show();
    	else if (lista2.getSelectedItem().equals("Mes"))
    		Toast.makeText(getApplicationContext(), "Debe elegir un mes", Toast.LENGTH_SHORT).show();
    	else if (lista3.getSelectedItem().equals("Año"))
    		Toast.makeText(getApplicationContext(), "Debe elegir un año", Toast.LENGTH_SHORT).show();
    	else{

	        Intent i = new Intent(Cuando_viajas.this, Lista_viajes.class);
	        i.putExtra("parametro1", lista.getSelectedItem().toString());
	        i.putExtra("parametro2", lista2.getSelectedItem().toString());
	        i.putExtra("parametro3", lista3.getSelectedItem().toString());
	        i.putExtra("parametro4", mailp.toString());
	        startActivity(i);
    	}
    }
    
    public void atras(View view){
    	Intent i = new Intent(Cuando_viajas.this, ADedo.class);
        startActivity(i);
	}
}
