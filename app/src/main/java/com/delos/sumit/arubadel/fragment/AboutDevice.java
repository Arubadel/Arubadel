package com.delos.sumit.arubadel.fragment;

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
/**
 * Created by sumit on 28/10/16.
 */

public class AboutDevice extends Fragment
{
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_about_device, container, false);

        return rootView;
    }
}
