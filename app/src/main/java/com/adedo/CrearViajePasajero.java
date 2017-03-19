package com.adedo;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by Rulo-PC on 25/4/2016.
 */
public class CrearViajePasajero extends Activity {

    private Spinner lista7;
    AutoCompleteTextView lista5, lista6;
    EditText comentarios;

    TextView select_time;
    TextView select_date;
    int mHour, mMinute;

    int mYear;
    int mMonth;
    int mDay;

    private String[] datos7 = {"Cantidad", "0", "1", "2", "3", "4", "5", "6"};
    private String mailc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crear_viaje_pasajero);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mailc = getIntent().getExtras().getString("parametro1");
        comentarios = (EditText) findViewById(R.id.comentarios);

        select_time = (TextView) findViewById(R.id.select_time);
        select_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showtimerPicker();
            }
        });

        select_date = (TextView) findViewById(R.id.select_date);
        select_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
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
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }

        });
    }

    private String truncateStr(String input) {
        String output = "";
        output = input.split(",")[0];
        return output;
    }

    public void siguiente(View view) {
        if (select_date.getText().toString().isEmpty())
            Toast.makeText(getApplicationContext(), "Debe elegir una fecha", Toast.LENGTH_SHORT).show();
        else if (select_time.getText().toString().isEmpty())
            Toast.makeText(getApplicationContext(), "Debe elegir el horario", Toast.LENGTH_SHORT).show();
        else if (lista5.getText().toString().isEmpty())
            Toast.makeText(getApplicationContext(), "Debe elegir una ciudad de partida", Toast.LENGTH_SHORT).show();
        else if (lista6.getText().toString().isEmpty())
            Toast.makeText(getApplicationContext(), "Debe elegir una ciudad de llegada", Toast.LENGTH_SHORT).show();
        else {
            Intent i = new Intent(CrearViajePasajero.this, Confirmacion_Viaje.class);
            i.putExtra("parametro1", String.valueOf(mDay));
            i.putExtra("parametro2", mMonth);
            i.putExtra("parametro3", String.valueOf(mYear));
            i.putExtra("parametro4", String.valueOf(select_time.getText().toString()));
            i.putExtra("parametro5", truncateStr(lista5.getText().toString()));
            i.putExtra("parametro6", truncateStr(lista6.getText().toString()));
            i.putExtra("parametro7", 100); //100 representa que es un pasajero, es un workaround por las limitaciones del server
            i.putExtra("parametro8", mailc);
            i.putExtra("parametro9", comentarios.getText());
            startActivity(i);
            finish();
        }
    }

    public void atras(View view) {
        Intent i = new Intent(CrearViajePasajero.this, ADedo.class);
        startActivity(i);
    }

    private void showtimerPicker() {
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                            int minute) {

                        if (minute < 10) {
                            select_time.setText(hourOfDay + ": 0" + minute);
                        } else {
                            select_time.setText(hourOfDay + ":" + minute);
                        }

                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    private void showDatePicker() {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                            int monthOfYear, int dayOfMonth) {

                        mYear = year;
                        mMonth = monthOfYear + 1;
                        mDay = dayOfMonth;

                        select_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
}
