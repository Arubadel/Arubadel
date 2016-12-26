package com.delos.github.arubadel.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.delos.github.arubadel.R;
import com.delos.github.arubadel.app.Activity;
import com.delos.github.arubadel.util.ShellUtils;
import com.eminayar.panter.PanterDialog;
import com.eminayar.panter.enums.Animation;
import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import java.util.regex.Pattern;

import eu.chainfire.libsuperuser.Shell;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Sumit on 19.10.2016.
 */

public class Flasher extends Fragment
{
    private ShellUtils mShell;
    private RadioButton mFlashBoot,mFlashRecovery;
    private static final int FILE_PICKER_REQUEST_CODE = 1;
    private String path,mBRootPath,StringFlashRB;
    private PanterDialog dialog;

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        this.mShell = ((Activity) getActivity()).getShellSession();
        View view = inflater.inflate(R.layout.fragment_flasher, container, false);
        mFlashBoot=(RadioButton)view.findViewById(R.id.fragment_flasher_radio_boot);
        mFlashRecovery=(RadioButton)view.findViewById(R.id.fragment_flasher_radio_recovery);


        mFlashBoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBRootPath="/sdcard/flashed";
                StringFlashRB="Boot";
                showFileChooser();
            }
        });

        mFlashRecovery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBRootPath="/sdcard/flashed_recovery";
                StringFlashRB="Recovery";
                showFileChooser();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();


    }

    private void showFileChooser() {
        new MaterialFilePicker()
                .withSupportFragment(this)
                .withRequestCode(FILE_PICKER_REQUEST_CODE)
                .withHiddenFiles(true)
                .withFilter(Pattern.compile(".*\\.img$"))
                .start();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            path = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
            dialog = new PanterDialog(getContext());
            dialog.setTitle("Flash "+StringFlashRB );
            dialog.setHeaderBackground(R.color.colorPrimaryDark)
                    .setMessage("Are you sure you want to Flash this "+StringFlashRB+" ?"+"\n"+path)
                    .setPositive("Flash", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                /*use sdcard/flash until this feature is ready*/
                            Shell.SU.run("dd if="+path+" of="+mBRootPath);
                            dialog.dismiss();

                        }
                    })
                    .setNegative("Cancel")
                    .withAnimation(Animation.POP)
                    .show();
        }
    }



}
