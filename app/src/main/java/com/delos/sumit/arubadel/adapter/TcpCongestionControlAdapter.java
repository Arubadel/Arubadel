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
    String tcp_change="sysctl -w net.ipv4.tcp_congestion_control= ";
    List<String> get_tcp=(Shell.SU.run("sysctl net.ipv4.tcp_available_congestion_control"));
    String store_tcp_value= String.valueOf(get_tcp);
    String[] parts = store_tcp_value.split("\\s+");

    public TcpCongestionControlAdapter(Context context)
    {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        String tcp1 = parts[3];
        String tcp2 = parts[4];
        String tcp3 = parts[5];
        String tcp4 = parts[6];
        String tcp5 = parts[7];
        String tcp6 = parts[8];
        String tcp7 = parts[9];
        String tcp8 = parts[10];
        String tcp9 = parts[11];
        String tcp10 = parts[12];
        String tcp11 = parts[13];
        String tcp12 = parts[14];

        mList.add(new TcpItem(tcp1,  tcp_change + tcp1));
        mList.add(new TcpItem(tcp2,  tcp_change + tcp2));
        mList.add(new TcpItem(tcp3,  tcp_change + tcp3));
        mList.add(new TcpItem(tcp4,  tcp_change + tcp4));
        mList.add(new TcpItem(tcp5,  tcp_change + tcp5));
        mList.add(new TcpItem(tcp6,  tcp_change + tcp6));
        mList.add(new TcpItem(tcp7,  tcp_change + tcp7));
        mList.add(new TcpItem(tcp8,  tcp_change + tcp8));
        mList.add(new TcpItem(tcp9,  tcp_change + tcp9));
        mList.add(new TcpItem(tcp10,  tcp_change + tcp10));
        mList.add(new TcpItem(tcp11,  tcp_change + tcp11));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if(Objects.equals(tcp12, "illinois]"))
                mList.add(new TcpItem("illinois",  tcp_change + "illinois"));
            else
            {
                mList.add(new TcpItem(tcp12,  tcp_change + tcp12));
            }
        }
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
