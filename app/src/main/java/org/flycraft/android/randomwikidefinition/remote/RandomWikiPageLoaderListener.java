package org.flycraft.android.randomwikidefinition.remote;

public interface RandomWikiPageLoaderListener {

    void onResult(WikiPage page);
    void onException(Exception e);

}
