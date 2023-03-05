package edu.gatech.seclass.jobcompare6300;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.intent.Checks;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import androidx.test.runner.lifecycle.Stage;
import edu.gatech.seclass.jobcompare6300.db.JobEntity;
import edu.gatech.seclass.jobcompare6300.ui.JobActivity;
import edu.gatech.seclass.jobcompare6300.ui.JobCompareActivity;
import edu.gatech.seclass.jobcompare6300.ui.JobOffersActivity;
import edu.gatech.seclass.jobcompare6300.ui.MainActivity;

import static androidx.core.util.Preconditions.checkNotNull;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.hasBackground;
import static androidx.test.espresso.matcher.ViewMatchers.hasTextColor;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.isNotEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static edu.gatech.seclass.jobcompare6300.TestUtils.withRecyclerView;

@RunWith(AndroidJUnit4.class)
public class JobCompareActivityTest {

    @Rule public ActivityScenarioRule<JobCompareActivity> jobCompareActivityActivityScenarioRule
            = new ActivityScenarioRule<>(JobCompareActivity.class);
    @Test
    public void testJobOffersOnView() {
        onView(withId(R.id.rvJobOffers)).check(matches(isDisplayed()));
    }

    @Test
    public void testMenuOptionsOnView() {
        onView(withId(R.id.action_cancel)).check(matches(isDisplayed()));
        onView(withId(R.id.action_compare)).check(matches(isDisplayed()));
    }
}