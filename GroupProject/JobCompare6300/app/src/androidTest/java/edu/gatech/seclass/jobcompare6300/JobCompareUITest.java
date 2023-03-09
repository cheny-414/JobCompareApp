package edu.gatech.seclass.jobcompare6300;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import edu.gatech.seclass.jobcompare6300.ui.JobActivity;
import edu.gatech.seclass.jobcompare6300.ui.JobCompareSettingsActivity;

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
import static edu.gatech.seclass.jobcompare6300.TestUtilities.loadDefaultWeight;
import static edu.gatech.seclass.jobcompare6300.TestUtilities.setValue;
import static edu.gatech.seclass.jobcompare6300.TestUtilities.withValue;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class JobCompareUITest {

	@Rule
	public ActivityScenarioRule<JobCompareSettingsActivity> activityScenarioRule
			= new ActivityScenarioRule<>(JobCompareSettingsActivity.class);


	@Test
	public void testCompareSettingsSave() {
		onView(withId(R.id.action_save)).perform(click());
	}

	@Test
	public void testSlider() {

//		update value and check
		loadDefaultWeight();
		onView(withId(R.id.slYearlySalaryWeight)).check(matches(withValue(1)));
		onView(withId(R.id.slYearlySalaryWeight)).perform(setValue(2));
		onView(withId(R.id.slYearlySalaryWeight)).check(matches(withValue(2)));

		onView(withId(R.id.slYearlyBonusWeight)).check(matches(withValue(1)));
		onView(withId(R.id.slYearlyBonusWeight)).perform(setValue(3));
		onView(withId(R.id.slYearlyBonusWeight)).check(matches(withValue(3)));


		onView(withId(R.id.slRsuaWeight)).check(matches(withValue(1)));
		onView(withId(R.id.slRsuaWeight)).perform(setValue(4));
		onView(withId(R.id.slRsuaWeight)).check(matches(withValue(4)));

		onView(withId(R.id.slRelocStipendWeight)).check(matches(withValue(1)));
		onView(withId(R.id.slRelocStipendWeight)).perform(setValue(4));
		onView(withId(R.id.slRelocStipendWeight)).check(matches(withValue(4)));

		onView(withId(R.id.slPcHolidayWeight)).check(matches(withValue(1)));
		onView(withId(R.id.slPcHolidayWeight)).perform(setValue(5));
		onView(withId(R.id.slPcHolidayWeight)).check(matches(withValue(5)));
	}




}