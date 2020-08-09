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

    public void addSingleConformity(String itemSpinnerLevel1, ArrayList<String> dataList){
        singleBranch.put(itemSpinnerLevel1, dataList);
    }

    public String[] getItemsForSpinner(){
        String[] items = new String[singleBranch.size()];
        ArrayList<String> listItems = new ArrayList<>();
        for(String key: singleBranch.keySet()){
            listItems.add(key);
        }
        for(int i = 0; i < items.length; i++){
            items[i] = listItems.get(i);
        }
        return items;
    }

    public void customizeSingleDialog() {
        dialogAddProperty = new Dialog(context, R.style.TableDialogStyle);
        dialogAddProperty.setContentView(R.layout.defect_tree_dialog);
    }

    public void showSingleDialog() {
        dialogAddProperty.show();
    }

    public void cancelSingleDialog(Item item) {
        String title = spinnerLevel1.getSelectedItem().toString();
        DataList dataList = new DataList();
        for(String key: singleBranch.keySet()){
            if(title.equals(key)){
                dataList.setTitle(title, singleBranch.get(title));
                ExpandableListViewHandle.addViewToAdapter(activity, ((ExpandableListView) item.getView()), dataList, expListTitle, title);
            }
        }
        dialogAddProperty.cancel();
        ExpandableListViewHandle.setListViewHeightBasedOnItems((ExpandableListView) item.getView());
    }

    public Dialog getDialogAddProperty(){
        return dialogAddProperty;
    }

    public void setSpinnerLevel1(Spinner spinnerLevel1){
        this.spinnerLevel1 = spinnerLevel1;
    }

    public void setSpinnerLevel2(Spinner spinnerLevel2){
        this.spinnerLevel2 = spinnerLevel2;
    }


}
