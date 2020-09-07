package com.example.lider_express.Core.Handlers;

import android.app.Activity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.lider_express.Core.Item;

import java.util.ArrayList;

public class RadioGroupHandler{

    public static String getValueSelectedButton(Activity activity, RadioGroup radioGroup){
        if(radioGroup.getCheckedRadioButtonId() != -1){
            return ((RadioButton)activity.findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString();
        }else {
            return null;
        }
    }

    public static ArrayList<RadioButton> getButtons(RadioGroup radioGroup){
        int count = radioGroup.getChildCount();
        ArrayList<RadioButton> buttons = new ArrayList<RadioButton>();
        for (int i=0; i<count; i++) {
            View button = radioGroup.getChildAt(i);
            if (button instanceof RadioButton) {
                buttons.add((RadioButton)button);
            }
        }
        return buttons;
    }

    public static void setActionSingleHidden(final Activity activity, Item mainItem, final Item hiddenItem, final String[] negativeValues){
        ((RadioGroup) mainItem.getView()).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton button = activity.findViewById(checkedId);
                for (int i = 0; i < negativeValues.length; i++){
                    if(button.getText().toString().equals(negativeValues[i])){
                        hiddenItem.hidden(View.GONE);
                    }else{
                        hiddenItem.hidden(View.VISIBLE);
                    }
                }
            }
        });
    }

    public static void setActionSingleHiddenPositive(final Activity activity, Item mainItem, final Item hiddenItem, final String[] positiveValues){
        ((RadioGroup) mainItem.getView()).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton button = activity.findViewById(checkedId);
                for (int i = 0; i < positiveValues.length; i++){
                    if(button.getText().toString().equals(positiveValues[i])){
                        hiddenItem.hidden(View.VISIBLE);
                    }else{
                        hiddenItem.hidden(View.GONE);
                    }
                }
            }
        });
    }

    // !!!
    public static void setActionMultipleHidden(Item mainItem, Item hiddenItem){
        ((RadioGroup) mainItem.getView()).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

            }
        });
    }


}
