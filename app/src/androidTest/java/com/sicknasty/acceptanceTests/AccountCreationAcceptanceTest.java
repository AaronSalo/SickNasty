package com.sicknasty.acceptanceTests;

import android.os.SystemClock;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.MediumTest;
import androidx.test.rule.ActivityTestRule;

import com.sicknasty.R;
import com.sicknasty.presentation.LoginActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@MediumTest
public class AccountCreationAcceptanceTest {
    @Rule
    public ActivityTestRule<LoginActivity> activityRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void accessAccountAfterRegister()
    {
        SystemClock.sleep(1000);
        Espresso.closeSoftKeyboard();
        SystemClock.sleep(1000);
        onView(withId(R.id.signUp)).perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.signUpName)).perform(typeText("Jay K"));
        onView(withId(R.id.signUpUsername)).perform(typeText("jay1"));
        onView(withId(R.id.signUpPassword)).perform(typeText("1234567"),closeSoftKeyboard());
        onView(withId(R.id.Register)).perform(click());

        //

        onView(withId(R.id.userName)).perform(typeText("jay1"));
        SystemClock.sleep(1000);

        onView(withId(R.id.password)).perform(typeText("1234567"),closeSoftKeyboard());
        SystemClock.sleep(1000);

        onView(withId(R.id.Login)).perform(click());


    }



}
