package com.example.lider_express.Core;

import android.app.Activity;
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

    private String tool;
    private String form;
    private String id;
    private String subId;

    // variable for link on column DB
    private String column = "";

    // default - visible
    private int visibility = View.VISIBLE;

    // for Defect Tree
    private int parentId;
    private int referenceId;

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

        type = Types.getTypeView(view);
    }

    /**
     * Constructor for Spinner, ListView
     * @param activity - Parent Activity
     * @param resourceId - Id View
     * @param isGiven - flag, that give info about this View(This View have data, that need write to DataBase)
     * @param adapter
     */
    public Item(Activity activity, int resourceId, boolean isGiven, Adapter adapter){
        this.activity = activity;
        this.resourceId = resourceId;
        this.isGiven = isGiven;
        this.view = activity.findViewById(resourceId);
        this.adapter = adapter;

        type = Types.getTypeView(view);
    }

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

    public Object getViewType(){
        return view.getClass();
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


// Class<? extends View> vClass = viewToDestroy.getClass();
// ViewGroup viewGroup = (ViewGroup) viewToDestroy.getParent();
// View view = vClass.getConstructor(Context.class).newInstance(this);
// viewGroup.addView(view);


}
