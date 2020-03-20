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
import java.lang.reflect.Array;
import java.util.ArrayList;

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
     private String stolb54;
     private String stolb55;
     private String stolb56;
     private String stolb57;
     private String stolb58;
     private String stolb59;
     private String stolb60;
     private String stolb61;
     private String stolb62;
     private String stolb63;
     private String stolb64;
     private String stolb65;
     private String stolb66;
     private String stolb67;
     private String stolb68;


    String[] arrStolb = new String[42];


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

        // Инициализируем массив
        for(int i = 0; i < arrStolb.length; i ++){
            arrStolb[i] = "";
        }


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

             //   MainActivity.getSQLiteDatabase().close();
             //   MainActivity.getDBHelper().close();
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
/*
            for (int i = 1; i < cursor.getColumnCount(); i ++){
                Log.e("Column", String.valueOf(cursor.getColumnCount()));
                if(i == 1){
                    if (cursor.getString(i) != null) {
                        position = (cursor.getString(1)); }
                } else{
                    if (cursor.getString(i) != null) {
                        arrStolb[i - 1] = (cursor.getString(i));
                    }
                }
            }

            */
            if (cursor.getString(1) != null) {  //27
                position = cursor.getString(1); }
            if (cursor.getString(2) != null) {  //28
                stolb27 = cursor.getString(2); }
            if (cursor.getString(3) != null) {  //29
                stolb28 = cursor.getString(3); }
            if (cursor.getString(4) != null) {  //30
                stolb29 = cursor.getString(4); }
            if (cursor.getString(5) != null) {  //31
                stolb30 = cursor.getString(5); }
            if (cursor.getString(6) != null) {  //32
                stolb31 = cursor.getString(6); }
            if (cursor.getString(7) != null) {  //33
                stolb32 = cursor.getString(7); }
            if (cursor.getString(8) != null) { //34
                stolb33 = cursor.getString(8); }
            if (cursor.getString(9) != null) { //35
                stolb34 = cursor.getString(9); }
            if (cursor.getString(10) != null) { //36
                stolb35 = cursor.getString(10); }
            if (cursor.getString(11) != null) { //37
                stolb36 = cursor.getString(11); }
            if (cursor.getString(12) != null) { //38
                stolb37 = cursor.getString(12); }
            if (cursor.getString(13) != null) { //39
                stolb38 = cursor.getString(13); }
            if (cursor.getString(14) != null) { //40
                stolb39 = cursor.getString(14); }
            if (cursor.getString(15) != null) { //41
                stolb40 = cursor.getString(15); }
            if (cursor.getString(16) != null) { //42
                stolb41 = cursor.getString(16); }
            if (cursor.getString(17) != null) { //43
                stolb42 = cursor.getString(17); }
            if (cursor.getString(18) != null) { //44
                stolb43 = cursor.getString(18); }
            if (cursor.getString(19) != null) { //45
                stolb44 = cursor.getString(19); }
            if (cursor.getString(20) != null) { //46
                stolb45 = cursor.getString(20); }
            if (cursor.getString(21) != null) { //47
                stolb46 = cursor.getString(21); }
            if (cursor.getString(22) != null) { //48
                stolb47 = cursor.getString(22); }
            if (cursor.getString(23) != null) {  //49
                stolb48 = cursor.getString(23); }
            if (cursor.getString(24) != null) { //50
                stolb49 = cursor.getString(24); }
            if (cursor.getString(25) != null) { //51
                stolb50 = cursor.getString(25); }
            if (cursor.getString(26) != null) { //52
                stolb51 = cursor.getString(26); }
            if (cursor.getString(27) != null) { //53
                stolb52 = cursor.getString(27); }
            if (cursor.getString(28) != null) { //54
                stolb53 = cursor.getString(28); }
            if (cursor.getString(29) != null) { //55
                stolb54 = cursor.getString(29); }
            if (cursor.getString(30) != null) { //56
                stolb55 = cursor.getString(30); }
            if (cursor.getString(31) != null) { //57
                stolb56 = cursor.getString(31); }
            if (cursor.getString(32) != null) { //58
                stolb57 = cursor.getString(32); }
            if (cursor.getString(33) != null) { //59
                stolb58 = cursor.getString(33); }
            if (cursor.getString(34) != null) { //60
                stolb59 = cursor.getString(34); }
            if (cursor.getString(35) != null) { //61
                stolb60 = cursor.getString(35); }
            if (cursor.getString(36) != null) { //62
                stolb61 = cursor.getString(36); }
            if (cursor.getString(37) != null) { //63
                stolb62 = cursor.getString(37); }
            if (cursor.getString(38) != null) { //64
                stolb63 = cursor.getString(38); }
            if (cursor.getString(39) != null) { //65
                stolb64 = cursor.getString(39); }
            if (cursor.getString(40) != null) { //66
                stolb65 = cursor.getString(40); }
            if (cursor.getString(41) != null) { //67
                stolb66 = cursor.getString(41); }
            if (cursor.getString(42) != null) { //68
                stolb67 = cursor.getString(42); }
            if (cursor.getString(42) != null) { //68
                stolb68 = cursor.getString(43); }

            zapros = new JsonZapros(Zakazchik);

            //zapros.download(position, arrStolb);
            zapros.download(position, stolb27, stolb28, stolb29, stolb30, stolb31, stolb32, stolb33, stolb34, stolb35, stolb36, stolb37, stolb38, stolb39,
                    stolb40, stolb41, stolb42, stolb43, stolb44, stolb45, stolb46, stolb47, stolb48, stolb49, stolb50, stolb51, stolb52, stolb53, stolb54,
                    stolb55, stolb56, stolb57, stolb58, stolb59, stolb60, stolb61, stolb62, stolb63, stolb64, stolb65, stolb66, stolb67, stolb68);

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
