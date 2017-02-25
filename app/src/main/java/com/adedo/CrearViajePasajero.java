package com.adedo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by Rulo-PC on 25/4/2016.
 */
public class CrearViajePasajero extends Activity {

    private Spinner lista,lista2,lista3,lista4,lista7;

    AutoCompleteTextView lista5,lista6;


    private String[] datos = {"Día", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
    private String[] datos2 = {"Mes", "ene", "feb", "mar", "abr", "may", "jun", "jul", "ago", "sep", "oct", "nov", "dic"};
    private String[] datos3 = {"Año", "2015", "2016"};
    private String[] datos4 = {"Hora", "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24"};
    private String[] datos5 = {"Ciudad de partida", "Rosario", "Santa Fe", "Paraná", "Córdoba", "Buenos Aires", "(-)", "Corrientes", "Jujuy", "La Plata", "Mar del Plata", "Mendoza", "Posadas", "Resistencia", "Salta", "San Juan", "Santiago del Estero", "Tucumán", "(-)" , "Alejandra", "Alto Verde", "Amstrong", "Angélica", "Angeloni", "Arequito", "Arocena", "Arroyo Aguiar", "Arroyo Leyes", "Arroyo Seco", "Arteaga", "Avellaneda", "Barrancas", "Bigand", "Bouquet", "Cabal", "Cacique Ariacaiquin", "Calchaquí", "Campo Andino", "Candiotti", "Cañada de Gómez", "Capitán Bermudez", "Carcaraña", "Casilda", "Castellanos", "Cayastá", "Cayastacito", "Ceres", "Chabas", "Colonia Dolores", "Coronda", "Desvio Arijon", "El Trébol", "Elisa", "Elortondo", "Emilia", "Escalada", "Esperanza", "Esther", "Firmat", "Florencia", "Franck", "Fray Luis Beltrán", "Frontera", "Funes", "Gaboto", "Gálvez", "Gobernador Crespo", "Granadero Baigorria", "Helvecia", "Humboldt", "Irigoyen", "Iriondo", "La Brava", "La Camila", "La Criolla", "La Pelada", "La Penca", "Laguna Paiva", "Las Parejas", "Las Rosas", "Las Toscas", "Lehmann", "Llambi Campbell", "Los Molinos", "Luciano Leiva", "Maciel", "Malabrigo", "Marcelino Escalada", "Margarita", "Matilde", "Melincué", "Moises Ville", "Monje", "Monte Vera", "Montes de Oca", "Naré", "Nelson", "Oliveros", "Pedro Gómez Cello", "Pérez", "Pilar", "Progreso", "Pueblo Andino", "Puerto Gaboto", "Puerto General San Martin", "Rafaela", "Ramayón", "Ramona", "Reconquista", "Recreo", "Ricardone", "Roldán", "Romang", "Rosario", "Rufino", "Saladero Cabal", "Salto Grande", "San Antonio", "San Bernardo", "San Carlos Centro", "San Carlos Norte", "San Carlos Sud", "San Cristóbal", "San Eduardo", "San Francisco", "San Genaro", "San Guillermo", "San Javier", "San Jeronimo Norte", "San Jorge", "San José de la Esquina", "San José del Rincón", "San Justo", "San Lorenzo", "San Martín Norte", "Sanford", "Santa Rosa de Calchines", "Santo Domingo", "Santo Tomé", "Sargento Cabral", "Sarmiento", "Sastre", "Sauce Viejo", "Silva", "Soledad", "Sunchales", "Timbues", "Tortugas", "Tostado", "Totoras", "Venado Tuerto", "Vera", "Vera y Pintado", "Videla", "Villa Cañas", "Villa Constitución", "Villa Eloisa", "Villa Gobernador Galvez", "Villa Guillermina", "Villa Ocampo", "Villa Saralegui", "Villada", "Wheelright"};
    private String[] datos6 = {"Ciudad de llegada", "Rosario", "Santa Fe", "Paraná", "Córdoba", "Buenos Aires", "(-)", "Corrientes", "Jujuy", "La Plata", "Mar del Plata", "Mendoza", "Posadas", "Resistencia", "Salta", "San Juan", "Santiago del Estero", "Tucumán", "(-)" , "Alejandra", "Alto Verde", "Amstrong", "Angélica", "Angeloni", "Arequito", "Arocena", "Arroyo Aguiar", "Arroyo Leyes", "Arroyo Seco", "Arteaga", "Avellaneda", "Barrancas", "Bigand", "Bouquet", "Cabal", "Cacique Ariacaiquin", "Calchaquí", "Campo Andino", "Candiotti", "Cañada de Gómez", "Capitán Bermudez", "Carcaraña", "Casilda", "Castellanos", "Cayastá", "Cayastacito", "Ceres", "Chabas", "Colonia Dolores", "Coronda", "Desvio Arijon", "El Trébol", "Elisa", "Elortondo", "Emilia", "Escalada", "Esperanza", "Esther", "Firmat", "Florencia", "Franck", "Fray Luis Beltrán", "Frontera", "Funes", "Gaboto", "Gálvez", "Gobernador Crespo", "Granadero Baigorria", "Helvecia", "Humboldt", "Irigoyen", "Iriondo", "La Brava", "La Camila", "La Criolla", "La Pelada", "La Penca", "Laguna Paiva", "Las Parejas", "Las Rosas", "Las Toscas", "Lehmann", "Llambi Campbell", "Los Molinos", "Luciano Leiva", "Maciel", "Malabrigo", "Marcelino Escalada", "Margarita", "Matilde", "Melincué", "Moises Ville", "Monje", "Monte Vera", "Montes de Oca", "Naré", "Nelson", "Oliveros", "Pedro Gómez Cello", "Pérez", "Pilar", "Progreso", "Pueblo Andino", "Puerto Gaboto", "Puerto General San Martin", "Rafaela", "Ramayón", "Ramona", "Reconquista", "Recreo", "Ricardone", "Roldán", "Romang", "Rosario", "Rufino", "Saladero Cabal", "Salto Grande", "San Antonio", "San Bernardo", "San Carlos Centro", "San Carlos Norte", "San Carlos Sud", "San Cristóbal", "San Eduardo", "San Francisco", "San Genaro", "San Guillermo", "San Javier", "San Jeronimo Norte", "San Jorge", "San José de la Esquina", "San José del Rincón", "San Justo", "San Lorenzo", "San Martín Norte", "Sanford", "Santa Rosa de Calchines", "Santo Domingo", "Santo Tomé", "Sargento Cabral", "Sarmiento", "Sastre", "Sauce Viejo", "Silva", "Soledad", "Sunchales", "Timbues", "Tortugas", "Tostado", "Totoras", "Venado Tuerto", "Vera", "Vera y Pintado", "Videla", "Villa Cañas", "Villa Constitución", "Villa Eloisa", "Villa Gobernador Galvez", "Villa Guillermina", "Villa Ocampo", "Villa Saralegui", "Villada", "Wheelright"};
    private String[] datos7 = {"Cantidad", "0", "1", "2", "3", "4", "5", "6"};
    private String mailc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear_viaje_pasajero);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mailc = getIntent().getExtras().getString("parametro1");

