package com.example.lider_express;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.lider_express.DataBase.DatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Megion2020 extends AppCompatActivity {

    public static final String POSITION = "Position";
    public DatabaseHelper mDBHelper;
    public SQLiteDatabase mDb;

    private Calendar dateAndTime = Calendar.getInstance();

    private String position;


    private RadioGroup Ispolnenie;
    private RadioGroup Shurf;
    private RadioGroup ActShurf;
    private RadioGroup LukLaz;
    private RadioGroup Naryad;
    private RadioGroup NK;
    private TextView TextView_DataNachaloOstanova;
    private Button Button_DataNachaloOstanova;
    private TextView TextView_DataOkonOstanova;
    private Button Button_DataOkonOstanova;
    private RadioGroup OsmotrVnutrPov;
    private TextView TextView_DataViezdaBezNKO;
    private Button Button_DataViezdaBezNKO;
    private TextView TextView_SpecViezdObject;
    private Button Button_SpecViezdObject;
    private TextView TextView_DataActNegotov;
    private Button Button_DataActNegotov;
    private TextView TextView_DataActNepredostav;
    private Button Button_DataActNepredostav;
    private TextView TextView_DataNK;
    private Button Button_DataNK;
    private TextView TextView_SpecProvNK;
    private Button Button_SpecProvNK;
    private EditText EditText_Defects;
    private RadioGroup DefectVedom;
    private TextView TextView_DataPovtorViezd;
    private Button Button_DataPovtorViezd;
    private CheckBox CB_DefUstranen;
    private CheckBox CB_DefNeYstranen;
    private CheckBox CB_DefDrugoe;
    private EditText EditText_DefDrugoe;
    private RadioGroup Iskluchenie;
    private TextView TextView_DataIsklucheniya;
    private Button Button_DataIsklucheniya;
    private CheckBox CB_PrichinaDemon;
    private CheckBox CB_PrichinaIskluchil;
    private CheckBox CB_PrichinaDrugoe;
    private EditText EditText_PrichinaDrugoe;
    private EditText EditText_KtoIdkluchil;
    private RadioGroup ZapisiVPasporte;
    private Button Button_Vnesti;


    // --- String ---
    private String strIspolnenie;
    private String strShurf;
    private String strActShurf;
    private String strLukLaz;
    private String strNaryad;
    private String strNK;
    private String strDataNachaloOstanova;
    private String strDataOkonOstanova;
    private String strOsmotrVnutrPov;
    private String strDataViezdBezNKO;
    private String strSpecViezdObject;
    private String strDataActNegotov;
    private String strDataActNepredostav;
    private String strDataNK;
    private String strNKSpec;
    private String strDefects;
    private String strDefectVedom;
    private String strDataPovtorViezd;
    private String strCBDefect;
    private String strIskluchenie;
    private String strDataIskluchenie;
    private String strCBPrichina;
    private String strKtoIskluchil;
    private String strZapisiVPasporte;
    private String lampa;


    static final private int PEOPLE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_megion2020);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Ispolnenie = findViewById(R.id.RadioGroup_Ispolnenie);
        Shurf = findViewById(R.id.RadioGroup_Shurf);
        ActShurf = findViewById(R.id.RadioGroup_ActShurf);
        LukLaz = findViewById(R.id.RadioGroup_LukLaz);
        Naryad = findViewById(R.id.RadioGroup_Naryad);
        NK = findViewById(R.id.RadioGroup_NK);
        TextView_DataNachaloOstanova = findViewById(R.id.TextView_DataNachalo);
        Button_DataNachaloOstanova = findViewById(R.id.Button_DataNachalo);
        TextView_DataOkonOstanova = findViewById(R.id.TextView_DataOkon);
        Button_DataOkonOstanova = findViewById(R.id.Button_DataOkon);
        OsmotrVnutrPov = findViewById(R.id.RadioGroup_OsmotrVnutrPov);
        TextView_DataViezdaBezNKO = findViewById(R.id.TextView_DataViezdaBezNKO);
        Button_DataViezdaBezNKO = findViewById(R.id.Button_DataViezdaBezNKO);
        TextView_SpecViezdObject = findViewById(R.id.TextView_SpecViezdObject);
        Button_SpecViezdObject = findViewById(R.id.Button_SpecViezdObject);
        TextView_DataActNegotov = findViewById(R.id.TextView_DataActNegotov);
        Button_DataActNegotov = findViewById(R.id.Button_DataActNegotov);
        TextView_DataActNepredostav = findViewById(R.id.TextView_ActNepredostav);
        Button_DataActNepredostav = findViewById(R.id.Button_ActNepredostav);
        TextView_DataNK = findViewById(R.id.TextView_DataNK);
        Button_DataNK = findViewById(R.id.Button_DataNK);
        TextView_SpecProvNK = findViewById(R.id.TextView_SpecProvNK);
        Button_SpecProvNK = findViewById(R.id.Button_SpecProvNK);
        EditText_Defects = findViewById(R.id.EditText_Defects);
        DefectVedom = findViewById(R.id.RadioGroup_DefectVedom);
        TextView_DataPovtorViezd = findViewById(R.id.TextView_DataPovtorViezd);
        Button_DataPovtorViezd = findViewById(R.id.Button_DataPovtorViezd);
        CB_DefUstranen = findViewById(R.id.CheckBox_DefUstranen);
        CB_DefNeYstranen = findViewById(R.id.CheckBox_DefNeUstranen);;
        CB_DefDrugoe = findViewById(R.id.CheckBox_DefDrugoe);;
        EditText_DefDrugoe = findViewById(R.id.EditText_DefDrugoe);
        Iskluchenie = findViewById(R.id.RadioGroup_Iskluchenie);
        TextView_DataIsklucheniya = findViewById(R.id.TextView_DataIsklucheniya);
        Button_DataIsklucheniya = findViewById(R.id.Button_DataIsklucheniya);
        CB_PrichinaDemon = findViewById(R.id.CheckBox_PrichinaDemon);
        CB_PrichinaIskluchil = findViewById(R.id.CheckBox_PrichinaIskluchil);
        CB_PrichinaDrugoe = findViewById(R.id.CheckBox_PrichinaDrugoe);
        EditText_PrichinaDrugoe = findViewById(R.id.EditText_PrichinaDrugoe);
        EditText_KtoIdkluchil  = findViewById(R.id.EditText_KtoIskluchil);
        ZapisiVPasporte = findViewById(R.id.RadioGroup_ZapisiVPasporte);
        Button_Vnesti = findViewById(R.id.Button_Vnesti);

        mDBHelper = MainActivity.getDBHelper();// new DatabaseHelper(this);// подклчюение к БД

        try {
            mDb = mDBHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }

        Bundle arguments = getIntent().getExtras(); // Bundle
        position = arguments.getString("position"); // Позиция оборудования в БД
        Cursor cursor = mDb.query("Megion2019", null, "POSITION = ?", new String[]{position}, null, null, null);
        cursor.moveToFirst();

        if (cursor.getString(28) != null) {
            strIspolnenie = cursor.getString(28);
            Ispolnenie.check("Надземное".equals(strIspolnenie) ? R.id.nadzemnoe : R.id.podzemnoe);
        }
        if (cursor.getString(29) != null) {
            strShurf = cursor.getString(29);
            Shurf.check("Да".equals(strShurf) ? R.id.Shurf_DA : R.id.Shurf_NET);
        }
        if (cursor.getString(30) != null) {
            strActShurf = cursor.getString(30);
            ActShurf.check("Да".equals(strActShurf) ? R.id.ActShurf_DA : R.id.ActShurf_NET);
        }
        if (cursor.getString(31) != null) {
            strLukLaz = cursor.getString(31);
            LukLaz.check("Да".equals(strLukLaz) ? R.id.LukLaz_DA : R.id.LukLaz_NET);
        }
        if (cursor.getString(32) != null) {
            strNaryad = cursor.getString(32);
            Naryad.check("Да".equals(strNaryad) ? R.id.Naryad_DA : R.id.Naryad_NET);
        }
        if (cursor.getString(33) != null) {
            strNK = cursor.getString(33);
            NK.check("Да".equals(strNK) ? R.id.NK_DA : R.id.NK_NET);
        }
        if (cursor.getString(34) != null) {
            strDataNachaloOstanova = cursor.getString(34);
            TextView_DataNachaloOstanova.setText(strDataNachaloOstanova);
        }
        if (cursor.getString(35) != null) {
            strDataOkonOstanova = cursor.getString(35);
            TextView_DataOkonOstanova.setText(strDataOkonOstanova);
        }
        if (cursor.getString(36) != null) {
            strOsmotrVnutrPov = cursor.getString(36);
            OsmotrVnutrPov.check("Да".equals(strOsmotrVnutrPov) ? R.id.OsmotrVnutrPov_DA : R.id.OsmotrVnutrPov_NET);
        }
        if (cursor.getString(37) != null) {
            strDataNK = cursor.getString(37);
            TextView_DataNK.setText(strDataNK);
        }
        if (cursor.getString(37) != null) {
            strDataViezdBezNKO = cursor.getString(37);
            TextView_DataViezdaBezNKO.setText(strDataViezdBezNKO);
        }
        if (cursor.getString(38) != null) {
            strSpecViezdObject = cursor.getString(38);
            TextView_SpecViezdObject.setText(strSpecViezdObject);
        }
        if (cursor.getString(39) != null) {
            strDataActNegotov = cursor.getString(40);
            TextView_DataActNegotov.setText(strDataActNegotov);
        }
        if (cursor.getString(40) != null) {
            strDataActNepredostav = cursor.getString(40);
            TextView_DataActNepredostav.setText(strDataActNepredostav);
        }
        if (cursor.getString(41) != null) {
            strDataNK = cursor.getString(41);
            TextView_DataNK.setText(strDataNK);
        }
        if (cursor.getString(42) != null) {
            strNKSpec = cursor.getString(42);
            TextView_SpecProvNK.setText(strNKSpec);
        }
        if (cursor.getString(43) != null) {
            strDefects = cursor.getString(43);
            EditText_Defects.setText(strDefects);
        }
        if (cursor.getString(44) != null) {
            strDefectVedom = cursor.getString(36);
            DefectVedom.check("Да".equals(strDefectVedom) ? R.id.Defect_DA : R.id.Defect_NET);
        }
        if (cursor.getString(45) != null) {
            strDataPovtorViezd = cursor.getString(45);
            TextView_DataPovtorViezd.setText(strDataPovtorViezd);
        }
        //CheckBox
        if (cursor.getString(46) != null) {
            strCBDefect = cursor.getString(46);
            EditText_DefDrugoe.setText(strCBDefect);
        }
        if (cursor.getString(47) != null) {
            strIskluchenie = cursor.getString(47);
            Iskluchenie.check("Да".equals(strIskluchenie) ? R.id.Iskluchenie_DA : R.id.Iskluchenie_NET);
        }
        if (cursor.getString(48) != null) {
            strDataIskluchenie = cursor.getString(48);
            TextView_DataIsklucheniya.setText(strDataIskluchenie);
        }
        //CheckBox
        if (cursor.getString(49) != null) {
            strCBPrichina = cursor.getString(49);
            EditText_PrichinaDrugoe.setText(strCBPrichina);
        }
        if (cursor.getString(50) != null) {
            strKtoIskluchil = cursor.getString(50);
            EditText_KtoIdkluchil.setText(strKtoIskluchil);
        }
        if (cursor.getString(51) != null) {
            strZapisiVPasporte = cursor.getString(51);
            ZapisiVPasporte.check("Да".equals(strZapisiVPasporte) ? R.id.ZapisiVPasporte_DA : R.id.ZapisiVPasporte_NET);
        }

        /** Исполенение? -  **/
        Ispolnenie.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.nadzemnoe:
                        strIspolnenie="Надземное";
                        break;
                    case R.id.podzemnoe:
                        strIspolnenie="Подземное";
                        break;
                }
            }
        });

        /** Шурфовка фактически проводилась? -  **/
        Shurf.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.shurfDA:
                        strActShurf="Да";
                        break;
                    case R.id.shurfNet:
                        strActShurf="Нет";
                        break;
                }
            }
        });

        /** Оформлен акт шурфовки? -  **/
        ActShurf.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.ActShurf_DA:
                        strActShurf="Да";
                        break;
                    case R.id.ActShurf_NET:
                        strActShurf="Нет";
                        break;
                }
            }
        });

        /** Размер люк-лаза позволяет провести контроль изнутри? -  **/
        LukLaz.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.LukLaz_DA:
                        strLukLaz="Да";
                        break;
                    case R.id.LukLaz_NET:
                        strLukLaz="Нет";
                        break;
                }
            }
        });
        /** Наряд на газоопасные работы для проведения контроля изнутри получен?
         *  **/
        Naryad.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.Naryad_DA:
                        strNaryad="Да";
                        break;
                    case R.id.Naryad_NET:
                        strNaryad="Нет";
                        break;
                }
            }
        });
        /** На момент проведения НК оборудование было в основе? -  **/
        NK.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.NK_DA:
                        strNK="Да";
                        break;
                    case R.id.NK_NET:
                        strNK="Нет";
                        break;
                }
            }
        });
        /** Осмотр внутренней поверхности проводился? -  **/
        OsmotrVnutrPov.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.OsmotrVnutrPov_DA:
                        strOsmotrVnutrPov="Да";
                        break;
                    case R.id.OsmotrVnutrPov_NET:
                        strOsmotrVnutrPov="Нет";
                        break;
                }
            }
        });
        /** Дефектная ведомость оформлена? -  **/
        DefectVedom.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.Defect_DA:
                        strDefectVedom="Да";
                        break;
                    case R.id.Defect_NET:
                        strDefectVedom="Нет";
                        break;
                }
            }
        });
        /** Исключение позиции из договора? -  **/
        Iskluchenie.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.Iskluchenie_DA:
                        strIskluchenie="Да";
                        break;
                    case R.id.Iskluchenie_NET:
                        strIskluchenie="Нет";
                        break;
                }
            }
        });
        /** Исключение позиции из договора? -  **/
        ZapisiVPasporte.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.ZapisiVPasporte_DA:
                        strZapisiVPasporte="Да";
                        break;
                    case R.id.ZapisiVPasporte_NET:
                        strZapisiVPasporte="Нет";
                        break;
                }
            }
        });



        // Дата начала останова
        Button_DataNachaloOstanova.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(Megion2020.this, Picker_DataNachaloOstanova,
                        dateAndTime.get(Calendar.YEAR),
                        dateAndTime.get(Calendar.MONTH),
                        dateAndTime.get(Calendar.DAY_OF_MONTH))
                        .show();
            }
        });

        // Дата окончания останова
        Button_DataOkonOstanova.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(Megion2020.this, Picker_DataOkonOstanova,
                        dateAndTime.get(Calendar.YEAR),
                        dateAndTime.get(Calendar.MONTH),
                        dateAndTime.get(Calendar.DAY_OF_MONTH))
                        .show();

            }
        });

        // Дата выезда на объект без провеедния НКО
        Button_DataViezdaBezNKO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(Megion2020.this, Picker_DataViezdaBezNKO,
                        dateAndTime.get(Calendar.YEAR),
                        dateAndTime.get(Calendar.MONTH),
                        dateAndTime.get(Calendar.DAY_OF_MONTH))
                        .show();

            }
        });

        // Фамилия специалиста(-ов), выезжающего(-их) на обьект
        Button_SpecViezdObject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent IntentSittings = new Intent(Megion2020.this, SpisokMegion.class);
                IntentSittings.putExtra("people", "expertsObject");
                startActivityForResult(IntentSittings, PEOPLE);
            }
        });

        // Акт неготовности
        Button_DataActNegotov.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(Megion2020.this, Picker_DataActNegotov,
                        dateAndTime.get(Calendar.YEAR),
                        dateAndTime.get(Calendar.MONTH),
                        dateAndTime.get(Calendar.DAY_OF_MONTH))
                        .show();
            }
        });

        // Акт о непредоставлении документов
        Button_DataActNepredostav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(Megion2020.this, Picker_DataActNepredostav,
                        dateAndTime.get(Calendar.YEAR),
                        dateAndTime.get(Calendar.MONTH),
                        dateAndTime.get(Calendar.DAY_OF_MONTH))
                        .show();

            }
        });

        // ФИО Спец, проводивших НК
        Button_SpecProvNK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent IntentSittings = new Intent(Megion2020.this, SpisokMegion.class);
                IntentSittings.putExtra("people", "expertsNK");
                startActivityForResult(IntentSittings, PEOPLE);
            }
        });


        // Дата НК
        Button_DataNK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(Megion2020.this, Picker_DataNK,
                        dateAndTime.get(Calendar.YEAR),
                        dateAndTime.get(Calendar.MONTH),
                        dateAndTime.get(Calendar.DAY_OF_MONTH))
                        .show();

            }
        });

        // Дата повторного выезда
        Button_DataPovtorViezd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(Megion2020.this, Picker_DataPovtorViezd,
                        dateAndTime.get(Calendar.YEAR),
                        dateAndTime.get(Calendar.MONTH),
                        dateAndTime.get(Calendar.DAY_OF_MONTH))
                        .show();

            }
        });

        // Дата исключения
        Button_DataIsklucheniya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(Megion2020.this, Picker_DataIsklucheniya,
                        dateAndTime.get(Calendar.YEAR),
                        dateAndTime.get(Calendar.MONTH),
                        dateAndTime.get(Calendar.DAY_OF_MONTH))
                        .show();
            }
        });



        Button_Vnesti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // EditText
                strDefects = EditText_Defects.getText().toString();
                strCBDefect = EditText_DefDrugoe.getText().toString();
                strCBPrichina = EditText_PrichinaDrugoe.getText().toString();
                strKtoIskluchil = EditText_KtoIdkluchil.getText().toString();

                ContentValues initialValues = new ContentValues();
                initialValues.put(POSITION, position);
                initialValues.put("Stolb28", strIspolnenie);
                initialValues.put("Stolb29", strShurf);
                initialValues.put("Stolb30", strActShurf);
                initialValues.put("Stolb31", strLukLaz);
                initialValues.put("Stolb32", strNaryad);
                initialValues.put("Stolb33", strNK);
                initialValues.put("Stolb34", strDataNachaloOstanova);
                initialValues.put("Stolb35", strDataOkonOstanova);
                initialValues.put("Stolb36", strOsmotrVnutrPov);
                initialValues.put("Stolb37", strDataViezdBezNKO);
                initialValues.put("Stolb38", strSpecViezdObject);
                initialValues.put("Stolb39", strDataActNegotov );
                initialValues.put("Stolb40", strDataActNepredostav );
                initialValues.put("Stolb41", strDataNK);
                initialValues.put("Stolb42", strNKSpec);
                initialValues.put("Stolb43", strDefects);
                initialValues.put("Stolb44", strDefectVedom);
                initialValues.put("Stolb45", strDataPovtorViezd);
                initialValues.put("Stolb46", strCBDefect);
                initialValues.put("Stolb47", strIskluchenie);
                initialValues.put("Stolb48", strDataIskluchenie);
                initialValues.put("Stolb49", strCBPrichina);
                initialValues.put("Stolb50", strKtoIskluchil);
                initialValues.put("Stolb51", strZapisiVPasporte);
                mDb.insert("DefectMegion", null, initialValues);
                displayMessage(getBaseContext(), "Записан: "+ position);
                Intent IntentSittings = new Intent(Megion2020.this, MainActivity.class);
                startActivity(IntentSittings);
            }
        });
    }


    DatePickerDialog.OnDateSetListener Picker_DataNachaloOstanova = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            TextView_DataNachaloOstanova.setText(new SimpleDateFormat("dd.MM.yyyy").format(new Date(dateAndTime.getTimeInMillis())));
            strDataNachaloOstanova = TextView_DataNachaloOstanova.getText().toString();
        }
    };

    DatePickerDialog.OnDateSetListener Picker_DataOkonOstanova = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            TextView_DataOkonOstanova.setText(new SimpleDateFormat("dd.MM.yyyy").format(new Date(dateAndTime.getTimeInMillis())));
            strDataOkonOstanova = TextView_DataOkonOstanova.getText().toString();
        }
    };

    DatePickerDialog.OnDateSetListener Picker_DataViezdaBezNKO = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            TextView_DataViezdaBezNKO.setText(new SimpleDateFormat("dd.MM.yyyy").format(new Date(dateAndTime.getTimeInMillis())));
            strDataViezdBezNKO = TextView_DataViezdaBezNKO.getText().toString();
        }
    };

    DatePickerDialog.OnDateSetListener Picker_DataActNegotov = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            TextView_DataActNegotov.setText(new SimpleDateFormat("dd.MM.yyyy").format(new Date(dateAndTime.getTimeInMillis())));
            strDataActNegotov = TextView_DataActNegotov.getText().toString();
        }
    };

    DatePickerDialog.OnDateSetListener Picker_DataActNepredostav = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            TextView_DataActNepredostav.setText(new SimpleDateFormat("dd.MM.yyyy").format(new Date(dateAndTime.getTimeInMillis())));
            strDataActNepredostav = TextView_DataActNepredostav.getText().toString();
        }
    };
    DatePickerDialog.OnDateSetListener Picker_DataNK = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            TextView_DataNK.setText(new SimpleDateFormat("dd.MM.yyyy").format(new Date(dateAndTime.getTimeInMillis())));
            strDataNK = TextView_DataNK.getText().toString();
        }
    };

    DatePickerDialog.OnDateSetListener Picker_DataPovtorViezd = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            TextView_DataPovtorViezd.setText(new SimpleDateFormat("dd.MM.yyyy").format(new Date(dateAndTime.getTimeInMillis())));
            strDataPovtorViezd = TextView_DataPovtorViezd.getText().toString();
        }
    };

    DatePickerDialog.OnDateSetListener Picker_DataIsklucheniya = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            TextView_DataIsklucheniya.setText(new SimpleDateFormat("dd.MM.yyyy").format(new Date(dateAndTime.getTimeInMillis())));
            strDataIskluchenie = TextView_DataIsklucheniya.getText().toString();
        }
    };


    private void displayMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            lampa = data.getStringExtra("people");
            String exp;
            switch (lampa) {
                case "expertsObject":
                    exp = data.getStringExtra("select");
                    strSpecViezdObject = exp;
                    TextView_SpecViezdObject.setText(exp);
                    break;
                case "expertsNK":
                    exp = data.getStringExtra("select");
                    strNKSpec = exp;
                    TextView_SpecProvNK.setText(exp);
                    break;
            }
        }
    }

}