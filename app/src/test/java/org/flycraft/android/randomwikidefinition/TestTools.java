package org.flycraft.android.randomwikidefinition;

import org.powermock.api.mockito.PowerMockito;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

import javax.net.ssl.HttpsURLConnection;

public class TestTools {

    public static URL getMockUrlThatReturnsFileContent(final String filename) {
        final File file = new File("src/main/assets/" + filename);
        try {
            final HttpsURLConnection mockConnection = PowerMockito.mock(HttpsURLConnection.class);
            PowerMockito.when(mockConnection.getResponseCode()).thenReturn(HttpsURLConnection.HTTP_OK);
            PowerMockito.when(mockConnection.getInputStream()).thenReturn(
                    new FileInputStream(file)
            );

            final URLStreamHandler handler = new URLStreamHandler() {
                @Override
                protected URLConnection openConnection(final URL arg0)
                        throws IOException {
                    return mockConnection;
                }
            };
            return new URL("https://foo.bar", "foo.bar", 80, "", handler);
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Can't read file: " + file.getAbsolutePath());
    }

}
