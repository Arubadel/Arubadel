package com.delos.github.arubadel.activity;


import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
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

import com.delos.github.arubadel.R;
import com.delos.github.arubadel.app.Activity;
import com.delos.github.arubadel.fragment.AboutDevice;
import com.delos.github.arubadel.fragment.CPUToolsFragment;
import com.delos.github.arubadel.fragment.CreditsFragment;
import com.delos.github.arubadel.fragment.Flasher;
import com.delos.github.arubadel.fragment.GithubReleasesFragment;
import com.delos.github.arubadel.fragment.MiscFragment;
import com.delos.github.arubadel.fragment.MsmMpdecisionHotplug;
import com.delos.github.arubadel.fragment.OverAllDeviceInfo;
import com.delos.github.arubadel.fragment.PreferencesFragment;
import com.delos.github.arubadel.fragment.SelinuxChanger;
import com.delos.github.arubadel.util.CPUTools;
import com.delos.github.arubadel.util.Config;
import com.delos.github.arubadel.util.FileUtil;
import com.delos.github.arubadel.util.Tools;
import com.eminayar.panter.PanterDialog;
import com.eminayar.panter.enums.Animation;
import com.github.javiersantos.appupdater.AppUpdaterUtils;
import com.github.javiersantos.appupdater.enums.AppUpdaterError;
import com.github.javiersantos.appupdater.enums.UpdateFrom;
import com.github.javiersantos.appupdater.objects.Update;
import com.sendbird.SendBirdOpenChannelListActivity;
import com.sendbird.android.SendBird;
import com.sendbird.android.SendBirdException;
import com.sendbird.android.User;
import com.stericson.RootTools.RootTools;
import com.thefinestartist.finestwebview.FinestWebView;


import static com.delos.github.arubadel.util.NetworkStat.isNetworkAvailable;

