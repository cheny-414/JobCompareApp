package edu.gatech.seclass.jobcompare6300.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Objects;

@DatabaseTable
public class JobEntity {
	@DatabaseField(generatedId = true)
	private int id;

	@DatabaseField(canBeNull = false)
	private String title;

	@DatabaseField(canBeNull = false)
	private String company;

	@DatabaseField(canBeNull = false)
	private String location;

	@DatabaseField(canBeNull = false)
	private int costIndex;

	@DatabaseField(canBeNull = false)
	private float yearlySalary;

	@DatabaseField(canBeNull = false)
	private float yearlyBonus;

	@DatabaseField(canBeNull = false)
	private float rsua;

	@DatabaseField(canBeNull = false)
	private float relocStipend;

	@DatabaseField(canBeNull = false)
	private int pcHolidays;

	@DatabaseField
	private boolean isCurrentJob;

	@DatabaseField
	private float yearlyAdjustedSalary;

	@DatabaseField
	private float yearlyAdjustedBonus;

	@DatabaseField
	private float jobScore;

	// --------------------------------------------------------------------------------------------|
	// -----[  Constructors  ]---------------------------------------------------------------------|
	// --------------------------------------------------------------------------------------------|


	public JobEntity() {
		// no-arg constructor for ORMLite
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getCompany() {
		return company;
	}


	public void setCompany(String company) {
		this.company = company;
	}


	public String getLocation() {
		return location;
	}


	public void setLocation(String location) {
		this.location = location;
	}


	public int getCostIndex() {
		return costIndex;
	}


	public void setCostIndex(int costIndex) {
		this.costIndex = costIndex;
	}


	public float getYearlySalary() {
		return yearlySalary;
	}


	public void setYearlySalary(float yearlySalary) {
		this.yearlySalary = yearlySalary;
	}


	public float getYearlyBonus() {
		return yearlyBonus;
	}


	public void setYearlyBonus(float yearlyBonus) {
		this.yearlyBonus = yearlyBonus;
	}


	public float getRsua() {
		return rsua;
	}


	public void setRsua(float rsua) {
		this.rsua = rsua;
	}


	public float getRelocStipend() {
		return relocStipend;
	}


	public void setRelocStipend(float relocStipend) {
		this.relocStipend = relocStipend;
	}


	public int getPcHolidays() {
		return pcHolidays;
	}


	public void setPcHolidays(int pcHolidays) {
		this.pcHolidays = pcHolidays;
	}


	public boolean isCurrentJob() {
		return isCurrentJob;
	}

	public String isCurrentJobString() {
		if(isCurrentJob == false){
			return "N";
		}
		else{
			return "Y";
		}

	} //Feng added for current job indicator in the comparison results

	public void setCurrentJob(boolean currentJob) {
		isCurrentJob = currentJob;
	}


	public float getYearlyAdjustedSalary() {
		return yearlyAdjustedSalary;
	}


	public void setYearlyAdjustedSalary(float yearlyAdjustedSalary) {
		this.yearlyAdjustedSalary = yearlyAdjustedSalary;
	}


	public float getYearlyAdjustedBonus() {
		return yearlyAdjustedBonus;
	}


	public void setYearlyAdjustedBonus(float yearlyAdjustedBonus) {
		this.yearlyAdjustedBonus = yearlyAdjustedBonus;
	}


	public float getJobScore() {
		return jobScore;
	}


	public void setJobScore(float jobScore) {
		this.jobScore = jobScore;
	}


	@Override
	public boolean equals(Object other) {
		return other instanceof JobEntity && getId() == ((JobEntity) other).getId();
	}


	@Override
	public int hashCode() {
		return Objects.hash(id);
	}


	@Override
	public String toString() {
		return "JobOffer{" +
				"title=" + title +
				", Company=" + company +
				", Score=" + jobScore +
				'}';
	}
}
