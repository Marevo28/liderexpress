package com.example.lider_express.Svodnaya;

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

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lider_express.DataBase.DatabaseHelper;
import com.example.lider_express.MainActivity;
import com.example.lider_express.R;
import com.example.lider_express.Shared;
import com.example.lider_express.Tools.SpisokBND;

import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BNDSvodnaya extends AppCompatActivity {
    
    public static final String POSITION = "Position";
    public DatabaseHelper mDBHelper;
    public SQLiteDatabase mDb;
    Calendar dateAndTime = Calendar.getInstance();
    EditText TextDefects;
    EditText TextNote;
    EditText TextPrichinaIskl;
    TextView textexperts;
    TextView textdefek;
    TextView textirldefek;
    TextView TextDataExp;
    TextView TextDataSpec;
    TextView TextNachOstanov;
    TextView TextKonOstanov;
    TextView TextDataActNegot;
    TextView TextDataNK;
    TextView textdataBeznk;
    TextView textdefecBeznk;
    TextView TextDataActNoDoc;
    Button PickDefectsBeznk;
    Button PickExpert;
    Button PickDefects;
    Button PickIrlDefects;
    Button ButZapisat;
    Button PickDataExp;
    Button PickDataSpec;
    Button PickDataNegotovnosti;
    String dataexperts;
    String experts;
    String dataspec;
    String spec;
    String ispol;
    String shurf;
    String actshurf;
    String naryadS;
    String position;
    String luk;
    String ostanovka;
    String nachostanovka;
    String konostanovka;
    String datanegotovnosti;
    String datank;
    String osmotrel;
    String irlspec;
    String doki;
    String defects;
    String vedomost;
    String iskluch;
    String note;
    String prichina;
    String lampa;
    String databeznk;
    String defecBeznk;
    String dataActNoDoc;
    RadioGroup ispolnenie;
    RadioGroup shurfovka;
    RadioGroup actshurfovka;
    RadioGroup naryad;
    RadioGroup luk_laz;
    RadioGroup ostanov;
    RadioGroup osmotr;
    RadioGroup documents;
    RadioGroup vedomostdef;
    RadioGroup iskluchenie;
    static final private int PEOPLE = 0;

    private String Zakazchik;
    private String ZakazchikDefect;

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
        TextDataActNegot = findViewById(R.id.TextDataActNegot);
        TextDataNK= findViewById(R.id.TextDataNK);
        TextDefects = findViewById(R.id.TextDefects);
        TextNote = findViewById(R.id.TextNote);
        TextPrichinaIskl = findViewById(R.id.TextPrichinaIskl);
        textdataBeznk = findViewById(R.id.textdataBeznk);
        textdefecBeznk = findViewById(R.id.textdefecBeznk);
        TextDataActNoDoc = findViewById(R.id.TextDataActNoDoc);
        PickDefectsBeznk = findViewById(R.id.PickDefectsBeznk);

        mDBHelper = MainActivity.getDBHelper();  // new DatabaseHelper(this);// подклчюение к БД

        try {
            mDb = mDBHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }

        Bundle arguments = getIntent().getExtras();
        position = arguments.getString("position");

        Zakazchik = arguments.getString("Zakazchik");
        switch (Zakazchik){
        //    case "ZayavkaBND2019": ZakazchikDefect = Shared.nameDefectBND2019; break;
              case "Bashneft2020": ZakazchikDefect = Shared.nameDefectBND2020; break;
        //    case "ZayavkaBND2021": ZakazchikDefect = Shared.nameDefectBND2021; break;
        }
        Log.e("Svod_Zakaz--------", ZakazchikDefect );


        Cursor cursor = mDb.query(Zakazchik, null, "POSITION = ?", new String[]{position}, null, null, null);
        cursor.moveToFirst();

        if (cursor.getString(27) != null) {
            dataexperts = cursor.getString(27);
            TextDataExp.setText(dataexperts);
        }

        if (cursor.getString(28) != null) {
            experts = cursor.getString(28);
            textexperts.setText(experts);
        }
        if (cursor.getString(29) != null) {
            dataspec = cursor.getString(29);
            TextDataSpec.setText(dataspec);
        }
        if (cursor.getString(30) != null) {
            spec = cursor.getString(30);
            textdefek.setText(spec);
        }

        if (cursor.getString(31) != null) {
            ispol = cursor.getString(31);
            switch (ispol) {
                case ("Да"):
                    ispolnenie.check(R.id.nadzemnoe);
                    break;
                case ("Нет"):
                    ispolnenie.check(R.id.podzemnoe);
                    break;
            }
        }

        ispolnenie.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.nadzemnoe:
                        ispol="Надземное";
                                break;
                    case R.id.podzemnoe:
                        ispol="Подземное";
                        break;
                }
            }
        });

        if (cursor.getString(32) != null) {
            shurf = cursor.getString(32);
            switch (shurf){
                case ("Да"):shurfovka.check(R.id.shurfDA);
                    break;
                case ("Нет"):shurfovka.check(R.id.shurfNet);
                    break;
            }
        }
        shurfovka.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.shurfDA:
                        shurf="Да";
                        break;
                    case R.id.shurfNet:
                        shurf="Нет";
                        break;
                }
            }
        });
        if (cursor.getString(33) != null) {
            actshurf = cursor.getString(33);
            switch (actshurf){
                case ("Да"):actshurfovka.check(R.id.actshurfDA);
                    break;
                case ("Нет"):actshurfovka.check(R.id.actshurfNet);
                    break;
            }
        }
        actshurfovka.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.actshurfDA:
                        actshurf="Да";
                        break;
                    case R.id.actshurfNet:
                        actshurf="Нет";
                        break;
                }
            }
        });
        if (cursor.getString(34) != null) {
            luk = cursor.getString(34);
            switch (luk){
                case ("Да"):luk_laz.check(R.id.luklazDA);
                    break;
                case ("Нет"):luk_laz.check(R.id.luklazNet);
                    break;
            }

        }
        luk_laz.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.luklazDA:
                        luk="Да";
                        break;
                    case R.id.luklazNet:
                        luk="Нет";
                        break;
                }
            }
        });
        if (cursor.getString(35) != null) {
            naryadS = cursor.getString(35);
            switch (naryadS){
                case ("Да"):naryad.check(R.id.naryadDa);
                    break;
                case ("Нет"):naryad.check(R.id.naryadNet);
                    break;
            }
        }
        naryad.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.naryadDa:
                        naryadS="Да";
                        break;
                    case R.id.naryadNet:
                        naryadS="Нет";
                        break;
                }
            }
        });
        if (cursor.getString(36) != null) {
            ostanovka = cursor.getString(36);
            switch (ostanovka){
                case ("Да"):ostanov.check(R.id.ostanovDa);
                    break;
                case ("Нет"):ostanov.check(R.id.ostanovNet);
                    break;
            }
        }
        ostanov.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.ostanovDa:
                        ostanovka="Да";
                        break;
                    case R.id.ostanovNet:
                        ostanovka="Нет";
                        break;
                }
            }
        });

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
            switch (osmotrel){
                case ("Да"):osmotr.check(R.id.osmotrDa);
                    break;
                case ("Нет"):osmotr.check(R.id.osmotrNet);
                    break;
            }
        }
        osmotr.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.osmotrDa:
                        osmotrel="Да";
                        break;
                    case R.id.osmotrNet:
                        osmotrel="Нет";
                        break;
                }
            }
        });
        if (cursor.getString(42) != null) {
            databeznk=cursor.getString(42);
            textdataBeznk.setText(databeznk);
        }
        if (cursor.getString(43) != null) {
            defecBeznk=cursor.getString(43);
            textdefecBeznk.setText(defecBeznk);
        }
        if (cursor.getString(44) != null) {
            datanegotovnosti = cursor.getString(44);
            TextDataActNegot.setText(cursor.getString(44));
        }

        if (cursor.getString(45) != null) {
            dataActNoDoc = cursor.getString(45);
            TextDataActNoDoc.setText(dataActNoDoc);
        }
        if (cursor.getString(46) != null) {
            datank = cursor.getString(46);
            TextDataNK.setText(datank);
        }
        if (cursor.getString(47) != null) {
            irlspec = cursor.getString(47);
            textirldefek.setText(irlspec);
        }

        if (cursor.getString(48) != null) {
            doki = cursor.getString(48);
            switch (doki){
                case ("Да"):documents.check(R.id.documentsDa);
                    break;
                case ("Нет"):documents.check(R.id.documentsNet);
                    break;
            }
        }
        documents.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.documentsDa:
                        doki="Да";
                        break;
                    case R.id.documentsNet:
                        doki="Нет";
                        break;
                }
            }
        });
        if (cursor.getString(50) != null) {
            vedomost = cursor.getString(50);
            switch (vedomost){
                case ("Да"):vedomostdef.check(R.id.vedomostdefDa);
                    break;
                case ("Нет"):vedomostdef.check(R.id.vedomostdefNet);
                    break;
            }
        }
        vedomostdef.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.vedomostdefDa:
                        vedomost="Да";
                        break;
                    case R.id.vedomostdefNet:
                        vedomost="Нет";
                        break;
                }
            }
        });


        if (cursor.getString(52) != null) {
            iskluch = cursor.getString(52);
            switch (iskluch){
                case ("Да"):iskluchenie.check(R.id.iskluchenieDa);
                    break;
                case ("Нет"):iskluchenie.check(R.id.iskluchenieNet);
                    break;
            }

        }
        iskluchenie.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.iskluchenieDa:
                        iskluch="Да";
                        break;
                    case R.id.iskluchenieNet:
                        iskluch="Нет";
                        break;
                }
            }
        });

        PickExpert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent IntentSittings = new Intent(BNDSvodnaya.this, SpisokBND.class);
                IntentSittings.putExtra("people", "experts");
                startActivityForResult(IntentSittings, PEOPLE);
            }
        });
        PickDefects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent IntentSittings = new Intent(BNDSvodnaya.this, SpisokBND.class);
                IntentSittings.putExtra("people", "defects");
                startActivityForResult(IntentSittings, PEOPLE);
            }
        });
        PickIrlDefects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent IntentSittings = new Intent(BNDSvodnaya.this, SpisokBND.class);
                IntentSittings.putExtra("people", "irldefects");
                startActivityForResult(IntentSittings, PEOPLE);
            }

        });
        PickDefectsBeznk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent IntentSittings = new Intent(BNDSvodnaya.this, SpisokBND.class);
                IntentSittings.putExtra("people", "defectsbeznk");
                startActivityForResult(IntentSittings, PEOPLE);
            }
        });

        ButZapisat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // EditText
                defects = TextDefects.getText().toString();
                note = TextNote.getText().toString();
                prichina = TextPrichinaIskl.getText().toString();

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
                //initialValues.put("Stolb40", ); Планирование
                //initialValues.put("Stolb41", ); Планирование
                initialValues.put("Stolb42", databeznk); //Планирование
                initialValues.put("Stolb43", defecBeznk); //Дата выезда на объектметод считывания значение
                initialValues.put("Stolb44", datanegotovnosti);
                initialValues.put("Stolb45", dataActNoDoc);
                initialValues.put("Stolb46", datank);
                initialValues.put("Stolb47", irlspec);
                initialValues.put("Stolb48", doki);
                initialValues.put("Stolb49", defects);
                initialValues.put("Stolb50", vedomost);
                initialValues.put("Stolb51", note);
                initialValues.put("Stolb52", iskluch);
                initialValues.put("Stolb53", prichina);
                mDb.insert(ZakazchikDefect, null, initialValues);
                displayMessage(getBaseContext(), "Записан: "+ position);
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
                case "defectsbeznk":
                    a = data.getStringExtra("select");
                    defecBeznk=a;
                    textdefecBeznk.setText(a);
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
    public void setTextDataBezNK(View v) {
        new DatePickerDialog(BNDSvodnaya.this, g, dateAndTime.get(Calendar.YEAR), dateAndTime.get(Calendar.MONTH), dateAndTime.get(Calendar.DAY_OF_MONTH)).show();
    }
    DatePickerDialog.OnDateSetListener g = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            textdataBeznk.setText(new SimpleDateFormat("dd.MM.yyyy").format(new Date(dateAndTime.getTimeInMillis())));
            databeznk = textdataBeznk.getText().toString();
        }
    };
    public void setDataActNoDoc(View v) {
        new DatePickerDialog(BNDSvodnaya.this, h, dateAndTime.get(Calendar.YEAR), dateAndTime.get(Calendar.MONTH), dateAndTime.get(Calendar.DAY_OF_MONTH)).show();
    }
    DatePickerDialog.OnDateSetListener h = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            TextDataActNoDoc.setText(new SimpleDateFormat("dd.MM.yyyy").format(new Date(dateAndTime.getTimeInMillis())));
            dataActNoDoc = TextDataActNoDoc.getText().toString();
        }
    };

    public static String getTextFromEdit(EditText editText){
        String str = editText.getText().toString();
        return str;
    }

}