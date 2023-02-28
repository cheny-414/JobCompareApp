package edu.gatech.seclass.jobcompare6300.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import edu.gatech.seclass.jobcompare6300.ApplicationController;
import edu.gatech.seclass.jobcompare6300.JobComparator;
import edu.gatech.seclass.jobcompare6300.R;
import edu.gatech.seclass.jobcompare6300.db.JobEntity;

public class JobCompareActivity extends AppCompatActivity {
	private final ApplicationController instance = ApplicationController.getInstance();
	public static final String INTENT_JOB1_ID = "job1Id";
	public static final String INTENT_JOB2_ID = "job2Id";
	private final org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());


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
		int job1Id = getIntent().getIntExtra(INTENT_JOB1_ID, -1);
		int job2Id = getIntent().getIntExtra(INTENT_JOB2_ID, -1);
		if (job1Id != -1 && job2Id != -1) {
			JobComparator comparator = new JobComparator(instance.getJobById(job1Id), instance.getJobById(job2Id),
					ApplicationController.getInstance().getJobCompareSettings());
			Map<String, Integer> compareResults = comparator.compareJobs();
			// we are initializing our adapter class and passing our jobs to it.
			JobCompareAdapter jobCompareAdapter = new JobCompareAdapter(this, comparator.getJob1(), comparator.getJob2(), compareResults);
			LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
			rvJobOffers.setLayoutManager(linearLayoutManager);
			rvJobOffers.setAdapter(jobCompareAdapter);
		}
		else {
			logger.error("Invalid job ids for comparison job1Id: " + job1Id + " ,job2Id:" + job2Id);
			Toast.makeText(this, "Invalid job ids for comparison", Toast.LENGTH_LONG).show();
		}
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.job_offer_menu, menu);
		return true;
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.action_compare) {
			Intent jobOffersIntent = new Intent(this, JobOffersActivity.class);
			jobOffersIntent.putExtra(MainActivity.INTENT_ACTION_BAR_TITLE, "Job Offers");
			finish();
			startActivity(jobOffersIntent);
		}
		if (item.getItemId() == R.id.action_cancel) {
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
		}
		return (super.onOptionsItemSelected(item));
	}

}