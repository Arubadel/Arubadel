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

/**
 * Created by Veli on 18.10.2016.
 */

public class RecoveryUpdatesFragment extends Fragment
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
        View view = inflater.inflate(R.layout.fragment_recovery, container, false);

        this.mPager = (ViewPager) view.findViewById(R.id.fragment_recovery_view_pager);
        this.mTabLayout = (TabLayout) view.findViewById(R.id.fragment_recovery_tab_layout);

        this.mFragmentBetaReleases = new KernelReleasesBetaFragment();
        this.mFragmentStableReleases = new KernelReleasesStableFragment();

        this.mFragmentPager = new SimpleFragmentPagerAdapter(getFragmentManager(), getActivity(), new Fragment[]{mFragmentStableReleases, mFragmentBetaReleases}, new String[]{getString(R.string.stable), getString(R.string.beta)});

        this.mPager.setAdapter(this.mFragmentPager);
        this.mTabLayout.setupWithViewPager(this.mPager);

        return view;
    }
}