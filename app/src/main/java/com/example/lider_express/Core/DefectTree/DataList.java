package com.example.lider_express.Core.DefectTree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataList {

    public HashMap<String, List<String>> expDetails = new HashMap<>();
    public List<String> oopLanguages = new ArrayList<>();

    public DataList(){
        oopLanguages.add("Продольный");
        oopLanguages.add("Поперечный");
        oopLanguages.add("Глубина");

    }


    public HashMap<String, List<String>> loadData() {
        return expDetails;
    }

    public void setTitle(String title){
        expDetails.put(title, oopLanguages);
    }

}
