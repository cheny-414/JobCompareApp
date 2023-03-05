package edu.gatech.seclass.jobcompare6300.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Objects;

@DatabaseTable
public class JobCompareSettings {
	@DatabaseField(generatedId = true)
	private int id;

	@DatabaseField(canBeNull = false, defaultValue = "1")
	private int yearlySalaryWeight = 1;

	@DatabaseField(canBeNull = false, defaultValue = "1")
	private int yearlyBonusWeight = 1;

	@DatabaseField(canBeNull = false, defaultValue = "1")
	private int rsuaWeight = 1;

	@DatabaseField(canBeNull = false, defaultValue = "1")
	private int reloWeight = 1;

	@DatabaseField(canBeNull = false, defaultValue = "1")
	private int pchWeight = 1;

	// --------------------------------------------------------------------------------------------|
	// -----[  Constructors  ]---------------------------------------------------------------------|
	// --------------------------------------------------------------------------------------------|


	public JobCompareSettings() {
		// no-arg constructor for ORMLite
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getYearlySalaryWeight() {
		return yearlySalaryWeight;
	}


	public void setYearlySalaryWeight(int yearlySalaryWeight) {
		this.yearlySalaryWeight = yearlySalaryWeight;
	}


	public int getYearlyBonusWeight() {
		return yearlyBonusWeight;
	}


	public void setYearlyBonusWeight(int yearlyBonusWeight) {
		this.yearlyBonusWeight = yearlyBonusWeight;
	}


	public int getRsuaWeight() {
		return rsuaWeight;
	}


	public void setRsuaWeight(int rsuaWeight) {
		this.rsuaWeight = rsuaWeight;
	}


	public int getReloWeight() {
		return reloWeight;
	}


	public void setReloWeight(int reloWeight) {
		this.reloWeight = reloWeight;
	}


	public int getPchWeight() {
		return pchWeight;
	}


	public void setPchWeight(int pchWeight) {
		this.pchWeight = pchWeight;
	}

	// --------------------------------------------------------------------------------------------|
	// -----[  Public Section  ]-------------------------------------------------------------------|
	// --------------------------------------------------------------------------------------------|


	@Override
	public boolean equals(Object other) {
		return other instanceof JobCompareSettings && getId() == ((JobCompareSettings) other).getId();
	}


	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

}
