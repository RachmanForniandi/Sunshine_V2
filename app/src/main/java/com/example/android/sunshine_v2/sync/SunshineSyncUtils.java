package com.example.android.sunshine_v2.sync;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.example.android.sunshine_v2.data.WeatherContract;

/**
 * Created by USER on 20/03/2018.
 */

public class SunshineSyncUtils {
    //Declare a private static boolean field called sInitialized
    private static boolean sInitialized;

    //Create a synchronized public static void method called initialize
    synchronized public static void initialize(@NonNull final Context context){

        //Only execute this method body if sInitialized is false
        /*
         * Only perform initialization once per app lifetime. If initialization has already been
         * performed, we have nothing to do in this method.
         */
        if (sInitialized)return;

        //If the method body is executed, set sInitialized to true
        sInitialized = true;
        new AsyncTask<Void, Void, Void>(){
            @Override
            public Void doInBackground(Void... voids){
                /* URI for every row of weather data in our weather table*/
                Uri forecastQueryUri = WeatherContract.WeatherEntry.CONTENT_URI;

                /*
                 * Since this query is going to be used only as a check to see if we have any
                 * data (rather than to display data), we just need to PROJECT the ID of each
                 * row. In our queries where we display data, we need to PROJECT more columns
                 * to determine what weather details need to be displayed.
                 */
                String[] projectionColumns = {WeatherContract.WeatherEntry._ID};
                String selectionStatement = WeatherContract.WeatherEntry
                        .getSqlSelectForTodayOnWards();

                /* Here, we perform the query to check to see if we have any weather data */
                Cursor cursor = context.getContentResolver().query(
                        forecastQueryUri,
                        projectionColumns,
                        selectionStatement,
                        null,
                        null);
                /*
                 * A Cursor object can be null for various different reasons. A few are
                 * listed below.
                 *
                 *   1) Invalid URI
                 *   2) A certain ContentProvider's query method returns null
                 *   3) A RemoteException was thrown.
                 *
                 * Bottom line, it is generally a good idea to check if a Cursor returned
                 * from a ContentResolver is null.
                 *
                 * If the Cursor was null OR if it was empty, we need to sync immediately to
                 * be able to display data to the user.
                 */
                if (null == cursor || cursor.getCount() ==0){
                    startImmediateSync(context);
                }
                cursor.close();
                return null;
            }
        }.execute();
    }

    /**
     * Helper method to perform a sync immediately using an IntentService for asynchronous
     * execution.
     *
     * @param ctx The Context used to start the IntentService for the sync.
     */
    public static void startImmediateSync(@NonNull final Context ctx){
        //Within that method, start the SunshineSyncIntentService
        Intent intentToSyncImmediately = new Intent(ctx, SunshineSyncIntentService.class);
        ctx.startService(intentToSyncImmediately);
    }
}
