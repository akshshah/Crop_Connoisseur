package com.example.cropconnoisseur.Utils;

import android.location.Location;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Common {

    public static final String APP_ID = "4e256e0940e676156215488acf4a09f9";

    public static Location currentLocation = null;

    public static final Map<String,String> getIcons = new HashMap<String, String>(){{
        put("Clear 01d","https://openweathermap.org/img/wn/01d@2x.png");
        put("Clear 01n","https://openweathermap.org/img/wn/01n@2x.png");

        put("few clouds 02d","https://openweathermap.org/img/wn/02d@2x.png");
        put("few clouds 02n","https://openweathermap.org/img/wn/02n@2x.png");

        put("scattered clouds 03d","https://openweathermap.org/img/wn/03d@2x.png");
        put("scattered clouds 03n","https://openweathermap.org/img/wn/03d@2x.png");

        put("broken clouds 04d","https://openweathermap.org/img/wn/04d@2x.png");
        put("broken clouds 04n","https://openweathermap.org/img/wn/04d@2x.png");

        put("overcast clouds 04d","https://openweathermap.org/img/wn/04d@2x.png");
        put("overcast clouds 04n","https://openweathermap.org/img/wn/04d@2x.png");

        put("Drizzle 09d","https://openweathermap.org/img/wn/09d@2x.png");
        put("Drizzle 09n","https://openweathermap.org/img/wn/09d@2x.png");

        put("Rain 10d","https://openweathermap.org/img/wn/10d@2x.png");
        put("Rain 10n","https://openweathermap.org/img/wn/10n@2x.png");

        put("Thunderstorm 11d","https://openweathermap.org/img/wn/11d@2x.png");
        put("Thunderstorm 11n","https://openweathermap.org/img/wn/11d@2x.png");

        put("Snow 13d","https://openweathermap.org/img/wn/13d@2x.png");
        put("Snow 13n","https://openweathermap.org/img/wn/13d@2x.png");

        put("Mist 50d","https://openweathermap.org/img/wn/50d@2x.png");
        put("Mist 50n","https://openweathermap.org/img/wn/50d@2x.png");
        put("Smoke 50d","https://openweathermap.org/img/wn/50d@2x.png");
        put("Smoke 50n","https://openweathermap.org/img/wn/50d@2x.png");
        put("Haze 50d","https://openweathermap.org/img/wn/50d@2x.png");
        put("Haze 50n","https://openweathermap.org/img/wn/50d@2x.png");
        put("Fog 50d","https://openweathermap.org/img/wn/50d@2x.png");
        put("Fog 50n","https://openweathermap.org/img/wn/50d@2x.png");
        put("Sand 50d","https://openweathermap.org/img/wn/50d@2x.png");
        put("Sand 50n","https://openweathermap.org/img/wn/50d@2x.png");
        put("Tornado 50d","https://openweathermap.org/img/wn/50d@2x.png");
        put("Tornado 50n","https://openweathermap.org/img/wn/50d@2x.png");
    }};

    public static String convertUnitToDate(long dt){
        Date date = new Date(dt*1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("h:mm a EEE, dd-MM-yyyy");
        String formatted = sdf.format(date);
        return formatted;
    }

    public static String convertUnitToHour(long sunrise) {
        Date date = new Date(sunrise*1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("h:mm a");
        String formatted = sdf.format(date);
        return formatted;
    }
}
