package org.flycraft.android.randomwikidefinition;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.flycraft.android.randomwikidefinition.fragments.LoadingFragment;
import org.flycraft.android.randomwikidefinition.fragments.NoConnectionFragment;
import org.flycraft.android.randomwikidefinition.fragments.WikiDefinitionFragment;
import org.flycraft.android.randomwikidefinition.remote.RandomWikiPageLoader;
import org.flycraft.android.randomwikidefinition.remote.RandomWikiPageLoaderImpl;
import org.flycraft.android.randomwikidefinition.remote.RandomWikiPageLoaderListener;
import org.flycraft.android.randomwikidefinition.remote.WikiPage;

public class MainActivity extends AppCompatActivity {

    private RandomWikiDefinition application;

    private CoordinatorLayout coordinatorLayout;
    private AppBarLayout appBarLayout;
    private Toolbar toolbar;
    private NestedScrollView contentScrollView;
    private FloatingActionButton fab;
    private ActionBar actionBar;

    private boolean fabShouldBeShown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        application = (RandomWikiDefinition) getApplication();

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);
        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        contentScrollView = (NestedScrollView) findViewById(R.id.content);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        actionBar = getSupportActionBar();
        if(actionBar == null) {
            throw new NullPointerException("getSupportActionBar() returns null");
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appBarLayout.setExpanded(true, false); // to avoid glitches
                nextPage();
            }
        });
        nextPage();
    }

    public void nextPage() {
        showLoadingFragment();
        setFabShow(false);
        RandomWikiPageLoader loader = application.getRandomWikiPageLoader();
        loader.setListener(new RandomWikiPageLoaderListener() {
            @Override
            public void onResult(final WikiPage page) {
                setFabShow(true);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showDefinitionFragment(page);
                    }
                });
            }

            @Override
            public void onException(Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showNoConnectionFragment();
                    }
                });
            }
        });
        loader.startLoading();
    }

    public void showDefinitionFragment(WikiPage page) {
        WikiDefinitionFragment fragment = new WikiDefinitionFragment();
        Bundle arguments = new Bundle();
        arguments.putParcelable(WikiDefinitionFragment.ARG_WIKI_PAGE, page);
        fragment.setArguments(arguments);

        putFragment(getString(R.string.wiki_definition_fragment_title), fragment);
    }

    public void showLoadingFragment() {
        putFragment(getString(R.string.fragment_loading_title), new LoadingFragment());
    }

    public void showNoConnectionFragment() {
        putFragment(getString(R.string.fragment_no_connection_title), new NoConnectionFragment());
    }

    public void setFabShow(boolean show) { // small hack to avoid interrupting animation
        FloatingActionButton.OnVisibilityChangedListener listener = new FloatingActionButton.OnVisibilityChangedListener() {
            @Override
            public void onShown(FloatingActionButton fab) {
                super.onShown(fab);
                if(!fabShouldBeShown){
                    fab.hide();
                }
            }

            @Override
            public void onHidden(FloatingActionButton fab) {
                super.onHidden(fab);
                if(fabShouldBeShown){
                    fab.show();
                }
            }
        };

        fabShouldBeShown = show;
        if(show) {
            fab.show(listener);
        } else {
            fab.hide(listener);
        }
    }

    public AppBarLayout getAppBarLayout() {
        return appBarLayout;
    }

    private void putFragment(String title, Fragment fragment) {
        actionBar.setTitle(title);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, R.anim.empty_anim)
                .replace(R.id.content, fragment)
                .commit();
    }

}
