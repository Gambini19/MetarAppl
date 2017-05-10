package ru.artemdivin.metarappl;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AsyncTaskCompleteListner {
    public Button btnShowWether;
    public ListView lvInformation;
    public EditText etAirportCode;
    public ProgressBar pbarDowload;


    private ArrayList<MetarObject> list;
    private MetarAdapter adapter;
    private AsyncTaskCompleteListner listner;

    ConnectionDetector detector;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listner = this;


        btnShowWether = (Button) findViewById(R.id.btnShowWeather);
        lvInformation = (ListView) findViewById(R.id.lv_Information);
        etAirportCode = (EditText) findViewById(R.id.etAirportCode);
        pbarDowload = (ProgressBar) findViewById(R.id.pbar);


        btnShowWether.setOnClickListener(this);
        adapter = new MetarAdapter(this);

        detector = new ConnectionDetector(this);

    }

    @Override
    public void onClick(View v) {

        if (detector.isConnectingToInternet()) {


            String codeRequest = etAirportCode.getText().toString().toUpperCase();
            if (!codeRequest.equals("")) {
                adapter.clearList();
                new Downloader(listner).execute(codeRequest);
                pbarDowload.setVisibility(View.VISIBLE);
                btnShowWether.setClickable(false);
            } else Toast.makeText(this, "введите код аэропорта", Toast.LENGTH_SHORT).show();

        } else Toast.makeText(this, "Нет подключения к интернету", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTaskComplete(ArrayList<MetarObject> result) {

        pbarDowload.setVisibility(View.INVISIBLE);
        btnShowWether.setClickable(true);

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
        } else Toast.makeText(this, "Аэропорт с таким кодом не найден", Toast.LENGTH_SHORT).show();
    }
}
