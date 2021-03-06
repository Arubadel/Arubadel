package com.delos.github.arubadel.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.delos.github.arubadel.R;
import com.thefinestartist.finestwebview.FinestWebView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by: veli
 * Date: 10/23/16 12:51 PM
 */

public class CreditsAdapter extends GithubAdapterIDEA {
    private JSONObject release;
    private String Url;
    private String contributors[] = new String[5];
    private TextView text1, text2;

    public CreditsAdapter(Context context) {
        super(context);
    }

    @Override
    protected View onView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = mInflater.inflate(R.layout.list_credits, parent, false);

        text1 = (TextView) convertView.findViewById(R.id.list_credits_name);
        text2 = (TextView) convertView.findViewById(R.id.list_credits_contributions);
        final Button mButton = (Button) convertView.findViewById(R.id.button_credits_open_in_github);
        release = (JSONObject) getItem(position);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mButton.setVisibility((mButton.getVisibility() == View.GONE) ? View.VISIBLE : View.GONE);
            }
        });

        try {
            if (release.has("login"))
                text1.setText(release.getString("login"));
            contributors[position] = release.getString("login"); //Using array for storing the names
            mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Url = "https://github.com/" + contributors[position];
                    new FinestWebView.Builder(getContext()).show(Url);
                }
            });

            if (release.has("contributions"))
                text2.setText(getContext().getString(R.string.contribution_counter_info, release.getInt("contributions")));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return convertView;
    }

}