        //spinner1
        lista = (Spinner) findViewById(R.id.spinner11);
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
        lista2 = (Spinner) findViewById(R.id.spinner12);
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
        lista3 = (Spinner) findViewById(R.id.spinner13);
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

        //spinner4
        lista4 = (Spinner) findViewById(R.id.spinner14);
        ArrayAdapter<String> adaptador4 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, datos4);
        lista4.setAdapter(adaptador4);
        lista4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }

        });

        //spinner5
        lista5 = (AutoCompleteTextView) findViewById(R.id.spinner15);
        lista5.setAdapter(new PlacesAutoCompleteAdapter(this, R.layout.autocomplete_list_item));

        lista5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
               // lista5.setText(truncateStr(lista5.getText().toString()));
            }
        });



        //spinner6
        lista6 = (AutoCompleteTextView) findViewById(R.id.spinner16);
        ArrayAdapter<String> adaptador6 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, datos6);
        lista6.setAdapter(new PlacesAutoCompleteAdapter(this, R.layout.autocomplete_list_item));
        lista6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //lista6.setText(truncateStr(lista6.getText().toString()));
            }
        });



        //spinner7
        lista7 = (Spinner) findViewById(R.id.spinner17);
        ArrayAdapter<String> adaptador7 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, datos7);
        lista7.setAdapter(adaptador7);
        lista7.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }

        });
    }

    private String truncateStr(String input){
        String output = "";
        output = input.split(",")[0];
        return output;
    }

    public void siguiente(View view){
        if (lista.getSelectedItem().equals("Día"))
            Toast.makeText(getApplicationContext(), "Debe elegir un día", Toast.LENGTH_SHORT).show();
        else if (lista2.getSelectedItem().equals("Mes"))
            Toast.makeText(getApplicationContext(), "Debe elegir un mes", Toast.LENGTH_SHORT).show();
        else if (lista3.getSelectedItem().equals("Año"))
            Toast.makeText(getApplicationContext(), "Debe elegir un año", Toast.LENGTH_SHORT).show();
        else if (lista4.getSelectedItem().equals("Hora"))
            Toast.makeText(getApplicationContext(), "Debe elegir una hora", Toast.LENGTH_SHORT).show();
        else if (lista5.getText().toString().isEmpty())
            Toast.makeText(getApplicationContext(), "Debe elegir una ciudad de partida", Toast.LENGTH_SHORT).show();
        else if (lista6.getText().toString().isEmpty())
            Toast.makeText(getApplicationContext(), "Debe elegir una ciudad de llegada", Toast.LENGTH_SHORT).show();
        else{
            Intent i = new Intent(CrearViajePasajero.this, Confirmacion_Viaje.class);
            i.putExtra("parametro1", lista.getSelectedItem().toString());
            i.putExtra("parametro2", lista2.getSelectedItem().toString());
            i.putExtra("parametro3", lista3.getSelectedItem().toString());
            i.putExtra("parametro4", lista4.getSelectedItem().toString());
            i.putExtra("parametro5", truncateStr(lista5.getText().toString()));
            i.putExtra("parametro6", truncateStr(lista6.getText().toString()));
            i.putExtra("parametro7", 100); //100 representa que es un pasajero, es un workaround por las limitaciones del server
            i.putExtra("parametro8", mailc);
            startActivity(i);
            finish();
        }
    }

    public void atras(View view){
        Intent i = new Intent(CrearViajePasajero.this, ADedo.class);
        startActivity(i);
    }
}
