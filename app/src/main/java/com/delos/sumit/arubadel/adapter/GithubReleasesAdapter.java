package com.delos.sumit.arubadel.adapter;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.delos.sumit.arubadel.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by: veli
 * Date: 10/25/16 10:13 PM
 */

public class GithubReleasesAdapter extends GithubAdapterIDEA
{
    public GithubReleasesAdapter(Context context)
    {
        super(context);
    }

    @Override
    protected View onView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
            convertView = mInflater.inflate(R.layout.list_rom, parent, false);

        TextView text1 = (TextView) convertView.findViewById(R.id.list_rom_text1);
        TextView text2 = (TextView) convertView.findViewById(R.id.list_rom_text2);
        TextView text3 = (TextView) convertView.findViewById(R.id.list_rom_text3);
        Button download = (Button) convertView.findViewById(R.id.Download);
        download.setVisibility(View.GONE);

        final JSONObject release = (JSONObject) getItem(position);

        try
        {
            if (release.has("tag_name"))
            {
                text1.setText(release.getString("tag_name"));
            }

            if (release.has("name"))
            {
                text2.setText(release.getString("name"));
            }

            if (release.has("body"))
            {
                text3.setText(release.getString("body"));

            }
            if (release.has("assets"))
            {
                download.setVisibility(View.VISIBLE);

                JSONArray assets = release.getJSONArray("assets");

                if(assets.length() > 0)
                {
                    final JSONObject firstAsset = assets.getJSONObject(0);

                    if (firstAsset.has("browser_download_url"))
                    {
                        download.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                DownloadManager downloadManager = (DownloadManager) mContext.getSystemService(getContext().DOWNLOAD_SERVICE);
                                Uri uri = null;

                                try
                                {
                                    uri = Uri.parse(firstAsset.getString("browser_download_url"));
                                } catch (JSONException e)
                                {
                                    e.printStackTrace();
                                }
                                DownloadManager.Request request = new DownloadManager.Request(uri);
                                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, String.valueOf(uri));
                                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                                Long refrence = downloadManager.enqueue(request);
                            }
                        });
                    }

                }
            }

        } catch (JSONException e)
        {
            e.printStackTrace();
        }

        return convertView;
    }
}
