package com.example.lider_express.Core.Handlers;

import android.app.Dialog;
import android.view.View;

import com.example.lider_express.Core.DefectTree.DefectTree;
import com.example.lider_express.Core.Item;

public class ButtonHandler {

    public static void setActionSingleHidden(Item mainItem, final Item hiddenItem) {
        mainItem.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(hiddenItem.getVisibility() == View.GONE){
                    hiddenItem.hidden(View.VISIBLE);
                }else{
                    hiddenItem.hidden(View.GONE);
                }
            }
        });
    }

    public static void setActionShowDialog(Item mainItem, final DefectTree defectTree){
        mainItem.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                defectTree.showDialog();
            }
        });
    }

    public static void setActionCancelSingleDialog(Item mainItem, final Item item, final DefectTree defectTree){
        mainItem.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                defectTree.cancelSingleDialog(item);
            }
        });
    }

    public static void setActionCancelDoubleDialog(Item mainItem, final Item item, final DefectTree defectTree){
        mainItem.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                defectTree.cancelDoubleDialog(item);
            }
        });
    }

}
