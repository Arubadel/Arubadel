package com.delos.sumit.arubadel.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.delos.sumit.arubadel.R;

import java.util.ArrayList;

/**
 * Created by: veli
 * Date: 10/23/16 4:28 PM
 */

public class PowerManagementAdapter extends BaseAdapter
{
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<PowerItem> mList = new ArrayList<>();

    public PowerManagementAdapter(Context context)
    {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);

        mList.add(new PowerItem(context.getString(R.string.power_item_soft_reboot), "killall system_server"));
        mList.add(new PowerItem(context.getString(R.string.power_item_reboot), "reboot"));
        mList.add(new PowerItem(context.getString(R.string.power_item_reboot_recovery), "reboot recovery"));
        mList.add(new PowerItem(context.getString(R.string.power_item_poweroff), "poweroff"));
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
            convertView = mInflater.inflate(R.layout.list_power_options, parent, false);

        TextView text = (TextView) convertView.findViewById(R.id.list_power_text);

        text.setText(((PowerItem) getItem(position)).cmdName);

        return convertView;
    }

    public class PowerItem
    {
        public String cmdName;
        public String command;

        public PowerItem(String cmdName, String command)
        {
            this.cmdName = cmdName;
            this.command = command;
        }
    }
}
