package Fragment;

/**
 * Created by sumit on 7/7/16.
 */

import android.os.*;
import android.support.annotation.*;
import android.support.v4.app.*;
import android.support.v7.widget.SwitchCompat;
import android.view.*;
import android.widget.*;
import com.delos.sumit.arubadel.*;
import eu.chainfire.libsuperuser.*;
import java.util.*;

public class MainFragment extends Fragment
{
	private SwitchCompat mCPU0;
	private SwitchCompat mCPU1;
	private SwitchCompat mCPU2;
	private SwitchCompat mCPU3;

	@Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
        View rootview = inflater.inflate(R.layout.fragment_main, container, false);
		mCPU0 = (SwitchCompat) rootview.findViewById(R.id.cpu0);
		mCPU1 = (SwitchCompat) rootview.findViewById(R.id.cpu1);
		mCPU2 = (SwitchCompat) rootview.findViewById(R.id.cpu2);
		mCPU3 = (SwitchCompat) rootview.findViewById(R.id.cpu3);

		//attach a listener to check for changes in state
		mCPU0.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
			{
				@Override
				public void onCheckedChanged(CompoundButton buttonView,
											 boolean isChecked)
                {
					if (isChecked)
                    {
						Shell.SU.run("echo \"1\" > /sys/devices/system/cpu/cpu1/online\n");
                    }
					else
                    {
						Shell.SU.run("echo \"0\" > /sys/devices/system/cpu/cpu1/online\n");
                    }
                }
            });

        return rootview;
    }

	@Override
	public void onResume()
	{
		super.onResume();
		
		List<String> cpu0_resultList = Shell.SU.run("cat /sys/devices/system/cpu/cpu1/online\n");
		List<String> cpu1_resultList = Shell.SU.run("cat /sys/devices/system/cpu/cpu2/online\n");
		List<String> cpu2_resultList = Shell.SU.run("cat /sys/devices/system/cpu/cpu2/online\n");
		List<String> cpu3_resultList = Shell.SU.run("cat /sys/devices/system/cpu/cpu3/online\n");

		if (cpu0_resultList.size() > 0)
		{
			// catch if bash send wrong type of string
			try
			{
				boolean currentState = 1 == Integer.valueOf(cpu0_resultList.get(0));
				this.mCPU0.setChecked(currentState);
			}
			catch(Exception e)
			{}
		}
		if (cpu1_resultList.size() > 0)
		{
			// catch if bash send wrong type of string
			try
			{
				boolean currentState = 1 == Integer.valueOf(cpu1_resultList.get(0));
				this.mCPU2.setChecked(currentState);
			}
			catch(Exception e)
			{}
		}

		if (cpu2_resultList.size() > 0)
		{
			// catch if bash send wrong type of string
			try
			{
				boolean currentState = 1 == Integer.valueOf(cpu2_resultList.get(0));
				this.mCPU2.setChecked(currentState);
			}
			catch(Exception e)
			{}
		}

		if (cpu3_resultList.size() > 0)
		{
			// catch if bash send wrong type of string
			try
			{
				boolean currentState = 1 == Integer.valueOf(cpu3_resultList.get(0));
				this.mCPU3.setChecked(currentState);
			}
			catch(Exception e)
			{}
		}

	}
}
