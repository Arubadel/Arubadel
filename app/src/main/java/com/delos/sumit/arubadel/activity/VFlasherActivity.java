package com.delos.sumit.arubadel.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.delos.sumit.arubadel.R;
import com.delos.sumit.arubadel.app.Activity;
import com.delos.sumit.arubadel.util.ShellUtils;

import java.io.File;
import java.net.URI;
import java.util.List;

import eu.chainfire.libsuperuser.Shell;

/**
 * Created by: veli
 * Date: 10/31/16 10:21 PM
 */

public class VFlasherActivity extends Activity
{
	protected int mSelectedPartition = -1;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		final ShellUtils shell = getShellSession();

		if (getIntent() != null)
		{
			Uri uri = getIntent().getData();

			if (uri != null)
			{
				final File file = new File(URI.create(uri.toString()));

				if(file.isFile())
				{
					setContentView(R.layout.activity_vflasher);

					TextView fileText = (TextView)findViewById(R.id.activity_vflasher_file_text);
					Button flashButton = (Button)findViewById(R.id.activity_vflasher_flash_button);

					fileText.setText(file.getAbsolutePath());

					flashButton.setOnClickListener(new View.OnClickListener()
					{
						@Override
						public void onClick(View v)
						{
							if (mSelectedPartition != -1)
							{
								String commandPrefix = "dd if='" + file.getAbsolutePath() + "' of=/dev/block/mmcblk0p";

								final String partitionName;
								final String command;

								if (mSelectedPartition == R.id.activity_vflasher_radio_kernel)
								{
									partitionName = getString(R.string.kernel_partition);
									command = commandPrefix + "8";
								}
								else
								{
									partitionName = getString(R.string.recovery_partition);
									command = commandPrefix + "13";
								}

								AlertDialog.Builder dialog = new AlertDialog.Builder(VFlasherActivity.this);

								dialog.setTitle(getString(R.string.flash_verb) + ": " + partitionName);
								dialog.setMessage(getString(R.string.flash_before_start_warning) + "\n\n" + command);

								dialog.setPositiveButton(R.string.proceed, new DialogInterface.OnClickListener()
								{
									@Override
									public void onClick(DialogInterface dialog, int which)
									{
										final ProgressDialog prDialog = new ProgressDialog(VFlasherActivity.this);
										prDialog.setTitle(getString(R.string.please_wait));
										prDialog.setMessage(getString(R.string.flashing) + ": " + partitionName);
										prDialog.setIndeterminate(true);
										prDialog.setCancelable(false);
										prDialog.show();

										shell.getSession().addCommand(command, 10, new Shell.OnCommandResultListener()
										{
											@Override
											public void onCommandResult(int commandCode, int exitCode, final List<String> output)
											{
												VFlasherActivity.this.runOnUiThread(new Runnable()
												{
													@Override
													public void run()
													{
														prDialog.cancel();

														if(output.size() > 0)
														{
															StringBuilder strB = new StringBuilder();

															for (String line : output)
															{
																strB.append("\n");
																strB.append(line);
															}

															Toast.makeText(VFlasherActivity.this, getString(R.string.flash_result, strB.toString()), Toast.LENGTH_LONG).show();
														}
														else
															Toast.makeText(VFlasherActivity.this, R.string.flash_gone_wrong_error, Toast.LENGTH_LONG).show();
													}
												});
											}
										});
									}
								});

								dialog.setNegativeButton("Cancel", null);
								dialog.show();
							}
							else
								Toast.makeText(VFlasherActivity.this, R.string.specify_partition_warning, Toast.LENGTH_SHORT).show();
						}
					});

					return;
				}
			}
		}

		Toast.makeText(this, "Not supported operation", Toast.LENGTH_SHORT).show();
		finish();
	}

	public void onRadioButtonClicked(View view)
	{
		this.mSelectedPartition = view.getId();
	}
}
