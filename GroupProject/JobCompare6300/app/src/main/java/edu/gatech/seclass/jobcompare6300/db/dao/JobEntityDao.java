package edu.gatech.seclass.jobcompare6300.db.dao;

import com.j256.ormlite.dao.Dao;

import java.util.List;

import edu.gatech.seclass.jobcompare6300.db.JobEntity;

/**
 * Data access object for {@link JobEntity}.
 */
public interface JobEntityDao extends Dao<JobEntity, Integer> {
	/**
	 * @return A list of {@link JobEntity}s
	 */
	List<JobEntity> loadJobOffers();

}
