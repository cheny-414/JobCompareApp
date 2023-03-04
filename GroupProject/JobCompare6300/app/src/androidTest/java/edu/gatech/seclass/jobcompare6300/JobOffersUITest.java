package edu.gatech.seclass.jobcompare6300;

import android.app.Activity;

import org.hamcrest.Matchers;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collection;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import androidx.test.runner.lifecycle.Stage;
import edu.gatech.seclass.jobcompare6300.ui.JobCompareActivity;
import edu.gatech.seclass.jobcompare6300.ui.JobOffersActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
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


	@Ignore
	@Test
	public void testJobCompareError() {
		ActivityScenario.launch(JobCompareActivity.class);
		Activity jobCompareActivity = getCurrentActivity();
		onView(withText("Invalid job ids for comparison"))
				.inRoot(withDecorView(Matchers.not(jobCompareActivity.getWindow().getDecorView())))// Here we use decorView
				.check(matches(isDisplayed()));
	}


	public Activity getCurrentActivity() {
		final Activity[] currentActivity = new Activity[1];
		InstrumentationRegistry.getInstrumentation().runOnMainSync(() -> {
			Collection<Activity> allActivities = ActivityLifecycleMonitorRegistry.getInstance()
					.getActivitiesInStage(Stage.RESUMED);
			if (!allActivities.isEmpty()) {
				currentActivity[0] = allActivities.iterator().next();
			}
		});
		return currentActivity[0];
	}

}