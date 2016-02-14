package org.flycraft.android.randomwikidefinition;

import android.app.Application;
import android.support.test.rule.ActivityTestRule;
import android.test.ApplicationTestCase;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.*;

public class MainActivityTest { // TODO: Next time, use Robolectric

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @BeforeClass
    public static void prepareFakeData() {
        RandomWikiDefinition.USE_FAKE_LOADER = true;
    }

    @Test
    public void startupWithInternetConnection() {
        onView(withId(R.id.extract))
                .check(matches(isDisplayed()));
    }

    @Test
    public void loadNextDefinition() {
        onView(withId(R.id.fab))
                .perform(click());

        onView(withId(R.id.extract))
                .check(matches(isDisplayed()));
    }

}