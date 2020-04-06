package com.sicknasty.acceptanceTests;

import android.os.SystemClock;

import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import com.sicknasty.R;
import com.sicknasty.presentation.LoginActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MessageBetween2UsersTest {

    @Rule
    public ActivityTestRule<LoginActivity> message  = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void testInteractionBetweenUsers()
    {
        //try to login for one user
        SystemClock.sleep(2500);
        Espresso.closeSoftKeyboard();
        SystemClock.sleep(1000);
        onView(withId(R.id.userName)).perform(typeText("notbob123"),closeSoftKeyboard());
        SystemClock.sleep(1000);

        onView(withId(R.id.password)).perform(typeText("bobsgreatpass"),closeSoftKeyboard());
        SystemClock.sleep(1000);

        onView(withId(R.id.Login)).perform(click());

        SystemClock.sleep(1000);

        //find the message button through search
        onView(withId(R.id.searchButton)).perform(click());
        SystemClock.sleep(1000);


        onView(withId(R.id.search_view)).perform(typeText("tex"),closeSoftKeyboard());
        SystemClock.sleep(1000);

        onView(allOf(withText("texanwits"),isDisplayed())).perform(click());

        SystemClock.sleep(1000);


        onView(withId(R.id.messageButton)).perform(click());
        SystemClock.sleep(1000);

        //enter a bunch of messages
        onView(withId(R.id.messageEntered)).perform(typeText("hey engineer!! what's up"));
        SystemClock.sleep(1000);
        onView(withId(R.id.sendMessage)).perform(click());
        SystemClock.sleep(1000);

        onView(withId(R.id.messageEntered)).perform(typeText("How's life going dude,is it sick or is it nasty?"));
        SystemClock.sleep(1000);
        onView(withId(R.id.sendMessage)).perform(click(),closeSoftKeyboard());
        SystemClock.sleep(1000);

        pressBack();

        //go back and logout and login using diffeent account
        SystemClock.sleep(1000);
        onView(withId(R.id.home)).perform(click());

        SystemClock.sleep(1000);
        onView(withId(R.id.settings)).perform(click());
        SystemClock.sleep(1000);

        Espresso.closeSoftKeyboard();
        onView(withId(R.id.logout)).perform(click());

        //follow same steps
        SystemClock.sleep(2500);
        Espresso.closeSoftKeyboard();
        SystemClock.sleep(1000);
        onView(withId(R.id.userName)).perform(typeText("texanwits"),closeSoftKeyboard());
        SystemClock.sleep(1000);

        onView(withId(R.id.password)).perform(typeText("ihatespies"),closeSoftKeyboard());
        SystemClock.sleep(1000);

        onView(withId(R.id.Login)).perform(click());

        SystemClock.sleep(1000);

        onView(withId(R.id.searchButton)).perform(click());
        SystemClock.sleep(1000);

        onView(allOf(withText("notbob123"),isDisplayed())).perform(click());

        SystemClock.sleep(1000);


        //enter a few messages to the return user and we can see we have received
        onView(withId(R.id.messageButton)).perform(click());
        SystemClock.sleep(1000);

        onView(withId(R.id.messageEntered)).perform(typeText("nothing much,apart from cororna it's going great!!"));
        SystemClock.sleep(1000);
        onView(withId(R.id.sendMessage)).perform(click());
        SystemClock.sleep(1000);

        onView(withId(R.id.messageEntered)).perform(typeText("How's your quarantine going on?"),closeSoftKeyboard());
        SystemClock.sleep(1000);
        onView(withId(R.id.sendMessage)).perform(click());
        SystemClock.sleep(1000);

        pressBack();
        SystemClock.sleep(1000);
        //we can see how the messages are displayed
        SystemClock.sleep(1000);
        onView(withId(R.id.home)).perform(click());

        SystemClock.sleep(1000);
        onView(withId(R.id.settings)).perform(click());
        SystemClock.sleep(1000);

        Espresso.closeSoftKeyboard();
        SystemClock.sleep(1000);
        onView(withId(R.id.logout)).perform(click());
        SystemClock.sleep(1000);

    }



}
