package com.example.lider_express.Tools.HMMR;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;

import com.example.lider_express.Core.DefectTree.DataList;
import com.example.lider_express.Core.DefectTree.DefectTree;
import com.example.lider_express.Core.Handlers.ButtonHandler;
import com.example.lider_express.Core.Handlers.ExpandableListViewHandle;
import com.example.lider_express.Core.Handlers.RadioGroupHandler;
import com.example.lider_express.Core.Handlers.SpinnerHandler;
import com.example.lider_express.Core.Handlers.TextViewHandler;
import com.example.lider_express.Core.Item;
import com.example.lider_express.Core.Summary.Summary;
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

public class HMMRPumpControlCard extends AppCompatActivity {

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
                Shared.nameDefectHMMR_Pump, mDb, position);

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
        tableDialog = new Dialog(HMMRPumpControlCard.this, R.style.TableDialogStyle);
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

        /** ----------------  КОРПУС МЕХАНИЧЕСКИЕ ДЕФЕКТЫ ---------------------**/

        Item item103 = new Item(this, R.id.bnd2020_pump_defectTree_103, false);
        Item item104 = new Item(this, R.id.bnd2020_pump_defectTree_104, false, View.GONE);
        TextViewHandler.setActionSingleHidden(item103, item104);

        Item item105 = new Item(this, R.id.bnd2020_pump_defectTree_105, false);
        Item item106 = new Item(this, R.id.bnd2020_pump_defectTree_106, false, View.GONE);
        RadioGroupHandler.setActionSingleHidden(this, item105, item106, new String[]{"Нет"});

        DefectTree defectTree1 = new DefectTree(this, this);
        ArrayList<String> properties1 = new ArrayList<>();
        properties1.add("Продольный размер");
        properties1.add("Поперечный размер");
        properties1.add("Глубина дефекта");
        properties1.add("Привязать дефект(по вертикали)");
        properties1.add("Привязать дефект(по горизонтали)");
        defectTree1.addSingleConformity("Скол", properties1);
        defectTree1.addSingleConformity("Задир", properties1);
        defectTree1.addSingleConformity("Трещина", properties1);
        defectTree1.addSingleConformity("Риска", properties1);

        defectTree1.customizeSingleDialog();

        Item ItemSpinnerDefectTree1 = new Item(this, defectTree1.getDialogAddProperty(), R.id.defect_tree_dialog_spinner_level_1, false);
        final ArrayAdapter<String> adapterDefectTree1  =  new  ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, defectTree1.getItemsSingleBranch());
        ((Spinner) ItemSpinnerDefectTree1.getView()).setAdapter(adapterDefectTree1);

        defectTree1.setSpinnerLevel1(((Spinner) ItemSpinnerDefectTree1.getView()));

        Item item109 = new Item(this, R.id.bnd2020_pump_defectTree_109, false);
        ButtonHandler.setActionShowDialog(item109, defectTree1);

        // Exp List View Meh defects Corpus
        Item item108 = new Item(this, R.id.bnd2020_pump_defectTree_108, false);

        Item ItemButtonDefectTreeDialogDefectTree1 = new Item(this, defectTree1.getDialogAddProperty(), R.id.defect_tree_dialog_button_ok, false);

        ButtonHandler.setActionCancelSingleDialog(ItemButtonDefectTreeDialogDefectTree1, item108, defectTree1);

        ExpandableListViewHandle.setExpand(item108);
        ExpandableListViewHandle.setCollapse(item108);


        /** ----------------  КОРПУС КОРРОЗИЯ ---------------------**/
        Item item110 = new Item(this, R.id.bnd2020_pump_defectTree_110, false);
        Item item111 = new Item(this, R.id.bnd2020_pump_defectTree_111, false, View.GONE);
        RadioGroupHandler.setActionSingleHidden(this, item110, item111, new String[]{"Нет"});

        DefectTree defectTree2 = new DefectTree(this, this);
        defectTree2.addDoubleConformity("Локальная","Пятнаями, более 50 мм", properties1);

        ArrayList<String> properties2 = new ArrayList<>();
        properties2.add("Диаметр");
        properties2.add("Глубина");
        properties2.add("Привязать дефект(по вертикали)");
        properties2.add("Привязать дефект(по горизонтали)");

        defectTree2.addDoubleConformity("Локальная","Язвенная, от 2 до 50 мм", properties2);
        defectTree2.addDoubleConformity("Локальная","Точечная (Питтинг) до 2 мм", properties2);
        defectTree2.addDoubleConformity("Локальная","Щелевая", properties1);

        ArrayList<String> properties3 = new ArrayList<>();
        properties3.add("Продольный");
        properties3.add("Поперечный");
        properties3.add("Привязать дефект(по вертикали)");
        properties3.add("Привязать дефект(по горизонтали)");

        defectTree2.addDoubleConformity("Локальная","Сквозная", properties3);
        defectTree2.addDoubleConformity("Локальная","Межкристалитная", properties3);
        defectTree2.addDoubleConformity("Сплошная","Равномерная", properties1);
        defectTree2.addDoubleConformity("Сплошная","Неравномерная", properties1);
        defectTree2.addDoubleConformity("Сплошная","Избирательная", properties1);

        defectTree2.customizeDoubleDialog();

        Item ItemSpinnerDefectTree2Level1 = new Item(this, defectTree2.getDialogAddProperty(), R.id.defect_tree_dialog_spinner_level_1, false);
        Item ItemSpinnerDefectTree2Level2 = new Item(this, defectTree2.getDialogAddProperty(), R.id.defect_tree_dialog_spinner_level_2, false);

        final ArrayAdapter<String> adapterDefectTree2SpinnerLevel1  =  new  ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, defectTree2.getItemsDoubleBranchLevel1());
        ((Spinner) ItemSpinnerDefectTree2Level1.getView()).setAdapter(adapterDefectTree2SpinnerLevel1);
        defectTree2.setSpinnerLevel1(((Spinner) ItemSpinnerDefectTree2Level1.getView()));

        SpinnerHandler.setListenerDoubleSpinner(this, ItemSpinnerDefectTree2Level1, ItemSpinnerDefectTree2Level2, defectTree2);

        Item item114 = new Item(this, R.id.bnd2020_pump_defectTree_114, false);
        ButtonHandler.setActionShowDialog(item114, defectTree2);

        // Exp List View Meh defects Corpus
        Item item113 = new Item(this, R.id.bnd2020_pump_defectTree_113, false);

        Item ItemButtonDefectTreeDialogDefectTree2 = new Item(this, defectTree2.getDialogAddProperty(), R.id.defect_tree_dialog_button_ok, false);

        ButtonHandler.setActionCancelDoubleDialog(ItemButtonDefectTreeDialogDefectTree2, item113, defectTree2);

        ExpandableListViewHandle.setExpand(item113);
        ExpandableListViewHandle.setCollapse(item113);

        /** ----------------  КОРПУС СОЕДИНЕНИЯ ---------------------**/
        Item item115 = new Item(this, R.id.bnd2020_pump_defectTree_115, false);
        Item item116 = new Item(this, R.id.bnd2020_pump_defectTree_116, false, View.GONE);
        RadioGroupHandler.setActionSingleHidden(this, item115, item116, new String[]{"Нет"});

        DefectTree defectTree3 = new DefectTree(this, this);

        ArrayList<String> properties4 = new ArrayList<>();
        properties4.add("Продольный");
        properties4.add("Поперечный");
        properties4.add("Глубина");
        properties4.add("Привязать дефект(по вертикали)");
        properties4.add("Привязать дефект(по горизонтали)");

        defectTree3.addDoubleConformity("Сварка","Включения", properties4);
        defectTree3.addDoubleConformity("Сварка","Несплавление", properties4);
        defectTree3.addDoubleConformity("Сварка","Непровар", properties4);
        defectTree3.addDoubleConformity("Сварка","Наплыв", properties4);
        defectTree3.addDoubleConformity("Сварка","Полость", properties4);
        defectTree3.addDoubleConformity("Сварка","Пора", properties4);
        defectTree3.addDoubleConformity("Сварка","Трещина", properties4);

        defectTree3.addDoubleConformity("Сварка","Свищ", properties3);

        defectTree3.customizeDoubleDialog();

        Item ItemSpinnerDefectTree3Level1 = new Item(this, defectTree3.getDialogAddProperty(), R.id.defect_tree_dialog_spinner_level_1, false);
        Item ItemSpinnerDefectTree3Level2 = new Item(this, defectTree3.getDialogAddProperty(), R.id.defect_tree_dialog_spinner_level_2, false);

        final ArrayAdapter<String> adapterDefectTree3SpinnerLevel1  =  new  ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, defectTree3.getItemsDoubleBranchLevel1());
        ((Spinner) ItemSpinnerDefectTree3Level1.getView()).setAdapter(adapterDefectTree3SpinnerLevel1);
        defectTree3.setSpinnerLevel1(((Spinner) ItemSpinnerDefectTree3Level1.getView()));

        SpinnerHandler.setListenerDoubleSpinner(this, ItemSpinnerDefectTree3Level1, ItemSpinnerDefectTree3Level2, defectTree3);

        Item item119 = new Item(this, R.id.bnd2020_pump_defectTree_119, false);
        ButtonHandler.setActionShowDialog(item119, defectTree3);

        // Exp List View Meh defects Corpus
        Item item118 = new Item(this, R.id.bnd2020_pump_defectTree_118, false);

        Item ItemButtonDefectTreeDialogDefectTree3 = new Item(this, defectTree3.getDialogAddProperty(), R.id.defect_tree_dialog_button_ok, false);

        ButtonHandler.setActionCancelDoubleDialog(ItemButtonDefectTreeDialogDefectTree3, item118, defectTree3);

        ExpandableListViewHandle.setExpand(item118);
        ExpandableListViewHandle.setCollapse(item118);



        /** ----------------  МУФТА ---------------------**/

        Item item120 = new Item(this, R.id.bnd2020_pump_defectTree_120, false);
        Item item121 = new Item(this, R.id.bnd2020_pump_defectTree_121, false, View.GONE);
        TextViewHandler.setActionSingleHidden(item120, item121);

        /** ----------------  МУФТА МЕХАНИЧЕСКИЕ ДЕФЕКТЫ ---------------------**/

        Item item122 = new Item(this, R.id.bnd2020_pump_defectTree_122, false);
        Item item123 = new Item(this, R.id.bnd2020_pump_defectTree_123, false, View.GONE);
        RadioGroupHandler.setActionSingleHidden(this, item122, item123, new String[]{"Нет"});

        DefectTree defectTree4 = new DefectTree(this, this);

        defectTree4.addSingleConformity("Скол", properties1);
        defectTree4.addSingleConformity("Задир", properties1);
        defectTree4.addSingleConformity("Трещина", properties1);
        defectTree4.addSingleConformity("Риска", properties1);

        defectTree4.customizeSingleDialog();

        Item ItemSpinnerDefectTree4 = new Item(this, defectTree4.getDialogAddProperty(), R.id.defect_tree_dialog_spinner_level_1, false);
        final ArrayAdapter<String> adapterDefectTree4  =  new  ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, defectTree4.getItemsSingleBranch());
        ((Spinner) ItemSpinnerDefectTree4.getView()).setAdapter(adapterDefectTree4);

        defectTree4.setSpinnerLevel1(((Spinner) ItemSpinnerDefectTree4.getView()));

        Item item126 = new Item(this, R.id.bnd2020_pump_defectTree_126, false);
        ButtonHandler.setActionShowDialog(item126, defectTree4);

        // Exp List View Meh defects Corpus
        Item item125 = new Item(this, R.id.bnd2020_pump_defectTree_125, false);

        Item ItemButtonDefectTreeDialogDefectTree4 = new Item(this, defectTree4.getDialogAddProperty(), R.id.defect_tree_dialog_button_ok, false);

        ButtonHandler.setActionCancelSingleDialog(ItemButtonDefectTreeDialogDefectTree4, item125, defectTree4);

        ExpandableListViewHandle.setExpand(item125);
        ExpandableListViewHandle.setCollapse(item125);

        /** ----------------  МУФТА КОРРОЗИЯ ---------------------**/

        Item item127 = new Item(this, R.id.bnd2020_pump_defectTree_127, false);
        Item item128 = new Item(this, R.id.bnd2020_pump_defectTree_128, false, View.GONE);
        RadioGroupHandler.setActionSingleHidden(this, item127, item128, new String[]{"Нет"});

        DefectTree defectTree5 = new DefectTree(this, this);

        defectTree5.addDoubleConformity("Локальная","Пятнаями, более 50 мм", properties1);
        defectTree5.addDoubleConformity("Локальная","Язвенная, от 2 до 50 мм", properties2);
        defectTree5.addDoubleConformity("Локальная","Точечная (Питтинг) до 2 мм", properties2);
        defectTree5.addDoubleConformity("Локальная","Щелевая", properties1);
        defectTree5.addDoubleConformity("Локальная","Сквозная", properties3);
        defectTree5.addDoubleConformity("Сплошная","Равномерная", properties1);
        defectTree5.addDoubleConformity("Сплошная","Неравномерная", properties1);
        defectTree5.addDoubleConformity("Сплошная","Избирательная", properties1);

        defectTree5.customizeDoubleDialog();

        Item ItemSpinnerDefectTree5Level1 = new Item(this, defectTree5.getDialogAddProperty(), R.id.defect_tree_dialog_spinner_level_1, false);
        Item ItemSpinnerDefectTree5Level2 = new Item(this, defectTree5.getDialogAddProperty(), R.id.defect_tree_dialog_spinner_level_2, false);

        final ArrayAdapter<String> adapterDefectTree5SpinnerLevel1  =  new  ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, defectTree5.getItemsDoubleBranchLevel1());
        ((Spinner) ItemSpinnerDefectTree5Level1.getView()).setAdapter(adapterDefectTree5SpinnerLevel1);
        defectTree5.setSpinnerLevel1(((Spinner) ItemSpinnerDefectTree5Level1.getView()));

        SpinnerHandler.setListenerDoubleSpinner(this, ItemSpinnerDefectTree5Level1, ItemSpinnerDefectTree5Level2, defectTree5);

        Item item131 = new Item(this, R.id.bnd2020_pump_defectTree_131, false);
        ButtonHandler.setActionShowDialog(item131, defectTree5);

        // Exp List View Meh defects Corpus
        Item item130 = new Item(this, R.id.bnd2020_pump_defectTree_130, false);

        Item ItemButtonDefectTreeDialogDefectTree5 = new Item(this, defectTree5.getDialogAddProperty(), R.id.defect_tree_dialog_button_ok, false);

        ButtonHandler.setActionCancelDoubleDialog(ItemButtonDefectTreeDialogDefectTree5, item130, defectTree5);

        ExpandableListViewHandle.setExpand(item130);
        ExpandableListViewHandle.setCollapse(item130);



        /** ----------------  ДВИГАТЕЛЬ ---------------------**/

        Item item132 = new Item(this, R.id.bnd2020_pump_defectTree_132, false);
        Item item133 = new Item(this, R.id.bnd2020_pump_defectTree_133, false, View.GONE);
        TextViewHandler.setActionSingleHidden(item132, item133);

        /** ----------------  Отсутствие двигателя ---------------------**/
        Item item134 = new Item(this, R.id.bnd2020_pump_defectTree_134, false);
        Item item135 = new Item(this, R.id.bnd2020_pump_defectTree_135, false, View.GONE);
        RadioGroupHandler.setActionSingleHidden(this, item134, item135, new String[]{"Отсутствует"});

        /** ----------------  ДВИГАТЕЛЬ МЕХАНИЧЕСКИЕ ДЕФЕКТЫ ---------------------**/
        Item item136 = new Item(this, R.id.bnd2020_pump_defectTree_136, false);
        Item item137 = new Item(this, R.id.bnd2020_pump_defectTree_137, false, View.GONE);
        RadioGroupHandler.setActionSingleHidden(this, item136, item137, new String[]{"Нет"});

        DefectTree defectTree6 = new DefectTree(this, this);

        defectTree6.addSingleConformity("Скол", properties1);
        defectTree6.addSingleConformity("Задир", properties1);
        defectTree6.addSingleConformity("Трещина", properties1);
        defectTree6.addSingleConformity("Риска", properties1);

        defectTree6.customizeSingleDialog();

        Item ItemSpinnerDefectTree6 = new Item(this, defectTree6.getDialogAddProperty(), R.id.defect_tree_dialog_spinner_level_1, false);
        final ArrayAdapter<String> adapterDefectTree6  =  new  ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, defectTree6.getItemsSingleBranch());
        ((Spinner) ItemSpinnerDefectTree6.getView()).setAdapter(adapterDefectTree6);

        defectTree6.setSpinnerLevel1(((Spinner) ItemSpinnerDefectTree6.getView()));

        Item item140 = new Item(this, R.id.bnd2020_pump_defectTree_140, false);
        ButtonHandler.setActionShowDialog(item140, defectTree6);

        // Exp List View Meh defects Corpus
        Item item139 = new Item(this, R.id.bnd2020_pump_defectTree_139, false);

        Item ItemButtonDefectTreeDialogDefectTree6 = new Item(this, defectTree6.getDialogAddProperty(), R.id.defect_tree_dialog_button_ok, false);

        ButtonHandler.setActionCancelSingleDialog(ItemButtonDefectTreeDialogDefectTree6, item139, defectTree6);

        ExpandableListViewHandle.setExpand(item139);
        ExpandableListViewHandle.setCollapse(item139);

        /** ----------------  ДВИГАТЕЛЬ КОРРОЗИЯ ---------------------**/

        Item item141 = new Item(this, R.id.bnd2020_pump_defectTree_141, false);
        Item item142 = new Item(this, R.id.bnd2020_pump_defectTree_142, false, View.GONE);
        RadioGroupHandler.setActionSingleHidden(this, item141, item142, new String[]{"Нет"});

        DefectTree defectTree7 = new DefectTree(this, this);

        defectTree7.addDoubleConformity("Локальная","Пятнаями, более 50 мм", properties1);
        defectTree7.addDoubleConformity("Локальная","Язвенная, от 2 до 50 мм", properties2);
        defectTree7.addDoubleConformity("Локальная","Точечная (Питтинг) до 2 мм", properties2);
        defectTree7.addDoubleConformity("Локальная","Щелевая", properties1);
        defectTree7.addDoubleConformity("Локальная","Сквозная", properties3);
        defectTree7.addDoubleConformity("Сплошная","Равномерная", properties1);
        defectTree7.addDoubleConformity("Сплошная","Неравномерная", properties1);
        defectTree7.addDoubleConformity("Сплошная","Избирательная", properties1);

        defectTree7.customizeDoubleDialog();

        Item ItemSpinnerDefectTree7Level1 = new Item(this, defectTree7.getDialogAddProperty(), R.id.defect_tree_dialog_spinner_level_1, false);
        Item ItemSpinnerDefectTree7Level2 = new Item(this, defectTree7.getDialogAddProperty(), R.id.defect_tree_dialog_spinner_level_2, false);

        final ArrayAdapter<String> adapterDefectTree7SpinnerLevel1  =  new  ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, defectTree7.getItemsDoubleBranchLevel1());
        ((Spinner) ItemSpinnerDefectTree7Level1.getView()).setAdapter(adapterDefectTree7SpinnerLevel1);
        defectTree7.setSpinnerLevel1(((Spinner) ItemSpinnerDefectTree7Level1.getView()));

        SpinnerHandler.setListenerDoubleSpinner(this, ItemSpinnerDefectTree7Level1, ItemSpinnerDefectTree7Level2, defectTree7);

        Item item145 = new Item(this, R.id.bnd2020_pump_defectTree_145, false);
        ButtonHandler.setActionShowDialog(item145, defectTree7);

        // Exp List View Meh defects Corpus
        Item item144 = new Item(this, R.id.bnd2020_pump_defectTree_144, false);

        Item ItemButtonDefectTreeDialogDefectTree7 = new Item(this, defectTree7.getDialogAddProperty(), R.id.defect_tree_dialog_button_ok, false);

        ButtonHandler.setActionCancelDoubleDialog(ItemButtonDefectTreeDialogDefectTree7, item144, defectTree7);

        ExpandableListViewHandle.setExpand(item144);
        ExpandableListViewHandle.setCollapse(item144);




        /** ----------------  РАМПА МЕХАНИЧЕСКИЕ ДЕФЕКТЫ ---------------------**/

        Item item146 = new Item(this, R.id.bnd2020_pump_defectTree_146, false);
        Item item147 = new Item(this, R.id.bnd2020_pump_defectTree_147, false, View.GONE);
        TextViewHandler.setActionSingleHidden(item146, item147);

        Item item148 = new Item(this, R.id.bnd2020_pump_defectTree_148, false);
        Item item149 = new Item(this, R.id.bnd2020_pump_defectTree_149, false, View.GONE);
        RadioGroupHandler.setActionSingleHidden(this, item148, item149, new String[]{"Нет"});

        DefectTree defectTree8 = new DefectTree(this, this);
        defectTree8.addSingleConformity("Скол", properties1);
        defectTree8.addSingleConformity("Задир", properties1);
        defectTree8.addSingleConformity("Трещина", properties1);
        defectTree8.addSingleConformity("Риска", properties1);

        defectTree8.customizeSingleDialog();

        Item ItemSpinnerDefectTree8 = new Item(this, defectTree8.getDialogAddProperty(), R.id.defect_tree_dialog_spinner_level_1, false);
        final ArrayAdapter<String> adapterDefectTree8  =  new  ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, defectTree8.getItemsSingleBranch());
        ((Spinner) ItemSpinnerDefectTree8.getView()).setAdapter(adapterDefectTree8);

        defectTree8.setSpinnerLevel1(((Spinner) ItemSpinnerDefectTree8.getView()));

        Item item152 = new Item(this, R.id.bnd2020_pump_defectTree_152, false);
        ButtonHandler.setActionShowDialog(item152, defectTree8);

        // Exp List View Meh defects Corpus
        Item item151 = new Item(this, R.id.bnd2020_pump_defectTree_151, false);

        Item ItemButtonDefectTreeDialogDefectTree8 = new Item(this, defectTree8.getDialogAddProperty(), R.id.defect_tree_dialog_button_ok, false);

        ButtonHandler.setActionCancelSingleDialog(ItemButtonDefectTreeDialogDefectTree8, item151, defectTree8);

        ExpandableListViewHandle.setExpand(item151);
        ExpandableListViewHandle.setCollapse(item151);


        /** ----------------  РАМПА КОРРОЗИЯ ---------------------**/
        Item item153 = new Item(this, R.id.bnd2020_pump_defectTree_153, false);
        Item item154 = new Item(this, R.id.bnd2020_pump_defectTree_154, false, View.GONE);
        RadioGroupHandler.setActionSingleHidden(this, item153, item154, new String[]{"Нет"});

        DefectTree defectTree9 = new DefectTree(this, this);
        defectTree2.addDoubleConformity("Локальная","Пятнаями, более 50 мм", properties1);

        defectTree9.addDoubleConformity("Локальная","Язвенная, от 2 до 50 мм", properties2);
        defectTree9.addDoubleConformity("Локальная","Точечная (Питтинг) до 2 мм", properties2);
        defectTree9.addDoubleConformity("Локальная","Щелевая", properties1);

        defectTree9.addDoubleConformity("Локальная","Сквозная", properties3);
        defectTree9.addDoubleConformity("Локальная","Межкристалитная", properties3);
        defectTree9.addDoubleConformity("Сплошная","Равномерная", properties1);
        defectTree9.addDoubleConformity("Сплошная","Неравномерная", properties1);
        defectTree9.addDoubleConformity("Сплошная","Избирательная", properties1);

        defectTree9.customizeDoubleDialog();

        Item ItemSpinnerDefectTree9Level1 = new Item(this, defectTree9.getDialogAddProperty(), R.id.defect_tree_dialog_spinner_level_1, false);
        Item ItemSpinnerDefectTree9Level2 = new Item(this, defectTree9.getDialogAddProperty(), R.id.defect_tree_dialog_spinner_level_2, false);

        final ArrayAdapter<String> adapterDefectTree9SpinnerLevel1  =  new  ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, defectTree9.getItemsDoubleBranchLevel1());
        ((Spinner) ItemSpinnerDefectTree9Level1.getView()).setAdapter(adapterDefectTree9SpinnerLevel1);
        defectTree9.setSpinnerLevel1(((Spinner) ItemSpinnerDefectTree9Level1.getView()));

        SpinnerHandler.setListenerDoubleSpinner(this, ItemSpinnerDefectTree9Level1, ItemSpinnerDefectTree9Level2, defectTree9);

        Item item157 = new Item(this, R.id.bnd2020_pump_defectTree_157, false);
        ButtonHandler.setActionShowDialog(item157, defectTree9);

        // Exp List View Meh defects Corpus
        Item item156 = new Item(this, R.id.bnd2020_pump_defectTree_156, false);

        Item ItemButtonDefectTreeDialogDefectTree9 = new Item(this, defectTree9.getDialogAddProperty(), R.id.defect_tree_dialog_button_ok, false);

        ButtonHandler.setActionCancelDoubleDialog(ItemButtonDefectTreeDialogDefectTree9, item156, defectTree9);

        ExpandableListViewHandle.setExpand(item156);
        ExpandableListViewHandle.setCollapse(item156);

        /** ----------------  РАМПА СОЕДИНЕНИЯ ---------------------**/
        Item item158 = new Item(this, R.id.bnd2020_pump_defectTree_158, false);
        Item item159 = new Item(this, R.id.bnd2020_pump_defectTree_159, false, View.GONE);
        RadioGroupHandler.setActionSingleHidden(this, item158, item159, new String[]{"Нет"});

        DefectTree defectTree10 = new DefectTree(this, this);

        defectTree10.addDoubleConformity("Сварка","Включения", properties4);
        defectTree10.addDoubleConformity("Сварка","Несплавление", properties4);
        defectTree10.addDoubleConformity("Сварка","Непровар", properties4);
        defectTree10.addDoubleConformity("Сварка","Наплыв", properties4);
        defectTree10.addDoubleConformity("Сварка","Полость", properties4);
        defectTree10.addDoubleConformity("Сварка","Пора", properties4);
        defectTree10.addDoubleConformity("Сварка","Трещина", properties4);

        defectTree10.addDoubleConformity("Сварка","Свищ", properties3);

        defectTree10.customizeDoubleDialog();

        Item ItemSpinnerDefectTree10Level1 = new Item(this, defectTree10.getDialogAddProperty(), R.id.defect_tree_dialog_spinner_level_1, false);
        Item ItemSpinnerDefectTree10Level2 = new Item(this, defectTree10.getDialogAddProperty(), R.id.defect_tree_dialog_spinner_level_2, false);

        final ArrayAdapter<String> adapterDefectTree10SpinnerLevel1  =  new  ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, defectTree10.getItemsDoubleBranchLevel1());
        ((Spinner) ItemSpinnerDefectTree10Level1.getView()).setAdapter(adapterDefectTree10SpinnerLevel1);
        defectTree10.setSpinnerLevel1(((Spinner) ItemSpinnerDefectTree10Level1.getView()));

        SpinnerHandler.setListenerDoubleSpinner(this, ItemSpinnerDefectTree10Level1, ItemSpinnerDefectTree10Level2, defectTree10);

        Item item162 = new Item(this, R.id.bnd2020_pump_defectTree_162, false);
        ButtonHandler.setActionShowDialog(item162, defectTree10);

        // Exp List View Meh defects Corpus
        Item item161 = new Item(this, R.id.bnd2020_pump_defectTree_161, false);

        Item ItemButtonDefectTreeDialogDefectTree10 = new Item(this, defectTree10.getDialogAddProperty(), R.id.defect_tree_dialog_button_ok, false);

        ButtonHandler.setActionCancelDoubleDialog(ItemButtonDefectTreeDialogDefectTree10, item161, defectTree10);

        ExpandableListViewHandle.setExpand(item161);
        ExpandableListViewHandle.setCollapse(item161);



        /** ----------------  ОНОВАНИЕ ДЕФЕКТЫ ---------------------**/

        Item item163 = new Item(this, R.id.bnd2020_pump_defectTree_163, false);
        Item item164 = new Item(this, R.id.bnd2020_pump_defectTree_164, false, View.GONE);
        TextViewHandler.setActionSingleHidden(item163, item164);

        Item item165 = new Item(this, R.id.bnd2020_pump_defectTree_165, false);
        Item item166 = new Item(this, R.id.bnd2020_pump_defectTree_166, false, View.GONE);
        RadioGroupHandler.setActionSingleHidden(this, item165, item166, new String[]{"Нет"});

        DefectTree defectTree11 = new DefectTree(this, this);
        defectTree11.addSingleConformity("Выбоина", properties1);
        defectTree11.addSingleConformity("Трещина", properties1);

        defectTree11.customizeSingleDialog();

        Item ItemSpinnerDefectTree11 = new Item(this, defectTree11.getDialogAddProperty(), R.id.defect_tree_dialog_spinner_level_1, false);
        final ArrayAdapter<String> adapterDefectTree11  =  new  ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, defectTree11.getItemsSingleBranch());
        ((Spinner) ItemSpinnerDefectTree11.getView()).setAdapter(adapterDefectTree11);

        defectTree11.setSpinnerLevel1(((Spinner) ItemSpinnerDefectTree11.getView()));

        Item item169 = new Item(this, R.id.bnd2020_pump_defectTree_169, false);
        ButtonHandler.setActionShowDialog(item169, defectTree11);

        // Exp List View Meh defects Corpus
        Item item168 = new Item(this, R.id.bnd2020_pump_defectTree_168, false);

        Item ItemButtonDefectTreeDialogDefectTree11 = new Item(this, defectTree11.getDialogAddProperty(), R.id.defect_tree_dialog_button_ok, false);

        ButtonHandler.setActionCancelSingleDialog(ItemButtonDefectTreeDialogDefectTree11, item168, defectTree11);

        ExpandableListViewHandle.setExpand(item168);
        ExpandableListViewHandle.setCollapse(item168);




        //  Находим нашу заветную кнопочку Save
        Button save = findViewById(R.id.bnd_2020_pump_save);

        //  Листенер на нашу кнопочку
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Собираем данные от вьюшек
//                bnd.collectData();
                pump.collectData();
                //  Сохраняем данные в базу
//                bnd.saveData();
                pump.saveData();
                // Уходим на главную активити
                // Да все так просто ! =)
                Intent IntentSittings = new Intent(HMMRPumpControlCard.this, MainActivity.class);
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


}