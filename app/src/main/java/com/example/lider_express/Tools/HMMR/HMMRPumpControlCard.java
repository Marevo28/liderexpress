package com.example.lider_express.Tools.HMMR;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.lider_express.Core.Handlers.ButtonHandler;
import com.example.lider_express.Core.Handlers.RadioGroupHandler;
import com.example.lider_express.Core.Handlers.TextViewHandler;
import com.example.lider_express.Core.Item;
import com.example.lider_express.Core.Summary.SummaryBehavior;
import com.example.lider_express.Core.Summary.SummaryBeta;
import com.example.lider_express.DataBase.DatabaseHelper;
import com.example.lider_express.MainActivity;
import com.example.lider_express.R;
import com.example.lider_express.Shared;

import java.util.ArrayList;

/**
 * @author LeStat
 * ДОКУМЕНТАЦИЮ
 * ПО ИСПОЛЬЗОВАНИЮ ХЕЛПЕРА SUMMARY
 * И РЕКОМАНДАЦИЮ ПО ИМЕНОВАНИЮ
 * ВЬЮШЕК ОПИСАНО В КЛАССЕ SUMMARY
 */

public class HMMRPumpControlCard extends AppCompatActivity implements SummaryBehavior {

    public DatabaseHelper mDBHelper;
    public SQLiteDatabase mDb;

    private SummaryBeta pump;
    private final String PUMP_READ_DATA_BASE = Shared.nameHMMR_SUMMARY;
    private final String PUMP_WRITE_DATA_BASE = Shared.nameDefectHMMR_Pump;

    Item item7 ,item8,
            item9 ,item10,
            item11 ,item12,
            item13 ,item14,
            item15 ,item16,
            item39 ,item40,
            item41 ,item42,
            item43 ,item44,
            item45 ,item46,
            item47 ,item48,
            item49 ,item50,
            item57 ,item58,
            item69 /** quantification */ ,item70,
            item71 ,item72,
            item73 ,item74,
            item75 ,item76,
            item77 ,item78,
            item79 ,item80,
            item81 ,item82,
            item101;


    Item bndItem27 ,bndItem28,
            bndItem29 ,bndItem30,
            bndItem37 ,bndItem38,
            bndItem43 ,bndItem44,
            bndItem45 ,bndItem46,
            bndItem47 ,bndItem48,
            bndItem49 ,bndItem50,
            bndItem51 ,bndItem52;

    Item radioHiddenItem1, radioHiddenItem2,
            linearHiddenItem1, linearHiddenItem2,
            linearHiddenItem3;

