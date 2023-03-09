package edu.gatech.seclass.jobcompare6300;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.PerformException;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.espresso.util.HumanReadables;

import com.google.android.material.slider.Slider;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import edu.gatech.seclass.jobcompare6300.db.JobCompareSettings;
import edu.gatech.seclass.jobcompare6300.db.JobEntity;

/**
 * Created by dannyroa on 5/9/15.
 */
public class TestUtilities {

	private final org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());

	public static LinkedList<JobEntity> testJobList = new LinkedList<>();


//	Author: https://stackoverflow.com/questions/28742495/testing-background-color-espresso-android
	public static Matcher<View> matchesBackgroundColor(final int expectedResourceId) {
		return new BoundedMatcher<View, View>(View.class) {
			int actualColor;
			int expectedColor;
			String message;

			@Override
			protected boolean matchesSafely(View item) {
				if (item.getBackground() == null) {
					message = item.getId() + " does not have a background";
					return false;
				}
				Resources resources = item.getContext().getResources();
				expectedColor = ResourcesCompat.getColor(resources, expectedResourceId, null);

				actualColor = ((ColorDrawable) item.getBackground()).getColor();

				return actualColor == expectedColor;
			}
			@Override
			public void describeTo(final Description description) {
				if (actualColor != 0) { message = "Background color did not match: Expected "
						+  String.format("#%06X", (0xFFFFFF & expectedColor))
						+ " was " + String.format("#%06X", (0xFFFFFF & actualColor));
				}
				description.appendText(message);
			}
		};
	}
	public static void replaceTextHelper(int viewId, String stringToBeSet) {
		// to reduce flaky test, https://stackoverflow.com/a/53430379/1326678
		onView(withId(viewId)).perform(clearText(), replaceText(stringToBeSet), closeSoftKeyboard());
	}

	public static void removeAllJobs(){
		List<JobEntity> jobList = new ArrayList<>(ApplicationController.getInstance().getJobs());
		for (JobEntity jobEntity : jobList) {
			ApplicationController.getInstance().removeJob(jobEntity);
		}

		List<JobEntity> emptyJobList = ApplicationController.getInstance().getJobs();
		Assert.assertTrue(emptyJobList.isEmpty());

	}

	public static void loadJobs() {
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

		testJobList.add(jobOffer0);
		testJobList.add(jobOffer1);
		testJobList.add(jobOffer2);
	}
	public static void loadDefaultWeight(){

		JobCompareSettings compareSettings = ApplicationController.getInstance().getJobCompareSettings();
		compareSettings.setYearlySalaryWeight(1);
		compareSettings.setYearlyBonusWeight(1);
		compareSettings.setRsuaWeight(1);
		compareSettings.setReloWeight(1);
		compareSettings.setPchWeight(1);

		ApplicationController.getInstance().updateJobCompareSettingsInDb(compareSettings);

	}

	public static void updateWeight(){
		JobCompareSettings compareSettings = ApplicationController.getInstance().getJobCompareSettings();
		compareSettings.setYearlySalaryWeight(1);
		compareSettings.setYearlyBonusWeight(1);
		compareSettings.setRsuaWeight(5);
		compareSettings.setReloWeight(5);
		compareSettings.setPchWeight(1);

		ApplicationController.getInstance().updateJobCompareSettingsInDb(compareSettings);

	}

	//	Methods to test sliders
	//	Adapted from https://stackoverflow.com/questions/65390086/androidx-how-to-test-slider-in-ui-tests-espresso
	public static Matcher<View> withValue(final float expectedValue) {
		return new BoundedMatcher<View, Slider>(Slider.class) {
			@Override
			public void describeTo(Description description) {
				description.appendText("expected: " + expectedValue);
			}

			@Override
			protected boolean matchesSafely(Slider slider) {
				return slider.getValue() == expectedValue;
			}
		};
	}
	public static ViewAction setValue(final float value) {
		return new ViewAction() {
			@Override
			public String getDescription() {
				return "Set Slider value to " + value;
			}

			@Override
			public Matcher<View> getConstraints() {
				return ViewMatchers.isAssignableFrom(Slider.class);
			}

			@Override
			public void perform(UiController uiController, View view) {
				Slider slider = (Slider) view;
				slider.setValue(value);
			}
		};
	}
	//end of methods for slider testing

}

