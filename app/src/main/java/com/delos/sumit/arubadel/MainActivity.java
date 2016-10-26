package com.delos.sumit.arubadel;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.delos.sumit.arubadel.app.Activity;
import com.delos.sumit.arubadel.fragment.CPUToolsFragment;
import com.delos.sumit.arubadel.fragment.CreditsFragment;
import com.delos.sumit.arubadel.fragment.GithubReleasesFragment;
import com.delos.sumit.arubadel.fragment.MiscFragment;
import com.delos.sumit.arubadel.fragment.PowerOptionsDialogFragment;
import com.delos.sumit.arubadel.fragment.PreferencesFragment;
import com.delos.sumit.arubadel.util.Config;

public class MainActivity extends Activity implements NavigationView.OnNavigationItemSelectedListener
{
    // Keep fragments in memory and load once to use less memory
    private CPUToolsFragment mFragmentCPUTools;
    private CreditsFragment mFragmentCredits;
    private MiscFragment mFragmentMisc;
    private PreferencesFragment mFragmentPreferences;
    private FloatingActionButton mFAB;
    private GithubReleasesFragment mFragmentRelKernel;
    private GithubReleasesFragment mFragmentRelApp;
    private GithubReleasesFragment mFragmentRelRecovery;
    private GithubReleasesFragment mFragmentRelROM;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        this.loadShell();

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        this.mFAB = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        this.mFragmentCPUTools = new CPUToolsFragment();
        this.mFragmentCredits = new CreditsFragment();
        this.mFragmentMisc = new MiscFragment();
        this.mFragmentRelKernel = new GithubReleasesFragment().setTargetURL(Config.URL_KERNEL_RELEASES);
        this.mFragmentRelApp = new GithubReleasesFragment().setTargetURL(Config.URL_APP_RELEASES);
        this.mFragmentRelRecovery = new GithubReleasesFragment().setTargetURL(Config.URL_RECOVERY_RELEASES);
        this.mFragmentRelROM = new GithubReleasesFragment().setTargetURL(Config.URL_ROM_RELEASES);
        this.mFragmentPreferences = new PreferencesFragment();

        // register click listener for fab
        this.mFAB.setOnClickListener(
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        PowerOptionsDialogFragment fragment = new PowerOptionsDialogFragment();
                        fragment.show(getSupportFragmentManager(), "power_dialog_fragment");
                    }
                }
        );

        this.updateFragment(this.mFragmentCPUTools);
    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if (id == R.id.nav_cputools)
            this.updateFragment(this.mFragmentCPUTools);
        else if (id == R.id.nav_misc)
            this.updateFragment(this.mFragmentMisc);
        else if (id == R.id.nav_app_updates)
            this.updateFragment(this.mFragmentRelApp);
        else if (id == R.id.nav_kernel_updates)
            this.updateFragment(this.mFragmentRelKernel);
        else if (id == R.id.nav_recovery)
            this.updateFragment(this.mFragmentRelRecovery);
        else if (id == R.id.nav_rom)
            this.updateFragment(this.mFragmentRelROM);
        else if (id == R.id.nav_credits)
            this.updateFragment(this.mFragmentCredits);
        else if (id == R.id.nav_credits)
            this.updateFragment(this.mFragmentCredits);
        else if (id == R.id.nav_settings)
            this.updateFragment(this.mFragmentPreferences);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    protected void updateFragment(Fragment fragment)
    {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        ft.replace(R.id.content_frame, fragment);
        ft.commit();
    }
}
