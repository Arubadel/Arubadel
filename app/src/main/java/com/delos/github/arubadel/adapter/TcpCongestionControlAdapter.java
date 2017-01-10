package com.delos.github.arubadel.adapter;

import android.content.Context;
import android.text.TextUtils;
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

public class TcpCongestionControlAdapter extends BaseAdapter
{
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<TcpItem> mList = new ArrayList<>();
    private String tcp_change="sysctl -w net.ipv4.tcp_congestion_control=";
    private String get_tcp(){
        return ShellExecuter.runAsRoot("sysctl net.ipv4.tcp_available_congestion_control");
    }
    private String store_tcp_value= get_tcp();
    private String[] parts = store_tcp_value.split("net.ipv4.tcp_available_congestion_control|=|\\s+");

    private String tcp1,tcp2,tcp3,tcp4,tcp5,tcp6,tcp7,tcp8,tcp9,tcp10,tcp11,tcp12,tcp13;

    public TcpCongestionControlAdapter(Context context)
    {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);

        for (String GetTcp : parts)
        {
            String GetTcpList = GetTcp;
            if (TextUtils.isEmpty(GetTcpList)) {
                GetTcpList=null;
            }

            if(GetTcpList==null){}else
            mList.add(new TcpItem(GetTcpList , ""));
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
