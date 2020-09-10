package com.example.lider_express.Core.Handlers;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.lider_express.Core.Item;
import com.example.lider_express.Core.Types;
import com.example.lider_express.Instruments.SpisokBND;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TextViewHandler {

    public static Calendar dateAndTime = Calendar.getInstance();
    public static View variableView;

    //   }
    //                            if (TYPE == TYPE_SPEC_NKO) {
    //                                variableView = hashViews.get(TYPE).get(0); // Записываем переменные для ActivityResult
    //                                Intent IntentSittings = new Intent(context, SpisokBND.class);
    //                                IntentSittings.putExtra("people", "SPEC_NKO");
    //                                activity.startActivityForResult(IntentSittings, PEOPLE);
    //                            }
    //                            if (TYPE == TYPE_SPEC_JOURNAL) {
    //                                variableView = hashViews.get(TYPE).get(0); // Записываем переменные для ActivityResult
    //                                Intent IntentSittings = new Intent(context, SpisokBND.class);
    //                                IntentSittings.putExtra("people", "SPEC_JOURNAL");
    //                                activity.startActivityForResult(IntentSittings, PEOPLE);
    //                            }
    //                            if (TYPE == TYPE_EXP_JOURNAL) {
    //                                variableView = hashViews.get(TYPE).get(0); // Записываем переменные для ActivityResult
    //                                Intent IntentSittings = new Intent(context, SpisokBND.class);
    //                                IntentSittings.putExtra("people", "EXP_JOURNAL");
    //                                activity.startActivityForResult(IntentSittings, PEOPLE);

    public static final String DESTINATION_DATA = "DATE";
    public static final String DESTINATION_SPEC_NKO = "SPEC_NKO";
    public static final String DESTINATION_SPEC_JOURNAL = "SPEC_JOURNAL";
    public static final String DESTINATION_EXP_JOURNAL = "EXP_JOURNAL";
    public static final String DESTINATION_HMMR_SPEC_NKO = "HMMR_EXP_JOURNAL";



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

    public static void setStartActivityListener(final Activity activity, final Context context, final Item item, final String destination) {
        final TextView textView = ((TextView) item.getView());
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(destination == DESTINATION_DATA){
                    new DatePickerDialog(context, dateSelect(item),
                            dateAndTime.get(Calendar.YEAR),
                            dateAndTime.get(Calendar.MONTH),
                            dateAndTime.get(Calendar.DAY_OF_MONTH))
                            .show();
                }else {
                    variableView = item.getView(); // Записываем переменные для ActivityResult
                    Intent IntentSittings = new Intent(context, SpisokBND.class);

                    if(destination == DESTINATION_SPEC_NKO){
                        IntentSittings.putExtra("people", "SPEC_NKO");
                    }else if(destination == DESTINATION_SPEC_JOURNAL){
                        IntentSittings.putExtra("people", "SPEC_JOURNAL");
                    }else if(destination == DESTINATION_EXP_JOURNAL){
                        IntentSittings.putExtra("people", "EXP_JOURNAL");
                    }else if(destination == DESTINATION_HMMR_SPEC_NKO){
                        IntentSittings.putExtra("people", "HMMR_SPEC_NKO");
                    }

                    activity.startActivityForResult(IntentSittings, 0);
                }
            }
        });
    }


    private static DatePickerDialog.OnDateSetListener dateSelect(final Item item){
        DatePickerDialog.OnDateSetListener pickerDate = new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                dateAndTime.set(Calendar.YEAR, year);
                dateAndTime.set(Calendar.MONTH, monthOfYear);
                dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                ((TextView) item.getView()).setText(new SimpleDateFormat("dd.MM.yyyy").format(new Date(dateAndTime.getTimeInMillis())));
            }
        };
        return pickerDate;
    }

}
