package org.flycraft.android.randomwikidefinition;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.flycraft.android.randomwikidefinition.remote.ResponseWikiPageDeserializer;
import org.flycraft.android.randomwikidefinition.remote.WikiPage;
import org.junit.Test;

import static junit.framework.Assert.*;

public class ResponseWikiPageDeserializerTest {

    private long jsonPageId = 123456789;
    private String jsonPageTitle = "some_title";
    private String jsonPageExtract = "some_extract";
    private String jsonPage =
            "{\n" +
            "   \"pageid\": " + jsonPageId + ",\n" +
            "   \"ns\": 0,\n" +
            "   \"title\": \"" + jsonPageTitle + "\",\n" +
            "   \"extract\": \"" + jsonPageExtract + "\"\n" +
            "}";

    private Gson gson = new GsonBuilder()
            .registerTypeAdapter(WikiPage.class, new ResponseWikiPageDeserializer())
            .create();

    @Test
    public void checkJsonAndFinalObjectFieldsEquals() {
        WikiPage wikiPage = gson.fromJson(jsonPage, WikiPage.class);

        assertEquals(jsonPageId, wikiPage.getId());
        assertEquals(jsonPageTitle, wikiPage.getTitle());
        assertEquals(jsonPageExtract, wikiPage.getExtract());
    }

}
