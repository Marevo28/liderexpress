package com.example.lider_express.Core;

import android.view.View;

public class Types {

    public static final String TEXT_VIEW = "Text view";
    public static final String EDIT_TEXT = "Edit text";
    public static final String BUTTON = "Button";
    public static final String RADIO_GROUP = "Radio group";
    public static final String RADIO_BUTTON = "Radio button";
    public static final String IMAGE_BUTTON = "Image button";
    public static final String LIST_VIEW = "List view";
    public static final String SPINNER = "Spinner";
    public static final String LINEAR_LAYOUT = "Linear layout";

    public static String getTypeView(View view){
        String type = "This type are not supported";
        // separate class view and get last element array
        // ["android", "widget", "Button"]
        int lastElement = view.getClass().toString().split(".").length - 1;
        String typeView = view.getClass().toString().split(".")[lastElement];
        switch (typeView){
            case "TextView": type = TEXT_VIEW; break;
            case "EditText": type = EDIT_TEXT; break;
            case "Button": type = BUTTON; break;
            case "RadioGroup": type = RADIO_GROUP; break;
            case "RadioButton": type = RADIO_BUTTON; break;
            case "ImageButton": type = IMAGE_BUTTON; break;
            case "ListView": type = LIST_VIEW; break;
            case "Spinner": type = SPINNER; break;
            case "LinearLayout": type = LINEAR_LAYOUT; break;
        }
        return type;
    }
}
