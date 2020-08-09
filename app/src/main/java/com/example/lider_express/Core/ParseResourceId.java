package com.example.lider_express.Core;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;

import java.util.HashMap;

public class ParseResourceId {

    public static HashMap<String, String> parse(Activity activity, int idResource){
        HashMap<String, String> description = new HashMap<>();
        View view = activity.findViewById(idResource);
        String fullName = activity.getResources().getResourceName(idResource);
        String[] fields = fullName.split("/")[1].split("_");
        description.put("location", fields[0]);
        description.put("section", fields[1]);
        description.put("form", fields[2]);
        description.put("id", fields[3]);
        return description;
    }

    public static HashMap<String, String> parse(Activity activity, Dialog dialog, int idResource){
        HashMap<String, String> description = new HashMap<>();
        View view = dialog.findViewById(idResource);
        String fullName = activity.getResources().getResourceName(idResource);
        String[] fields = fullName.split("/")[1].split("_");
        description.put("location", fields[0]);
        description.put("section", fields[1]);
        description.put("form", fields[2]);
        description.put("id", fields[3]);
        return description;
    }

}
