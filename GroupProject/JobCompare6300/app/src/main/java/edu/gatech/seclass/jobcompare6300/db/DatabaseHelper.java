/*
 * Copyright (c) 2014-2022 Gutermann Technology GmbH. All rights reserved.
 */
package edu.gatech.seclass.jobcompare6300.db;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import org.slf4j.LoggerFactory;

import java.sql.SQLException;

import edu.gatech.seclass.jobcompare6300.db.dao.JobCompareSettingsDao;
import edu.gatech.seclass.jobcompare6300.db.dao.JobCompareSettingsDaoImpl;
import edu.gatech.seclass.jobcompare6300.db.dao.JobEntityDao;
import edu.gatech.seclass.jobcompare6300.db.dao.JobEntityDaoImpl;

/**
 * The main access class for database related operations.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

	private static final org.slf4j.Logger log = LoggerFactory.getLogger(DatabaseHelper.class);

	// The current version of the database. Increase this when you add or change database objects.
	private static final int DATABASE_VERSION = 1;

	// The data access objects that we use for updating and retrieving database objects.
	private JobEntityDao jobEntityDao;
	private JobCompareSettingsDao jobCompareSettingsDao;


	public DatabaseHelper(Context context) {
		super(context, getDataBaseName(context), null, DATABASE_VERSION);
		log.info("Connecting to database {} using schema version {}", getDataBaseName(context), DATABASE_VERSION);
	}


	@Override
	public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
		try {
			// Create the tables for each database object here.
			log.debug("Creating database schema");
			createTablesIfNotExist();
		}
		catch (SQLException e) {
			log.error("Database creation failed", e);
			throw new RuntimeException(e);
		}
	}


	@Override
	public void onOpen(SQLiteDatabase db) {
		super.onOpen(db);
		db.disableWriteAheadLogging();
		if (!db.isReadOnly()) {
			// Enable foreign key constraints (including cascading)
			db.execSQL("PRAGMA foreign_keys=ON;");
		}
	}


	@Override
	public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
		//Upgrade the database schema here for a particular version.
		log.debug("Upgrading database schema from version {} to {}", oldVersion, newVersion);
	}


	public static String getDataBaseName(Context context) {
		return getAppName(context) + ".db";
	}


	private static String getAppName(Context context) {
		String appName = "";
		try {
			PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			CharSequence applicationLabel = context.getPackageManager().getApplicationLabel(packageInfo.applicationInfo);
			appName = applicationLabel != null ? applicationLabel.toString() : "(unknown)";
		}
		catch (Resources.NotFoundException | PackageManager.NameNotFoundException e) {
			log.error(e.getMessage());
		}
		return appName;
	}


	/**
	 * Creates the tables in the database. Used for testing.
	 *
	 * @throws SQLException
	 */
	public void createTablesIfNotExist() throws SQLException {
		// Add any new table here.
		TableUtils.createTableIfNotExists(connectionSource, JobEntity.class);
		TableUtils.createTableIfNotExists(connectionSource, JobCompareSettings.class);
	}


	/**
	 * @return the {@link JobCompareSettingsDao} object.
	 */
	public JobCompareSettingsDao getJobCompareSettingsDao() {
		if (jobCompareSettingsDao == null)
			jobCompareSettingsDao = new JobCompareSettingsDaoImpl(this, createDao(JobCompareSettings.class, Integer.class));
		return jobCompareSettingsDao;
	}


	/**
	 * @return the {@link JobEntityDao} object.
	 */
	public JobEntityDao getJobEntityDao() {
		if (jobEntityDao == null)
			jobEntityDao = new JobEntityDaoImpl(this, createDao(JobEntity.class, Integer.class));
		return jobEntityDao;
	}


	private <T, ID> Dao<T, ID> createDao(Class<T> dataClass, Class<ID> idClass) {
		try {
			Dao<T, ID> dao = getDao(dataClass);
			dao.setObjectCache(true);
			return dao;
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
