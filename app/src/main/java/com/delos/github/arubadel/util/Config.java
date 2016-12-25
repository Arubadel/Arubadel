package com.delos.github.arubadel.util;

import android.app.Activity;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by: veli
 * Date: 10/21/16 12:41 AM
 */


public class Config extends Activity
{
    public static FirebaseAuth auth;
    public static void getInstance(){
        auth = FirebaseAuth.getInstance();
    }


    public final static String PATH_CPUS = "/sys/devices/system/cpu";
    public final static String URL_CONTRIBUTORS = "https://api.github.com/repos/Arubadel/Arubadel/contributors";
    public final static String URL_APP_RELEASES ="https://api.github.com/repos/Arubadel/AppUpdates/releases";
    public final static String URL_KERNEL_RELEASES ="https://api.github.com/repos/Arubadel/Kernel/releases";
    public final static String URL_RECOVERY_RELEASES ="https://api.github.com/repos/Arubadel/Recovery/releases";
    public final static String URL_ROM_RELEASES="https://api.github.com/repos/Arubadel/ROM/releases";
    public static String UserName(){

        String getName=auth.getCurrentUser().getEmail();

        return getName;
    }
}
