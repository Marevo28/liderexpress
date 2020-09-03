package com.example.lider_express.Core;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.lider_express.Core.Handlers.RadioGroupHandler;
import com.example.lider_express.Core.Handlers.SpinnerHandler;

import java.util.ArrayList;
import java.util.HashMap;

public class Item {

    // This class describe View as object.
    // All View will have properties, that describe view and data paths View

    private Activity activity;

    private int resourceId;
    private View view;
    private String type;
    private boolean isGiven;
    private String value;
    private Adapter adapter;

    private String location = "location";
    private String section = "section";
    private String form = "form";
    private String id = "id";
    private String subId = "subId";

    // variable for link on column DB
    private String tableName = "";
    private String column = "";

    // default - visible
    private int visibility = View.VISIBLE;

    // for Defect Tree
    private int parentId;
    private int referenceId;

    public HashMap<String, String> params(){
        HashMap<String, String> params = new HashMap<>();
        params.put("Activity", activity.toString());
        params.put("ResourceId", String.valueOf(resourceId));
        params.put("View", view.toString());
        params.put("Type", type);
        params.put("Is given", String.valueOf(isGiven));
        params.put("Value", value);
        params.put("Location", location);
        params.put("Section", section);
        params.put("Form", form);
        params.put("Id", id);
        params.put("Sub id", subId);
        params.put("Table name", tableName);
        params.put("Column", column);
        params.put("visibility", String.valueOf(visibility));
        params.put("Parent id", value);
        params.put("Reference id", value);

        return params;
    }

    /**
     * Constructor for TextView, EditText, Button, RadioGroup
     * @param activity - Parent Activity
     * @param resourceId - Id View
     * @param isGiven - flag, that give info about this View(This View have data, that need write to DataBase)
     */
    public Item(Activity activity, int resourceId, boolean isGiven){
        this.activity = activity;
        this.resourceId = resourceId;
        this.isGiven = isGiven;
        this.view = activity.findViewById(resourceId);
        view.setVisibility(visibility);

        type = Types.getTypeView(view);

        HashMap<String, String> description = ParseResourceId.parse(activity, resourceId);
        location = description.get(location);
        section = description.get(section);
        form = description.get(form);
        id = description.get(id);
    }

    /**
     * Constructor for TextView, EditText, Button, RadioGroup
     * @param activity - Parent Activity
     * @param resourceId - Id View
     * @param isGiven - flag, that give info about this View(This View have data, that need write to DataBase)
     */
    public Item(Activity activity, int resourceId, boolean isGiven, int visibility){
        this.activity = activity;
        this.resourceId = resourceId;
        this.isGiven = isGiven;
        this.view = activity.findViewById(resourceId);
        this.visibility = visibility;
        view.setVisibility(visibility);

        type = Types.getTypeView(view);

        HashMap<String, String> description = ParseResourceId.parse(activity, resourceId);
        location = description.get(location);
        section = description.get(section);
        form = description.get(form);
        id = description.get(id);
    }

    /**
     * Constructor for TextView, EditText, Button, RadioGroup
     * @param dialog
     * @param resourceId - Id View
     * @param isGiven - flag, that give info about this View(This View have data, that need write to DataBase)
     */
    public Item(Activity activity, Dialog dialog, int resourceId, boolean isGiven){
        this.activity = activity;
        this.resourceId = resourceId;
        this.isGiven = isGiven;
        this.view = dialog.findViewById(resourceId);
        view.setVisibility(visibility);

        type = Types.getTypeView(view);

        HashMap<String, String> description = ParseResourceId.parse(activity, dialog, resourceId);
        location = description.get(location);
        section = description.get(section);
        form = description.get(form);
        id = description.get(id);
    }

//    /**
//     * Constructor for Spinner, ListView
//     * @param activity - Parent Activity
//     * @param resourceId - Id View
//     * @param isGiven - flag, that give info about this View(This View have data, that need write to DataBase)
//     * @param adapter
//     */
//    public Item(Activity activity, int resourceId, boolean isGiven, Adapter adapter){
//        this.activity = activity;
//        this.resourceId = resourceId;
//        this.isGiven = isGiven;
//        this.view = activity.findViewById(resourceId);
//        this.adapter = adapter;
//
//        type = Types.getTypeView(view);
//    }

    // Set view values like DB
    public void setState(String value){
        switch (type){
            case Types.TEXT_VIEW:
                ((TextView) view).setText(value);
                break;
            case Types.EDIT_TEXT:
                ((EditText) view).setText(value);
                break;
            case Types.RADIO_GROUP:
                ArrayList<RadioButton> buttons = RadioGroupHandler.getButtons((RadioGroup) view);
                for(RadioButton button: buttons){
                    if(button.getText().toString().equals(value)){
                        ((RadioGroup) view).check(button.getId());
                    }
                }
                break;
            case Types.SPINNER:
                ArrayList<String> items = SpinnerHandler.getItems((Spinner) view);
                for(String item: items){
                    if(item.equals(value)){
                        ((Spinner) view).setSelection(SpinnerHandler.getIndexOnValue((Spinner) view, item));
                    }
                }
                break;
        }
    }

    public String getValue(){
        switch (type){
            case Types.TEXT_VIEW:
                value = ((TextView) view).getText().toString();
                break;
            case Types.EDIT_TEXT:
                value = ((EditText) view).getText().toString();
                break;
            case Types.BUTTON:
                value = ((Button) view).getText().toString();
                break;
            case Types.RADIO_GROUP:
                value = RadioGroupHandler.getValueSelectedButton(activity, (RadioGroup) view);
                break;
            case Types.SPINNER:
                value = SpinnerHandler.getValueSelectedItem((Spinner) view);
                break;
        }
        return value;
    }

    // set visibility
    public void hidden(int visibility){
        this.visibility = visibility;
        view.setVisibility(visibility);
    }

    /** GETTERS and SETTERS **/

    // Resource Id View
    public int getResourceId() {
        return resourceId;
    }

    // This View have data that need write to DataBase?
    public boolean isGiven(){
        return isGiven;
    }

    public int getVisibility(){
        return visibility;
    }

    public void setVisibility(int visibility){
        this.visibility = visibility;
    }

    public View getView(){
        return view;
    }

    public String type(){
        return type;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    /** Defect Tree */

    public int getParentId(){
        return parentId;
    }

    public void setParentId(int parentId){
        this.parentId = parentId;
    }

    public int getReferenceId(){
        return referenceId;
    }

    public void setReferenceId(int parentId){
        this.referenceId = referenceId;
    }

    public Adapter getAdapter() {
        return adapter;
    }

    public void setAdapter(Adapter adapter) {
        this.adapter = adapter;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }


// Class<? extends View> vClass = viewToDestroy.getClass();
// ViewGroup viewGroup = (ViewGroup) viewToDestroy.getParent();
// View view = vClass.getConstructor(Context.class).newInstance(this);
// viewGroup.addView(view);


}
