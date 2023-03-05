package edu.gatech.seclass.jobcompare6300;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.fail;
import static edu.gatech.seclass.jobcompare6300.TestUtilities.replaceTextHelper;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import edu.gatech.seclass.jobcompare6300.db.JobEntity;
import edu.gatech.seclass.jobcompare6300.ui.MainActivity;

@RunWith(AndroidJUnit4.class)
public class AddJobUITest {

	@Rule
	public ActivityScenarioRule<MainActivity> tActivityRule = new ActivityScenarioRule<>(
			MainActivity.class);

	private JobEntity makeJobAndInputText(String title, String company, String location, int costInd,
			int salary, int bonus, int rusa, int reloc, int pch) {
		JobEntity testJob = new JobEntity();
		testJob.setTitle(title);
		testJob.setCompany(company);
		testJob.setLocation(location);
		testJob.setCostIndex(costInd);
		testJob.setYearlySalary(salary);
		testJob.setYearlyBonus(bonus);
		testJob.setRsua(rusa);
		testJob.setRelocStipend(reloc);
		testJob.setPcHolidays(pch);

		replaceTextHelper(R.id.etTitle, title);
		replaceTextHelper(R.id.etCompany, company);
		replaceTextHelper(R.id.etLocation, location);
		replaceTextHelper(R.id.etCostIndex, Integer.toString(costInd));
		replaceTextHelper(R.id.etYearlySalary, Integer.toString(salary));
		replaceTextHelper(R.id.etYearlyBonus, Integer.toString(bonus));
		replaceTextHelper(R.id.etRsua, Integer.toString(rusa));
		replaceTextHelper(R.id.etRelocStipend, Integer.toString(reloc));
		replaceTextHelper(R.id.etPcHolidays, Integer.toString(pch));

		return testJob;
	}

	@Test
	public void addJobTest() {
		onView(withId(R.id.btnJobOffer)).perform(click());

		String title = "Senior Analyst";
		String company = "Company AAA";
		String location = "Philadelphia, PA";

		replaceTextHelper(R.id.etTitle, title);
		replaceTextHelper(R.id.etCompany, company);
		replaceTextHelper(R.id.etLocation, location);
		replaceTextHelper(R.id.etCostIndex, Integer.toString(167));
		replaceTextHelper(R.id.etYearlySalary, Integer.toString(100000));
		replaceTextHelper(R.id.etYearlyBonus, Integer.toString(30000));
		replaceTextHelper(R.id.etRsua, Integer.toString(900));
		replaceTextHelper(R.id.etRelocStipend, Integer.toString(8000));
		replaceTextHelper(R.id.etPcHolidays, Integer.toString(20));
		onView(withId(R.id.action_save)).perform(click());
		onView(withText("Go to Main Menu")).perform(click());
		List<JobEntity> testGetJobs = ApplicationController.getInstance().getJobs();

		boolean test = false;
		for (JobEntity job : testGetJobs) {
			if (job.getTitle().equals(title) &&
				job.getCompany().equals(company) &&
				job.getLocation().equals(location) &&
				job.getCostIndex() == 167 &&
				job.getYearlySalary() == 100000 &&
				job.getYearlyBonus() == 30000 &&
				job.getRsua() == 900 &&
				job.getRelocStipend() == 8000 &&
				job.getPcHolidays() == 20)
			{
				test = true;
				break;
			}
		}
		assert test;
	}
}
