package edu.gatech.seclass.jobcompare6300;

import android.app.Activity;
import android.view.View;

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
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import androidx.test.runner.lifecycle.Stage;
import edu.gatech.seclass.jobcompare6300.db.JobEntity;
import edu.gatech.seclass.jobcompare6300.ui.JobActivity;
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
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static edu.gatech.seclass.jobcompare6300.TestUtils.withRecyclerView;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class JobOffersUITest {

	@Rule public ActivityScenarioRule<JobOffersActivity> jobOffersActivityActivityScenarioRule
			= new ActivityScenarioRule<>(JobOffersActivity.class);


	@Test
	public void testJobOffers() {
		onView(withRecyclerView(R.id.rvJobOffers)
				.atPositionOnView(0, R.id.tvTitle))
				.check(matches(withText("My Current Title")));

		onView(withRecyclerView(R.id.rvJobOffers)
				.atPositionOnView(0, R.id.tvTitle))
				.perform(longClick());

		onView(withRecyclerView(R.id.rvJobOffers)
				.atPositionOnView(1, R.id.tvTitle))
				.perform(longClick());

		onView(withId(R.id.action_compare)).check(matches(isEnabled()));

		onView(withId(R.id.action_compare)).perform(click());

		onView(withRecyclerView(R.id.rvJobOffers)
				.atPositionOnView(1, R.id.tvJobAttributeName))
				.check(matches(withText(JobComparator.TITLE)));
		onView(withRecyclerView(R.id.rvJobOffers)
				.atPositionOnView(1, R.id.tvJob1Attribute))
				.check(matches(withText("My Current Title")));
		onView(withRecyclerView(R.id.rvJobOffers)
				.atPositionOnView(1, R.id.tvJob2Attribute))
				.check(matches(withText("My Title")));

		onView(withRecyclerView(R.id.rvJobOffers)
				.atPositionOnView(8, R.id.tvJobAttributeName))
				.check(matches(withText(JobComparator.PC_HOLIDAYS)));
		onView(withRecyclerView(R.id.rvJobOffers)
				.atPositionOnView(8, R.id.tvJob1Attribute))
				.check(matches(withText("10")));
		onView(withRecyclerView(R.id.rvJobOffers)
				.atPositionOnView(8, R.id.tvJob2Attribute))
				.check(matches(withText("10")));

	}

}