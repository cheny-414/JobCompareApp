package edu.gatech.seclass.jobcompare6300;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.Map;
import java.util.Optional;

import edu.gatech.seclass.jobcompare6300.db.JobCompareSettings;
import edu.gatech.seclass.jobcompare6300.db.JobEntity;

public class JobComparatorTest {

    private JobEntity jobEntity1 = new JobEntity();
    private JobEntity jobEntity2 = new JobEntity();
    private JobCompareSettings jobCompareSettings = new JobCompareSettings();

    private JobComparator jobComparator;
    @Before
    public void setup() {
        jobEntity1 = new JobEntity();
        jobEntity1.setId(1);
        jobEntity1.setTitle("Senior Analyst");
        jobEntity1.setCompany("Company AAA");
        jobEntity1.setLocation("Philadelphia, PA");
        jobEntity1.setYearlySalary(100000);
        jobEntity1.setYearlyBonus(30000);
        jobEntity1.setCostIndex(167);
        jobEntity1.setYearlyAdjustedSalary(59880);
        jobEntity1.setYearlyAdjustedBonus(17964);
        jobEntity1.setRsua(900);
        jobEntity1.setRelocStipend(8000);
        jobEntity1.setPcHolidays(20);
        jobEntity1.setCurrentJob(true);


        jobEntity2 = new JobEntity();
        jobEntity2.setId(2);
        jobEntity2.setTitle("Lead Analyst");
        jobEntity2.setCompany("Company BBB");
        jobEntity2.setLocation("San Francisco, CA");
        jobEntity2.setYearlySalary(130000);
        jobEntity2.setYearlyBonus(40000);
        jobEntity2.setCostIndex(203);
        jobEntity2.setYearlyAdjustedSalary(64039);
        jobEntity2.setYearlyAdjustedBonus(19704);
        jobEntity2.setRsua(1000);
        jobEntity2.setRelocStipend(12000);
        jobEntity2.setPcHolidays(15);
        jobEntity2.setCurrentJob(false);


        jobCompareSettings = new JobCompareSettings();
        jobCompareSettings.setYearlySalaryWeight(3);
        jobCompareSettings.setYearlyBonusWeight(2);
        jobCompareSettings.setRsuaWeight(1);
        jobCompareSettings.setReloWeight(2);
        jobCompareSettings.setPchWeight(1);

        jobComparator = new JobComparator(jobEntity1, jobEntity2, jobCompareSettings);
    }





    @Test
    public void testCompareJobs() {
//        JobComparator comparator = new JobComparator(jobEntity1, jobEntity2, jobCompareSettings);
        Map<String, Integer> result = jobComparator.compareJobs();
        System.out.println(result);
        // Size of Results
        assertEquals(11, result.size());
        assertEquals(-1, result.get("Yearly Adjusted Salary"),0);
        assertEquals(-1, result.get("Yearly Adjusted Bonus"),0);
        assertEquals(-1, result.get("Restricted Stock Unit Award"),0);
        assertEquals(-1, result.get("Relocation stipend "),0);
        assertEquals(1, result.get("Personal Choice Holidays"),0);
    }

    @Test
    public void testgetJob1() {
        JobEntity job1 = jobComparator.getJob1();
        assertEquals(jobEntity1, job1);
    }

    @Test
    public void testgetJob2() {
        JobEntity job2 = jobComparator.getJob2();
        assertEquals(jobEntity2, job2);
    }

    @Test
    public void testCalculateJobScore() {

        //calculate score and compare
        float expectedJobScore = 26266.6f;
        float actualJobScore = JobComparator.calculateJobScore(jobEntity1, jobCompareSettings);
        assertEquals(expectedJobScore, actualJobScore, 0.1f);
    }
}