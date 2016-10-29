package com.delos.sumit.arubadel.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.delos.sumit.arubadel.R;

import java.util.ArrayList;
import com.delos.sumit.arubadel.util.CPUTools;

/**
 * Created by: sumit
 * Date: 10/29/16 4:28 PM
 */

public class GovernorAdapter extends BaseAdapter
{
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<GovernorItem> mList = new ArrayList<>();
    String scaling_gov_path="/sys/devices/system/cpu/cpu0/cpufreq/scaling_governor";
    public GovernorAdapter(Context context)
    {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);

        mList.add(new GovernorItem(context.getString(R.string.lazy), "echo 'lazy' > " + scaling_gov_path));
        mList.add(new GovernorItem(context.getString(R.string.smoove), "echo 'smoove' > "+ scaling_gov_path));
        mList.add(new GovernorItem(context.getString(R.string.lagfree), "echo 'lagfree' >  " + scaling_gov_path));
        mList.add(new GovernorItem(context.getString(R.string.intellidemand), "echo 'intellidemand' > " + scaling_gov_path));
        mList.add(new GovernorItem(context.getString(R.string.ondemandX), "echo 'ondemandX' >  " + scaling_gov_path));
        mList.add(new GovernorItem(context.getString(R.string.hyper), "echo 'hyper' > " + scaling_gov_path));
        mList.add(new GovernorItem(context.getString(R.string.smartassV2), "echo 'smartassV2' > " + scaling_gov_path));
        mList.add(new GovernorItem(context.getString(R.string.lulzactive), "echo 'lulzactive' > " + scaling_gov_path));
        mList.add(new GovernorItem(context.getString(R.string.interactiveX), "echo 'interactiveX' > " + scaling_gov_path));
        mList.add(new GovernorItem(context.getString(R.string.adaptive), "echo 'adaptive' > " + scaling_gov_path));
        mList.add(new GovernorItem(context.getString(R.string.conservative), "echo 'conservative' > " + scaling_gov_path));
        mList.add(new GovernorItem(context.getString(R.string.userspace), "echo 'userspace' > " + scaling_gov_path));
        mList.add(new GovernorItem(context.getString(R.string.wheatley), "echo 'wheatley' > " + scaling_gov_path));
        mList.add(new GovernorItem(context.getString(R.string.lionheart), "echo 'lionheart' > " + scaling_gov_path));
        mList.add(new GovernorItem(context.getString(R.string.ondemand), "echo 'ondemand' > " + scaling_gov_path));
        mList.add(new GovernorItem(context.getString(R.string.performance), "echo 'performance' > " + scaling_gov_path));

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

        text.setText(((GovernorItem) getItem(position)).cmdName);

        return convertView;
    }

    public class GovernorItem
    {
        public String cmdName;
        public String command;

        public GovernorItem(String cmdName, String command)
        {
            this.cmdName = cmdName;
            this.command = command;
        }
    }
}
