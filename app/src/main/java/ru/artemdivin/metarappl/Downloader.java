package ru.artemdivin.metarappl;


import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

public class Downloader extends AsyncTask<String, Void, ArrayList<MetarObject>> {

    private ArrayList<MetarObject> metarObjects = new ArrayList<>();
    private AsyncTaskCompleteListner delegate;

    Downloader(AsyncTaskCompleteListner delegate) {
        this.delegate = delegate;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected ArrayList<MetarObject> doInBackground(String... params) {

        HttpURLConnection httpUrl;
        InputStream inputStream;
        long milisec;
        String name;
        Map<String, Long> tMap = new TreeMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:" +
                "mm", Locale.US);
        long max = 0;
        String line;
        String fileName = null;

        try {
            URL netURL = new URL("http://tgftp.nws.noaa.gov/data/observations/metar/cycles");
            httpUrl = (HttpURLConnection) netURL.openConnection();
            inputStream = httpUrl.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));


            while ((line = reader.readLine()) != null) {
                if (line.matches(".+\\d{2}Z.TXT.+")) {
                    name = line.substring(17, 24);
                   /* Log.d("LINE", line);
                    Log.d("LINE", line.substring(105,106));*/

                    milisec = sdf.parse(line.substring(60, 77)).getTime();
                    if(line.substring(105,106).equals("M")){
                    tMap.put(name, milisec);
                    if (max < milisec) max = milisec;}
                }
            }

            for (Map.Entry<String, Long> entry : tMap.entrySet()
                    ) {
                if (entry.getValue().equals(max)) {

                    fileName = entry.getKey();
                    Log.d("Code Air", fileName);
                    //System.out.println(entry.getKey());
                    break;

                }
            }
            reader.close();
            inputStream.close();
            httpUrl.disconnect();


        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            URL netURL = new URL("http://tgftp.nws.noaa.gov/data/observations/metar/cycles/".concat(fileName));
            httpUrl = (HttpURLConnection) netURL.openConnection();
            InputStream f = httpUrl.getInputStream();
            String code = params[0];
            Log.d("code", code);
            StringBuilder stringBuilder = new StringBuilder();
            StringBuilder stringBuilder2 = new StringBuilder();

            int x;
            while ((x = f.read()) != -1) {
                stringBuilder2.append(x);
                stringBuilder.append((char) x);


                if (x == 10 && stringBuilder.length() != 0)

                {
                    String[] lineReader = stringBuilder.toString().split(" ");

                    if (code.equals(lineReader[0])) {
                        metarObjects.add(new MetarObject(lineReader[0]).create(stringBuilder.toString()));
                        stringBuilder.setLength(0);
                        stringBuilder2.setLength(0);
                        return metarObjects;
                    }
                    stringBuilder.setLength(0);
                    stringBuilder2.setLength(0);
                }
            }
            httpUrl.disconnect();
        } catch (IOException e) {
            e.printStackTrace();

        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<MetarObject> result) {
        super.onPostExecute(result);
        delegate.onTaskComplete(metarObjects);

    }
}