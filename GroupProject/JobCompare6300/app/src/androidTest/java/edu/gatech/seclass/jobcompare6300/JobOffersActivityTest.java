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
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.intent.Checks;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
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
import static edu.gatech.seclass.jobcompare6300.TestUtilities.*;

@RunWith(AndroidJUnit4.class)
public class JobOffersActivityTest {

	@Rule
	public ActivityTestRule<JobOffersActivity> mActivityRule = new ActivityTestRule<>(
			JobOffersActivity.class, false, false);


	@Before
	public void startUp() {
		removeAllJobs();
		loadJobs();
		loadDefaultWeight();
		mActivityRule.launchActivity(null);
	}
	//    @After
	//    public void tearDown(){
	//        removeAllJobs();
	//    }


	//testing whether the ranked list is shown as expected
	@Test
	public void testCurrentJobOnTop() {
		onView(withRecyclerView(R.id.rvJobOffers)
				.atPositionOnView(0, R.id.tvTitle))
				.check(matches(withText("Senior Analyst")));
		onView(withRecyclerView(R.id.rvJobOffers)
				.atPositionOnView(0, R.id.tvCompany))
				.check(matches(withText("Company AAA")));
		onView(withRecyclerView(R.id.rvJobOffers)
				.atPositionOnView(0, R.id.tvJobScore))
				.check(matches(withText("Score: 18135.1")));
	}


	@Test
	public void testJobOffersRankedByScore() {
		onView(withRecyclerView(R.id.rvJobOffers)
				.atPositionOnView(1, R.id.tvTitle))
				.check(matches(withText("Manager")));
		onView(withRecyclerView(R.id.rvJobOffers)
				.atPositionOnView(1, R.id.tvCompany))
				.check(matches(withText("Company CCC")));
		onView(withRecyclerView(R.id.rvJobOffers)
				.atPositionOnView(1, R.id.tvJobScore))
				.check(matches(withText("Score: 21657.1")));

		onView(withRecyclerView(R.id.rvJobOffers)
				.atPositionOnView(2, R.id.tvTitle))
				.check(matches(withText("Lead Analyst")));
		onView(withRecyclerView(R.id.rvJobOffers)
				.atPositionOnView(2, R.id.tvCompany))
				.check(matches(withText("Company BBB")));
		onView(withRecyclerView(R.id.rvJobOffers)
				.atPositionOnView(2, R.id.tvJobScore))
				.check(matches(withText("Score: 19937.7")));

	}


	//Testing whether the compare button is disabled with only 1 or more than 2 jobs selected
	@Test
	public void testSelectOnlyOneJobOffer() {
		onView(withRecyclerView(R.id.rvJobOffers)
				.atPositionOnView(0, R.id.tvTitle))
				.perform(longClick());
		onView(withId(R.id.action_compare)).check(matches(isNotEnabled()));
	}


	@Test
	public void testSelectThreeJobOffers() {
		onView(withRecyclerView(R.id.rvJobOffers)
				.atPositionOnView(0, R.id.tvTitle))
				.perform(longClick());
		onView(withRecyclerView(R.id.rvJobOffers)
				.atPositionOnView(1, R.id.tvTitle))
				.perform(longClick());
		onView(withRecyclerView(R.id.rvJobOffers)
				.atPositionOnView(2, R.id.tvTitle))
				.perform(longClick());
		onView(withId(R.id.action_compare)).check(matches(isNotEnabled()));
	}


	@Test
	public void testJobOfferSelection() {
		onView(withRecyclerView(R.id.rvJobOffers)
				.atPositionOnView(0, R.id.tvTitle))
				.perform(longClick());
		onView(withRecyclerView(R.id.rvJobOffers)
				.atPositionOnView(0, R.id.tvTitle))
				.perform(longClick());
		onView(withRecyclerView(R.id.rvJobOffers)
				.atPositionOnView(0, R.id.tvTitle))
				.perform(click());
		onView(withId(R.id.etTitle)).check(matches(withText("Senior Analyst")));
	}


