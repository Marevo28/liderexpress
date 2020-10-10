package com.example.lider_express;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.lider_express.DataBase.DatabaseHelper;
import com.example.lider_express.Tools.BND.PumpControlCard;
import com.example.lider_express.Svodnaya.KartaKontrolyaSPPK;
import com.example.lider_express.Svodnaya.KartaKontrolyaYDE;
import com.example.lider_express.Synchronization.Synchronization;
import com.example.lider_express.Instruments.Menu_tools;
import com.example.lider_express.Tools.HMMR.HMMRContainerControlCard;
import com.example.lider_express.Tools.HMMR.HMMRPumpControlCard;
import com.example.lider_express.Сamera2.MainCamera2;
import com.google.android.material.navigation.NavigationView;

import org.apache.commons.lang3.StringUtils;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final String APP_FILES = "mysettings";
    public static final String APP_ZAKAZCHIK = "Zakazchik";
    private SharedPreferences mSettings;
    static SharedPreferences mPrefs;

    private static DatabaseHelper mDBHelper;
    public static SQLiteDatabase mDb;

    private static Context context;

    private static int mDisplayWidth;
    private static int mDisplayHeight;

    private EditText EntryField;
    private ImageButton btnSearch;

    private Button btnCardControl;
    private Button btnPhotoObject;
    private Button btnPhotoDoc;
    private Button btnPhotoControl;
    private TextView textViewUprav;
    private TextView textViewCeh;
    private TextView textViewObject;
    private TextView textViewTypeTU;
    private TextView textViewSkvazhina;
    private TextView textViewNameTu;
    private Spinner spinnerTypeTU;

    private static String enteredValue;
    private static String position;
    private static String Papka;
    private String typeTU;
    private static String NameTU;
    // Bashneft, Megion and other
    private String location = "";
    // TODO DELETE!
    private String temporaryLocation = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this; // Временно!!!
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        EntryField = findViewById(R.id.edit_text_position);
        btnSearch = (ImageButton) findViewById(R.id.btn_search);
        btnCardControl = (Button) findViewById(R.id.button_card_control);
        btnPhotoObject = (Button) findViewById(R.id.button_photo_object);
        btnPhotoDoc = (Button) findViewById(R.id.button_photo_doc);
        btnPhotoControl = (Button) findViewById(R.id.button_photo_control);

        textViewTypeTU = (TextView) findViewById(R.id.text_view_typetu);
        textViewNameTu = (TextView) findViewById(R.id.text_view_nametu);
        textViewUprav = (TextView) findViewById(R.id.text_view_uprav);
        textViewCeh = (TextView) findViewById(R.id.text_view_ceh);
        textViewObject = (TextView) findViewById(R.id.text_view_object);
        textViewSkvazhina = (TextView) findViewById(R.id.text_view_skvajina);

        spinnerTypeTU = (Spinner) findViewById(R.id.spinner_type_tu);

        final String[] search = new String[]{"Позиция", "Имя устройства"};

        ArrayAdapter<String> searchAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, search);
        searchAdapter.setDropDownViewResource(R.layout.custom_spinner_style);
        spinnerTypeTU.setAdapter(searchAdapter);

        setEnabledButton(false);

        mSettings = getSharedPreferences(APP_FILES, MODE_PRIVATE);
        mPrefs = getSharedPreferences("myAppPrefs", MODE_PRIVATE);

        // final String SelectedCamera = mSettings.getString(APP_CAMERA, "Open Camera");

        // Инициализировать высоту и ширину устройства - (Для камеры)
        iniWH();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    0);
        } else {
            if (mDBHelper == null || Shared.flagUpdate == true) {
                mDBHelper = new DatabaseHelper(this, Shared.nameDB, 3);
                Shared.flagUpdate = false;
                Log.e("MainActivity", "New DataBase");
            }
            try {
                mDb = mDBHelper.getWritableDatabase();
                Log.e("MainActivity", "getWritableDatabase");
            } catch (Exception e) {
            }
        }

        btnSearch.setOnClickListener(listenerBtnPosition()); // - btnpostion -

        btnCardControl.setOnClickListener(listenerBtnCardControl());
        btnPhotoObject.setOnClickListener(listenerBtnPhotoObject("Фото"));
        btnPhotoDoc.setOnClickListener(listenerBtnPhotoObject("Документы"));
        btnPhotoControl.setOnClickListener(listenerBtnPhotoObject("Контроль"));

        spinnerTypeTU.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedId) {
                EntryField.setHint(search[selectedItemPosition]);
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    View.OnClickListener listenerBtnPosition() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Selected location
                location = mSettings.getString(APP_ZAKAZCHIK, "Zakazchik");
                switch (location) {
                    case "Башнефть 2020":
                        location = Shared.nameBND2020;
                        temporaryLocation = Shared.nameBND2020;
                        break;
                    case "XMMP":
                        // TODO Change Table on HMMR
                        location = Shared.nameHMMR_SUMMARY;
                        temporaryLocation = Shared.nameHMMR;
                        break;
                }
                // Selected field for search
                String selectedSearch = spinnerTypeTU.getSelectedItem().toString();
                String selection = null;
                switch (selectedSearch) {
                    case "Позиция":
                        selection = "POSITION";
                        break;
//                    case "Тип ТУ":
//                        selection = "field2";
//                        break;
                    case "Имя устройства":
                        selection = "field7";
                        break;
                }
                // Entry value in edit text
                enteredValue = EntryField.getText().toString();

                // StringUtils.isBlank("") = true
                // StringUtils.isBlank(" ") = true
                // StringUtils.isBlank(null) = true
                if (!StringUtils.isBlank(location) && !location.equals("Zakazchik")) {
                    if (!StringUtils.isBlank(enteredValue)) {
                        search(location, selection, enteredValue);
                    } else {
                        displayMessage(getBaseContext(), "Строка поиска пуста, пожалуйста введите значение.");
                    }
                } else {
                    displayMessage(getBaseContext(), "Объект не выбран! Пожалуйста перейдите в настройки и выберите объект.");
                }

            } // - onClick -
        };
        return listener;
    }

    private void search(String location, String selection, String enteredValue) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(btnSearch.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

        Cursor cursor = mDb.query(location, null, selection + " = ?", new String[]{enteredValue},
                null, null, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            setEnabledButton(true);

            textViewTypeTU.setText(cursor.getString(2));//Тип оборудования
            typeTU = cursor.getString(2);
            textViewNameTu.setText(cursor.getString(7));
            NameTU = cursor.getString(7);
            textViewUprav.setText(cursor.getString(5));// Управление
            textViewCeh.setText(cursor.getString(14));//Цех
            textViewObject.setText(cursor.getString(15));//объект
            textViewSkvazhina.setText(cursor.getString(16));//скважина
            // set select position
            position = cursor.getString(cursor.getColumnIndex("Position"));

            cursor.close();
        } else {
            setEnabledButton(false);
            displayMessage(getBaseContext(), "Пожалуйста выберите существующее устройство или обновите базу");
        }
    }

    public static DatabaseHelper getDBHelper() {
        return mDBHelper;
    }

    public static Context getContext() {
        return context;
    }

    public static SQLiteDatabase getSQLiteDatabase() {
        return mDb;
    }

    public static boolean getFirstRun() {
        return mPrefs.getBoolean("firstRun", true);
    }

    public static void setRunned() {
        SharedPreferences.Editor edit = mPrefs.edit();
        edit.putBoolean("firstRun", false);
        edit.commit();
    }

    public void startIntent(Intent intent) {
        intent.putExtra("position", position);
        intent.putExtra("Zakazchik", location);
        startActivity(intent);
    }

    private void setEnabledButton(boolean enabledButton) {
        btnCardControl.setEnabled(enabledButton);
        btnPhotoObject.setEnabled(enabledButton);
        btnPhotoDoc.setEnabled(enabledButton);
        btnPhotoControl.setEnabled(enabledButton);
    }

    private void iniWH() {  // Ширина и высота экрана устройства
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        mDisplayWidth = size.x;
        mDisplayHeight = size.y;
    }

    public static String getPositionMain() {
        return position;
    }

    public static String getPapkaMain() {
        return Papka;
    }

    public static String getNameMain() {
        return NameTU;
    }

    // Ширина дисплея
    public static int getDisplayWidth() {
        return mDisplayWidth;
    }

    // Высота дисплея
    public static int getDisplayHeight() {
        return mDisplayHeight;
    }

    @Deprecated
    public String getNameZakaz(String str) {
        String zakazchik = "def";
        switch (str) {
            case "ZayavkaBND":
                zakazchik = "Башнефть_2019";
                break;
            case "ХММР":
                zakazchik = "HMMP";
                break;
            default:

        }
        return zakazchik;
    }

    View.OnClickListener listenerBtnCardControl() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO CHANGE!
                if(temporaryLocation == Shared.nameBND2020){
                    switch (typeTU) {
                        case "Насос":
                            Intent IntentCardPump = new Intent(MainActivity.this, PumpControlCard.class);
                            IntentCardPump.putExtra("position", position);
                            startIntent(IntentCardPump);
                            break;
                        case "Блок реагентов (УДЭ)":
                            Intent IntentKartaKontrolyaUde = new Intent(MainActivity.this, KartaKontrolyaYDE.class);
                            IntentKartaKontrolyaUde.putExtra("position", position);
                            IntentKartaKontrolyaUde.putExtra("Zakazchik", getNameZakaz(location));
                            startIntent(IntentKartaKontrolyaUde);
                            break;
                        case "СППК":
                            Intent IntentKartaKontrolyaSppk = new Intent(MainActivity.this, KartaKontrolyaSPPK.class);
                            IntentKartaKontrolyaSppk.putExtra("position", position);
                            IntentKartaKontrolyaSppk.putExtra("Zakazchik", getNameZakaz(location));
                            startIntent(IntentKartaKontrolyaSppk);
                            break;
                        default:
                            displayMessage(getBaseContext(), "Допустимые типы ТУ: Насос, Блок реагентов (УДЭ), СППК");
                    }
                }else if (temporaryLocation == Shared.nameHMMR){
                    switch (typeTU) {
                        case "Насос":
                            Intent IntentCardPump = new Intent(MainActivity.this, HMMRPumpControlCard.class);
                            IntentCardPump.putExtra("position", position);
                            startIntent(IntentCardPump);
                            break;
                        case "Сосуд":
                            Intent IntentCardContainer = new Intent(MainActivity.this, HMMRContainerControlCard.class);
                            IntentCardContainer.putExtra("position", position);
                            startIntent(IntentCardContainer);
                            break;
                        default:
                            displayMessage(getBaseContext(), "Допустимые типы ТУ: Насос, Сосуд");
                    }
                }else {

                }
            }
        };
        return listener;
    }

    View.OnClickListener listenerBtnPhotoObject(String folder) {
        View.OnClickListener listener = null;

        if (folder == "Фото") {
            listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent IntentPhoto = new Intent(MainActivity.this, MainCamera2.class);//кнопка вызова Фото документов
                    Papka = "Фото";
                    startActivity(IntentPhoto);
                }
            };
        }

        if (folder == "Документы") {
            listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent IntentPhoto = new Intent(MainActivity.this, MainCamera2.class);//кнопка вызова Фото документов
                    Papka = "Документы";
                    startActivity(IntentPhoto);
                }
            };
        }

        if (folder == "Контроль") {
            listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent IntentPhoto = new Intent(MainActivity.this, MainCamera2.class);//кнопка вызова Фото документов
                    Papka = "Контроль";
                    startActivity(IntentPhoto);
                }
            };
        }
        return listener;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_оbekt) {
            // Handle the camera action
        } else if (id == R.id.nav_оbekt) {
            Intent IntentSittings = new Intent(MainActivity.this, MainActivity.class);
            startActivity(IntentSittings);
        } else if (id == R.id.nav_oborud) {
            Intent IntentSittings = new Intent(MainActivity.this, Oborudovanie.class);
            startActivity(IntentSittings);
        } else if (id == R.id.transport_list) {
            Intent IntentSittings = new Intent(MainActivity.this, Transport_list.class);
            startActivity(IntentSittings);
        } else if (id == R.id.nav_sinhron) {
            Intent IntentSittings = new Intent(MainActivity.this, Synchronization.class);
            startActivity(IntentSittings);
        } else if (id == R.id.nav_sittings) {
            Intent IntentSittings = new Intent(MainActivity.this, SittingsActivity.class);
            startActivity(IntentSittings);
        } else if (id == R.id.nav_tools) {
//            Intent IntentSittings = new Intent(MainActivity.this, Menu_tools.class);
//            startActivity(IntentSittings);
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 0) {
            if (mDBHelper == null || Shared.flagUpdate == true) {
                mDBHelper = new DatabaseHelper(this, Shared.nameDB, 3);
            }
            try {
                mDb = mDBHelper.getWritableDatabase();
            } catch (Exception e) {
            }
        }
    }

    private void displayMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

}
