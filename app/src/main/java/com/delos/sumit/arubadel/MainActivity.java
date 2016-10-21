package com.delos.sumit.arubadel;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.delos.sumit.arubadel.app.Activity;
import com.delos.sumit.arubadel.fragment.CPUToolsFragment;
import com.delos.sumit.arubadel.fragment.CreditsFragment;
import com.delos.sumit.arubadel.fragment.KernelUpdatesFragment;
import com.delos.sumit.arubadel.fragment.PowerFragment;

public class MainActivity extends Activity implements NavigationView.OnNavigationItemSelectedListener
{
    // Keep fragments in memory and load once to use less memory
    public KernelUpdatesFragment mFragmentKernelUpdates;
    public CPUToolsFragment mFragmentCPUTools;
    public CreditsFragment mFragmentCredits;
    public PowerFragment mFragmentPower;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        this.loadShell();

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        this.mFragmentKernelUpdates = new KernelUpdatesFragment();
        this.mFragmentCPUTools = new CPUToolsFragment();
        this.mFragmentCredits = new CreditsFragment();
        this.mFragmentPower = new PowerFragment();

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        FragmentManager fm = getFragmentManager();
        int id = item.getItemId();

        if (id == R.id.nav_fragment1)
        {
            this.updateFragment(this.mFragmentCPUTools);
        }
        else if (id == R.id.nav_power)
        {
            this.updateFragment(this.mFragmentPower);
        }
        else if (id == R.id.nav_kernel_updates)
        {
            this.updateFragment(this.mFragmentKernelUpdates);
        }
        else if (id == R.id.nav_credits)
        {
            this.updateFragment(this.mFragmentCredits);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    protected void updateFragment(Fragment fragment)
    {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment).commit();
    }
}
