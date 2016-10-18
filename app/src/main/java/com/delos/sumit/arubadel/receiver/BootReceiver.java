package com.delos.sumit.arubadel.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Veli on 18.10.2016.
 */

public class BootReceiver extends BroadcastReceiver
{

    @Override
    public void onReceive(Context context, Intent intent)
    {
        if (intent != null && Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction()))
        {
            // after device boots up this statement will be triggered
        }
    }
}
