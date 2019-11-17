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
import android.widget.EditText;
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
    Calendar dateAndTime = Calendar.getInstance();
    EditText TextDefects,TextNote,TextPrichinaIskl;
    TextView textexperts, textdefek, textirldefek, TextDataExp, TextDataSpec,TextNachOstanov,TextKonOstanov,TextDataActNegot,TextDataNK;
    Button PickExpert, PickDefects, PickIrlDefects, ButZapisat, PickDataExp, PickDataSpec,PickDataNegotovnosti;
    String dataexperts,experts,dataspec,spec,ispol, shurf, actshurf, naryadS, position, luk,ostanovka,nachostanovka, konostanovka,datanegotovnosti,datank, osmotrel,irlspec, doki,defects, vedomost, iskluch, note,prichina;
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
        TextNachOstanov = findViewById(R.id.TextNachOstanov);
        TextKonOstanov = findViewById(R.id.TextKonOstanov);
        PickDataNegotovnosti = findViewById(R.id.PickDataNegotovnosti);
        TextDataActNegot= findViewById(R.id.TextDataActNegot);
        TextDataNK= findViewById(R.id.TextDataNK);
        TextDefects= findViewById(R.id.TextDefects);
        TextNote= findViewById(R.id.TextNote);
        TextPrichinaIskl= findViewById(R.id.TextPrichinaIskl);

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
            dataexperts=cursor.getString(27);
            TextDataExp.setText(dataexperts);
        }
        if (cursor.getString(28) != null) {
            experts=cursor.getString(28);
            textexperts.setText(experts);
        }
        if (cursor.getString(29) != null) {
            dataspec=cursor.getString(29);
            TextDataSpec.setText(dataspec);
        }
        if (cursor.getString(30) != null) {
            spec = cursor.getString(30);
            textdefek.setText(spec);
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
        if (cursor.getString(35) != null) {
            naryadS = cursor.getString(35);
            if ("Да".equals(naryadS))
                naryad.check(R.id.naryadDa);
            else
                naryad.check(R.id.naryadNet);
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
        if (cursor.getString(37) != null) {
            nachostanovka = cursor.getString(37);
            TextNachOstanov.setText(nachostanovka);
        }
        if (cursor.getString(38) != null) {
            konostanovka = cursor.getString(38);
            TextKonOstanov.setText(konostanovka);
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
        if (cursor.getString(45) != null) {
            datanegotovnosti=cursor.getString(45);
            TextDataActNegot.setText(datanegotovnosti);
        }
        if (cursor.getString(46) != null) {
            datank=cursor.getString(46);
            TextDataNK.setText(datank);
        }
        if (cursor.getString(47) != null) {
            irlspec=cursor.getString(47);
            textirldefek.setText(irlspec);
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
        if (cursor.getString(49) != null) {
            defects=cursor.getString(49);
            TextDefects.setText(defects);
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
        if (cursor.getString(51) != null) {
            note=cursor.getString(51);
            TextNote.setText(note);
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
        if (cursor.getString(53) != null) {
            prichina=cursor.getString(53);
            TextPrichinaIskl.setText(prichina);
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
                ContentValues initialValues = new ContentValues();
                initialValues.put(POSITION, position);
                initialValues.put("Stolb27", dataexperts);
                initialValues.put("Stolb28", experts);
                initialValues.put("Stolb29", dataspec);
                initialValues.put("Stolb30", spec);
                initialValues.put("Stolb31", ispol);
                initialValues.put("Stolb32", shurf);
                initialValues.put("Stolb33", actshurf);
                initialValues.put("Stolb34", luk);
                initialValues.put("Stolb35", naryadS);
                initialValues.put("Stolb36", ostanovka);
                initialValues.put("Stolb37", nachostanovka);
                initialValues.put("Stolb38", konostanovka);
                initialValues.put("Stolb39", osmotrel );
                //initialValues.put("Stolb40", ); не используется
                //initialValues.put("Stolb41", ); Планирование
                //initialValues.put("Stolb42", ); Планирование
                //initialValues.put("Stolb43", ); Дата выезда на объект
                //initialValues.put("Stolb44", ); ФИО Специалиста выехавшего на объект без контроля
                initialValues.put("Stolb45", datanegotovnosti);
                initialValues.put("Stolb46", datank);
                initialValues.put("Stolb47", irlspec);
                initialValues.put("Stolb48", doki);
                initialValues.put("Stolb49", defects);
                initialValues.put("Stolb50", vedomost);
                initialValues.put("Stolb51", note);
                initialValues.put("Stolb52", iskluch);
                initialValues.put("Stolb53", prichina);
                long result = mDb.insert("DefectBND", null, initialValues);
                //Cursor defectBND = mDBHelper.getReadableDatabase().query("DefectBND", null, null, null, null, null, null);
                //displayMessage(getBaseContext(), String.valueOf(defectBND.getCount()));
                displayMessage(getBaseContext(), "Записан: "+position);
                Intent IntentSittings = new Intent(BNDSvodnaya.this, MainActivity.class);
                startActivity(IntentSittings);
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
                    experts=a;
                    textexperts.setText(a);
                    break;
                case "defects":
                    a = data.getStringExtra("select");
                    spec=a;
                    textdefek.setText(a);
                    break;
                case "irldefects":
                    a = data.getStringExtra("select");
                    irlspec=a;
                    textirldefek.setText(a);
                    break;
            }
        }
    }

    private void displayMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public void setDateExp(View v) {
        new DatePickerDialog(BNDSvodnaya.this, a, dateAndTime.get(Calendar.YEAR), dateAndTime.get(Calendar.MONTH), dateAndTime.get(Calendar.DAY_OF_MONTH)).show();
    }
    DatePickerDialog.OnDateSetListener a = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            TextDataExp.setText(new SimpleDateFormat("dd.MM.yyyy").format(new Date(dateAndTime.getTimeInMillis())));
            dataexperts = TextDataExp.getText().toString();
        }
    };
    public void setDateSpec(View v) {
        new DatePickerDialog(BNDSvodnaya.this, b, dateAndTime.get(Calendar.YEAR), dateAndTime.get(Calendar.MONTH), dateAndTime.get(Calendar.DAY_OF_MONTH)).show();
    }
    DatePickerDialog.OnDateSetListener b = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            TextDataSpec.setText(new SimpleDateFormat("dd.MM.yyyy").format(new Date(dateAndTime.getTimeInMillis())));
            dataspec = TextDataSpec.getText().toString();
        }
    };
    public void setDateNachOstanov(View v) {
        new DatePickerDialog(BNDSvodnaya.this, c, dateAndTime.get(Calendar.YEAR), dateAndTime.get(Calendar.MONTH), dateAndTime.get(Calendar.DAY_OF_MONTH)).show();
    }
    DatePickerDialog.OnDateSetListener c = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            TextNachOstanov.setText(new SimpleDateFormat("dd.MM.yyyy").format(new Date(dateAndTime.getTimeInMillis())));
            nachostanovka = TextNachOstanov.getText().toString();
        }
    };
    public void setDateKonOstanov(View v) {
        new DatePickerDialog(BNDSvodnaya.this, d, dateAndTime.get(Calendar.YEAR), dateAndTime.get(Calendar.MONTH), dateAndTime.get(Calendar.DAY_OF_MONTH)).show();
    }
    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            TextKonOstanov.setText(new SimpleDateFormat("dd.MM.yyyy").format(new Date(dateAndTime.getTimeInMillis())));
            konostanovka = TextKonOstanov.getText().toString();
        }
    };

    public void setDataNegotovnosti(View v) {
        new DatePickerDialog(BNDSvodnaya.this, e, dateAndTime.get(Calendar.YEAR), dateAndTime.get(Calendar.MONTH), dateAndTime.get(Calendar.DAY_OF_MONTH)).show();
    }
    DatePickerDialog.OnDateSetListener e = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            TextDataActNegot.setText(new SimpleDateFormat("dd.MM.yyyy").format(new Date(dateAndTime.getTimeInMillis())));
            datanegotovnosti = TextDataActNegot.getText().toString();
        }
    };
    public void setTextDataNK(View v) {
        new DatePickerDialog(BNDSvodnaya.this, f, dateAndTime.get(Calendar.YEAR), dateAndTime.get(Calendar.MONTH), dateAndTime.get(Calendar.DAY_OF_MONTH)).show();
    }
    DatePickerDialog.OnDateSetListener f = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            TextDataNK.setText(new SimpleDateFormat("dd.MM.yyyy").format(new Date(dateAndTime.getTimeInMillis())));
            datank = TextDataNK.getText().toString();
        }
    };
}