package com.sicknasty.acceptanceTests;

import android.Manifest;
import android.app.Instrumentation;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.SystemClock;
import android.provider.MediaStore;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;

import com.sicknasty.R;
import com.sicknasty.presentation.LoggedUserPageActivity;
import com.sicknasty.presentation.LoginActivity;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.app.Activity.RESULT_OK;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.Intents.intending;
import static org.hamcrest.core.AllOf.allOf;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasData;
import static androidx.test.espresso.matcher.ViewMatchers.withId;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class PostToPersonalAccountTest {

    @Rule
    //public ActivityTestRule<LoginActivity> activityRule = new ActivityTestRule<>(LoginActivity.class);
    public IntentsTestRule<LoginActivity> intentsTestRule = new IntentsTestRule<>(LoginActivity.class);

    @Test
    public void followPagesFromAccessedAccount()
    {
        //login
        SystemClock.sleep(2500);

        onView(withId(R.id.userName)).perform(typeText("notbob123"),closeSoftKeyboard());
        SystemClock.sleep(1000);

        onView(withId(R.id.password)).perform(typeText("bobsgreatpass"),closeSoftKeyboard());
        SystemClock.sleep(1000);

        onView(withId(R.id.Login)).perform(click());



        SystemClock.sleep(1000);
        //accept the permission to view storage automatuic
        GrantPermissionRule.grant(Manifest.permission.WRITE_EXTERNAL_STORAGE);

        //have an intent
        SystemClock.sleep(1000);
        Matcher<Intent> matcher = allOf(hasAction(Intent.ACTION_PICK),hasData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI));

        intending(matcher).respondWith(create());


        onView(withId(R.id.postButton)).perform(click());

        SystemClock.sleep(1000);
        //then match intent
        intended(matcher);
    }
    private Instrumentation.ActivityResult create(){
        //get resources
        Resources resources  = InstrumentationRegistry.getInstrumentation().getContext().getResources();

        //calculate uri
        Uri imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
                        resources.getResourcePackageName(R.drawable.ic_launcher_foreground)+"/"+
                        resources.getResourceTypeName(R.drawable.ic_launcher_foreground)+"/"+
                        resources.getResourceEntryName(R.drawable.ic_launcher_foreground));
        Intent newIntent = new Intent();
        //give it for startACtivityResult
        newIntent.setData(imageUri);
        //then return the activity result
        return new Instrumentation.ActivityResult(RESULT_OK,newIntent);
    }


}
