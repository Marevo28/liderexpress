package com.example.lider_express;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class BNDSvodnaya extends AppCompatActivity {
    TextView textexperts;
    Button PickExpert;
    String experts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bndsvodnaya);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bundle arguments = getIntent().getExtras();
        textexperts=findViewById(R.id.textexperts);
        //if(experts=null)
        //experts = arguments.getString("experts");
        PickExpert=findViewById(R.id.PickExpert);
        PickExpert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent IntentSittings = new Intent(BNDSvodnaya.this, Spisok.class);
                startActivity(IntentSittings);
            }
        });
    }
}