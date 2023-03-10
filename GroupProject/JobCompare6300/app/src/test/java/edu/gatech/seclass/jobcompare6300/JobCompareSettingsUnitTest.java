package edu.gatech.seclass.jobcompare6300.db;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class JobCompareSettingsUnitTest {

    private JobCompareSettings jobCompareSettings = new JobCompareSettings();
    private JobCompareSettings jobCompareSettings2 = new JobCompareSettings();

    @Before
    public void setup() {
        jobCompareSettings2 = new JobCompareSettings();
        jobCompareSettings2.setYearlySalaryWeight(3);
        jobCompareSettings2.setYearlyBonusWeight(2);
        jobCompareSettings2.setRsuaWeight(1);
        jobCompareSettings2.setReloWeight(2);
        jobCompareSettings2.setPchWeight(1);
    }


    @Test
    public void testSettingsDefaultValue() {

        assertEquals(1, jobCompareSettings.getYearlySalaryWeight());
        assertEquals(1, jobCompareSettings.getYearlyBonusWeight());
        assertEquals(1, jobCompareSettings.getRsuaWeight());
        assertEquals(1, jobCompareSettings.getReloWeight());
        assertEquals(1, jobCompareSettings.getPchWeight());

    }

    @Test
    public void testSettingsUpdatedValue() {

        assertEquals(3, jobCompareSettings2.getYearlySalaryWeight());
        assertEquals(2, jobCompareSettings2.getYearlyBonusWeight());
        assertEquals(1, jobCompareSettings2.getRsuaWeight());
        assertEquals(2, jobCompareSettings2.getReloWeight());
        assertEquals(1, jobCompareSettings2.getPchWeight());

    }
}