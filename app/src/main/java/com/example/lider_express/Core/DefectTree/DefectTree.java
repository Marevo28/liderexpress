package com.example.lider_express.Core.DefectTree;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.widget.ExpandableListView;
import android.widget.Spinner;

import com.example.lider_express.Core.Handlers.ExpandableListViewHandle;
import com.example.lider_express.Core.Item;
import com.example.lider_express.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DefectTree {

    private Activity activity;
    private Context context;
    private Dialog dialogAddProperty;
    final List<String> expListTitle = new ArrayList<>();
    private Spinner spinnerLevel1;
    private Spinner spinnerLevel2;
    private DataList dataList;

    private HashMap<String, ArrayList<String>> singleBranch;
    private HashMap<String, HashMap<String, ArrayList<String>>> doubleBranch;


    public DefectTree(Activity activity, Context context) {
        this.activity = activity;
        this.context = context;
        singleBranch = new HashMap<>();
        doubleBranch = new HashMap<>();
    }

    public void addSingleConformity(String itemSpinnerLevel1, ArrayList<String> dataList) {
        singleBranch.put(itemSpinnerLevel1, dataList);
    }

    public void addDoubleConformity(String itemSpinnerLevel1, String itemSpinnerLevel2, ArrayList<String> dataList) {
        HashMap<String, ArrayList<String>> conformity;
        if (doubleBranch.get(itemSpinnerLevel1) != null) {
            conformity = doubleBranch.get(itemSpinnerLevel1);
        } else {
            conformity = new HashMap<>();
        }
        conformity.put(itemSpinnerLevel2, dataList);
        doubleBranch.put(itemSpinnerLevel1, conformity);
    }

    public String[] getItemsSingleBranch() {
        String[] items = new String[singleBranch.size()];
        ArrayList<String> listItems = new ArrayList<>();
        for (String key : singleBranch.keySet()) {
            listItems.add(key);
        }
        for (int i = 0; i < items.length; i++) {
            items[i] = listItems.get(i);
        }
        return items;
    }

    public String[] getItemsDoubleBranchLevel1() {
        ArrayList<String> listItems = new ArrayList<>();
        for (String key: doubleBranch.keySet()) {
            listItems.add(key);
        }
        String[] items = new String[listItems.size()];
        for (int i = 0; i < items.length; i++) {
            items[i] = listItems.get(i);
        }
        return items;
    }

    public String[] getItemsDoubleBranchLevel2(String valueSpinnerLevel1) {
        ArrayList<String> listItems = new ArrayList<>();
        for (String key: doubleBranch.keySet()) {
            if(valueSpinnerLevel1 == key){
                for(String k: doubleBranch.get(key).keySet()){
                    listItems.add(k);
                }
            }
        }
        String[] items = new String[listItems.size()];
        for (int i = 0; i < items.length; i++) {
            items[i] = listItems.get(i);
        }
        return items;
    }

    public void customizeSingleDialog() {
        dialogAddProperty = new Dialog(context, R.style.TableDialogStyle);
        dialogAddProperty.setContentView(R.layout.defect_tree_single_dialog);
    }

    public void customizeDoubleDialog() {
        dialogAddProperty = new Dialog(context, R.style.TableDialogStyle);
        dialogAddProperty.setContentView(R.layout.defect_tree_double_dialog);
    }

    public void showDialog() {
        dialogAddProperty.show();
    }

    public void cancelSingleDialog(Item item) {
        String title = spinnerLevel1.getSelectedItem().toString();
        DataList dataList = new DataList();
        for (String key : singleBranch.keySet()) {
            if (title.equals(key)) {
                dataList.setTitle(title, singleBranch.get(title));
                ExpandableListViewHandle.addViewToAdapter(activity, ((ExpandableListView) item.getView()), dataList, expListTitle, title);
            }
        }
        dialogAddProperty.cancel();
        ExpandableListViewHandle.setListViewHeightBasedOnItems((ExpandableListView) item.getView());
    }

    public void cancelDoubleDialog(Item item) {
        String titleLevel1 = spinnerLevel1.getSelectedItem().toString();
        String titleLevel2 = spinnerLevel2.getSelectedItem().toString();
        DataList dataList = new DataList();
        for (String key : doubleBranch.keySet()) {
            if (titleLevel1.equals(key)) {
                HashMap<String, ArrayList<String>> Conformity = doubleBranch.get(key);
                for(String k: Conformity.keySet()){
                    if(titleLevel2.equals(k)){
                        String title = titleLevel1 + "(" + titleLevel2 + ")";
                        dataList.setTitle(title, Conformity.get(k));
                        ExpandableListViewHandle.addViewToAdapter(activity, ((ExpandableListView) item.getView()), dataList, expListTitle, title);
                    }
                }

            }
        }
        dialogAddProperty.cancel();
        ExpandableListViewHandle.setListViewHeightBasedOnItems((ExpandableListView) item.getView());
    }

    public Dialog getDialogAddProperty() {
        return dialogAddProperty;
    }

    public void setSpinnerLevel1(Spinner spinnerLevel1) {
        this.spinnerLevel1 = spinnerLevel1;
    }

    public void setSpinnerLevel2(Spinner spinnerLevel2) {
        this.spinnerLevel2 = spinnerLevel2;
    }


}
