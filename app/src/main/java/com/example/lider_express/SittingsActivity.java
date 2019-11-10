package com.example.lider_express;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class SittingsActivity extends AppCompatActivity {
    String[] Zakazchiki = {"Башнефть 2019", "Мегион 2019", "Полюс 2019"};
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
        spinner.setSelection(2);
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
            String name = mSettings.getString(APP_ZAKAZCHIK,"не определено");
            TextZakazchik.setText(name);
        }
    }
}
