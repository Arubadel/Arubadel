package com.delos.github.arubadel.fragment;

/**
 * Created by sumit on 6/11/16.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.delos.github.arubadel.R;
import com.delos.github.arubadel.util.FileUtil;
import com.delos.github.arubadel.util.Tools;


/**
 * Created by sumit on 28/10/16.
 */

public class SelinuxChanger extends Fragment {

    private TextView mSelinuxStatus;
    private FileUtil Shell;
    private Button mEnforcing;
    private Button mPermissive;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_selinux, container, false);

        mSelinuxStatus = (TextView) rootView.findViewById(R.id.selinux_status_textView);
        mEnforcing = (Button) rootView.findViewById(R.id.Enforcing_button);
        mPermissive = (Button) rootView.findViewById(R.id.Permissive_button);
        mSelinuxStatus.setText(SelinuxStatus());

        if (SelinuxStatusFromKernel().equals("1")) {
            mPermissive.setEnabled(true);
            mEnforcing.setEnabled(false);
        } else {
            mEnforcing.setEnabled(true);
            mPermissive.setEnabled(false);
        }

        this.mEnforcing.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SetEnforcing();
                        mSelinuxStatus.setText(SelinuxStatus());
                        mEnforcing.setEnabled(false);
                        mPermissive.setEnabled(true);
                    }
                }
        );

        this.mPermissive.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SetPermissive();
                        mSelinuxStatus.setText(SelinuxStatus());
                        mPermissive.setEnabled(false);
                        mEnforcing.setEnabled(true);

                    }
                }
        );

        return rootView;
    }

    private String SelinuxStatus() {
        return Tools.shell("getenforce",false);
    }

    private String SelinuxStatusFromKernel() {
        return Tools.shell("cat /sys/fs/selinux/enforce",false);
    }

    private String SetEnforcing() {
        return Tools.shell("su -c 'setenforce 1'",false);
    }

    private String SetPermissive() {
        return Tools.shell("su -c 'setenforce 0'",false);
    }

}
