package org.flycraft.android.randomwikidefinition.remote;

import java.util.Map;

public class WikiQuery {

    private Map<String, WikiPage> pages;

    public WikiPage getOnePage() {
        return pages.values().iterator().next();
    }

}
