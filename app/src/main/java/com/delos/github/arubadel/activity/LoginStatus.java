package com.delos.github.arubadel.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.delos.github.arubadel.MainActivity;

/**
 * Created by sumit on 10/1/17.
 */

public class LoginStatus extends Activity {
    private SharedPreferences settings;
    private String Tag="LoginStatus";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        settings = getSharedPreferences("LoginUser", 0); // 0 - for private mode
        boolean LoginUser = settings.getBoolean("LoginUser",false);
        if(LoginUser==true)
        {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            Log.i(Tag, String.valueOf(LoginUser));
            finish();
        }
        else
        {
            Log.i(Tag, String.valueOf(LoginUser));
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();

        }

    }
}
