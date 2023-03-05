package edu.gatech.seclass.jobcompare6300.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.slider.Slider;

import org.slf4j.LoggerFactory;

import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import edu.gatech.seclass.jobcompare6300.ApplicationController;
import edu.gatech.seclass.jobcompare6300.R;
import edu.gatech.seclass.jobcompare6300.db.JobCompareSettings;

public class JobCompareSettingsActivity extends AppCompatActivity {
	private final ApplicationController instance = ApplicationController.getInstance();
	private Slider slYearlySalaryWeight, slYearlyBonusWeight, slRsuaWeight,
			slRelocStipendWeight, slPcHolidaysWeight;
	private final org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compare_settings);
		slYearlySalaryWeight = (Slider) findViewById(R.id.slYearlySalaryWeight);
		slYearlyBonusWeight = (Slider) findViewById(R.id.slYearlyBonusWeight);
		slRsuaWeight = (Slider) findViewById(R.id.slRsuaWeight);
		slRelocStipendWeight = (Slider) findViewById(R.id.slRelocStipendWeight);
		slPcHolidaysWeight = (Slider) findViewById(R.id.slPcHolidayWeight);
		String actionBarTitle = getIntent().getStringExtra(MainActivity.INTENT_ACTION_BAR_TITLE);
		if (actionBarTitle == null || actionBarTitle.isEmpty()) {
			actionBarTitle = getString(R.string.app_name);
		}
		Objects.requireNonNull(getSupportActionBar()).setTitle(actionBarTitle);
		JobCompareSettings compareSettings = instance.getJobCompareSettings();
		if (null != compareSettings) {
			updateJobCompareSettings(compareSettings);
		}
	}


	private void updateJobCompareSettings(JobCompareSettings compareSettings) {
		slYearlySalaryWeight.setValue(compareSettings.getYearlySalaryWeight());
		slYearlyBonusWeight.setValue(compareSettings.getYearlyBonusWeight());
		slRsuaWeight.setValue(compareSettings.getRsuaWeight());
		slRelocStipendWeight.setValue(compareSettings.getReloWeight());
		slPcHolidaysWeight.setValue(compareSettings.getPchWeight());
	}


	private JobCompareSettings buildCompareSettings() {
		JobCompareSettings compareSettings = (instance.getJobCompareSettings() == null) ? new JobCompareSettings() : instance.getJobCompareSettings();
		compareSettings.setYearlySalaryWeight((int) slYearlySalaryWeight.getValue());
		compareSettings.setYearlyBonusWeight((int) slYearlyBonusWeight.getValue());
		compareSettings.setRsuaWeight((int) slRsuaWeight.getValue());
		compareSettings.setReloWeight((int) slRelocStipendWeight.getValue());
		compareSettings.setPchWeight((int) slPcHolidaysWeight.getValue());
		return compareSettings;
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.save_cancel_menu, menu);
		return true;
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.action_save) {
			boolean settingsSaved = validateAndSaveSettings();
			if (settingsSaved) {
				Intent intent = new Intent(this, MainActivity.class);
				startActivity(intent);
			}
		}
		if (item.getItemId() == R.id.action_cancel) {
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
		}
		return (super.onOptionsItemSelected(item));
	}


	private boolean validateAndSaveSettings() {
		JobCompareSettings compareSettings = buildCompareSettings();
		if (instance.getJobCompareSettings() == null) {
			logger.info("Creating a new job compare settings..");
			int created = instance.createJobCompareSettingsInDb(compareSettings);
			if (created == 1) {
				Toast.makeText(this, "Created a job compare settings", Toast.LENGTH_LONG).show();
			}
		}
		else {
			logger.info("Updating a existing job compare settings..");
			int updated = instance.updateJobCompareSettingsInDb(compareSettings);
			if (updated == 1) {
				Toast.makeText(this, "Updated job compare settings", Toast.LENGTH_LONG).show();
			}
		}
		return true;
	}
}