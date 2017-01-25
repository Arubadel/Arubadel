package com.delos.github.arubadel.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.delos.github.arubadel.R;
import com.delos.github.arubadel.util.ShellExecuter;

import java.util.ArrayList;

/**
 * Created by: sumit
 * Date: 10/29/16 4:28 PM
 */

public class GpuAdapter extends BaseAdapter {
    private final String GPU_SCALING_PATH = "/sys/class/kgsl/kgsl-3d0/max_gpuclk";

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<GpuItem> mList = new ArrayList<>();

    public GpuAdapter(Context context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        String[] availableSpeeds = ShellExecuter.runAsRoot("cat /sys/class/kgsl/kgsl-3d0/gpu_available_frequencies").split("\\s+");

        for (String speed : availableSpeeds) {
            String mhzSpeed = speed;

            try {
                mhzSpeed = String.valueOf(Integer.valueOf(speed) / 1000000);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            mList.add(new GpuItem(mhzSpeed + "MHz", "echo " + speed + " > " + GPU_SCALING_PATH));
        }
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = mInflater.inflate(R.layout.list_governor, parent, false);

        TextView text = (TextView) convertView.findViewById(R.id.list_governor_text);

        text.setText(((GpuItem) getItem(position)).cmdName);

        return convertView;
    }

    public class GpuItem {
        public String cmdName;
        public String command;

        public GpuItem(String cmdName, String command) {
            this.cmdName = cmdName;
            this.command = command;
        }
    }
}
