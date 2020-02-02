package com.example.lider_express.UpdateDB;

import com.example.lider_express.Shared;

import java.io.File;

public class DeleteDB {

    public DeleteDB(String nameDB){
        File file = new File( Shared.pathDB + nameDB);
        if(file.exists()) {
            file.delete();
        }
    }

}
