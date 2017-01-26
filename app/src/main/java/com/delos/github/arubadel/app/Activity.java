package com.delos.github.arubadel.app;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.delos.github.arubadel.R;
import com.delos.github.arubadel.util.ShellUtils;
import com.delos.github.arubadel.util.Tools;


/**
 * Created by: veli
 * Date: 10/20/16 4:05 PM
 */

public class Activity extends AppCompatActivity {
    private ShellUtils mShellInstance;
    private SharedPreferences settings;
    private SharedPreferences.Editor editor;
    private String o = null;

    public ShellUtils getShellSession() {
        if (this.mShellInstance == null || this.mShellInstance.getSession() == null)
            loadShell();

        return this.mShellInstance;
    }

    protected void loadShell() {
        this.mShellInstance = new ShellUtils(this);
    }

    protected void init() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);

        // try to find device code name
        if (!sp.contains("device_code_name")) {
            String deviceCode = null;

            if (Build.MODEL.equals("GT-I8552") || Build.MODEL.equals("I8552") || Build.MODEL.equals("GT-I8550") || Build.MODEL.equals("SAMSUNG I-8552"))
                deviceCode = "delos3geur";
            else if (Build.MODEL.equals("GT-I8262") || Build.MODEL.equals("GT-I8260"))
                deviceCode = "arubaslim";

            if (deviceCode == null)
                Toast.makeText(this, R.string.device_code_error, Toast.LENGTH_LONG).show();
            else
                sp.edit().putString("device_code_name", deviceCode).commit();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();
        SaveStat();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mShellInstance != null) {
            mShellInstance.closeSession();
            mShellInstance = null;
        }
    }

    public void SetPreferences(String Name, String stat) {
        settings = getSharedPreferences(Name, 0); // 0 - for private mode
        editor = settings.edit();
        editor.putString(Name, stat);
        editor.commit();

    }

    public void getGovernor() {
        o = Tools.shell("cat /sys/devices/system/cpu/cpu0/cpufreq/scaling_governor",false);
        SetPreferences("Governor", o);
    }

    public void getCpu1() {
        o = Tools.shell("cat /sys/devices/system/cpu/cpu1/online",false);
        SetPreferences("Cpu1", o);
    }

    public void getCpu2() {
        o = Tools.shell("cat /sys/devices/system/cpu/cpu2/online",false);
        SetPreferences("Cpu2", o);
    }

    public void getCpu3() {
        o = Tools.shell("cat /sys/devices/system/cpu/cpu3/online",false);
        SetPreferences("Cpu3", o);
    }

    public void getCpuMinFreq() {
        o = Tools.shell("cat /sys/devices/system/cpu/cpu0/cpufreq/scaling_min_freq",false);
        SetPreferences("Minfreq", o);
    }

    public void getCpuMaxFreq() {
        o = Tools.shell("cat /sys/devices/system/cpu/cpu0/cpufreq/scaling_max_freq",false);
        SetPreferences("Maxfreq", o);
    }

    public void SaveStat() {
        getGovernor();
        getCpu1();
        getCpu2();
        getCpu3();
        getCpuMinFreq();
        getCpuMaxFreq();
    }

}
