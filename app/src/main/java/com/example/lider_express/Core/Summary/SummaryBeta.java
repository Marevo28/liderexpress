package com.example.lider_express.Core.Summary;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.lider_express.Core.Item;

import java.util.LinkedHashMap;


public class SummaryBeta {

    /**
     * Constants
     */

    private Cursor cursor;
    private Context context;
    private Activity activity;

    private SQLiteDatabase database;
    private String readDb; // name data base where read data
    private String writeDb; // name data base where write data

    private String position;

    private LinkedHashMap<Integer, Item> items;

    public SummaryBeta(Activity activity, Context context, @NonNull String readDb, @NonNull String writeDb,
                       @NonNull SQLiteDatabase database, String position) {
        this.context = context;
        this.readDb = readDb;
        this.writeDb = writeDb;
        this.database = database;
        this.activity = activity;
        this.position = position;

        items = new LinkedHashMap<>();

        cursor = database.query(readDb, null, "POSITION = ?",
                new String[]{this.position}, null, null, null);
        cursor.moveToFirst();
    }

    public void addItem(int column, Item item) {
        if (item != null){
            items.put(column, item);
        }else{
            Log.e("SummaryBetta", "Item is null - column: " + column);
        }
    }

    public void setState() {
        String value = null;
        Item item = null;
        for (int column : items.keySet()) {
            value = cursor.getString(column);
            item = items.get(column);

            if (value != null && item != null) {
                item.setState(value);
            }
        }
    }

    public void saveData() {
        ContentValues initialValues = new ContentValues();
        initialValues.put("Position", position);

        String constantPath = "Stolb";
        String column;
        for (int changePath : items.keySet()) {
            column = constantPath + changePath;
            initialValues.put(column, items.get(changePath).getValue());
        }

        for (String k : initialValues.keySet()) {
            Log.e(k + ": => ", initialValues.get(k).toString());
        }

        database.insert(writeDb, null, initialValues);
        displayMessage(activity.getBaseContext(), "Записан: " + position);
    }

    private void displayMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

}
