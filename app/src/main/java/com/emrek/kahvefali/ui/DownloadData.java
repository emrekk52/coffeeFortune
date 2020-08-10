package com.emrek.kahvefali.ui;

import android.os.AsyncTask;
import android.util.JsonReader;

import com.emrek.kahvefali.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

public class DownloadData extends AsyncTask<String, Void, String> {


    @Override
    protected String doInBackground(String... strings) {


        String result = "";
        URL url;
        HttpURLConnection httpURLConnection;

        try {

            url = new URL("https://www.kucukemre.com/fortune-array.txt"); // mainden gelen url...  (0. indiste ki)
            httpURLConnection = (HttpURLConnection) url.openConnection(); //bağlantıyı açma
            InputStream inputStream = httpURLConnection.getInputStream(); //okuma işlemi
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

            int data = inputStreamReader.read();
            while (data > 0) {

                char character = (char) data;
                result += character;

                data = inputStreamReader.read();
            }


            return result;
        } catch (Exception e) {
            return null;
        }


    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

    }
}