    Item imageItem1;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hmmr_2020_pump_control_card);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // new DatabaseHelper(this);// подклчюение к БД
        mDBHelper = MainActivity.getDBHelper();
        try {
            mDb = mDBHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }

        Bundle arguments = getIntent().getExtras();
        String position = arguments.getString("position");


        pump = new SummaryBeta(this, this, PUMP_READ_DATA_BASE,
                PUMP_WRITE_DATA_BASE, mDb, position);

        initializeItems();

        initializeSummary();

        pump.setState();

        initializeListeners();


        //  Находим нашу заветную кнопочку Save
        Button save = findViewById(R.id.hmmr_pump_save);

        //  Листенер на нашу кнопочку
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Save values to data base
                pump.saveData();
                Intent IntentSittings = new Intent(HMMRPumpControlCard.this, MainActivity.class);
                startActivity(IntentSittings);
            }
        });
    }

    @Override
    public void initializeItems() {
        bndItem27 = new Item(this, R.id.hmmr_pump_from_hmmr_id_27, true);
        bndItem28 = new Item(this, R.id.hmmr_pump_from_hmmr_id_28, true);
        bndItem29 = new Item(this, R.id.hmmr_pump_from_hmmr_id_29, true);
        bndItem30 = new Item(this, R.id.hmmr_pump_from_hmmr_id_30, true);
        bndItem37 = new Item(this, R.id.hmmr_pump_from_hmmr_id_37, true);
        bndItem38 = new Item(this, R.id.hmmr_pump_from_hmmr_id_38, true);
        bndItem44 = new Item(this, R.id.hmmr_pump_from_hmmr_id_44, true);
        bndItem45 = new Item(this, R.id.hmmr_pump_from_hmmr_id_45, true);
        bndItem46 = new Item(this, R.id.hmmr_pump_from_hmmr_id_46_42, true);
        bndItem47 = new Item(this, R.id.hmmr_pump_from_hmmr_id_47_43, true);
        bndItem49 = new Item(this, R.id.hmmr_pump_from_hmmr_id_49, true);
        bndItem51 = new Item(this, R.id.hmmr_pump_from_hmmr_id_51, true);

        item7 = new Item(this,  R.id.hmmr_pump_id_7, true);
        item10 = new Item(this, R.id.hmmr_pump_id_10, true);
        item12 = new Item(this, R.id.hmmr_pump_id_12, true);
        item15 = new Item(this, R.id.hmmr_pump_id_15, true);
        item39 = new Item(this, R.id.hmmr_pump_id_39, true);
        item40 = new Item(this, R.id.hmmr_pump_id_40, true);
        item41 = new Item(this, R.id.hmmr_pump_id_41, true);
        item42 = new Item(this, R.id.hmmr_pump_id_42, true);
        item43 = new Item(this, R.id.hmmr_pump_id_43, true);
        item44 = new Item(this, R.id.hmmr_pump_id_44, true);
        item45 = new Item(this, R.id.hmmr_pump_id_45, true);
        item46 = new Item(this, R.id.hmmr_pump_id_46, true);
        item47 = new Item(this, R.id.hmmr_pump_id_47, true);
        item48 = new Item(this, R.id.hmmr_pump_id_48, true);
        item49 = new Item(this, R.id.hmmr_pump_id_49, true);
        item57 = new Item(this, R.id.hmmr_pump_id_57, true);
        item58 = new Item(this, R.id.hmmr_pump_id_58, true);
        item69 = new Item(this, R.id.hmmr_pump_id_69, true);
        item71 = new Item(this, R.id.hmmr_pump_id_71, true);
        item72 = new Item(this, R.id.hmmr_pump_id_72, true);
        item73 = new Item(this, R.id.hmmr_pump_id_73, true);
        item74 = new Item(this, R.id.hmmr_pump_id_74, true);
        item75 = new Item(this, R.id.hmmr_pump_id_75, true);
        item76 = new Item(this, R.id.hmmr_pump_id_76, true);
        item77 = new Item(this, R.id.hmmr_pump_id_77, true);
        item78 = new Item(this, R.id.hmmr_pump_id_78, true);
        item79 = new Item(this, R.id.hmmr_pump_id_79, true);
        item80 = new Item(this, R.id.hmmr_pump_id_80, true);
        item81 = new Item(this, R.id.hmmr_pump_id_81, true);
        item82 = new Item(this, R.id.hmmr_pump_id_82, true);
        item101 = new Item(this, R.id.hmmr_pump_defectTree_101, true);

        radioHiddenItem1 = new Item(this,  R.id.hmmr_pump_radio_group_hidden_1, true);
        radioHiddenItem2 = new Item(this,  R.id.hmmr_pump_radio_group_hidden_2, true);
        linearHiddenItem1 = new Item(this, R.id.hmmr_pump_layout_hidden_1, true);
        linearHiddenItem2 = new Item(this, R.id.hmmr_pump_layout_hidden_2, true);
        linearHiddenItem3 = new Item(this, R.id.hmmr_pump_layout_hidden_3, true);

        imageItem1 = new Item(this, R.id.hmmr_pump_info_table, true);
        ButtonHandler.setActionInfDialog(this, imageItem1, R.drawable.nasos_table);
    }


    @Override
    public void initializeSummary() {

        pump.addItem(1, bndItem27); //  Эксперты, записанные в журнал "Дата записи:
        pump.addItem(2, bndItem28); //   Эксперты, записанные в журнал ФИО:
        pump.addItem(3, bndItem29); //  Аттестованные специалисты НК, вписанные в журнал инструктажа Дата записи:
        pump.addItem(4, bndItem30); //   Аттестованные специалисты НК, вписанные в журнал инструктажа ФИО:
        pump.addItem(5, bndItem37); //  Дата начала останова
        pump.addItem(6, bndItem38); //  Дата окончания останова
        pump.addItem(7, bndItem44); //  Акт неготовности оборудования составлен?
        pump.addItem(8, bndItem45); // Акт непредоставления документов составлен?
        pump.addItem(9, bndItem46); // Дата НК:
        pump.addItem(10, bndItem47); // ФИО Специалиста:
        pump.addItem(11, bndItem49); // Причина по которой не проводилось обследование?
        pump.addItem(12, bndItem51); // Какой выезд?
        pump.addItem(13, item7); //  Наименовение
        pump.addItem(14, item10); // Заводской №
        pump.addItem(15, item12); // Технологический №
        pump.addItem(16, item15); // Обьект/Куст
        pump.addItem(17, item39); // Напорный патрубок (от корпуса насоса до фланца) Наружный диаметр, мм:
        pump.addItem(18, item40); // Напорный патрубок (от корпуса насоса до фланца) Толщина, мм
        pump.addItem(19, item41); //  Всасывающий патрубок (от корпуса насоса до фланца) Наружный диаметр, мм:
        pump.addItem(20, item42); //  Всасывающий патрубок (от корпуса насоса до фланца) Толщина, мм
        pump.addItem(21, item43); //  Работоспособное состояние?
        pump.addItem(22, item44); //  Наличие маркировки завода изготовителя
        pump.addItem(23, item45); //  Состояние фундамента
        pump.addItem(24, item46); //  Состояние ЛКП
        pump.addItem(25, item47); // Наличие заземления
        pump.addItem(26, item48); // Протечки торцевых уплотнений
        pump.addItem(27, item49); // Имеются стрелки направления среды
        pump.addItem(28, item57); // На напорном трубопроводе установлен обратный клапан?
        pump.addItem(29, item58); // На двигателе указано направление вращения ротора?
        pump.addItem(30, item69); // Качественная оценка технического состояния агрегата:
        pump.addItem(31, item71); // 1 - X мм
        pump.addItem(32, item72); // 1 - Y мм
        pump.addItem(33, item73); // 1 - Z мм
        pump.addItem(34, item74); // 2 - X мм
        pump.addItem(35, item75); // 2 - Y мм
        pump.addItem(36, item76); // 2 - Z мм
        pump.addItem(37, item77); // 3 - X мм
        pump.addItem(38, item78); // 3 - Y мм
        pump.addItem(39, item79); // 3 - Z мм
        pump.addItem(40, item80); // 4 - X мм
        pump.addItem(41, item81); // 4 - Y мм
        pump.addItem(42, item82); // 4 - Z мм
        pump.addItem(43, item101); // 4 - Z мм


//
//
//
        /**
         *
         *
         *         1 //  Эксперты, записанные в журнал "Дата записи:
         *         2 //   Эксперты, записанные в журнал ФИО:
         *         3 //  Аттестованные специалисты НК, вписанные в журнал инструктажа Дата записи:
         *         4 //   Аттестованные специалисты НК, вписанные в журнал инструктажа ФИО:
         *         5 //  Дата начала останова
         *         6 //  Дата окончания останова
         *         7 //  Акт неготовности оборудования составлен?
         *         8 // Акт непредоставления документов составлен?
         *         9 // Дата НК:
         *         10 // ФИО Специалиста:
         *         11 // Причина по которой не проводилось обследование?
         *         12 // Какой выезд?
         *         13 //  Наименовение
         *         14  // Заводской №
         *         15  // Технологический №
         *         16  // Обьект/Куст
         *         17  // Напорный патрубок (от корпуса насоса до фланца) Наружный диаметр, мм:
         *         18  // Напорный патрубок (от корпуса насоса до фланца) Толщина, мм
         *         19  //  Всасывающий патрубок (от корпуса насоса до фланца) Наружный диаметр, мм:
         *         20  //  Всасывающий патрубок (от корпуса насоса до фланца) Толщина, мм
         *         21  //  Работоспособное состояние?
         *         22  //  Наличие маркировки завода изготовителя
         *         23  //  Состояние фундамента
         *         24  //  Состояние ЛКП
         *         25  // Наличие заземления
         *         26  // Протечки торцевых уплотнений
         *         27  // Имеются стрелки направления среды
         *         28  // На напорном трубопроводе установлен обратный клапан?
         *         29  // На двигателе указано направление вращения ротора?
         *         30  // Качественная оценка технического состояния агрегата:
         *         31  // 1 - X мм
         *         32  // 1 - Y мм
         *         33  // 1 - Z мм
         *         34  // 2 - X мм
         *         35  // 2 - Y мм
         *         36  // 2 - Z мм
         *         37  // 3 - X мм
         *         38  // 3 - Y мм
         *         39  // 3 - Z мм
         *         30  // 4 - X мм
         *         41  // 4 - Y мм
         *         42  // 4 - Z мм
         */

    }

    @Override
    public void initializeListeners() {

        RadioGroupHandler.setActionMultipleHidden(this, radioHiddenItem1,
                new Item[]{linearHiddenItem1}, new Item[]{linearHiddenItem2},
                new String[]{"Да"});

        RadioGroupHandler.setActionSingleHidden(this, radioHiddenItem2, linearHiddenItem2, new String[]{"Нет"});

        ((EditText) item71.getView()).addTextChangedListener(listenerVibration());
        ((EditText) item72.getView()).addTextChangedListener(listenerVibration());
        ((EditText) item73.getView()).addTextChangedListener(listenerVibration());
        ((EditText) item74.getView()).addTextChangedListener(listenerVibration());
        ((EditText) item75.getView()).addTextChangedListener(listenerVibration());
        ((EditText) item76.getView()).addTextChangedListener(listenerVibration());
        ((EditText) item77.getView()).addTextChangedListener(listenerVibration());
        ((EditText) item78.getView()).addTextChangedListener(listenerVibration());
        ((EditText) item79.getView()).addTextChangedListener(listenerVibration());
        ((EditText) item80.getView()).addTextChangedListener(listenerVibration());
        ((EditText) item81.getView()).addTextChangedListener(listenerVibration());
        ((EditText) item82.getView()).addTextChangedListener(listenerVibration());

        TextViewHandler.setStartActivityListener(this, this, bndItem46, TextViewHandler.DESTINATION_DATA);
        TextViewHandler.setStartActivityListener(this, this, bndItem27, TextViewHandler.DESTINATION_DATA);
        TextViewHandler.setStartActivityListener(this, this, bndItem29, TextViewHandler.DESTINATION_DATA);

        TextViewHandler.setStartActivityListener(this, this, bndItem37, TextViewHandler.DESTINATION_DATA);
        TextViewHandler.setStartActivityListener(this, this, bndItem38, TextViewHandler.DESTINATION_DATA);

        TextViewHandler.setStartActivityListener(this, this, bndItem47, TextViewHandler.DESTINATION_SPEC_NKO);
        TextViewHandler.setStartActivityListener(this, this, bndItem28, TextViewHandler.DESTINATION_EXP_JOURNAL);
        TextViewHandler.setStartActivityListener(this, this, bndItem30, TextViewHandler.DESTINATION_SPEC_JOURNAL);
    }

    //  Кастомный листенер для EditText
    //  Кол. оченка агрегата
    @Deprecated
    TextWatcher listenerVibration() {
        TextWatcher listener = new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                setQuantification();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        };

        return listener;
    }


    // Установить колличественную оценку
    @Deprecated
    private void setQuantification(){
        ArrayList<EditText> listEditTexts = new ArrayList<>();

        listEditTexts.add(((EditText) item71.getView()));
        listEditTexts.add(((EditText) item72.getView()));
        listEditTexts.add(((EditText) item73.getView()));
        listEditTexts.add(((EditText) item74.getView()));
        listEditTexts.add(((EditText) item75.getView()));
        listEditTexts.add(((EditText) item76.getView()));
        listEditTexts.add(((EditText) item77.getView()));
        listEditTexts.add(((EditText) item78.getView()));
        listEditTexts.add(((EditText) item79.getView()));
        listEditTexts.add(((EditText) item80.getView()));
        listEditTexts.add(((EditText) item81.getView()));
        listEditTexts.add(((EditText) item82.getView()));

        float maxVibration = 0;
        for (EditText editText : listEditTexts) {
            String strVibration = editText.getText().toString();
            if (strVibration != null && strVibration.length() != 0) {
                float vibration = Float.parseFloat(strVibration);
                if (vibration > maxVibration) {
                    maxVibration = vibration;
                }
            }
        }
        if (maxVibration < 4.5) {
            ((TextView)item69.getView()).setText("Хорошо");
        }
        if (maxVibration >= 4.5 && maxVibration < 7.0) {
            ((TextView)item69.getView()).setText("Удовлетворительно");
        }
        if (maxVibration >= 7.0 && maxVibration <= 11.2) {
            ((TextView)item69.getView()).setText("Еще допустимо");
        }
        if (maxVibration > 11.2) {
            ((TextView)item69.getView()).setText("Недопустимо");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String exp = data.getStringExtra("select");

            ((TextView) TextViewHandler.variableView).setText(exp);
        }
    }

    @Override
    public void onBackPressed(){
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Остались несохранненые данные!")
                .setMessage("Хотите выйти?")
                .setPositiveButton("Да", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("Нет", null)
                .show();
    }

}