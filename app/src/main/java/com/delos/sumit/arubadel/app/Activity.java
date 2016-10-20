package com.delos.sumit.arubadel.app;

import android.support.v7.app.AppCompatActivity;

import com.delos.sumit.arubadel.util.ShellUtils;

/**
 * Created by: veli
 * Date: 10/20/16 4:05 PM
 */

public class Activity extends AppCompatActivity
{
    private ShellUtils mShellInstance;

    protected void loadShell()
    {
        this.mShellInstance = new ShellUtils(this);
    }

    public ShellUtils getShellSession()
    {
        return this.mShellInstance;
    }
}
