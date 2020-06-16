package com.example.lider_express.Svodnaya;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.Layout;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.lider_express.Tools.SpisokBND;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;


/**
 * @author LeStat
 * API Для сводной БНД
 * Для использования во всех сводных
 * <p>
 * - ИНСТУКЦИЯ К ИСПОЛЬЗОВАНИЮ -
 * <p>
 * Класс с API
 * Для реализации карт контроля и сводных
 * Класс с API будет представлять из себя
 * Отдельный класс, который будет родительским
 * Для все классов Карт и Сводных
 * <p>
 * Замечания:
 * - Сделать валидации на пустые поля
 * - Дополнить/переделать выбор специалистов
 * для ФИО TextView
 * <p>
 * ОБРАТИТЬ ВНИМЕНИЕ:
 * - ВСЕ КНОПКИ(Button) ИЛИ ДРУГИЕ
 * Layouts ИСПОЛЬЗУЕМЫЕ В XML ФАЙЛЕ
 * ИНДЕКСИРОВАТЬ И
 * ОБРАБАТЫВАТЬ ОТЕДЕЛЬНО!!!
 * <p>
 * <p>
 * Рекомендации по именованию View:
 * в XML файле
 * <p>
 * - Если берем данные из базы, для которой
 * делалась XML то:
 * ------ TextView -----
 * - bnd_2020_nasos_id_7
 * ---------------- ----
 * ^              ^
 * a)             b)
 * <p>
 * С EditText точно так же
 * <p>
 */

public class Summary {

    /**
     * Constants
     */

    private Calendar dateAndTime = Calendar.getInstance();


    public static final int TYPE_DATA = 0;
    public static final int TYPE_SPEC_ALL = 1;
    // Bashneft
    public static final int TYPE_SPEC_NKO = 2;
    public static final int TYPE_SPEC_JOURNAL = 3;
    public static final int TYPE_EXP_JOURNAL = 4;

    public static final int TYPE_EDIT_TEXT = 5;
    public static final int TYPE_RADIO_GROUP = 6;
    public static final int TYPE_TEXT_VIEW = 7;

    static final private int PEOPLE = 0;

    private Context context;
    private Activity activity;
    private String nameReadDb;

    private String nameWriteDb;
    private String position;
    private LinkedHashMap<Integer, String> values;
    private LinkedHashMap<Integer, HashMap<Integer, ArrayList<View>>> views;
    private LinkedHashMap<Integer, Integer> idBundles;

    private SQLiteDatabase database;
    private Cursor cursor;

    private View variableView;


    /**
     * @param activity
     * @param context
     * @param nameReadDb
     * @param nameWriteDb
     * @param database
     * @param position
     */
    public Summary(Activity activity, Context context, @NonNull String nameReadDb, @NonNull String nameWriteDb,
                   @NonNull SQLiteDatabase database, String position) {
        this.context = context;
        this.nameReadDb = nameReadDb;
        this.nameWriteDb = nameWriteDb;
        this.database = database;
        this.activity = activity;
        this.position = position;

        cursor = database.query(nameReadDb, null, "POSITION = ?",
                new String[]{position}, null, null, null);
        cursor.moveToFirst();

        views = new LinkedHashMap<>();
        values = new LinkedHashMap<>();
        idBundles = new LinkedHashMap<>();
    }

    /**
     * @param type
     * @param idColumn
     * @param idView
     */
    public void addTextView(int idColumn, int type, int idView) {
        HashMap<Integer, ArrayList<View>> hash = new HashMap<>();
        ArrayList<View> list = new ArrayList<>();
        TextView textView = activity.findViewById(idView);
        list.add(textView);
        hash.put(type, list);
        views.put(idColumn, hash);
        values.put(idColumn, "");
    }

    /**
     * @param idColumn
     * @param idView
     */
    public void addEditText(@NonNull int idColumn, @NonNull int idView) {
        HashMap<Integer, ArrayList<View>> hash = new HashMap<>();
        ArrayList<View> list = new ArrayList<>();
        EditText editText = activity.findViewById(idView);
        list.add(editText);
        hash.put(TYPE_EDIT_TEXT, list);
        views.put(idColumn, hash);
        values.put(idColumn, "");
    }

    /**
     * @param idColumn
     * @param idRadioGroup
     * @param idButton1
     * @param idButton2
     */
    public void addRadioGroup(@NonNull int idColumn, @NonNull int idRadioGroup,
                              @NonNull int idButton1, @NonNull int idButton2) {
        HashMap<Integer, ArrayList<View>> hash = new HashMap<>();
        ArrayList<View> list = new ArrayList<>();
        RadioGroup radioGroup = activity.findViewById(idRadioGroup);
        RadioButton radioButton1 = activity.findViewById(idButton1);
        RadioButton radioButton2 = activity.findViewById(idButton2);
        list.add(radioGroup);
        list.add(radioButton1);
        list.add(radioButton2);
        hash.put(TYPE_RADIO_GROUP, list);
        views.put(idColumn, hash);
        values.put(idColumn, "");
    }

