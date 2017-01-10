package com.delos.github.arubadel.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by sumit on 10/1/17.
 */

public class DeepReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, RestoreDeviceStat.class);
        context.startService(i);
    }
}
