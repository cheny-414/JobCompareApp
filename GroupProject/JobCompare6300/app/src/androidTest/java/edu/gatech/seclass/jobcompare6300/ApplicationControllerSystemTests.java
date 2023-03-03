package edu.gatech.seclass.jobcompare6300;

import android.content.Context;
import android.service.autofill.FieldClassification;

import androidx.core.widget.TextViewCompat;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import org.hamcrest.Matcher;
import android.os.Looper;

import org.hamcrest.Matchers;
import org.hamcrest.beans.HasPropertyWithValue;
import org.hamcrest.beans.SamePropertyValuesAs;
import org.hamcrest.collection.IsIn;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matcher.*;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasItemInArray;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.isIn;
import static org.junit.Assert.*;

import java.util.List;

import java.beans.*;

import edu.gatech.seclass.jobcompare6300.db.JobEntity;
import edu.gatech.seclass.jobcompare6300.ui.MainActivity;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import edu.gatech.seclass.jobcompare6300.ApplicationController;
import edu.gatech.seclass.jobcompare6300.R;
import edu.gatech.seclass.jobcompare6300.db.JobEntity;


@RunWith(AndroidJUnit4.class)
public class ApplicationControllerSystemTests {


    @Rule
    public ActivityScenarioRule<MainActivity> tActivityRule = new ActivityScenarioRule<>(
            MainActivity.class);

    private void replaceTextHelper(int viewId, String stringToBeSet) {
        // to reduce flaky test, https://stackoverflow.com/a/53430379/1326678
        onView(withId(viewId)).perform(clearText(), replaceText(stringToBeSet), closeSoftKeyboard());
    }

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
    public void addJobTest1() {
        JobEntity testJob = new JobEntity();
        testJob.setTitle("TestJob2");
        testJob.setCompany("TestCompany4545");
        testJob.setLocation("TestLocation");
        testJob.setCostIndex(5);
        testJob.setYearlySalary(111);
        testJob.setYearlyBonus(222);
        testJob.setRsua(333);
        testJob.setRelocStipend(444);
        testJob.setPcHolidays(555);
        testJob.setCurrentJob(false);
        ApplicationController.getInstance().addJob(testJob);
        List<JobEntity> testGetJobs = ApplicationController.getInstance().getJobs();
        Assert.assertTrue(testGetJobs.contains(testJob));
    }

    @Test
    public void addJobTestUI() {
        onView(withId(R.id.btnJobOffer)).perform(click());

        String title = "TestJob";
        String company = "TestCompany";
        String location = "TestLocation";

        JobEntity testJob = makeJobAndInputText(
                title, company, location,
                5, 111, 222, 333, 444, 45);
        onView(withId(R.id.action_save)).perform(click());
        onView(withText("Go to Main Menu")).perform(click());
        List<JobEntity> testGetJobs = ApplicationController.getInstance().getJobs();

        boolean test = false;
        for (JobEntity job : testGetJobs) {
            if (job.getTitle().equals(title) &&
                    job.getCompany().equals(company) &&
                    job.getLocation().equals(location)) {
                test = true;
            }
        }
        if(!test){fail();}
    }
}
