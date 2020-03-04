package com.example.lider_express.Synchronization;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.lider_express.DataBase.UpdateDataBaseHelper;
import com.example.lider_express.MainActivity;
import com.example.lider_express.R;
import com.example.lider_express.Shared;

import java.io.File;

public class Synchronization extends AppCompatActivity {


     private Spinner spinnerDefect;
     AppCompatActivity appCompatActivity;
     String[] Zakazchiki = {//"Башнефть 2019", "Мегион 2019", "Полюс 2019",
             "Башнефть 2020", "Мегион 2020", "Башнефть 2020 УДЭ" //"Полюс 2020",
            // "Башнефть 2021", "Мегион 2021", "Полюс 2021"
                           };

     private String Zakazchik = Shared.nameDefectBND2020;

     private ProgressBar progressBar;

     int amount;
     private Button btnpostion;
     private Button btnUpdateDB;
     private Button btnSendData;

     private TextView TextKolvoZap;
     private EditText textpostion;
     private String position;
     private String stolb27;
     private String stolb28;
     private String stolb29;
     private String stolb30;
     private String stolb31;
     private String stolb32;
     private String stolb33;
     private String stolb34;
     private String stolb35;
     private String stolb36;
     private String stolb37;
     private String stolb38;
     private String stolb39;
     private String stolb40;
     private String stolb41;
     private String stolb42;
     private String stolb43;
     private String stolb44;
     private String stolb45;
     private String stolb46;
     private String stolb47;
     private String stolb48;
     private String stolb49;
     private String stolb50;
     private String stolb51;
     private String stolb52;
     private String stolb53;
     private JsonZapros zapros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_synchronization);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        btnpostion = (Button) findViewById(R.id.btnposition);
        btnUpdateDB = findViewById(R.id.ButtonUpdateDB);
        btnSendData = findViewById(R.id.ButtonSendData);
        textpostion = (EditText) findViewById(R.id.textpositon);
        TextKolvoZap = (TextView) findViewById(R.id.TextKolvoZap);
        spinnerDefect = findViewById(R.id.spinnerDefect);

        progressBar = findViewById(R.id.progress_bar);
        progressBar.setIndeterminate(true);
        progressBar.setVisibility(View.INVISIBLE);

        appCompatActivity = this;

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Zakazchiki);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerDefect.setAdapter(adapter);
        spinnerDefect.setSelection(0);

    //    mDBHelper = new DatabaseHelper();

    //    try {
    //        mDb = mDBHelper.getWritableDatabase();
    //    } catch (SQLException mSQLException) {
    //        throw mSQLException;
    //    }

        btnpostion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor defect = MainActivity.mDb.query(Zakazchik, null, null, null, null, null, null);
                TextKolvoZap.setText("Количество записей в базе: " + defect.getCount());
                defect.close();
            }
        });

        spinnerDefect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                 //   case 0: Zakazchik = Shared.nameDefectBND2019; break;
                 //   case 1: Zakazchik = Shared.nameDefectMegion2019; break;
                 //   case 2: /** Zakazchik = Shared.nameDefectPolus2019; **/ break;
                    case 0: Zakazchik = Shared.nameDefectBND2020; break;
                    case 1: Zakazchik = Shared.nameDefectMegion2020; break;
                    case 2: Zakazchik = Shared.nameDefectBashneft2020_UDE; break;
                 //   case 5: Zakazchik = Shared.nameDefectPolus2020;**/  break;
                 //   case 6: Zakazchik = Shared.nameDefectBND2021; break;
                 //   case 7: Zakazchik = Shared.nameDefectMegion2021; break;
                 //   case 8: Zakazchik = Shared.nameDefectPolus2021;**/  break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Обвнление Базы данных
        btnUpdateDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Удаляем Базу данных в папке для обвновления
                File file = new File(Shared.pathUpdateDB + "/" + Shared.nameUpdateDB);
                if(file.exists()){
                    file.delete();
                }
                // Удаляем Базу данных
                Shared.context.deleteDatabase(Shared.nameDB);

                //качаем базу данных
                new UpdateDataBaseHelper(appCompatActivity, MainActivity.mDb, Shared.URL,
                        Shared.pathUpdateDB, Shared.nameUpdateDB, progressBar).execute();
            }
        });
    }

    public void onDown(View view) {

        Cursor cursor = MainActivity.mDb.query(Zakazchik, null, null,
                null, null, null, null);
        Cursor defect = MainActivity.mDb.query(Zakazchik, null, null,
                null, null, null, null);

        amount = defect.getCount();
        if (amount == 0) {
            displayMessage(getBaseContext(), "В базе пусто");
        }else {
            cursor.moveToFirst();
            if (cursor.getString(1) != null) {
                position = cursor.getString(1); }
            if (cursor.getString(2) != null) {
                stolb27 = cursor.getString(2); }
            if (cursor.getString(3) != null) {
                stolb28 = cursor.getString(3); }
            if (cursor.getString(4) != null) {
                stolb29 = cursor.getString(4); }
            if (cursor.getString(5) != null) {
                stolb30 = cursor.getString(5); }
            if (cursor.getString(6) != null) {
                stolb31 = cursor.getString(6); }
            if (cursor.getString(7) != null) {
                stolb32 = cursor.getString(7); }
            if (cursor.getString(8) != null) {
                stolb33 = cursor.getString(8); }
            if (cursor.getString(9) != null) {
                stolb34 = cursor.getString(9); }
            if (cursor.getString(10) != null) {
                stolb35 = cursor.getString(10); }
            if (cursor.getString(11) != null) {
                stolb36 = cursor.getString(11); }
            if (cursor.getString(12) != null) {
                stolb37 = cursor.getString(12); }
            if (cursor.getString(13) != null) {
                stolb38 = cursor.getString(13); }
            if (cursor.getString(14) != null) {
                stolb39 = cursor.getString(14); }
            if (cursor.getString(15) != null) {
                stolb40 = cursor.getString(15); }
            if (cursor.getString(16) != null) {
                stolb41 = cursor.getString(16); }
            if (cursor.getString(17) != null) {
                stolb42 = cursor.getString(17); }
            if (cursor.getString(18) != null) {
                stolb43 = cursor.getString(18); }
            if (cursor.getString(19) != null) {
                stolb44 = cursor.getString(19); }
            if (cursor.getString(20) != null) {
                stolb45 = cursor.getString(20); }
            if (cursor.getString(21) != null) {
                stolb46 = cursor.getString(21); }
            if (cursor.getString(22) != null) {
                stolb47 = cursor.getString(22); }
            if (cursor.getString(23) != null) {
                stolb48 = cursor.getString(23); }
            if (cursor.getString(24) != null) {
                stolb49 = cursor.getString(24); }
            if (cursor.getString(25) != null) {
                stolb50 = cursor.getString(25); }
            if (cursor.getString(26) != null) {
                stolb51 = cursor.getString(26); }
            if (cursor.getString(27) != null) {
                stolb52 = cursor.getString(27); }
            if (cursor.getString(28) != null) {
                stolb53 = cursor.getString(28); }
            zapros = new JsonZapros(Zakazchik);
            zapros.download(position, stolb27, stolb28, stolb29, stolb30, stolb31, stolb32, stolb33, stolb34, stolb35, stolb36, stolb37, stolb38, stolb39,
                    stolb40, stolb41, stolb42, stolb43, stolb44, stolb45, stolb46, stolb47, stolb48, stolb49, stolb50, stolb51, stolb52, stolb53);
            try {
                zapros.join();
            } catch (InterruptedException ie) {
                Log.e("pass 0", ie.getMessage());
            }

            displayMessage(getBaseContext(), "отправлено: " + zapros.reposition());
            MainActivity.mDb.delete(Zakazchik, "Position = " + zapros.reposition(), null);
            TextKolvoZap.setText("Количество записей в базе: " + defect.getCount());
            zapros.interrupt(); // Останваливаем поток
        }
    }

    private void displayMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

}
