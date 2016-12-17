package com.delos.github.arubadel;


import android.Manifest;
import android.app.DownloadManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.delos.github.arubadel.app.Activity;
import com.delos.github.arubadel.app.LoginActivity;
import com.delos.github.arubadel.fragment.AboutDevice;
import com.delos.github.arubadel.fragment.CPUToolsFragment;
import com.delos.github.arubadel.fragment.CreditsFragment;
import com.delos.github.arubadel.fragment.FirebaseChat;
import com.delos.github.arubadel.fragment.Flasher;
import com.delos.github.arubadel.fragment.GithubReleasesFragment;
import com.delos.github.arubadel.fragment.MiscFragment;
import com.delos.github.arubadel.fragment.MsmMpdecisionHotplug;
import com.delos.github.arubadel.fragment.OverAllDeviceInfo;
import com.delos.github.arubadel.fragment.PreferencesFragment;
import com.delos.github.arubadel.fragment.SelinuxChanger;
import com.delos.github.arubadel.util.CPUTools;
import com.delos.github.arubadel.util.Config;
import com.delos.github.arubadel.util.ShellExecuter;
import com.eminayar.panter.PanterDialog;
import com.eminayar.panter.enums.Animation;
import com.github.javiersantos.appupdater.AppUpdaterUtils;
import com.github.javiersantos.appupdater.enums.AppUpdaterError;
import com.github.javiersantos.appupdater.enums.UpdateFrom;
import com.github.javiersantos.appupdater.objects.Update;
import com.google.firebase.auth.FirebaseAuth;

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
    private Flasher mFlasher;
    private FirebaseChat mFirebseChat;
    private FirebaseAuth mFBAuth;
    private String email;

    /*Navigation drawer*/
    private NavigationView navigationView;
    private View navHeaderView;

    /*Dialog*/
    private PanterDialog UpdateDialog;
    boolean suAvailable= Shell.SU.available();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        mFBAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        final DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navHeaderView= navigationView.inflateHeaderView(R.layout.nav_header_main);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
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
        mFlasher=new Flasher();
        mFirebseChat=new FirebaseChat();
        Menu menu = navigationView.getMenu();
        MenuItem msm_hotplug = menu.findItem(R.id.nav_msm_mpdecision_hotplug);
        MenuItem Cputools=menu.findItem(R.id.nav_cputools);
        MenuItem Misc=menu.findItem(R.id.nav_misc);
        MenuItem bSelinuxChanger=menu.findItem(R.id.nav_selinux_changer);
        MenuItem bOverAllDeviceStatus=menu.findItem(R.id.nav_over_all_device_info);
        MenuItem bFlasher=menu.findItem(R.id.nav_flasher);
        MenuItem mAppUpdates=menu.findItem(R.id.nav_app_updates);

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
    this.updateFragment(this.mFragmentCPUTools);

    if(ShellExecuter.hasSelinux())
    {
        bSelinuxChanger.setVisible(true);
    }else{
        bSelinuxChanger.setVisible(false);
    }
    bFlasher.setVisible(true);
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
    bFlasher.setVisible(false);
    Toast.makeText(getApplicationContext(), "Device is not rooted . Some options are hidden.", Toast.LENGTH_LONG).show();
    mFAB.setVisibility(View.GONE);

}

        /*Hide app updates fragment*/
        mAppUpdates.setVisible(false);

        // register click listener for fab
        this.mFAB.setOnClickListener(
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        AlertDialog.Builder builderSingle = new AlertDialog.Builder(MainActivity.this);
                        builderSingle.setIcon(R.mipmap.ic_launcher);
                        builderSingle.setTitle("Reboot Menu:-");

                        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.select_dialog_singlechoice);
                        arrayAdapter.add("Reboot");
                        arrayAdapter.add("Power Off");
                        arrayAdapter.add("Soft Reboot");
                        arrayAdapter.add("Reboot Recovery");

                        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String strName = arrayAdapter.getItem(which);
                                if(strName.equals("Reboot")){Shell.SU.run("reboot");}
                                if(strName.equals("Power Off")){Shell.SU.run("reboot -p");}
                                if(strName.equals("Soft Reboot")){Shell.SU.run("killall system_server");}
                                if(strName.equals("Reboot Recovery")){Shell.SU.run("reboot recovery");}
                            }
                        });
                        builderSingle.show();
                    }
                }
        );
        if (!sp.contains("user_nick_name")) {
            email=mFBAuth.getCurrentUser().getEmail();
            sp.edit().putString("user_nick_name", email).commit();
        }
        /*Updater*/

        AppUpdaterUtils appUpdaterUtils = new AppUpdaterUtils(this)
                .setUpdateFrom(UpdateFrom.XML)
                .setUpdateXML("https://raw.githubusercontent.com/Arubadel/Arubadel/master/Updater.xml")
        .withListener(new AppUpdaterUtils.UpdateListener() {
                    @Override
                    public void onSuccess(final Update update, Boolean isUpdateAvailable) {
                        Log.d("AppUpdater", update.getLatestVersion() + ", " + update.getUrlToDownload() + ", " + Boolean.toString(isUpdateAvailable));
                        if(isUpdateAvailable==true){
                            UpdateDialog= new PanterDialog(MainActivity.this);
                            UpdateDialog.setTitle("Update Found")
                                    .setHeaderBackground(R.color.colorPrimaryDark)
                                    .setMessage("Changelog :- \n\n"+update.getReleaseNotes())
                                    .setPositive("Download",new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Uri uri = Uri.parse(String.valueOf(update.getUrlToDownload()));
                                            DownloadManager.Request request = new DownloadManager.Request(uri);
                                            String  fileName = uri.getLastPathSegment();
                                            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,fileName);

                                            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                                            Long reference = downloadManager.enqueue(request);
                                            UpdateDialog.dismiss();

                                        }
                                    })
                                    .setNegative("DISMISS")
                                    .isCancelable(false)
                                    .withAnimation(Animation.SIDE)
                                    .show();


                        }
                    }

                    @Override
                    public void onFailed(AppUpdaterError error) {
                        Log.d("AppUpdater", "Something went wrong");
                    }
                });
                    appUpdaterUtils.start();

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
        else if (id == R.id.nav_flasher) {
            this.updateFragment(this.mFlasher);
            setTitle("Flash");
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
        }else if(id==R.id.nav_firebase_chat)
        {
            this.updateFragment(this.mFirebseChat);
            setTitle("Chat");
            mFAB.setVisibility(View.GONE);
        }else if (id==R.id.nav_logout){
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();

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
