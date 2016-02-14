package org.flycraft.android.randomwikidefinition;

import android.app.Application;

import org.flycraft.android.randomwikidefinition.remote.FakeRandomWikiPageLoaderImpl;
import org.flycraft.android.randomwikidefinition.remote.RandomWikiPageLoader;
import org.flycraft.android.randomwikidefinition.remote.RandomWikiPageLoaderImpl;

public class RandomWikiDefinition extends Application {

    public static boolean USE_FAKE_LOADER = false;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public RandomWikiPageLoader getRandomWikiPageLoader() {
        if(USE_FAKE_LOADER) {
            return new FakeRandomWikiPageLoaderImpl();
        } else {
            return new RandomWikiPageLoaderImpl();
        }
    }

}
