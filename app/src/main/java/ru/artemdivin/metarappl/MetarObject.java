package ru.artemdivin.metarappl;

/**
 * Created by Kondratiy on 30.04.2017.
 */

public class MetarObject {
    private String nameAirport;
    private static String termo;
    private static String pressure;
    private static String wind;
    private static String visibility;
    private static String cloud;

    private static String termoString = null;
    private static String pressureString = null;

    public MetarObject(String nameAirport, String termo, String pressure, String wind, String visibility, String cloud) {
        this.nameAirport = nameAirport;
        this.termo = termo;
        this.pressure = pressure;
        this.wind = wind;
        this.visibility = visibility;
        this.cloud = cloud;
    }

    /*public String GetCode(Map map) {
        nameAirport = String.valueOf(map.get("EditText Field"));
        return nameAirport;
    }*/

    public static void  getMeteo(String line) {
       // line = "KMYJ 021355Z AUTO 27013G17KT 10SM CLR 10/05 A2988 RMK AO2 ";
        String[] masData = line.split(" ");


        for (String s : masData
                ) {
            //Температура
            if (s.matches("\\d\\d/\\d\\d")) setTermo(s.substring(0, 2) + " C");
            else if (s.matches("M\\d\\d/M?\\d\\d"))  setTermo("-" + s.substring(1,3)+ " C");

            //Давление
            /*if (s.matches("Q\\d{4}")) pressure = Integer.parseInt(s.substring(1,6));
            else if (s.matches("A\\d{4}")) pressure= (int) (Integer.parseInt(s.substring(1,6))*(0.3386));*/

            if (s.matches("Q\\d{4}")) setPressure(s.substring(1,5) + "hPa");
            else if (s.matches("A\\d{4}")) setPressure (s.substring(1,3)+","+s.substring(3,5)+ " дюймов ртутного столба");

            //Ветер
            if (s.matches("\\d{5}MPS"))
                setWind(String.format("%s узлов %s градусов",s.substring(3,5),s.substring(0,3)));
            else if (s.matches("\\d{5}KT"))
                setWind(String.format("%s узлов %s градусов",s.substring(3,5),s.substring(0,3)));
            if (s.matches("\\d{5}...KT"))
                setWind(String.format("%s узлов %s градусов. Ветер меняется",s.substring(3,5),s.substring(0,3)));
            if (s.matches("00000KT"))
                setWind("Спокойный ветер");
            else if (s.matches("VBR\\d\\dMPS"))setWind("Направление ветра меняется");



            //Видимость
            if ( s.matches("\\d{4}")) setVisibility(s);
            else if (s.matches("\\d{2}SM"))setVisibility(s.substring(0,2) + " Сухопутных миль");
            else if (s.matches("\\dSM"))setVisibility(s.substring(0,1) + " Сухопутных миль");
          //  else return visibility = "нет информации";

            //Облачность

            if (s.matches("CAVOK") || s.equals("NSC") || s.equals("NCD") || s.equals("CLR")) setCloud("Небо чистое");
            else if  (s.matches("SCT\\d{3}") || s.matches("FEW\\d{3}"))setCloud("Малооблачно");
            else if (s.matches("BKN\\d{3}") || s.matches("OVC\\d{3}"))setCloud("Облачно");
            //else return cloud = "нет информации";

        }
    }

    public static void setTermo(String termo) {
        MetarObject.termo = termo;
    }

    public static void setPressure(String pressure) {
        MetarObject.pressure = pressure;
    }

    public static void setWind(String wind) {
        MetarObject.wind = wind;
    }

    public static void setVisibility(String visibility) {
        MetarObject.visibility = visibility;
    }

    public static void setCloud(String cloud) {
        MetarObject.cloud = cloud;
    }

    public static void setTermoString(String termoString) {
        MetarObject.termoString = termoString;
    }

    public static void setPressureString(String pressureString) {
        MetarObject.pressureString = pressureString;
    }

    public static String getTermo() {
        return termo;
    }

    public static String getPressure() {
        return pressure;
    }

    public static String getWind() {
        return wind;
    }

    public static String getVisibility() {
        return visibility;
    }

    public static String getCloud() {
        return cloud;
    }

    public static String getTermoString() {
        return termoString;
    }

    public static String getPressureString() {
        return pressureString;
    }

    public static   MetarObject getObj (String codeAirport, String w){
        /*nameAirport = String.valueOf(map.get("EditText Field"));
        getMeteo(map.get(nameAirport));*/
       /* setCloud(getMeteo(w));
        setTermo((getMeteo(w)));
        setPressure((getMeteo(w)));
        setVisibility(getMeteo(w));
        setWind(getMeteo(w));*/
        getMeteo(w);

        return new MetarObject
                        (codeAirport
                        ,getTermo()
                        ,getPressure()
                        ,getWind()
                        ,getVisibility()
                        ,getCloud());
    }

    @Override
    public String toString() {
        return "MetarObject{" + "\n" +
                " nameAirport=  '" + nameAirport + '\'' + "\n" +
                "  температура " + getTermo() + "\n" +
                "  Давление  " + getPressure() + "\n" +
                "  Ветер  " + getWind() + "\n" +
                "  Видимость " + getVisibility() + "\n" +
                "  Облачность " + getCloud()+ "\n" +
                '}';
    }
}
