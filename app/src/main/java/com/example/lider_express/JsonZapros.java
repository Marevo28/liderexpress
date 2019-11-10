package com.example.lider_express;

import android.util.Log;
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

public class JsonZapros extends Thread {
    String id;
    String login="АШИПКА";
    String surname = "АШИПКА";
    String name = "АШИПКА";
    String midle = "АШИПКА";
    String result = null;
    String line = null;
    InputStream is = null;

    public void run() {
        // создаем лист для отправки запросов
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        // один параметр, если нужно два и более просто добоовляем также
        nameValuePairs.add(new BasicNameValuePair("a", login));
        nameValuePairs.add(new BasicNameValuePair("b", surname));
        nameValuePairs.add(new BasicNameValuePair("c", name));
        nameValuePairs.add(new BasicNameValuePair("d", midle));
        //  подключаемся к php запросу и отправляем в него id
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://peremoga.tech/Sync.php");
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();
            Log.e("pass 1", "connection success ");
        } catch (Exception e) {
            Log.e("Fail 1", e.toString());
        }
        // получаем ответ от php запроса в формате json
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
        try {
            JSONObject json_data = new JSONObject(result);
            login= (json_data.getString("a"));
            surname = (json_data.getString("b"));
            name = (json_data.getString("c"));
            midle = (json_data.getString("d"));
            Log.e("pass 3", name);
        } catch (Exception e) {
            Log.e("Fail 3", e.toString());
        }
    }
    // принемаем id при запуске потока
    public void start(String idp) {
        this.id = idp;
        this.start();
    }
    public void download(String loginp,String cehp,String upravp,String obekt) {
        this.login = loginp;
        this.surname = cehp;
        this.name = upravp;
        this.midle = obekt;
        this.start();
    }

    public String resname() {
        return name;
    }

    public String ressurname() {
        return surname;
    }

    public String resmiddlename() {
        return midle;
    }


}
