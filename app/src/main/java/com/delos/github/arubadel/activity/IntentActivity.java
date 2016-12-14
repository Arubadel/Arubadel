package com.delos.github.arubadel.activity;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.delos.github.arubadel.MainActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

/**
 * Created by sumit on 14/12/16.
 */

public class IntentActivity extends Activity {
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST =9000 ;
    private String TAG="INTENT ACTIVITY";

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(checkPlayServices())
        {
            Intent intent = new Intent(this, MainActivity.class);
            startService(intent);
        }
        else
        {
            Intent intent = new Intent(this, MainActivity.class);
            startService(intent);
        }
    }



}
