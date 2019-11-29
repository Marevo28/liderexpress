package com.example.lider_express;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Point;
import android.os.Bundle;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import com.example.lider_express.DataBase.DatabaseHelper;
import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Display;
import android.view.Menu;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final String APP_FILES = "mysettings";
    public static final String APP_ZAKAZCHIK = "Zakazchik";
    private static DatabaseHelper mDBHelper;
    public static Context context; // Временно !!!!!

    private static int mDisplayWidth;
    private static int mDisplayHeight;



    public SQLiteDatabase mDb;
    SharedPreferences mSettings;
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
    private String position;
    private String Papka;
    private String Name;
    private String Zakazchik;
    int max;
    int intposition;



    public static DatabaseHelper getDBHelper() {
        return mDBHelper;
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

        iniWH(); // Инициализировать высоту и ширину устройства - (Для камеры)

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
        mSettings = getSharedPreferences(APP_FILES, MODE_PRIVATE);
        btnSvodnaya.setEnabled(false);
        btnKarta.setEnabled(false);
        btnPhotoTu.setEnabled(false);
        btnPhotoDoc.setEnabled(false);
        btnPhotoKontrol.setEnabled(false);

        if(mDBHelper == null) {
            mDBHelper = new DatabaseHelper(this);
        }

      /**  try {
            mDBHelper.updateDataBase();
        } catch (IOException mSQLException) {
            throw new Error("Ошибка обновления MainActivity 90 стр.");
        } **/

        try {
            mDb = mDBHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;

        }

        btnpostion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                position = textpostion.getText().toString();//сквозной номер
                if(position.length() > 0) {
                    intposition = Integer.parseInt(position);
                Zakazchik = mSettings.getString(APP_ZAKAZCHIK, "не определено");

                switch (Zakazchik) {
                    case "Башнефть 2019":
                        Zakazchik = "ZayavkaBND";
                        break;
                    case "Мегион 2019":
                        Zakazchik = "Megion2019";
                        break;
                    case "Полюс 2019":
                        Zakazchik = "ZayavkaBND";
                        break;
                    default: Zakazchik = "Не выбран";
                        break;
                }

                if (Zakazchik != "Не выбран" || position.length() != 0) {
                        long rowCount = DatabaseUtils.queryNumEntries(mDb, Zakazchik);
                    if(Integer.parseInt(position) > rowCount){
                        displayMessage(getBaseContext(), "Такого нет, понимаешь!?");
                    }else{
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(btnpostion.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                        btnSvodnaya.setEnabled(true);
                        btnPhotoTu.setEnabled(true);
                        btnPhotoDoc.setEnabled(true);
                        btnPhotoKontrol.setEnabled(true);
                        Cursor cursor = mDb.query(Zakazchik, null, "POSITION = ?", new String[]{position}, null, null, null);
                        cursor.moveToFirst();
                        texttypetu.setText(cursor.getString(2));//Тип оборудования
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
                    displayMessage(getBaseContext(), "Ахтунг! Выбери объект!");
                }




           /**     if (Zakazchik == "Не выбран") {
                    displayMessage(getBaseContext(), "Ахтунг! Выбери объект!");
                }else if (position.length() == 0) {
                    Cursor MaxCount = mDb.query(Zakazchik, null, null, null, null, null, null);
                    max = MaxCount.getCount();
                    MaxCount.close();
                    displayMessage(getBaseContext(), "Алло! Ебать! Введи число!");
                //}else if (intposition>Max) {
                //    displayMessage(getBaseContext(), "Брат, там столько нет, обновись"); //Разобратся так как надо проверочку на наличие этих данных
                } else {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(btnpostion.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    btnSvodnaya.setEnabled(true);
                    btnPhotoTu.setEnabled(true);
                    btnPhotoDoc.setEnabled(true);
                    btnPhotoKontrol.setEnabled(true);
                    Cursor cursor = mDb.query(Zakazchik, null, "POSITION = ?", new String[]{position}, null, null, null);
                    cursor.moveToFirst();
                    texttypetu.setText(cursor.getString(2));//Тип оборудования
                    textuprav.setText(cursor.getString(5));// Управление
                    textceh.setText(cursor.getString(14));//Цех
                    textobekt.setText(cursor.getString(15));//объект
                    textskvazhina.setText(cursor.getString(16));//скважина
                    String NameTy = cursor.getString(7);//Наименование устройства
                    NameTu.setText(NameTy);
                    cursor.close();
                    Name = NameTy;
                } **/


                }
            }
        });

        btnPhotoTu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent IntentPhoto = new Intent(MainActivity.this, Camera.class);//кнопка вызова Фото объекта
                Papka = "Фото";
                IntentPhoto.putExtra("position", position);
                IntentPhoto.putExtra("Zakazchik", getNameZakaz(Zakazchik));
                IntentPhoto.putExtra("Papka", Papka);
                IntentPhoto.putExtra("Name", Name);
                startActivity(IntentPhoto);
            }
        });
        btnPhotoDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent IntentPhoto = new Intent(MainActivity.this, Camera.class);//кнопка вызова Фото документов
                Papka = "Документы";
                IntentPhoto.putExtra("position", position);
                IntentPhoto.putExtra("Zakazchik", getNameZakaz(Zakazchik));
                IntentPhoto.putExtra("Papka", Papka);
                IntentPhoto.putExtra("Name", Name);
                startActivity(IntentPhoto);
            }
        });
        btnPhotoKontrol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent IntentPhoto = new Intent(MainActivity.this, Camera.class);//кнопка вызова контроля
                Papka = "Контроль";
                IntentPhoto.putExtra("position", position);
                IntentPhoto.putExtra("Zakazchik", getNameZakaz(Zakazchik));
                IntentPhoto.putExtra("Papka", Papka);
                IntentPhoto.putExtra("Name", Name);
                startActivity(IntentPhoto);
            }
        });
        btnSvodnaya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent IntentSittings = new Intent(MainActivity.this, BNDSvodnaya.class);
                IntentSittings.putExtra("position", position);
                startActivity(IntentSittings);
            }
        });
    }

    private void iniWH(){  // Ширина и высота экрана устройства
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        mDisplayWidth = size.x;
        mDisplayHeight = size.y;
    }

    public static int getDisplayWidth(){
        return mDisplayWidth;
    }

    public static int getDisplayHeight(){
        return mDisplayHeight;
    }

    public String getNameZakaz(String str){
        String zakazchik = "def";
        switch (str) {
            case "ZayavkaBND" :
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
    public void onPause(){
        super.onPause();
        // Thread stop
    }
    @Override
    public void onResume(){
        super.onResume();
        // Thread resume
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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
            Intent IntentSittings = new Intent(this, SittingsActivity.class);
            startActivity(IntentSittings);
        } else if (id == R.id.nav_photo) {

        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void displayMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
