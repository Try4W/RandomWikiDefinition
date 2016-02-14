package org.flycraft.android.randomwikidefinition.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.flycraft.android.randomwikidefinition.MainActivity;
import org.flycraft.android.randomwikidefinition.R;
import org.flycraft.android.randomwikidefinition.remote.WikiPage;
import org.flycraft.android.randomwikidefinition.utils.HtmlUtils;

public class WikiDefinitionFragment extends Fragment {

    public static final String TAG = "WikiDefinitionFragment";

    public static final String ARG_WIKI_PAGE = "wikiPage";

    private MainActivity mainActivity;
    private View pageTitleView;
    private TextView titleTextView;
    private TextView extractTextView;
    private Button readMoreButton;

    private WikiPage wikiPage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return LayoutInflater.from(getContext())
                .inflate(R.layout.fragment_definition, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainActivity = (MainActivity) getActivity();

        extractTextView = (TextView) view.findViewById(R.id.extract);
        readMoreButton = (Button) view.findViewById(R.id.read_more_button);

        readMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPageInBrowser();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        pageTitleView = appendPageTitleToToolbar();
        titleTextView = (TextView) pageTitleView.findViewById(R.id.page_title);
        setup();
    }

    @Override
    public void onStop() {
        super.onStop();
        removePageTitleFromToolbar();
    }

    public void setup() {
        wikiPage = getArguments().getParcelable(ARG_WIKI_PAGE);
        if(wikiPage == null) {
            throw new NullPointerException("No wikiPage argument");
        }

        titleTextView.setText(wikiPage.getTitle());
        String pageExtract = HtmlUtils.trim(wikiPage.getExtract());
        extractTextView.setText(Html.fromHtml(pageExtract)); // Apply http format
    }

    public void openPageInBrowser() {
        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://ru.m.wikipedia.org/?curid=" + wikiPage.getId()));
        startActivity(intent);
    }

    public View appendPageTitleToToolbar() {
        AppBarLayout appBarLayout = mainActivity.getAppBarLayout();
        View toolbarPart = LayoutInflater
                .from(getContext())
                .inflate(R.layout.view_definition_toolbar_part, appBarLayout, false);

        appBarLayout.addView(toolbarPart);
        return toolbarPart;
    }

    public void removePageTitleFromToolbar() {
        AppBarLayout appBarLayout = mainActivity.getAppBarLayout();
        appBarLayout.removeView(pageTitleView);
    }

}
