package com.delos.sumit.arubadel.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;

import com.delos.sumit.arubadel.R;
import com.delos.sumit.arubadel.adapter.AbstractGithubAdapter;
import com.delos.sumit.arubadel.adapter.CreditsAdapter;
import com.delos.sumit.arubadel.util.Config;
import com.github.kevinsawicki.http.HttpRequest;

import org.json.JSONArray;

/**
 * Created by: Sumit
 * Date: 19.10.2016 12:43 AM
 */

public class CreditsFragment extends AbstractGithubFragment
{
    @Override
    public String onTargetURL()
    {
        return Config.URL_CONTRIBUTORS;
    }

    @Override
    public AbstractGithubAdapter onAdapter()
    {
        return new CreditsAdapter(getActivity());
    }
}
