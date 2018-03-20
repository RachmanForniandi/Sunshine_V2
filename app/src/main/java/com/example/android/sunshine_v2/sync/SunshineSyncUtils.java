package com.example.android.sunshine_v2.sync;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

/**
 * Created by USER on 20/03/2018.
 */

public class SunshineSyncUtils {

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
