package com.delos.sumit.arubadel.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.delos.sumit.arubadel.R;

import java.util.List;

import eu.chainfire.libsuperuser.Shell;
import eu.chainfire.libsuperuser.Shell.SH;

/**
 * Created by sumit on 28/10/16.
 */

public class AboutDevice extends Fragment
{
    public TextView model_text;
    public TextView platform_release_text;

    List<String> model_sh;
    List<String> platform_release;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        model_sh= Shell.SH.run("getprop ro.product.model");
        platform_release= Shell.SH.run("getprop ro.build.version.release");

        View rootView = inflater.inflate(R.layout.fragment_about_device, container, false);
        model_text=(TextView)rootView.findViewById(R.id.model_text);
        platform_release_text=(TextView)rootView.findViewById(R.id.platform_release_text);

        model_text.setText("Model Number :- " + model_sh);
        platform_release_text.setText("Device Version :- " + platform_release);
        return rootView;
    }

}
