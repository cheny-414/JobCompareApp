package edu.gatech.seclass.jobcompare6300;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.junit.Assert.fail;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import edu.gatech.seclass.jobcompare6300.db.JobEntity;
import edu.gatech.seclass.jobcompare6300.ui.JobActivity;

import static edu.gatech.seclass.jobcompare6300.TestUtilities.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class GetJobsTest {

	@Rule public ActivityScenarioRule<JobActivity> activityScenarioRule
			= new ActivityScenarioRule<>(JobActivity.class);

	@Before
	public void startUp(){
		removeAllJobs();
		loadJobs();
	}
	@After
	public void tearDown(){
		removeAllJobs();
	}

	@Test
	public void getJobsTest(){

		List<JobEntity> testGetJobs = ApplicationController.getInstance().getJobs();

		boolean test1 = false;
		boolean test2 = false;
		for (JobEntity job : testGetJobs) {
			if (job.getTitle().equals(testJobList.get(1).getTitle()) &&
					job.getCompany().equals("Company BBB") &&
					job.getLocation().equals("San Francisco, CA")) {
				test1 = true;
			}
			if (job.getTitle().equals("Manager") &&
					job.getCompany().equals("Company CCC") &&
					job.getLocation().equals("Houston, TX")
			) {
				test2 = true;
			}
		}
		assert test1;
		assert test2;
	}

	@Test
	public void getCurrentJobTest(){

		JobEntity testCurrJob = ApplicationController.getInstance().getCurrentJob();

		boolean test1 = false;
		if (testCurrJob.getTitle().equals(testJobList.get(0).getTitle()) &&
				testCurrJob.getCompany().equals(testJobList.get(0).getCompany()) &&
				testCurrJob.getLocation().equals(testJobList.get(0).getLocation())) {
			test1 = true;
		}
		assert test1;
	}
}