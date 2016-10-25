package com.delos.sumit.arubadel.adapter;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.delos.sumit.arubadel.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by: veli
 * Date: 10/25/16 5:45 PM
 */

abstract public class AbstractGithubAdapter extends BaseAdapter
{
    public Context mContext;
    public LayoutInflater mInflater;

    abstract protected JSONArray onIndex();
    abstract protected void onUpdate(JSONArray list);
    abstract protected View onView(int position, View convertView, ViewGroup parent);

    public AbstractGithubAdapter(Context context)
    {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
    }

    public Context getContext() { return this.mContext; }
    public LayoutInflater getInflater() { return this.mInflater; }

    @Override
    public int getCount()
    {
        return this.onIndex().length();
    }

    @Override
    public Object getItem(int position)
    {
        try
        {
            return this.onIndex().getJSONObject(position);
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

    public void update(JSONArray newList)
    {
        this.onUpdate(newList);
        this.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        return this.onView(position, convertView, parent);
    }
}
