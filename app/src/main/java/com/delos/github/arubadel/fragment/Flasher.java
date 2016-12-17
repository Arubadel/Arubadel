package com.delos.github.arubadel.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.delos.github.arubadel.R;
import com.delos.github.arubadel.app.Activity;
import com.delos.github.arubadel.util.FileUtil;
import com.delos.github.arubadel.util.ShellUtils;
import com.eminayar.panter.PanterDialog;
import com.eminayar.panter.enums.Animation;
import com.nononsenseapps.filepicker.FilePickerActivity;

import java.net.URISyntaxException;

import eu.chainfire.libsuperuser.Shell;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Sumit on 19.10.2016.
 */

public class Flasher extends Fragment
{
    private ShellUtils mShell;
    private RadioButton mFlashBoot,mFlashRecovery;
    private static final int FILE_SELECT_CODE = 0;
    private String path,mBootPath,mRecoveryPath,StringFlashRB;
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
                mBootPath="/sdcard/flashed";
                StringFlashRB="Boot";
                showFileChooser();
            }
        });

        mFlashRecovery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBootPath="/sdcard/flashed_recovery";
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
        Intent i = new Intent(getContext(), FilePickerActivity.class);
        i.putExtra(FilePickerActivity.EXTRA_ALLOW_MULTIPLE, false);
        i.putExtra(FilePickerActivity.EXTRA_ALLOW_CREATE_DIR, false);
        i.putExtra(FilePickerActivity.EXTRA_MODE, FilePickerActivity.MODE_FILE);
        i.addCategory(".img");
        i.putExtra(FilePickerActivity.EXTRA_START_PATH, Environment.getExternalStorageDirectory().getPath());

        startActivityForResult(i, FILE_SELECT_CODE);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    Log.d("TAG", "File Uri: " + uri.toString());
                    // Get the path
                    try {
                        path = FileUtil.getPath(getContext(), uri);
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                    Log.d("TAG", "File Path: " + path);
                    dialog = new PanterDialog(getContext());
                    dialog.setHeaderBackground(R.color.colorPrimaryDark)
                            .setMessage("Are you sure you want to Flash this "+StringFlashRB+" ?"+"\n"+path)
                            .setPositive("Flash", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                /*use sdcard/flash until this feature is ready*/
                                    Shell.SU.run("dd if="+path+" of="+mBootPath);
                                    dialog.dismiss();

                                }
                            })
                            .setNegative("Cancel")
                            .withAnimation(Animation.POP)
                            .show();

                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}
