package ru.artemdivin.metarappl;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AsyncTaskCompleteListner {
    public Button btnShowWether;
    public ListView lvInformation;
    public EditText etAirportCode;


    private ArrayList<MetarObject> list;
    private MetarAdapter adapter;
    private AsyncTaskCompleteListner listner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listner = this;


        btnShowWether = (Button) findViewById(R.id.btnShowWeather);
        lvInformation = (ListView) findViewById(R.id.lv_Information);
        etAirportCode = (EditText) findViewById(R.id.etAirportCode);

        if (!etAirportCode.getText().toString().equals("")) {

            String codeRequest = etAirportCode.getText().toString();

        }


        btnShowWether.setOnClickListener(this);
        adapter = new MetarAdapter(this);

    }

    @Override
    public void onClick(View v) {
        new Downloader(listner).execute();

    }

    @Override
    public void onTaskComplete(ArrayList<MetarObject> result) {

        Log.d("Responseeeee", String.valueOf(result.size()));
        this.list = result;
        if (list.size() > 0) {

            for (int i = 0; i < list.size(); i++) {
                {
                    if (Objects.equals(etAirportCode.getText().toString().toUpperCase(), list.get(i).getNameAirport())) {
                        adapter.clearList();
                        adapter.updateObject(list.get(i));
                        adapter.notifyDataSetChanged();
                        lvInformation.setAdapter(adapter);
                        break;
                    }

                }
            }
        }
    }
}
