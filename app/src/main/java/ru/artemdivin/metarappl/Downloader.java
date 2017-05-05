package ru.artemdivin.metarappl;


import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Downloader extends AsyncTask<Void, Void, ArrayList<MetarObject>>  {

    //Map<String, String> data = new HashMap<>();
    ArrayList<MetarObject> metarObjects = new ArrayList<>();

    private AsyncTaskCompleteListner delegate;

    public Downloader(AsyncTaskCompleteListner delegate) {
        this.delegate = delegate;
    }

    @Override
    protected ArrayList<MetarObject> doInBackground(Void... params) {

        HttpURLConnection httpUrl;
        try {
            URL netURL = new URL("http://tgftp.nws.noaa.gov/data/observations/metar/cycles/14Z.TXT");
            httpUrl = (HttpURLConnection) netURL.openConnection();
            InputStream f = httpUrl.getInputStream();
            String code = "NGFU";
            StringBuilder stringBuilder = new StringBuilder();
            StringBuilder stringBuilder2 = new StringBuilder();
            String toScreen = null;
            int count = 0;

            int x;
            while ((x = f.read()) != -1) {
                //    Log.d("    x     ", String.valueOf(x));
                stringBuilder2.append(x);
                Log.d(" ", stringBuilder2.toString());

                stringBuilder.append((char) x);


                if (x == 10 && stringBuilder.length() != 0)

                {
                    String[] line = stringBuilder.toString().split(" ");
                    Log.d("T1", line[0]);
                    Log.d("T1", String.valueOf(line[0].getBytes()));
                    Log.d("T3", stringBuilder.toString());

                    // System.out.println(m[0]);

                    if (code.equals(line[0])) {
                        toScreen = stringBuilder.toString();
                        Log.d("12321", toScreen);
                        Log.d("1232111", line[1]);
                        metarObjects.add(new MetarObject(line[0]).create(stringBuilder.toString()));
                        stringBuilder.setLength(0);
                        stringBuilder2.setLength(0);
                        count++;
                        Log.d("COUNTTTTTTTTTTTTT", String.valueOf(count));
                        return metarObjects;


                    }
                    stringBuilder.setLength(0);
                    stringBuilder2.setLength(0);

                }
            }
                httpUrl.disconnect();



        }

         catch (IOException  e) {
            e.printStackTrace();
        System.out.println("BLABLA ");
        }
        return metarObjects;
    }

    @Override
    protected void onPostExecute(ArrayList<MetarObject> result) {
        super.onPostExecute(result);
                   delegate.onTaskComplete(metarObjects);

        Log.d("RESULTTTT" , String.valueOf(result));
        Log.d("RESULTTTT2" , String.valueOf(metarObjects));

    }


}