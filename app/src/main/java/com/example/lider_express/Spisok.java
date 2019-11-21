package com.example.lider_express;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class Spisok extends AppCompatActivity {
    TextView selection;
    String select;
    public static String PEOPLE;
    Button butSave;
    String[] spisochek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spisok);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        selection = (TextView) findViewById(R.id.selection);// получаем элемент ListView
        butSave=(Button)findViewById(R.id.butSave);
        final ListView countriesList = (ListView) findViewById(R.id.countriesList);// получаем ресурс

        Bundle arguments = getIntent().getExtras();
        PEOPLE = arguments.getString("people");
        switch (PEOPLE){
            case "experts":
                spisochek = getResources().getStringArray(R.array.bnd_experts);// создаем адаптер
                break;
            case "defects":
                spisochek = getResources().getStringArray(R.array.bnd_defects);// создаем адаптер
                break;
            case "irldefects":
                spisochek = getResources().getStringArray(R.array.bnd_irldefects);// создаем адаптер
                break;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, spisochek);
        countriesList.setAdapter(adapter);
        countriesList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                SparseBooleanArray sp=countriesList.getCheckedItemPositions();
                String selectedItems="";
                for(int i=0;i < spisochek.length;i++)
                {
                    if(sp.get(i))
                        selectedItems+=spisochek[i]+",";
                }
                selection.setText("Выбрано: " + selectedItems);
                select=selectedItems;
            }
        });

       butSave.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent IntentSvodnaya = new Intent(Spisok.this, BNDSvodnaya.class);//данны дял передачи
               IntentSvodnaya.putExtra("select", select);
               IntentSvodnaya.putExtra("people", PEOPLE);
               setResult(RESULT_OK, IntentSvodnaya);
               finish();
           }
       });
    }
}

