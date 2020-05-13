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

import com.example.lider_express.DataBase.DatabaseHelper;
import com.example.lider_express.MainActivity;
import com.example.lider_express.R;
import com.example.lider_express.Shared;
import com.example.lider_express.Tools.SpisokMegion;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author LeStat
 */

public class KartaKontolyaNasos extends AppCompatActivity {


    public static final String POSITION = "Position";
    public DatabaseHelper mDBHelper;
    public SQLiteDatabase mDb;

    private Calendar dateAndTime = Calendar.getInstance();

    private String position;
    private String Zakazchik;
    private String ZakazchikDefect;
    private String nameTableForCursor;

    // XML Привязки
    //                 НАИМЕНОВАНИЕ               СТОЛБЕЦ         ОПИСАНИЕ


    private TextView TextView_DataNk;              // - 27         TextView Дата НК
    private Button Button_DataNK;                  //   ^^         Button Дата начала останова
    private TextView TextView_SpecProvNK;          // - 28         ФИО Спец, провод НК
    private Button Button_SpecProvNK;              //   ^^         Button ФИО Спец, провод НК
    private EditText EditText_Kyst;                // - 29         EditText  Куст
    private EditText EditText_Skvajina;            // - 30         EditText  Скважина
    private EditText EditText_Mestorojdenie;       // - 31         EditText  Месторождение
    private EditText EditText_Zav;                 // - 32         EditText  Зав
    private EditText EditText_Inv;                 // - 33         EditText  Инвентарь
    private RadioGroup RadioGroup_Sostoyanie;      // - 34        RadioGroup Работоспособное состояние
    private RadioGroup RadioGroup_MarkZavod;       // - 35         RadioGroup наличие маркировки изготовителя
    private RadioGroup RadioGroup_MarkZavodInf;    // - 36         RadioGroup Информативность заводской маркировки
    private RadioGroup RadioGroup_MarkOrganiz;     // - 37         RadioGroup наличие маркировки предприятия
    private RadioGroup RadioGroup_MarkOrganizInf;  // - 38         RadioGroup информативность маркировки предприятия
    private RadioGroup RadioGroup_Nal_Zazemleniya; // - 39         RadioGroup Наличие заземления
    private RadioGroup RadioGroup_Fundament;       // - 40         RadioGroup Фундамент
    private RadioGroup RadioGroup_Anticor;         // - 41         RadioGroup Антикорозийное покрытие
    private RadioGroup RadioGroup_Flow;            // - 42         RadioGroup Протечки рабочей среды
    private RadioGroup RadioGroup_Klapan;          // - 43         RadioGroup Предохранительный клапан
    private RadioGroup RadioGroup_KlapanSost;      // - 44         RadioGroup Состояние клапана
    private RadioGroup RadioGroup_ArrowFlow;       // - 45         RadioGroup Стрелки направляющей среды
    private EditText EditText_DefEngine;           // - 46         EditText  дефекты электродвигателя
    private EditText EditText_DefKorpus;           // - 47         EditText  дефекты корпуса
    private EditText[] EditText_Vibraci;           // - 48-59         EditText Вибрация Замеры
    private EditText[] EditText_Vsas_zameri;       // - 60-63         EditText Всасывающий размеры
    private EditText[] EditText_Naporniy_zameri;   // - 64-67         EditText Напольный размеры
    private RadioGroup RadioGroup_Vedomost;        // - 68              RadioGroup Ведомость оформлена

    private Button Button_Vnesti;


    // --- String ---
    //                 НАИМЕНОВАНИЕ               СТОЛБЕЦ         ОПИСАНИЕ

