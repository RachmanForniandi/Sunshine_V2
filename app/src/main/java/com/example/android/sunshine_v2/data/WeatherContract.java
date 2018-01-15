package com.example.android.sunshine_v2.data;

import android.provider.BaseColumns;

/**
 * Created by USER on 14/01/2018.
 */

public class WeatherContract {
    //Within WeatherContract, create a public static final class called WeatherEntry that implements BaseColumns
    public static final class WeatherEntry implements BaseColumns{
        //Create a public static final String call TABLE_NAME with the value "weather"
        public static final String TABLE_NAME = "weather";
        //Create a public static final String call COLUMN_DATE with the value "date"
        public static final String COLUMN_DATE = "date";
        //Create a public static final String call COLUMN_WEATHER_ID with the value "weather_id"
        public static final String COLUMN_WEATHER_ID = "weather_id";
        //Create a public static final String call COLUMN_MIN_TEMP with the value "min"
        public static final String COLUMN_MIN_TEMP = "min";
        //Create a public static final String call COLUMN_MAX_TEMP with the value "max"
        public static final String COLUMN_MAX_TEMP = "max";
        //Create a public static final String call COLUMN_HUMIDITY with the value "humidity"
        public static final String COLUMN_HUMIDITY = "humidity";
        //Create a public static final String call COLUMN_PRESSURE with the value "pressure"
        public static final String COLUMN_PRESSURE = "pressure";
        //Create a public static final String call COLUMN_WIND_SPEED with the value "wind"
        public static final String COLUMN_WIND_SPEED = "wind";
        //Create a public static final String call COLUMN_DEGREES with the value "degrees"
        public static final String COLUMN_DEGREES = "degrees";
    }

}
