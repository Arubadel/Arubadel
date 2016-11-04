package com.delos.sumit.arubadel.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.delos.sumit.arubadel.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.delos.sumit.arubadel.util.CPUTools;

import eu.chainfire.libsuperuser.Shell;

/**
 * Created by: sumit
 * Date: 10/29/16 4:28 PM
 */

public class GpuAdapter extends BaseAdapter
{
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<GpuItem> mList = new ArrayList<>();
    private String gpu_scaling_path="/sys/class/kgsl/kgsl-3d0/max_gpuclk";
    private List<String> get_gpu_scaling_freq=(Shell.SU.run("cat /sys/class/kgsl/kgsl-3d0/gpu_available_frequencies"));
    private String store_gov= String.valueOf(get_gpu_scaling_freq);
    private String[] governor_parts = store_gov.split("\\[");
    private String[] parts = governor_parts[1].split("\\s+"); // escape .
    private String gpufreq1;
    private String gpufreq2;
    private String gpufreq3;
    private String gpufreq4;
    public GpuAdapter(Context context)
    {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        gpufreq1=parts[0];
        gpufreq2=parts[1];
        gpufreq3=parts[2];
        gpufreq4=parts[3];


        mList.add(new GpuItem(gpufreq1, "echo " + gpufreq1 + " > "  + gpu_scaling_path));
        mList.add(new GpuItem(gpufreq2, "echo " + gpufreq2 + " > "  + gpu_scaling_path));
        mList.add(new GpuItem(gpufreq3, "echo " + gpufreq3 + " > "  + gpu_scaling_path));
        mList.add(new GpuItem(gpufreq4, "echo " + gpufreq4 + " > "  + gpu_scaling_path));

    }

    @Override
    public int getCount()
    {
        return mList.size();
    }

    @Override
    public Object getItem(int position)
    {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
            convertView = mInflater.inflate(R.layout.list_governor, parent, false);

        TextView text = (TextView) convertView.findViewById(R.id.list_governor_text);

        text.setText(((GpuItem) getItem(position)).cmdName);

        return convertView;
    }

    public class GpuItem
    {
        public String cmdName;
        public String command;

        public GpuItem(String cmdName, String command)
        {
            this.cmdName = cmdName;
            this.command = command;
        }
    }
}
