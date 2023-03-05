package edu.gatech.seclass.jobcompare6300;

import android.app.Activity;
import android.view.View;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import edu.gatech.seclass.jobcompare6300.db.JobEntity;
import edu.gatech.seclass.jobcompare6300.ui.JobActivity;
import edu.gatech.seclass.jobcompare6300.ui.MainActivity;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.fail;

public class ApplicationControllerUnitTest {

    public void removeAllJobs(){
        List<JobEntity> jobList = new ArrayList<>(ApplicationController.getInstance().getJobs());
        for (JobEntity jobEntity : jobList) {
            ApplicationController.getInstance().removeJob(jobEntity);
        }

        List<JobEntity> emptyJobList = ApplicationController.getInstance().getJobs();
        Assert.assertTrue(emptyJobList.isEmpty());

    }

    @Test
    public void getJobsTest(){

        removeAllJobs();

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
        ApplicationController.getInstance().addJob(jobOffer1);

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
        ApplicationController.getInstance().addJob(jobOffer2);

        List<JobEntity> testGetJobs = ApplicationController.getInstance().getJobs();

        boolean test1 = false;
        boolean test2 = false;
        for (JobEntity job : testGetJobs) {
            if (job.getTitle().equals("Lead Analyst") &&
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
        if (!test1 && !test2) {fail();}
    }
}
