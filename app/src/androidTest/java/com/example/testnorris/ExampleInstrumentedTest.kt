package com.example.testnorris

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers.`is`
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @get:Rule
    var activityScenarioRule = activityScenarioRule<MainActivity>()

    @Test
    public fun testFirst() {

        //Fist test on empty field
        onView(withId(R.id.button)).check(matches(withText("RELOAD")))
        onView(withId(R.id.button)).perform(click())
        //check that our recyclerView is still empty
        onView(withId(R.id.recyclerView)).check(RecyclerViewItemCountAssertion(0))

        //Input exact number of Jokes and check how many of them appeared
        onView(withId(R.id.editText)).perform(typeText("3"), closeSoftKeyboard())
        onView(withId(R.id.button)).perform(click())
        Thread.sleep(3000) // only for better test visuals
        onView(withId(R.id.recyclerView)).check(RecyclerViewItemCountAssertion(3))

        //Go to the web Fragment
        onView(withId(R.id.navigation_dashboard)).perform(click())
        Thread.sleep(3000) // only for better test visuals
        onView(isRoot()).perform(pressBack());
        Thread.sleep(3000) // only for better test visuals

        //Input big number and scroll down
        onView(withId(R.id.editText)).perform(clearText(), typeText("10"), closeSoftKeyboard())
        onView(withId(R.id.button)).perform(click())
        Thread.sleep(7000) // only for better test visuals
        onView(withId(R.id.recyclerView)).perform(swipeUp())

        Thread.sleep(3000) // only for better test visuals

    }

    class RecyclerViewItemCountAssertion(private var expectedCount: Int) : ViewAssertion {
        fun RecyclerViewItemCountAssertion(expectedCount: Int) {
            this.expectedCount = expectedCount
        }

        override fun check(view: View, noViewFoundException: NoMatchingViewException?) {
            if (noViewFoundException != null) {
                throw noViewFoundException
            }
            val recyclerView = view as RecyclerView
            val adapter = recyclerView.adapter
            assertThat(adapter!!.itemCount, `is`(expectedCount))
        }
    }
}