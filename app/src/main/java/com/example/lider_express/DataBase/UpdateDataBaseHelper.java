package com.example.lider_express.DataBase;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lider_express.MainActivity;
import com.example.lider_express.Shared;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author LeStat
 * Этот класс создан для обновления баззы данных
 * Просто качаем новую БД в папку для обновления БД
 */

public class UpdateDataBaseHelper extends AsyncTask<Void, Void, Void> {

    private ProgressBar progressbar;

    private String mURL = "";
    File mFile;
    byte[] buffer;
    int totalSize;

    AppCompatActivity mActivity;
    SQLiteDatabase mDb;

    public UpdateDataBaseHelper(AppCompatActivity act, SQLiteDatabase sqldb,
                                String url, String path, String nameDB, ProgressBar pb) {
        mActivity = act;
        mDb = sqldb;
        mURL = url; //URL
        File file = new File(path); // Путь к файлу
        if(!file.exists()) {  // если дериктории не существует, создаем ее
            file.mkdirs();
        }
        mFile = new File(file,  nameDB);// Полный путь к файлу
        progressbar = pb;
    }

    @Override
    protected void onPreExecute() {
        progressbar.setVisibility(View.VISIBLE);
    }


    @Override
    protected Void doInBackground(Void... params) {

        // Далее качаем файл базы данных для обновления

        try {
            URL url = new URL(mURL);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            BufferedInputStream bis = new BufferedInputStream(url.openStream());
            FileOutputStream fis = new FileOutputStream(mFile);
            buffer = new byte[1024];
            int count = 0;
            long bSize = 0;
            totalSize = urlConnection.getContentLength();

            while((count = bis.read(buffer,0,1024)) != -1)
            {
                fis.write(buffer, 0, count);
                bSize += count;

                Log.e("Async", String.valueOf(bSize));
            }

            fis.close();
            bis.close();

        }catch(IOException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    // обновляем progressDialog
    @Override
    protected void onPostExecute(Void params) {

        // закрываем прогресс и удаляем временный файл\
        progressbar.setVisibility(View.INVISIBLE);
        Toast.makeText(Shared.context, "Обновлено!", Toast.LENGTH_LONG);

        Shared.flagUpdate = true;

        Intent intent = new Intent(mActivity, MainActivity.class);
        mActivity.startActivity(intent);
    }


}





