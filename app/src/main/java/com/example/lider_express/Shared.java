package com.example.lider_express;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lider_express.DataBase.DatabaseHelper;

public class Shared {

    public static AppCompatActivity appCompatActivity = MainActivity.getAppCompatActivity();
    public static Context context = MainActivity.getContext();
    public static DatabaseHelper databaseHelper = MainActivity.getDBHelper();
    public static SQLiteDatabase mDb = MainActivity.getSQLiteDatabase();
    public static String nameDB = "leaderexpress.db";
    public static String nameBND = "ZayavkaBND";
    public static String nameMegion = "Megion2019";
    public static String pathDB = Shared.context.getFilesDir().getPath()+"/../databases/";
    public static String URL = "https://drive.google.com/uc?export=download&id=1eQd_wpkcixYyiz1xKiNvKdmKA_QRe9Tf";


}
