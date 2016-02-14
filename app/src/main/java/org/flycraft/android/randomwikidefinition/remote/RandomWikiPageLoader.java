package org.flycraft.android.randomwikidefinition.remote;

public interface RandomWikiPageLoader {

    void startLoading();
    void setListener(RandomWikiPageLoaderListener listener);

}
