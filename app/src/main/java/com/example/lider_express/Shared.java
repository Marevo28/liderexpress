package com.example.lider_express;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

public class Shared {

    public static AppCompatActivity appCompatActivity = MainActivity.getAppCompatActivity();
    public static Context context = MainActivity.getContext();
    public static String nameBND = "ZayavkaBND";
    public static String nameMegion = "Megion2019";

}
