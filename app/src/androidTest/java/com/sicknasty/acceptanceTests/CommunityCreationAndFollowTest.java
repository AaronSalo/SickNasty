package com.sicknasty.acceptanceTests;

import android.os.SystemClock;

import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import com.sicknasty.R;
import com.sicknasty.presentation.LoginActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class CommunityCreationAndFollowTest {

    @Rule
    public ActivityTestRule<LoginActivity> createCommunityAndFollow = new ActivityTestRule<>(LoginActivity.class);
    @Before
    public void login()
    {

        //default login details
        SystemClock.sleep(2500);
        Espresso.closeSoftKeyboard();
        SystemClock.sleep(1000);
        onView(withId(R.id.userName)).perform(typeText("notbob123"),closeSoftKeyboard());
        SystemClock.sleep(1000);

        onView(withId(R.id.password)).perform(typeText("bobsgreatpass"),closeSoftKeyboard());
        SystemClock.sleep(1000);

        onView(withId(R.id.Login)).perform(click());
    }
    @After
    public void logout()
    {
        //logout from the app
        SystemClock.sleep(1000);
        onView(withId(R.id.settings)).perform(click());
        SystemClock.sleep(1000);
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.logout)).perform(click());
        SystemClock.sleep(1000);
    }

    @Test
    public void testCommunityCreation()
    {
        //try to view communities
        SystemClock.sleep(1000);
        onView(withId(R.id.communityListButton)).perform(click());
        SystemClock.sleep(1000);

        //create bunch of communities
        onView(withId(R.id.createCommunityButton)).perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.communityNameAdder)).perform(typeText("COMPUTER"));
        SystemClock.sleep(1000);
        onView(withId(R.id.addCommunity)).perform(click());
        SystemClock.sleep(1000);

        onView(withId(R.id.createCommunityButton)).perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.communityNameAdder)).perform(typeText("ARTS"));
        SystemClock.sleep(1000);
        onView(withId(R.id.addCommunity)).perform(click());
        SystemClock.sleep(1000);

        onView(withId(R.id.createCommunityButton)).perform(click());
        SystemClock.sleep(1000);
        onView(withId(R.id.communityNameAdder)).perform(typeText("BIOLOGY"));
        SystemClock.sleep(1000);
        onView(withId(R.id.addCommunity)).perform(click());
        SystemClock.sleep(1000);

        //go to a particular community and test follow button
        onView(withText("COMPUTER")).perform(click());
        SystemClock.sleep(1000);

        onView(withId(R.id.communityfollowButton)).perform(click());
        SystemClock.sleep(1000);

        pressBack();
        SystemClock.sleep(1000);

        onView(withId(R.id.communityListButton)).perform(click());
        SystemClock.sleep(1000);

        //select more community pages and try to follow
        onView(withText("ARTS")).perform(click());
        SystemClock.sleep(1000);

        onView(withId(R.id.communityfollowButton)).perform(click());
        SystemClock.sleep(1000);

        pressBack();
        SystemClock.sleep(1000);

        onView(withId(R.id.communityListButton)).perform(click());
        SystemClock.sleep(1000);

        onView(withText("BIOLOGY")).perform(click());
        SystemClock.sleep(1000);

        onView(withId(R.id.communityfollowButton)).perform(click());
        SystemClock.sleep(1000);

        pressBack();
        SystemClock.sleep(1000);

        logout();

        Espresso.closeSoftKeyboard();
        SystemClock.sleep(1000);
        onView(withId(R.id.userName)).perform(typeText("texanwits"),closeSoftKeyboard());
        SystemClock.sleep(1000);

        onView(withId(R.id.password)).perform(typeText("ihatespies"),closeSoftKeyboard());
        SystemClock.sleep(1000);

        onView(withId(R.id.Login)).perform(click());

        SystemClock.sleep(1000);
        onView(withId(R.id.communityListButton)).perform(click());
        SystemClock.sleep(1000);

        onView(withText("COMPUTER")).perform(click());
        SystemClock.sleep(1000);

        onView(withId(R.id.communityfollowButton)).perform(click());
        SystemClock.sleep(1000);

        pressBack();
        SystemClock.sleep(1000);

        onView(withId(R.id.communityListButton)).perform(click());
        SystemClock.sleep(1000);

        //select more community pages and try to follow
        onView(withText("ARTS")).perform(click());
        SystemClock.sleep(1000);

        onView(withId(R.id.communityfollowButton)).perform(click());
        SystemClock.sleep(1000);

        pressBack();
        SystemClock.sleep(1000);
    }
}
