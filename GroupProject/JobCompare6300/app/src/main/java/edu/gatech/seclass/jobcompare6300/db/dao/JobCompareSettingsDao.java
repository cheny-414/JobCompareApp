package edu.gatech.seclass.jobcompare6300.db.dao;

import com.j256.ormlite.dao.Dao;

import edu.gatech.seclass.jobcompare6300.db.JobCompareSettings;

/**
 * Data access object for {@link JobCompareSettings}.
 */
public interface JobCompareSettingsDao extends Dao<JobCompareSettings, Integer> {

	/**
	 * @return the job compare settings {@link JobCompareSettings}
	 */
	JobCompareSettings loadCompareSettings();

}
