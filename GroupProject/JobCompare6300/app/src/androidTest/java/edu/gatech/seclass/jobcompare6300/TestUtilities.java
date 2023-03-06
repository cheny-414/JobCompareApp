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

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import edu.gatech.seclass.jobcompare6300.db.JobEntity;

/**
 * Created by dannyroa on 5/9/15.
 */
public class TestUtilities {

	private final org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());

	public static LinkedList<JobEntity> testJobList = new LinkedList<>();

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
}

