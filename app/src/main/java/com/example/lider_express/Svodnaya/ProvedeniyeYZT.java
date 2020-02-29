package com.example.lider_express.Svodnaya;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lider_express.R;

import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;

public class ProvedeniyeYZT extends AppCompatActivity {

    private Spinner spinner;
    private EditText editText;
    private Button btnZap;
    private Button btnSave;

    private ArrayAdapter arrayAdapter;
    private String[] numberPoints = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
            "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"};
    private String[] Points = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
            "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"};


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setRequestedOrientation(SCREEN_ORIENTATION_LANDSCAPE);

        setContentView(R.layout.activity_provedenie_yzt);

        spinner = findViewById(R.id.YZT_Spinner);
        editText = findViewById(R.id.YZT_EditText);
        btnZap = findViewById(R.id.YZT_Button_Zap);
        btnSave = findViewById(R.id.YZT_Button_Save);

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, numberPoints);
        spinner.setAdapter(arrayAdapter);

        Points = getIntent().getStringArrayExtra("points");

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent IntentSvodnaya = new Intent(ProvedeniyeYZT.this, KartaKontrolyaYDE.class);//данны дял передачи
                IntentSvodnaya.putExtra("people", "points");
                IntentSvodnaya.putExtra("points", Points);
                setResult(RESULT_OK, IntentSvodnaya);
                finish();
            }

        });

        btnZap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Points[(int)spinner.getSelectedItemId()] = editText.getText().toString();
            }
        });

    }

    @Override
    public void onBackPressed(){

        super.onBackPressed();
    }
}
