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

import com.delos.sumit.arubadel.R;
import com.delos.sumit.arubadel.app.Activity;
import com.delos.sumit.arubadel.util.ShellUtils;
import com.genonbeta.core.util.NetworkUtils;

import java.util.List;

import eu.chainfire.libsuperuser.Shell;

import static android.content.Context.WIFI_SERVICE;

/**
 * Created by Sumit on 19.10.2016.
 */

public class MiscFragment extends Fragment
{
    private SwitchCompat mADBSwitcher;
    private ShellUtils mShell;
    private TextView mInfoText;

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        this.mShell = ((Activity) getActivity()).getShellSession();

        View view = inflater.inflate(R.layout.fragment_misc, container, false);

        mADBSwitcher = (SwitchCompat) view.findViewById(R.id.fragment_misc_adb_switcher);
        mInfoText = (TextView) view.findViewById(R.id.fragment_misc_info_text);

        mADBSwitcher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                mShell.getSession().addCommand(((isChecked) ? "setprop service.adb.tcp.port 5555 ; stop adbd ; start adbd" : " setprop service.adb.tcp.port -1 ; stop adbd ; start adbd"));
            }
        });

        return view;
    }

    @Override
    public void onResume()
    {
        super.onResume();

        mShell.getSession().addCommand("getprop service.adb.tcp.port", 10, new Shell.OnCommandResultListener()
        {
            @Override
            public void onCommandResult(int commandCode, int exitCode, List<String> output)
            {
                if (output.size() > 0)
                    mADBSwitcher.setChecked(!"-1".equals(output.get(0)));
            }
        });

        List<String> availableNetworks = NetworkUtils.getInterfacesWithOnlyIp(true, new String[]{"rmnet"});

        if(availableNetworks.size() > 0)
            mInfoText.setText("adb connect " + availableNetworks.get(0) + ":5555");

    }
}