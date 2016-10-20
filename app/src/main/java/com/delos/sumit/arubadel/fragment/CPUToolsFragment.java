package com.delos.sumit.arubadel.fragment;

/**
 * Created by sumit on 7/7/16.
 */

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

public class CPUToolsFragment extends Fragment
{
    private SwitchCompat mMPDecision;
    private SwitchCompat mCPU1;
    private SwitchCompat mCPU2;
    private SwitchCompat mCPU3;
    private TextView mCPUText;

    private ShellUtils mShell;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // get terminal session
        this.mShell = ((Activity) getActivity()).getShellSession();

        View rootView = inflater.inflate(R.layout.fragment_cputools, container, false);

        mCPU1 = (SwitchCompat) rootView.findViewById(R.id.cpu1);
        mCPU2 = (SwitchCompat) rootView.findViewById(R.id.cpu2);
        mCPU3 = (SwitchCompat) rootView.findViewById(R.id.cpu3);
        mCPUText = (TextView) rootView.findViewById(R.id.cputext);
        mMPDecision = (SwitchCompat) rootView.findViewById(R.id.mpdecision);

        //attach a listener to check for changes in state
        mCPU1.setOnCheckedChangeListener(createSwitchListener(1));
        mCPU2.setOnCheckedChangeListener(createSwitchListener(2));
        mCPU3.setOnCheckedChangeListener(createSwitchListener(3));

        mMPDecision.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                mShell.getSession().addCommand("killall mpdecision");
                mShell.getSession().addCommand("killall mpdecision");
                mShell.getSession().addCommand("killall mpdecision");
                mShell.getSession().addCommand("killall mpdecision");



                if (isChecked)
                    mShell.getSession().addCommand("start mpdecision");
            }
        });

        return rootView;
    }

    private void updateCpuState(final SwitchCompat switchView, final int cpuId)
    {
        mShell.getSession().addCommand("cat /sys/devices/system/cpu/cpu" + cpuId + "/online\n", cpuId, new Shell.OnCommandResultListener()
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

        updateCpuState(this.mCPU1, 1);
        updateCpuState(this.mCPU2, 2);
        updateCpuState(this.mCPU3, 3);

        mShell.getSession().addCommand("pgrep mpdecision", 10, new Shell.OnCommandResultListener()
        {
            @Override
            public void onCommandResult(int commandCode, int exitCode, List<String> output)
            {
               if (exitCode == 10)
                   mMPDecision.setChecked(output.size() > 0);
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
                mShell.getSession().addCommand("echo " + ((isChecked) ? 1 : 0) + " > /sys/devices/system/cpu/cpu" + cpuId + "/online\n");
                mCPUText.setText((isChecked) ? "turned on cpu "+ cpuId : "turned off cpu "+ cpuId);
            }
        };
    }

}
