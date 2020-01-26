package com.example.lider_express;

import android.content.Context;
import androidx.annotation.NonNull;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class RWClass {

    private final String FILE_NAME = "info";
    private String mPath = null;

    private FileInputStream inputStream;

    private FileOutputStream outputStream;

    Context mContext;


    public RWClass(){
        mContext = Shared.context;
    }

    public void writeCountPhoto(@NonNull int countPhoto) throws IOException {
        String str = Integer.toString(countPhoto);

        outputStream = new FileOutputStream(mPath);

        outputStream.write(str.getBytes());

        outputStream.close();
    }

    public int getCountPhoto() throws IOException {
        int countPhoto = 0;
        inputStream = new FileInputStream(mPath);
        int data = inputStream.read();
        char content = 0;
        String str = "";

        while(data != -1) {

            content = (char) data;

            str += Character.toString(content);

            data = inputStream.read();
        }
        inputStream.close();

        if(str.length() > 0) {
            countPhoto = Integer.parseInt(str);
        }
        countPhoto++;
        return countPhoto;
    }

    public void setPath(String path){
        mPath = path + "/" + FILE_NAME;
    }

}
