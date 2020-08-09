package com.example.lider_express.Tools.BND;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import android.widget.ListAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;

import com.example.lider_express.Core.DefectTree.DataList;
import com.example.lider_express.Core.DefectTree.CustomListAdapter;
import com.example.lider_express.Core.Handlers.RadioGroupHandler;
import com.example.lider_express.Core.Handlers.TextViewHandler;
import com.example.lider_express.Core.Item;
import com.example.lider_express.DataBase.DatabaseHelper;
import com.example.lider_express.MainActivity;
import com.example.lider_express.R;
import com.example.lider_express.Shared;
import com.example.lider_express.Core.Summary.Summary;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author LeStat
 * ДОКУМЕНТАЦИЮ
 * ПО ИСПОЛЬЗОВАНИЮ ХЕЛПЕРА SUMMARY
 * И РЕКОМАНДАЦИЮ ПО ИМЕНОВАНИЮ
 * ВЬЮШЕК ОПИСАНО В КЛАССЕ SUMMARY
 */

public class PumpControlCard extends AppCompatActivity {

    public static final String POSITION = "Position";
    public DatabaseHelper mDBHelper;
    public SQLiteDatabase mDb;
    public DataList dataList;

    /**
     * ОТДЕЛЬНЫЕ ЭЛЕМЕНТЫ XML НЕ ИНДЕКСИРОВАННЫЕ
     */
    private Summary bnd;
    private Summary pump;
    private TextView quantification;
    private ImageButton imageButton;
    private Dialog tableDialog;


    @SuppressLint("ClickableViewAccessibility")
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

        bnd.addTextView(46, bnd.TYPE_DATA, R.id.bnd_2020_pump_from_bnd_2020_id_46_42);
        bnd.addTextView(47, bnd.TYPE_SPEC_NKO, R.id.bnd_2020_pump_from_bnd_2020_id_47_43);

        bnd.addTextView(27, bnd.TYPE_DATA, R.id.bnd_2020_pump_from_bnd_2020_id_27);
        bnd.addTextView(28, bnd.TYPE_EXP_JOURNAL, R.id.bnd_2020_pump_from_bnd_2020_id_28);
        bnd.addTextView(29, bnd.TYPE_DATA, R.id.bnd_2020_pump_from_bnd_2020_id_29);
        bnd.addTextView(30, bnd.TYPE_SPEC_JOURNAL, R.id.bnd_2020_pump_from_bnd_2020_id_30);

        bnd.addEditText(49, R.id.bnd_2020_pump_from_bnd_2020_id_49);
        bnd.addRadioGroup(44, R.id.bnd_2020_pump_from_bnd_2020_id_44,
                R.id.bnd_2020_pump_from_bnd_2020_id_44_1, R.id.bnd_2020_pump_from_bnd_2020_id_44_2);
        bnd.addRadioGroup(44, R.id.bnd_2020_pump_from_bnd_2020_id_45,
                R.id.bnd_2020_pump_from_bnd_2020_id_45_1, R.id.bnd_2020_pump_from_bnd_2020_id_45_2);

        bnd.addTextView(37, bnd.TYPE_DATA, R.id.bnd_2020_pump_from_bnd_2020_id_37);
        bnd.addTextView(38, bnd.TYPE_DATA, R.id.bnd_2020_pump_from_bnd_2020_id_38);

        bnd.addRadioGroup(51, R.id.bnd_2020_pump_from_bnd_2020_id_51,
                R.id.bnd_2020_pump_from_bnd_2020_id_51_1, R.id.bnd_2020_pump_from_bnd_2020_id_51_2);

        bnd.addTextView(15, bnd.TYPE_EDIT_TEXT, R.id.bnd_2020_pump_id_15);
        bnd.addTextView(7, bnd.TYPE_EDIT_TEXT, R.id.bnd_2020_pump_id_7);
        bnd.addTextView(10, bnd.TYPE_EDIT_TEXT, R.id.bnd_2020_pump_id_10);
        bnd.addTextView(12, bnd.TYPE_EDIT_TEXT, R.id.bnd_2020_pump_id_12);


