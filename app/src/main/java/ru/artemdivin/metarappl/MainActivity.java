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


    private Downloader downloader = new Downloader();
    private ArrayList<MetarObject> list;
    private MetarAdapter adapter;
    private AsyncTaskCompleteListner listner;
   

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listner = this;


        btnShowWether = (Button)   findViewById(R.id.btnShowWeather);
        lvInformation = (ListView) findViewById(R.id.lv_Information);
        etAirportCode = (EditText) findViewById(R.id.etAirportCode);

        btnShowWether.setOnClickListener(this);
        adapter = new MetarAdapter(this);

    }

    @Override
    public void onClick(View v) {

        downloader.execute();
        lvInformation.setAdapter(adapter);
    }

    @Override
    public void onTaskComplete(ArrayList<MetarObject> result) {
        this.list = result;

        // display results of first element
        if (list != null && list.size() > 0)
            adapter.updateObject(list.get(0));
        adapter.notifyDataSetChanged();
    }

}
