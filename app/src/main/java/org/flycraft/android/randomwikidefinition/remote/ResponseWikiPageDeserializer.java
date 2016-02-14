package org.flycraft.android.randomwikidefinition.remote;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class ResponseWikiPageDeserializer implements JsonDeserializer<WikiPage> {

    @Override
    public WikiPage deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = json.getAsJsonObject();

        WikiPage wikiPage = new WikiPage(
                obj.get("pageid").getAsLong(),
                obj.get("title").getAsString(),
                obj.get("extract").getAsString()
        );

        return wikiPage;
    }

}
