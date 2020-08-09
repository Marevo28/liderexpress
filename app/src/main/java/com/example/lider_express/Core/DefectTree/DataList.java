package com.example.lider_express.Core.DefectTree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataList {

    // String Title -> List<String> dimensions
    public HashMap<String, List<String>> expDetails = new HashMap<>();

    public DataList(){
    }

    public HashMap<String, List<String>> loadData() {
        return expDetails;
    }

    public void setTitle(String title, ArrayList<String> properties){
        expDetails.put(title, properties);
    }

}
