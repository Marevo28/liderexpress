package com.example.lider_express.Svodnaya;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.lider_express.DataBase.DatabaseHelper;
import com.example.lider_express.MainActivity;
import com.example.lider_express.R;

import com.example.lider_express.Tools.SpisokBND;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class KartaKontrolyaYDE extends AppCompatActivity {

    public static final String POSITION = "Position";
    public DatabaseHelper mDBHelper;
    public SQLiteDatabase mDb;

    private Calendar dateAndTime = Calendar.getInstance();

    private String position;
    private String Zakazchik;
   // private String ZakazchikDefect;

    // XML Привязки
    //                 НАИМЕНОВАНИЕ               СТОЛБЕЦ         ОПИСАНИЕ


    private TextView TextView_DataNK;              //             TextView Дата НК
    private Button Button_DataNK;                 //              Button Дата НК
    private TextView TextView_SpecProvNK;    //              TextView Специалисты, выезжающие на обьект
    private Button Button_SpecProvNK;             //              Button  Специалисты, проводившие НК

    private EditText EditText_Kyst;
    private EditText EditText_Skvajina;
    private EditText EditText_Mestorojdeniye;
    private EditText EditText_Zav;
    private EditText EditText_Inv;

    private RadioGroup RadioGroup_Razmecheniye;

    private EditText EditText_NameBlockOborud;

    private RadioGroup RadioGroup_NalichInfTable;
    private RadioGroup RadioGroup_NalichZazeml;
    private RadioGroup RadioGroup_Antikor;
    private RadioGroup RadioGroup_ImManometr;
    private RadioGroup RadioGroup_ManometrPlomba;
    private RadioGroup RadioGroup_ManometrIspraven;
    private RadioGroup RadioGroup_ManometrShkala;
    private RadioGroup RadioGroup_ManomtrProverka;

    private Button Button_ProvedenieYZT;

    private RadioGroup RadioGroup_DefectVedom;               // -          RadioGroup Дфектная ведомость

    private Button Button_Vnesti;


    // --- String ---
    //                 НАИМЕНОВАНИЕ                    СТОЛБЕЦ         ОПИСАНИЕ

    private String strDataNK;                      // - 27        Дата НК
    private String strNKSpec;                      //- 28           Спец, проводившие НК
    private String strKyst;                        // - 29          Куст
    private String strSkvajina;                    // - 30         Скважина
    private String strMestorojdenie;               //- 31          Месторождение
    private String strZav;                         //- 32         Зав. №
    private String strInv;                         // - 33         Инф. №
    private String strRazmesheniye;                //- 34          Размещение
    private String strNameBlockOborud;             // - 35         имя блока оборудования
    private String strNalichieInfTable;            //- 36          Наличие инф табл
    private String strNalZazemleniya;              // - 37         Наличие заземления
    private String strAntikor;                     //- 38          Антикор
    private String strImeetsyaManometr;            // - 39         Имеется манометр?
    private String strManometrPlomba;              // - 40         пломба на манометре
    private String strManometrIspraven;            // - 41         манометр исправен?
    private String strManometrShkala;              // - 42         шкала манометра Рраб
    private String strManometrProverka;            // - 43        менометр проверка
    private String strYZT_Points[];                // - 44 - 64        УЗТ
    private String strVedomostDef;                 // - 65         Дефектная ведомость


    // Переменная в метод onActivityResult
    private String lampa;


    static final private int PEOPLE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_karta_kontrolya_ude);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView_DataNK = this.findViewById(R.id.TextView_DataNK);
        Button_DataNK = this.findViewById(R.id.Button_DataNK);
        TextView_SpecProvNK = this.findViewById(R.id.TextView_SpecProvNK);
        Button_SpecProvNK = this.findViewById(R.id.Button_SpecProvNK);

        EditText_Kyst = this.findViewById(R.id.EditText_Kyst);
        EditText_Skvajina = this.findViewById(R.id.EditText_Skvajina);
        EditText_Mestorojdeniye = this.findViewById(R.id.EditText_Mestorojdenie);
        EditText_Zav = this.findViewById(R.id.EditText_Zav);
        EditText_Inv = this.findViewById(R.id.EditText_Inv);

        RadioGroup_Razmecheniye = this.findViewById(R.id.RadioGroup_Razmeshenie);

        EditText_NameBlockOborud = this.findViewById(R.id.EditText_Naimenovaniye);

        RadioGroup_NalichInfTable = this.findViewById(R.id.RadioGroup_Nal_Inf_Tab);
        RadioGroup_NalichZazeml = this.findViewById(R.id.RadioGroup_Nal_Zazemleniya);
        RadioGroup_Antikor = this.findViewById(R.id.RadioGroup_Anticor);
        RadioGroup_ImManometr = this.findViewById(R.id.RadioGroup_Im_Manometr);
        RadioGroup_ManometrPlomba = this.findViewById(R.id.RadioGroup_Manometr_Plomba);
        RadioGroup_ManometrIspraven = this.findViewById(R.id.RadioGroup_Manometr_Ispraven);
        RadioGroup_ManometrShkala = this.findViewById(R.id.RadioGroup_Manometr_Cherta);
        RadioGroup_ManomtrProverka = this.findViewById(R.id.RadioGroup_Manoment_Proverka);

        Button_ProvedenieYZT = this.findViewById(R.id.Button_YZT);

        RadioGroup_DefectVedom = this.findViewById(R.id.RadioGroup_Vedomost);               // -          RadioGroup Дфектная ведомость

        Button_Vnesti = this.findViewById(R.id.Button_Vnest);

        //инициализируем массив для точек
        strYZT_Points = new String[20];

        // инициализируем каждый элемент массива, чтобы не было null
        for(int i = 0; i < strYZT_Points.length; i++){
            if(strYZT_Points[i] == null){
                strYZT_Points[i] = "";
            }
        }

        mDBHelper = MainActivity.getDBHelper();  // подклчюение к БД

        try {
            mDb = mDBHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }

        Bundle arguments = getIntent().getExtras(); // Bundle
        position = arguments.getString("position"); // Позиция оборудования в БД

        Zakazchik = arguments.getString("Zakazchik");// Заказчик


        /** =============================== СЧИТЫВАНИЕМ ИЗ БАЗЫ И УСТАНВЛИВАЕМ В VIEW ========================================== **/

        Cursor cursor = mDb.query(Zakazchik, null, "POSITION = ?", new String[]{position}, null, null, null);
        cursor.moveToFirst();

        /**
        // Берем из таблицы значения и кладем в массив с точками
        int number_column_for_point = 44;
        for (int i = 0; i < strYZT_Points.length; i++) {
            // Точки
            if (cursor.getString(number_column_for_point) != null) {
                strYZT_Points[i] = cursor.getString(number_column_for_point);
                number_column_for_point++;
            }
        }

             // Исполнение
             if (cursor.getString(28) != null) {
             strIspolnenie = cursor.getString(28);
             Ispolnenie.check("Надземное".equals(strIspolnenie) ? R.id.nadzemnoe : R.id.podzemnoe);
             }
             // Шурфовка фактически проводилась
             if (cursor.getString(29) != null) {
             strShurf = cursor.getString(29);
             Shurf.check("Да".equals(strShurf) ? R.id.Shurf_DA : R.id.Shurf_NET);
             }
             // Акт шурфовки оформлен
             if (cursor.getString(30) != null) {
             strActShurf = cursor.getString(30);
             ActShurf.check("Да".equals(strActShurf) ? R.id.ActShurf_DA : R.id.ActShurf_NET);
             }
             // Размер люк-лаза позволяет провести контроль изнутри
             if (cursor.getString(31) != null) {
             strLukLaz = cursor.getString(31);
             LukLaz.check("Да".equals(strLukLaz) ? R.id.LukLaz_DA : R.id.LukLaz_NET);
             }
             // Наряд на газоопасные работы для проведения контроля изнутри получен
             if (cursor.getString(32) != null) {
             strNaryad = cursor.getString(32);
             Naryad.check("Да".equals(strNaryad) ? R.id.Naryad_DA : R.id.Naryad_NET);
             }
             // На момент проведения НК оборудование было в останове
             if (cursor.getString(33) != null) {
             strNK = cursor.getString(33);
             NK.check("Да".equals(strNK) ? R.id.NK_DA : R.id.NK_NET);
             }
             // Дата останова: начало
             if (cursor.getString(34) != null) {
             strDataNachaloOstanova = cursor.getString(34);
             TextView_DataNachaloOstanova.setText(strDataNachaloOstanova);
             }
             // Дата останова: окончание
             if (cursor.getString(35) != null) {
             strDataOkonOstanova = cursor.getString(35);
             TextView_DataOkonOstanova.setText(strDataOkonOstanova);
             }
             // Осмотр внутренней поверхности проводился
             if (cursor.getString(36) != null) {
             strOsmotrVnutrPov = cursor.getString(36);
             OsmotrVnutrPov.check("Да".equals(strOsmotrVnutrPov) ? R.id.OsmotrVnutrPov_DA : R.id.OsmotrVnutrPov_NET);
             }
             // Дата выезда Без НК
             if (cursor.getString(37) != null) {
             strDataViezdBezNK = cursor.getString(37);
             TextView_DataViezdaBezNKO.setText(strDataViezdBezNK);
             }
             // Специалисты, выезжающие на обьект
             if (cursor.getString(38) != null) {
             strSpecViezdObject = cursor.getString(38);
             TextView_SpecViezdObject.setText(strSpecViezdObject);
             }
             // Акт некотовности
             if (cursor.getString(39) != null) {
             strDataActNegotov = cursor.getString(40);
             TextView_DataActNegotov.setText(strDataActNegotov);
             }
             // Акт непредоставления
             if (cursor.getString(40) != null) {
             strDataActNepredostav = cursor.getString(40);
             TextView_DataActNepredostav.setText(strDataActNepredostav);
             }
             // Дата НК
             if (cursor.getString(41) != null) {
             strDataNK = cursor.getString(41);
             TextView_DataNK.setText(strDataNK);
             }
             // ФИО специалиста (-ов), проводящего (-их) НК
             if (cursor.getString(42) != null) {
             strNKSpec = cursor.getString(42);
             TextView_SpecProvNK.setText(strNKSpec);
             }
             // Дефекты (указать выявленные дефекты)
             if (cursor.getString(43) != null) {
             strDefects = cursor.getString(43);
             EditText_Defects.setText(strDefects);
             }
             // Дефектная ведомость оформлена
             if (cursor.getString(44) != null) {
             strDefectVedom = cursor.getString(36);
             DefectVedom.check("Да".equals(strDefectVedom) ? R.id.Defect_DA : R.id.Defect_NET);
             }
             // Дата повторного выезда
             if (cursor.getString(45) != null) {
             strDataPovtorViezd = cursor.getString(45);
             TextView_DataPovtorViezd.setText(strDataPovtorViezd);
             }

             // Результат повторного выезда
             if (cursor.getString(46) != null) {
             switch (cursor.getString(46)){
             case "Дефекты устранены": ResultatPovtorViezd.check(R.id.DefUstranen);
             CB_DefDrugoe.setEnabled(false);
             break;
             case "Дефекты не устранены": ResultatPovtorViezd.check(R.id.DefNeUstranen);
             CB_DefDrugoe.setEnabled(false);
             break;
             default: ResultatPovtorViezd.setEnabled(false);
             CB_DefDrugoe.setChecked(true);
             strResultatPovtorViezd = cursor.getString(46);
             EditText_DefDrugoe.setText(strResultatPovtorViezd);
             break;
             }
             }

             // Исключение позиции из договора
             if (cursor.getString(47) != null) {
             strIskluchenie = cursor.getString(47);
             Iskluchenie.check("Да".equals(strIskluchenie) ? R.id.Iskluchenie_DA : R.id.Iskluchenie_NET);
             }
             // Дата исключения
             if (cursor.getString(48) != null) {
             strDataIskluchenie = cursor.getString(48);
             TextView_DataIsklucheniya.setText(strDataIskluchenie);
             }
             // CheckBox Причина исключения
             if (cursor.getString(49) != null) {
             strPrichinaDomIsk = cursor.getString(49);
             EditText_PrichinaDrugoe.setText(strPrichinaDomIsk);
             }


             // Замечания дефектоскопистов
             if (cursor.getString(52) != null) {
             strZamechaniya = cursor.getString(50);
             EditText_Zamechaniya.setText(strZamechaniya);
             }


             // Записи в паспорте
             if (cursor.getString(53) != null) {
             strZapisiVPasporte = cursor.getString(51);
             ZapisiVPasporte.check("Да".equals(strZapisiVPasporte) ? R.id.ZapisiVPasporte_DA : R.id.ZapisiVPasporte_NET);
             } **/


            /** =============================== СЧИТЫВАНИЕМ ИЗ VIEW И УСТАНВЛИВАЕМ В STRING ========================================== **/

            /**  Размещение -  **/
            RadioGroup_Razmecheniye.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case R.id.Razmeshenie_Ul:
                            strRazmesheniye = "Надземное";
                            break;
                        case R.id.Razmeshenie_Block:
                            strRazmesheniye = "Подземное";
                            break;
                    }
                }
            });

            /**  Наличие инф таблички -  **/
            RadioGroup_NalichInfTable.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case R.id.Nal_Inf_Tab_Da:
                            strNalichieInfTable = "Надземное";
                            break;
                        case R.id.Nal_Inf_Tab_Net:
                            strNalichieInfTable = "Подземное";
                            break;
                    }
                }
            });

            /** Наличие заземления -  **/
            RadioGroup_NalichZazeml.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case R.id.Nal_Zazemleniya_Da:
                            strNalZazemleniya = "Надземное";
                            break;
                        case R.id.Nal_Zazemleniya_Net:
                            strNalZazemleniya = "Подземное";
                            break;
                    }
                }
            });

            /** Антикорозийное покрытие -  **/
            RadioGroup_Antikor.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case R.id.Anticor_Otsutstvie:
                            strAntikor = "Да";
                            break;
                        case R.id.Anticor_Udovl:
                            strAntikor = "Нет";
                            break;
                        case R.id.Anticor_NeUdovl:
                            strAntikor = "Нет";
                            break;
                        case R.id.Anticor_Povr:
                            strAntikor = "Нет";
                            break;
                    }
                }
            });


            /** Имеется манометр -  **/
            RadioGroup_ImManometr.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case R.id.Im_Manometr_Da:
                            strImeetsyaManometr = "Да";
                            break;
                        case R.id.Im_Manometr_Net:
                            strImeetsyaManometr = "Нет";
                            break;
                    }
                }
            });

            /**  На манометре есть пломба? -  **/
            RadioGroup_ManometrPlomba.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case R.id.Manometr_Plomba_Da:
                            strManometrPlomba = "Да";
                            break;
                        case R.id.Manometr_Plomba_Net:
                            strManometrPlomba = "Нет";
                            break;
                    }
                }
            });
            /** Манометр исправен  - **/
            RadioGroup_ManometrIspraven.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case R.id.Manometr_Ispraven_Da:
                            strManometrIspraven = "Да";
                            break;
                        case R.id.Manometr_Ispraven_Net:
                            strManometrIspraven = "Нет";
                            break;
                    }
                }
            });
            /** На шкале манометра есть красная шкала Pраб? -  **/
            RadioGroup_ManometrShkala.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case R.id.Manometr_Cherta_Da:
                            strManometrShkala = "Да";
                            break;
                        case R.id.Manometr_Cherta_Net:
                            strManometrShkala = "Нет";
                            break;
                    }
                }
            });
            /** Проверка манометра действующая? -  **/
            RadioGroup_ManomtrProverka.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case R.id.Manoment_Proverka_Da:
                            strManometrProverka = "Да";
                            break;
                        case R.id.Manoment_Proverka_Net:
                            strManometrProverka = "Нет";
                            break;
                    }
                }
            });
            Button_ProvedenieYZT.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(KartaKontrolyaYDE.this, ProvedeniyeYZT.class);
                    intent.putExtra("points", strYZT_Points);
                    startActivityForResult(intent, 0);
                }
            });

            /** Дефектная ведомость оформлена? -  **/
            RadioGroup_DefectVedom.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case R.id.Vedomost_Da:
                            strVedomostDef = "Да";
                            break;
                        case R.id.Vedomost_Net:
                            strVedomostDef = "Нет";
                            break;
                    }
                }
            });

            // Button Дата НК
            Button_DataNK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new DatePickerDialog(KartaKontrolyaYDE.this, Picker_DataNK,
                            dateAndTime.get(Calendar.YEAR),
                            dateAndTime.get(Calendar.MONTH),
                            dateAndTime.get(Calendar.DAY_OF_MONTH))
                            .show();

                }
            });

            // Button ФИО Спец, проводивших НК
            Button_SpecProvNK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent IntentSittings = new Intent(KartaKontrolyaYDE.this, SpisokBND.class);
                    IntentSittings.putExtra("people", "experts");
                    startActivityForResult(IntentSittings, PEOPLE);
                }
            });


            /**
             // Button Дата исключения
             Button_DataIsklucheniya.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
            new DatePickerDialog(MegionSvodnaya.this, Picker_DataIsklucheniya,
            dateAndTime.get(Calendar.YEAR),
            dateAndTime.get(Calendar.MONTH),
            dateAndTime.get(Calendar.DAY_OF_MONTH))
            .show();
            }
            });
             **/


            // Button Внести
            Button_Vnesti.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // EditText Куста
                    strKyst = EditText_Kyst.getText().toString();

                    // EditText Скважина
                    strSkvajina = EditText_Skvajina.getText().toString();

                    // EditText Месторождение
                    strMestorojdenie = EditText_Mestorojdeniye.getText().toString();

                    // EditText Зав №
                    strZav = EditText_Zav.getText().toString();

                    // EditText Инв №
                    strInv = EditText_Inv.getText().toString();

                    // EditText Наименование блочного оборудования
                    strNameBlockOborud = EditText_NameBlockOborud.getText().toString();

                    ContentValues initialValues = new ContentValues();
                    initialValues.put(POSITION, position);
                    initialValues.put("Stolb27", strDataNK);
                    initialValues.put("Stolb28", strNKSpec);
                    initialValues.put("Stolb29", strKyst);
                    initialValues.put("Stolb30", strSkvajina);
                    initialValues.put("Stolb31", strMestorojdenie);
                    initialValues.put("Stolb32", strZav);
                    initialValues.put("Stolb33", strInv);
                    initialValues.put("Stolb34", strRazmesheniye);
                    initialValues.put("Stolb35", strNameBlockOborud);
                    initialValues.put("Stolb36", strNalichieInfTable);
                    initialValues.put("Stolb37", strNalZazemleniya);
                    initialValues.put("Stolb38", strAntikor);
                    initialValues.put("Stolb39", strImeetsyaManometr);
                    initialValues.put("Stolb40", strManometrPlomba);
                    initialValues.put("Stolb41", strManometrIspraven);
                    initialValues.put("Stolb42", strManometrShkala);
                    initialValues.put("Stolb43", strManometrProverka);

                    // берем из массива точки и кладем в initialValues
                    int number_column_for_point = 44;
                    String strNumber_column_for_point = "";
                    for (int i = 0; i < strYZT_Points.length; i++) {
                        strNumber_column_for_point += Integer.toString(number_column_for_point);
                        // Точки
                        initialValues.put(strNumber_column_for_point, strYZT_Points[i]);
                        number_column_for_point++;
                    }

                    initialValues.put("Stolb65", strVedomostDef);

                    mDb.insert("Bashneft2020_UDe", null, initialValues);
                    displayMessage(getBaseContext(), "Записан: " + position);
                    Intent IntentSittings = new Intent(KartaKontrolyaYDE.this, MainActivity.class);
                    startActivity(IntentSittings);
                }
            });
    }

    /**
     public void OnCheckedPrichina(View view){
     if(CB_PrichinaDrugoe.isChecked()){
     EditText_PrichinaDrugoe.setEnabled(true);
     PrichinaDemIskl.setEnabled(false);
     }
     else{
     EditText_PrichinaDrugoe.setEnabled(false);
     PrichinaDemIskl.setEnabled(true);
     }
     }
     **/


    // Дата НК
    DatePickerDialog.OnDateSetListener Picker_DataNK = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            TextView_DataNK.setText(new SimpleDateFormat("dd.MM.yyyy").format(new Date(dateAndTime.getTimeInMillis())));
            strDataNK = TextView_DataNK.getText().toString();
        }
    };

    /**
     // Дата Исключения
     DatePickerDialog.OnDateSetListener Picker_DataIsklucheniya = new DatePickerDialog.OnDateSetListener() {
     public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
     dateAndTime.set(Calendar.YEAR, year);
     dateAndTime.set(Calendar.MONTH, monthOfYear);
     dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
     TextView_DataIsklucheniya.setText(new SimpleDateFormat("dd.MM.yyyy").format(new Date(dateAndTime.getTimeInMillis())));
     strDataIskluchenie = TextView_DataIsklucheniya.getText().toString();
     }
     };
     **/


    private void displayMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            lampa = data.getStringExtra("people");
            String exp;
            String[] array;
            switch (lampa) {
                case "experts":
                    exp = data.getStringExtra("select");
                    strNKSpec = exp;
                    TextView_SpecProvNK.setText(exp);
                    break;
                case "points":
                    array = data.getStringArrayExtra("points");
                    strYZT_Points = array;
                    break;
            }
        }
    }

}
