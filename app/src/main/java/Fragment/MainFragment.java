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
	private SwitchCompat mCPU1;
	private SwitchCompat mCPU2;
	private SwitchCompat mCPU3;

	@Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
        View rootview = inflater.inflate(R.layout.fragment_main, container, false);
		mCPU1 = (SwitchCompat) rootview.findViewById(R.id.cpu1);
		mCPU2 = (SwitchCompat) rootview.findViewById(R.id.cpu2);
		mCPU3 = (SwitchCompat) rootview.findViewById(R.id.cpu3);

		//attach a listener to check for changes in state
		mCPU1.setOnCheckedChangeListener(createSwitchListener(1));
		mCPU2.setOnCheckedChangeListener(createSwitchListener(2));
		mCPU3.setOnCheckedChangeListener(createSwitchListener(3));
		
        return rootview;
    }

	@Override
	public void onResume()
	{
		super.onResume();

		updateCpuState(this.mCPU1, 1);
		updateCpuState(this.mCPU2, 2);
		updateCpuState(this.mCPU3, 3);
	}

	private CompoundButton.OnCheckedChangeListener createSwitchListener(final int cpuId)
	{
		return new CompoundButton.OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
				Shell.SU.run("echo \"" + ((isChecked) ? 1 : 0) + "\" > /sys/devices/system/cpu/cpu" + cpuId + "/online\n");
			}
		};
	}
	
	private void updateCpuState(SwitchCompat switchView, int cpuId)
	{
		List<String> resultList = Shell.SU.run("cat /sys/devices/system/cpu/cpu" + cpuId + "/online\n");

		if (resultList.size() > 0)
		{
			// catch if bash send wrong type of string
			try
			{
				boolean currentState = 1 == Integer.valueOf(resultList.get(0));
				switchView.setChecked(currentState);
			}
			catch (Exception e)
			{}
		}
	}
}
