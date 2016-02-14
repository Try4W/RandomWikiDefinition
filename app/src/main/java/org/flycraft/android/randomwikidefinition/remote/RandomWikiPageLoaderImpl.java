package org.flycraft.android.randomwikidefinition.remote;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class RandomWikiPageLoaderImpl extends Thread implements RandomWikiPageLoader {

    private static final String TAG = "RandomWikiPageLoader";
    private static final String RANDOM_PAGE_HTTPS_REQUEST =
            "https://ru.wikipedia.org/w/api.php" +
                    "?action=query" +
                    "&generator=random" +
                    "&grnnamespace=0" +
                    "&prop=extracts" +
                    "&exchars=500" +
                    "&format=json";

    private RandomWikiPageLoaderListener listener;
    private Gson gson = new GsonBuilder()
            .registerTypeAdapter(WikiPage.class, new ResponseWikiPageDeserializer())
            .create();

    @Override
    public void run() {
        try {
            String response = makeRequest();
            WikiResponse wikiResponse = gson.fromJson(response, WikiResponse.class);
            WikiPage page = wikiResponse.getQuery().getOnePage();
            listener.onResult(page);
        } catch (IOException e) {
            listener.onException(e);
            e.printStackTrace();
        }
    }

    private String makeRequest() throws IOException {
        try {
            URL url = new URL(RANDOM_PAGE_HTTPS_REQUEST);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            int responseCode = connection.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                return inputStreamToString(inputStream);
            } else {
                InputStream errorStream = connection.getErrorStream();
                String error = inputStreamToString(errorStream);

                Log.e(TAG, "Error response: " + error);
                throw new RuntimeException("Response code is not OK: " + responseCode);
            }

        } catch (MalformedURLException e) {
            Log.d(TAG, "Wrong URL?");
            e.printStackTrace();
        }
        throw new RuntimeException("Error while makeRequest");
    }

    private String inputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder totalResponse = new StringBuilder();
        String tmpLine;
        while ((tmpLine = reader.readLine()) != null) {
            totalResponse.append(tmpLine);
        }
        return totalResponse.toString();
    }

    @Override
    public void startLoading() {
        start();
    }

    @Override
    public void setListener(RandomWikiPageLoaderListener listener) {
        this.listener = listener;
    }
}
