package Fragment;

/**
 * Created by sumit on 7/7/16.
 */

import android.os.*;
import android.support.annotation.*;
import android.support.v4.app.*;
import android.view.*;
import android.widget.*;
import com.delos.sumit.arubadel.*;
import eu.chainfire.libsuperuser.*;
import java.util.*;

public class MainFragment extends Fragment
{
	private Switch mCPU1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
        View rootview = inflater.inflate(R.layout.fragment_main, container, false);
        mCPU1 = (Switch) rootview.findViewById(R.id.cpu1);
  
        //attach a listener to check for changes in state
        mCPU1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() 
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
		
		List<String> resultList = Shell.SU.run("cat /sys/devices/system/cpu/cpu1/online\n");
		
		Toast.makeText(getActivity(), "ResultIndex = " + resultList.size() + "; row0 = " + ((resultList.size() > 0) ? resultList.get(0) : "List is empty"), Toast.LENGTH_LONG).show();
		
		if (resultList.size() > 1)
		{
			boolean currentState = "0".equals(resultList.get(0));
			
			this.mCPU1.setChecked(currentState);
		}
	}
}
