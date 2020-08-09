package com.example.lider_express.Svodnaya;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.lider_express.DataBase.DatabaseHelper;
import com.example.lider_express.MainActivity;
import com.example.lider_express.R;
import com.example.lider_express.Shared;
import com.example.lider_express.Instruments.SpisokBND;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class KartaKontrolyaSPPK extends AppCompatActivity {
    public DatabaseHelper mDBHelper;
    public SQLiteDatabase mDb;
    private Calendar dateAndTime = Calendar.getInstance();
    static final private int PEOPLE = 0;
    public static final String POSITION = "Position";
    // Переменная в метод onActivityResult для определения специалиста
    private String lampa;

    private String position;
    private String Zakazchik;
    private String ZakazchikDefect;
    private String nameTableForCursor;


    String[] SpinerSppk = {"на факел", "в атмосферу", "в дренажку", "прочее"};
    // XML Привязки
    //                 НАИМЕНОВАНИЕ               СТОЛБЕЦ         ОПИСАНИЕ
    private TextView Text_sppk_DataNK;                //   27         TextView Дата НК
    private Button Button_sppk_DataNK;                //   ^^         Button Дата НК
    private TextView Text_sppk__SpecProvNK;           //   28         TextView ФИО Спец, провод НК
    private Button Button_sppk_SpecProvNK;            //   ^^         Button ФИО Спец, провод НК
    private EditText EditText_sppk_Zav;               // - 30         EditText  Зав
    private EditText EditText_sppk_Inv;               // - 31         EditText  Инвентарь
    private EditText EditText_sppk_usl_prohod;        // - 32         EditText Условный проход клапана
    private EditText EditText_sppk_usl_davlenie;      // - 33         EditText  Условное давление клапана
    private RadioGroup RadioGroup_sppk_Plombs;        // - 34        RadioGroup наличие пломбы
    private RadioGroup RadioGroup_sppk_AKP;           // - 35         RadioGroup наличие АКП
    private RadioGroup RadioGroup_sppk_SostoyanieAKP; // - 36         RadioGroup состояние АКП
    private RadioGroup RadioGroup_sppk_Zaglushka;     // - 37         RadioGroup наличие заглушки
    private RadioGroup RadioGroup_sppk_tablichka;     // - 38         RadioGroup табличка
    private EditText EditText_sppk_factdavl;          // - 38         EditText  фактическое давление
    private RadioGroup RadioGroup_sppk_germetik;      // - 39         RadioGroup Герметичность разъемов
    private RadioGroup RadioGroup_sppk_germetikklapan;// - 40         RadioGroup Герметичность затвора клапана
    private RadioGroup RadioGroup_sppk_pruzhina;      // - 41         RadioGroup Состояние пружины
    private EditText EditText_sppk_VhodDiametr;       // - 42         EditText  диаметр входного патрубка
    private EditText EditText_sppk_VihodDiametr;      // - 43         EditText  диаметр выходного патрубка
    private EditText EditText_sppk_VhodUZT1;          // - 44         EditText  диаметр выходного патрубка
    private EditText EditText_sppk_VihodUZT1;         // - 45         EditText  диаметр выходного патрубка
    private EditText EditText_sppk_VhodUZT2;          // - 46         EditText  диаметр выходного патрубка
    private EditText EditText_sppk_VihodUZT2;         // - 47         EditText  диаметр выходного патрубка
    private EditText EditText_sppk_VhodUZT3;          // - 48         EditText  диаметр выходного патрубка
    private EditText EditText_sppk_VihodUZT3;         // - 49         EditText  диаметр выходного патрубка
    private EditText EditText_sppk_VhodUZT4;          // - 50         EditText  диаметр выходного патрубка
    private EditText EditText_sppk_VihodUZT4;         // - 51         EditText  диаметр выходного патрубка
    private Button Button_sppk_Vnesti;
    // --- String ---
    //                 НАИМЕНОВАНИЕ               СТОЛБЕЦ         ОПИСАНИЕ

    private String DataNk;                        // - 27         Исполнение
    private String SpecProvNK;                    // - 28         Шурфовка проводилась?
    private String Zav;                           // - 32         На момент проведения НК о
    private String Inv;                           // - 33         Дата начала Останова
    private String Usl_prohod;
    private String Usl_davlenie;
    private String Plombs;                        // - 34         Наличие пломбы
    private String AKP;
    private String SostoyanieAKP;
    private String Zaglushka;
    private String tablichka;
    private String factdavl;
    private String SbrosSppk;
    private String germetik;
    private String germetikklapan;
    private String pruzhina;
    private String VhodDiametr;
    private String VihodDiametr;
    private String VhodUZT1;
    private String VihodUZT1;
    private String VhodUZT2;
    private String VihodUZT2;
    private String VhodUZT3;
    private String VihodUZT3;
    private String VhodUZT4;
    private String VihodUZT4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_karta_kontrolya_s_p_p_k);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Text_sppk_DataNK = findViewById(R.id.Text_sppk_DataNK);
        Button_sppk_DataNK = findViewById(R.id.Button_sppk_DataNK);
        Text_sppk__SpecProvNK = findViewById(R.id.Text_sppk__SpecProvNK);
        Button_sppk_SpecProvNK = findViewById(R.id.Button_sppk_SpecProvNK);
        EditText_sppk_Zav = findViewById(R.id.EditText_sppk_Zav);
        EditText_sppk_Inv = findViewById(R.id.EditText_sppk_Inv);
        EditText_sppk_usl_prohod = findViewById(R.id.EditText_sppk_usl_prohod);
        EditText_sppk_usl_davlenie = findViewById(R.id.EditText_sppk_usl_davlenie);
        RadioGroup_sppk_Plombs = findViewById(R.id.RadioGroup_sppk_Plombs);
        RadioGroup_sppk_AKP = findViewById(R.id.RadioGroup_sppk_AKP);
        RadioGroup_sppk_SostoyanieAKP = findViewById(R.id.RadioGroup_sppk_SostoyanieAKP);
        RadioGroup_sppk_Zaglushka = findViewById(R.id.RadioGroup_sppk_Zaglushka);
        RadioGroup_sppk_tablichka = findViewById(R.id.RadioGroup_sppk_tablichka);
        EditText_sppk_factdavl = findViewById(R.id.EditText_sppk_factdavl);
        RadioGroup_sppk_germetik = findViewById(R.id.RadioGroup_sppk_germetik);
        RadioGroup_sppk_germetikklapan = findViewById(R.id.RadioGroup_sppk_germetikklapan);
        RadioGroup_sppk_pruzhina = findViewById(R.id.RadioGroup_sppk_pruzhina);
        EditText_sppk_VhodDiametr = findViewById(R.id.EditText_sppk_VhodDiametr);
        EditText_sppk_VihodDiametr = findViewById(R.id.EditText_sppk_VihodDiametr);
        EditText_sppk_VhodUZT1 = findViewById(R.id.EditText_sppk_VhodUZT1);
        EditText_sppk_VhodUZT2 = findViewById(R.id.EditText_sppk_VhodUZT2);
        EditText_sppk_VhodUZT3 = findViewById(R.id.EditText_sppk_VhodUZT3);
        EditText_sppk_VhodUZT4 = findViewById(R.id.EditText_sppk_VhodUZT4);
        EditText_sppk_VihodUZT1 = findViewById(R.id.EditText_sppk_VihodUZT1);
        EditText_sppk_VihodUZT2 = findViewById(R.id.EditText_sppk_VihodUZT2);
        EditText_sppk_VihodUZT3 = findViewById(R.id.EditText_sppk_VihodUZT3);
        EditText_sppk_VihodUZT4 = findViewById(R.id.EditText_sppk_VihodUZT4);
        Button_sppk_Vnesti = findViewById(R.id.Button_sppk_Vnesti);
        /*Spiner*/
        final Spinner spinnerSPPK = findViewById(R.id.SpinnerSppk);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, SpinerSppk);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSPPK.setAdapter(adapter);
        spinnerSPPK.setPrompt("Title");
        spinnerSPPK.setSelection(0);
        spinnerSPPK.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                SbrosSppk = spinnerSPPK.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        /*Spiner*/

        Bundle arguments = getIntent().getExtras(); // Bundle
        position = arguments.getString("position"); // Позиция оборудования в БД
        Zakazchik= arguments.getString("Zakazchik");// Заказчик
        switch (Zakazchik){
            case "Bashneft2020": ZakazchikDefect = Shared.nameDefectBashneft2020_SPPK;
                nameTableForCursor = Shared.nameBashneft2020_Nasos; break;
        }

        mDBHelper = MainActivity.getDBHelper();  // подклчюение к БД
        try {
            mDb = mDBHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }
        // Дата НК
        // Дата НК
        Button_sppk_DataNK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(KartaKontrolyaSPPK.this, Picker_DataNK,
                        dateAndTime.get(Calendar.YEAR),
                        dateAndTime.get(Calendar.MONTH),
                        dateAndTime.get(Calendar.DAY_OF_MONTH))
                        .show();
            }
        });
        // Button ФИО Спец, проводивших НК
        Button_sppk_SpecProvNK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent IntentSittings = new Intent(KartaKontrolyaSPPK.this, SpisokBND.class);
                IntentSittings.putExtra("people", "irldefects");
                startActivityForResult(IntentSittings, PEOPLE);
            }
        });

        /** Налчиие пломбы ? -  **/
        RadioGroup_sppk_Plombs.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.Plombs_sppk_Da:
                        Plombs = "Да";
                        break;
                    case R.id.Plombs_sppk_Net:
                        Plombs = "Нет";
                        break;
                }
            }
        });
        /** Наличие АКП ? -  **/
        RadioGroup_sppk_AKP.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.AKP_sppk_Da:
                        AKP = "Да";
                        break;
                    case R.id.AKP_sppk_Net:
                        AKP = "Нет";
                        break;
                }
            }
        });
        /** Состояние АКП ? -  **/
        RadioGroup_sppk_SostoyanieAKP.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.SostoyanieAKP_sppk_Da:
                        SostoyanieAKP = "Да";
                        break;
                    case R.id.SostoyanieAKP_sppk_Net:
                        SostoyanieAKP = "Нет";
                        break;
                }
            }
        });
        /** Наличие заглушки? -  **/
        RadioGroup_sppk_Zaglushka.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.Zaglushka_sppk_Da:
                        Zaglushka = "Да";
                        break;
                    case R.id.Zaglushka_sppk_Net:
                        Zaglushka = "Нет";
                        break;
                }
            }
        });
        /** Наличие таблички? -  **/
        RadioGroup_sppk_tablichka.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.tablichka_sppk_Da:
                        tablichka = "Да";
                        break;
                    case R.id.tablichka_sppk_Net:
                        tablichka = "Нет";
                        break;
                }
            }
        });
        /** Герметичность разъемов? -  **/
        RadioGroup_sppk_germetik.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.germetik_sppk_Da:
                        germetik = "Да";
                        break;
                    case R.id.germetik_sppk_Net:
                        germetik = "Нет";
                        break;
                }
            }
        });
        /** Герметичность клапана? -  **/
        RadioGroup_sppk_germetikklapan.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.germetikklapan_sppk_Da:
                        germetikklapan = "Да";
                        break;
                    case R.id.germetikklapan_sppk_Net:
                        germetikklapan = "Нет";
                        break;
                }
            }
        });
        /** Состояние пружины? -  **/
        RadioGroup_sppk_pruzhina.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.pruzhina_sppk_Da:
                        pruzhina = "Да";
                        break;
                    case R.id.pruzhina_sppk_Net:
                        pruzhina = "Нет";
                        break;
                }
            }
        });
        /** Внести   **/
        Button_sppk_Vnesti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /** Зав №  **/
                Zav = EditText_sppk_Zav.getText().toString();
                /** Инв №  **/
                Inv = EditText_sppk_Inv.getText().toString();
                /** Условный проход  **/
                Usl_prohod = EditText_sppk_usl_prohod.getText().toString();
                /** Условное давление  **/
                Usl_davlenie = EditText_sppk_usl_davlenie.getText().toString();
                /** Фактическое давление  **/
                factdavl = EditText_sppk_factdavl.getText().toString();
                /** Диаметр входного  **/
                VhodDiametr = EditText_sppk_VhodDiametr.getText().toString();
                /** Диаметр выходного  **/
                VihodDiametr = EditText_sppk_VihodDiametr.getText().toString();
                /** УЗТ1 входного  **/
                VhodUZT1 = EditText_sppk_VhodUZT1.getText().toString();
                /** УЗТ2 входного  **/
                VhodUZT2 = EditText_sppk_VhodUZT2.getText().toString();
                /** УЗТ3 входного  **/
                VhodUZT3 = EditText_sppk_VhodUZT3.getText().toString();
                /** УЗТ4 входного  **/
                VhodUZT4 = EditText_sppk_VhodUZT4.getText().toString();
                /** УЗТ1 выходного  **/
                VihodUZT1 = EditText_sppk_VihodUZT1.getText().toString();
                /** УЗТ2 выходного  **/
                VihodUZT2 = EditText_sppk_VihodUZT2.getText().toString();
                /** УЗТ3 выходного  **/
                VihodUZT3 = EditText_sppk_VihodUZT3.getText().toString();
                /** УЗТ4 выходного  **/
                VihodUZT4 = EditText_sppk_VihodUZT4.getText().toString();

                ContentValues initialValues = new ContentValues();
                initialValues.put(POSITION, position);
                initialValues.put("Stolb27", DataNk);
                initialValues.put("Stolb28", SpecProvNK);
                initialValues.put("Stolb29", Zav);
                initialValues.put("Stolb30", Inv);
                initialValues.put("Stolb31", Usl_prohod);
                initialValues.put("Stolb32", Usl_davlenie);
                initialValues.put("Stolb33", Plombs);
                initialValues.put("Stolb34", AKP);
                initialValues.put("Stolb35", SostoyanieAKP);
                initialValues.put("Stolb36", Zaglushka );
                initialValues.put("Stolb37", tablichka );
                initialValues.put("Stolb38", factdavl);
                initialValues.put("Stolb39", SbrosSppk);
                initialValues.put("Stolb40", germetik);
                initialValues.put("Stolb41", germetikklapan);
                initialValues.put("Stolb42", pruzhina);
                initialValues.put("Stolb43", VhodDiametr);
                initialValues.put("Stolb44", VihodDiametr);
                initialValues.put("Stolb45", VhodUZT1);
                initialValues.put("Stolb46", VhodUZT2);
                initialValues.put("Stolb47", VhodUZT3);
                initialValues.put("Stolb48", VhodUZT4);
                initialValues.put("Stolb49", VihodUZT1);
                initialValues.put("Stolb50", VihodUZT2);
                initialValues.put("Stolb51", VihodUZT3);
                initialValues.put("Stolb52", VihodUZT4);
                mDb.insert(ZakazchikDefect, null, initialValues);
                displayMessage(getBaseContext(), "Записан: "+ position);
                Intent IntentSittings = new Intent(KartaKontrolyaSPPK.this, MainActivity.class);
                startActivity(IntentSittings);
            }
        });
    }
    DatePickerDialog.OnDateSetListener Picker_DataNK = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            Text_sppk_DataNK.setText(new SimpleDateFormat("dd.MM.yyyy").format(new Date(dateAndTime.getTimeInMillis())));
            DataNk = Text_sppk_DataNK.getText().toString();
        }
    };
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            lampa = data.getStringExtra("people");
            String exp;
            switch (lampa) {
                case "irldefects":
                    exp = data.getStringExtra("select");
                    SpecProvNK=exp;
                    Text_sppk__SpecProvNK.setText(exp);
                    break;
            }
        }
    }
    private void displayMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
