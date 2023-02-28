package edu.gatech.seclass.jobcompare6300.db.dao;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;

import org.slf4j.LoggerFactory;

import java.sql.SQLException;

import edu.gatech.seclass.jobcompare6300.db.DatabaseHelper;
import edu.gatech.seclass.jobcompare6300.db.JobCompareSettings;

public class JobCompareSettingsDaoImpl extends RuntimeExceptionDaoEx<JobCompareSettings, Integer> implements JobCompareSettingsDao {
	private static final org.slf4j.Logger log = LoggerFactory.getLogger(JobCompareSettingsDaoImpl.class);


	public JobCompareSettingsDaoImpl(DatabaseHelper helper, Dao<JobCompareSettings, Integer> dao) {
		super(helper, dao);
	}


	@Override public JobCompareSettings loadCompareSettings() {
		try {
			PreparedQuery<JobCompareSettings> q = queryBuilder().prepare();
			return queryForFirst(q);
		}
		catch (SQLException e) {
			log.error("Error executing query", e);
			throw new RuntimeException(e);
		}
	}
}
