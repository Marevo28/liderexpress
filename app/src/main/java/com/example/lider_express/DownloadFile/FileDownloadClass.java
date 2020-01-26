package com.example.lider_express.DownloadFile;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class FileDownloadClass extends AsyncTask<Void, Void, Void> {

    private String url;
    private File destination;

    private Throwable throwable;
    Context context;

    public FileDownloadClass(String url, File destination, ProgressBar fileLoadingListener, Context c) {
        this.url = url;
        this.destination = destination;
        this.context = c;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            FileUtils.copyURLToFile(new URL(url), destination);
        } catch (IOException e) {
            throwable = e;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        Toast.makeText(context, "Обновлено", Toast.LENGTH_LONG).show();
    }
}
