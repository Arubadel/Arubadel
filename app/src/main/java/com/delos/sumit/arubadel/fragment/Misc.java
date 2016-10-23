package com.delos.sumit.arubadel.fragment;

import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.delos.sumit.arubadel.app.Activity;
import com.delos.sumit.arubadel.util.ShellUtils;

import com.delos.sumit.arubadel.R;

import java.util.List;

import static android.content.Context.WIFI_SERVICE;

/**
 * Created by Sumit on 19.10.2016.
 */

public class Misc extends Fragment
{
    private SwitchCompat mADB_WIRELESS;
    private ShellUtils mShell;
    private TextView madb_wireless_text;

    @Nullable
    @Override
    @SuppressWarnings("deprecation")

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        this.mShell = ((Activity) getActivity()).getShellSession();

        View view =inflater.inflate(R.layout.fragment_misc, container, false);
        mADB_WIRELESS = (SwitchCompat) view.findViewById(R.id.adb_wireless);

        mADB_WIRELESS.setVisibility(View.VISIBLE);

        mADB_WIRELESS.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                mShell.getSession().addCommand(((isChecked) ? "setprop service.adb.tcp.port 5555 ; stop adbd ; start adbd" : " setprop service.adb.tcp.port -1 ; stop adbd ; start adbd"));
            }
        });
try {
    madb_wireless_text = (TextView) view.findViewById(R.id.adb_wireless_text);
    WifiManager wim = (WifiManager) getActivity().getSystemService(WIFI_SERVICE);
    List<WifiConfiguration> l = wim.getConfiguredNetworks();
    WifiConfiguration wc = l.get(0);
    madb_wireless_text.append("\n" + "adb connect \n" + Formatter.formatIpAddress(wim.getConnectionInfo().getIpAddress()));
}
catch (Exception e)
{
    e.printStackTrace();
}
        return view;
    }
}
