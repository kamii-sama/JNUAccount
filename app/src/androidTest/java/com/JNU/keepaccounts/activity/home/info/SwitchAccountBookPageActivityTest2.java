package com.JNU.keepaccounts.activity.home.info;


import static android.content.Context.MODE_PRIVATE;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;

import com.JNU.keepaccounts.R;
import com.JNU.keepaccounts.bean.AccountBook;
import com.JNU.keepaccounts.utils.globle.GlobalInfo;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class SwitchAccountBookPageActivityTest2 {


    @Before
    public void setUp() throws Exception {
        ArrayList<AccountBook> data = new ArrayList<>();
        try
        {
            Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
            FileOutputStream dataStream=context.openFileOutput("mydata.dat",Context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(dataStream);
            out.writeObject(data);
            Log.v("dataSave", String.valueOf(data));
            out.close();
            dataStream.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    @After
    public void  tearDown() {
        ArrayList<AccountBook> data = new ArrayList<>();
        try
        {
            Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
            FileOutputStream dataStream=context.openFileOutput("mydata.dat",Context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(dataStream);
            out.writeObject(data);
            Log.v("dataSave", String.valueOf(data));
            out.close();
            dataStream.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    @Rule
    public ActivityScenarioRule<SwitchAccountBookPageActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(SwitchAccountBookPageActivity.class);

    @Test
    public void switchAccountBookPageActivityTest2() throws Exception {
        ViewInteraction linearLayout = onView(
                allOf(withId(R.id.activity_switch_account_book_page_add_linear),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                1)));
        linearLayout.perform(scrollTo(), click());

        ViewInteraction editText = onView(
                allOf(childAtPosition(
                                allOf(withId(com.google.android.material.R.id.custom),
                                        childAtPosition(
                                                withId(com.google.android.material.R.id.customPanel),
                                                0)),
                                0),
                        isDisplayed()));
        editText.perform(replaceText("1"), closeSoftKeyboard());

        ViewInteraction materialButton = onView(
                allOf(withId(android.R.id.button1), withText("添加"),
                        childAtPosition(
                                childAtPosition(
                                        withId(com.google.android.material.R.id.buttonPanel),
                                        0),
                                3)));
        materialButton.perform(scrollTo(), click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.item_switch_account_book_account_book_name), withText("1"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))),
                        isDisplayed()));
        textView.check(matches(isDisplayed()));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
