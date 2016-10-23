package com.delos.sumit.arubadel.fragment.kernelupdater;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;

import com.delos.sumit.arubadel.adapter.GithubReleasesAdapter;
import com.github.kevinsawicki.http.HttpRequest;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by: Veli
 * Date: 19.10.2016 12:43 AM
 */

public class KernelReleasesStableFragment extends ListFragment
{
    private GithubReleasesAdapter mAdapter;
    private JSONArray mAwaitedList;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        this.mAdapter = new GithubReleasesAdapter(getActivity());

        setListAdapter(mAdapter);
        updateCache();
    }

    public void updateCache()
    {
        new Thread()
        {
            @Override
            public void run()
            {
                super.run();

                try
                {
                    final StringBuilder result = new StringBuilder();

                    HttpRequest httpRequest = HttpRequest.get("https://api.github.com/repos/genonbeta/CoolSocket-Client/releases");
                    httpRequest.receive(result);

                    mAwaitedList = new JSONArray(result.toString());

                    update();
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }.start();
    }


    public void update()
    {
        if (isDetached() || getActivity() == null)
            return;

        if (!isDetached() && getActivity() != null)
            getActivity().runOnUiThread(
                    new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            if (mAwaitedList != null)
                                mAdapter.update(mAwaitedList);
                        }
                    }
            );
    }
}
