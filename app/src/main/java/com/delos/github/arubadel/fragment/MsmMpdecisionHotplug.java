package com.delos.github.arubadel.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.delos.github.arubadel.R;
import com.delos.github.arubadel.app.Activity;
import com.delos.github.arubadel.util.ShellUtils;

import java.util.List;

import eu.chainfire.libsuperuser.Shell;

/**
 * Created by Sumit on 19.10.2016.
 */

public class MsmMpdecisionHotplug extends Fragment {
    private ShellUtils mShell;
    private SwitchCompat mMSM_Hotplug;


    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.mShell = ((Activity) getActivity()).getShellSession();

        View view = inflater.inflate(R.layout.fragment_msm_mpdecision_hotplug, container, false);
        mMSM_Hotplug = (SwitchCompat) view.findViewById(R.id.fragment_cputools_msm_mpdecision_hotplug_switch);

        mMSM_Hotplug.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mShell.getSession().addCommand("echo " + ((isChecked) ? 1 : 0) + " > /sys/kernel/msm_mpdecision/conf/enabled\n");
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        mShell.getSession().addCommand("cat /sys/kernel/msm_mpdecision/conf/enabled", 10, new Shell.OnCommandResultListener() {
            @Override
            public void onCommandResult(int commandCode, int exitCode, List<String> output) {
                if (output.size() > 0)
                    mMSM_Hotplug.setChecked("1".equals(output.get(0)));
            }
        });


    }
}
