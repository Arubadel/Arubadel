package com.delos.sumit.arubadel.fragment;

/**
 * Created by sumit on 7/7/16.
 */

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.delos.sumit.arubadel.R;
import com.delos.sumit.arubadel.app.Activity;
import com.delos.sumit.arubadel.util.ShellUtils;

import java.util.List;

import eu.chainfire.libsuperuser.Shell;

public class PowerFragment extends Fragment
{
    private SwitchCompat mFastChargeSwitcher;
    private TextView mInfoText;
    private ShellUtils mShell;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // get terminal session
        this.mShell = ((Activity) getActivity()).getShellSession();

        View rootView = inflater.inflate(R.layout.fragment_power, container, false);

        mFastChargeSwitcher = (SwitchCompat) rootView.findViewById(R.id.fragment_power_fastcharge_switch);
        mInfoText = (TextView) rootView.findViewById(R.id.fragment_power_info_text);

        //attach a listener to check for changes in state
        mFastChargeSwitcher.setOnCheckedChangeListener(createSwitchListener(1));

        return rootView;
    }

    private void updateCpuState(final SwitchCompat switchView, final int cpuId)
    {
        mShell.getSession().addCommand("cat sys/kernel/fast_charge/force_fast_charge\n", cpuId, new Shell.OnCommandResultListener()
        {
            @Override
            public void onCommandResult(int commandCode, int exitCode, List<String> output)
            {
                if (commandCode == cpuId)
                {
                    if (output.size() > 0)
                    {
                        // catch if bash send wrong type of string
                        try
                        {
                            boolean currentState = 1 == Integer.valueOf(output.get(0));
                            switchView.setChecked(currentState);
                        } catch (Exception e)
                        {
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onResume()
    {
        super.onResume();

        updateCpuState(this.mFastChargeSwitcher, 1);

        mShell.getSession().addCommand("pgrep mpdecision", 10, new Shell.OnCommandResultListener()
        {
            @Override
            public void onCommandResult(int commandCode, int exitCode, List<String> output)
            {
                if (exitCode == 10)
                    mFastChargeSwitcher.setChecked(output.size() > 0);
            }
        });
    }

    private CompoundButton.OnCheckedChangeListener createSwitchListener(final int cpuId)
    {
        return new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                mShell.getSession().addCommand("echo " + ((isChecked) ? 1 : 0) + " > sys/kernel/fast_charge/force_fast_charge\n");
                mInfoText.setText(getString((isChecked) ? R.string.fast_charge_enabled : R.string.fast_charge_disable));
                mInfoText.setTextColor(Color.GREEN);
            }
        };
    }

}
