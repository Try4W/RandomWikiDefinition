package org.flycraft.android.randomwikidefinition;

import org.flycraft.android.randomwikidefinition.remote.RandomWikiPageLoaderImpl;
import org.flycraft.android.randomwikidefinition.remote.RandomWikiPageLoaderListener;
import org.flycraft.android.randomwikidefinition.remote.WikiPage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.verification.VerificationMode;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import static org.junit.Assert.*;

@PowerMockIgnore({"javax.net.ssl.*"})
@RunWith(PowerMockRunner.class)
@PrepareForTest(value = {HttpsURLConnection.class})
public class RandomWikiPageLoaderTest {

    @Test(timeout = 5000)
    public void makeRequestAndCheckIsResultExists() throws Exception {
        URL mockedUrl = TestTools.getMockUrlThatReturnsFileContent("demo_response.json");

        PowerMockito.whenNew(URL.class)
                .withAnyArguments()
                .thenReturn(mockedUrl);

        RandomWikiPageLoaderListener listener = Mockito.mock(RandomWikiPageLoaderListener.class);

        RandomWikiPageLoaderImpl randomWikiPageLoader = new RandomWikiPageLoaderImpl();
        randomWikiPageLoader.setListener(listener);
        randomWikiPageLoader.start();

        Mockito.verify(listener, Mockito.timeout(3000)).onResult(Mockito.any(WikiPage.class));
    }

}