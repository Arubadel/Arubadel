package com.delos.sumit.arubadel.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;

import com.delos.sumit.arubadel.R;
import com.delos.sumit.arubadel.adapter.GithubReleasesAdapter;
import com.delos.sumit.arubadel.util.Config;
import com.delos.sumit.arubadel.util.LongLiveResource;
import com.github.kevinsawicki.http.HttpRequest;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by sumit on 25/10/16.
 */

public class RecoveryReleaseBetaFragment extends ListFragment
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
        setEmptyText(getString(R.string.connecting_to_github));

        new Thread()
        {
            @Override
            public void run()
            {
                super.run();

                try
                {
                    final StringBuilder result = new StringBuilder();

                    HttpRequest httpRequest = HttpRequest.get(Config.URL_RECOVERY_BETA_RELEASE);
                    httpRequest.receive(result);

                    LongLiveResource.stableReleases = new JSONArray(result.toString());

                    update();
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }.start();
    }


    public void update()
    {
        if (!isDetached() && getActivity() != null)
            getActivity().runOnUiThread(
                    new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            setEmptyText(getString(R.string.no_files_found));

                            if (LongLiveResource.stableReleases != null)
                                mAdapter.update(LongLiveResource.stableReleases );
                        }
                    }
            );
    }
}
