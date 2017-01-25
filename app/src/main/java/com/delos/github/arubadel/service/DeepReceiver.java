package com.delos.github.arubadel.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by sumit on 10/1/17.
 */

public class DeepReceiver extends BroadcastReceiver {
    private String TAG = "DeepSleepReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, RestoreDeviceStat.class);
        Log.i(TAG, "Starting Service");
        context.startService(i);

    }
}