    private String DataNk;                        // - 27         Исполнение
    private String SpecProvNK;                    // - 28         Шурфовка проводилась?
    private String Kyst;                          // - 29         Акт шурфовки
    private String Skvajina;                      // - 30         ЛюкЛаз
    private String Mestorojdenie;                 // - 31         Наряд
    private String Zav;                           // - 32         На момент проведения НК оборудование было в останове?
    private String Inv;                           // - 33         Дата начала Останова
    private String Sostoyanie;                    // - 34         Дата окончания остановка
    private String MarkZavod;                     // - 35         Осмотр внутренней поверхности проводился?
    private String MarkZavodInf;                  // - 36         Дата выезда без НК
    private String MarkOrganiz;                   // - 37         Специалисты, выезжающие на обьект
    private String MarkOrganizInf;                // - 38         Акт неготовности
    private String Nal_Zazemleniya;               // - 39         Акт непредоставления
    private String Fundament;                     // - 40         Дата НК
    private String Anticor;                       // - 41         Специалисты, проводившие НК
    private String Flow;                          // - 42         Дефекты
    private String Klapan;                        // - 43         Дефектная ведомость оформлена?
    private String KlapanSost;                    // - 44         Дата повтороного выезда
    private String ArrowFlow;                     // - 45         EditText Результат повтороного выезда - Дефекты - Другое
    private String DefEngine;                     // - 46         EditText Результат повтороного выезда - Дефекты - Другое
    private String DefKorpus;                     // - 47         EditText Результат повтороного выезда - Дефекты - Другое
    private String[] Vibraci = new String[12];            // - 48-59        EditText Результат повтороного выезда - Дефекты - Другое
    private String[] Vsas_zameri = new String[4];        // -  60-63        EditText Результат повтороного выезда - Дефекты - Другое
    private String[] Naporniy_zameri = new String[4];    // -  64-67        EditText Результат повтороного выезда - Дефекты - Другое
    private String Vedomost;                      // - 68         EditText Результат повтороного выезда - Дефекты - Другое


    // Переменная в метод onActivityResult
    private String lampa;


    static final private int PEOPLE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Android имеет общее поле ID всех элементов.
        // Каждый элемент имеет уникальный ID
        // Поэтому, если в разных Layout
        // Назвать какие то View одинаково
        // То компилятор не покажет на ошибку
        // В будущем лучше делать через View.inflate,
        // Чтобы избежать ошибок с одинаковыми именами,
        // И брать элемент от него !

        setContentView(R.layout.activity_karta_kontolya_nasos);

     //   Toolbar toolbar = findViewById(R.id.toolbar);
     //   setSupportActionBar(toolbar);

        TextView_DataNk = findViewById(R.id.TextView_DataNK);
        Button_DataNK = findViewById(R.id.Button_DataNK);
        TextView_SpecProvNK = findViewById(R.id.TextView_SpecProvNK);
        Button_SpecProvNK = findViewById(R.id.Button_SpecProvNK);
        EditText_Kyst = findViewById(R.id.EditText_Kyst);
        EditText_Skvajina = findViewById(R.id.EditText_Skvajina);
        EditText_Mestorojdenie = findViewById(R.id.EditText_Mestorojdenie);
        EditText_Zav = findViewById(R.id.EditText_Zav);
        EditText_Inv = findViewById(R.id.EditText_Inv);
        RadioGroup_Sostoyanie = findViewById(R.id.RadioGroup_Sostoyanie);
        RadioGroup_MarkZavod = findViewById(R.id.RadioGroup_MarkZavod);
        RadioGroup_MarkZavodInf = findViewById(R.id.RadioGroup_MarkZavodInf);
        RadioGroup_MarkOrganiz = findViewById(R.id.RadioGroup_MarkOrganiz);
        RadioGroup_MarkOrganizInf = findViewById(R.id.RadioGroup_MarkOrganizInf);
        RadioGroup_Nal_Zazemleniya = findViewById(R.id.RadioGroup_Nal_Zazemleniya);
        RadioGroup_Fundament = findViewById(R.id.RadioGroup_Fundament);
        RadioGroup_Anticor = findViewById(R.id.RadioGroup_Anticor);
        RadioGroup_Flow = findViewById(R.id.RadioGroup_Flow);
        RadioGroup_Klapan = findViewById(R.id.RadioGroup_Klapan);
        RadioGroup_KlapanSost = findViewById(R.id.RadioGroup_KlapanSost);
        RadioGroup_ArrowFlow = findViewById(R.id.RadioGroup_ArrowFlow);
        EditText_DefEngine = findViewById(R.id.EditText_DefEngine);
        EditText_DefKorpus = findViewById(R.id.EditText_DefKorpus);


