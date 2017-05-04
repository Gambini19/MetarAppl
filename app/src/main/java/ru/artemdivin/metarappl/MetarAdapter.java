package ru.artemdivin.metarappl;

import android.content.Context;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Kondratiy on 30.04.2017.
 */

public class MetarAdapter extends BaseAdapter {

    Context ctx;
    LayoutInflater lInflater;
    MetarObject metarObject;
   // HashMap<String,String> ourMap;
   private ArrayList<Pair<String,String>> data = new ArrayList<>();

    public MetarAdapter(Context ctx) {
        this.ctx = ctx;
     //   this.metarObject = objects;
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
        if (view == null)
        {
            view = lInflater.inflate(R.layout.list_layout,parent, false);
        }


        ((TextView)view.findViewById(R.id.meteoKey)).setText(data.get(position).first);
        ((TextView)view.findViewById(R.id.meteoValue)).setText(data.get(position).second);



        return view;
    }

    public void updateObject (MetarObject metarObject)
    {

       /* if (metarObject.getTermo() != null) ourMap.put("Температура  ", metarObject.getTermo());
        if (metarObject.getPressure() != null) ourMap.put("Давление  ", metarObject.getPressure());
        if (metarObject.getWind() != null) ourMap.put("Ветер  ", metarObject.getWind());
        if (metarObject.getVisibility() != null) ourMap.put("Видимость  ", metarObject.getVisibility());
        if (metarObject.getCloud() != null) ourMap.put("Облачность  ", metarObject.getCloud());
*/


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

        Log.i ("DATAAA", String.valueOf(data));

    }


}
