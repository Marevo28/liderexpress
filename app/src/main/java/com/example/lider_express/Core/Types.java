package com.example.lider_express.Core;

import android.view.View;

public class Types {

    public static final String TEXT_VIEW = "TextView";
    public static final String EDIT_TEXT = "EditText";
    public static final String BUTTON = "Button";
    public static final String RADIO_GROUP = "RadioGroup";
    public static final String RADIO_BUTTON = "RadioButton";
    public static final String IMAGE_BUTTON = "ImageButton";
    public static final String APP_COMPAT_BUTTON = "AppCompatButton";
    public static final String LIST_VIEW = "ListView";
    public static final String EXPANDABLE_LIST_VIEW = "ExpandableListView";
    public static final String SPINNER = "Spinner";
    public static final String LINEAR_LAYOUT = "LinearLayout";

    public static String getTypeView(View view){
        String type = "This type are not supported";
        // separate class view and get last element array
        // ["android", "widget", "Button"]
        int lastElement = view.getClass().getName().split("\\.").length - 1;
        String typeView = view.getClass().getName().split("\\.")[lastElement];
        switch (typeView){
            case "TextView": type = TEXT_VIEW; break;
            case "EditText": type = EDIT_TEXT; break;
            case "Button": type = BUTTON; break;
            case "AppCompatButton": type = APP_COMPAT_BUTTON; break;
            case "RadioGroup": type = RADIO_GROUP; break;
            case "RadioButton": type = RADIO_BUTTON; break;
            case "ImageButton": type = IMAGE_BUTTON; break;
            case "ListView": type = LIST_VIEW; break;
            case "ExpandableListView": type = EXPANDABLE_LIST_VIEW; break;
            case "Spinner": type = SPINNER; break;
            case "LinearLayout": type = LINEAR_LAYOUT; break;
        }
        return type;
    }
}
