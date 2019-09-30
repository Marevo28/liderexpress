package com.example.lider_express;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class BNDSvodnaya extends AppCompatActivity {
    TextView textexperts;
    Button PickExpert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bndsvodnaya);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bundle arguments = getIntent().getExtras();
        String position = arguments.getString("position");
        textexperts=findViewById(R.id.textexperts);
        PickExpert=findViewById(R.id.PickExpert);
        PickExpert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent IntentSittings = new Intent(BNDSvodnaya.this, Spisok.class);
                startActivity(IntentSittings);
            }
        });
        RadioGroup ispolnenie = (RadioGroup) findViewById(R.id.ispolnenie);
        ispolnenie.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.nadzemnoe:
                        Toast.makeText(getApplicationContext(), "Надземное",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.podzemnoe:
                        Toast.makeText(getApplicationContext(), "Подземное",
                                Toast.LENGTH_SHORT).show();
                    default:
                        break;
                }
            }
        });
    }
}