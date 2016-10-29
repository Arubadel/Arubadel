package com.delos.sumit.arubadel.fragment;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.delos.sumit.arubadel.R;
import com.delos.sumit.arubadel.util.ShellUtils;

import java.util.List;

import eu.chainfire.libsuperuser.Shell;
import eu.chainfire.libsuperuser.Shell.SH;

/**
 * Created by sumit on 28/10/16.
 */

public class AboutDevice extends Fragment
{
    private TextView model_text;
    private TextView mplatform_release_text;
    private TextView mRil_class;
    private TextView mBoard;
    private TextView mKernel_version;
    private TextView mBuild_description;
    private TextView mBuild_fingerprint;
    private TextView mRoot_Status;
    String App_version = "";
    TextView mApp_version;

    List<String> model_sh=Shell.SH.run("getprop ro.product.model");;
    List<String> platform_release=Shell.SH.run("getprop ro.build.version.release");
    List<String> ril_class=Shell.SH.run("getprop ro.telephony.ril_class");
    List<String> board=Shell.SH.run("getprop ro.product.board");
    List<String> kernel_version=Shell.SH.run("cat /proc/version");
    List<String> build_desciption=Shell.SH.run("getprop ro.build.description");
    List<String> build_fingerprint=Shell.SH.run("getprop ro.build.fingerprint");

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_about_device, container, false);
        model_text=(TextView)rootView.findViewById(R.id.model_text);
        mplatform_release_text=(TextView)rootView.findViewById(R.id.platform_release_text);
        mRil_class=(TextView)rootView.findViewById(R.id.Ril_class);
        mBuild_fingerprint=(TextView)rootView.findViewById(R.id.build_fingerprint);
        mBuild_description=(TextView)rootView.findViewById(R.id.build_discription_text);
        mKernel_version=(TextView)rootView.findViewById(R.id.kernel_version_text);
        mBoard=(TextView)rootView.findViewById(R.id.board_text);
        mRoot_Status=(TextView)rootView.findViewById(R.id.root_status);
         mApp_version = (TextView) rootView.findViewById(R.id.app_version);

        try
        {
            App_version = getActivity().getApplicationContext().getPackageManager().getPackageInfo(getActivity().getApplicationContext().getPackageName(), 0).versionName;
        }
        catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
        mApp_version.setText("App Version :- "+ "[ "+App_version+" ]\n");

        model_text.setText("Model Number :- " + model_sh+"\n");
        mplatform_release_text.setText("Device Version :- " + platform_release+"\n");
        mRil_class.setText("Ril Class :- " + ril_class+"\n");
        mBoard.setText("Device Board :- " + board+"\n");
        mKernel_version.setText("Kernel Version :- " + kernel_version+"\n");
        mBuild_description.setText("Build Description :- " + build_desciption+"\n");
        mBuild_fingerprint.setText("Build FingerPrint :- " + build_fingerprint+"\n");
        mRoot_Status.setText("Root Status :- Rooted" + "\n");

        return rootView;
    }

}
