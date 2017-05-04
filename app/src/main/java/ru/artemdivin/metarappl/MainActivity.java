package ru.artemdivin.metarappl;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AsyncTaskCompleteListner{
    public Button btnShowWether;
    public ListView lvInformation;
    public EditText etAirportCode;
    Downloader downloader = new Downloader();
   

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnShowWether = (Button)   findViewById(R.id.btnShowWeather);
        lvInformation = (ListView) findViewById(R.id.lv_Information);
        etAirportCode = (EditText) findViewById(R.id.etAirportCode);

        btnShowWether.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {



        downloader.execute();





    }

    @Override
    public void onTaskComplete(ArrayList<MetarObject> result) {
        downloader.delegate.onTaskComplete(result);
    }
}
