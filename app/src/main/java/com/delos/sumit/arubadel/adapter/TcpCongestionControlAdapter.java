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

public class TcpCongestionControlAdapter extends BaseAdapter
{
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<TcpItem> mList = new ArrayList<>();
    private String tcp_change="sysctl -w net.ipv4.tcp_congestion_control=";
    private List<String> get_tcp=(Shell.SH.run("sysctl net.ipv4.tcp_available_congestion_control"));
    private String store_tcp_value= String.valueOf(get_tcp);
    private String[] parts = store_tcp_value.split("\\s+");
    private String[] tcp_last_part = parts[14].split("\\]");

    private String tcp1;
    private String tcp2;
    private String tcp3;
    private String tcp4;
    private String tcp5;
    private String tcp6;
    private String tcp7;
    private String tcp8;
    private String tcp9;
    private String tcp10;
    private String tcp11;
    private String tcp12;
    private String tcp13;

    public TcpCongestionControlAdapter(Context context)
    {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);

        tcp1=parts[1];
        tcp2=parts[2];
        try
        {
            tcp3=parts[3];
            tcp4=parts[4];
            tcp5=parts[5];
            tcp1=parts[6];
            tcp6=parts[7];
            tcp7=parts[8];
            tcp8=parts[9];
            tcp9=parts[10];
            tcp10=parts[11];
            tcp11=parts[12];
            tcp12=parts[13];
            tcp13=tcp_last_part[0];

        }
        catch (Exception e)
        {

        }


        mList.add(new TcpItem(tcp1,  tcp_change+tcp1));
        mList.add(new TcpItem(tcp2,  tcp_change+tcp2));
        mList.add(new TcpItem(tcp3,  tcp_change+tcp3));
        mList.add(new TcpItem(tcp4,  tcp_change+tcp4));
        mList.add(new TcpItem(tcp5,  tcp_change+tcp5));
        mList.add(new TcpItem(tcp6,  tcp_change+tcp6));
        mList.add(new TcpItem(tcp7,  tcp_change+tcp7));
        mList.add(new TcpItem(tcp8,  tcp_change+tcp8));
        mList.add(new TcpItem(tcp9,  tcp_change+tcp9));
        mList.add(new TcpItem(tcp10,  tcp_change+tcp10));
        mList.add(new TcpItem(tcp11,  tcp_change+tcp11));
        mList.add(new TcpItem(tcp12,  tcp_change + tcp12));
        mList.add(new TcpItem(tcp13,  tcp_change + tcp13));


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

        text.setText(((TcpItem) getItem(position)).cmdName);

        return convertView;
    }

    public class TcpItem
    {
        public String cmdName;
        public String command;

        public TcpItem(String cmdName, String command)
        {
            this.cmdName = cmdName;
            this.command = command;
        }
    }
}
