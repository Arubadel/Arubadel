package com.delos.github.arubadel.service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.delos.github.arubadel.util.Tools;

/**
 * Created by sumit on 10/1/17.
 */

public class RestoreDeviceStat extends Service {
    private SharedPreferences settings;
    private String o, TAG = "Arubadel";

    public IBinder onBind(Intent intent) {
        return null;
    }

    @Nullable
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        SaveStat();
        return START_STICKY;
    }

    private String getPref(String name, String getStatus) {
        settings = getSharedPreferences(name, 0); // 0 - for private mode
        o = settings.getString(name, getStatus);
        return o;
    }

    private void Governor() {
        o = getPref("Governor", null);
        Tools.shell("echo " + o + " > /sys/devices/system/cpu/cpu0/cpufreq/scaling_governor",true);
        Log.i(TAG, o);
    }

    private void Cpu1() {
        o = getPref("Cpu1", null);
        Tools.shell("echo " + o + " > cat /sys/devices/system/cpu/cpu1/online",true);
        Log.i(TAG, o);

    }

    private void Cpu2() {
        o = getPref("Cpu2", null);
        Tools.shell("echo " + o + " > cat /sys/devices/system/cpu/cpu2/online",true);
        Log.i(TAG, o);

    }

    private void Cpu3() {
        o = getPref("Cpu2", null);
        Tools.shell("echo " + o + " > cat /sys/devices/system/cpu/cpu3/online",true);
        Log.i(TAG, o);

    }

    private void getCpuMinFreq() {
        o = getPref("Minfreq", null);
        Tools.shell("echo " + o + " > /sys/devices/system/cpu/cpu0/cpufreq/scaling_min_freq",true);
        Log.i(TAG, o);

    }

    private void getCpuMaxFreq() {
        o = getPref("Maxfreq", null);
        Tools.shell("echo " + o + " > /sys/devices/system/cpu/cpu0/cpufreq/scaling_max_freq",true);
        Log.i(TAG, o);

    }

    public void SaveStat() {
        Governor();
        Cpu1();
        Cpu2();
        Cpu3();
        getCpuMinFreq();
        getCpuMaxFreq();
    }

}