	//Testing whether the comparison results are shown as expected
	//including text, background color, field value
	@Test
	public void testCompareJobOffersResults() {
		onView(withRecyclerView(R.id.rvJobOffers)
				.atPositionOnView(0, R.id.tvTitle))
				.perform(longClick());
		onView(withRecyclerView(R.id.rvJobOffers)
				.atPositionOnView(1, R.id.tvTitle))
				.perform(longClick());
		onView(withId(R.id.action_compare)).perform(click());

		//check descriptions and values
		onView(withRecyclerView(R.id.rvJobOffers)
				.atPositionOnView(1, R.id.tvJobAttributeName))
				.check(matches(withText(JobComparator.TITLE)));
		onView(withRecyclerView(R.id.rvJobOffers)
				.atPositionOnView(1, R.id.tvJob1Attribute))
				.check(matches(withText("Senior Analyst")));
		onView(withRecyclerView(R.id.rvJobOffers)
				.atPositionOnView(1, R.id.tvJob2Attribute))
				.check(matches(withText("Manager")));

		onView(withRecyclerView(R.id.rvJobOffers)
				.atPositionOnView(2, R.id.tvJobAttributeName))
				.check(matches(withText(JobComparator.COMPANY)));
		onView(withRecyclerView(R.id.rvJobOffers)
				.atPositionOnView(2, R.id.tvJob1Attribute))
				.check(matches(withText("Company AAA")));
		onView(withRecyclerView(R.id.rvJobOffers)
				.atPositionOnView(2, R.id.tvJob2Attribute))
				.check(matches(withText("Company CCC")));

		onView(withRecyclerView(R.id.rvJobOffers)
				.atPositionOnView(3, R.id.tvJobAttributeName))
				.check(matches(withText(JobComparator.LOCATION)));
		onView(withRecyclerView(R.id.rvJobOffers)
				.atPositionOnView(3, R.id.tvJob1Attribute))
				.check(matches(withText("Philadelphia, PA")));
		onView(withRecyclerView(R.id.rvJobOffers)
				.atPositionOnView(3, R.id.tvJob2Attribute))
				.check(matches(withText("Houston, TX")));

		onView(withRecyclerView(R.id.rvJobOffers)
				.atPositionOnView(4, R.id.tvJobAttributeName))
				.check(matches(withText(JobComparator.YEARLY_ADJUSTED_SALARY)));
		onView(withRecyclerView(R.id.rvJobOffers)
				.atPositionOnView(4, R.id.tvJob1Attribute))
				.check(matches(withText("59880.24")));
		onView(withRecyclerView(R.id.rvJobOffers)
				.atPositionOnView(4, R.id.tvJob2Attribute))
				.check(matches(withText("77464.79")));
		//        check text background color

		onView(withRecyclerView(R.id.rvJobOffers)
				.atPositionOnView(4, R.id.tvJob1Attribute))
				.check(matches(matchesBackgroundColor(R.color.red)));
		onView(withRecyclerView(R.id.rvJobOffers)
				.atPositionOnView(4, R.id.tvJob2Attribute))
				.check(matches(matchesBackgroundColor(R.color.green)));

		onView(withRecyclerView(R.id.rvJobOffers)
				.atPositionOnView(5, R.id.tvJobAttributeName))
				.check(matches(withText(JobComparator.YEARLY_ADJUSTED_BONUS)));
		onView(withRecyclerView(R.id.rvJobOffers)
				.atPositionOnView(5, R.id.tvJob1Attribute))
				.check(matches(withText("17964.072")));
		onView(withRecyclerView(R.id.rvJobOffers)
				.atPositionOnView(5, R.id.tvJob2Attribute))
				.check(matches(withText("17605.635")));
		//        check text background color

		onView(withRecyclerView(R.id.rvJobOffers)
				.atPositionOnView(5, R.id.tvJob1Attribute))
				.check(matches(matchesBackgroundColor(R.color.green)));
		onView(withRecyclerView(R.id.rvJobOffers)
				.atPositionOnView(5, R.id.tvJob2Attribute))
				.check(matches(matchesBackgroundColor(R.color.red)));


		onView(withRecyclerView(R.id.rvJobOffers)
				.atPositionOnView(6, R.id.tvJobAttributeName))
				.check(matches(withText(JobComparator.RSUA)));
		onView(withRecyclerView(R.id.rvJobOffers)
				.atPositionOnView(6, R.id.tvJob1Attribute))
				.check(matches(withText("900.0")));
		onView(withRecyclerView(R.id.rvJobOffers)
				.atPositionOnView(6, R.id.tvJob2Attribute))
				.check(matches(withText("600.0")));
		//        check text background color

		onView(withRecyclerView(R.id.rvJobOffers)
				.atPositionOnView(6, R.id.tvJob1Attribute))
				.check(matches(matchesBackgroundColor(R.color.green)));
		onView(withRecyclerView(R.id.rvJobOffers)
				.atPositionOnView(6, R.id.tvJob2Attribute))
				.check(matches(matchesBackgroundColor(R.color.red)));


		onView(withRecyclerView(R.id.rvJobOffers)
				.atPositionOnView(7, R.id.tvJobAttributeName))
				.check(matches(withText(JobComparator.RELOC_STIPEND)));
		onView(withRecyclerView(R.id.rvJobOffers)
				.atPositionOnView(7, R.id.tvJob1Attribute))
				.check(matches(withText("8000.0")));
		onView(withRecyclerView(R.id.rvJobOffers)
				.atPositionOnView(7, R.id.tvJob2Attribute))
				.check(matches(withText("8000.0")));
		//        check text background color

		onView(withRecyclerView(R.id.rvJobOffers)
				.atPositionOnView(7, R.id.tvJob1Attribute))
				.check(matches(matchesBackgroundColor(R.color.white)));
		onView(withRecyclerView(R.id.rvJobOffers)
				.atPositionOnView(7, R.id.tvJob2Attribute))
				.check(matches(matchesBackgroundColor(R.color.white)));


		onView(withRecyclerView(R.id.rvJobOffers)
				.atPositionOnView(8, R.id.tvJobAttributeName))
				.check(matches(withText(JobComparator.PC_HOLIDAYS)));
		onView(withRecyclerView(R.id.rvJobOffers)
				.atPositionOnView(8, R.id.tvJob1Attribute))
				.check(matches(withText("20")));
		onView(withRecyclerView(R.id.rvJobOffers)
				.atPositionOnView(8, R.id.tvJob2Attribute))
				.check(matches(withText("17")));

		//        check text background color

		onView(withRecyclerView(R.id.rvJobOffers)
				.atPositionOnView(8, R.id.tvJob1Attribute))
				.check(matches(matchesBackgroundColor(R.color.green)));
		onView(withRecyclerView(R.id.rvJobOffers)
				.atPositionOnView(8, R.id.tvJob2Attribute))
				.check(matches(matchesBackgroundColor(R.color.red)));

	}


