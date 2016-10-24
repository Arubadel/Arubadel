package com.delos.sumit.arubadel.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.delos.sumit.arubadel.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by: veli
 * Date: 10/23/16 12:51 PM
 */

public class GithubReleasesAdapter extends BaseAdapter
{
    public JSONArray mReleases;
    public Context mContext;
    public LayoutInflater mInflater;

    public GithubReleasesAdapter(Context context)
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
            convertView = mInflater.inflate(R.layout.list_release, parent, false);

        TextView text1 = (TextView) convertView.findViewById(R.id.list_release_text1);
        TextView text2 = (TextView) convertView.findViewById(R.id.list_release_text2);
        JSONObject release = (JSONObject) getItem(position);

        try
        {
            if (release.has("tag_name"))
                text1.setText(release.getString("tag_name"));

            if (release.has("name"))
                text2.setText(release.getString("name"));
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
