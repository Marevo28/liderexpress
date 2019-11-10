package com.example.lider_express;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.amitshekhar.DebugDB;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BNDSvodnaya extends AppCompatActivity {
    public static final String POSITION = "Position";
    public DatabaseHelper mDBHelper;
    public SQLiteDatabase mDb;
    public DbHandler dbHandler;
    Calendar dateAndTime = Calendar.getInstance();
    TextView textexperts, textdefek, textirldefek, TextDataExp, TextDataSpec;
    Button PickExpert, PickDefects, PickIrlDefects, ButZapisat, PickDataExp, PickDataSpec;
    String ispol, shurf, actshurf, naryadS, position, luk, ostanovka, osmotrel, doki, vedomost, iskluch;
    String lampa;
    RadioGroup ispolnenie, shurfovka, actshurfovka, naryad, luk_laz, ostanov, osmotr, documents, vedomostdef, iskluchenie;
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
        textirldefek = findViewById(R.id.textirldefek);
        PickExpert = findViewById(R.id.PickExpert);
        PickDefects = findViewById(R.id.PickDefects);
        PickIrlDefects = findViewById(R.id.PickIrlDefects);
        ButZapisat = findViewById(R.id.ButZapisat);
        ispolnenie = findViewById(R.id.ispolnenie);
        shurfovka = findViewById(R.id.shurfovka);
        actshurfovka = findViewById(R.id.actshurfovka);
        naryad = findViewById(R.id.naryad);
        luk_laz = findViewById(R.id.luklaz);
        ostanov = findViewById(R.id.ostanov);
        osmotr = findViewById(R.id.osmotr);
        documents = findViewById(R.id.documents);
        vedomostdef = findViewById(R.id.vedomostdef);
        iskluchenie = findViewById(R.id.iskluchenie);
        TextDataExp = findViewById(R.id.TextDataExp);
        PickDataExp = findViewById(R.id.PickDataExp);
        TextDataSpec = findViewById(R.id.TextDataSpec);
        PickDataSpec = findViewById(R.id.PickDataSpec);


        mDBHelper = MainActivity.getDBHelper();// new DatabaseHelper(this);// подклчюение к БД
        try {
            mDb = mDBHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }

        Bundle arguments = getIntent().getExtras();
        position = arguments.getString("position");

        Cursor cursor = mDb.query("ZayavkaBND", null, "POSITION = ?", new String[]{position}, null, null, null);
        cursor.moveToFirst();

        if (cursor.getString(27) != null) {
            TextDataExp.setText(cursor.getString(27));
        }
        if (cursor.getString(28) != null) {
            textexperts.setText(cursor.getString(28));
        }
        if (cursor.getString(29) != null) {
            TextDataSpec.setText(cursor.getString(29));
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
        if (cursor.getString(35) != null) {
            naryadS = cursor.getString(35);
            // naryad.check("Да".equals(naryadS) ? R.id.naryadDa : R.id.naryadNet);
            // загугли тернарный оператор
            if ("Да".equals(naryadS))
                naryad.check(R.id.naryadDa);
            else
                naryad.check(R.id.naryadNet);
        }
        if (cursor.getString(34) != null) {
            luk = cursor.getString(34);
            switch (luk) {
                case "Да":
                    luk_laz.check(R.id.luklazDA);
                    break;
                case "Нет":
                    luk_laz.check(R.id.luklazNet);
                    break;
            }
        }
        if (cursor.getString(36) != null) {
            ostanovka = cursor.getString(36);
            switch (ostanovka) {
                case "Да":
                    ostanov.check(R.id.ostanovDa);
                    break;
                case "Нет":
                    ostanov.check(R.id.ostanovNet);
                    break;
            }
        }
        if (cursor.getString(39) != null) {
            osmotrel = cursor.getString(39);
            switch (osmotrel) {
                case "Да":
                    osmotr.check(R.id.osmotrDa);
                    break;
                case "Нет":
                    osmotr.check(R.id.osmotrNet);
                    break;
            }
        }
        if (cursor.getString(48) != null) {
            doki = cursor.getString(48);
            switch (doki) {
                case "Да":
                    documents.check(R.id.documentsDa);
                    break;
                case "Нет":
                    documents.check(R.id.documentsNet);
                    break;
            }
        }
        if (cursor.getString(50) != null) {
            vedomost = cursor.getString(50);
            switch (vedomost) {
                case "Да":
                    vedomostdef.check(R.id.vedomostdefDa);
                    break;
                case "Нет":
                    vedomostdef.check(R.id.vedomostdefNet);
                    break;
            }
        }
        if (cursor.getString(52) != null) {
            iskluch = cursor.getString(52);
            switch (iskluch) {
                case "Да":
                    iskluchenie.check(R.id.iskluchenieDa);
                    break;
                case "Нет":
                    iskluchenie.check(R.id.iskluchenieNet);
                    break;
            }
        }
        // отображаем диалоговое окно для выбора даты
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
        PickIrlDefects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent IntentSittings = new Intent(BNDSvodnaya.this, Spisok.class);
                IntentSittings.putExtra("people", "irldefects");
                startActivityForResult(IntentSittings, PEOPLE);
            }
        });
        ButZapisat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DebugDB.getAddressLog();
                ContentValues initialValues = new ContentValues();
                initialValues.put(POSITION, position);
                initialValues.put("Ispolnenie", ispol);
                initialValues.put("Shurfovka", shurf);
                initialValues.put("Actshurfovka", actshurf);
                initialValues.put("Naryad", naryadS);
                long result = mDb.insert("DefectBND", null, initialValues);
                Cursor defectBND = mDBHelper.getReadableDatabase().query("DefectBND", null, null, null, null, null, null);
                displayMessage(getBaseContext(), String.valueOf(defectBND.getCount()));
                //}
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
                case "irldefects":
                    a = data.getStringExtra("select");
                    textirldefek.setText(a);
                    break;
            }
        }
    }

    private void displayMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public void setDate(View v) {
        new DatePickerDialog(BNDSvodnaya.this, d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDateTime();
        }
    };

    // установка начальных даты и времени
    private void setInitialDateTime() {
        TextDataExp.setText(new SimpleDateFormat("dd.MM.yyyy").format(new Date(dateAndTime.getTimeInMillis())));
    }
}