package com.delos.sumit.arubadel.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;

import com.delos.sumit.arubadel.R;
import com.delos.sumit.arubadel.adapter.AbstractGithubAdapter;
import com.delos.sumit.arubadel.util.Config;
import com.github.kevinsawicki.http.HttpRequest;

import org.json.JSONArray;

/**
 * Created by: veli
 * Date: 10/25/16 5:45 PM
 */

abstract public class AbstractGithubFragment extends ListFragment
{
    public abstract String onTargetURL();
    public abstract AbstractGithubAdapter onAdapter();

    private AbstractGithubAdapter mAdapter;
    private String mTargetURL;
    private JSONArray mAwaitedList;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        this.mAdapter = this.onAdapter();
        this.mTargetURL = this.onTargetURL();

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

                    HttpRequest httpRequest = HttpRequest.get(mTargetURL);
                    httpRequest.receive(result);

                    mAwaitedList = new JSONArray(result.toString());

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

                            if (mAwaitedList != null)
                                mAdapter.update(mAwaitedList);
                        }
                    }
            );
    }
}
