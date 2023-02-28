package edu.gatech.seclass.jobcompare6300.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import org.slf4j.LoggerFactory;

import java.util.Objects;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import edu.gatech.seclass.jobcompare6300.ApplicationController;
import edu.gatech.seclass.jobcompare6300.R;
import edu.gatech.seclass.jobcompare6300.db.JobEntity;

public class JobActivity extends AppCompatActivity {
	public static final String INTENT_IS_CURRENT_JOB = "isCurrentJob";
	public static final String INTENT_JOB_ID = "jobId";
	private final org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());
	private final ApplicationController instance = ApplicationController.getInstance();
	private EditText etTitle, etCompany, etLocation, etCostIndex, etYearlySalary,
			etYearlyBonus, etRsua, etRelocStipend, etPcholidays;
	private boolean isCurrentJob;
	private int jobId;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_job);
		Intent intent = getIntent();
		isCurrentJob = intent.getBooleanExtra(INTENT_IS_CURRENT_JOB, false);
		jobId = intent.getIntExtra(INTENT_JOB_ID, -1);
		String actionBarTitle = intent.getStringExtra(MainActivity.INTENT_ACTION_BAR_TITLE);
		if (actionBarTitle == null || actionBarTitle.isEmpty()) {
			actionBarTitle = getString(R.string.app_name);
		}
		Objects.requireNonNull(getSupportActionBar()).setTitle(actionBarTitle);
		etTitle = (EditText) findViewById(R.id.etTitle);
		etCompany = (EditText) findViewById(R.id.etCompany);
		etLocation = (EditText) findViewById(R.id.etLocation);
		etCostIndex = (EditText) findViewById(R.id.etCostIndex);
		etYearlySalary = (EditText) findViewById(R.id.etYearlySalary);
		etYearlyBonus = (EditText) findViewById(R.id.etYearlyBonus);
		etRsua = (EditText) findViewById(R.id.etRsua);
		etRelocStipend = (EditText) findViewById(R.id.etRelocStipend);
		etPcholidays = (EditText) findViewById(R.id.etPcHolidays);
		JobEntity jobEntity = instance.getJobById(jobId);
		if (null != jobEntity) {
			updateJob(jobEntity);
		}
	}


	private void updateJob(JobEntity currentJob) {
		etTitle.setText(currentJob.getTitle());
		etCompany.setText(currentJob.getCompany());
		etLocation.setText(currentJob.getLocation());
		etCostIndex.setText(String.format(java.util.Locale.US, "%d", currentJob.getCostIndex()));
		etYearlySalary.setText(String.format(java.util.Locale.US, "%.2f", currentJob.getYearlySalary()));
		etYearlyBonus.setText(String.format(java.util.Locale.US, "%.2f", currentJob.getYearlyBonus()));
		etRsua.setText(String.format(java.util.Locale.US, "%.2f", currentJob.getRsua()));
		etRelocStipend.setText(String.format(java.util.Locale.US, "%.2f", currentJob.getRelocStipend()));
		etPcholidays.setText(String.format(java.util.Locale.US, "%d", currentJob.getPcHolidays()));
	}


	private boolean validInputs() {
		boolean valid = true;
		if (TextUtils.isEmpty(etTitle.getText().toString())) {
			etTitle.setError("Job title cannot be empty");
			valid = false;
		}
		if (TextUtils.isEmpty(etCompany.getText().toString())) {
			etCompany.setError("Company name cannot be empty");
			valid = false;
		}
		if (TextUtils.isEmpty(etLocation.getText().toString())) {
			etLocation.setError("Location cannot be empty");
			valid = false;
		}
		if (TextUtils.isEmpty(etCostIndex.getText().toString())) {
			etCostIndex.setError("Cost index cannot be empty");
			valid = false;
		}
		if (TextUtils.isEmpty(etYearlySalary.getText().toString())) {
			etYearlySalary.setError("Yearly salary cannot be empty");
			valid = false;
		}
		if (TextUtils.isEmpty(etYearlyBonus.getText().toString())) {
			etYearlyBonus.setError("Yearly bonus cannot be empty");
			valid = false;
		}
		if (TextUtils.isEmpty(etRsua.getText().toString())) {
			etRsua.setError("Restricted Stock Unit Award cannot be empty");
			valid = false;
		}
		if (TextUtils.isEmpty(etRelocStipend.getText().toString())) {
			etRelocStipend.setError("Relocation stipend cannot be empty");
			valid = false;
		}
		else {
			float relocStipend = Float.parseFloat(etRelocStipend.getText().toString());
			if (relocStipend < 0 || relocStipend > 25000) {
				etRelocStipend.setError("Relocation stipend cannot be less than 0$ or greater than 25000$");
				valid = false;
			}
		}
		if (TextUtils.isEmpty(etPcholidays.getText().toString())) {
			etPcholidays.setError("Personal Choice Holidays cannot be empty");
			valid = false;
		}
		else {
			float pcHolidays = Integer.parseInt(etPcholidays.getText().toString());
			if (pcHolidays < 0 || pcHolidays > 20) {
				etPcholidays.setError("Personal Choice Holidays cannot be less than 0 or greater than 20");
				valid = false;
			}
		}
		return valid;
	}


	private JobEntity buildJob() {
		JobEntity jobEntity = (jobId == -1) ? new JobEntity() : instance.getJobById(jobId);
		jobEntity.setTitle(etTitle.getText().toString());
		jobEntity.setCompany(etCompany.getText().toString());
		jobEntity.setLocation(etLocation.getText().toString());
		jobEntity.setCostIndex(Integer.parseInt(etCostIndex.getText().toString()));
		jobEntity.setYearlySalary(Float.parseFloat(etYearlySalary.getText().toString()));
		jobEntity.setYearlyBonus(Float.parseFloat(etYearlyBonus.getText().toString()));
		jobEntity.setRsua(Float.parseFloat(etRsua.getText().toString()));
		jobEntity.setRelocStipend(Float.parseFloat(etRelocStipend.getText().toString()));
		jobEntity.setPcHolidays(Integer.parseInt(etPcholidays.getText().toString()));
		jobEntity.setCurrentJob(isCurrentJob);
		return jobEntity;
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.save_cancel_menu, menu);
		return true;
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.action_save) {
			JobEntity jobEntity = validateAndSaveJob();
			if (jobEntity != null) {
				if (!isCurrentJob) {
					// for save from job offers screen
					showAdditionalOptions(jobEntity);
				}
				else {
					Intent intent = new Intent(this, MainActivity.class);
					startActivity(intent);
				}
			}
		}
		if (item.getItemId() == R.id.action_cancel) {
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
		}
		return (super.onOptionsItemSelected(item));
	}


	private void showAdditionalOptions(JobEntity jobEntity) {
		AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.setTitle("Select Options");
		alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Enter another offer", (dialog, id) -> {
			finish();
			Intent jobOfferIntent = new Intent(this, JobActivity.class);
			jobOfferIntent.putExtra(JobActivity.INTENT_IS_CURRENT_JOB, false);
			jobOfferIntent.putExtra(MainActivity.INTENT_ACTION_BAR_TITLE, "Job Offer Details");
			startActivity(jobOfferIntent);
		});

		alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Compare with current job", (dialog, id) -> {
			Intent intent = new Intent(this, JobCompareActivity.class);
			JobEntity currentJob = instance.getCurrentJob();
			if (null != currentJob) {
				intent.putExtra(JobCompareActivity.INTENT_JOB1_ID, currentJob.getId());
				intent.putExtra(JobCompareActivity.INTENT_JOB2_ID, jobEntity.getId());
				startActivity(intent);
			}
			else {
				Toast.makeText(this, "Add current job first for comparison", Toast.LENGTH_LONG).show();
			}
		});

		alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Go to Main Menu", (dialog, id) -> {
			Intent intent = new Intent(JobActivity.this, MainActivity.class);
			finish();
			startActivity(intent);
		});
		alertDialog.show();
	}


	private JobEntity validateAndSaveJob() {
		boolean inputsValid = validInputs();
		logger.info("Inputs validated: " + inputsValid);
		JobEntity jobEntity = null;
		if (inputsValid) {
			jobEntity = buildJob();
			if (jobId == -1) {
				logger.info("Creating a new job..");
				instance.addJob(jobEntity);
				Toast.makeText(this, "Created a new job", Toast.LENGTH_LONG).show();
			}
			else {
				logger.info("Updating a existing job..");
				instance.updateJob(jobEntity);
				Toast.makeText(this, "Updated the existing job", Toast.LENGTH_LONG).show();
			}
		}
		return jobEntity;
	}
}