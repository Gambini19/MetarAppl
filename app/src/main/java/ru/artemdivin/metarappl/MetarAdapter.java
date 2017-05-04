package ru.artemdivin.metarappl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.HashMap;

/**
 * Created by Kondratiy on 30.04.2017.
 */

public class MetarAdapter extends BaseAdapter {

    Context ctx;
    LayoutInflater lInflater;
    MetarObject metarObject;
    HashMap<String,String> ourMap;

    public MetarAdapter(Context ctx) {
        this.ctx = ctx;
     //   this.metarObject = objects;
        lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }


    @Override
    public int getCount() {
        return ourMap.size();
    }

    @Override
    public Object getItem(int position) {
        return ourMap.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null)
        {
            view = lInflater.inflate(R.layout.list_layout,parent, false);
        }

        ourMap = new HashMap<>();
        ((TextView)view.findViewById(R.id.meteoKey)).setText(ourMap.get(position));
        ((TextView)view.findViewById(R.id.meteoValue)).setText(ourMap.get(position));



        return view;
    }

    public void updateObject (MetarObject metarObject)
    {

        if (metarObject.getTermo() != null) ourMap.put("Температура  ", metarObject.getTermo());
        if (metarObject.getPressure() != null) ourMap.put("Давление  ", metarObject.getPressure());
        if (metarObject.getWind() != null) ourMap.put("Ветер  ", metarObject.getWind());
        if (metarObject.getVisibility() != null) ourMap.put("Видимость  ", metarObject.getVisibility());
        if (metarObject.getCloud() != null) ourMap.put("Облачность  ", metarObject.getCloud());





    }


}
