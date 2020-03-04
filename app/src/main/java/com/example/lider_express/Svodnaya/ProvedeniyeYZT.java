package com.example.lider_express.Svodnaya;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lider_express.R;

import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;

public class ProvedeniyeYZT extends AppCompatActivity {

    private Button btnSave;

    private EditText editText_YZT_1;
    private EditText editText_YZT_2;
    private EditText editText_YZT_3;
    private EditText editText_YZT_4;
    private EditText editText_YZT_5;
    private EditText editText_YZT_6;
    private EditText editText_YZT_7;
    private EditText editText_YZT_8;
    private EditText editText_YZT_9;
    private EditText editText_YZT_10;
    private EditText editText_YZT_11;
    private EditText editText_YZT_12;
    private EditText editText_YZT_13;
    private EditText editText_YZT_14;
    private EditText editText_YZT_15;
    private EditText editText_YZT_16;
    private EditText editText_YZT_17;
    private EditText editText_YZT_18;
    private EditText editText_YZT_19;
    private EditText editText_YZT_20;
    private EditText editText_YZT_Dlina;
    private EditText editText_YZT_Shirina;
    private EditText editText_YZT_Visota;

    private String[] Points = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
            "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"};
    private String strDlina = "";
    private String strShirina = "";
    private String strVisota = "";


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setRequestedOrientation(SCREEN_ORIENTATION_LANDSCAPE);

        setContentView(R.layout.activity_provedenie_yzt);

        btnSave = findViewById(R.id.YZT_Button_Save);

        editText_YZT_1 = findViewById(R.id.EditText_YZT_1);
        editText_YZT_2 = findViewById(R.id.EditText_YZT_2);
        editText_YZT_3 = findViewById(R.id.EditText_YZT_3);
        editText_YZT_4 = findViewById(R.id.EditText_YZT_4);
        editText_YZT_5 = findViewById(R.id.EditText_YZT_5);
        editText_YZT_6 = findViewById(R.id.EditText_YZT_6);
        editText_YZT_7 = findViewById(R.id.EditText_YZT_7);
        editText_YZT_8 = findViewById(R.id.EditText_YZT_8);
        editText_YZT_9 = findViewById(R.id.EditText_YZT_9);
        editText_YZT_10 = findViewById(R.id.EditText_YZT_10);
        editText_YZT_11 = findViewById(R.id.EditText_YZT_11);
        editText_YZT_12 = findViewById(R.id.EditText_YZT_12);
        editText_YZT_13 = findViewById(R.id.EditText_YZT_13);
        editText_YZT_14 = findViewById(R.id.EditText_YZT_14);
        editText_YZT_15 = findViewById(R.id.EditText_YZT_15);
        editText_YZT_16 = findViewById(R.id.EditText_YZT_16);
        editText_YZT_17 = findViewById(R.id.EditText_YZT_17);
        editText_YZT_18 = findViewById(R.id.EditText_YZT_18);
        editText_YZT_19 = findViewById(R.id.EditText_YZT_19);
        editText_YZT_20 = findViewById(R.id.EditText_YZT_20);
        editText_YZT_Dlina = findViewById(R.id.EditText_YZT_Dlina);
        editText_YZT_Shirina = findViewById(R.id.EditText_YZT_Shirina);
        editText_YZT_Visota = findViewById(R.id.EditText_YZT_Visota);

        Points = getIntent().getStringArrayExtra("points");
        strDlina = getIntent().getStringExtra("Dlina");
        strShirina = getIntent().getStringExtra("Shirina");
        strVisota = getIntent().getStringExtra("Visota");

        editText_YZT_1.setText(Points[0]);
        editText_YZT_2.setText(Points[1]);
        editText_YZT_3.setText(Points[2]);
        editText_YZT_4.setText(Points[3]);
        editText_YZT_5.setText(Points[4]);
        editText_YZT_6.setText(Points[5]);
        editText_YZT_7.setText(Points[6]);
        editText_YZT_8.setText(Points[7]);
        editText_YZT_9.setText(Points[8]);
        editText_YZT_10.setText(Points[9]);
        editText_YZT_11.setText(Points[10]);
        editText_YZT_12.setText(Points[11]);
        editText_YZT_13.setText(Points[12]);
        editText_YZT_14.setText(Points[13]);
        editText_YZT_15.setText(Points[14]);
        editText_YZT_16.setText(Points[15]);
        editText_YZT_17.setText(Points[16]);
        editText_YZT_18.setText(Points[17]);
        editText_YZT_19.setText(Points[18]);
        editText_YZT_20.setText(Points[19]);

        editText_YZT_Dlina.setText(strDlina);
        editText_YZT_Shirina.setText(strShirina);
        editText_YZT_Visota.setText(strVisota);

        btnSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Points[0] = editText_YZT_1.getText().toString();
                Points[1] = editText_YZT_2.getText().toString();
                Points[2] = editText_YZT_3.getText().toString();
                Points[3] = editText_YZT_4.getText().toString();
                Points[4] = editText_YZT_5.getText().toString();
                Points[5] = editText_YZT_6.getText().toString();
                Points[6] = editText_YZT_7.getText().toString();
                Points[7] = editText_YZT_8.getText().toString();
                Points[8] = editText_YZT_9.getText().toString();
                Points[9] = editText_YZT_10.getText().toString();
                Points[10] = editText_YZT_11.getText().toString();
                Points[11] = editText_YZT_12.getText().toString();
                Points[12] = editText_YZT_13.getText().toString();
                Points[13] = editText_YZT_14.getText().toString();
                Points[14] = editText_YZT_15.getText().toString();
                Points[15] = editText_YZT_16.getText().toString();
                Points[16] = editText_YZT_17.getText().toString();
                Points[17] = editText_YZT_18.getText().toString();
                Points[18] = editText_YZT_19.getText().toString();
                Points[19] = editText_YZT_20.getText().toString();
                strDlina = editText_YZT_Dlina.getText().toString();
                strShirina = editText_YZT_Shirina.getText().toString();
                strVisota = editText_YZT_Visota.getText().toString();

                Intent IntentSvodnaya = new Intent(ProvedeniyeYZT.this, KartaKontrolyaYDE.class);
                IntentSvodnaya.putExtra("people", "points");
                IntentSvodnaya.putExtra("points", Points);
                IntentSvodnaya.putExtra("Dlina", strDlina);
                IntentSvodnaya.putExtra("Shirina", strShirina);
                IntentSvodnaya.putExtra("Visota", strVisota);
                setResult(RESULT_OK, IntentSvodnaya);
                finish();
            }

        });

    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }
}
