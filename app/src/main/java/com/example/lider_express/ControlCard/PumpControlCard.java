package com.example.lider_express.ControlCard;

import android.content.Intent;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import android.widget.LinearLayout;

import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.lider_express.DataBase.DatabaseHelper;
import com.example.lider_express.MainActivity;
import com.example.lider_express.R;
import com.example.lider_express.Shared;
import com.example.lider_express.Svodnaya.Summary;

/**
 * @author LeStat
 * ДОКУМЕНТАЦИЮ
 * ПО ИСПОЛЬЗОВАНИЮ ХЕЛПЕРА SUMMARY
 * И РЕКОМАНДАЦИЮ ПО ИМЕНОВАНИЮ
 * ВЬШЕК ОПИСАНО В КЛАССЕ SUMMARY
 */

public class PumpControlCard extends AppCompatActivity {

    public static final String POSITION = "Position";
    public DatabaseHelper mDBHelper;
    public SQLiteDatabase mDb;

    /**
     * ОТДЕЛЬНЫЕ ЭЛЕМЕНТЫ XML НЕ ИНДЕКСИРОВАННЫЕ
     */
    private Summary bnd;
    private Summary pump;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bnd_2020_pump_control_card);
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

        /**
         * ПОДГОТАВЛИВАЕМ ДАННЫЕ ДЛЯ BND СВОДНОЙ
         * 1) Инициализируем конструктор
         * 2) добавляем вьюхи
         * 3) делаем чеки по базе
         * 4) инициализируем листенеры
         * 5) Делаем кастомные Чекеры и Листенеры
         */
        bnd = new Summary(this, this, Shared.nameBND2020,
                Shared.nameDefectBND2020, mDb, position);

        /** Пример с TextView и Типом Выбора*/

        /**

        bnd.addTextView(27, bnd.TYPE_DATA, R.id.bnd_2020_pump_from_bnd_2020_id_27);
        bnd.addTextView(27, bnd.TYPE_EXP_JOURNAL, R.id.bnd_2020_pump_from_bnd_2020_id_28);
        bnd.addTextView(29, bnd.TYPE_DATA, R.id.bnd_2020_pump_from_bnd_2020_id_29);
        bnd.addTextView(30, bnd.TYPE_SPEC_JOURNAL, R.id.bnd_2020_pump_from_bnd_2020_id_30);

        bnd.addEditText(49, R.id.bnd_2020_pump_from_bnd_2020_id_49);
        bnd.addRadioGroup(44, R.id.bnd_2020_pump_from_bnd_2020_id_44,
                R.id.bnd_2020_pump_from_bnd_2020_id_44_1, R.id.bnd_2020_pump_from_bnd_2020_id_44_2);
        bnd.addRadioGroup(44, R.id.bnd_2020_pump_from_bnd_2020_id_45,
                R.id.bnd_2020_pump_from_bnd_2020_id_45_1, R.id.bnd_2020_pump_from_bnd_2020_id_45_2);

        bnd.addTextView(37, bnd.TYPE_DATA, R.id.bnd_2020_pump_from_bnd_2020_id_37);
        bnd.addTextView(38, bnd.TYPE_DATA, R.id.bnd_2020_pump_from_bnd_2020_id_38);

        //  Чекаем данные из базы
        bnd.checkData();

        //  Делаем листенеры
        bnd.initListener();
         */

        /** КАСТОМНЫЙ ЧЕКЕР ПРИМЕР!
        // берем данные из Cursor из Summary из нужной колонки
        String data44 = bnd.getCursor().getString(44);
        // Инициализируемся для элемента, который нужно чекнуть
        RadioGroup rb44 = (RadioGroup) bnd.getView(44);
        // Далее проверяем, есть ли в колонке данные
        // если есть, то проверям форму записи - Да, или Нет
        // Если ни то, не другое, значит там была записана дата
        // Обратить внимание как достали RadioButton
        // Есть метод, который возвращает ArrayList<View> Из хеша
        // Из этого листа берем вьюшки под индексами 1 и 2
        // что соответсвует радиокнопкам 1 и 2
        if (data44.length() > 1) {
            if (data44 == ((RadioButton) bnd.getListView(44).get(1)).getText().toString()) {
                rb44.check(bnd.getListView(44).get(1).getId());
            } else if (data44 == ((RadioButton) bnd.getListView(44).get(2)).getText().toString()) {
                rb44.check(bnd.getListView(44).get(2).getId());
            } else {
                rb44.check(bnd.getListView(44).get(1).getId());
            }
        }

        // Все тоже самое что и сверху, только для 45 столбца
        String data45 = bnd.getCursor().getString(45);
        RadioGroup rb45 = (RadioGroup) bnd.getView(45);
        if (data45.length() > 1) {
            if (data45 == ((RadioButton) bnd.getListView(45).get(1)).getText().toString()) {
                rb45.check(bnd.getListView(45).get(1).getId());
            } else if (data45 == ((RadioButton) bnd.getListView(45).get(2)).getText().toString()) {
                rb45.check(bnd.getListView(45).get(2).getId());
            } else {
                rb45.check(bnd.getListView(45).get(1).getId());
            }
        }

         **/
        // Далее определяем лейоты, которые должны скрываться
        final LinearLayout layoutHidden1 = findViewById(R.id.bnd_2020_pump_layout_hidden_1);
        final LinearLayout layoutHidden2 = findViewById(R.id.bnd_2020_pump_layout_hidden_2);
        final LinearLayout layoutHidden3 = findViewById(R.id.bnd_2020_pump_layout_hidden_3);
        RadioGroup radioGroupHidden1 = findViewById(R.id.bnd_2020_pump_radio_group_hidden_1);
        // И устанаваливаем кастомные Листенеры
        // ОБРАТИТЬ ВНИМАНИЕ!!!
        // АКТИВНЫМ БУДЕТ ТОТ ЛИСТЕНЕР, КОТОРЫЙ
        // ИНИЦИАЛИЗИРОВАН ПОСЛЕДНИМ!!!
        // Поэтому можно лекго переопредялть
        // листены по свеому желанию
        // Класс Summary позволяет делать все
        // необходимые действия
        radioGroupHidden1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.bnd_2020_pump_radio_group_hidden_1_button_1:
                        layoutHidden1.setVisibility(View.GONE);
                        layoutHidden3.setVisibility(View.VISIBLE);
                        bnd.addTextView(46, bnd.TYPE_DATA, R.id.bnd_2020_pump_from_bnd_2020_id_46_42);
                        bnd.addTextView(47, bnd.TYPE_SPEC_NKO, R.id.bnd_2020_pump_from_bnd_2020_id_47_43);
                        bnd.removeView(42);
                        bnd.removeView(43);
                        break;
                    case R.id.bnd_2020_pump_radio_group_hidden_1_button_2:
                        layoutHidden1.setVisibility(View.VISIBLE);
                        layoutHidden3.setVisibility(View.GONE);
                        bnd.addTextView(42, bnd.TYPE_DATA, R.id.bnd_2020_pump_from_bnd_2020_id_46_42);
                        bnd.addTextView(43, bnd.TYPE_SPEC_NKO, R.id.bnd_2020_pump_from_bnd_2020_id_47_43);
                        bnd.removeView(46);
                        bnd.removeView(47);
                        break;
                }
            }
        });

        RadioGroup radioGroupHidden2 = findViewById(R.id.bnd_2020_pump_radio_group_hidden_2);
        radioGroupHidden2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.bnd_2020_pump_radio_group_hidden_2_button_1:
                        layoutHidden2.setVisibility(View.GONE);
                        break;
                    case R.id.bnd_2020_pump_radio_group_hidden_2_button_2:
                        layoutHidden2.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });


        /**
         * ПОДГОТАВЛИВАЕМ ДАННЫЕ ДЛЯ КАРТЫ КОНТРОЛЯ PUMP
         * 1) Инициализируем конструктор
         * 2) добавляем вьюхи
         * 3) делаем чеки по базе
         * 4) инициализируем листенеры

        pump = new Summary(this, this, Shared.nameBashneft2020_Nasos,
                Shared.nameDefectBashneft2020_Nasos, mDb, position);

        pump.addTextView(15, bnd.TYPE_DATA, R.id.bnd_2020_pump_id_15);
        pump.addTextView(7, bnd.TYPE_EXP_JOURNAL, R.id.bnd_2020_pump_id_7);
        pump.addTextView(10, bnd.TYPE_DATA, R.id.bnd_2020_pump_id_10);
        pump.addTextView(12, bnd.TYPE_SPEC_JOURNAL, R.id.bnd_2020_pump_id_12);

        pump.addRadioGroup(43, R.id.bnd_2020_pump_id_43,
                R.id.bnd_2020_pump_id_43_1, R.id.bnd_2020_pump_id_43_2);
        pump.addRadioGroup(44, R.id.bnd_2020_pump_id_44,
                R.id.bnd_2020_pump_id_44_1, R.id.bnd_2020_pump_id_44_2);
        pump.addRadioGroup(45, R.id.bnd_2020_pump_id_45,
                R.id.bnd_2020_pump_id_45_1, R.id.bnd_2020_pump_id_45_2);
        pump.addRadioGroup(47, R.id.bnd_2020_pump_id_47,
                R.id.bnd_2020_pump_id_47_1, R.id.bnd_2020_pump_id_47_2);
        pump.addRadioGroup(46, R.id.bnd_2020_pump_id_46,
                R.id.bnd_2020_pump_id_46_1, R.id.bnd_2020_pump_id_46_2, R.id.bnd_2020_pump_id_46_3);
        pump.addRadioGroup(48, R.id.bnd_2020_pump_id_48,
                R.id.bnd_2020_pump_id_48_1, R.id.bnd_2020_pump_id_48_2);
        pump.addRadioGroup(49, R.id.bnd_2020_pump_id_48,
                R.id.bnd_2020_pump_id_49_1, R.id.bnd_2020_pump_id_49_2);

        pump.addEditText(39, R.id.bnd_2020_pump_id_39);
        pump.addEditText(40, R.id.bnd_2020_pump_id_40);
        pump.addEditText(41, R.id.bnd_2020_pump_id_41);
        pump.addEditText(42, R.id.bnd_2020_pump_id_42);

        pump.addEditText(57, R.id.bnd_2020_pump_id_57);
        pump.addEditText(58, R.id.bnd_2020_pump_id_58);
        pump.addEditText(59, R.id.bnd_2020_pump_id_59);
        pump.addEditText(60, R.id.bnd_2020_pump_id_60);
        pump.addEditText(61, R.id.bnd_2020_pump_id_61);
        pump.addEditText(62, R.id.bnd_2020_pump_id_62);
        pump.addEditText(63, R.id.bnd_2020_pump_id_63);
        pump.addEditText(64, R.id.bnd_2020_pump_id_64);
        pump.addEditText(65, R.id.bnd_2020_pump_id_65);
        pump.addEditText(66, R.id.bnd_2020_pump_id_66);
        pump.addEditText(67, R.id.bnd_2020_pump_id_67);
        pump.addEditText(68, R.id.bnd_2020_pump_id_68);

        // pump.addEditText(69, R.id.bnd_2020_pump_id_69);

        //  Чекаем данные из базы
        pump.checkData();

        //  Делаем листенеры
        pump.initListener();


        /**
         * КОГДА СДЕЛАЛИ ЧЕКЕРЫ И ИНИЦИАЛИЗАЦИЮ
         * ПО ВСЕМ ОБЬЕКТАМ
         * НАДО СОБРАТЬ ДАННЫЕ СО ВСЕХ ВЬШЕК
         * И ОТПРАВИТЬ ИХ
         * ДЕЛАЕМ ЭТО В ЛИСТЕНЕРЕ
         * КНОПКИ SAVE
         

        //  Находим нашу заветную кнопочку Save
        Button save = findViewById(R.id.bnd_2020_pump_save);

        //  Листенер на нашу кнопочку
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Собираем данные от вьюшек
                bnd.collectData();
                pump.collectData();
                //  Сохраняем данные в базу
                bnd.saveData();
                pump.saveData();
                // Уходим на главную активити
                // Да все так просто ! =)
                Intent IntentSittings = new Intent(PumpControlCard.this, MainActivity.class);
                startActivity(IntentSittings);
            }
        });
           */
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String exp = data.getStringExtra("select");
            // Сюда вью
            // которую выбрали
            // когда переходи в активити со списком экспертов
            ((TextView) bnd.getVariableView()).setText(exp);
        }
    }


}