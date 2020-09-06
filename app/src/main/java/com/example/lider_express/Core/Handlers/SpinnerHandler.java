package com.example.lider_express.Core.Handlers;

import android.app.Activity;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.lider_express.Core.DefectTree.DefectTree;
import com.example.lider_express.Core.Item;

import java.util.ArrayList;
import java.util.HashMap;

public class SpinnerHandler{


    public static void setListenerDoubleSpinner(final Activity activity, final Item spinnerLevel1, final Item spinnerLevel2, final DefectTree defectTree){
        ((Spinner) spinnerLevel1.getView()).setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // spinnerKorozLocal.getItemAtPosition(position).toString()
                final ArrayAdapter<String>  adapter = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1,
                        defectTree.getItemsDoubleBranchLevel2(((Spinner) spinnerLevel1.getView()).getItemAtPosition(position).toString()));
                ((Spinner) spinnerLevel2.getView()).setAdapter(adapter);
                defectTree.setSpinnerLevel2(((Spinner) spinnerLevel2.getView()));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });
    }

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
