package com.example.lider_express;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public DatabaseHelper mDBHelper;
    public SQLiteDatabase mDb;
    Button btnpostion, btnSvodnaya, btnPhotoTu, btnPhotoDoc, btnPhotoKontrol, btnKarta;
    TextView textuprav, textceh, textobekt, texttypetu, textskvazhina, textpostion;
    String position, Papka, NameTu;
    String Zakazchik="БНД 2019";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        mDBHelper = new DatabaseHelper(this);
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
        textpostion = (EditText) findViewById(R.id.textpositon);
        btnSvodnaya.setEnabled(false);
        btnKarta.setEnabled(false);
        mDBHelper = new DatabaseHelper(this);
        try {
            mDBHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }
        try {
            mDb = mDBHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }
        btnpostion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = textpostion.getText().toString();//сквозной номер
                if (position.length() == 0) {
                    displayMessage(getBaseContext(), "Алло! Ебать! Введи число!");
                } else {
                    btnSvodnaya.setEnabled(true);
                    Cursor cursor = mDb.query("ZayavkaBND", null, "POSITION = ?", new String[]{position}, null, null, null);
                    cursor.moveToFirst();
                    texttypetu.setText(cursor.getString(2));//Тип оборудования
                    textuprav.setText(cursor.getString(5));// Управление
                    NameTu=cursor.getString(7);
                    textceh.setText(cursor.getString(14));//Цех
                    textobekt.setText(cursor.getString(15));//объект
                    textskvazhina.setText(cursor.getString(16));//скважина
                    cursor.close();
                }
            }
        });
        btnPhotoTu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent IntentPhoto = new Intent(MainActivity.this, Photo.class);
                Papka = "Фото";
                IntentPhoto.putExtra("position", position);
                IntentPhoto.putExtra("Zakazchik", Zakazchik);
                IntentPhoto.putExtra("Papka", Papka);
                IntentPhoto.putExtra("Name", NameTu);
                startActivity(IntentPhoto);
            }
        });
        btnPhotoDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent IntentPhoto = new Intent(MainActivity.this, Photo.class);
                Papka = "Документы";
                IntentPhoto.putExtra("position", position);
                IntentPhoto.putExtra("Zakazchik", Zakazchik);
                IntentPhoto.putExtra("Papka", Papka);
                IntentPhoto.putExtra("Name", NameTu);
                startActivity(IntentPhoto);
            }
        });
        btnPhotoKontrol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent IntentPhoto = new Intent(MainActivity.this, Photo.class);
                Papka = "Контроль";
                IntentPhoto.putExtra("position", position);
                IntentPhoto.putExtra("Zakazchik", Zakazchik);
                IntentPhoto.putExtra("Papka", Papka);
                IntentPhoto.putExtra("Name", NameTu);
                startActivity(IntentPhoto);
            }
        });
        btnSvodnaya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent IntentSittings = new Intent(MainActivity.this, BNDSvodnaya.class);
                startActivity(IntentSittings);
                //Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://forms.gle/p9ECCxeg8tQ2NTQVA"));
                //startActivity(browserIntent);
            }
        });
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

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

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
