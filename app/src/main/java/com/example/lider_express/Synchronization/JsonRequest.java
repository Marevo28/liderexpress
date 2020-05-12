package com.example.lider_express.Synchronization;

import android.util.Log;

import com.example.lider_express.Shared;

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
import java.util.LinkedHashMap;

public class JsonRequest extends Thread {

    private String tableName;
    private String position;
    private String mURL;
    private ArrayList<LinkedHashMap<String, String>> rows;

    private String result = null;
    private String line = null;
    private InputStream is = null;

    public JsonRequest(String Zakazchik){
        System.out.println("Таблица, которую отправляем: - " + Zakazchik);
        mURL = Shared.URLAllForm;
        tableName = Zakazchik;
      //  mURL = Shared.URLAllForm;

      /**  switch (Zakazchik){
           // case "DefectBND2019": mURL = "http://peremoga.tech/Android/DefectBND2020.php"; break;
           // case "DefectMegion2019": mURL = "http://peremoga.tech/Android/DefectMEGION2020.php"; break;
           // case "DefectPolus2019": mURL = ""; break;
            case "defectBashneft2020": mURL = "http://peremoga.tech/Android/DefectBND2020.php"; break;
            case "defectMegion2020": mURL = "http://peremoga.tech/Android/DefectMEGION2020.php"; break;
            case "DefectBND2020_UDE": mURL = "http://peremoga.tech/Android/DefectBND2020_UDE.php"; break;
            case "DefectBND2020_Nasos": mURL = "http://peremoga.tech/Android/DefectBND2020_Nasos.php"; break;
           // case "DefectPolus2020": mURL = ""; break;
           // case "DefectBND2021": mURL = "http://peremoga.tech/Android/DefectBND2020.php"; break;
           // case "DefectMegion2021": mURL = "http://peremoga.tech/Android/DefectMEGION2020.php"; break;
           // case "DefectPolus2021": mURL = ""; break;
        }
       **/

    }

    public void run() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        for(LinkedHashMap<String, String> row : rows) {

            nameValuePairs.add(new BasicNameValuePair("Table", tableName));

            System.out.println("URL : " + mURL);
            System.out.println("TableName : " + tableName);

            for (LinkedHashMap.Entry<String, String> entry : row.entrySet()) {
                System.out.println(" entry Key " + entry.getKey() + " entry Value " + entry.getValue());
                if(entry.getKey() != "id"){
                    nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
            }
            for(int i = 0; i < nameValuePairs.toArray().length; i ++){
                System.out.println("nameValuePairs : " + nameValuePairs.toArray()[i]);
            }
            connection(nameValuePairs);
            nameValuePairs.clear();
        }

    } // run

    private void connection(ArrayList<NameValuePair> nameValuePairs){
        //  подключаемся к php запросу и отправляем в него id
        System.out.println(" подключаемся к php запросу и отправляем в него id");
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(mURL);
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
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
            position= (json_data.getString("a"));
            Log.e("pass 3", position);
        } catch (Exception e) {
            Log.e("Fail 3", e.toString());
        }
    }

    // принемаем id при запуске потока
    public void start(String idp) {
        this.position = idp;
        this.start();
    }

    public void download(String position, ArrayList<LinkedHashMap<String, String>> rows){
        this.position = position;
        this.rows = rows;
        this.start();
    }

    public String reposition() {
        return position;
    }

}





/**
 public void download(String positionb,String stolb27b,String stolb28b,String stolb29b,String stolb30b,
 String stolb31b,String stolb32b,String stolb33b,String stolb34b,String stolb35b,
 String stolb36b,String stolb37b,String stolb38b,String stolb39b, String stolb40b,
 String stolb41b,String stolb42b,String stolb43b,String stolb44b,String stolb45b,
 String stolb46b,String stolb47b,String stolb48b,String stolb49b,String stolb50b,
 String stolb51b,String stolb52b,String stolb53b, String stolb54b,String stolb55b,String stolb56b,
 String stolb57b,String stolb58b,String stolb59b, String stolb60b,String stolb61b,String stolb62b,
 String stolb63b,String stolb64b,String stolb65b,String stolb66b,String stolb67b,String stolb68b) {
 this.position = positionb;
 this.stolb27 = stolb27b;
 this.stolb28 = stolb28b;
 this.stolb29 = stolb29b;
 this.stolb30 = stolb30b;
 this.stolb31 = stolb31b;
 this.stolb32 = stolb32b;
 this.stolb33 = stolb33b;
 this.stolb34 = stolb34b;
 this.stolb35 = stolb35b;
 this.stolb36 = stolb36b;
 this.stolb37 = stolb37b;
 this.stolb38 = stolb38b;
 this.stolb39 = stolb39b;
 this.stolb40 = stolb40b;
 this.stolb41 = stolb41b;
 this.stolb42 = stolb42b;
 this.stolb43 = stolb43b;
 this.stolb44 = stolb44b;
 this.stolb45 = stolb45b;
 this.stolb46 = stolb46b;
 this.stolb47 = stolb47b;
 this.stolb48 = stolb48b;
 this.stolb49 = stolb49b;
 this.stolb50 = stolb50b;
 this.stolb51 = stolb51b;
 this.stolb52 = stolb52b;
 this.stolb53 = stolb53b;
 this.stolb54 = stolb54b;
 this.stolb55 = stolb55b;
 this.stolb56 = stolb56b;
 this.stolb57 = stolb57b;
 this.stolb58 = stolb58b;
 this.stolb59 = stolb59b;
 this.stolb60 = stolb60b;
 this.stolb61 = stolb61b;
 this.stolb62 = stolb62b;
 this.stolb63 = stolb63b;
 this.stolb64 = stolb64b;
 this.stolb65 = stolb65b;
 this.stolb66 = stolb66b;
 this.stolb67 = stolb67b;
 this.stolb68 = stolb68b;

 this.start();
 }

 **/

