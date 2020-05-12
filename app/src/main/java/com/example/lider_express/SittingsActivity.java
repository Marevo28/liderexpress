package com.example.lider_express;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SittingsActivity extends AppCompatActivity {
    String[] Zakazchiki = {//"Башнефть 2019", "Мегион 2019", "Полюс 2019",
            "Башнефть 2020", "Мегион 2020", // "Полюс 2020",
            //"Башнефть 2021", "Мегион 2021", "Полюс 2021"
    };
    String[] Cameras = {"Camera API2","Open Camera"};

    String Zakazchik,SelectedCamera, user, password;
    Button ButSave, ButEnter;
    TextView TextZakazchik,TextCamera;
    EditText TextUser, TextPassword;
    public static final String APP_FILES = "mysettings";
    public static final String APP_ZAKAZCHIK = "Zakazchik";
    public static final String APP_CAMERA = "Camera";
    public static final String APP_USER = "User";
    public static final String APP_PASSWORD = "Password";
    private String result = null;
    private String line = null;
    private InputStream is = null;
    SharedPreferences mSettings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sittings);
        Toolbar toolbar = findViewById(R.id.toolbar);
        ButSave = findViewById(R.id.ButSave);
        ButEnter = findViewById(R.id.ButEnter);
        TextZakazchik = findViewById(R.id.TextZakazchik);
        TextCamera = findViewById(R.id.TextCamera);
        TextUser = findViewById(R.id.TextUser);
        TextPassword = findViewById(R.id.TextPassword);
        mSettings = getSharedPreferences(APP_FILES, MODE_PRIVATE);
        setSupportActionBar(toolbar);

        // адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Zakazchiki);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(adapter);
        // заголовок
        spinner.setPrompt("Title");
        // выделяем элемент
        spinner.setSelection(0);
        // устанавливаем обработчик нажатия


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // показываем позиция нажатого элемента
                //Toast.makeText(getBaseContext(), "Position = " + position, Toast.LENGTH_SHORT).show();
                Zakazchik = spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        // адаптер
        ArrayAdapter<String> adapterCamera = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Cameras);
        adapterCamera.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        final Spinner spinnerCamera = (Spinner) findViewById(R.id.spinnerCamera);
        spinnerCamera.setAdapter(adapterCamera);
        // заголовок
        spinnerCamera.setPrompt("Title");
        // выделяем элемент
        spinnerCamera.setSelection(0);
        // устанавливаем обработчик нажатия
        spinnerCamera.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // показываем позиция нажатого элемента
                //Toast.makeText(getBaseContext(), "Position = " + position, Toast.LENGTH_SHORT).show();
                SelectedCamera = spinnerCamera.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        ButSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextZakazchik.setText(Zakazchik);
                TextCamera.setText(SelectedCamera);
                SharedPreferences.Editor prefEditor = mSettings.edit();
                prefEditor.putString(APP_ZAKAZCHIK, TextZakazchik.getText().toString());
                prefEditor.putString(APP_CAMERA, TextCamera.getText().toString());
                prefEditor.apply();

            }
        });
        ButEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = TextUser.getText().toString();
                password = TextPassword.getText().toString();
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("user", user));
                params.add(new BasicNameValuePair("password", password));
                try {
                    HttpClient httpclient = new DefaultHttpClient();
                    HttpPost httppost = new HttpPost("http://peremoga.tech/Android/ScriptEnter.php");
                    httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
                    HttpResponse response = httpclient.execute(httppost);
                    HttpEntity entity = response.getEntity();
                    is = entity.getContent();
                    Log.e("pass 1", "connection success ");
                } catch (Exception e) {
                    Log.e("Fail 1", e.toString());
                }
                // получаем ответ от php запроса в формате json
                System.out.println("-----получаем ответ от php запроса в формате json-------");
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    is.close();
                    result = sb.toString();
                    Log.e("pass 2", "connection success" + result);
                } catch (Exception e) {
                    Log.e("Fail 2", e.toString());
                }
                // обрабатываем полученный json
                System.out.println("------обрабатываем полученный json------");
                try {
                    JSONObject json_data = new JSONObject(result);
                    user= (json_data.getString("a"));
                    Log.e("pass 3", user);
                } catch (Exception e) {
                    Log.e("Fail 3", e.toString());
                }
                if (!user.equals("fail")){
                    SharedPreferences.Editor prefEditor = mSettings.edit();
                    prefEditor.putString(APP_USER, user);
                    prefEditor.apply();
                }else{
                    Toast.makeText(getBaseContext(), "Неверный логин или пароль", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public String getZakazchik(){
        Zakazchik=TextZakazchik.getText().toString();
        return Zakazchik;
    }

    @Override
    protected void onResume() {
        super.onResume();
        {
            String cam = mSettings.getString(APP_CAMERA,"Open Camera");
            String name = mSettings.getString(APP_ZAKAZCHIK,"Zakazchik");
            TextZakazchik.setText(name);
            TextCamera.setText(cam);
        }
    }


}
