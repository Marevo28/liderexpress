package com.example.lider_express.Instruments;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.lider_express.R;

public class Menu_tools extends AppCompatActivity {
    private Button Button_tool1;
    private Button Button_tool2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button_tool1 = (Button) findViewById(R.id.Button_tool1);
        Button_tool2 = (Button) findViewById(R.id.Button_tool2);
        setContentView(R.layout.activity_menu_tools);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button_tool1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent IntentPhoto = new Intent(Menu_tools.this, GiSosuda.class);//кнопка вызова Фото документов
                startActivity(IntentPhoto);
            }
        });
        Button_tool2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent IntentPhoto = new Intent(Menu_tools.this, VmyatinaSocuda.class);//кнопка вызова Фото документов
                startActivity(IntentPhoto);
            }
        });
    }

}