        EditText_Vibraci = new EditText[]{
                findViewById(R.id.EditText_Zamer_1_X),  findViewById(R.id.EditText_Zamer_1_Y), findViewById(R.id.EditText_Zamer_1_Z),
                findViewById(R.id.EditText_Zamer_2_X),  findViewById(R.id.EditText_Zamer_2_Y), findViewById(R.id.EditText_Zamer_2_Z),
                findViewById(R.id.EditText_Zamer_3_X),  findViewById(R.id.EditText_Zamer_3_Y), findViewById(R.id.EditText_Zamer_3_Z),
                findViewById(R.id.EditText_Zamer_4_X),  findViewById(R.id.EditText_Zamer_4_Y), findViewById(R.id.EditText_Zamer_4_Z)
        };

        EditText_Vsas_zameri = new EditText[]{
                findViewById(R.id.Vsas_Diametr), findViewById(R.id.Vsas_Razmer_1),
                findViewById(R.id.Vsas_Razmer_2), findViewById(R.id.Vsas_Razmer_3)
        };

        EditText_Naporniy_zameri = new EditText[]{
                findViewById(R.id.Naporniy_Diametr), findViewById(R.id.Naporniy_Razmer_1),
                findViewById(R.id.Naporniy_Razmer_2), findViewById(R.id.Naporniy_Razmer_3)
        };

        RadioGroup_Vedomost = findViewById(R.id.RadioGroup_Vedomost);
        Button_Vnesti = findViewById(R.id.Button_Vnest);

        mDBHelper = MainActivity.getDBHelper();  // подклчюение к БД
        try {
            mDb = mDBHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }

        Bundle arguments = getIntent().getExtras(); // Bundle
        position = arguments.getString("position"); // Позиция оборудования в БД
        Zakazchik= arguments.getString("Zakazchik");// Заказчик
        switch (Zakazchik){
            case "Bashneft2020": ZakazchikDefect = Shared.nameDefectBashneft2020_Nasos;
                nameTableForCursor = Shared.nameBashneft2020_Nasos; break;
        }

        /** =============================== СЧИТЫВАНИЕМ ИЗ БАЗЫ И УСТАНВЛИВАЕМ В VIEW ========================================== **/

        Cursor cursor = mDb.query(Zakazchik, null, "POSITION = ?", new String[]{position}, null, null, null);
        cursor.moveToFirst();

        // Дата НК
        if (cursor.getString(27) != null) {
            DataNk = cursor.getString(28);
            TextView_DataNk.setText(DataNk);
        }
        //  Фил спец, проводивших НК
        if (cursor.getString(28) != null) {
            SpecProvNK = cursor.getString(28);
            TextView_SpecProvNK.setText(SpecProvNK);
        }

        /** Куст
         *  -- > 29
         *  Скважина
         *  -- > 30
         *  Месторождение
         *  -- > 31
         *  Зав
         *  -- > 32
         *  Инв
         *  -- > 33
         * **/

