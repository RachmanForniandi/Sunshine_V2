package com.example.android.sunshine_v2.sync;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;

import com.example.android.sunshine_v2.data.WeatherContract;
import com.example.android.sunshine_v2.utilities.NetworkUtils;
import com.example.android.sunshine_v2.utilities.OpenWeatherJsonUtils;

import java.net.URL;

/**
 * Created by USER on 20/03/2018.
 */

public class SunshineSyncTask {

    synchronized public static void syncWeather(Context ctx){

        try {
            /*
             * The getUrl method will return the URL that we need to get the forecast JSON for the
             * weather. It will decide whether to create a URL based off of the latitude and
             * longitude or off of a simple location as a String.
             */
            URL weatherRequestUrl = NetworkUtils.getUrl(ctx);

            /* Use the URL to retrieve the JSON */
            String jsonWeatherResponse = NetworkUtils.getResponseFromHttpUrl(weatherRequestUrl);

            /* Parse the JSON into a list of weather values */
            ContentValues[] weatherValues = OpenWeatherJsonUtils
                    .getWeatherContentValuesFromJson(ctx, jsonWeatherResponse);

            /*
             * In cases where our JSON contained an error code, getWeatherContentValuesFromJson
             * would have returned null. We need to check for those cases here to prevent any
             * NullPointerExceptions being thrown. We also have no reason to insert fresh data if
             * there isn't any to insert.
             */
            if (weatherValues != null && weatherValues.length != 0){
                /* Get a handle on the ContentResolver to delete and insert data */
                ContentResolver sunshineContentResolver = ctx.getContentResolver();

                //If we have valid results, delete the old data and insert the new
                /* Delete old weather data because we don't need to keep multiple days' data */
                sunshineContentResolver.delete(
                        WeatherContract.WeatherEntry.CONTENT_URI,
                        null,
                        null);

                /* Insert our new weather data into Sunshine's ContentProvider */
                sunshineContentResolver.bulkInsert(
                        WeatherContract.WeatherEntry.CONTENT_URI,
                        weatherValues);
            }

            /* If the code reaches this point, we have successfully performed our sync */
        }catch (Exception e){
             /* Server probably invalid */
            e.printStackTrace();
        }
    }
}
