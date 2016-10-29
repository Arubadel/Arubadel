package com.delos.sumit.arubadel.fragment;

/**
 * Created by sumit on 29/10/16.
 */

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.delos.sumit.arubadel.R;
import com.delos.sumit.arubadel.adapter.TcpCongestionControlAdapter;
import com.delos.sumit.arubadel.app.Activity;
import com.delos.sumit.arubadel.util.ShellUtils;

import eu.chainfire.libsuperuser.Shell;

/**
 * Created by: veli
 * Date: 10/23/16 4:17 PM
 */

public class TcpOptionDialogFragment extends DialogFragment
{

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        final TcpCongestionControlAdapter adapter = new TcpCongestionControlAdapter(getActivity());

        final ShellUtils shell = ((Activity)getActivity()).getShellSession();

        dialogBuilder.setTitle(getString(R.string.choose_tcp));
        //dialogBuilder.setMessage("What would like to do?");

        dialogBuilder.setAdapter(adapter, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                shell.getSession().addCommand(((TcpCongestionControlAdapter.TcpItem) adapter.getItem(which)).command);
            }
        });

        return dialogBuilder.create();
    }
}