        //  Чекаем данные из базы
        bnd.checkData();

        //  Делаем листенеры
        bnd.initListener();

        /** КАСТОМНЫЙ ЧЕКЕР ПРИМЕР! */
        // берем данные из Cursor из Summary из нужной колонки
        String data44 = bnd.getCursor().getString(44);
        // Инициализируемся для элемента, который нужно чекнуть
        RadioGroup rb44 = (RadioGroup) bnd.getView(44);
        // Далее проверяем, есть ли в колонке данные
        // если есть, то проверям форму записи - Да, или Нет
        // Если ни то, ни другое, значит там была записана дата
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
        // листенеры по свеому желанию
        radioGroupHidden1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.bnd_2020_pump_radio_group_hidden_1_button_1:
                        layoutHidden1.setVisibility(View.GONE);
                        layoutHidden3.setVisibility(View.VISIBLE);
                        bnd.overrideId(42, 46);
                        bnd.overrideId(43, 47);
                        Log.e("RADIO ___ 2", "1");
                        break;
                    case R.id.bnd_2020_pump_radio_group_hidden_1_button_2:
                        layoutHidden1.setVisibility(View.VISIBLE);
                        layoutHidden3.setVisibility(View.GONE);
                        bnd.overrideId(46, 42);
                        bnd.overrideId(47, 43);
                        Log.e("RADIO ___ 2", "2");
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
                        layoutHidden2.setVisibility(View.VISIBLE);
                        break;
                    case R.id.bnd_2020_pump_radio_group_hidden_2_button_2:
                        layoutHidden2.setVisibility(View.GONE);
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
         */

        pump = new Summary(this, this, Shared.nameBashneft2020_Nasos,
                Shared.nameDefectBashneft2020_Nasos, mDb, position);

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
        pump.addRadioGroup(57, R.id.bnd_2020_pump_id_57,
                R.id.bnd_2020_pump_id_57_1, R.id.bnd_2020_pump_id_57_2);
        pump.addRadioGroup(58, R.id.bnd_2020_pump_id_58,
                R.id.bnd_2020_pump_id_58_1, R.id.bnd_2020_pump_id_58_2);

        pump.addEditText(39, R.id.bnd_2020_pump_id_39);
        pump.addEditText(40, R.id.bnd_2020_pump_id_40);
        pump.addEditText(41, R.id.bnd_2020_pump_id_41);
        pump.addEditText(42, R.id.bnd_2020_pump_id_42);

        pump.addEditText(71, R.id.bnd_2020_pump_id_71);
        pump.addEditText(72, R.id.bnd_2020_pump_id_72);
        pump.addEditText(73, R.id.bnd_2020_pump_id_73);
        pump.addEditText(74, R.id.bnd_2020_pump_id_74);
        pump.addEditText(75, R.id.bnd_2020_pump_id_75);
        pump.addEditText(76, R.id.bnd_2020_pump_id_76);
        pump.addEditText(77, R.id.bnd_2020_pump_id_77);
        pump.addEditText(78, R.id.bnd_2020_pump_id_78);
        pump.addEditText(79, R.id.bnd_2020_pump_id_79);
        pump.addEditText(80, R.id.bnd_2020_pump_id_80);
        pump.addEditText(81, R.id.bnd_2020_pump_id_81);
        pump.addEditText(82, R.id.bnd_2020_pump_id_82);

        // TextView колличественная оценка
        quantification = findViewById(R.id.bnd_2020_pump_id_69);
        // Групповой Листенер для замеров и оценки тех сост. агрегата
        int[] idColumns = new int[]{71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82};
        pump.setGroupTextChangedListener(idColumns, listenerVibration(pump));

        // Чекаем данные из базы
        pump.checkData();

        //  Делаем листенеры
        pump.initListener();

        // Устанавливаем колличественную оценку
        setQuantification(pump);

