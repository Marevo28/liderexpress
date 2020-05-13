package com.example.lider_express.Svodnaya;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.lider_express.R;

public class KartaKontrolyaSPPK extends AppCompatActivity {
    String SbrosSppk;
    String[] SpinerSppk = {"на факел", "в атмосферу", "в дренажку", "прочее"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_karta_kontrolya_s_p_p_k);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        /*Spiner*/
        final Spinner spinnerSPPK = findViewById(R.id.SpinnerSppk);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, SpinerSppk);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSPPK.setAdapter(adapter);
        spinnerSPPK.setPrompt("Title");
        spinnerSPPK.setSelection(0);
        spinnerSPPK.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                SbrosSppk = spinnerSPPK.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        /*Spiner*/
    }

}
