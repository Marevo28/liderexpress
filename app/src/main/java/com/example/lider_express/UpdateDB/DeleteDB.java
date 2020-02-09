package com.example.lider_express.UpdateDB;

import com.example.lider_express.Shared;

import java.io.File;

public class DeleteDB {


    public DeleteDB(){
        File file = new File(Shared.context.getDatabasePath(Shared.nameDB).getPath());
        if(file.exists()) {
            file.delete();
        }
        File file2 = new File(Shared.pathDB + "/" + Shared.nameDB + "-shm");
        if(file2.exists()) {
            file2.delete();
        }
        File file3 = new File(Shared.pathDB + "/" + Shared.nameDB + "-wal");
        if(file3.exists()) {
            file3.delete();
        }
    }

}
