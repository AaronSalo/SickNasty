package com.sicknasty.acceptanceTests;

import android.os.SystemClock;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
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
@LargeTest
public class AccountAccessAndUpdatesTest {
    @Rule
    public ActivityTestRule<LoginActivity> activityRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void accessAccountAfterRegister()
    {
        //sign up
        SystemClock.sleep(2500);
        Espresso.closeSoftKeyboard();
        SystemClock.sleep(1000);
        onView(withId(R.id.signUp)).perform(click());
        SystemClock.sleep(1000);
        //have predefined data for signup
        onView(withId(R.id.signUpName)).perform(typeText("Jay K"),closeSoftKeyboard());
        onView(withId(R.id.signUpUsername)).perform(typeText("jay1"),closeSoftKeyboard());
        onView(withId(R.id.signUpPassword)).perform(typeText("1234567"),closeSoftKeyboard());
        onView(withId(R.id.Register)).perform(click());
        SystemClock.sleep(1000);


        //sign in using created account
        onView(withId(R.id.userName)).perform(typeText("jay1"),closeSoftKeyboard());
        SystemClock.sleep(1000);

        onView(withId(R.id.password)).perform(typeText("1234567"),closeSoftKeyboard());
        SystemClock.sleep(1000);

        onView(withId(R.id.Login)).perform(click());


        //try to go to srttings
        SystemClock.sleep(1000);
        onView(withId(R.id.settings)).perform(click());

        Espresso.closeSoftKeyboard();

        onView(withId(R.id.passwordShow)).perform(click());

        //update usernma and password
        onView(withId(R.id.updateUsername)).perform(clearText());
        SystemClock.sleep(1000);
        onView(withId(R.id.updateUsername)).perform(typeText("jay2"),closeSoftKeyboard());
        SystemClock.sleep(1000);

        //play around with it just to make sure
        onView(withId(R.id.updatePassword)).perform(clearText());
        SystemClock.sleep(1000);
        onView(withId(R.id.updatePassword)).perform(typeText("JAYISNICE"),closeSoftKeyboard());
        SystemClock.sleep(1000);

        onView(withId(R.id.passwordShow)).perform(click());
        SystemClock.sleep(1000);

        onView(withId(R.id.passwordShow)).perform(click());
        SystemClock.sleep(1000);

        onView(withId(R.id.updateInfo)).perform(click(),closeSoftKeyboard());
        SystemClock.sleep(1000);

        //logout and then login using updated username
        onView(withId(R.id.logout)).perform(click());

        onView(withId(R.id.userName)).perform(typeText("jay2"),closeSoftKeyboard());
        SystemClock.sleep(1000);

        onView(withId(R.id.password)).perform(typeText("JAYISNICE"),closeSoftKeyboard());
        SystemClock.sleep(1000);

        onView(withId(R.id.Login)).perform(click());

        //successful login
        SystemClock.sleep(1000);
        onView(withId(R.id.settings)).perform(click());

        Espresso.closeSoftKeyboard();
        SystemClock.sleep(1000);

        //logout just to make sure we delete shared prefernces
        onView(withId(R.id.logout)).perform(click());

        SystemClock.sleep(1000);
        Espresso.closeSoftKeyboard();
        SystemClock.sleep(1000);
    }
}
