package edu.gatech.seclass.jobcompare6300;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import edu.gatech.seclass.jobcompare6300.ui.JobActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

import static edu.gatech.seclass.jobcompare6300.TestUtilities.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class JobOfferUITest {

	@Rule public ActivityScenarioRule<JobActivity> activityScenarioRule
			= new ActivityScenarioRule<>(JobActivity.class);

	@Test
	public void testAddJobFields() {
		replaceTextHelper(R.id.etTitle, "My Title");
		replaceTextHelper(R.id.etCompany, "My Company");
		replaceTextHelper(R.id.etLocation, "My Title");

		replaceTextHelper(R.id.etCostIndex, "50");
		replaceTextHelper(R.id.etYearlySalary, "1000");
		replaceTextHelper(R.id.etYearlyBonus, "1000");

		replaceTextHelper(R.id.etRsua, "500");
		replaceTextHelper(R.id.etRelocStipend, "500");
		replaceTextHelper(R.id.etPcHolidays, "10");

		onView(withId(R.id.action_save)).perform(click());

		onView(withText("Select Options"))
				.inRoot(isDialog()) // <---
				.check(matches(isDisplayed()));
		//
		//		//Verify Toast is Displayed After clicking the Plant List Fragment
		//		onView(withText("Welcome For The Visit"))
		//				.inRoot(withDecorView(Matchers.not(decorView)))// Here we use decorView
		//				.check(matches(isDisplayed()));
	}


	@Test
	public void testAddJobFieldErrors() {

		replaceTextHelper(R.id.etTitle, "");
		replaceTextHelper(R.id.etCompany, "");
		replaceTextHelper(R.id.etLocation, "Philadelphia, PA");
		replaceTextHelper(R.id.etCostIndex, Integer.toString(167));
		replaceTextHelper(R.id.etYearlySalary, Integer.toString(100000));
		replaceTextHelper(R.id.etYearlyBonus, Integer.toString(30000));
		replaceTextHelper(R.id.etRsua, Integer.toString(900));
		replaceTextHelper(R.id.etRelocStipend, Integer.toString(8000));
//		replaceTextHelper(R.id.etRelocStipend, "50000");
		replaceTextHelper(R.id.etPcHolidays, Integer.toString(20));
//		replaceTextHelper(R.id.etPcHolidays, "50");

		onView(withId(R.id.action_save)).perform(click());
		onView(withId(R.id.etTitle)).check(matches(hasErrorText("Job title cannot be empty")));
		onView(withId(R.id.etCompany)).check(matches(hasErrorText("Company name cannot be empty")));
//		onView(withId(R.id.etRelocStipend)).check(matches(hasErrorText("Relocation stipend cannot be less than 0$ or greater than 25000$")));
//		onView(withId(R.id.etPcHolidays)).check(matches(hasErrorText("Personal Choice Holidays cannot be less than 0 or greater than 20")));
	}

}