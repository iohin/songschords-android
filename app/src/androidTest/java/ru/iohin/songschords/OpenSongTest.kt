package ru.iohin.songschords

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import ru.iohin.songschords.ui.MainActivity

class OpenSongTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun registerIdlingResource() {
        activityRule.scenario.onActivity {
            IdlingRegistry.getInstance().register(it.mainActivityComponent.provideIdlingResource())
        }
    }

    @After
    fun unregisterIdlingResource() {
        activityRule.scenario.onActivity {
            IdlingRegistry.getInstance()
                .unregister(it.mainActivityComponent.provideIdlingResource())
        }
    }

    @Test
    fun shouldOpenSong() {
        Espresso.onView(ViewMatchers.withId(ru.iohin.songschords.feature.search.R.id.recycler_view))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0, click()
                )
            )

        Espresso.onView(ViewMatchers.withId(ru.iohin.songschords.feature.search.R.id.recycler_view))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0, click()
                )
            )

        Espresso.onView(ViewMatchers.withContentDescription("song"))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}