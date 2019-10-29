package com.example.lider_express;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class BNDSvodnaya extends AppCompatActivity {
    public static final String POSITION = "Position";
    public DatabaseHelper mDBHelper;
    public SQLiteDatabase mDb;
    TextView textexperts, textdefek;
    Button PickExpert, PickDefects, ButZapisat;
    String ispol, shurf, actshurf, naryadS, position, lampa;
    RadioGroup ispolnenie, shurfovka, actshurfovka, naryad;
    static final private int PEOPLE = 0;
    Statement stmt = null;
    int ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bndsvodnaya);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        textexperts = findViewById(R.id.textexperts);
        textdefek = findViewById(R.id.textdefek);
        PickExpert = findViewById(R.id.PickExpert);
        PickDefects = findViewById(R.id.PickDefects);
        ButZapisat = findViewById(R.id.ButZapisat);
        ispolnenie = findViewById(R.id.ispolnenie);
        shurfovka = findViewById(R.id.shurfovka);
        actshurfovka = findViewById(R.id.actshurfovka);
        naryad = findViewById(R.id.naryad);

        mDBHelper = new DatabaseHelper(this);// подклчюение к БД
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

        Bundle arguments = getIntent().getExtras();
        position = arguments.getString("position");

        Cursor cursor = mDb.query("ZayavkaBND", null, "POSITION = ?", new String[]{position}, null, null, null);
        cursor.moveToFirst();

        if (cursor.getString(28) != null) {
            textexperts.setText(cursor.getString(28));
        }

        if (cursor.getString(30) != null) {
            textdefek.setText(cursor.getString(30));
        }

        if (cursor.getString(31) != null) {
            ispol = cursor.getString(31);
            switch (ispol) {
                case "Надземное":

                    ispolnenie.check(R.id.nadzemnoe);
                    break;
                case "Подземное":
                    ispolnenie.check(R.id.podzemnoe);
                    break;
            }
        }

        if (cursor.getString(32) != null) {
            shurf = cursor.getString(32);
            switch (shurf) {
                case "Да":
                    shurfovka.check(R.id.shurfDA);
                    break;
                case "Нет":
                    shurfovka.check(R.id.shurfNet);
                    break;
            }
        }

        if (cursor.getString(33) != null) {
            actshurf = cursor.getString(33);
            switch (actshurf) {
                case "Да":
                    actshurfovka.check(R.id.actshurfDA);
                    break;
                case "Нет":
                    actshurfovka.check(R.id.actshurfNet);
                    break;
            }
        }

        if (cursor.getString(34) != null) {
            naryadS = cursor.getString(34);
            // naryad.check("Да".equals(naryadS) ? R.id.naryadDa : R.id.naryadNet);
            // загугли тернарный оператор
            if ("Да".equals(naryadS))
                naryad.check(R.id.naryadDa);
            else
                naryad.check(R.id.naryadNet);

        }

        PickExpert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent IntentSittings = new Intent(BNDSvodnaya.this, Spisok.class);
                IntentSittings.putExtra("people", "experts");
                startActivityForResult(IntentSittings, PEOPLE);
            }
        });
        PickDefects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent IntentSittings = new Intent(BNDSvodnaya.this, Spisok.class);
                IntentSittings.putExtra("people", "defects");
                startActivityForResult(IntentSittings, PEOPLE);

            }
        });
        ButZapisat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectDB();
                ContentValues initialValues = new ContentValues();
                //initialValues.put("_id", ID);
                initialValues.put(POSITION, position);
                initialValues.put("Ispolnenie", ispol);
                initialValues.put("Shurfovka", shurf);
                initialValues.put("Actshurfovka", actshurf);
                initialValues.put("Naryad", naryadS);
                //Statement statement = mDBHelper.createStatement();
                long result = mDBHelper.getWritableDatabase().insert("DefectBND", null, initialValues);
                //= "INSERT INTO DefectBND (_id,Position,Ispolnenie,Shurfovka,Actshurfovka,Naryad) " +
                // "VALUES ('"+ ID + "', '"+ position + "', '"+ ispol + "', '"+ shurf + "', '"+ actshurf + "', '"+ naryadS + "') ";


                ID++;
                if (result > 0) {
                    System.out.println("insert");
                    Cursor defectBND = mDBHelper.getReadableDatabase().query("DefectBND", null, null, null, null, null, null);
                    displayMessage(getBaseContext(), String.valueOf(defectBND.getCount()));
                    // действия
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            lampa = data.getStringExtra("people");
            String a;
            switch (lampa) {
                case "experts":
                    a = data.getStringExtra("select");
                    textexperts.setText(a);
                    break;
                case "defects":
                    a = data.getStringExtra("select");
                    textdefek.setText(a);
                    break;
            }
        }
    }

    private void displayMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
    public static Connection ConnectDB(){
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:info1.db");
            System.out.println("connected");
        }catch(Exception e) {
            System.out.println(e);
        }
        return null;
    }
}