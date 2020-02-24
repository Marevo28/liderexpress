package com.example.lider_express;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SittingsActivity extends AppCompatActivity {
    String[] Zakazchiki = {//"Башнефть 2019", "Мегион 2019", "Полюс 2019",
            "Башнефть 2020", "Мегион 2020", // "Полюс 2020",
            //"Башнефть 2021", "Мегион 2021", "Полюс 2021"
    };
    String Zakazchik;
    Button ButSave;
    TextView TextZakazchik;
    public static final String APP_FILES = "mysettings";
    public static final String APP_ZAKAZCHIK = "Zakazchik";
    SharedPreferences mSettings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sittings);
        Toolbar toolbar = findViewById(R.id.toolbar);
        ButSave = findViewById(R.id.ButSave);
        TextZakazchik = findViewById(R.id.TextZakazchik);
        mSettings = getSharedPreferences(APP_FILES, MODE_PRIVATE);
        setSupportActionBar(toolbar);
        // адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Zakazchiki);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(adapter);
        // заголовок
        spinner.setPrompt("Title");
        // выделяем элемент
        spinner.setSelection(0);
        // устанавливаем обработчик нажатия

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // показываем позиция нажатого элемента
                Toast.makeText(getBaseContext(), "Position = " + position, Toast.LENGTH_SHORT).show();
                Zakazchik = spinner.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        ButSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextZakazchik.setText(Zakazchik);
                SharedPreferences.Editor prefEditor = mSettings.edit();
                prefEditor.putString(APP_ZAKAZCHIK, Zakazchik);
                prefEditor.apply();
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        {
            String name = mSettings.getString(APP_ZAKAZCHIK,"Zakazchik");
            TextZakazchik.setText(name);
        }
    }
}
