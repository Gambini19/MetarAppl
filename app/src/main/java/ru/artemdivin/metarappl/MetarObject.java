package ru.artemdivin.metarappl;

import android.util.Log;

/**
 * Created by Kondratiy on 30.04.2017.
 */

public class MetarObject {
    public String nameAirport;
    public String termo;
    public String pressure;
    public String wind;
    public String visibility;
    public String cloud;
    final static String defaultStatus = "Нет информации";

    public MetarObject(String nameAirport) {
        this.nameAirport = nameAirport;
    }

    public MetarObject create(String line) {

        String[] masData = line.split(" ");

        setCloud(defaultStatus);
        setWind(defaultStatus);
        setVisibility(defaultStatus);
        setTermo(defaultStatus);
        setPressure(defaultStatus);


        for (String s : masData
                ) {
            Log.d("s", s);

            //Температура
            if (s.matches("\\d\\d/M?\\d\\d")) setTermo(s.substring(0, 2) + " C");
            else if (s.matches("M?\\d\\d/M?\\d\\d")) setTermo("-" + s.substring(1, 3) + " C");
            else if (s.matches("\\d\\d/MM")) setTermo(s.substring(0, 2) + " C");
            else if (s.matches("\\d\\d/")) setTermo(s.substring(0, 2) + " C");


            //Ветер
            if (s.matches("\\d{5}MPS")) {
                setWind(String.format("%s узлов %s градусов", s.substring(3, 5), s.substring(0, 3)));
            } else if (s.matches("\\d{5}KT")) {
                setWind(String.format("%s узлов %s градусов", s.substring(3, 5), s.substring(0, 3)));
            } else if (s.matches("\\d{5}...KT")) {
                setWind(String.format("%s узлов %s градусов. \n Ветер меняется", s.substring(3, 5), s.substring(0, 3)));
            } else if (s.matches("00000KT")) {
                setWind("Спокойный ветер");
            } else if (s.matches("VBR\\d\\dMPS")) setWind("Направление ветра меняется");

            //Видимость
            if (s.matches("\\d{4}")) setVisibility(s);
            else if (s.matches("\\d{2}SM")) setVisibility(s.substring(0, 2) + " Сухопутных миль");
            else if (s.matches("\\dSM")) setVisibility(s.substring(0, 1) + " Сухопутных миль");

            //Облачность

            if (s.matches("CAVOK") || s.equals("NSC") || s.equals("NCD") || s.equals("CLR"))
                setCloud("Небо чистое");
            else if (s.matches("SCT\\d{3}") || s.matches("FEW\\d{3}")) setCloud("Малооблачно");
            else if (s.matches("BKN\\d{3}") || s.matches("OVC\\d{3}")) setCloud("Облачно");

            //Давление
            if (s.matches("Q\\d{4}[\\n]?")) setPressure(s.substring(1, 5) + "hPa");
            else if (s.matches("A\\d{4}[\\n]?"))
                setPressure(s.substring(1, 3) + "," + s.substring(3, 5) + " дюймов рт.ст.");

        }
        return this;
    }

    public String getNameAirport() {
        return nameAirport;
    }

    public String getTermo() {
        return termo;
    }

    public void setTermo(String termo) {
        this.termo = termo;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getCloud() {
        return cloud;
    }

    public void setCloud(String cloud) {
        this.cloud = cloud;
    }

    @Override
    public String toString() {
        return "MetarObject{" + "\n" +
                " nameAirport=  " + nameAirport + '\n' +
                "  температура " + getTermo() + "\n" +
                "  Давление  " + getPressure() + "\n" +
                "  Ветер  " + getWind() + "\n" +
                "  Видимость " + getVisibility() + "\n" +
                "  Облачность " + getCloud() + "\n" +
                '}';
    }
}