public class MainActivity extends Activity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String appId = "1DEACB60-9F4A-40AE-B9C6-A7CFF1CF8BBE";
    // Keep fragments in memory and load once to use less memory
    private FloatingActionButton mFAB;
    private GithubReleasesFragment mFragmentRelKernel;
    private GithubReleasesFragment mFragmentRelApp;
    private GithubReleasesFragment mFragmentRelRecovery;
    private GithubReleasesFragment mFragmentRelROM;
    private String TAG = "MainActivity";
    private MenuItem msm_hotplug, Cputools, Misc, bSelinuxChanger, bOverAllDeviceStatus, bFlasher, mAppUpdates;
    /*Navigation drawer*/
    private NavigationView navigationView;
    private View navHeaderView;
    private SharedPreferences settings;
    private SharedPreferences.Editor editor;
    /*Dialog*/
    private PanterDialog UpdateDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SendBird.init(appId, this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        final DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navHeaderView = navigationView.inflateHeaderView(R.layout.nav_header_main);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        this.mFAB = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        this.mFragmentRelKernel = new GithubReleasesFragment().setTargetURL(Config.URL_KERNEL_RELEASES);
        this.mFragmentRelApp = new GithubReleasesFragment().setTargetURL(Config.URL_APP_RELEASES);
        this.mFragmentRelRecovery = new GithubReleasesFragment().setTargetURL(Config.URL_RECOVERY_RELEASES);
        this.mFragmentRelROM = new GithubReleasesFragment().setTargetURL(Config.URL_ROM_RELEASES);
        Menu menu = navigationView.getMenu();
        msm_hotplug = menu.findItem(R.id.nav_msm_mpdecision_hotplug);
        Cputools = menu.findItem(R.id.nav_cputools);
        Misc = menu.findItem(R.id.nav_misc);
        bSelinuxChanger = menu.findItem(R.id.nav_selinux_changer);
        bOverAllDeviceStatus = menu.findItem(R.id.nav_over_all_device_info);
        bFlasher = menu.findItem(R.id.nav_flasher);
        mAppUpdates = menu.findItem(R.id.nav_app_updates);

        if (RootTools.isRootAvailable()) {
            if (RootTools.isAccessGiven()) {

            Cputools.setVisible(true);
            Misc.setVisible(true);
            if (CPUTools.hasMsmMPDecisionHotplug())

            {
                msm_hotplug.setVisible(true);
            } else {
                msm_hotplug.setVisible(false);

            }
            this.updateFragment(new CPUToolsFragment());

            if (FileUtil.hasSelinux()) {
                bSelinuxChanger.setVisible(true);
            } else {
                bSelinuxChanger.setVisible(false);
            }
            bFlasher.setVisible(true);
            bOverAllDeviceStatus.setVisible(true);

            mFAB.setVisibility(View.VISIBLE);
        }else{
                Cputools.setVisible(false);
                Misc.setVisible(false);
                msm_hotplug.setVisible(false);
                bSelinuxChanger.setVisible(false);
                this.updateFragment(new AboutDevice());
                bOverAllDeviceStatus.setVisible(false);
                bFlasher.setVisible(false);
                Toast.makeText(getApplicationContext(), "Device is not rooted . Some options are hidden.", Toast.LENGTH_LONG).show();
                mFAB.setVisibility(View.GONE);
                Log.i(TAG,"Fuck you");
                UpdateDialog = new PanterDialog(MainActivity.this);
                UpdateDialog.setTitle("Permission not granted ")
                        .setHeaderBackground(R.color.colorPrimaryDark)
                        .setMessage("Please grant root permission to enable all features.")
                        .setNegative("dismiss", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                UpdateDialog.dismiss();
                            }
                        })
                        .setPositive("Grant", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                UpdateDialog.dismiss();
                            }
                        });
                UpdateDialog.show();

            }
        } else {
            Cputools.setVisible(false);
            Misc.setVisible(false);
            msm_hotplug.setVisible(false);
            bSelinuxChanger.setVisible(false);
            this.updateFragment(new AboutDevice());
            bOverAllDeviceStatus.setVisible(false);
            bFlasher.setVisible(false);
            Toast.makeText(getApplicationContext(), "Device is not rooted . Some options are hidden.", Toast.LENGTH_LONG).show();
            mFAB.setVisibility(View.GONE);

        }

        /*Hide app updates fragment*/
        mAppUpdates.setVisible(false);
        // register click listener for fab
        this.mFAB.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
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
                                if (strName.equals("Reboot")) {
                                    Tools.shell("reboot",true);
                                }
                                if (strName.equals("Power Off")) {
                                    Tools.shell("reboot -p",true);
                                }
                                if (strName.equals("Soft Reboot")) {
                                    Tools.shell("killall system_server",true);
                                }
                                if (strName.equals("Reboot Recovery")) {
                                    Tools.shell("reboot recovery",true);
                                }
                            }
                        });
                        builderSingle.show();
                    }
                }
        );

        /*Updater*/

        AppUpdaterUtils appUpdaterUtils = new AppUpdaterUtils(this)
                .setUpdateFrom(UpdateFrom.XML)
                .setUpdateXML("https://raw.githubusercontent.com/Arubadel/Arubadel/master/Updater.xml")
                .withListener(new AppUpdaterUtils.UpdateListener() {
                    @Override
                    public void onSuccess(final Update update, Boolean isUpdateAvailable) {
                        Log.d("AppUpdater", update.getLatestVersion() + ", " + update.getUrlToDownload() + ", " + Boolean.toString(isUpdateAvailable));
                        if (isUpdateAvailable == true) {
                            UpdateDialog = new PanterDialog(MainActivity.this);
                            UpdateDialog.setTitle("Update Found")
                                    .setHeaderBackground(R.color.colorPrimaryDark)
                                    .setMessage("Changelog :- \n\n" + update.getReleaseNotes())
                                    .setPositive("Download", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Uri uri = Uri.parse(String.valueOf(update.getUrlToDownload()));
                                            DownloadManager.Request request = new DownloadManager.Request(uri);
                                            String fileName = uri.getLastPathSegment();
                                            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);

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

        if (isNetworkAvailable(this) == true) {
            String x, y;
            String mPassword = "Password";
            String mEmail = "Email";
            x = getPreferences(mEmail);
            y = getPreferences(mPassword);
            Log.i(TAG, "Email :- " + x + " Password :- " + y);
        } else {
            Log.i(TAG, "Can't Login");
        }

        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);

    }

    public String getPreferences(String Name) {
        String o;
        settings = getSharedPreferences(Name, 0); // 0 - for private mode
        o = settings.getString(Name, null);

        return o;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_over_all_device_info) {
            this.updateFragment(new OverAllDeviceInfo());
            setTitle("Device Status");
        }

        if (id == R.id.nav_cputools) {
            this.updateFragment(new CPUToolsFragment());
            setTitle("Cpu Tools");
        } else if (id == R.id.nav_msm_mpdecision_hotplug) {
            this.updateFragment(new MsmMpdecisionHotplug());
            setTitle("Msm Hotplug");
        } else if (id == R.id.nav_misc) {
            this.updateFragment(new MiscFragment());
            setTitle("Misc Stuff");
        } else if (id == R.id.nav_selinux_changer) {
            this.updateFragment(new SelinuxChanger());
            setTitle("Selinux Changer");
        } else if (id == R.id.nav_flasher) {
            this.updateFragment(new Flasher());
            setTitle("Flash");
        } else if (id == R.id.nav_app_updates) {
            this.updateFragment(this.mFragmentRelApp);
            setTitle("App Updates");
        } else if (id == R.id.nav_kernel_updates) {
            this.updateFragment(this.mFragmentRelKernel);
            setTitle("Kernels");
        } else if (id == R.id.nav_recovery) {
            this.updateFragment(this.mFragmentRelRecovery);
            setTitle("Recoverys");
        } else if (id == R.id.nav_rom) {
            this.updateFragment(this.mFragmentRelROM);
            setTitle("Roms");
        } else if (id == R.id.nav_credits) {
            this.updateFragment(new CreditsFragment());
            setTitle("Credits");
        } else if (id == R.id.nav_settings) {
            this.updateFragment(new PreferencesFragment());
            setTitle("Settings");
        } else if (id == R.id.nav_about_device) {
            this.updateFragment(new AboutDevice());
            setTitle("About Device");
        } else if (id == R.id.nav_logout) {
            settings = getSharedPreferences("LoginUser", 0); // 0 - for private mode
            editor = settings.edit();
            editor.putBoolean("LoginUser", false);
            editor.commit();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        } else if (id == R.id.nav_chat) {
            connect();
        }else if(id==R.id.nav_website){
            new FinestWebView.Builder(this).show("https://arubadel.000webhostapp.com/");

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    protected void updateFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        ft.replace(R.id.content_frame, fragment);
        ft.commit();
    }

    private void connect() {
        Snackbar.make(getCurrentFocus(), "starting please wait", Snackbar.LENGTH_INDEFINITE).show();

        SendBird.connect(getPreferences("Email"), getPreferences("Password"), new SendBird.ConnectHandler() {
            @Override
            public void onConnected(User user, SendBirdException e) {
                if (e != null) {
                    Toast.makeText(MainActivity.this, "" + e.getCode() + ":" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    Snackbar.make(getCurrentFocus(), "Can't connect", Snackbar.LENGTH_SHORT).show();

                    return;
                }

                final String nickname = getPreferences("Name");

                SendBird.updateCurrentUserInfo(nickname, null, new SendBird.UserInfoUpdateHandler() {
                    @Override
                    public void onUpdated(SendBirdException e) {
                        if (e != null) {
                            Toast.makeText(MainActivity.this, "" + e.getCode() + ":" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            Snackbar.make(getCurrentFocus(), "Can't connect", Snackbar.LENGTH_SHORT).show();

                            return;
                        }

                        SharedPreferences.Editor editor = getPreferences(Context.MODE_PRIVATE).edit();
                        editor.putString("user_id", "");
                        editor.putString("nickname", nickname);
                        editor.commit();
                        Snackbar.make(getCurrentFocus(), "Started", Snackbar.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, SendBirdOpenChannelListActivity.class));
                    }
                });

            }
        });
    }

}
