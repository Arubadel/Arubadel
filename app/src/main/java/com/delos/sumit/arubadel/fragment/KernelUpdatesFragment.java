package com.delos.sumit.arubadel.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.delos.sumit.arubadel.R;
import com.delos.sumit.arubadel.adapter.SimpleFragmentPagerAdapter;
import com.delos.sumit.arubadel.fragment.kernelupdater.KernelReleasesBetaFragment;
import com.delos.sumit.arubadel.fragment.kernelupdater.KernelReleasesStableFragment;

/**
 * Created by Veli on 18.10.2016.
 */

public class KernelUpdatesFragment extends Fragment
{
    private ViewPager mPager;
    private TabLayout mTabLayout;
    private Fragment mFragmentBetaReleases;
    private Fragment mFragmentStableReleases;
    private SimpleFragmentPagerAdapter mFragmentPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_kernelupdates, container, false);

        this.mPager = (ViewPager) view.findViewById(R.id.fragment_kernelupdates_view_pager);
        this.mTabLayout = (TabLayout) view.findViewById(R.id.fragment_kernelupdates_tab_layout);

        this.mFragmentBetaReleases = Fragment.instantiate(getActivity(), KernelReleasesBetaFragment.class.getName(), savedInstanceState);
        this.mFragmentStableReleases = Fragment.instantiate(getActivity(), KernelReleasesStableFragment.class.getName(), savedInstanceState);

        this.mFragmentPager = new SimpleFragmentPagerAdapter(getFragmentManager(), getActivity(), new Fragment[]{mFragmentStableReleases, mFragmentBetaReleases}, new String[]{"Stable", "Beta"});

        this.mPager.setAdapter(this.mFragmentPager);
        this.mTabLayout.setupWithViewPager(this.mPager);


        return view;
    }
}
