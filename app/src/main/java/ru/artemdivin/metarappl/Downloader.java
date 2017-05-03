package ru.artemdivin.metarappl;


import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Downloader extends AsyncTask<Void, Void, Void> {

    Map<String, String> data = new HashMap<>();
    ArrayList<MetarObject> metarObjects = new ArrayList<>();

    @Override
    protected Void doInBackground(Void... params) {

        HttpURLConnection httpUrl;
        try {
            URL netURL = new URL("http://tgftp.nws.noaa.gov/data/observations/metar/cycles/14Z.TXT");
            httpUrl = (HttpURLConnection) netURL.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpUrl.getInputStream()));
            String s;
            int count = 0;
            while ((s = bufferedReader.readLine()) != null) {
                if (!s.isEmpty() && !(s.matches("^\\d{4}.+"))) {
                    String[] line = s.split(" ", 2);
                    Log.i("line0  ", line[0]);
                    Log.i("line1  ", line[1]);
                    //data.put(line[0], line[1]);
                    metarObjects.add(MetarObject.getObj(line[0],line[1]));
                    count++;
                    Log.i("obj " , metarObjects.toString());
                }
                if (count == 20) break;

            }
            httpUrl.disconnect();

            Log.i ("list", String.valueOf(metarObjects));
            //Log.i("metarObjects.size()  ", String.valueOf(metarObjects.size()));
          //  Log.i("metarObjects.get(1)  ", String.valueOf(metarObjects.get(1)));



        } catch (IOException  e) {
            e.printStackTrace();
        }


        return null;
    }

}

