package com.example.lider_express.Core.Handlers;

import android.app.Activity;
import android.app.Dialog;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.lider_express.Core.DefectTree.DefectTree;
import com.example.lider_express.Core.Item;
import com.example.lider_express.Core.Types;
import com.example.lider_express.R;

import java.util.HashMap;

public class ButtonHandler {

    public static void setActionSingleHidden(Item mainItem, final Item hiddenItem) {
        mainItem.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hiddenItem.getVisibility() == View.GONE) {
                    hiddenItem.hidden(View.VISIBLE);
                } else {
                    hiddenItem.hidden(View.GONE);

                    if(hiddenItem.type() == Types.LINEAR_LAYOUT){
                        if(hiddenItem.getChilds() != null){
                            Item[] childs = hiddenItem.getChilds();
                            for(int i = 0; i < childs.length; i ++){
                                Item child = childs[i];
                                if(child.type() == Types.EDIT_TEXT ){
                                    ((EditText) child.getView()).setText("");
                                }else if (child.type() == Types.TEXT_VIEW){
                                    ((TextView) child.getView()).setText("");
                                }else if (child.type() == Types.RADIO_GROUP){
                                    ((RadioGroup) child.getView()).clearCheck();
                                }
                            }
                        }
                    }else if(hiddenItem.type() == Types.EDIT_TEXT ){
                        ((EditText) hiddenItem.getView()).setText("");
                    }else if (hiddenItem.type() == Types.TEXT_VIEW){
                        ((TextView) hiddenItem.getView()).setText("");
                    }else if (hiddenItem.type() == Types.RADIO_GROUP){
                        ((RadioGroup) hiddenItem.getView()).clearCheck();
                    }

                }
            }
        });
    }

    public static void setActionShowDialog(Item mainItem, final DefectTree defectTree) {
        mainItem.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                defectTree.showDialog();
            }
        });
    }

    public static void setActionCancelSingleDialog(Item mainItem, final Item item, final DefectTree defectTree) {
        mainItem.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                defectTree.cancelSingleDialog(item);
            }
        });
    }

    public static void setActionInfDialog(final Activity activity, Item itemButton, final int idResource) {

        itemButton.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog tableDialog = new Dialog(activity, R.style.TableDialogStyle);
                tableDialog.setContentView(R.layout.info_dialog);
                ImageView image = (ImageView) tableDialog.findViewById(R.id.info_dialog_image);
                if (image != null) {
                    image.setImageResource(idResource);
                    tableDialog.show();
                }
            }
        });
    }

    public static void setActionCancelDoubleDialog(Item mainItem, final Item item, final DefectTree defectTree) {
        mainItem.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                defectTree.cancelDoubleDialog(item);
            }
        });
    }

    /**
     * @param mainItem
     * @param add      - if True -> add item, if False -> remove item
     * @param items
     */
    public static void setActionAddItemToUI(final Item mainItem, final boolean add, final Item[] items) {
        mainItem.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < items.length; i++) {
                    if (items[i].getVisibility() == View.GONE) {
                        items[i].setVisibility(View.VISIBLE);
                        items[i].getView().setVisibility(View.VISIBLE);
                        break;
                    }
                }

            }
        });
    }

}
