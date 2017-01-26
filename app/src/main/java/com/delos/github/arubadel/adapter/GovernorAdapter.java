package com.delos.github.arubadel.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.delos.github.arubadel.R;
import com.delos.github.arubadel.util.Tools;

import java.util.ArrayList;

/**
 * Created by: sumit
 * Date: 10/29/16 4:28 PM
 */

public class GovernorAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<GovernorItem> mList = new ArrayList<>();
    private String Scaling_gov_path = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_governor";
    private String store_gov = get_gov();
    private String[] parts = store_gov.split("\\s+"); // escape .

    public GovernorAdapter(Context context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        for (String GovernorList : parts) {
            String SelectedGovernor = GovernorList;

            mList.add(new GovernorItem(SelectedGovernor, "echo " + SelectedGovernor + " > " + Scaling_gov_path));
        }

    }

    private String get_gov() {
        return Tools.shell("cat sys/devices/system/cpu/cpu0/cpufreq/scaling_available_governors", false);
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

        text.setText(((GovernorItem) getItem(position)).cmdName);

        return convertView;
    }

    public class GovernorItem {
        public String cmdName;
        public String command;

        public GovernorItem(String cmdName, String command) {
            this.cmdName = cmdName;
            this.command = command;
        }
    }
}
