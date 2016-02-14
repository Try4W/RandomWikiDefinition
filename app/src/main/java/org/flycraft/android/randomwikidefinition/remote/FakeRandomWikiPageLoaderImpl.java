package org.flycraft.android.randomwikidefinition.remote;

public class FakeRandomWikiPageLoaderImpl implements RandomWikiPageLoader {

    private RandomWikiPageLoaderListener listener;

    @Override
    public void startLoading() {
        listener.onResult(new WikiPage(0, "Some title", "Some extract"));
    }

    @Override
    public void setListener(RandomWikiPageLoaderListener listener) {
        this.listener = listener;
    }
}
