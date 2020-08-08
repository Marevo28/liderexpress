package com.example.lider_express.Core;

import android.app.Activity;
import android.view.View;

public class ParseResourceId {

    public static String tool;

    public static String form;

    public static String mainId;

    public static String subId;

    public static Activity activity;

    public ParseResourceId(Activity activity){
        this.activity = activity;
    }

    public static void parse(int idResource){
        View view = activity.findViewById(idResource);
        String fullName = activity.getResources().getResourceName(view.getId());
        String name = fullName.substring(fullName.lastIndexOf("/") + 1);

        String[] names = name.split("_");

        if(names[0] != null)
            tool = names[0];

        if(names[1] != null)
            form = names[1];

        if(names[2] != null)
            mainId = names[2];

        if(names[3] != null)
            subId = names[3];
    }



}
