package com.delos.sumit.arubadel.fragment.preference;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.delos.sumit.arubadel.R;

/**
 * Created by: veli
 * Date: 10/25/16 11:56 PM
 */

public class MainPreferencesFragment extends PreferenceFragment
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences_main);
    }
}
