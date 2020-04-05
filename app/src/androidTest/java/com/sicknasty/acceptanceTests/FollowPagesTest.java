package com.sicknasty.acceptanceTests;

import android.os.SystemClock;

import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.MediumTest;
import androidx.test.rule.ActivityTestRule;

import com.sicknasty.R;
import com.sicknasty.application.Service;
import com.sicknasty.objects.Exceptions.ChangeNameException;
import com.sicknasty.objects.Exceptions.ChangeUsernameException;
import com.sicknasty.objects.Exceptions.PasswordErrorException;
import com.sicknasty.objects.Exceptions.UserCreationException;
import com.sicknasty.objects.User;
import com.sicknasty.persistence.PagePersistence;
import com.sicknasty.persistence.UserPersistence;
import com.sicknasty.persistence.exceptions.DBPageNameExistsException;
import com.sicknasty.persistence.exceptions.DBUsernameExistsException;
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
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;

@RunWith(AndroidJUnit4.class)
@MediumTest
public class FollowPagesTest {

    private UserPersistence userPersistence;
    private PagePersistence pagePersistence;
    private User user;
    @Rule
    public ActivityTestRule<LoginActivity> activityActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Before
    public void login() throws ChangeNameException, PasswordErrorException, UserCreationException, ChangeUsernameException, DBUsernameExistsException, DBPageNameExistsException {
        userPersistence = Service.getUserData();
        pagePersistence = Service.getPageData();
        user = new User("JAAAY K","JAY$$","1234567");
        userPersistence.insertNewUser(user);
        pagePersistence.insertNewPage(user.getPersonalPage());
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
        SystemClock.sleep(1000);
        onView(withId(R.id.settings)).perform(click());
        SystemClock.sleep(1000);
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.logout)).perform(click());

        //also delete user and delete page

        userPersistence.deleteUser(user);
        pagePersistence.deletePage(user.getUsername());
    }

    @Test
    public void testFollowPages()
    {
        SystemClock.sleep(1000);

        onView(withId(R.id.searchButton)).perform(click());
        SystemClock.sleep(1000);

        onView(allOf(withText("texanwits"),isDisplayed())).perform(click());

        SystemClock.sleep(1000);
        onView(withId(R.id.followButton)).perform(click());

        SystemClock.sleep(1000);
        onView(withId(R.id.followButton)).perform(click());

        SystemClock.sleep(2500);

        onView(withId(R.id.home)).perform(click());

        SystemClock.sleep(1000);

        onView(withId(R.id.searchButton)).perform(click());
        SystemClock.sleep(1000);

        onView(withId(R.id.search_view)).perform(typeText("JA"));
        SystemClock.sleep(1000);

        onView(allOf(withText("JAY$$"),isDisplayed())).perform(click());

        SystemClock.sleep(1000);
        onView(withId(R.id.followButton)).perform(click());


        SystemClock.sleep(5000);

        onView(withId(R.id.home)).perform(click());
        SystemClock.sleep(1000);

        onView(withId(R.id.searchButton)).perform(click());
        SystemClock.sleep(1000);

        onView(withId(R.id.search_view)).perform(typeText("JA"),closeSoftKeyboard());
        SystemClock.sleep(1000);
        onView(allOf(withText("JAY$$"),isDisplayed())).perform(click());

        SystemClock.sleep(1000);
        onView(withId(R.id.followButton)).perform(click());

        SystemClock.sleep(5000);

        pressBack();
        SystemClock.sleep(1000);
        Espresso.closeSoftKeyboard();
        SystemClock.sleep(1000);

        onView(allOf(withText("texanwits"),isDisplayed())).perform(click());

        SystemClock.sleep(1000);
        onView(withId(R.id.followButton)).perform(click());

        SystemClock.sleep(5000);

        onView(withId(R.id.home)).perform(click());
        SystemClock.sleep(1000);

    }
}
