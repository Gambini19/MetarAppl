package ru.artemdivin.metarappl;

import java.util.Map;

/**
 * Created by Kondratiy on 30.04.2017.
 */

public class MetarObject {
    private String nameAirport;
    private int termo;
    private int pressure;
    private String wind;
    private String visibility;
    private String cloud;

    private String termoString = null;
    private String pressureString = null;

    public MetarObject(String nameAirport, int termo, int pressure, String wind, String visibility, String cloud) {
        this.nameAirport = nameAirport;
        this.termo = termo;
        this.pressure = pressure;
        this.wind = wind;
        this.visibility = visibility;
        this.cloud = cloud;
    }

    public String GetCode(Map map) {
        nameAirport = String.valueOf(map.get("EditText Field"));
        return nameAirport;
    }

    public String  getMeteo(String line) {
        String[] masData = line.split(" ");


        for (String s : masData
                ) {
            //Температура
            if (s.matches("\\d\\d\\\\\\d\\d")) return termoString = (s.substring(0, 3));
            //else if (s.matches("M\\d\\d\\\\M?\\d\\d"))  return  termoString = (s.substring(1,4))*(-1);
            else if (s.matches("M\\d\\d\\\\M?\\d\\d"))  return  termoString = (s.substring(1,4));
            //else termo = Integer.MAX_VALUE;

            //Давление
            /*if (s.matches("Q\\d{4}")) pressure = Integer.parseInt(s.substring(1,6));
            else if (s.matches("A\\d{4}")) pressure= (int) (Integer.parseInt(s.substring(1,6))*(0.3386));*/

            if (s.matches("Q\\d{4}")) return  pressureString =(s.substring(1,6));
            else if (s.matches("A\\d{4}")) return  pressureString= (s.substring(1,6));

            //Ветер
            if (s.matches("\\d{5}MPS"))
                return wind = String.format("%s узлов %s градусов",s.substring(3,5),s.substring(0,4));
            else if (s.matches("VBR\\d\\dMPS"))return wind = "Направление ветра меняется";
          //  else return wind  = "нет информации";

            //Видимость
            if ( s.matches("\\d{4}")) return visibility = s;
            else if (s.matches("\\d{2}SM"))return visibility = s.substring(0,3) + " Statute Miles" ;
          //  else return visibility = "нет информации";

            //Облачность

            if (s.matches("CAVOK") || s.equals("NSC") || s.equals("NCD"))return cloud = "Sky is clear";
            else if  (s.matches("SCT\\d{3}") || s.matches("FEW\\d{3}"))return cloud = "Малооблачно";
            else if (s.matches("BKN\\d{3}") || s.matches("OVC\\d{3}"))return cloud = "Облачно";
            //else return cloud = "нет информации";

        }
        return line;
    }

    public  MetarObject getObj (Map <String, String> map){
        nameAirport = String.valueOf(map.get("EditText Field"));
        getMeteo(map.get(nameAirport));

        return new MetarObject
                        (nameAirport
                        ,Integer.parseInt(termoString)
                        ,Integer.parseInt(pressureString)
                        ,getMeteo(map.get(nameAirport))
                        ,getMeteo(map.get(nameAirport))
                        ,getMeteo(map.get(nameAirport)));


        
    }
}
