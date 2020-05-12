package com.example.lider_express;

import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Transport_list extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transport_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        int counter=5;
        TableLayout tableLayout =findViewById(R.id.tableoborudovanie);
        for (int i = 0; i < counter; i++) {
            // create a new TableRow
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT));
            // create a new TextView
            TextView date = new TextView(this);
            TextView startkm = new TextView(this);
            TextView endkm = new TextView(this);
            // set the text to "text xx"
            date.setText("date");
            startkm.setText("startkm");
            endkm.setText("endkm");
            // add the TextView and the CheckBox to the new TableRow
            row.addView(date);
            row.addView(startkm);
            row.addView(endkm);
            // add the TableRow to the TableLayout
            tableLayout.addView(row);
        }
    }

}
