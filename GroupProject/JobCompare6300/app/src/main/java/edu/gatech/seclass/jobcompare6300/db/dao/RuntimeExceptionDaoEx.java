package edu.gatech.seclass.jobcompare6300.db.dao;

import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import edu.gatech.seclass.jobcompare6300.db.DatabaseHelper;

public class RuntimeExceptionDaoEx<T, ID> extends RuntimeExceptionDao<T, ID> implements Dao<T, ID> {
	protected DatabaseHelper helper;
	
	public RuntimeExceptionDaoEx(DatabaseHelper helper, Dao<T, ID> dao) {
		super(dao);
		this.helper = helper;
	}

	public DatabaseHelper getHelper() {
		return helper;
	}

	@Override
	public CloseableIterator<T> closeableIterator() {
		return iterator();
	}
	
}
