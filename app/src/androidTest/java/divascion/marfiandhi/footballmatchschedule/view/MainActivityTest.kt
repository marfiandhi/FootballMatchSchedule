package divascion.marfiandhi.footballmatchschedule.view

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.swipeDown
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

    private val delay = 2500L

    @Test
    fun testAppBehaviour() {
        onView(withId(linear_bottom_navigation)).check(matches(isDisplayed()))

        //Prev Match Behaviour with Favorite and UnFavorite
        onView(withId(prevMatch)).perform(click())
        testEventListBehaviour()
        testDetailEventBehaviour("Added to favorite")
        onView(withId(favorite)).perform(click())
        testFavoriteEventBehaviour()
        testDetailEventBehaviour("Removed from favorite")
        onView(withId(mainSwipeRefresh)).perform(swipeDown())

        //Next Match Behaviour with Favorite and UnFavorite
        onView(withId(nextMatch)).perform(click())
        testEventListBehaviour()
        testDetailEventBehaviour("Added to favorite")
        onView(withId(favorite)).perform(click())
        testFavoriteEventBehaviour()
        testDetailEventBehaviour("Removed from favorite")
        onView(withId(mainSwipeRefresh)).perform(swipeDown())

    }

    private fun testEventListBehaviour() {
        sleep(delay)
        onView(withId(recycler)).check(matches(isDisplayed()))
        onView(withId(mainSwipeRefresh)).perform(swipeDown())
        onView(withId(recycler)).check(matches(isDisplayed()))
        onView(withId(recycler)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(7))
        onView(withId(recycler)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(7, click()))
    }

    private fun testDetailEventBehaviour(text: String?) {
        sleep(delay)
        onView(withId(detailEventView)).check(matches(isDisplayed()))
        onView(withId(homeImg)).check(matches(isDisplayed()))
        onView(withId(awayImg)).check(matches(isDisplayed()))
        onView(withId(detailsSwipeRefresh)).perform(swipeDown())
        onView(withId(detailEventView)).check(matches(isDisplayed()))
        onView(withId(add_to_favorite)).check(matches(isDisplayed()))
        onView(withId(add_to_favorite)).perform(click())
        onView(withText(text)).check(matches(isDisplayed()))
        pressBack()

    }

    private fun testFavoriteEventBehaviour() {
        sleep(delay)
        onView(withId(recycler)).check(matches(isDisplayed()))
        onView(withId(mainSwipeRefresh)).perform(swipeDown())
        onView(withId(recycler)).check(matches(isDisplayed()))
        onView(withId(recycler)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
    }

}