        /**
         * КОГДА СДЕЛАЛИ ЧЕКЕРЫ И ИНИЦИАЛИЗАЦИЮ
         * ПО ВСЕМ ОБЬЕКТАМ
         * НАДО СОБРАТЬ ДАННЫЕ СО ВСЕХ ВЬШЕК
         * И ОТПРАВИТЬ ИХ
         * ДЕЛАЕМ ЭТО В ЛИСТЕНЕРЕ
         * КНОПКИ SAVE
         *
         * */

        // Информационная табличка
        tableDialog = new Dialog(PumpControlCard.this, R.style.TableDialogStyle);
        tableDialog.setContentView(R.layout.table_dialog);
        imageButton = findViewById(R.id.bnd_2020_pump_info_table);
        imageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                tableDialog.show();
            }
        });

        Item item101 = new Item(this, R.id.bnd2020_pump_defectTree_101, false);
        Item item102 = new Item(this, R.id.bnd2020_pump_defectTree_102, false, View.GONE);
        RadioGroupHandler.setActionSingleHidden(this, item101, item102, new String[]{"Нет"});

        Item item103 = new Item(this, R.id.bnd2020_pump_defectTree_103, false);
        Item item104 = new Item(this, R.id.bnd2020_pump_defectTree_104, false, View.GONE);
        TextViewHandler.setActionSingleHidden(item103, item104);

//        Log.i("PARAMS-", "-Params");
//        HashMap<String, String> params = item103.params();
//        for(String key: params.keySet()){
//            Log.i("Message", key + ": " +params.get(key));
//        }

