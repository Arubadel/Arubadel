package com.delos.github.arubadel.fragment;

import com.delos.github.arubadel.adapter.AbstractGithubAdapter;
import com.delos.github.arubadel.adapter.CreditsAdapter;
import com.delos.github.arubadel.util.Config;

/**
 * Created by: Sumit
 * Date: 19.10.2016 12:43 AM
 */

public class CreditsFragment extends AbstractGithubFragment {
    @Override
    public String onTargetURL() {
        return Config.URL_CONTRIBUTORS;
    }

    @Override
    public AbstractGithubAdapter onAdapter() {
        return new CreditsAdapter(getActivity());
    }

}