    /**
     * @param idColumn
     * @param idRadioGroup
     * @param idButton1
     * @param idButton2
     * @param idButton3
     */
    public void addRadioGroup(@NonNull int idColumn, @NonNull int idRadioGroup, @NonNull int idButton1,
                              @NonNull int idButton2, @NonNull int idButton3) {
        HashMap<Integer, ArrayList<View>> hash = new HashMap<>();
        ArrayList<View> list = new ArrayList<>();
        RadioGroup radioGroup = activity.findViewById(idRadioGroup);
        RadioButton radioButton1 = activity.findViewById(idButton1);
        RadioButton radioButton2 = activity.findViewById(idButton2);
        RadioButton radioButton3 = activity.findViewById(idButton3);
        list.add(radioGroup);
        list.add(radioButton1);
        list.add(radioButton2);
        list.add(radioButton3);
        hash.put(TYPE_RADIO_GROUP, list);
        views.put(idColumn, hash);
        values.put(idColumn, "");
    }

    /**
     * @param idColumn
     * @param idRadioGroup
     * @param idButton1
     * @param idButton2
     * @param idButton3
     * @param idButton4
     */
    public void addRadioGroup(@NonNull int idColumn, @NonNull int idRadioGroup, @NonNull int idButton1,
                              @NonNull int idButton2, @NonNull int idButton3, @NonNull int idButton4) {
        HashMap<Integer, ArrayList<View>> hash = new HashMap<>();
        ArrayList<View> list = new ArrayList<>();
        RadioGroup radioGroup = activity.findViewById(idRadioGroup);
        RadioButton radioButton1 = activity.findViewById(idButton1);
        RadioButton radioButton2 = activity.findViewById(idButton2);
        RadioButton radioButton3 = activity.findViewById(idButton3);
        RadioButton radioButton4 = activity.findViewById(idButton4);
        list.add(radioGroup);
        list.add(radioButton1);
        list.add(radioButton2);
        list.add(radioButton3);
        list.add(radioButton4);
        hash.put(TYPE_RADIO_GROUP, list);
        views.put(idColumn, hash);
        values.put(idColumn, "");
    }


    public void checkData() {
        for (int columnId : views.keySet()) {
            HashMap<Integer, ArrayList<View>> hashViews = views.get(columnId);
            for (int TYPE : hashViews.keySet()) {
                if (cursor.getCount() > 0) {
                    if (cursor.getString(columnId) != null || hashViews.get(TYPE).get(0) != null) {
                        String data = cursor.getString(columnId);
                        // ПРОВЕРЯЕМ ТИП RadioGroup
                        if (TYPE == TYPE_RADIO_GROUP) {
                            RadioGroup radioGroup = (RadioGroup) hashViews.get(TYPE).get(0); // Создаем RadioGroup
                            for (View view : hashViews.get(TYPE)) {
                                if (view.getClass() == RadioButton.class) {
                                    RadioButton radioButton = (RadioButton) view;  // Создаем RadioButtons
                                    if (radioButton.getText().toString() == data) {
                                        radioGroup.check(radioButton.getId());
                                    }
                                }
                            }
                            // ПРОВЕРЯЕМ ТИП EditText
                        } else if (TYPE == TYPE_EDIT_TEXT) {
                            EditText editText = (EditText) hashViews.get(TYPE).get(0); // Создаем EditText
                            editText.setText(data);
                            // ТИП TEXT_VIEW
                        } else {
                            TextView textView = (TextView) hashViews.get(TYPE).get(0); // Создаем TextView
                            textView.setText(data);
                        }
                    }
                }
            }
        }
    }

