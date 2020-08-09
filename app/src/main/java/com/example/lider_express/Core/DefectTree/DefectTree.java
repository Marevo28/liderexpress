package com.example.lider_express.Core.DefectTree;

import android.app.Activity;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class DefectTree{

    private Activity activity;


    public DefectTree(Activity activity){
        this.activity = activity;
    }

//    public void addViewToAdapter(ExpandableListView listView, List<String> listTitle, String title){
//        dataList.setTitle(title);
//        HashMap<String, List<String>> expListDetail = dataList.loadData();
//        listTitle.add(title);
//
//        ExpandableListAdapter expListAdapter = new CustomListAdapter(this, listTitle, expListDetail);
//        listView.setAdapter(expListAdapter);
//    }

}
