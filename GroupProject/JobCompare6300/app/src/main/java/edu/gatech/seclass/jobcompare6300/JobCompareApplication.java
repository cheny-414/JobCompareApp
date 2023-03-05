package edu.gatech.seclass.jobcompare6300;

import android.app.Application;
import android.content.Intent;

import org.slf4j.LoggerFactory;

import java.sql.SQLException;

import edu.gatech.seclass.jobcompare6300.db.DatabaseManager;
import edu.gatech.seclass.jobcompare6300.db.JobCompareSettings;
import edu.gatech.seclass.jobcompare6300.db.dao.JobCompareSettingsDao;
import edu.gatech.seclass.jobcompare6300.ui.MainActivity;

public class JobCompareApplication extends Application {

	private final org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());


	@Override
	public void onCreate() {
		super.onCreate();
		DatabaseManager.init(this);
		createDefaultCompareSettings();
		Thread.setDefaultUncaughtExceptionHandler(new DefaultExceptionHandler(logger, BuildConfig.DEBUG) {
			@Override
			public void uncaughtException(Thread paramThread, Throwable paramThrowable) {
				super.uncaughtException(paramThread, paramThrowable);
				Intent crashedIntent = new Intent(JobCompareApplication.this, MainActivity.class);
				crashedIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(crashedIntent);
				System.exit(1);
			}
		});
	}


	private void createDefaultCompareSettings() {
		JobCompareSettingsDao jobCompareSettingsDao = DatabaseManager.getInstance().getDb().getJobCompareSettingsDao();
		JobCompareSettings compareSettings = jobCompareSettingsDao.loadCompareSettings();
		if (compareSettings == null) {
			compareSettings = new JobCompareSettings();
			compareSettings.setYearlySalaryWeight(1);
			compareSettings.setYearlyBonusWeight(1);
			compareSettings.setRsuaWeight(1);
			compareSettings.setReloWeight(1);
			compareSettings.setPchWeight(1);
			try {
				jobCompareSettingsDao.create(compareSettings);
			}
			catch (SQLException e) {
				logger.error(e.getMessage());
			}
		}
	}
}
