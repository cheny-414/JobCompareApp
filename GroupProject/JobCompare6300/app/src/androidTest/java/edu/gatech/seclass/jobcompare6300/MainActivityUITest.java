package edu.gatech.seclass.jobcompare6300;

import android.content.Context;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import edu.gatech.seclass.jobcompare6300.ui.JobActivity;
import edu.gatech.seclass.jobcompare6300.ui.JobCompareSettingsActivity;
import edu.gatech.seclass.jobcompare6300.ui.JobOffersActivity;
import edu.gatech.seclass.jobcompare6300.ui.MainActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityUITest {

	@Rule public ActivityScenarioRule<MainActivity> activityScenarioRule
			= new ActivityScenarioRule<>(MainActivity.class);


	@Test
	public void useAppContext() {
		// Context of the app under test.
		Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
		assertEquals("edu.gatech.seclass.jobcompare6300", appContext.getPackageName());
	}


	@Before
	public void setUp() {
		Intents.init();
	}

	@Test
	public void testCurrentJobActivityLaunch() {
		// Type text and then press the button.
		onView(withId(R.id.btnCurrentJob)).perform(click());
		intended(hasComponent(JobActivity.class.getName()));
	}

	@Test
	public void testJobOfferActivityLaunch() {
		// Type text and then press the button.
		onView(withId(R.id.btnJobOffer)).perform(click());
		intended(hasComponent(JobActivity.class.getName()));
	}

	@Test
	public void testJobOffersActivityLaunch() {
		// Type text and then press the button.
		onView(withId(R.id.btnCompareJobOffers)).perform(click());
		intended(hasComponent(JobOffersActivity.class.getName()));
	}

	@Test
	public void testJobCompareActivityLaunch() {
		// Type text and then press the button.
		onView(withId(R.id.btnAdjustJobSettings)).perform(click());
		intended(hasComponent(JobCompareSettingsActivity.class.getName()));
	}

//	@Test
	//	public void testAddJob() {
	//		JobEntity testJob = new JobEntity();
	//		testJob.setTitle("TestJob2");
	//		testJob.setCompany("TestCompany");
	//		testJob.setLocation("TestLocation");
	//		testJob.setCostIndex(5);
	//		testJob.setYearlySalary(111);
	//		testJob.setYearlyBonus(222);
	//		testJob.setRsua(333);
	//		testJob.setRelocStipend(444);
	//		testJob.setPcHolidays(555);
	//		testJob.setCurrentJob(false);
	//		ApplicationController.getInstance().addJob(testJob);
	//		assertEquals(testJob.getTitle(), "TestJob2");
	//	}
	//
	//
	//	@Test
	//	public void removeAllJobs() {
	//		// Context of the app under test.
	//		List<JobEntity> jobs = new ArrayList<>(ApplicationController.getInstance().getJobs());
	//		for (JobEntity job : jobs) {
	//			ApplicationController.getInstance().removeJob(job);
	//		}
	//	}


	@After
	public void tearDown() {
		Intents.release();
	}

}