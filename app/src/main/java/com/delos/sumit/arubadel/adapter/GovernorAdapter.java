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

public class GovernorAdapter extends BaseAdapter
{
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<GovernorItem> mList = new ArrayList<>();
    private String Scaling_gov_path="/sys/devices/system/cpu/cpu0/cpufreq/scaling_governor";
    private List<String> get_gov=(Shell.SU.run("cat sys/devices/system/cpu/cpu0/cpufreq/scaling_available_governors"));
    private String store_gov= String.valueOf(get_gov);
    private String[] parts = store_gov.split("\\s+"); // escape .
    private String gov1;
    private String gov2;
    private String gov3;
    private String gov4;
    private String gov5;
    private String gov6;
    private String gov7;
    private String gov8;
    private String gov9;
    private String gov10;
    private String gov11;
    private String gov12;
    private String gov13;
    private String gov14;
    private String gov15;
    private String gov16;
    private String gov17;
    public GovernorAdapter(Context context)
    {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        gov1=parts[0];
        gov2=parts[1];
        gov3=parts[2];
        gov4=parts[3];
        gov5=parts[4];
        try
        {
            gov6 = parts[5];
            gov7 = parts[6];
            gov8 = parts[7];
            gov9 = parts[8];
            gov10 = parts[9];
            gov11 = parts[10];
            gov12 = parts[11];
            gov13 = parts[12];
            gov14 = parts[13];
            gov15 = parts[14];
            gov16 = parts[15];
            gov17 = parts[16];
        }
        catch (Exception e)
        {

        }

        mList.add(new GovernorItem(gov1, "echo " + gov1 + " > "  + Scaling_gov_path));
        mList.add(new GovernorItem(gov2, "echo " + gov2 + " > "  + Scaling_gov_path));
        mList.add(new GovernorItem(gov3, "echo " + gov3 + " > "  + Scaling_gov_path));
        mList.add(new GovernorItem(gov4, "echo " + gov4 + " > "  + Scaling_gov_path));
        mList.add(new GovernorItem(gov5, "echo " + gov5 + " > "  + Scaling_gov_path));
        mList.add(new GovernorItem(gov6, "echo " + gov6 + " > "  + Scaling_gov_path));
        mList.add(new GovernorItem(gov7, "echo " + gov7 + " > "  + Scaling_gov_path));
        mList.add(new GovernorItem(gov8, "echo " + gov8 + " > "  + Scaling_gov_path));
        mList.add(new GovernorItem(gov9, "echo " + gov9 + " > "  + Scaling_gov_path));
        mList.add(new GovernorItem(gov10, "echo " + gov10 + " > "  + Scaling_gov_path));
        mList.add(new GovernorItem(gov11, "echo " + gov11 + " > "  + Scaling_gov_path));
        mList.add(new GovernorItem(gov12, "echo " + gov12 + " > "  + Scaling_gov_path));
        mList.add(new GovernorItem(gov13, "echo " + gov13 + " > "  + Scaling_gov_path));
        mList.add(new GovernorItem(gov14, "echo " + gov14 + " > "  + Scaling_gov_path));
        mList.add(new GovernorItem(gov15, "echo " + gov15 + " > "  + Scaling_gov_path));
        mList.add(new GovernorItem(gov16, "echo " + gov16 + " > "  + Scaling_gov_path));
        mList.add(new GovernorItem(gov17, "echo " + gov17 + " > "  + Scaling_gov_path));

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
