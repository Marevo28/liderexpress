package com.example.lider_express;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class VmyatinaSocuda extends AppCompatActivity {
    public DatabaseHelper mDBHelper;
    public SQLiteDatabase mDb;
    Button butItog;
    String metal,sigma,uslovie,V;
    int sigmaR,davl,diam,ispol,shir,dlin,glub, min;
    TextView textUslovie,textZnachenie;
    EditText textDavlenie,textDiametr,textIspolnitelnoe,textShirina,textDlina,textGlubina;
    double psipsi,psi,cospsi,rnar,n,pi,sigmaMAX,drob1,drob2,drob3,drob4,drob5,drob6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vmyatina_socuda);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        butItog=findViewById(R.id.butItog);
        textUslovie=findViewById(R.id.textUslovie);
        textZnachenie=findViewById(R.id.textZnachenie);
        textDavlenie=findViewById(R.id.textDavlenie);
        textDiametr=findViewById(R.id.textDiametr);
        textIspolnitelnoe=findViewById(R.id.textIspolnitelnoe);
        textShirina=findViewById(R.id.textShirina);
        textDlina=findViewById(R.id.textDlina);
        textGlubina=findViewById(R.id.textGlubina);

        mDBHelper = new DatabaseHelper(this);
        try {
            mDBHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }
        try {
            mDb = mDBHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }
        final AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView3);
        String[] cats = getResources().getStringArray(R.array.Металлы);
        List<String> catList = Arrays.asList(cats);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, catList);
        autoCompleteTextView.setAdapter(adapter);
        butItog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                metal = autoCompleteTextView.getText().toString();
                davl = Integer.parseInt(textDavlenie.getText().toString());
                diam = Integer.parseInt(textDiametr.getText().toString());
                ispol = Integer.parseInt(textIspolnitelnoe.getText().toString());
                shir = Integer.parseInt(textShirina.getText().toString());
                dlin = Integer.parseInt(textDlina.getText().toString());
                glub = Integer.parseInt(textGlubina.getText().toString());
                if (shir < dlin) {
                    min = shir;
                } else if (shir > dlin) {
                    min = dlin;
                }
                rnar=(double)(diam*diam)/2;
                cospsi=(rnar-(min*min))/rnar;
                psipsi=Math.acos(cospsi);
                psi=psipsi/2;
                pi=Math.PI;
                n=pi/(2*psi);
                Cursor cursormetal = mDb.query("Metal", null, "metal = ?", new String[]{metal}, null, null, null);
                cursormetal.moveToFirst();
                sigma = cursormetal.getString(cursormetal.getColumnIndex("20"));
                sigmaR= Integer.parseInt(sigma);
                sigmaR=3*sigmaR;
                drob1=(double)(6*glub)/(ispol-1);
                drob2=(double)(diam/(ispol-1));
                drob3=(double)davl/200000;
                drob4=1.365/(n*n-1);
                drob5=(1+drob4*drob3*drob2*drob2*drob2);
                drob6=((davl*diam)/(2*0.9*(ispol-1)));
                sigmaMAX=drob6*(1+drob1/drob5);
                sigmaMAX=Math.round(sigmaMAX * 100.0) / 100.0;
                if (sigmaMAX < sigmaR) {
                    uslovie="выполняется";
                    V="<";
                } else if (sigmaMAX >= sigmaR) {
                    uslovie="не выполняется";
                    V=">";
                }
                textUslovie.setText("Условие " + uslovie);
                textZnachenie.setText(sigmaMAX+V+sigmaR);
                cursormetal.close();
            }
        });
    }
}
