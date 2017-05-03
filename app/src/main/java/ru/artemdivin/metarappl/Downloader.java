package ru.artemdivin.metarappl;


import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Downloader extends AsyncTask<Void, Void, Void> {

    Map<String, String> data = new HashMap<>();

    @Override
    protected Void doInBackground(Void... params) {

        HttpURLConnection httpUrl;
        try {
            URL netURL = new URL("http://tgftp.nws.noaa.gov/data/observations/metar/cycles/14Z.TXT");
            httpUrl = (HttpURLConnection) netURL.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpUrl.getInputStream()));
            String s;
            while ((s = bufferedReader.readLine()) != null) {
                if (!s.isEmpty() && !(s.matches("^\\d{4}.+"))) {
                    String[] line = s.split(" ", 2);
                    data.put(line[0], line[1]);
                }
            }
            System.out.println(data);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

}

