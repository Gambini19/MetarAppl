package ru.artemdivin.metarappl;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class MetarAdapter extends BaseAdapter {

    public Context ctx;
    private LayoutInflater lInflater;
    private ArrayList<Pair<String, String>> data = new ArrayList<>();

    public MetarAdapter(Context ctx) {
        this.ctx = ctx;
        lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {

        return data.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.list_layout, parent, false);
        }


        ((TextView) view.findViewById(R.id.meteoKey)).setText(data.get(position).first);
        ((TextView) view.findViewById(R.id.meteoValue)).setText(data.get(position).second);


        return view;
    }

    public void updateObject(MetarObject metarObject) {
        if (metarObject.getTermo() != null)
            data.add((new Pair<>("Температура  ", metarObject.getTermo())));
        if (metarObject.getPressure() != null)
            data.add((new Pair<>("Давление  ", metarObject.getPressure())));
        if (metarObject.getWind() != null)
            data.add((new Pair<>("Ветер  ", metarObject.getWind())));
        if (metarObject.getVisibility() != null)
            data.add((new Pair<>("Видимость  ", metarObject.getVisibility())));
        if (metarObject.getCloud() != null)
            data.add((new Pair<>("Облачность  ", metarObject.getCloud())));

    }

    public void clearList() {
        data.clear();
        this.notifyDataSetChanged();
    }


}
