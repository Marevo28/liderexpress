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
import com.example.lider_express.Svodnaya.BNDSvodnaya;
import com.example.lider_express.Svodnaya.KartaKontolyaNasos;
import com.example.lider_express.Svodnaya.KartaKontrolyaYDE;
import com.example.lider_express.Svodnaya.MegionSvodnaya;
import com.example.lider_express.Synchronization.Synchronization;
import com.example.lider_express.Tools.GiSosuda;
import com.example.lider_express.Tools.VmyatinaSocuda;
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

    private Button btnpostion;
    private Button btnSvodnaya;
    private Button btnPhotoTu;
    private Button btnPhotoDoc;
    private Button btnPhotoKontrol;
    private Button btnKarta;
    private TextView textuprav;
    private TextView textceh;
    private TextView textobekt;
    private TextView texttypetu;
    private TextView textskvazhina;
    private EditText textpostion;
    private TextView NameTu;
    private static String position;
    private static String Papka;
    private static String Name;
    private String Zakazchik;
    private String typetu;
    private String Karta;
    private int intposition;
    public String formattedDate;


    public static DatabaseHelper getDBHelper() {
        return mDBHelper;
    }
    public static Context getContext(){
        return context;
    }
    public static AppCompatActivity getAppCompatActivity(){
        return appCompatActivity;
    }
    public static SQLiteDatabase getSQLiteDatabase(){
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


        btnpostion = (Button) findViewById(R.id.btnposition);
        btnSvodnaya = (Button) findViewById(R.id.btnSvodnaya);
        btnPhotoTu = (Button) findViewById(R.id.btnPhotoTu);
        btnPhotoDoc = (Button) findViewById(R.id.btnPhotoDoc);
        btnPhotoKontrol = (Button) findViewById(R.id.btnPhotoKontrol);
        btnKarta = (Button) findViewById(R.id.btnKarta);
        textuprav = (TextView) findViewById(R.id.textuprav);
        textceh = (TextView) findViewById(R.id.textceh);
        textobekt = (TextView) findViewById(R.id.textobekt);
        texttypetu = (TextView) findViewById(R.id.texttypetu);
        textskvazhina = (TextView) findViewById(R.id.textskvazhina);
        NameTu = (TextView) findViewById(R.id.NameTu);
        textpostion = (EditText) findViewById(R.id.textpositon);

        btnSvodnaya.setEnabled(false);
        btnKarta.setEnabled(false);
        btnPhotoTu.setEnabled(false);
        btnPhotoDoc.setEnabled(false);
        btnPhotoKontrol.setEnabled(false);

        mSettings = getSharedPreferences(APP_FILES, MODE_PRIVATE);
        mPrefs = getSharedPreferences("myAppPrefs", MODE_PRIVATE);

        final String SelectedCamera = mSettings.getString(APP_CAMERA, "Open Camera");

        // Инициализировать высоту и ширину устройства - (Для камеры)
        iniWH();

        if ( ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED &&
        ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED ){
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    0);
        }else {
            if(mDBHelper == null || Shared.flagUpdate == true){
                mDBHelper = new DatabaseHelper(this, Shared.nameDB, 3);
                Shared.flagUpdate = false;
                Log.e("MainActivity", "New DataBase");
            }
            try{
                mDb = mDBHelper.getWritableDatabase();
                Log.e("MainActivity", "getWritableDatabase");
            }catch (Exception e){
            }
        }

        btnpostion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //сквозной номер
                position = textpostion.getText().toString();

                if(position.length() > 0) {
                    intposition = Integer.parseInt(position);
                    Zakazchik =  mSettings.getString(APP_ZAKAZCHIK,"Zakazchik");

                    switch (Zakazchik) {
                    //    case "Башнефть 2019": Zakazchik = Shared.nameBND2019;break;
                    //    case "Мегион 2019": Zakazchik = Shared.nameMegion2019; break;
                    //    case "Полюс 2019": Zakazchik = Shared.namePolus2019; break;
                        case "Башнефть 2020": Zakazchik = Shared.nameBND2020;break;
                        case "Мегион 2020": Zakazchik = Shared.nameMegion2020; break;
                    //    case "Полюс 2020": Zakazchik = Shared.namePolus2020; break;
                    //    case "Башнефть 2021": Zakazchik = Shared.nameBND2021;break;
                    //    case "Мегион 2021": Zakazchik = Shared.nameMegion2021; break;
                    //    case "Полюс 2021": Zakazchik = Shared.namePolus2021; break;
                        default: Zakazchik = "No"; break;
                    }

                    if (Zakazchik != "No") {
                        if(Integer.parseInt(position) != 0 && position.length() != 0) {
                            long rowCount = DatabaseUtils.queryNumEntries(mDb, Zakazchik);

                            if (Integer.parseInt(position) > rowCount) {
                                displayMessage(getBaseContext(), "Такого не существует!");
                            } else {
                                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(btnpostion.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                                btnSvodnaya.setEnabled(true);
                                btnPhotoTu.setEnabled(true);
                                btnPhotoDoc.setEnabled(true);
                                btnPhotoKontrol.setEnabled(true);

                                Cursor cursor = mDb.query(Zakazchik, null, "POSITION = ?", new String[]{position},
                                        null, null, null);
                                cursor.moveToFirst();

                                texttypetu.setText(cursor.getString(2));//Тип оборудования
                                typetu=cursor.getString(2);
                                String block = "Блок реагентов (УДЭ)";
                                String nasos = "Насос";
                                if(block.equals(cursor.getString(2))) {
                                    btnKarta.setEnabled(true);
                                }else if(nasos.equals(cursor.getString(2))){
                                    btnKarta.setEnabled(true);
                                }else{
                                    btnKarta.setEnabled(false);
                                }
                                textuprav.setText(cursor.getString(5));// Управление
                                textceh.setText(cursor.getString(14));//Цех
                                textobekt.setText(cursor.getString(15));//объект
                                textskvazhina.setText(cursor.getString(16));//скважина
                                String NameTy = cursor.getString(7);//Наименование устройства
                                NameTu.setText(NameTy);
                                cursor.close();
                                Name = NameTy;
                            }
                        }else{
                            displayMessage(getBaseContext(), "Выберите существующую позицию или обновите базу!");
                        }
                    }else{
                        displayMessage(getBaseContext(), "Выберите объект!");
                    }
                } // - if -
            } // - onClick -
        }); // - btnpostion -


        btnPhotoTu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if(SelectedCamera.equals("Open Camera")) {
                    Intent IntentPhoto = new Intent(MainActivity.this, MainCamera2.class);//кнопка вызова Фото документов
                    Papka = "Фото";
                    startActivity(IntentPhoto);
                //}else{
                //    Intent IntentPhoto = new Intent(MainActivity.this, Camera.class);//кнопка вызова Фото документов
                //    Papka = "Фото";
                //    IntentPhoto.putExtra("position", position);
                //    IntentPhoto.putExtra("Zakazchik", getNameZakaz(Zakazchik));
                //    IntentPhoto.putExtra("Papka", Papka);
                //    IntentPhoto.putExtra("Name", Name);
                //    startActivity(IntentPhoto);
                //}
            }
        });
        btnPhotoDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if(SelectedCamera.equals("Open Camera")) {
                      Intent IntentPhoto = new Intent(MainActivity.this, MainCamera2.class);//кнопка вызова Фото документов
                      Papka = "Документы";
                      startActivity(IntentPhoto);
                //}else{
                //    Intent IntentPhoto = new Intent(MainActivity.this, Camera.class);//кнопка вызова Фото документов
                //    Papka = "Документы";
                //    IntentPhoto.putExtra("position", position);
                //    IntentPhoto.putExtra("Zakazchik", getNameZakaz(Zakazchik));
                //    IntentPhoto.putExtra("Papka", Papka);
                //    IntentPhoto.putExtra("Name", Name);
                //    startActivity(IntentPhoto);
                //}
            }
        });
        btnPhotoKontrol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if(SelectedCamera.equals("Open Camera")) {
                      Intent IntentPhoto = new Intent(MainActivity.this, MainCamera2.class);//кнопка вызова Фото документов
                      Papka = "Контроль";
                      startActivity(IntentPhoto);
                //}else{
                //    Intent IntentPhoto = new Intent(MainActivity.this, Camera.class);//кнопка вызова Фото документов
                //    Papka = "Контроль";
                //    IntentPhoto.putExtra("position", position);
                //    IntentPhoto.putExtra("Zakazchik", getNameZakaz(Zakazchik));
                //    IntentPhoto.putExtra("Papka", Papka);
                //    IntentPhoto.putExtra("Name", Name);
                //    startActivity(IntentPhoto);
                //}
            }
        });
        btnSvodnaya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Integer.parseInt(position) != 0 && position.length() != 0) {
                    switch (Zakazchik) {
                        //    case "ZayavkaBND2019": Intent IntentSvodnayaBND2019 = new Intent(MainActivity.this, BNDSvodnaya.class);
                        //                       startIntent(IntentSvodnayaBND2019); break;
                        //    case "Megion2019": Intent IntentMegion2019 = new Intent(MainActivity.this, MegionSvodnaya.class);
                        //                       startIntent(IntentMegion2019); break;
                        //    case "Polus2019": Intent IntentPolus2019 = new Intent(MainActivity.this, Polus.class);
                        //        startIntent(IntentPolus2019); break;
                        case "Bashneft2020":
                            Intent IntentBashneft2020 = new Intent(MainActivity.this, BNDSvodnaya.class);
                            startIntent(IntentBashneft2020);
                            break;
                        case "Megion2020":
                            Intent IntentMegion2020 = new Intent(MainActivity.this, MegionSvodnaya.class);
                            startIntent(IntentMegion2020);
                            break;
                        //    case "Polus2020": Intent IntentPolus2020 = new Intent(MainActivity.this, Polus.class);
                        //         startIntent(IntentPolus2020); break;
                        //    case "ZayavkaBND2021": Intent IntentSvodnayaBND2021 = new Intent(MainActivity.this, BNDSvodnaya.class);
                        //        startIntent(IntentSvodnayaBND2021); break;
                        //    case "Megion2021": Intent IntentMegion2021 = new Intent(MainActivity.this, MegionSvodnaya.class);
                        //        startIntent(IntentMegion2021); break;
                        //    case "Polus2021": Intent IntentPolus2021 = new Intent(MainActivity.this, Polus.class);
                        //         startIntent(IntentPolus2021);
                    }
                }else{ displayMessage(getBaseContext(), "Выберите существующую позицию или обновите базу!");}
            }
        });

        btnKarta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch(typetu) {
                    case "Насос":
                    Intent IntentKartaKontrolyaNasos = new Intent(MainActivity.this, KartaKontolyaNasos.class);
                        IntentKartaKontrolyaNasos.putExtra("position", position);
                        IntentKartaKontrolyaNasos.putExtra("Zakazchik", getNameZakaz(Zakazchik));
                    startIntent(IntentKartaKontrolyaNasos);
                break;
                    case "Блок реагентов (УДЭ)":
                    Intent IntentKartaKontrolyaUde = new Intent(MainActivity.this, KartaKontrolyaYDE.class);
                        IntentKartaKontrolyaUde.putExtra("position", position);
                        IntentKartaKontrolyaUde.putExtra("Zakazchik", getNameZakaz(Zakazchik));
                    startIntent(IntentKartaKontrolyaUde);
                break;
                        default: displayMessage(getBaseContext(), "Выберите существующую позицию или обновите базу!");
                }

            }
        });
    }

    public static boolean getFirstRun() {
        return mPrefs.getBoolean("firstRun", true);
    }

    public static void setRunned() {
        SharedPreferences.Editor edit = mPrefs.edit();
        edit.putBoolean("firstRun", false);
        edit.commit();
    }

    public void startIntent(Intent intent){
        intent.putExtra("position", position);
        intent.putExtra("Zakazchik", Zakazchik);
        startActivity(intent);
    }

    private void iniWH(){  // Ширина и высота экрана устройства
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
        return Name;
    }

    // Ширина дисплея
    public static int getDisplayWidth(){
        return mDisplayWidth;
    }

    // Высота дисплея
    public static int getDisplayHeight(){
        return mDisplayHeight;
    }

    public String getNameZakaz(String str){
        String zakazchik = "def";
        switch (str) {
            case "ZayavkaBND": zakazchik = "Башнефть_2019"; break;
            case "Megion2019": zakazchik = "Мегион_2019"; break;
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
    public void onPause(){
        super.onPause();
    }
    @Override
    public void onResume(){
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
        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            Intent IntentSittings = new Intent(MainActivity.this, GiSosuda.class);
            startActivity(IntentSittings);
        } else if (id == R.id.nav_slideshow) {
            Intent IntentSittings = new Intent(MainActivity.this, VmyatinaSocuda.class);
            startActivity(IntentSittings);
        } else if (id == R.id.nav_tools) {
            Intent IntentSittings = new Intent(MainActivity.this, Synchronization.class);
            startActivity(IntentSittings);
        } else if (id == R.id.nav_sittings) {
            Intent IntentSittings = new Intent(MainActivity.this, SittingsActivity.class);
            startActivity(IntentSittings);
        } else if (id == R.id.nav_photo) {
            Intent IntentSittings = new Intent(MainActivity.this, Oborudovanie.class);
            startActivity(IntentSittings);
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 0){
            if(mDBHelper == null || Shared.flagUpdate == true){
                mDBHelper = new DatabaseHelper(this, Shared.nameDB, 3);
            }
            try{
                mDb = mDBHelper.getWritableDatabase();
            }catch (Exception e){
            }
        }
    }

    private void displayMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

}
