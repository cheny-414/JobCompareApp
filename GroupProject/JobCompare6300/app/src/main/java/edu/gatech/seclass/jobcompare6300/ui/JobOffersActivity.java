package edu.gatech.seclass.jobcompare6300.ui;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import edu.gatech.seclass.jobcompare6300.ApplicationController;
import edu.gatech.seclass.jobcompare6300.R;
import edu.gatech.seclass.jobcompare6300.db.JobEntity;

public class JobOffersActivity extends AppCompatActivity {
	private final ApplicationController instance = ApplicationController.getInstance();
	private final List<JobEntity> selectedJobs = new ArrayList<>();
	private MenuItem compareItem;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_job_offers);
		String actionBarTitle = getIntent().getStringExtra(MainActivity.INTENT_ACTION_BAR_TITLE);
		if (actionBarTitle == null || actionBarTitle.isEmpty()) {
			actionBarTitle = getString(R.string.app_name);
		}
		Objects.requireNonNull(getSupportActionBar()).setTitle(actionBarTitle);
		RecyclerView rvJobOffers = findViewById(R.id.rvJobOffers);
		// we are initializing our adapter class and passing our job list to it.
		JobOffersAdapter jobOffersAdapter = new JobOffersAdapter(this, instance.sortJobsByScore(), new JobOffersAdapter.OnItemCheckListener() {
			@Override public void onItemCheck(JobEntity item) {
				selectedJobs.add(item);
				toggleCompareMenuItem(selectedJobs.size() == 2);
			}


			@Override public void onItemUncheck(JobEntity item) {
				selectedJobs.remove(item);
				toggleCompareMenuItem(selectedJobs.size() == 2);
			}


			@Override public void onItemSelected(JobEntity item) {
				Intent jobOfferIntent = new Intent(JobOffersActivity.this, JobActivity.class);
				jobOfferIntent.putExtra(JobActivity.INTENT_IS_CURRENT_JOB, false);
				jobOfferIntent.putExtra(MainActivity.INTENT_ACTION_BAR_TITLE, "Job Offer Details");
				jobOfferIntent.putExtra(JobActivity.INTENT_JOB_ID, item.getId());
				startActivity(jobOfferIntent);
			}
		});
		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
		rvJobOffers.setLayoutManager(linearLayoutManager);
		rvJobOffers.setAdapter(jobOffersAdapter);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.job_offer_menu, menu);
		return true;
	}


	@Override public boolean onPrepareOptionsMenu(Menu menu) {
		compareItem = menu.findItem(R.id.action_compare);
		toggleCompareMenuItem(false);
		return super.onPrepareOptionsMenu(menu);
	}


	private void toggleCompareMenuItem(boolean enable) {
		if (enable) {
			compareItem.setEnabled(true);
			compareItem.getIcon().setAlpha(255);
		}
		else {
			// disabled
			compareItem.setEnabled(false);
			compareItem.getIcon().setAlpha(130);
		}
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.action_compare) {
			if (selectedJobs.size() == 2) {
				Intent intent = new Intent(this, JobCompareActivity.class);
				intent.putExtra(JobCompareActivity.INTENT_JOB1_ID, this.selectedJobs.get(0).getId());
				intent.putExtra(JobCompareActivity.INTENT_JOB2_ID, this.selectedJobs.get(1).getId());
				startActivity(intent);
			}
			else {
				Toast.makeText(this,
						"Please select only 2 jobs for comparison.", Toast.LENGTH_LONG).show();
			}
		}
		if (item.getItemId() == R.id.action_cancel) {
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
		}
		return (super.onOptionsItemSelected(item));
	}

}