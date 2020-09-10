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
        if(typeView.contains("TextView")) {
            type = TEXT_VIEW;
        } else if (typeView.contains("EditText")) {
            type = EDIT_TEXT;
        } else if (typeView.contains("Button")) {
            type = BUTTON;
        } else if (typeView.contains("RadioGroup")) {
            type = RADIO_GROUP;
        } else if (typeView.contains("RadioButton")) {
            type = RADIO_BUTTON;
        } else if (typeView.contains("ImageButton")) {
            type = IMAGE_BUTTON;
        } else if (typeView.contains("ListView")) {
            type = LIST_VIEW;
        } else if (typeView.contains("ExpandableListView")) {
            type = EXPANDABLE_LIST_VIEW;
        } else if (typeView.contains("Spinner")) {
            type = SPINNER;
        } else if (typeView.contains("LinearLayout")) {
            type = LINEAR_LAYOUT;
        }
        return type;
    }
}
