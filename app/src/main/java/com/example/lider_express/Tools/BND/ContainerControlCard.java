package com.example.lider_express.Tools.BND;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.lider_express.Core.Handlers.ButtonHandler;
import com.example.lider_express.Core.Handlers.RadioGroupHandler;
import com.example.lider_express.Core.Handlers.TextViewHandler;
import com.example.lider_express.Core.Item;
import com.example.lider_express.Core.Summary.SummaryBeta;
import com.example.lider_express.Core.Summary.SummaryBehavior;
import com.example.lider_express.DataBase.DatabaseHelper;
import com.example.lider_express.MainActivity;
import com.example.lider_express.R;
import com.example.lider_express.Shared;

import java.util.HashMap;

public class ContainerControlCard extends AppCompatActivity implements SummaryBehavior {

    /**
     * НЕ АКТИВЕН!
     */

    public DatabaseHelper mDBHelper;
    public SQLiteDatabase mDb;

    private SummaryBeta containers;
    private final String CONTAINER_READ_DATA_BASE = "";
    private final String CONTAINER_WRITE_DATA_BASE = "";

    Item item1 /** LinearLayout */,                             item2  /** TextView Дата НК */,
            item3 /** TextView ФИО Спец НК*/,                   item4  /** Radio Сосуд в останове? */,
            item5 /* Свободный */,                              item6  /** EditText Цех */,
            item7 /** EditText Обьект */,                       item8  /** EditText Наименование */,
            item9 /** EditText Зав № */,                        item10 /** EditText Инв № */,
            item11 /** EditText Техн № */,                      item12 /** EditText Рег № */,
            item13 /** Radio Маркировка */,                     item14 /** Radio Соответствие маркировки */,
            item15 /** EditText Не соотв маркировки Hidden */,  item16 /** Radio Вид опор */,
            item17 /** Размещение */,                           item18 /** Radio Исполнение */,
            item19 /** EditText Исполнение другое Hidden */,    item20 /** Radio Форма*/,
            item21 /** Radio Изоляция */,                       item22 /** Radio Соотв изоляции */,
            item23 /** Radio Антикор покр наружн*/,             item24 /** Radio Антикор покр внутр*/,
            item25 /* Свободный */,                             item26 /* Свободный */,
            item27 /* Свободный */,                             item28 /* Свободный */,
            item29 /* Свободный */,                             item30 /* Свободный */,
            item31 /* Свободный */,                             item32 /* Свободный */,
            item33 /** Radio Сост опор */,                      item34 /** Radio Фундамент */,
            item35 /** Radio Сост фундамента */,                item36 /** Radio Заземление */,
            item37 /** Radio Состояние заземления */,           item38 /** Radio Молниезащита */,
            item39 /** Radio Сост молниещазиты */,              item40 /** Radio Анкерные болты */,
            item41 /** Radio Сост анкерных болтов */,           item42 /** Radio Футеровка */,
            item43 /** Radio Сост футеровки */,                 item44 /** Radio Манометр */,
            item45 /** LinearLayout Манометр Hidden*/,          item46 /** Radio Манометр пломба */,
            item47 /** Radio Клеймр */,                         item48 /** Radio Поверка маномтр действ? */,
            item49 /** Radio Манометр повр? */,                 item50 /** Radio Манометр исправен? */,
            item51 /** Radio Класс точн маномтра*/,             item52 /** Radio Манометр красная черта */,
            item53 /** Radio Манометр диаметр */,               item54 /** Radio Датчик температуры */,
            item55 /** Radio Указатель уровня жидкости*/,       item56 /** Radio Вакуумметр */,
            item57 /** Radio Запорная арматура */,              item58 /** Radio Предохр устройство */,
            item59 /* Свободный */,                             item60 /** EditText  Зав. № СППК */,
            item61 /** EditText Условн проход СППК, мм */,      item62 /** EditText Условное давление СППК, МПа */,
            item63 /** Radio между ... запор арм устан. */,     item64 /** Radio Конструкция сосуда соответствует паспорту */,
            item65 /** EditText Год поверки манометра */,       item66 /** Radio Был ли по факту проведен осмотр внутренней поверхности сосуда */,
            item67, item68,
            item69, item70,
            item71, item72,
            item73, item74;