    public void initListener() {
        for (final int columnId : views.keySet()) {
            final HashMap<Integer, ArrayList<View>> hashViews = views.get(columnId);
            for (final int TYPE : hashViews.keySet()) {
                if (TYPE != TYPE_RADIO_GROUP && TYPE != TYPE_EDIT_TEXT) {
                    final TextView textView = (TextView) hashViews.get(TYPE).get(0);
                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (TYPE == TYPE_DATA) {
                                Log.e("TEST 1 TEXT LISTENER", "1 TESTTESTTEST");
                                // Выбор даты
                                new DatePickerDialog(context, dateSelect(columnId, textView),
                                        dateAndTime.get(Calendar.YEAR),
                                        dateAndTime.get(Calendar.MONTH),
                                        dateAndTime.get(Calendar.DAY_OF_MONTH))
                                        .show();
                            }
                            if (TYPE == TYPE_SPEC_NKO) {
                                variableView = hashViews.get(TYPE).get(0); // Записываем переменные для ActivityResult
                                Intent IntentSittings = new Intent(context, SpisokBND.class);
                                IntentSittings.putExtra("people", "SPEC_NKO");
                                activity.startActivityForResult(IntentSittings, PEOPLE);
                            }
                            if (TYPE == TYPE_SPEC_JOURNAL) {
                                variableView = hashViews.get(TYPE).get(0); // Записываем переменные для ActivityResult
                                Intent IntentSittings = new Intent(context, SpisokBND.class);
                                IntentSittings.putExtra("people", "SPEC_JOURNAL");
                                activity.startActivityForResult(IntentSittings, PEOPLE);
                            }
                            if (TYPE == TYPE_EXP_JOURNAL) {
                                variableView = hashViews.get(TYPE).get(0); // Записываем переменные для ActivityResult
                                Intent IntentSittings = new Intent(context, SpisokBND.class);
                                IntentSittings.putExtra("people", "EXP_JOURNAL");
                                activity.startActivityForResult(IntentSittings, PEOPLE);
                            }
                        }
                    });
                }
            }
        }
    }


    public void collectData() {
        for (int columnId : views.keySet()) {
            HashMap<Integer, ArrayList<View>> hashViews = views.get(columnId);
            for (int TYPE : hashViews.keySet()) {
                if (TYPE == TYPE_RADIO_GROUP) {
                    RadioGroup radioGroup = (RadioGroup) hashViews.get(TYPE).get(0);
                    RadioButton checkedButton = (RadioButton) radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
                    if (checkedButton != null) {
                        values.put(columnId, checkedButton.getText().toString());
                    }
                } else if (TYPE == TYPE_EDIT_TEXT) {
                    EditText editText = (EditText) hashViews.get(TYPE).get(0);
                    values.put(columnId, editText.getText().toString());
                } else {
                    TextView textView = (TextView) hashViews.get(TYPE).get(0);
                    values.put(columnId, textView.getText().toString());
                }
            }
        }
    }

    public void saveData() {
        ContentValues initialValues = new ContentValues();
        initialValues.put("Position", position);

        String column = "Stolb";
        String key;
        for (int idColumn : values.keySet()) {
            key = column + String.valueOf(idColumn);
            initialValues.put(key, values.get(idColumn));
        }

        for (String k : initialValues.keySet()) {
            Log.e(k + ": => ", initialValues.get(k).toString());
        }

        database.insert(nameWriteDb, null, initialValues);
        displayMessage(activity.getBaseContext(), "Записан: " + position);
    }

    /**
     * @param columnId
     * @param textView
     * @return
     */
    private DatePickerDialog.OnDateSetListener dateSelect(final int columnId, final TextView textView) {
        DatePickerDialog.OnDateSetListener Picker_DataNachaloOstanova = new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                dateAndTime.set(Calendar.YEAR, year);
                dateAndTime.set(Calendar.MONTH, monthOfYear);
                dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                textView.setText(new SimpleDateFormat("dd.MM.yyyy").format(new Date(dateAndTime.getTimeInMillis())));
                values.put(columnId, textView.getText().toString());
            }
        };
        return Picker_DataNachaloOstanova;
    }

    /**
     * @param context
     * @param message
     */
    private void displayMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public View getVariableView() {
        return variableView;
    }

    /**
     * @param idColumn
     * @return
     */
    public View getView(int idColumn) {
        View view = null;
        HashMap<Integer, ArrayList<View>> hash = views.get(idColumn);
        if (hash != null) {
            for (Integer TYPE : hash.keySet()) {
                view = hash.get(TYPE).get(0);
            }
        }
        return view;
    }

    /**
     * @param idColumn
     * @return
     */
    public ArrayList<View> getListView(int idColumn) {
        ArrayList<View> listViews = null;
        HashMap<Integer, ArrayList<View>> hash = views.get(idColumn);
        if (hash != null) {
            for (Integer TYPE : hash.keySet()) {
                listViews = hash.get(TYPE);
            }
        }
        return listViews;
    }

    /**
     * @param idColumn
     */
    public void removeView(int idColumn) {
        views.remove(idColumn);
    }

    public Cursor getCursor() {
        return cursor;
    }

    /**
     * @param id1
     * @param id2
     */
    public void overrideId(int id1, int id2) {
        if (views.get(id1) != null) {
            View view = null;
            int type = -1;
            HashMap<Integer, ArrayList<View>> beforeHash = views.get(id1);
            for (int TYPE : beforeHash.keySet()) {
                type = TYPE;
                view = beforeHash.get(TYPE).get(0);
            }
            views.remove(id1);
            values.remove(id1);
            HashMap<Integer, ArrayList<View>> afterHash = new HashMap<>();
            ArrayList<View> list = new ArrayList<>();
            if (view != null) {
                list.add(view);
            }
            if (type != -1) {
                afterHash.put(type, list);
            }
            views.put(id2, afterHash);
            values.put(id2, "");
        }
    }

    /**
     * @param idColumns - массив с id View в Hash views
     * @param listener  - Listener для каждой View
     *                  устанавливается одинаково
     *                  для все View
     */
    public void setGroupTextChangedListener(int[] idColumns, TextWatcher listener) {
        for (int i = 0; i < idColumns.length; i++) {
            int id = idColumns[i];
            View view = null;
            if (views.get(id) != null) {
                HashMap<Integer, ArrayList<View>> hashViews = views.get(id);
                for (Integer TYPE : hashViews.keySet()) {
                    view = hashViews.get(TYPE).get(0);
                }
                ((EditText) view).addTextChangedListener(listener);
            }
        }
    }


}
