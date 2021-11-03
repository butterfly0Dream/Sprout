package com.jsx.sprout.ui.setting

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.jsx.sprout.MainActivity
import com.jsx.sprout.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Author: JackPan
 * Date: 2021-11-02
 * Time: 17:27
 * Description:
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class SettingFragmentTest{

    @Rule
    val mActivityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun tesLanguage(){
        onView(withId(R.id.tv_language)).perform(click())
        onView(withId(R.id.group_language)).check(matches(isDisplayed()))
    }
}