/**
 nameValuePairs.add(new BasicNameValuePair("position", position));
 nameValuePairs.add(new BasicNameValuePair("stolb27", stolb27));
 nameValuePairs.add(new BasicNameValuePair("stolb28", stolb28));
 nameValuePairs.add(new BasicNameValuePair("stolb29", stolb29));
 nameValuePairs.add(new BasicNameValuePair("stolb30", stolb30));
 nameValuePairs.add(new BasicNameValuePair("stolb31", stolb31));
 nameValuePairs.add(new BasicNameValuePair("stolb32", stolb32));
 nameValuePairs.add(new BasicNameValuePair("stolb33", stolb33));
 nameValuePairs.add(new BasicNameValuePair("stolb34", stolb34));
 nameValuePairs.add(new BasicNameValuePair("stolb35", stolb35));
 nameValuePairs.add(new BasicNameValuePair("stolb36", stolb36));
 nameValuePairs.add(new BasicNameValuePair("stolb37", stolb37));
 nameValuePairs.add(new BasicNameValuePair("stolb38", stolb38));
 nameValuePairs.add(new BasicNameValuePair("stolb39", stolb39));
 nameValuePairs.add(new BasicNameValuePair("stolb40", stolb40));
 nameValuePairs.add(new BasicNameValuePair("stolb41", stolb41));
 nameValuePairs.add(new BasicNameValuePair("stolb42", stolb42));
 nameValuePairs.add(new BasicNameValuePair("stolb43", stolb43));
 nameValuePairs.add(new BasicNameValuePair("stolb44", stolb44));
 nameValuePairs.add(new BasicNameValuePair("stolb45", stolb45));
 nameValuePairs.add(new BasicNameValuePair("stolb46", stolb46));
 nameValuePairs.add(new BasicNameValuePair("stolb47", stolb47));
 nameValuePairs.add(new BasicNameValuePair("stolb48", stolb48));
 nameValuePairs.add(new BasicNameValuePair("stolb49", stolb49));
 nameValuePairs.add(new BasicNameValuePair("stolb50", stolb50));
 nameValuePairs.add(new BasicNameValuePair("stolb51", stolb51));
 nameValuePairs.add(new BasicNameValuePair("stolb52", stolb52));
 nameValuePairs.add(new BasicNameValuePair("stolb53", stolb53));
 nameValuePairs.add(new BasicNameValuePair("stolb54", stolb54));
 nameValuePairs.add(new BasicNameValuePair("stolb55", stolb55));
 nameValuePairs.add(new BasicNameValuePair("stolb56", stolb56));
 nameValuePairs.add(new BasicNameValuePair("stolb57", stolb57));
 nameValuePairs.add(new BasicNameValuePair("stolb58", stolb58));
 nameValuePairs.add(new BasicNameValuePair("stolb59", stolb59));
 nameValuePairs.add(new BasicNameValuePair("stolb60", stolb60));
 nameValuePairs.add(new BasicNameValuePair("stolb61", stolb61));
 nameValuePairs.add(new BasicNameValuePair("stolb62", stolb62));
 nameValuePairs.add(new BasicNameValuePair("stolb63", stolb63));
 nameValuePairs.add(new BasicNameValuePair("stolb64", stolb64));
 nameValuePairs.add(new BasicNameValuePair("stolb65", stolb65));
 nameValuePairs.add(new BasicNameValuePair("stolb66", stolb66));
 nameValuePairs.add(new BasicNameValuePair("stolb67", stolb67));
 nameValuePairs.add(new BasicNameValuePair("stolb68", stolb68));

 **/

/**
 *  private String stolb27;
 *     private String stolb28;
 *     private String stolb29;
 *     private String stolb30;
 *     private String stolb31;
 *     private String stolb32;
 *     private String stolb33;
 *     private String stolb34;
 *     private String stolb35;
 *     private String stolb36;
 *     private String stolb37;
 *     private String stolb38;
 *     private String stolb39;
 *     private String stolb40;
 *     private String stolb41;
 *     private String stolb42;
 *     private String stolb43;
 *     private String stolb44;
 *     private String stolb45;
 *     private String stolb46;
 *     private String stolb47;
 *     private String stolb48;
 *     private String stolb49;
 *     private String stolb50;
 *     private String stolb51;
 *     private String stolb52;
 *     private String stolb53;
 *     private String stolb54;
 *     private String stolb55;
 *     private String stolb56;
 *     private String stolb57;
 *     private String stolb58;
 *     private String stolb59;
 *     private String stolb60;
 *     private String stolb61;
 *     private String stolb62;
 *     private String stolb63;
 *     private String stolb64;
 *     private String stolb65;
 *     private String stolb66;
 *     private String stolb67;
 *     private String stolb68;
 */
