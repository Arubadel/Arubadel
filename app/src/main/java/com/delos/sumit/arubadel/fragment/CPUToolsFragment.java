package com.delos.sumit.arubadel.fragment;

/**
 * Created by sumit on 7/7/16.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.delos.sumit.arubadel.R;
import com.delos.sumit.arubadel.adapter.CPUToolsCPUListAdapter;
import com.delos.sumit.arubadel.app.Activity;
import com.delos.sumit.arubadel.util.CPUInfo;
import com.delos.sumit.arubadel.util.CPUTools;
import com.delos.sumit.arubadel.util.ConcurrentSync;
import com.delos.sumit.arubadel.util.Config;
import com.delos.sumit.arubadel.util.ShellUtils;

import java.util.List;

import eu.chainfire.libsuperuser.Shell;

public class CPUToolsFragment extends Fragment
{
    public static final String TAG = "CPUToolsFragment";

    private CPUCoreListFragment mCPUCoreListFragment;
    private SwitchCompat mMPDecision;
    private TextView mCPUInfoText;
    private TextView mCPUInfoCurrentSpeedText;
    private UpdateHelper mSync;
    private View mMPDecisionLayout;

    // cpu sliders
    private TextView mCPUSliderMinLow;
    private TextView mCPUSliderMinHigh;
    private TextView mCPUSliderMaxLow;
    private TextView mCPUSliderMaxHigh;
    private SeekBar mCPUSliderMinSeekBar;
    private SeekBar mCPUSliderMaxSeekBar;

    private ShellUtils mShell;
    private CPUInfo mCPUInfo = new CPUInfo();
    boolean suAvailable = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        suAvailable = Shell.SU.available();
        // get terminal session
        if (suAvailable)
        {

            this.mShell = ((Activity) getActivity()).getShellSession();
        }
        View rootView = inflater.inflate(R.layout.fragment_cputools, container, false);

        mCPUInfoText = (TextView) rootView.findViewById(R.id.fragment_cputools_cpuinfo_text);
        mCPUInfoCurrentSpeedText= (TextView) rootView.findViewById(R.id.fragment_cputools_cpuinfo_currentspeed_text);
        mMPDecision = (SwitchCompat) rootView.findViewById(R.id.fragment_cputools_mpdecision_switch);
        mMPDecisionLayout = (View) rootView.findViewById(R.id.fragment_cputools_mpdecision_layout);

        mCPUSliderMaxHigh = (TextView) rootView.findViewById(R.id.cpu_sliders_max_high_value);
        mCPUSliderMaxLow = (TextView) rootView.findViewById(R.id.cpu_sliders_max_low_value);
        mCPUSliderMinHigh = (TextView) rootView.findViewById(R.id.cpu_sliders_min_high_value);
        mCPUSliderMinLow = (TextView) rootView.findViewById(R.id.cpu_sliders_min_low_value);
        mCPUSliderMaxSeekBar = (SeekBar) rootView.findViewById(R.id.cpu_sliders_max_seekbar);
        mCPUSliderMinSeekBar = (SeekBar) rootView.findViewById(R.id.cpu_sliders_min_seekbar);
        suAvailable = Shell.SU.available();
        if (suAvailable) {

            mCPUSliderMaxSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    mShell.getSession().addCommand("echo " + (seekBar.getProgress() + mCPUInfo.speedMinAllowed) + " > " + Config.PATH_CPUS + "/cpu0/cpufreq/scaling_max_freq");
                }
            });

            mCPUSliderMinSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    mShell.getSession().addCommand("echo " + (seekBar.getProgress() + mCPUInfo.speedMinAllowed) + " > " + Config.PATH_CPUS + "/cpu0/cpufreq/scaling_min_freq");
                }
            });

            // Detected: mpdecision (make visible)
            if (CPUTools.hasMPDecision())
                mMPDecisionLayout.setVisibility(View.VISIBLE);

            mMPDecision.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    mShell.getSession().addCommand(((isChecked) ? "mount -o rw,remount,rw /system ; chmod 777 /system/bin/mpdecision; start mpdecision" : "mount -o rw,remount,rw /system ; chmod 664 /system/bin/mpdecision ; killall mpdecision; stop mpdecision"));
                }
            });

            // Update stats for initializing
            updateOnActivity();
        }
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        mCPUCoreListFragment = (CPUCoreListFragment) getChildFragmentManager().findFragmentById(R.id.cputools_cpulist_fragment);
    }

    @Override
    public void onResume()
    {
        super.onResume();

        // check if another thread is running. if not start one
        if (mSync == null || mSync.getState() == Thread.State.TERMINATED)
        {
            mSync = new UpdateHelper();
            mSync.start();
        }
    }

    @Override
    public void onPause()
    {
        super.onPause();

        mSync.interrupt();
    }

    private boolean updateOnActivity()
    {
        if (getActivity() == null || isDetached())
            return false;

        getActivity().runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                // catch the exceptions if response of shell delays
                try
                {
                    CPUTools.getCpuInfo(mShell, mCPUInfo);

                    StringBuilder cpuInfo = new StringBuilder();

                    mCPUInfoCurrentSpeedText.setText(mCPUInfo.speedCurrent / 1000 + "MHz");

                    cpuInfo.append(mCPUInfo.speedMin / 1000 + "MHz / " + mCPUInfo.speedMax / 1000 + "MHz");
                    cpuInfo.append("\n");
                    cpuInfo.append(mCPUInfo.governor);

                    mCPUSliderMaxLow.setText(mCPUInfo.speedMinAllowed / 1000 + "MHz");
                    mCPUSliderMaxHigh.setText(mCPUInfo.speedMaxAllowed / 1000 + "MHz");
                    mCPUSliderMinLow.setText(mCPUInfo.speedMinAllowed / 1000 + "MHz");
                    mCPUSliderMinHigh.setText(mCPUInfo.speedMax / 1000 + "MHz");

                    mCPUSliderMaxSeekBar.setMax((int)(mCPUInfo.speedMaxAllowed - mCPUInfo.speedMinAllowed));
                    mCPUSliderMinSeekBar.setMax((int)(mCPUInfo.speedMax - mCPUInfo.speedMinAllowed));

                    mCPUSliderMaxSeekBar.setProgress((int)(mCPUInfo.speedMax - mCPUInfo.speedMinAllowed));
                    mCPUSliderMinSeekBar.setProgress((int)(mCPUInfo.speedMin - mCPUInfo.speedMinAllowed));


                    if (mCPUInfo.governor != null)
                        mCPUInfoText.setText(cpuInfo.toString());

                    if (mCPUCoreListFragment != null)
                        mCPUCoreListFragment.update();

                    mShell.getSession().addCommand("pgrep mpdecision", 10, new Shell.OnCommandResultListener()
                    {
                        @Override
                        public void onCommandResult(int commandCode, int exitCode, List<String> output)
                        {
                            if (exitCode == 0)
                                mMPDecision.setChecked(output.size() > 0);
                        }
                    });
                }
                catch (RuntimeException e)
                {}
                catch (Exception e)
                {}
            }
        });

        return true;
    }

    protected class UpdateHelper extends ConcurrentSync
    {
        @Override
        protected void onRun()
        {
            try
            {
                Thread.sleep(2000);
                updateOnActivity();
            } catch (InterruptedException e)
            {
                // Known state don't do anything
            }
        }

        @Override
        protected boolean onCondition()
        {
            return !isDetached() && getActivity() != null;
        }
    }

}
