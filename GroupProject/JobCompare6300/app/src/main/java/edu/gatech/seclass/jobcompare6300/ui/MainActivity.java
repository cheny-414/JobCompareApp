package edu.gatech.seclass.jobcompare6300.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import edu.gatech.seclass.jobcompare6300.ApplicationController;
import edu.gatech.seclass.jobcompare6300.R;
import edu.gatech.seclass.jobcompare6300.db.JobEntity;

public class MainActivity extends AppCompatActivity {
	private final ApplicationController instance = ApplicationController.getInstance();
	public static final String INTENT_ACTION_BAR_TITLE = "actionBarTitle";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}


	public void onEnterCurrentJobClicked(View view) {
		Intent currentJobIntent = new Intent(this, JobActivity.class);
		currentJobIntent.putExtra(JobActivity.INTENT_IS_CURRENT_JOB, true);
		currentJobIntent.putExtra(INTENT_ACTION_BAR_TITLE, "Current Job Details");
		JobEntity currentJob = instance.getCurrentJob();
		if (null != currentJob) {
			currentJobIntent.putExtra(JobActivity.INTENT_JOB_ID, currentJob.getId());
		}
		startActivity(currentJobIntent);
	}


	public void onEnterJobOfferClicked(View view) {
		Intent jobOfferIntent = new Intent(this, JobActivity.class);
		jobOfferIntent.putExtra(JobActivity.INTENT_IS_CURRENT_JOB, false);
		jobOfferIntent.putExtra(INTENT_ACTION_BAR_TITLE, "Job Offer Details");
		startActivity(jobOfferIntent);
	}


	public void onCompareJobOffersClicked(View view) {
		Intent jobOffersIntent = new Intent(this, JobOffersActivity.class);
		jobOffersIntent.putExtra(INTENT_ACTION_BAR_TITLE, "Job Offers");
		startActivity(jobOffersIntent);
	}


	public void onAdjustCompareSettingsClicked(View view) {
		Intent compareSettingsIntent = new Intent(this, JobCompareSettingsActivity.class);
		compareSettingsIntent.putExtra(INTENT_ACTION_BAR_TITLE, "Job Compare Settings");
		startActivity(compareSettingsIntent);
	}
}