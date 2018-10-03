package divascion.marfiandhi.footballmatchschedule.view

import android.support.test.espresso.Espresso.onView
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

    @Test
    fun testRecyclerViewBehaviour() {
        sleep(3000)
        onView(withId(recycler))
                .check(matches(isDisplayed()))
        onView(withId(recycler)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(11))
        onView(withId(recycler)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(11, click()))
    }

    /*@Test
    fun testAppBehaviourPrevMatch() {
        sleep(5000)

        val prevMatchTeam = activityRule.activity.teamA.text.toString()

        onView(withText(prevMatchTeam))
                .check(matches(isDisplayed()))
        onView(withText(prevMatchTeam)).perform(click())

        onView(withId(add_to_favorite))
                .check(matches(isDisplayed()))
        onView(withId(add_to_favorite)).perform(click())
        onView(withText("Added to favorite"))
                .check(matches(isDisplayed()))
        pressBack()

        onView(withId(favorite)).perform(click())

        onView(withText(prevMatchTeam))
                .check(matches(isDisplayed()))
        onView(withText(prevMatchTeam)).perform(click())

        onView(withId(add_to_favorite)).perform(click())
        onView(withText("Removed from favorite"))
                .check(matches(isDisplayed()))
        pressBack()

        onView(withId(favorite)).perform(click())

        sleep(1000)
    }

    @Test
    fun testAppBehaviourNextMatch() {
        onView(withId(linear_bottom_navigation))
                .check(matches(isDisplayed()))
        onView(withId(nextMatch)).perform(click())
        sleep(5000)
        onView(withId(recycler))
                .check(matches(isDisplayed()))
        onView(withId(recycler)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(3))
        onView(withId(recycler)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(3, click()))

        onView(withId(add_to_favorite))
                .check(matches(isDisplayed()))
        onView(withId(add_to_favorite)).perform(click())
        onView(withText("Added to favorite"))
                .check(matches(isDisplayed()))
        pressBack()

        onView(withId(favorite)).perform(click())

        onView(withId(recycler))
                .check(matches(isDisplayed()))
        onView(withId(recycler)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
        onView(withId(recycler)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        onView(withId(add_to_favorite)).perform(click())
        onView(withText("Removed from favorite"))
                .check(matches(isDisplayed()))
        pressBack()

        onView(withId(favorite)).perform(click())

        sleep(1000)
    }*/
    @Test
    fun testBottomNavBehaviour() {
        onView(withId(nextMatch)).perform(click())
        //assertEquals(expectPrev, actualPrev)

        onView(withId(mainSwipeRefresh)).perform(swipeDown())
        //TODO tambahkan verify method presenter, code ada di testEventDetailActivity
    }

    @Test
    fun testEventDetailsActivity() {
        onView(withId(mainSwipeRefresh)).perform(swipeDown())

        //TODO tambahkan verify method insert database dan delete database, edit di bawah
        /*val adapter = activityTestRule.activity
                .find<RecyclerView>(R.id.reviewsList)
                .adapter as FlexibleAdapter<ReviewItem>

        assertNotNull(adapter)

        val spy = spy(adapter)

        activityTestRule.activity
                .runOnUiThread {
                    activityTestRule.activity
                            .find<RecyclerView>(R.id.reviewsList)
                            .adapter = spy
                }

        doNothing().`when`(spy).updateDataSet(ArgumentMatchers.any())

        onView(withId(R.id.swipeRefresh)).perform(ViewActions.swipeDown())

        verify(spy, times(1)).updateDataSet(MOCKED_REVIEWS_FROM_SERVER.map(::ReviewItem))*/
    }

}