package com.example.lider_express;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.lider_express.DataBase.DatabaseHelper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Synchronization extends AppCompatActivity {
    public DatabaseHelper mDBHelper;
    public SQLiteDatabase mDb;

    Button btnpostion;
    int n = 1;
    EditText textuprav;
    EditText textceh;
    EditText textobekt;
    EditText texttypetu;
    EditText textskvazhina;
    EditText textpostion;
    EditText NameTu;
    String position;
    String stolb27;
    String stolb28;
    String stolb29;
    String stolb30;
    String stolb31;
    String stolb32;
    String stolb33;
    String stolb34;
    String stolb35;
    String stolb36;
    String stolb37;
    String stolb38;
    String stolb39;
    String stolb40;
    String stolb41;
    String stolb42;
    String stolb43;
    String stolb44;
    String stolb45;
    String stolb46;
    String stolb47;
    String stolb48;
    String stolb49;
    String stolb50;
    String stolb51;
    String stolb52;
    String stolb53;
    private JsonZapros zapros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_synchronization);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        btnpostion = (Button) findViewById(R.id.btnposition);
        textuprav = (EditText) findViewById(R.id.textuprav);
        textceh = (EditText) findViewById(R.id.textceh);
        textobekt = (EditText) findViewById(R.id.textobekt);
        texttypetu = (EditText) findViewById(R.id.texttypetu);
        textskvazhina = (EditText) findViewById(R.id.textskvazhina);
        textpostion = (EditText) findViewById(R.id.textpositon);
        mDBHelper = MainActivity.getDBHelper();// new DatabaseHelper(this);// подклчюение к БД
        try {
            mDb = mDBHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }
    }
    public void onSync(View view){
        Cursor defectBND = mDBHelper.getReadableDatabase().query("DefectBND", null,
                null, null, null, null, null);
        String count = String.valueOf(defectBND.getCount());
        displayMessage(getBaseContext(), count);
        defectBND.close();
       //position=textpostion.getText().toString();
       //zapros = new JsonZapros();
       //zapros.start(position);
       //try{
       //    zapros.join();
       //}catch (InterruptedException ie){
       //    Log.e("pass 0",ie.getMessage());
       //}
       //uprav = zapros.resname();
       //ceh = zapros.ressurname();
       //obekt = zapros.resmiddlename();
       //textuprav.setText(uprav);
       //textceh.setText(ceh);
       //textobekt.setText(obekt);
    }

    public void onDown(View view) {

        Cursor cursor = mDb.query("DefectBND", null, null, null, null, null, null);
        cursor.moveToFirst();
        if (cursor.getString(1) != null) { position =cursor.getString(1);}
        if (cursor.getString(2) != null) { stolb27 =cursor.getString(2);}
        if (cursor.getString(3) != null) { stolb28 =cursor.getString(3);}
        if (cursor.getString(4) != null) { stolb29 =cursor.getString(4);}
        if (cursor.getString(5) != null) { stolb30 =cursor.getString(5);}
        if (cursor.getString(6) != null) { stolb31 =cursor.getString(6);}
        if (cursor.getString(7) != null) { stolb32 =cursor.getString(7);}
        if (cursor.getString(8) != null) { stolb33 =cursor.getString(8);}
        if (cursor.getString(9) != null) { stolb34 =cursor.getString(9);}
        if (cursor.getString(10) != null) { stolb35 =cursor.getString(10);}
        if (cursor.getString(11) != null) { stolb36 =cursor.getString(11);}
        if (cursor.getString(12) != null) { stolb37 =cursor.getString(12);}
        if (cursor.getString(13) != null) { stolb38 =cursor.getString(13);}
        if (cursor.getString(14) != null) { stolb39 =cursor.getString(14);}
        if (cursor.getString(15) != null) { stolb40 =cursor.getString(15);}
        if (cursor.getString(16) != null) { stolb41 =cursor.getString(16);}
        if (cursor.getString(17) != null) { stolb42 =cursor.getString(17);}
        if (cursor.getString(18) != null) { stolb43 =cursor.getString(18);}
        if (cursor.getString(19) != null) { stolb44 =cursor.getString(19);}
        if (cursor.getString(20) != null) { stolb45 =cursor.getString(20);}
        if (cursor.getString(21) != null) { stolb46 =cursor.getString(21);}
        if (cursor.getString(22) != null) { stolb47 =cursor.getString(22);}
        if (cursor.getString(23) != null) { stolb48 =cursor.getString(23);}
        if (cursor.getString(24) != null) { stolb49 =cursor.getString(24);}
        if (cursor.getString(25) != null) { stolb50 =cursor.getString(25);}
        if (cursor.getString(26) != null) { stolb51 =cursor.getString(26);}
        if (cursor.getString(27) != null) { stolb52 =cursor.getString(27);}
        if (cursor.getString(28) != null) { stolb53 =cursor.getString(28);}

        zapros = new JsonZapros();
        zapros.download(position,stolb27,stolb28,stolb29,stolb30,stolb31,stolb32,stolb33,stolb34,stolb35,stolb36,stolb37,stolb38,stolb39,
                stolb40,stolb41,stolb42,stolb43,stolb44,stolb45,stolb46,stolb47,stolb48,stolb49,stolb50,stolb51,stolb52,stolb53);

        try{
            zapros.join();
        }catch (InterruptedException ie){
            Log.e("pass 0",ie.getMessage());
        }
        displayMessage(getBaseContext(), zapros.reposition());
        mDb.delete("DefectBND", "_id = " + n, null);
        n++;
    }
    private void displayMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
