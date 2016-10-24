package com.delos.sumit.arubadel.adapter;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.delos.sumit.arubadel.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by: veli
 * Date: 10/23/16 12:51 PM
 */

public class CreditsAdapter extends BaseAdapter
{
    public JSONArray mReleases;
    public Context mContext;
    public LayoutInflater mInflater;

    public CreditsAdapter(Context context)
    {
        this.mContext = context;
        this.mReleases = new JSONArray();
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount()
    {
        return this.mReleases.length();
    }

    @Override
    public Object getItem(int position)
    {
        try
        {
            return this.mReleases.getJSONObject(position);
        } catch (JSONException e)
        {
            e.printStackTrace();
        }

        return null;
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
            convertView = mInflater.inflate(R.layout.list_credits, parent, false);

        TextView text1 = (TextView) convertView.findViewById(R.id.list_name);
        TextView text2 = (TextView) convertView.findViewById(R.id.list_contributions);
        JSONObject release = (JSONObject) getItem(position);

        try
        {
            if (release.has("login"))
                text1.setText(release.getString("login"));
            if (release.has("contributions"))
                text2.setText("contributions "+ release.getString("contributions"));

        } catch (JSONException e)
        {
            e.printStackTrace();
        }


        return convertView;
    }

    public void update(JSONArray newList)
    {
        this.mReleases = newList;
        this.notifyDataSetChanged();
    }
}