	//Testing whether cancel/perform another comparison options are provided to users
	@Test
	public void testCompareJobOffersAndCancel() {
		onView(withRecyclerView(R.id.rvJobOffers)
				.atPositionOnView(0, R.id.tvTitle))
				.perform(longClick());
		onView(withRecyclerView(R.id.rvJobOffers)
				.atPositionOnView(1, R.id.tvTitle))
				.perform(longClick());
		onView(withId(R.id.action_compare)).perform(click());

		//        check return to main menu

		onView(withId(R.id.action_cancel)).perform(click());
		onView(withId(R.id.btnCurrentJob)).check(matches(isDisplayed()));

	}


	@Test
	public void testCompareJobOffersAndPerformAnotherCompare() {
		onView(withRecyclerView(R.id.rvJobOffers)
				.atPositionOnView(0, R.id.tvTitle))
				.perform(longClick());
		onView(withRecyclerView(R.id.rvJobOffers)
				.atPositionOnView(1, R.id.tvTitle))
				.perform(longClick());
		onView(withId(R.id.action_compare)).perform(click());

		//        check return to main menu

		onView(withId(R.id.action_compare)).perform(click());
		onView(withId(R.id.rvJobOffers)).check(matches(isDisplayed()));

	}


	@Test
	public void testCompareJobOffersWithUpdatedSettings() {
		updateWeight();
		ActivityScenario<JobOffersActivity> jobOffersActivityActivityScenarioRule
				= ActivityScenario.launch(JobOffersActivity.class);

		onView(withRecyclerView(R.id.rvJobOffers)
				.atPositionOnView(0, R.id.tvTitle))
				.check(matches(withText("Senior Analyst")));
		onView(withRecyclerView(R.id.rvJobOffers)
				.atPositionOnView(0, R.id.tvCompany))
				.check(matches(withText("Company AAA")));
		onView(withRecyclerView(R.id.rvJobOffers)
				.atPositionOnView(0, R.id.tvJobScore))
				.check(matches(withText("Score: 9505.8")));


		onView(withRecyclerView(R.id.rvJobOffers)
				.atPositionOnView(1, R.id.tvTitle))
				.check(matches(withText("Lead Analyst")));
		onView(withRecyclerView(R.id.rvJobOffers)
				.atPositionOnView(1, R.id.tvCompany))
				.check(matches(withText("Company BBB")));
		onView(withRecyclerView(R.id.rvJobOffers)
				.atPositionOnView(1, R.id.tvJobScore))
				.check(matches(withText("Score: 11437.6")));

		onView(withRecyclerView(R.id.rvJobOffers)
				.atPositionOnView(2, R.id.tvTitle))
				.check(matches(withText("Manager")));
		onView(withRecyclerView(R.id.rvJobOffers)
				.atPositionOnView(2, R.id.tvCompany))
				.check(matches(withText("Company CCC")));
		onView(withRecyclerView(R.id.rvJobOffers)
				.atPositionOnView(2, R.id.tvJobScore))
				.check(matches(withText("Score: 10837.3")));

	}

