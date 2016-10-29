package com.delos.sumit.arubadel;

/**
 * Created by sumit on 29/10/16.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class CheckSuSplash extends Activity {

    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 1000;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_check_su_splash);

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(CheckSuSplash.this,MainActivity.class);
                CheckSuSplash.this.startActivity(mainIntent);
                CheckSuSplash.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
