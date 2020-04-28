package com.example.lider_express.Svodnaya;

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
import com.example.lider_express.Shared;
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
    private String defectTable;
    private String nameTableForCursor;
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
    private String strYZT_Points[];                // - 44 - 64     УЗТ Точки
    private String strYZT_Dlina;                   // - 65          УЗТ Длина
    private String strYZT_Shirina;                 // - 66         УЗТ Ширина
    private String strYZT_Visota;                  // - 67         УЗТ Глубина
    private String strVedomostDef;                 // - 68         Дефектная ведомость


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
        Zakazchik = arguments.getString("Zakazchik");

        switch (Zakazchik){
            case "Bashneft2020": defectTable = Shared.nameDefectBashneft2020_UDE;
                nameTableForCursor = Shared.nameBashneft2020_UDE; break;
        }


        /** =============================== СЧИТЫВАНИЕМ ИЗ БАЗЫ И УСТАНВЛИВАЕМ В VIEW ========================================== **/

        Cursor cursor = mDb.query(nameTableForCursor, null, "POSITION = ?", new String[]{position}, null, null, null);
        cursor.moveToFirst();
       /**
        // Дата НК
        if (cursor.getString(27) != null) {
            strDataNK = cursor.getString(28);
            TextView_DataNK.setText(strDataNK);
        }
        // Специалисты
        if (cursor.getString(28) != null) {
            strNKSpec = cursor.getString(28);
            TextView_SpecProvNK.setText(strNKSpec);
        }
        //Размещение
        if (cursor.getString(34) != null) {
            strRazmesheniye = cursor.getString(34);
            RadioGroup_Razmecheniye.check("На улице".equals(strRazmesheniye) ? R.id.Razmeshenie_Ul : R.id.Razmeshenie_Block);
        }
        //Инф табличка
        if (cursor.getString(36) != null) {
            strNalichieInfTable = cursor.getString(36);
            RadioGroup_NalichInfTable.check("Eсть".equals(strNalichieInfTable) ? R.id.Nal_Inf_Tab_Da : R.id.Nal_Inf_Tab_Net);
        }
        //Заземление
        if (cursor.getString(37) != null) {
            strNalZazemleniya = cursor.getString(37);
            RadioGroup_NalichZazeml.check("Eсть".equals(strNalZazemleniya) ? R.id.Nal_Zazemleniya_Da : R.id.Nal_Zazemleniya_Net);
        }
        //Антикорозийное покрытие
        if (cursor.getString(38) != null) {
            strAntikor = cursor.getString(38);
            switch (strAntikor){
                case "Отсутсвует": RadioGroup_Antikor.check(R.id.Anticor_Otsutstvie); break;
                case "Удовлетворительное": RadioGroup_Antikor.check(R.id.Anticor_Udovl); break;
                case "Неудовлетворительное": RadioGroup_Antikor.check(R.id.Anticor_NeUdovl); break;
                case "Повреждено": ; RadioGroup_Antikor.check(R.id.Anticor_Povr); break;
            }
        }
        //Манометр имеется?
        if (cursor.getString(39) != null) {
            strImeetsyaManometr = cursor.getString(39);
             RadioGroup_ImManometr.check("Да".equals(strImeetsyaManometr) ? R.id.Im_Manometr_Da : R.id.Im_Manometr_Net);
        }
        //Манометр пломба
        if (cursor.getString(40) != null) {
            strManometrPlomba = cursor.getString(40);
            RadioGroup_ManometrPlomba.check("Да".equals(strManometrPlomba) ? R.id.Manometr_Plomba_Da : R.id.Manometr_Plomba_Net);
        }
        // Манометр исправен
        if (cursor.getString(41) != null) {
            strManometrIspraven = cursor.getString(41);
            RadioGroup_ManometrIspraven.check("Да".equals(strManometrIspraven) ? R.id.Manometr_Ispraven_Da : R.id.Manometr_Ispraven_Net);
        }
        // Манометр шкала
        if (cursor.getString(42) != null) {
            strManometrShkala = cursor.getString(42);
            RadioGroup_ManometrShkala.check("Да".equals(strManometrShkala) ? R.id.Manometr_Cherta_Da : R.id.Manometr_Cherta_Net);
        }
        //Проверка манометра действующая?
        if (cursor.getString(43) != null) {
            strManometrProverka = cursor.getString(43);
            RadioGroup_ManomtrProverka.check("Да".equals(strManometrProverka) ? R.id.Manoment_Proverka_Da : R.id.Manoment_Proverka_Net);
        }
        // Берем из таблицы значения и кладем в массив с точками
        //  44-67
        int number_column_for_point = 44;
        for (int i = 0; i < strYZT_Points.length; i++) {
            // Точки
            if (cursor.getString(number_column_for_point) != null) {
                strYZT_Points[i] = cursor.getString(number_column_for_point);
                number_column_for_point++;
            }
        }
        //
        if (cursor.getString(68) != null) {
            strVedomostDef = cursor.getString(68);
            RadioGroup_DefectVedom.check("Да".equals(strVedomostDef) ? R.id.Vedomost_Da : R.id.Vedomost_Da);
        }

        **/

            /** =============================== СЧИТЫВАНИЕМ ИЗ VIEW И УСТАНВЛИВАЕМ В STRING ==========================================
             * Listeners **/

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
                            strNalichieInfTable = "Да";
                            break;
                        case R.id.Nal_Inf_Tab_Net:
                            strNalichieInfTable = "Нет";
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
                            strNalZazemleniya = "Да";
                            break;
                        case R.id.Nal_Zazemleniya_Net:
                            strNalZazemleniya = "Нет";
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
                            strAntikor = "Отсутствует";
                            break;
                        case R.id.Anticor_Udovl:
                            strAntikor = "Удовлетворительное";
                            break;
                        case R.id.Anticor_NeUdovl:
                            strAntikor = "Неудовлетворительное";
                            break;
                        case R.id.Anticor_Povr:
                            strAntikor = "Повреждено";
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
                    IntentSittings.putExtra("people", "irldefects");
                    startActivityForResult(IntentSittings, PEOPLE);
                }
            });


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
                        strNumber_column_for_point = "Stolb" + Integer.toString(number_column_for_point);
                        // Точки
                        initialValues.put(strNumber_column_for_point, strYZT_Points[i]);
                        number_column_for_point++;
                        System.out.println(strNumber_column_for_point);
                        System.out.println(strYZT_Points[i]);
                    }
                    initialValues.put("Stolb64", "LyaLyaLya");
                    initialValues.put("Stolb65", strYZT_Dlina);
                    initialValues.put("Stolb66", strYZT_Shirina);
                    initialValues.put("Stolb67", strYZT_Visota);
                    initialValues.put("Stolb68", strVedomostDef);

                    mDb.insert(defectTable, null, initialValues);
                    displayMessage(getBaseContext(), "Записан: " + position);
                    Intent IntentSittings = new Intent(KartaKontrolyaYDE.this, MainActivity.class);
                    startActivity(IntentSittings);
                }
            });
    }


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
                case "irldefects":
                    exp = data.getStringExtra("select");
                    strNKSpec = exp;
                    TextView_SpecProvNK.setText(exp);
                    break;
                case "points":
                    array = data.getStringArrayExtra("points");
                    strYZT_Points = array;
                    strYZT_Dlina = data.getStringExtra("Dlina");
                    strYZT_Shirina = data.getStringExtra("Shirina");
                    strYZT_Visota = data.getStringExtra("Visota");
                    break;
            }
        }
    }

}