    Item item102, item104, item106, item108, item110, item112, item114, item116;
    Item item103, item105, item107, item109, item111, item113, item115, item117;
    Item item101;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_bnd_2020_container_control_card);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDBHelper = MainActivity.getDBHelper();
        try {
            mDb = mDBHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }

        Bundle arguments = getIntent().getExtras();
        String position = arguments.getString("position");

        containers = new SummaryBeta(this, this, CONTAINER_READ_DATA_BASE,
                CONTAINER_WRITE_DATA_BASE, mDb, position);

        initializeItems();

        initializeSummary();

        containers.setState();

        initializeListeners();


        Button buttonSave = findViewById(R.id.bnd2020_container_save);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Save values to data base
                containers.saveData();
                Intent IntentSittings = new Intent(ContainerControlCard.this, MainActivity.class);
                startActivity(IntentSittings);
            }
        });

    }

    @Override
    public void initializeItems() {
        item1 = new Item(this, R.id.bnd2020_container_controlCard_1, false);
        item2 = new Item(this, R.id.bnd2020_container_controlCard_2, true);
        item3 = new Item(this, R.id.bnd2020_container_controlCard_3, true);
        item4 = new Item(this, R.id.bnd2020_container_controlCard_4, true);
//        item5 = new Item(this, R.id.bnd2020_container_controlCard_5, true);
        item6 = new Item(this, R.id.bnd2020_container_controlCard_6, true);
        item7 = new Item(this, R.id.bnd2020_container_controlCard_7, true);
        item8 = new Item(this, R.id.bnd2020_container_controlCard_8, true);
        item9 = new Item(this, R.id.bnd2020_container_controlCard_9, true);
        item10 = new Item(this, R.id.bnd2020_container_controlCard_10, true);
        item11 = new Item(this, R.id.bnd2020_container_controlCard_11, true);
        item12 = new Item(this, R.id.bnd2020_container_controlCard_12, true);
        item13 = new Item(this, R.id.bnd2020_container_controlCard_13, true);
        item14 = new Item(this, R.id.bnd2020_container_controlCard_14, true);
        item15 = new Item(this, R.id.bnd2020_container_controlCard_15, false);
        item16 = new Item(this, R.id.bnd2020_container_controlCard_16, true);
        item17 = new Item(this, R.id.bnd2020_container_controlCard_17, true);
        item18 = new Item(this, R.id.bnd2020_container_controlCard_18, true);
        item19 = new Item(this, R.id.bnd2020_container_controlCard_19, true);
        item20 = new Item(this, R.id.bnd2020_container_controlCard_20, true);
        item21 = new Item(this, R.id.bnd2020_container_controlCard_21, true);
        item22 = new Item(this, R.id.bnd2020_container_controlCard_22, true);
        item23 = new Item(this, R.id.bnd2020_container_controlCard_23, true);
        item24 = new Item(this, R.id.bnd2020_container_controlCard_24, true);
//        item25 = new Item(this, R.id.bnd2020_container_controlCard_25, false);
//        item26 = new Item(this, R.id.bnd2020_container_controlCard_26, false);
//        item27 = new Item(this, R.id.bnd2020_container_controlCard_27, false);
//        item28 = new Item(this, R.id.bnd2020_container_controlCard_28, false);
//        item29 = new Item(this, R.id.bnd2020_container_controlCard_29, false);
//        item30 = new Item(this, R.id.bnd2020_container_controlCard_30, false);
//        item31 = new Item(this, R.id.bnd2020_container_controlCard_31, false);
//        item32 = new Item(this, R.id.bnd2020_container_controlCard_32, false);
        item33 = new Item(this, R.id.bnd2020_container_controlCard_33, true);
        item34 = new Item(this, R.id.bnd2020_container_controlCard_34, true);
        item35 = new Item(this, R.id.bnd2020_container_controlCard_35, true);
        item36 = new Item(this, R.id.bnd2020_container_controlCard_36, true);
        item37 = new Item(this, R.id.bnd2020_container_controlCard_37, true);
        item38 = new Item(this, R.id.bnd2020_container_controlCard_38, true);
        item39 = new Item(this, R.id.bnd2020_container_controlCard_39, true);
        item40 = new Item(this, R.id.bnd2020_container_controlCard_40, true);
        item41 = new Item(this, R.id.bnd2020_container_controlCard_41, true);
        item42 = new Item(this, R.id.bnd2020_container_controlCard_42, true);
        item43 = new Item(this, R.id.bnd2020_container_controlCard_43, true);
        item44 = new Item(this, R.id.bnd2020_container_controlCard_44, true);
        item45 = new Item(this, R.id.bnd2020_container_controlCard_45, true);
        item46 = new Item(this, R.id.bnd2020_container_controlCard_46, true);
        item47 = new Item(this, R.id.bnd2020_container_controlCard_47, true);
        item48 = new Item(this, R.id.bnd2020_container_controlCard_48, true);
        item49 = new Item(this, R.id.bnd2020_container_controlCard_49, true);
        item50 = new Item(this, R.id.bnd2020_container_controlCard_50, true);
        item51 = new Item(this, R.id.bnd2020_container_controlCard_51, true);
        item52 = new Item(this, R.id.bnd2020_container_controlCard_52, true);
        item53 = new Item(this, R.id.bnd2020_container_controlCard_53, true);
        item54 = new Item(this, R.id.bnd2020_container_controlCard_54, true);
        item55 = new Item(this, R.id.bnd2020_container_controlCard_55, true);
        item56 = new Item(this, R.id.bnd2020_container_controlCard_56, true);
        item57 = new Item(this, R.id.bnd2020_container_controlCard_57, true);
        item58 = new Item(this, R.id.bnd2020_container_controlCard_58, true);
//        item59 = new Item(this, R.id.bnd2020_container_controlCard_59, true);
        item60 = new Item(this, R.id.bnd2020_container_controlCard_60, true);
        item61 = new Item(this, R.id.bnd2020_container_controlCard_61, true);
        item62 = new Item(this, R.id.bnd2020_container_controlCard_62, true);
        item63 = new Item(this, R.id.bnd2020_container_controlCard_63, true);
        item64 = new Item(this, R.id.bnd2020_container_controlCard_64, true);
        item65 = new Item(this, R.id.bnd2020_container_controlCard_65, true);
        item66 = new Item(this, R.id.bnd2020_container_controlCard_66, true);

        item67 = new Item(this, R.id.bnd2020_container_controlCard_67, true);
        item68 = new Item(this, R.id.bnd2020_container_controlCard_68, true);
        item69 = new Item(this, R.id.bnd2020_container_controlCard_69, true);
        item70 = new Item(this, R.id.bnd2020_container_controlCard_70, true);
        item71 = new Item(this, R.id.bnd2020_container_controlCard_71, true);
        item72 = new Item(this, R.id.bnd2020_container_controlCard_72, true);
        item73 = new Item(this, R.id.bnd2020_container_controlCard_73, true);
        item74 = new Item(this, R.id.bnd2020_container_controlCard_74, true);

        item101 = new Item(this, R.id.bnd2020_container_controlCard_101, true);
        item102 = new Item(this, R.id.bnd2020_container_controlCard_102, true);
        item103 = new Item(this, R.id.bnd2020_container_controlCard_103, true);
        item104 = new Item(this, R.id.bnd2020_container_controlCard_104, true);
        item105 = new Item(this, R.id.bnd2020_container_controlCard_105, true);
        item106 = new Item(this, R.id.bnd2020_container_controlCard_106, true);
        item107 = new Item(this, R.id.bnd2020_container_controlCard_107, true);
        item108 = new Item(this, R.id.bnd2020_container_controlCard_108, true);
        item109 = new Item(this, R.id.bnd2020_container_controlCard_109, true);
        item110 = new Item(this, R.id.bnd2020_container_controlCard_110, true);
        item111 = new Item(this, R.id.bnd2020_container_controlCard_111, true);
        item112 = new Item(this, R.id.bnd2020_container_controlCard_112, true);
        item113 = new Item(this, R.id.bnd2020_container_controlCard_113, true);
        item114 = new Item(this, R.id.bnd2020_container_controlCard_114, true);
        item115 = new Item(this, R.id.bnd2020_container_controlCard_115, true);
        item116 = new Item(this, R.id.bnd2020_container_controlCard_116, true);
        item117 = new Item(this, R.id.bnd2020_container_controlCard_117, true);

    }

    @Override
    public void initializeSummary(){
        containers.addItem(1, item1);
        containers.addItem(2, item2);
        containers.addItem(3, item3);
        containers.addItem(4, item4);
//        containers.addItem(5, item5);
        containers.addItem(6, item6);
        containers.addItem(7, item7);
        containers.addItem(8, item8);
        containers.addItem(9, item9);
        containers.addItem(10, item10);
        containers.addItem(11, item11);
        containers.addItem(12, item12);
        containers.addItem(13, item13);
        containers.addItem(14, item14);
        containers.addItem(15, item15);
        containers.addItem(16, item16);
        containers.addItem(17, item17);
        containers.addItem(18, item18);
        containers.addItem(19, item19);
        containers.addItem(20, item20);
        containers.addItem(21, item21);
        containers.addItem(22, item22);
        containers.addItem(23, item23);
        containers.addItem(24, item24);
//        containers.addItem(25, item25);
//        containers.addItem(26, item26);
//        containers.addItem(27, item27);
//        containers.addItem(28, item28);
//        containers.addItem(29, item29);
//        containers.addItem(30, item30);
//        containers.addItem(31, item31);
//        containers.addItem(32, item32);
        containers.addItem(33, item33);
        containers.addItem(34, item34);
        containers.addItem(35, item35);
        containers.addItem(36, item36);
        containers.addItem(37, item37);
        containers.addItem(38, item38);
        containers.addItem(39, item39);
        containers.addItem(40, item40);
        containers.addItem(41, item41);
        containers.addItem(42, item42);
        containers.addItem(43, item43);
        containers.addItem(44, item44);
        containers.addItem(45, item45);
        containers.addItem(46, item46);
        containers.addItem(47, item47);
        containers.addItem(48, item48);
        containers.addItem(49, item49);
        containers.addItem(50, item50);
        containers.addItem(51, item51);
        containers.addItem(52, item52);
        containers.addItem(53, item53);
        containers.addItem(54, item54);
        containers.addItem(55, item55);
        containers.addItem(56, item56);
        containers.addItem(57, item57);
        containers.addItem(58, item58);
//        containers.addItem(59, item59);
        containers.addItem(60, item60);
        containers.addItem(61, item61);
        containers.addItem(62, item62);
        containers.addItem(63, item63);
        containers.addItem(64, item64);
        containers.addItem(65, item65);
        containers.addItem(66, item66);

        containers.addItem(67, item67);
        containers.addItem(68, item68);
        containers.addItem(69, item69);
        containers.addItem(70, item70);
        containers.addItem(71, item71);
        containers.addItem(72, item72);
        containers.addItem(73, item73);
        containers.addItem(74, item74);
    }

    @Override
    public void initializeListeners(){
        TextViewHandler.setStartActivityListener(this, this, item2, TextViewHandler.DESTINATION_DATA);
        TextViewHandler.setStartActivityListener(this, this, item3, TextViewHandler.DESTINATION_SPEC_NKO);

        RadioGroupHandler.setActionSingleHiddenPositive(this, item14, item15, new String[]{"Не соответствует"});
        RadioGroupHandler.setActionSingleHiddenPositive(this, item18, item19, new String[]{"Другое"});
        RadioGroupHandler.setActionSingleHiddenPositive(this, item21, item22, new String[]{"Есть"});
        RadioGroupHandler.setActionSingleHiddenPositive(this, item34, item35, new String[]{"Есть"});
        RadioGroupHandler.setActionSingleHiddenPositive(this, item36, item37, new String[]{"Есть"});
        RadioGroupHandler.setActionSingleHiddenPositive(this, item38, item39, new String[]{"Есть"});
        RadioGroupHandler.setActionSingleHiddenPositive(this, item40, item41, new String[]{"Есть"});
        RadioGroupHandler.setActionSingleHiddenPositive(this, item42, item43, new String[]{"Есть"});

        RadioGroupHandler.setActionShowDialog(this, R.id.bnd2020_container_controlCard_16_1, R.drawable.conteiner_on_the_ground);
        RadioGroupHandler.setActionShowDialog(this, R.id.bnd2020_container_controlCard_16_2, R.drawable.container_rack_supports);
        RadioGroupHandler.setActionShowDialog(this, R.id.bnd2020_container_controlCard_16_3, R.drawable.container_paw_supports);
        RadioGroupHandler.setActionShowDialog(this, R.id.bnd2020_container_controlCard_16_4, R.drawable.container_saddle_supports);
        RadioGroupHandler.setActionShowDialog(this, R.id.bnd2020_container_controlCard_16_5, R.drawable.container_metal_supporting_structure);
        RadioGroupHandler.setActionShowDialog(this, R.id.bnd2020_container_controlCard_16_6, R.drawable.container_conical_support);
        RadioGroupHandler.setActionShowDialog(this, R.id.bnd2020_container_controlCard_16_7, R.drawable.container_cylindrical_support);
        RadioGroupHandler.setActionShowDialog(this, R.id.bnd2020_container_controlCard_16_8, R.drawable.container_ring_supports);

        RadioGroupHandler.setActionSingleHiddenPositive(this, item44, item45, new String[]{"Есть"});

        Item[] items = new Item[]{item102, item104, item106, item108, item110, item112, item114, item116};
        ButtonHandler.setActionAddItemToUI(item101, true, items);

        ButtonHandler.setActionSingleHidden(item103, item102);
        ButtonHandler.setActionSingleHidden(item105, item104);
        ButtonHandler.setActionSingleHidden(item107, item106);
        ButtonHandler.setActionSingleHidden(item109, item108);
        ButtonHandler.setActionSingleHidden(item111, item110);
        ButtonHandler.setActionSingleHidden(item113, item112);
        ButtonHandler.setActionSingleHidden(item115, item114);
        ButtonHandler.setActionSingleHidden(item117, item116);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String exp = data.getStringExtra("select");
            ((TextView) TextViewHandler.variableView).setText(exp);
        }
    }
}
