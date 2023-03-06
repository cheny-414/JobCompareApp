package edu.gatech.seclass.jobcompare6300;

import android.app.Activity;
import android.view.View;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import androidx.test.runner.lifecycle.Stage;
import edu.gatech.seclass.jobcompare6300.db.JobEntity;
import edu.gatech.seclass.jobcompare6300.ui.JobActivity;
import edu.gatech.seclass.jobcompare6300.ui.MainActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class CurrentJobUITest {

	private View decorView;

	@Rule
	public ActivityScenarioRule<MainActivity> tActivityRule = new ActivityScenarioRule<>(
			MainActivity.class);

	@Before
	public void setUp() {
		Intents.init();
		tActivityRule.getScenario().onActivity(new ActivityScenario.ActivityAction<MainActivity>() {
			@Override
			public void perform(MainActivity activity) {
				decorView = activity.getWindow().getDecorView();
			}
		});
	}


	private void replaceTextHelper(int viewId, String stringToBeSet) {
		// to reduce flaky test, https://stackoverflow.com/a/53430379/1326678
		onView(withId(viewId)).perform(clearText(), replaceText(stringToBeSet), closeSoftKeyboard());
	}

	@Test
	public void currentJobEmptyTest() {
		//Empty Database
		List<JobEntity> jobs = new ArrayList<>(ApplicationController.getInstance().getJobs());
		for (JobEntity job : jobs) {
			ApplicationController.getInstance().removeJob(job);
		}

		onView(withId(R.id.btnCurrentJob)).perform(click());
		onView(withId(R.id.etTitle)).check(matches(withText("")));
		onView(withId(R.id.etCompany)).check(matches(withText("")));
		onView(withId(R.id.etLocation)).check(matches(withText("")));
		onView(withId(R.id.etCostIndex)).check(matches(withText("")));
		onView(withId(R.id.etYearlySalary)).check(matches(withText("")));
		onView(withId(R.id.etYearlyBonus)).check(matches(withText("")));
		onView(withId(R.id.etRsua)).check(matches(withText("")));
		onView(withId(R.id.etRelocStipend)).check(matches(withText("")));
		onView(withId(R.id.etPcHolidays)).check(matches(withText("")));
	}

	@Test
	public void currentJobFilledTest() {
		//Empty Database
		List<JobEntity> jobs = new ArrayList<>(ApplicationController.getInstance().getJobs());
		for (JobEntity job : jobs) {
			ApplicationController.getInstance().removeJob(job);
		}

		onView(withId(R.id.btnCurrentJob)).perform(click());

		replaceTextHelper(R.id.etTitle, "My Current Title");
		replaceTextHelper(R.id.etCompany, "My Current Company");
		replaceTextHelper(R.id.etLocation, "My Current Location");
		replaceTextHelper(R.id.etCostIndex, "50");
		replaceTextHelper(R.id.etYearlySalary, "1000");
		replaceTextHelper(R.id.etYearlyBonus, "1000");
		replaceTextHelper(R.id.etRsua, "500");
		replaceTextHelper(R.id.etRelocStipend, "500");
		replaceTextHelper(R.id.etPcHolidays, "10");

		onView(withId(R.id.action_save)).perform(click());
		onView(withId(R.id.btnCurrentJob)).perform(click());

		onView(withId(R.id.etTitle)).check(matches(withText("My Current Title")));
		onView(withId(R.id.etCompany)).check(matches(withText("My Current Company")));
		onView(withId(R.id.etLocation)).check(matches(withText("My Current Location")));
		onView(withId(R.id.etCostIndex)).check(matches(withText("50")));
		onView(withId(R.id.etYearlySalary)).check(matches(withText("1000.00")));
		onView(withId(R.id.etYearlyBonus)).check(matches(withText("1000.00")));
		onView(withId(R.id.etRsua)).check(matches(withText("500.00")));
		onView(withId(R.id.etRelocStipend)).check(matches(withText("500.00")));
		onView(withId(R.id.etPcHolidays)).check(matches(withText("10")));
	}

	@Test
	public void emptySaveCurrentJobTest() {
		//Empty Database
		List<JobEntity> jobs = new ArrayList<>(ApplicationController.getInstance().getJobs());
		for (JobEntity job : jobs) {
			ApplicationController.getInstance().removeJob(job);
		}

		onView(withId(R.id.btnCurrentJob)).perform(click());
		onView(withId(R.id.action_save)).perform(click());

		onView(withId(R.id.etTitle)).check(matches(hasErrorText("Job title cannot be empty")));
		onView(withId(R.id.etCompany)).check(matches(hasErrorText("Company name cannot be empty")));
		onView(withId(R.id.etCostIndex)).check(matches(hasErrorText("Cost index cannot be empty")));
		onView(withId(R.id.etYearlySalary)).check(matches(hasErrorText("Yearly salary cannot be empty")));
		onView(withId(R.id.etYearlyBonus)).check(matches(hasErrorText("Yearly bonus cannot be empty")));
		onView(withId(R.id.etRsua)).check(matches(hasErrorText("Restricted Stock Unit Award cannot be empty")));
		onView(withId(R.id.etRelocStipend)).check(matches(hasErrorText("Relocation stipend cannot be empty")));
		onView(withId(R.id.etPcHolidays)).check(matches(hasErrorText("Personal Choice Holidays cannot be empty")));
	}

	@Test
	public void testAddCurrentJob() throws InterruptedException {
		List<JobEntity> jobs = new ArrayList<>(ApplicationController.getInstance().getJobs());
		for (JobEntity job : jobs) {
			ApplicationController.getInstance().removeJob(job);
		}
		onView(withId(R.id.btnCurrentJob)).perform(click());
		intended(hasComponent(JobActivity.class.getName()));

		String title = "Senior Analyst";
		String company = "Company AAA";
		String location = "Philadelphia, PA";
		String costIndex = "167";
		String salary = "100000";
		String bonus = "30000";
		String rusa = "900";
		String relo = "8000";
		String pch = "20";

		replaceTextHelper(R.id.etTitle, title);
		replaceTextHelper(R.id.etCompany, company);
		replaceTextHelper(R.id.etLocation, location);
		replaceTextHelper(R.id.etCostIndex, costIndex);
		replaceTextHelper(R.id.etYearlySalary, salary);
		replaceTextHelper(R.id.etYearlyBonus, bonus);
		replaceTextHelper(R.id.etRsua, rusa);
		replaceTextHelper(R.id.etRelocStipend, relo);
		replaceTextHelper(R.id.etPcHolidays, pch);

//		Activity jobActivity = getCurrentActivity();
		onView(withId(R.id.action_save)).perform(click());
		onView(withId(R.id.btnCurrentJob)).perform(click());

		onView(withId(R.id.etTitle)).check(matches(withText(title)));
		onView(withId(R.id.etCompany)).check(matches(withText(company)));
		onView(withId(R.id.etLocation)).check(matches(withText(location)));
		onView(withId(R.id.etCostIndex)).check(matches(withText(costIndex)));
		onView(withId(R.id.etYearlySalary)).check(matches(withText(salary + ".00")));
		onView(withId(R.id.etYearlyBonus)).check(matches(withText(bonus + ".00")));
		onView(withId(R.id.etRsua)).check(matches(withText(rusa + ".00")));
		onView(withId(R.id.etRelocStipend)).check(matches(withText(relo + ".00")));
		onView(withId(R.id.etPcHolidays)).check(matches(withText(pch)));

		onView(withId(R.id.action_cancel)).perform(click());

		//new ActivityScenarioRule<>(JobActivity.class).getScenario().onActivity(activity -> decorView = activity.getWindow().getDecorView());
		//		onView(withText("Created a new job"))
		//				.inRoot(withDecorView(Matchers.not(jobActivity.getWindow().getDecorView())))// Here we use decorView
		//				.check(matches(isDisplayed()));
//		onView(withText("Created a new job"))
//				.inRoot(withDecorView(not(is(jobActivity.getWindow().getDecorView())))).check(matches(isDisplayed()));

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


	@After
	public void tearDown() {
		Intents.release();
	}
}