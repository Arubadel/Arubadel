package com.delos.github.arubadel;

/**
 * Created by sumit on 29/10/16.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import eu.chainfire.libsuperuser.Shell;

public class CheckSuSplash extends Activity {
    boolean suAvailable= Shell.SU.available();

    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 1;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        if (suAvailable) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                    Intent mainIntent = new Intent(CheckSuSplash.this, MainActivity.class);
                    CheckSuSplash.this.startActivity(mainIntent);
                    CheckSuSplash.this.finish();
                }
            }, SPLASH_DISPLAY_LENGTH);
        }
        else
        {
            finish();
            moveTaskToBack(true);
            Toast.makeText(getApplicationContext(), "Device is not rooted :( .", Toast.LENGTH_SHORT).show();


        }
    }
}
