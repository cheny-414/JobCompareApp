package edu.gatech.seclass.jobcompare6300.db;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;

public class DatabaseManager {
	private static DatabaseManager instance;
	
	public static void init(Context context) {
		if (instance == null)
			instance = new DatabaseManager(context);
	}
	
	public static void destroy() {
		OpenHelperManager.releaseHelper();
		instance = null;
	}
	
	public static DatabaseManager getInstance() {
		return instance;
	}
	
	private DatabaseHelper db;
	
	private DatabaseManager(Context context) {
		db = OpenHelperManager.getHelper(context, DatabaseHelper.class);
	}
	
	public DatabaseHelper getDb() {
		return db;
	}
	
}
