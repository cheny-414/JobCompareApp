package edu.gatech.seclass.jobcompare6300;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import org.slf4j.LoggerFactory;

import java.io.Console;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;


import edu.gatech.seclass.jobcompare6300.db.JobEntity;
import edu.gatech.seclass.jobcompare6300.ui.MainActivity;

public class ClearJobsTest {

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());

    @Rule
    public ActivityScenarioRule<MainActivity> tActivityRule = new ActivityScenarioRule<>(
            MainActivity.class);

    private void replaceTextHelper(int viewId, String stringToBeSet) {
        // to reduce flaky test, https://stackoverflow.com/a/53430379/1326678
        onView(withId(viewId)).perform(clearText(), replaceText(stringToBeSet), closeSoftKeyboard());
    }


    @Test
    public void removeAllJobs(){
        List<JobEntity> jobList = new ArrayList<>(ApplicationController.getInstance().getJobs());
        logger.info("Number of Jobs: " + jobList.size());
        for (JobEntity jobEntity : jobList) {
            ApplicationController.getInstance().removeJob(jobEntity);
        }

        List<JobEntity> emptyJobList = ApplicationController.getInstance().getJobs();
        Assert.assertTrue(emptyJobList.isEmpty());

    }

}
