package org.flycraft.android.randomwikidefinition.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.flycraft.android.randomwikidefinition.R;

public class LoadingFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return LayoutInflater
                .from(getContext())
                .inflate(R.layout.fragment_loading, container, false);
    }

}
