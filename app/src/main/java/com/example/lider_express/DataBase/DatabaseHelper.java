package com.example.lider_express.DataBase;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.lider_express.MainActivity;
import com.example.lider_express.Shared;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author LeStat
 * Смысл такой -
 * При первом запуске базу данных копируем из папки Assets в папку для обновления БД
 * далее копируем БД из папки для обновления в системную папку для хранения базы данных
 *
 * При непервичном запуске запуске копируем БД из папки для обновления -
 * в системную папку для хранения базы данных
 * Далее при обновлении - удаляем обе базы, качаем новый файл в папку
 * для обновления, после копируем оттуда в системную папку БД
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static String DB_PATH = "";

    private static final int MY_PERMISSIONS_REQUEST_STORAGE = 1;

    public DatabaseHelper(Context context, String nameDB, int version) {
        super(context, nameDB, null, version);


        if (android.os.Build.VERSION.SDK_INT >= 17) {
            DB_PATH = context.getDatabasePath(nameDB).getPath();
        }
        else {
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        }

        copyDataBase();

        this.getWritableDatabase();
    }

    private void copyDataBase() {
            try {
                copyDBFile();
            } catch (IOException mIOException) {
                throw new Error("ErrorCopyingDataBase");
            }
    }


    private void copyDBFile() throws IOException {
        /** ########################### --- Первый Запуск приложения
         * копируем базу данных из assets в папку для обновления базы данных
         */
        if(MainActivity.getFirstRun()) {
              // Это первый запуск
            Log.e("Start", "Первый зпуск!!!");
            MainActivity.setRunned();

            File pathUpDB = new File(Shared.pathUpdateDB);  // путь к файлу
            if(!pathUpDB.exists()){   // если такого пути нет, создаем его
                pathUpDB.mkdir();
            }
            File fullPathUpDB = new File(pathUpDB, Shared.nameUpdateDB);
            try {
                fullPathUpDB.createNewFile(); // создаем сам файл
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(fullPathUpDB.exists()) {
                System.out.println("----------------- File : " + fullPathUpDB + " Создался");
            }else{
                System.out.println("----------------- File : " + fullPathUpDB + " Не Создался");
            }

            // Копируем Базу данных из папки Assets в папку для обновления базы данных
            InputStream mInput = Shared.context.getAssets().open(Shared.nameUpdateDB);
            OutputStream mOutput = new FileOutputStream(fullPathUpDB);
            byte[] mBuffer = new byte[1024];
            int mLength;
            while ((mLength = mInput.read(mBuffer)) > 0)
                mOutput.write(mBuffer, 0, mLength);
            mOutput.flush();
            mOutput.close();
            mInput.close();

            // Копируем Базу данных из папки для обновления в системную папку
            InputStream is = null;
            OutputStream os = null;
            File source = new File(Shared.pathUpdateDB + "/" + Shared.nameUpdateDB);

            File pathDB = new File(Shared.pathDB);  // путь к файлу
            if(!pathDB.exists()){   // если такого пути нет, создаем его
                pathDB.mkdir();
            }
            File fullPathDB = new File(pathDB, Shared.nameDB);
            if(!fullPathDB.exists()) {
                try {
                    fullPathDB.createNewFile(); // создаем сам файл
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            
            try {
                is = new FileInputStream(source);
                os = new FileOutputStream(fullPathDB);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = is.read(buffer)) > 0) {
                    os.write(buffer, 0, length);
                }
            } finally {
                is.close();
                os.close();
            }
        }
        else {
            /** ########################### --- Не Первый Запуск приложения
             * копирум базу данных из папки для обновы в системную папку
             */
            Log.e("Start", "Не первый зпуск!!!");
              // Копируем Базу данных из папку с базой в системную папку
            InputStream is = null;
            OutputStream os = null;
            File source = new File(Shared.pathUpdateDB + "/" + Shared.nameUpdateDB);

            File pathDB = new File(Shared.pathDB);  // путь к файлу
            if(!pathDB.exists()){   // если такого пути нет, создаем его
                pathDB.mkdir();
            }
            File fullPathDB = new File(pathDB, Shared.nameDB);
            if(!fullPathDB.exists()) {
                try {
                    fullPathDB.createNewFile(); // создаем сам файл
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            try {
                is = new FileInputStream(source);
                os = new FileOutputStream(fullPathDB);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = is.read(buffer)) > 0) {
                    os.write(buffer, 0, length);
                }
            } finally {
                is.close();
                os.close();
            }

        }

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}