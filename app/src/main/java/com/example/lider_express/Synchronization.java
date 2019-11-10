package com.example.lider_express;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Synchronization extends AppCompatActivity {
    Button btnpostion;
    EditText textuprav, textceh, textobekt, texttypetu, textskvazhina, textpostion, NameTu;
    String position,login,uprav,ceh,obekt;
    private JsonZapros zapros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_synchronization);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        btnpostion = (Button) findViewById(R.id.btnposition);
        textuprav = (EditText) findViewById(R.id.textuprav);
        textceh = (EditText) findViewById(R.id.textceh);
        textobekt = (EditText) findViewById(R.id.textobekt);
        texttypetu = (EditText) findViewById(R.id.texttypetu);
        textskvazhina = (EditText) findViewById(R.id.textskvazhina);
        textpostion = (EditText) findViewById(R.id.textpositon);

    }
    public void onSync(View view){
        position=textpostion.getText().toString();
        zapros = new JsonZapros();
        zapros.start(position);

        try{
            zapros.join();
        }catch (InterruptedException ie){
            Log.e("pass 0",ie.getMessage());
        }
        uprav = zapros.resname();
        ceh = zapros.ressurname();
        obekt = zapros.resmiddlename();

        textuprav.setText(uprav);
        textceh.setText(ceh);
        textobekt.setText(obekt);
    }

    public void onDown(View view) {
        //position=textpostion.getText().toString();
        login=texttypetu.getText().toString();
        uprav=textuprav.getText().toString();
        ceh=textceh.getText().toString();
        obekt=textobekt.getText().toString();

        zapros = new JsonZapros();
        zapros.download(login,uprav,ceh,obekt);

        try{
            zapros.join();
        }catch (InterruptedException ie){
            Log.e("pass 0",ie.getMessage());
        }
        uprav = zapros.resname();
        ceh = zapros.ressurname();
        obekt = zapros.resmiddlename();
        textskvazhina.setText("Otpravil");
    }
}
