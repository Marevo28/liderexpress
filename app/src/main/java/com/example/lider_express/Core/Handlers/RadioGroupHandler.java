package com.example.lider_express.Core.Handlers;

import android.app.Activity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;

public class RadioGroupHandler implements RadioGroup.OnCheckedChangeListener {


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

    }

    public static String getValueSelectedButton(Activity activity, RadioGroup radioGroup){
        return ((RadioButton)activity.findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString();
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


}
