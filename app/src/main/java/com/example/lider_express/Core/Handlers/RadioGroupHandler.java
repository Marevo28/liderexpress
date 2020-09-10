package com.example.lider_express.Core.Handlers;

import android.app.Activity;
import android.app.Dialog;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.lider_express.Core.Item;
import com.example.lider_express.Core.Types;
import com.example.lider_express.R;

import java.util.ArrayList;
import java.util.HashMap;

public class RadioGroupHandler {

    public static String getValueSelectedButton(Activity activity, RadioGroup radioGroup) {
        if (radioGroup.getCheckedRadioButtonId() != -1) {
            return ((RadioButton) activity.findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString();
        } else {
            return null;
        }
    }

    public static ArrayList<RadioButton> getButtons(RadioGroup radioGroup) {
        int count = radioGroup.getChildCount();
        ArrayList<RadioButton> buttons = new ArrayList<RadioButton>();
        for (int i = 0; i < count; i++) {
            View button = radioGroup.getChildAt(i);
            if (button instanceof RadioButton) {
                buttons.add((RadioButton) button);
            }
        }
        return buttons;
    }

    public static void setActionSingleHidden(final Activity activity, Item mainItem, final Item hiddenItem, final String[] negativeValues) {
        ((RadioGroup) mainItem.getView()).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton button = activity.findViewById(checkedId);
                for (int i = 0; i < negativeValues.length; i++) {
                    if (button.getText().toString().equals(negativeValues[i])) {
                        hiddenItem.hidden(View.GONE);
                        if(hiddenItem.type() == Types.LINEAR_LAYOUT){
                            if(hiddenItem.getChilds() != null){
                                Item[] childs = hiddenItem.getChilds();
                                for(int j = 0; j < childs.length; j ++){
                                    Item child = childs[j];
                                    if(child.type() == Types.EDIT_TEXT ){
                                        ((EditText) child.getView()).setText("");
                                    }else if (child.type() == Types.TEXT_VIEW){
                                        ((TextView) child.getView()).setText("");
                                    }else if (child.type() == Types.RADIO_GROUP){
                                        ((RadioGroup) child.getView()).clearCheck();
                                    }
                                }
                            }
                        }
                    } else {
                        hiddenItem.hidden(View.VISIBLE);
                    }
                }
            }
        });
    }

    public static void setActionSingleHiddenPositive(final Activity activity, Item mainItem, final Item hiddenItem, final String[] positiveValues) {
        ((RadioGroup) mainItem.getView()).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton button = activity.findViewById(checkedId);
                for (int i = 0; i < positiveValues.length; i++) {
                    if (button.getText().toString().equals(positiveValues[i])) {
                        hiddenItem.hidden(View.VISIBLE);
                    } else {
                        hiddenItem.hidden(View.GONE);
                        if(hiddenItem.type() == Types.LINEAR_LAYOUT){
                            if(hiddenItem.getChilds() != null){
                                Item[] childs = hiddenItem.getChilds();
                                for(int j = 0; j < childs.length; j ++){
                                    Item child = childs[j];
                                    if(child.type() == Types.EDIT_TEXT ){
                                        ((EditText) child.getView()).setText("");
                                    }else if (child.type() == Types.TEXT_VIEW){
                                        ((TextView) child.getView()).setText("");
                                    }else if (child.type() == Types.RADIO_GROUP){
                                        ((RadioGroup) child.getView()).clearCheck();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        });
    }

    // !!!
    public static void setActionMultipleHidden(final Activity activity, Item mainItem, final Item[] items1, final Item[] items2, final String[] values) {
        ((RadioGroup) mainItem.getView()).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton button = activity.findViewById(checkedId);
                for (int i = 0; i < values.length; i++) {
                    if (button.getText().toString().equals(values[i])) {
                        for(int j = 0; j < items1.length; j++){
                            items1[j].hidden(View.GONE);
                        }
                        for(int j = 0; j < items2.length; j++){
                            items2[j].hidden(View.VISIBLE);
                        }
                    } else {
                        for(int j = 0; j < items1.length; j++){
                            items1[j].hidden(View.VISIBLE);
                        }
                        for(int j = 0; j < items2.length; j++){
                            items2[j].hidden(View.GONE);
                        }
                    }
                }
            }
        });
    }

    public static void setActionShowDialog(final Activity activity, final int radioButton, final int idResource) {
        ((RadioButton) activity.findViewById(radioButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
}