	@Test
	public void testEditCurrentJobFromRankedList() {

		onView(withRecyclerView(R.id.rvJobOffers)
				.atPositionOnView(0, R.id.tvTitle))
				.perform(click());
		onView(withId(R.id.tvTitle)).check(matches(isDisplayed()));
	}

	@Test
	public void testEditJobOfferFromRankedList() {

		onView(withRecyclerView(R.id.rvJobOffers)
				.atPositionOnView(1, R.id.tvTitle))
				.perform(click());
		onView(withId(R.id.tvTitle)).check(matches(isDisplayed()));
	}

	@Test
	public void addJobAndCompareToCurrentJobSaved() {
		removeAllJobs();
		loadJobs();
		ActivityScenario<MainActivity> jobOfferCompareScenario
				= ActivityScenario.launch(MainActivity.class);

		onView(withId(R.id.btnJobOffer)).perform(click());

		String title = "Lead Analyst";
		String company = "Company BBB";
		String location = "San Francisco, CA";

		replaceTextHelper(R.id.etTitle, title);
		replaceTextHelper(R.id.etCompany, company);
		replaceTextHelper(R.id.etLocation, location);
		replaceTextHelper(R.id.etCostIndex, Integer.toString(204));
		replaceTextHelper(R.id.etYearlySalary, Integer.toString(130000));
		replaceTextHelper(R.id.etYearlyBonus, Integer.toString(40000));
		replaceTextHelper(R.id.etRsua, Integer.toString(800));
		replaceTextHelper(R.id.etRelocStipend, Integer.toString(12000));
		replaceTextHelper(R.id.etPcHolidays, Integer.toString(15));
		onView(withId(R.id.action_save)).perform(click());
		onView(withText("Compare with current job")).perform(click());
		onView(withId(R.id.rvJobOffers)).check(matches(isDisplayed()));
	}

	@Ignore
	@Test
	public void addJobAndCompareToCurrentJobWithNoCurrentJobSaved() {
		removeAllJobs();
		ActivityScenario<MainActivity> jobOfferCompareScenario
				= ActivityScenario.launch(MainActivity.class);

		onView(withId(R.id.btnJobOffer)).perform(click());

		String title = "Lead Analyst";
		String company = "Company BBB";
		String location = "San Francisco, CA";

		replaceTextHelper(R.id.etTitle, title);
		replaceTextHelper(R.id.etCompany, company);
		replaceTextHelper(R.id.etLocation, location);
		replaceTextHelper(R.id.etCostIndex, Integer.toString(204));
		replaceTextHelper(R.id.etYearlySalary, Integer.toString(130000));
		replaceTextHelper(R.id.etYearlyBonus, Integer.toString(40000));
		replaceTextHelper(R.id.etRsua, Integer.toString(800));
		replaceTextHelper(R.id.etRelocStipend, Integer.toString(12000));
		replaceTextHelper(R.id.etPcHolidays, Integer.toString(15));
		onView(withId(R.id.action_save)).perform(click());
		onView(withText("Compare with current job")).perform(click());
		onView(withText("Add current job first for comparison"))
				.inRoot(new ToastMatcher())
				.check(matches(isDisplayed()));
	}
}