//        Item mehDefects = new Item(this, R.id.bnd_2020_pump_control_id_101, false, "bnd2020", "defectTree");
//        Item mehDefectsLayoutHidden = new Item(this, R.id.bnd_2020_pump_control_hidden_101, false, "bnd2020", "defectTree");
//        RadioGroupHandler.setActionSingleHidden(this, mehDefects, mehDefectsLayoutHidden, new String[]{"Нет"});















        /**  ---------------  ВРЕМЕННО --------------- **/
        /**  ---------------  ВРЕМЕННО --------------- **/
        /**  ---------------  ВРЕМЕННО --------------- **/



        final ExpandableListView expListView = (ExpandableListView) findViewById(R.id.bnd_2020_pump_control_hidden_101_list_view);
        // ImageButton
        ImageButton imageButtonAdd = findViewById(R.id.bnd_2020_pump_control_hidden_101_button_add);

        //DataList
        final List<String> expListTitle = new ArrayList<>();
        dataList = new DataList();

        // Dialog
        final Dialog defectTreeDialog = new Dialog(PumpControlCard.this, R.style.TableDialogStyle);
        defectTreeDialog.setContentView(R.layout.defect_tree_dialog);

        final String[] defect_tree_array_meh_defect = new String[]{"Скол", "Задир", "Трещина", "Риска"};

        // adapter
        final ArrayAdapter<String> defect_tree_meh_defect_adapter  =  new  ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, defect_tree_array_meh_defect);

        // Spinner Dialog
        final Spinner spinnerDefectTreeDialog = defectTreeDialog.findViewById(R.id.defect_tree_dialog_spinner);
        // Button Dialog
        Button buttonDefectTreeDialog = defectTreeDialog.findViewById(R.id.defect_tree_dialog_button_ok);
        // Set Adapter For Spinner
        spinnerDefectTreeDialog.setAdapter(defect_tree_meh_defect_adapter);
        //
        buttonDefectTreeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = spinnerDefectTreeDialog.getSelectedItem().toString();
                addViewToAdapter(expListView, expListTitle, title);
                defectTreeDialog.cancel();
                setListViewHeightBasedOnItems(expListView);
            }
        });

        imageButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                defectTreeDialog.show();
            }
        });

        RadioGroup radioGroup_101 = findViewById(R.id.bnd_2020_pump_control_id_101);
        final LinearLayout hidden_101 = findViewById(R.id.bnd_2020_pump_control_hidden_101);
        final LinearLayout hidden_101_linear_list_view = findViewById(R.id.bnd_2020_pump_control_hidden_101_linear_ist_view);

        radioGroup_101.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.bnd_2020_pump_control_id_101_1:
                        hidden_101.setVisibility(View.VISIBLE);
                        break;
                    case R.id.bnd_2020_pump_control_id_101_2:
                        hidden_101.setVisibility(View.GONE);
                        break;
                }
            }
        });
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                setListViewHeightBasedOnItems(expListView);
            }
        });
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                setListViewHeightBasedOnItems(expListView);
            }
        });
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                return false;
            }
        });

        RadioGroup radioGroup_102 = findViewById(R.id.bnd_2020_pump_control_id_102);
        final LinearLayout hidden_102 = findViewById(R.id.bnd_2020_pump_control_hidden_102);
        final LinearLayout hidden_102_1 = findViewById(R.id.bnd_2020_pump_control_hidden_102_1);
        final LinearLayout hidden_102_2 = findViewById(R.id.bnd_2020_pump_control_hidden_102_2);
        final LinearLayout hidden_102_3 = findViewById(R.id.bnd_2020_pump_control_hidden_102_3);
        final LinearLayout hidden_103 = findViewById(R.id.bnd_2020_pump_control_hidden_103);

        final String[] defectTreeLocalArray = new String[]{"Пятнами, более 50", "Язвенная, от 2 до 50 мм", "Точечная (Питтинг) до 2 мм",
                "Щелевая", "Сквозная", "Межкристаллитная"};

        final ArrayAdapter<String>  defectTreeLocal = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, defectTreeLocalArray);

        final Spinner spinnerKorozLocal = findViewById(R.id.bnd_2020_pump_control_hidden_102_spinner);
        spinnerKorozLocal.setAdapter(defectTreeLocal);

        spinnerKorozLocal.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(spinnerKorozLocal.getItemAtPosition(position).toString() == "Пятнами, более 50"){
                    hidden_102_1.setVisibility(View.VISIBLE);
                    hidden_102_2.setVisibility(View.GONE);
                    hidden_102_3.setVisibility(View.GONE);
                }
                if(spinnerKorozLocal.getItemAtPosition(position).toString() == "Язвенная, от 2 до 50 мм"){
                    hidden_102_1.setVisibility(View.GONE);
                    hidden_102_2.setVisibility(View.VISIBLE);
                    hidden_102_3.setVisibility(View.GONE);
                }
                if(spinnerKorozLocal.getItemAtPosition(position).toString() == "Точечная (Питтинг) до 2 мм"){
                    hidden_102_1.setVisibility(View.GONE);
                    hidden_102_2.setVisibility(View.VISIBLE);
                    hidden_102_3.setVisibility(View.GONE);
                }
                if(spinnerKorozLocal.getItemAtPosition(position).toString() == "Щелевая"){
                    hidden_102_1.setVisibility(View.VISIBLE);
                    hidden_102_2.setVisibility(View.GONE);
                    hidden_102_3.setVisibility(View.GONE);
                }
                if(spinnerKorozLocal.getItemAtPosition(position).toString() == "Сквозная"){
                    hidden_102_1.setVisibility(View.GONE);
                    hidden_102_2.setVisibility(View.GONE);
                    hidden_102_3.setVisibility(View.VISIBLE);
                }
                if(spinnerKorozLocal.getItemAtPosition(position).toString() == "Межкристаллитная"){
                    hidden_102_1.setVisibility(View.GONE);
                    hidden_102_2.setVisibility(View.GONE);
                    hidden_102_3.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });



        final String[] defectTreeSolidArray = new String[]{"Равномерная", "Неравномерная", "Избирательная"};

        final ArrayAdapter<String> defectTreeSolid  =  new  ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, defectTreeSolidArray);

        final Spinner spinnerSolid = findViewById(R.id.bnd_2020_pump_control_hidden_103_spinner);
        spinnerSolid.setAdapter(defectTreeSolid);

        radioGroup_102.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.bnd_2020_pump_control_id_102_1:
                        hidden_102.setVisibility(View.VISIBLE);
                        hidden_103.setVisibility(View.GONE);
                        break;
                    case R.id.bnd_2020_pump_control_id_102_2:
                        hidden_102.setVisibility(View.GONE);
                        hidden_103.setVisibility(View.VISIBLE);
                        break;
                    case R.id.bnd_2020_pump_control_id_102_3:
                        hidden_102.setVisibility(View.GONE);
                        hidden_103.setVisibility(View.GONE);
                        break;
                }
            }
        });


        final String[] defectTreeSoedArray = new String[]{"Включения", "Несплавление", "Непровар", "Наплыв", "Полость", "Пора", "Трещина", "Свищ"};

        final ArrayAdapter<String>  defectTreeSoed  =  new  ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, defectTreeSoedArray);

        final Spinner spinnerSoed = findViewById(R.id.bnd_2020_pump_control_spinner_104);
        final LinearLayout hidden_104_1 = findViewById(R.id.bnd_2020_pump_control_hidden_104_1);
        final LinearLayout hidden_104_2 = findViewById(R.id.bnd_2020_pump_control_hidden_104_2);

        spinnerSoed.setAdapter(defectTreeSoed);

        spinnerSoed.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(spinnerSoed.getItemAtPosition(position).toString() == "Свищ"){
                    hidden_104_1.setVisibility(View.GONE);
                    hidden_104_2.setVisibility(View.VISIBLE);
                }else{
                    hidden_104_1.setVisibility(View.VISIBLE);
                    hidden_104_2.setVisibility(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        /**  ---------------  ВРЕМЕННО --------------- **/





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

    //  Кастомный листенер для EditText
    //  Кол. оченка агрегата
    TextWatcher listenerVibration(final Summary summary) {
        TextWatcher listener = new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                setQuantification(summary);
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
    private void setQuantification(Summary summary){
        ArrayList<EditText> listEditTexts = new ArrayList<>();
        int count = 71;
        while (count < 83) {
            if (summary.getView(count).getClass() == AppCompatEditText.class) {
                listEditTexts.add((EditText) summary.getView(count));
            }
            count++;
        }
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
            quantification.setText("Хорошо");
        }
        if (maxVibration >= 4.5 && maxVibration < 7.0) {
            quantification.setText("Удовлетворительно");
        }
        if (maxVibration >= 7.0 && maxVibration <= 11.2) {
            quantification.setText("Еще допустимо");
        }
        if (maxVibration > 11.2) {
            quantification.setText("Недопустимо");
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



    // УБрать в будущем !
    // Установить высоту Листу
    public static boolean setListViewHeightBasedOnItems(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();

            // Get total height of all items.
            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, listView);
                float px = 500 * (listView.getResources().getDisplayMetrics().density);
                item.measure(View.MeasureSpec.makeMeasureSpec((int)px, View.MeasureSpec.AT_MOST), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                totalItemsHeight += item.getMeasuredHeight();
            }

            // Get total height of all item dividers.
            int totalDividersHeight = listView.getDividerHeight() *
                    (numberOfItems - 1);
            // Get padding
            int totalPadding = listView.getPaddingTop() + listView.getPaddingBottom();

            // Set list height.
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight + totalPadding;
            listView.setLayoutParams(params);
            listView.requestLayout();
            return true;

        } else {
            return false;
        }
    }

    public void addViewToAdapter(ExpandableListView listView, List<String> listTitle, String title){
        dataList.setTitle(title);
        HashMap<String, List<String>> expListDetail = dataList.loadData();
        listTitle.add(title);

        ExpandableListAdapter expListAdapter = new CustomListAdapter(this, listTitle, expListDetail);
        listView.setAdapter(expListAdapter);
    }

    public void removeViewToAdapter(ExpandableListView listView, List<String> listTitle, String title){
        dataList.setTitle(title);
        HashMap<String, List<String>> expListDetail = dataList.loadData();
        listTitle.add(title);

        ExpandableListAdapter expListAdapter = new CustomListAdapter(this, listTitle, expListDetail);
        listView.setAdapter(expListAdapter);
    }

}