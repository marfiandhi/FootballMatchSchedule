package divascion.marfiandhi.footballmatchschedule.view

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import divascion.marfiandhi.footballmatchschedule.R.id.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Thread.sleep

/**
 * Created by Marfiandhi on 9/27/2018.
 */
@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @Rule
    @JvmField var activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testRecyclerViewBehaviour() {
        sleep(3000)
        onView(withId(recycler))
                .check(matches(isDisplayed()))
        onView(withId(recycler)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))
        onView(withId(recycler)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(10, click()))
    }

    @Test
    fun testAppBehaviour() {
        sleep(5000)
        onView(withText("Arsenal"))
                .check(matches(isDisplayed()))
        onView(withText("Arsenal")).perform(click())

        onView(withId(add_to_favorite))
                .check(matches(isDisplayed()))
        onView(withId(add_to_favorite)).perform(click())
        onView(withText("Added to favorite"))
                .check(matches(isDisplayed()))
        pressBack()

        onView(withId(linear_bottom_navigation))
                .check(matches(isDisplayed()))
        onView(withId(nextMatch)).perform(click())

        sleep(5000)
        onView(withText("Wolves"))
                .check(matches(isDisplayed()))
        onView(withText("Wolves")).perform(click())

        onView(withId(add_to_favorite)).perform(click())
        onView(withText("Added to favorite"))
                .check(matches(isDisplayed()))
        pressBack()

        onView(withId(favorite)).perform(click())

        onView(withText("Arsenal"))
                .check(matches(isDisplayed()))
        onView(withText("Arsenal")).perform(click())

        onView(withId(add_to_favorite)).perform(click())
        onView(withText("Removed from favorite"))
                .check(matches(isDisplayed()))
        pressBack()
        onView(withId(favorite)).perform(click())
    }

}