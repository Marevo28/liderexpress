package com.example.lider_express;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.DatabaseUtils;
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
import android.widget.Button;
import android.widget.EditText;
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
import com.example.lider_express.ControlCard.BND.PumpControlCard;
import com.example.lider_express.Svodnaya.KartaKontrolyaSPPK;
import com.example.lider_express.Svodnaya.KartaKontrolyaYDE;
import com.example.lider_express.Synchronization.Synchronization;
import com.example.lider_express.Tools.Menu_tools;
import com.example.lider_express.Сamera2.MainCamera2;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final String APP_FILES = "mysettings";
    public static final String APP_ZAKAZCHIK = "Zakazchik";
    public static final String APP_CAMERA = "Camera";
    private SharedPreferences mSettings;
    static SharedPreferences mPrefs;

    private static DatabaseHelper mDBHelper;
    public static SQLiteDatabase mDb;

    private static Context context;
    private static AppCompatActivity appCompatActivity;

    private static int mDisplayWidth;
    private static int mDisplayHeight;

    private EditText editTextPosition;
    private Button btnPosition;

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
    private static String position;
    private static String Papka;
    private static String NameTU;
    private String Zakazchik;

    private String typeTU;
    private String Card;
    private int intPosition;
    public String formattedDate;


    public static DatabaseHelper getDBHelper() {
        return mDBHelper;
    }

    public static Context getContext() {
        return context;
    }

    public static AppCompatActivity getAppCompatActivity() {
        return appCompatActivity;
    }

    public static SQLiteDatabase getSQLiteDatabase() {
        return mDb;
    }


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


        editTextPosition = findViewById(R.id.edit_text_position);
        btnPosition = (Button) findViewById(R.id.btn_position);
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

        btnPosition.setOnClickListener(listenerBtnPosition()); // - btnpostion -

        btnCardControl.setOnClickListener(listenerBtnCardControl());
        btnPhotoObject.setOnClickListener(listenerBtnPhotoObject("Фото"));
        btnPhotoDoc.setOnClickListener(listenerBtnPhotoObject("Документы"));
        btnPhotoControl.setOnClickListener(listenerBtnPhotoObject("Контроль"));
    }

    View.OnClickListener listenerBtnPosition() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //сквозной номер
                position = editTextPosition.getText().toString();

                if (position.length() > 0) {
                    intPosition = Integer.parseInt(position);
                    Zakazchik = mSettings.getString(APP_ZAKAZCHIK, "Zakazchik");

                    switch (Zakazchik) {
                        case "Башнефть 2020":
                            Zakazchik = Shared.nameBND2020;
                            break;
                        default:
                            Zakazchik = "No";
                            break;
                    }
                    if (Zakazchik != "No") {
                        if (Integer.parseInt(position) != 0 && position.length() != 0) {
                            long rowCount = DatabaseUtils.queryNumEntries(mDb, Zakazchik);
                            if (Integer.parseInt(position) > rowCount) {
                                displayMessage(getBaseContext(), "Обьект не существует!");
                            } else {
                                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(btnPosition.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                                setEnabledButton(true);

                                Cursor cursor = mDb.query(Zakazchik, null, "POSITION = ?", new String[]{position},
                                        null, null, null);
                                cursor.moveToFirst();

                                textViewTypeTU.setText(cursor.getString(2));//Тип оборудования
                                typeTU = cursor.getString(2);
                                textViewNameTu.setText(cursor.getString(7));
                                NameTU = cursor.getString(7);
                                textViewUprav.setText(cursor.getString(5));// Управление
                                textViewCeh.setText(cursor.getString(14));//Цех
                                textViewObject.setText(cursor.getString(15));//объект
                                textViewSkvazhina.setText(cursor.getString(16));//скважина

                                /**
                                String blockUDE = "Блок реагентов (УДЭ)";
                                String pump = "Насос";
                                String SPPK = "СППК";

                                if (blockUDE.equals(cursor.getString(2))) {
                                    btnKarta.setEnabled(true);
                                } else if (pump.equals(cursor.getString(2))) {
                                    btnKarta.setEnabled(true);
                                } else if (SPPK.equals(cursor.getString(2))) {
                                    btnKarta.setEnabled(true);
                                } else {
                                    btnKarta.setEnabled(false);
                                }
                                 **/

                                cursor.close();
                            }
                        } else {
                            displayMessage(getBaseContext(), "Выберите существующую позицию или обновите базу!");
                        }
                    } else {
                        displayMessage(getBaseContext(), "Выберите объект!");
                    }
                } // - if -
            } // - onClick -
        };
        return listener;
    }

    View.OnClickListener listenerBtnCardControl() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (typeTU) {
                    case "Насос":
                        /** ИСПРАВИТЬ!!!!!!!
                         * ДЛЯ КАРТЫ НАСОСА ОТДЕЛЬНЫЙ КЛАСС!!!
                         * ВРЕМЕННО!!!!
                         */
                        Intent IntentCardPump = new Intent(MainActivity.this, PumpControlCard.class);
                        IntentCardPump.putExtra("position", position);
                        IntentCardPump.putExtra("Zakazchik", getNameZakaz(Zakazchik));
                        startIntent(IntentCardPump);
                        break;
                    case "Блок реагентов (УДЭ)":
                        Intent IntentKartaKontrolyaUde = new Intent(MainActivity.this, KartaKontrolyaYDE.class);
                        IntentKartaKontrolyaUde.putExtra("position", position);
                        IntentKartaKontrolyaUde.putExtra("Zakazchik", getNameZakaz(Zakazchik));
                        startIntent(IntentKartaKontrolyaUde);
                        break;
                    case "СППК":
                        Intent IntentKartaKontrolyaSppk = new Intent(MainActivity.this, KartaKontrolyaSPPK.class);
                        IntentKartaKontrolyaSppk.putExtra("position", position);
                        IntentKartaKontrolyaSppk.putExtra("Zakazchik", getNameZakaz(Zakazchik));
                        startIntent(IntentKartaKontrolyaSppk);
                        break;
                    default:
                        displayMessage(getBaseContext(), "Выберите существующую позицию или обновите базу!");
                }

            }
        };
        return listener;
    }

    View.OnClickListener listenerBtnPhotoObject(String folder) {
        View.OnClickListener listener = null;

        if (folder == "Фото"){
            listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent IntentPhoto = new Intent(MainActivity.this, MainCamera2.class);//кнопка вызова Фото документов
                    Papka = "Фото";
                    startActivity(IntentPhoto);
                }
            };
        }

        if (folder == "Документы"){
            listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent IntentPhoto = new Intent(MainActivity.this, MainCamera2.class);//кнопка вызова Фото документов
                    Papka = "Документы";
                    startActivity(IntentPhoto);
                }
            };
        }

        if (folder == "Контроль"){
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
        intent.putExtra("Zakazchik", Zakazchik);
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

    public String getNameZakaz(String str) {
        String zakazchik = "def";
        switch (str) {
            case "ZayavkaBND":
                zakazchik = "Башнефть_2019";
                break;
            case "Megion2019":
                zakazchik = "Мегион_2019";
                break;
        }
        return zakazchik;
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
            Intent IntentSittings = new Intent(MainActivity.this, Menu_tools.class);
            startActivity(IntentSittings);
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
