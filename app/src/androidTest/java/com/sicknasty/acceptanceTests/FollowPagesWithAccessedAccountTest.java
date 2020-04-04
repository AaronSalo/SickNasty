package com.sicknasty.acceptanceTests;

import android.os.SystemClock;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import com.sicknasty.R;
import com.sicknasty.presentation.LoginActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class FollowPagesWithAccessedAccountTest {

    @Rule
    public ActivityTestRule<LoginActivity> activityRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void followPagesFromAccessedAccount()
    {
        SystemClock.sleep(2500);

        onView(withId(R.id.userName)).perform(typeText("jay1"),closeSoftKeyboard());
        SystemClock.sleep(1000);

        onView(withId(R.id.password)).perform(typeText("1234567"),closeSoftKeyboard());
        SystemClock.sleep(1000);

        onView(withId(R.id.Login)).perform(click());


    }


}
