package com.example.lider_express;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Oborudovanie extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oborudovanie);
        int counter=5;
        TableLayout tableLayout =findViewById(R.id.tableoborudovanie);
        for (int i = 0; i < counter; i++) {
            // create a new TableRow
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT));
            // create a new TextView
            TextView type = new TextView(this);
            TextView mark = new TextView(this);
            TextView zav = new TextView(this);
            TextView status = new TextView(this);
            Button peredacha = new Button(this);
            peredacha.setText("Передать");
            // set the text to "text xx"
            type.setText("type");
            mark.setText("mark");
            zav.setText("zav");
            status.setText("status");
            // add the TextView and the CheckBox to the new TableRow
            row.addView(type);
            row.addView(mark);
            row.addView(zav);
            row.addView(status);
            row.addView(peredacha);
            // add the TableRow to the TableLayout
            tableLayout.addView(row);
        }
    }



}
