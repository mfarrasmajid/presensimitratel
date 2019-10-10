package com.example.presensimitratel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class MainActivity extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);
        downloadJSON("http://api.mitratel.co.id/rest/presensi/api/test.php");
    }

    private void downloadJSON(final String urlWebService) {

        class DownloadJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
//                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                try {
                    loadIntoListView(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                        URL url = new URL(urlWebService);
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
                        con.setRequestMethod("POST");
                        con.connect();
                        if (con.getResponseCode() == HttpURLConnection.HTTP_OK){
                            StringBuilder sb = new StringBuilder();
                            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                            String json;
                            while ((json = bufferedReader.readLine()) != null) {
                                sb.append(json + "\n");
                            }
                            return sb.toString().trim();
                        } else {
                            return null;
                        }
                    } catch (Exception e) {
                        return null;
                }
            }
        }
        DownloadJSON getJSON = new DownloadJSON();
        getJSON.execute();
    }

    private void loadIntoListView(String json) throws JSONException {
//        Toast.makeText(getApplicationContext(), "X", Toast.LENGTH_SHORT).show();
        try {
//            JSONArray jsonArray = new JSONArray(json);
            JSONObject jsonObject = new JSONObject(json);
            boolean success = jsonObject.getBoolean("success");

            if(success){
                Toast.makeText(getApplicationContext(), "Success retrieving data", Toast.LENGTH_SHORT).show();
                JSONArray jsonArrayData = jsonObject.getJSONArray("data");
                String[] stocks = new String[jsonArrayData.length()];
                for (int i = 0; i < jsonArrayData.length(); i++) {
                    JSONObject obj = jsonArrayData.getJSONObject(i);
                    stocks[i] = obj.getString("name") + " " + obj.getString("price");
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, stocks);
                listView.setAdapter(arrayAdapter);
            } else {
                Toast.makeText(getApplicationContext(), "Failed retrieving data", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}
