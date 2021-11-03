package com.jsx.sprout

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Author: JackPan
 * Date: 2021-11-02
 * Time: 17:53
 * Description:
 */
@RunWith(AndroidJUnit4::class)
class MainActivityTest{

    @Rule
    val mActivityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun getLanguage(){
        Espresso.onView(ViewMatchers.withId(R.id.tv_language)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.group_language))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}