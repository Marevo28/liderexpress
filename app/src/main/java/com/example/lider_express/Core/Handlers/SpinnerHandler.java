package com.example.lider_express.Core.Handlers;

import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Spinner;

import java.util.ArrayList;

public class SpinnerHandler{

    public static String getValueSelectedItem(Spinner spinner){
        return spinner.getItemAtPosition(spinner.getSelectedItemPosition()).toString();
    }

    public static ArrayList<String> getItems(Spinner spinner){
        Adapter adapter = spinner.getAdapter();
        int count = adapter.getCount();
        ArrayList<String> items = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            items.add((String)adapter.getItem(i));
        }
        return items;
    }

    public static int getIndexOnValue(Spinner spinner, String value){
        int index = 0;
        for (int i = 0; i < spinner.getCount(); i++){
            if (spinner.getItemAtPosition(i).toString().equals(value)){
                index = i;
            }
        }
        return index;
    }
}
