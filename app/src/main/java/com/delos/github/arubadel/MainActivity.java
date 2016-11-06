package com.delos.github.arubadel;


import android.Manifest;
import android.os.Bundle;
import android.support.design.internal.NavigationMenu;
import android.support.design.internal.NavigationMenuItemView;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.delos.github.arubadel.app.Activity;
import com.delos.github.arubadel.fragment.AboutDevice;
import com.delos.github.arubadel.fragment.CPUToolsFragment;
import com.delos.github.arubadel.fragment.CreditsFragment;
import com.delos.github.arubadel.fragment.GithubReleasesFragment;
import com.delos.github.arubadel.fragment.MiscFragment;
import com.delos.github.arubadel.fragment.MsmMpdecisionHotplug;
import com.delos.github.arubadel.fragment.OverAllDeviceInfo;
import com.delos.github.arubadel.fragment.PowerOptionsDialogFragment;
import com.delos.github.arubadel.fragment.PreferencesFragment;
import com.delos.github.arubadel.fragment.SelinuxChanger;
import com.delos.github.arubadel.util.CPUTools;
import com.delos.github.arubadel.util.Config;
import com.delos.github.arubadel.util.ShellExecuter;

import eu.chainfire.libsuperuser.Shell;

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
    private AboutDevice mAboutDevice;
    private MsmMpdecisionHotplug mHotplug;
    private OverAllDeviceInfo mDeviceStatus;
    private SelinuxChanger mSelinuxChanger;
    boolean suAvailable= Shell.SU.available();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

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
        this.mAboutDevice = new AboutDevice();
        this.mHotplug=new MsmMpdecisionHotplug();
        mDeviceStatus=new OverAllDeviceInfo();
        mSelinuxChanger=new SelinuxChanger();
        Menu menu = navigationView.getMenu();
        MenuItem msm_hotplug = menu.findItem(R.id.nav_msm_mpdecision_hotplug);
        MenuItem Cputools=menu.findItem(R.id.nav_cputools);
        MenuItem Misc=menu.findItem(R.id.nav_misc);
        MenuItem bSelinuxChanger=menu.findItem(R.id.nav_selinux_changer);
        MenuItem bOverAllDeviceStatus=menu.findItem(R.id.nav_over_all_device_info);
if(suAvailable)
{
    Cputools.setVisible(true);
    Misc.setVisible(true);
    if(CPUTools.hasMsmMPDecisionHotplug())

    {
        msm_hotplug.setVisible(true);
    }
    else
    {
        msm_hotplug.setVisible(false);

    }
    this.updateFragment(this.mDeviceStatus);

    if(ShellExecuter.hasSelinux())
    {
        bSelinuxChanger.setVisible(true);
    }else{
        bSelinuxChanger.setVisible(false);
    }

    bOverAllDeviceStatus.setVisible(true);

    mFAB.setVisibility(View.VISIBLE);

}
else
{
    Cputools.setVisible(false);
    Misc.setVisible(false);
    msm_hotplug.setVisible(false);
    bSelinuxChanger.setVisible(false);
    this.updateFragment(this.mAboutDevice);
    bOverAllDeviceStatus.setVisible(false);
    Toast.makeText(getApplicationContext(), "Device is not rooted . Some options are hidden.", Toast.LENGTH_LONG).show();
    mFAB.setVisibility(View.GONE);

}
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


            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);

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

        if(id==R.id.nav_over_all_device_info)
        {
            this.updateFragment(this.mDeviceStatus);
            setTitle("Device Status");
        }

        if (id == R.id.nav_cputools) {
            this.updateFragment(this.mFragmentCPUTools);
            setTitle("Cpu Tools");
        }
        else if (id == R.id.nav_msm_mpdecision_hotplug) {
            this.updateFragment(this.mHotplug);
            setTitle("Msm Hotplug");
        }
        else if (id == R.id.nav_misc) {
            this.updateFragment(this.mFragmentMisc);
            setTitle("Misc Stuff");
        }
        else if (id == R.id.nav_selinux_changer) {
            this.updateFragment(this.mSelinuxChanger);
            setTitle("Selinux Changer");
        }
        else if (id == R.id.nav_app_updates) {
            this.updateFragment(this.mFragmentRelApp);
            setTitle("App Updates");
        }
         else if (id == R.id.nav_kernel_updates) {
            this.updateFragment(this.mFragmentRelKernel);
            setTitle("Kernels");
        }
        else if (id == R.id.nav_recovery) {
            this.updateFragment(this.mFragmentRelRecovery);
            setTitle("Recoverys");
        }
        else if (id == R.id.nav_rom) {
            this.updateFragment(this.mFragmentRelROM);
            setTitle("Roms");
        }
        else if (id == R.id.nav_credits)
        {
            this.updateFragment(this.mFragmentCredits);
            setTitle("Credits");
        }
        else if (id == R.id.nav_settings)
        {
            this.updateFragment(this.mFragmentPreferences);
            setTitle("Settings");
        }
        else if (id == R.id.nav_about_device) {
            this.updateFragment(this.mAboutDevice);
            setTitle("About Device");
        }
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
