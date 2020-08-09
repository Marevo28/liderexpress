package com.example.lider_express.Instruments;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.lider_express.DataBase.DatabaseHelper;
import com.example.lider_express.MainActivity;
import com.example.lider_express.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class GiSosuda extends AppCompatActivity {
    public DatabaseHelper mDBHelper;
    public SQLiteDatabase mDb;
    Button butRaschet;
    String metal, niz, verh, a, b, c, d;
    int verhnee, nizhnee, temp;
    double result, sigma, davl;
    EditText textDavlenie, textTempreture;
    TextView textResult;
    int sigmaudel;
    String e;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gi_sosuda);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        butRaschet = (Button) findViewById(R.id.butRaschet);
        textDavlenie = (EditText) findViewById(R.id.textDavlenie);
        textTempreture = (EditText) findViewById(R.id.textTempreture);
        textResult = (TextView) findViewById(R.id.textResult);
        mDBHelper = MainActivity.getDBHelper();//new DatabaseHelper(this);
//        try {
//            mDBHelper.updateDataBase();
//        } catch (IOException mIOException) {
//            throw new Error("UnableToUpdateDatabase");
//        }
        try {
            mDb = mDBHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }

        final AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        String[] cats = getResources().getStringArray(R.array.Металлы);
        List<String> catList = Arrays.asList(cats);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, catList);
        autoCompleteTextView.setAdapter(adapter);

        butRaschet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                metal = autoCompleteTextView.getText().toString();
                c = textDavlenie.getText().toString();
                d = textTempreture.getText().toString();
                davl = Double.parseDouble(c);
                temp = Integer.parseInt(d);
                if (temp <= 100) {
                    niz = "20";
                    verh = "100";
                } else if (temp <= 150) {
                    niz = "100";
                    verh = "150";
                } else if (temp <= 200) {
                    niz = "150";
                    verh = "200";
                } else if (temp <= 250) {
                    niz = "200";
                    verh = "250";
                } else if (temp <= 300) {
                    niz = "250";
                    verh = "300";
                }
                Cursor cursor = mDb.query("Metal", null, "metal = ?", new String[]{metal}, null, null, null);
                cursor.moveToFirst();
                e = cursor.getString(cursor.getColumnIndex("20"));
                a = cursor.getString(cursor.getColumnIndex(niz));
                b = cursor.getString(cursor.getColumnIndex(verh));
                int f = Integer.parseInt(niz);
                int g = Integer.parseInt(verh);
                nizhnee = Integer.parseInt(a);
                verhnee = Integer.parseInt(b);
                sigmaudel = Integer.parseInt(e);
                double h = (double) (temp - f) / (g - f);
                sigma = nizhnee - h * (nizhnee - verhnee);
                h = (sigmaudel / sigma);
                result = davl * 1.25 * h;
                result = Math.round(result * 100.0) / 100.0;
                textResult.setText("Расчётное давление = " + result);
                cursor.close();
            }
        });
    }
}