        // Работоспособное состояние
        if (cursor.getString(34) != null) {
            Sostoyanie = cursor.getString(34);
            RadioGroup_Sostoyanie.check("Да".equals(Sostoyanie) ? R.id.Sostoyanie_Da : R.id.Sostoyanie_Net);
        }
        // Наличие маркировки изготовиля
        if (cursor.getString(35) != null) {
            MarkZavod = cursor.getString(35);
            RadioGroup_MarkZavod.check("Есть".equals(MarkZavod) ? R.id.Nal_MarkZavod_Da : R.id.Nal_MarkZavod_Net);
        }
        // Информативность маркировки изготовиля
        if (cursor.getString(36) != null) {
            MarkZavodInf = cursor.getString(36);
            RadioGroup_MarkZavodInf.check("Информативная".equals(MarkZavodInf) ? R.id.Nal_MarkZavodInf_Da : R.id.Nal_MarkZavodInf_Net);
        }
        // Наличие маркировки предприятия
        if (cursor.getString(37) != null) {
            MarkOrganiz = cursor.getString(37);
            RadioGroup_MarkOrganiz.check("Есть".equals(MarkOrganiz) ? R.id.Nal_MarkOrganiz_Da : R.id.Nal_MarkOrganiz_Net);
        }
        // Информативность маркировки предприятия
        if (cursor.getString(38) != null) {
            MarkOrganizInf = cursor.getString(38);
            RadioGroup_MarkOrganizInf.check("Информативная".equals(MarkOrganizInf) ? R.id.Nal_MarkOrganizInf_Da : R.id.Nal_MarkOrganizInf_Net);
        }
        // Наличие заземления
        if (cursor.getString(39) != null) {
            Nal_Zazemleniya = cursor.getString(39);
            RadioGroup_Nal_Zazemleniya.check("Есть".equals(Nal_Zazemleniya) ? R.id.Nal_Zazemleniya_Da : R.id.Nal_Zazemleniya_Net);
        }
        // Состояние фундамента
        if (cursor.getString(40) != null) {
            Fundament = cursor.getString(40);
            RadioGroup_Fundament.check("Удовлетворительное".equals(Fundament) ? R.id.Fundament_Da : R.id.Fundament_Net);
        }
        // Антикорозийное покрытие
        if (cursor.getString(41) != null) {
            Anticor = cursor.getString(41);
            RadioGroup_Anticor.check("Удовлетворительное".equals(Anticor) ? R.id.Anticor_Udovl : R.id.Anticor_NeUdovl);
        }
        // ПРОТЕЧКА РАБОЧЕЙ СРЕДЫ
        if (cursor.getString(42) != null) {
            Flow = cursor.getString(42);
            RadioGroup_Flow.check("Да".equals(Flow) ? R.id.Flow_Da : R.id.Flow_Net);
        }
        // Предохранительный клапан
        if (cursor.getString(43) != null) {
            Klapan = cursor.getString(43);
            RadioGroup_Klapan.check("Есть".equals(Klapan) ? R.id.Klapan_Da : R.id.Klapan_Net);
        }
        // Предохранительный клапан состояние
        if (cursor.getString(44) != null) {
            KlapanSost = cursor.getString(44);
            RadioGroup_KlapanSost.check("Удовлетворительное".equals(KlapanSost) ? R.id.KlapanSost_Udovl : R.id.KlapanSost_Neudovl);
        }
        // Стрелки направления воды
        if (cursor.getString(45) != null) {
            ArrowFlow = cursor.getString(45);
            RadioGroup_ArrowFlow.check("Да".equals(ArrowFlow) ? R.id.ArrowFlow_Da : R.id.ArrowFlow_Da);
        }
        // Дефект электродвигателя
        if (cursor.getString(46) != null) {}
        // Дефекты корпуса
        if (cursor.getString(47) != null) {}

        /** Замеры вибрации
         *  -- > 48-59
         *  Замеры толщинометрии
         *  Всасывающий
         *  -- > 60-63
         *  Напорный патрубок
         *  -- > 64-67
         * **/

        // Ведоместь дефектов оформлена
        if (cursor.getString(68) != null) {
            Vedomost = cursor.getString(68);
             RadioGroup_Vedomost.check("Да".equals(Vedomost) ? R.id.Vedomost_Da : R.id.Vedomost_Net);
        }


        /** =============================== СЧИТЫВАНИЕМ ИЗ VIEW И УСТАНВЛИВАЕМ В STRING ========================================== **/

