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

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class GetJobsTest {

	@Rule public ActivityScenarioRule<JobActivity> activityScenarioRule
			= new ActivityScenarioRule<>(JobActivity.class);

	private LinkedList<JobEntity> jobList = new LinkedList<JobEntity>();

	private void replaceTextHelper(int viewId, String stringToBeSet) {
		// to reduce flaky test, https://stackoverflow.com/a/53430379/1326678
		onView(withId(viewId)).perform(clearText(), replaceText(stringToBeSet), closeSoftKeyboard());
	}

	public void removeAllJobs(){
		List<JobEntity> jobList = new ArrayList<>(ApplicationController.getInstance().getJobs());
		for (JobEntity jobEntity : jobList) {
			ApplicationController.getInstance().removeJob(jobEntity);
		}

		List<JobEntity> emptyJobList = ApplicationController.getInstance().getJobs();
		Assert.assertTrue(emptyJobList.isEmpty());

	}

	public void loadJobs() {
		JobEntity jobOffer0 = new JobEntity();
		jobOffer0.setTitle("Senior Analyst");
		jobOffer0.setCompany("Company AAA");
		jobOffer0.setLocation("Philadelphia, PA");
		jobOffer0.setCostIndex(167);
		jobOffer0.setYearlySalary(100000);
		jobOffer0.setYearlyBonus(30000);
		jobOffer0.setRsua(900);
		jobOffer0.setRelocStipend(8000);
		jobOffer0.setPcHolidays(20);
		jobOffer0.setCurrentJob(true);

		JobEntity jobOffer1 = new JobEntity();
		jobOffer1.setTitle("Lead Analyst");
		jobOffer1.setCompany("Company BBB");
		jobOffer1.setLocation("San Francisco, CA");
		jobOffer1.setCostIndex(203);
		jobOffer1.setYearlySalary(130000);
		jobOffer1.setYearlyBonus(40000);
		jobOffer1.setRsua(1000);
		jobOffer1.setRelocStipend(12000);
		jobOffer1.setPcHolidays(15);
		jobOffer1.setCurrentJob(false);

		JobEntity jobOffer2 = new JobEntity();
		jobOffer2.setTitle("Manager");
		jobOffer2.setCompany("Company CCC");
		jobOffer2.setLocation("Houston, TX");
		jobOffer2.setCostIndex(142);
		jobOffer2.setYearlySalary(110000);
		jobOffer2.setYearlyBonus(25000);
		jobOffer2.setRsua(600);
		jobOffer2.setRelocStipend(8000);
		jobOffer2.setPcHolidays(17);
		jobOffer2.setCurrentJob(false);

		ApplicationController.getInstance().addJob(jobOffer0);
		ApplicationController.getInstance().addJob(jobOffer1);
		ApplicationController.getInstance().addJob(jobOffer2);

		jobList.add(jobOffer0);
		jobList.add(jobOffer1);
		jobList.add(jobOffer2);

	}


	@Test
	public void getJobsTest(){

//		JobEntity jobOffer1 = new JobEntity();
//		jobOffer1.setTitle("Lead Analyst");
//		jobOffer1.setCompany("Company BBB");
//		jobOffer1.setLocation("San Francisco, CA");
//		jobOffer1.setCostIndex(203);
//		jobOffer1.setYearlySalary(130000);
//		jobOffer1.setYearlyBonus(40000);
//		jobOffer1.setRsua(1000);
//		jobOffer1.setRelocStipend(12000);
//		jobOffer1.setPcHolidays(15);
//		ApplicationController.getInstance().addJob(jobOffer1);
//
//		JobEntity jobOffer2 = new JobEntity();
//		jobOffer2.setTitle("Manager");
//		jobOffer2.setCompany("Company CCC");
//		jobOffer2.setLocation("Houston, TX");
//		jobOffer2.setCostIndex(142);
//		jobOffer2.setYearlySalary(110000);
//		jobOffer2.setYearlyBonus(25000);
//		jobOffer2.setRsua(600);
//		jobOffer2.setRelocStipend(8000);
//		jobOffer2.setPcHolidays(17);
//		ApplicationController.getInstance().addJob(jobOffer2);

		List<JobEntity> testGetJobs = ApplicationController.getInstance().getJobs();

		boolean test1 = false;
		boolean test2 = false;
		for (JobEntity job : testGetJobs) {
			if (job.getTitle().equals(jobList.get(1).getTitle()) &&
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

	@Before
	public void startUp(){
		removeAllJobs();
		loadJobs();
	}

	@Test
	public void getCurrentJobTest(){

		JobEntity testCurrJob = ApplicationController.getInstance().getCurrentJob();

		boolean test1 = false;
		if (testCurrJob.getTitle().equals(jobList.get(0).getTitle()) &&
				testCurrJob.getCompany().equals(jobList.get(0).getCompany()) &&
				testCurrJob.getLocation().equals(jobList.get(0).getLocation())) {
			test1 = true;
		}
		assert test1;
	}

	@After
	public void tearDown(){
		removeAllJobs();
	}

}