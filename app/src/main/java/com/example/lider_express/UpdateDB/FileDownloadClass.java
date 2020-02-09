package com.example.lider_express.UpdateDB;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.Toast;

import com.example.lider_express.Shared;

import org.apache.commons.io.FileUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class FileDownloadClass extends AsyncTask<Void, Void, Void> {


    URL url;
    HttpURLConnection httpURLConnection;
    File file;
    FileOutputStream fos;
    InputStream inputStream;
    int totalSize;
    int downloadSize;
    byte[] buffer;
    int bufferLength;

    private Throwable throwable;

    public FileDownloadClass() {

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected Void doInBackground(Void... params) {
        File mFile = new File(Environment.
                getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                + "/llllllllllllllllllllll");
        mFile.mkdirs();
        File mFileWrite = new File(mFile,  "/leaderexpress.db");
        try {
            URL url = new URL("https://drive.google.com/u/0/uc?id=10y1b_wu5M0zO2kFkebMYQ6sojSyw7gWC&export=download");
            BufferedInputStream bis = new BufferedInputStream(url.openStream());
            FileOutputStream fis = new FileOutputStream(mFileWrite);
            byte[] buffer = new byte[1024];
            int count = 0;
            while((count = bis.read(buffer,0,1024)) != -1)
            {
                fis.write(buffer, 0, count);
            }
            fis.close();
            bis.close();

        }catch(IOException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        Toast.makeText(Shared.context, "Обновлено", Toast.LENGTH_LONG).show();
    }

}
