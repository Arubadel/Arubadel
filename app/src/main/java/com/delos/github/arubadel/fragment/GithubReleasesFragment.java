package com.delos.github.arubadel.fragment;

import com.delos.github.arubadel.adapter.AbstractGithubAdapter;
import com.delos.github.arubadel.adapter.GithubReleasesAdapter;

/**
 * Created by: veli
 * Date: 10/25/16 10:03 PM
 */

public class GithubReleasesFragment extends AbstractGithubFragment {
    public String mTargetUrl;

    public GithubReleasesFragment setTargetURL(String targetUrl) {
        this.mTargetUrl = targetUrl;
        return this;
    }

    @Override
    public String onTargetURL() {
        return this.mTargetUrl;
    }

    @Override
    public AbstractGithubAdapter onAdapter() {
        return new GithubReleasesAdapter(this.getActivity());
    }
}
