package com.delos.github.arubadel.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.delos.github.arubadel.R;
import com.delos.github.arubadel.app.Activity;
import com.delos.github.arubadel.util.FileUtil;
import com.delos.github.arubadel.util.ShellUtils;
import com.delos.github.arubadel.util.Tools;

/**
 * Created by Sumit on 19.10.2016.
 */

public class OverAllDeviceInfo extends Fragment {
    private ShellUtils mShell;
    private SwitchCompat mMSM_Hotplug;
    private FileUtil Shell;
    private TextView mGpuStatus;
    private TextView mBatteryVoltage;
    private TextView mBatteryTmp;

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.mShell = ((Activity) getActivity()).getShellSession();

        View view = inflater.inflate(R.layout.fragment_all_device_info, container, false);
        mGpuStatus = (TextView) view.findViewById(R.id.Gpu_status_textview);
        mBatteryTmp = (TextView) view.findViewById(R.id.battery_tmp);
        mGpuStatus.setVisibility(View.GONE);
        mBatteryVoltage = (TextView) view.findViewById(R.id.battery_voltage);

        //Custom font
        final Typeface fontRegular=Typeface.createFromAsset(getContext().getAssets(), "fonts/dosisRegular.ttf");
        mGpuStatus.setTypeface(fontRegular);
        mBatteryTmp.setTypeface(fontRegular);
        mBatteryVoltage.setTypeface(fontRegular);

        Thread t = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                updateTextView();
                            }
                        });
                    }
                } catch (Exception e) {
                }
            }
        };

        t.start();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();


    }

    public String gpuStatus() {
        return Tools.shell("cat /sys/class/kgsl/kgsl-3d0", false);
    }

    private String batterytmp() {
        return Tools.shell("cat /sys/class/power_supply/battery/batt_temp", false);
    }

    private String batteryvol() {
        return Tools.shell("cat /sys/class/power_supply/battery/batt_vol", false);
    }

    private void updateTextView() {
        if (Shell.hasGpu()) {
            mGpuStatus.setVisibility(View.VISIBLE);
            mGpuStatus.setText("Gpu Status :- " + gpuStatus() + " hz");
        }
        mBatteryTmp.setText("Battery Tmp:- " + batterytmp());
        mBatteryVoltage.setText("Battery Voltage:- " + batteryvol() + "muv");
    }


}
