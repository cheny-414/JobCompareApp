package edu.gatech.seclass.jobcompare6300;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import edu.gatech.seclass.jobcompare6300.db.JobCompareSettings;
import edu.gatech.seclass.jobcompare6300.db.JobEntity;

/**
 * Created by Puneeth Reddy on 2/26/2023.
 Initial version
 */

public class JobComparator {
	public static final String ATTRIBUTE = "Attribute";
	public static final String JOB_1 = "Job 1";
	public static final String JOB_2 = "Job 2";
	public static final String TITLE = "Title";
	public static final String COMPANY = "Company";
	public static final String LOCATION = "Location";
	public static final String YEARLY_ADJUSTED_SALARY = "Yearly Adjusted Salary";
	public static final String YEARLY_ADJUSTED_BONUS = "Yearly Adjusted Bonus";
	public static final String RSUA = "Restricted Stock Unit Award";
	public static final String RELOC_STIPEND = "Relocation stipend ";
	public static final String PC_HOLIDAYS = "Personal Choice Holidays";
	public static final String JOB_SCORE = "Job Score";
	private final JobEntity job1;
	private final JobEntity job2;
	private final JobCompareSettings jobCompareSettings;


	public JobComparator(JobEntity job1, JobEntity job2, JobCompareSettings jobCompareSettings) {
		this.job1 = job1;
		this.job2 = job2;
		this.jobCompareSettings = jobCompareSettings;
	}


	public Map<String, Integer> compareJobs() {
		Map<String, Integer> comparisonResult = new LinkedHashMap<>();
		comparisonResult.put(ATTRIBUTE, 0);
		comparisonResult.put(TITLE, 0);
		comparisonResult.put(COMPANY, 0);
		comparisonResult.put(LOCATION, 0);
		comparisonResult.put(YEARLY_ADJUSTED_SALARY, Float.compare((job1.getYearlySalary() - job1.getYearlyAdjustedSalary())
				, (job2.getYearlySalary() - job2.getYearlyAdjustedSalary())));
		comparisonResult.put(YEARLY_ADJUSTED_BONUS, Float.compare((job1.getYearlyBonus() - job1.getYearlyAdjustedBonus()),
				(job2.getYearlyBonus() - job2.getYearlyAdjustedBonus())));
		comparisonResult.put(RSUA, Float.compare(job1.getRsua(), job2.getRsua()));
		comparisonResult.put(RELOC_STIPEND, Float.compare(job1.getRelocStipend(), job2.getRelocStipend()));
		comparisonResult.put(PC_HOLIDAYS, Integer.compare(job1.getPcHolidays(), job2.getPcHolidays()));
		comparisonResult.put(JOB_SCORE, Float.compare(calculateJobScore(this.job1, this.jobCompareSettings),
				calculateJobScore(this.job2, this.jobCompareSettings)));
		return comparisonResult;
	}


	public JobEntity getJob1() {
		return job1;
	}


	public JobEntity getJob2() {
		return job2;
	}


	public static float calculateJobScore(JobEntity jobEntity, JobCompareSettings compareSettings) {
		int salaryWeight = compareSettings.getYearlySalaryWeight();
		int bonusWeight = compareSettings.getYearlyBonusWeight();
		int stockAwardWeight = compareSettings.getRsuaWeight();
		int stipendWeight = compareSettings.getReloWeight();
		int holidayCountWeight = compareSettings.getPchWeight();
		float totalWeight = (salaryWeight + bonusWeight + stockAwardWeight + stipendWeight + holidayCountWeight) * 1.0f;
		return (salaryWeight / totalWeight) * jobEntity.getYearlyAdjustedSalary()
				+ (bonusWeight / totalWeight) * jobEntity.getYearlyAdjustedBonus()
				+ (stockAwardWeight / totalWeight) * (jobEntity.getRsua() / 4)
				+ (stipendWeight / totalWeight) * jobEntity.getRelocStipend()
				+ (holidayCountWeight / totalWeight) * (jobEntity.getPcHolidays() * jobEntity.getYearlyAdjustedSalary() / 260);
	}
}
