package ru.artemdivin.metarappl;


import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

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
        String fileName;

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        int hour = calendar.get(Calendar.HOUR_OF_DAY) - 1;

        if (hour < 10) fileName = "0".concat(String.valueOf(hour)).concat("Z.TXT");
        else fileName = String.valueOf(hour).concat("Z.TXT");


        try {
            URL netURL = new URL("http://tgftp.nws.noaa.gov/data/observations/metar/cycles/".concat(fileName));
            httpUrl = (HttpURLConnection) netURL.openConnection();
            InputStream f = httpUrl.getInputStream();
            String code = params[0];
        //    Log.d("code", code);
            StringBuilder stringBuilder = new StringBuilder();

            int readingByte;
            while ((readingByte = f.read()) != -1) {
                stringBuilder.append((char) readingByte);


                if (readingByte == 10 && stringBuilder.length() != 0)

                {
                    String[] lineReader = stringBuilder.toString().split(" ");

                    if (code.equals(lineReader[0])) {
                        metarObjects.add(new MetarObject(lineReader[0]).create(stringBuilder.toString()));
                        stringBuilder.setLength(0);
                        return metarObjects;
                    }
                    stringBuilder.setLength(0);
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