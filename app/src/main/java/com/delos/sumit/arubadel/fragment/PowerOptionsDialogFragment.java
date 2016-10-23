package com.delos.sumit.arubadel.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.delos.sumit.arubadel.adapter.PowerManagementAdapter;
import com.delos.sumit.arubadel.app.Activity;
import com.delos.sumit.arubadel.util.ShellUtils;

/**
 * Created by: veli
 * Date: 10/23/16 4:17 PM
 */

public class PowerOptionsDialogFragment extends DialogFragment
{
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        final PowerManagementAdapter adapter = new PowerManagementAdapter(getActivity());

        final ShellUtils shell = ((Activity)getActivity()).getShellSession();

        dialogBuilder.setTitle("Power options");
        //dialogBuilder.setMessage("What would like to do?");

        dialogBuilder.setAdapter(adapter, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                shell.getSession().addCommand(((PowerManagementAdapter.PowerItem) adapter.getItem(which)).command);
            }
        });

        return dialogBuilder.create();
    }
}
