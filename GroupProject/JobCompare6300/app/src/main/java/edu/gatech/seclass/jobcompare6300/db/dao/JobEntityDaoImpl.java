package edu.gatech.seclass.jobcompare6300.db.dao;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;

import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

import edu.gatech.seclass.jobcompare6300.db.DatabaseHelper;
import edu.gatech.seclass.jobcompare6300.db.JobEntity;

public class JobEntityDaoImpl extends RuntimeExceptionDaoEx<JobEntity, Integer> implements JobEntityDao {
	private static final org.slf4j.Logger log = LoggerFactory.getLogger(JobEntityDaoImpl.class);


	public JobEntityDaoImpl(DatabaseHelper helper, Dao<JobEntity, Integer> dao) {
		super(helper, dao);
	}


	@Override public List<JobEntity> loadJobOffers() {
		try {
			PreparedQuery<JobEntity> q = queryBuilder().prepare();
			return query(q);
		}
		catch (SQLException e) {
			log.error("Error executing query", e);
			throw new RuntimeException(e);
		}
	}
}
