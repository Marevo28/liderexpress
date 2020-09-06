package com.example.lider_express.Core.Handlers;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.lider_express.Core.DefectTree.DefectTree;
import com.example.lider_express.Core.Item;
import com.example.lider_express.R;
import com.example.lider_express.Tools.BND.PumpControlCard;

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

    public static void setActionInfDialog(final Activity activity, Item itemButton, final int idResource){

        itemButton.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog tableDialog = new Dialog(activity, R.style.TableDialogStyle);
                tableDialog.setContentView(R.layout.info_dialog);
                Log.e("Activitu", activity.getClass().toString() );
                Log.e("IMAGE", String.valueOf(activity.findViewById(R.id.info_dialog_image)));
                ImageView image = (ImageView) activity.findViewById(R.id.info_dialog_image);
                if(image != null){
                    image.setImageResource(idResource);
                    tableDialog.show();
                }
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
