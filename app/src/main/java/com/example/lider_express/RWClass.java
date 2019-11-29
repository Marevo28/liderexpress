package com.example.lider_express;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import static android.content.Context.MODE_PRIVATE;

public class RWClass {

    /**
     * Данный класс необходим для чередования фото
     * и предотвращения перезаписи фото
     * */

    private final String FILE_NAME = "info";
    private String mPath = null;
    // Класс для работы потоком вывода из файла
    private FileInputStream inputStream;

    // Класс для работы потоком ввода в файл
    private FileOutputStream outputStream;

    Context mContext;


    public RWClass(){
        mContext = MainActivity.context;
    }

    // Записываем каунт
    public void writeCountPhoto(@NonNull int countPhoto) throws IOException {
        String str = Integer.toString(countPhoto);

        // открываем поток ввода в файл
        outputStream = new FileOutputStream(mPath);

        // записываем данные в файл
        outputStream.write(str.getBytes());

        outputStream.close();
    }

    // Возвращаем каунт
    public int getCountPhoto() throws IOException {
        int countPhoto = 0;
        inputStream = new FileInputStream(mPath);
        // читаем первый символ с потока байтов
        int data = inputStream.read();
        char content = 0;
        String str = "";
        // если data будет равна 0 то это значит,
        // что файл пуст
        while(data != -1) {
            // переводим байты в символ
            content = (char) data;
            // переводим символ в стринг
            str += Character.toString(content);

            // читаем следующий байты символа
            data = inputStream.read();
        }
        // закрываем поток чтения файла
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
