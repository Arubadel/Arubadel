package Fragment;

/**
 * Created by sumit on 7/7/16.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.delos.sumit.arubadel.R;

import eu.chainfire.libsuperuser.Shell;

public class MainFragment extends Fragment
{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_main, container, false);
        Switch cpu1 = (Switch) rootview.findViewById(R.id.cpu1);
        //set the switch to ON
        cpu1.setChecked(true);
        //attach a listener to check for changes in state
        cpu1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

@Override
            public void onCheckedChanged(CompoundButton buttonView,
                                            boolean isChecked)
                {

                if(isChecked)
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
}