        // Дата НК
        Button_DataNK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(KartaKontolyaNasos.this, Picker_DataNK,
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
                Intent IntentSittings = new Intent(KartaKontolyaNasos.this, SpisokMegion.class);
                IntentSittings.putExtra("people", "expertsNK");
                startActivityForResult(IntentSittings, PEOPLE);
            }
        });

        /** Работоспособное состояние ? -  **/
        RadioGroup_Sostoyanie.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.Sostoyanie_Da:
                        Sostoyanie = "Да";
                        break;
                    case R.id.Sostoyanie_Net:
                        Sostoyanie = "Нет";
                        break;
                }
            }
        });

        /** Наличие маркировки завода изготовителя -  **/
        RadioGroup_MarkZavod.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.Nal_MarkZavod_Da:
                        MarkZavod = "Есть";
                        break;
                    case R.id.Nal_MarkZavod_Net:
                        MarkZavod = "Нет";
                        break;
                }
            }
        });

        /** Маркировка завода информативная?-  **/
        RadioGroup_MarkZavodInf.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.Nal_MarkZavodInf_Da:
                        MarkZavodInf = "Информативная";
                        break;
                    case R.id.Nal_MarkZavodInf_Net:
                        MarkZavodInf = "Неинформативная";
                        break;
                }
            }
        });

        /** Маркировка предприятия имеется? -  **/
        RadioGroup_MarkOrganiz.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.Nal_MarkOrganiz_Da:
                        MarkOrganiz = "Да";
                        break;
                    case R.id.Nal_MarkOrganiz_Net:
                        MarkOrganiz = "Нет";
                        break;
                }
            }
        });
        /** Маркировка предприятия информативная?-  **/
        RadioGroup_MarkOrganizInf.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.Nal_MarkOrganizInf_Da:
                        MarkOrganizInf = "Информативная";
                        break;
                    case R.id.Nal_MarkOrganizInf_Net:
                        MarkOrganizInf = "Неинформативная";
                        break;
                }
            }
        });


        /** Наличие заземления -  **/
        RadioGroup_Nal_Zazemleniya.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.Nal_Zazemleniya_Da:
                        Nal_Zazemleniya = "Есть";
                        break;
                    case R.id.Nal_Zazemleniya_Net:
                        Nal_Zazemleniya = "Нет";
                        break;
                }
            }
        });
        /** Состояние фундамента -  **/
        RadioGroup_Fundament.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.Fundament_Da:
                        Fundament = "Удовлетворительное";
                        break;
                    case R.id.Fundament_Net:
                        Fundament = "Неудовлетворительное";
                        break;
                }
            }
        });
        /** Антикорозийное покртытие? -  **/
        RadioGroup_Anticor.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.Anticor_Udovl:
                        Anticor = "Удовлетворительное";
                        break;
                    case R.id.Anticor_NeUdovl:
                        Anticor = "Неудовлетворительное";
                        break;
                }
            }
        });
        /** Протечки рабочей среды? -  **/
        RadioGroup_Flow.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.Flow_Da:
                        Flow = "Да";
                        break;
                    case R.id.Flow_Net:
                        Flow = "Нет";
                        break;
                }
            }
        });

        /** Предохранительный клапан? -  **/
        RadioGroup_Klapan.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.Klapan_Da:
                        Klapan = "Есть";
                        break;
                    case R.id.Klapan_Net:
                        Klapan = "Нет";
                        break;
                }
            }
        });
        /** Предохранительный клапан состояние -  **/
        RadioGroup_KlapanSost.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.KlapanSost_Udovl:
                        KlapanSost = "Удовлетворительное";
                        break;
                    case R.id.KlapanSost_Neudovl:
                        KlapanSost = "Неудовлетворительное";
                        break;
                }
            }
        });

        /** Стрелки направления воды   **/
        RadioGroup_ArrowFlow.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.ArrowFlow_Da:
                        ArrowFlow = "Да";
                        break;
                    case R.id.ArrowFlow_Net:
                        ArrowFlow = "Нет";
                        break;
                }
            }
        });

        /** Ведомость дефектов оформлена?   **/
        RadioGroup_Vedomost.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.Vedomost_Da:
                        Vedomost = "Да";
                        break;
                    case R.id.Vedomost_Net:
                        Vedomost = "Нет";
                        break;
                }
            }
        });

        /** Внести   **/
        Button_Vnesti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /** Куст  **/
                Kyst = EditText_Kyst.getText().toString();
                /** Скважина  **/
                Skvajina = EditText_Skvajina.getText().toString();
                /** Месторождение  **/
                Mestorojdenie = EditText_Mestorojdenie.getText().toString();
                /** Зав №  **/
                Zav = EditText_Zav.getText().toString();
                /** Инв №  **/
                Inv = EditText_Inv.getText().toString();
                /** Дефекты Двигателя  **/
                DefEngine = EditText_DefEngine.getText().toString();
                /** Дефекты Корпуса  **/
                DefKorpus = EditText_DefKorpus.getText().toString();
                /** Замеры вибрации   **/
                for(int i = 0; i < EditText_Vibraci.length; i ++){
                    Vibraci[i] = EditText_Vibraci[i].getText().toString();
                }
                /** Замеры Всасывающий  **/
                for(int i = 0; i < EditText_Vsas_zameri.length; i ++){
                    Vsas_zameri[i] = EditText_Vsas_zameri[i].getText().toString();
                }
                /** Замеры Напорный патрубок  **/
                for(int i = 0; i < EditText_Naporniy_zameri.length; i ++){
                    Naporniy_zameri[i] = EditText_Naporniy_zameri[i].getText().toString();
                }

                ContentValues initialValues = new ContentValues();
                initialValues.put(POSITION, position);
                initialValues.put("Stolb27", DataNk);
                initialValues.put("Stolb28", SpecProvNK);
                initialValues.put("Stolb29", Kyst);
                initialValues.put("Stolb30", Skvajina);
                initialValues.put("Stolb31", Mestorojdenie);
                initialValues.put("Stolb32", Zav);
                initialValues.put("Stolb33", Inv);
                initialValues.put("Stolb34", Sostoyanie);
                initialValues.put("Stolb35", MarkZavod);
                initialValues.put("Stolb36", MarkZavodInf);
                initialValues.put("Stolb37", MarkOrganiz);
                initialValues.put("Stolb38", MarkOrganizInf);
                initialValues.put("Stolb39", Nal_Zazemleniya );
                initialValues.put("Stolb40", Fundament );
                initialValues.put("Stolb41", Anticor);
                initialValues.put("Stolb42", Flow);
                initialValues.put("Stolb43", Klapan);
                initialValues.put("Stolb44", KlapanSost);
                initialValues.put("Stolb45", ArrowFlow);
                initialValues.put("Stolb46", DefEngine);
                initialValues.put("Stolb47", DefKorpus);

                int countColumn = 48;
                String strColumn = "Stolb";
                for(int i = 0; i < Vibraci.length; i ++){
                    initialValues.put(strColumn + countColumn, Vibraci[i]);
                    countColumn++;
                }
                countColumn = 60;
                for(int i = 0; i < Vsas_zameri.length; i ++){
                    initialValues.put(strColumn + countColumn, Vsas_zameri[i]);
                    countColumn++;
                }
                countColumn = 64;
                for(int i = 0; i < Naporniy_zameri.length; i ++){
                    initialValues.put(strColumn + countColumn, Naporniy_zameri[i]);
                    countColumn++;
                }

                initialValues.put("Stolb68", Vedomost);

                mDb.insert(ZakazchikDefect, null, initialValues);
                displayMessage(getBaseContext(), "Записан: "+ position);
                Intent IntentSittings = new Intent(KartaKontolyaNasos.this, MainActivity.class);
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
            TextView_DataNk.setText(new SimpleDateFormat("dd.MM.yyyy").format(new Date(dateAndTime.getTimeInMillis())));
            DataNk = TextView_DataNk.getText().toString();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            lampa = data.getStringExtra("people");
            String exp;
            switch (lampa) {
                case "expertsObject": break;
                case "expertsNK":
                    exp = data.getStringExtra("select");
                    SpecProvNK = exp;
                    TextView_SpecProvNK.setText(exp);
                    break;
            }
        }
    }

    private void displayMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
