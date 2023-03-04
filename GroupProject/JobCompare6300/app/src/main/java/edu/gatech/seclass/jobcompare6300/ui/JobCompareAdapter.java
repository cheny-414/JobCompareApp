package edu.gatech.seclass.jobcompare6300.ui;

/**
 * Created by Puneeth Reddy on 2/27/2023.
 Initial version
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;
import com.j256.ormlite.stmt.query.In;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import edu.gatech.seclass.jobcompare6300.JobComparator;
import edu.gatech.seclass.jobcompare6300.R;
import edu.gatech.seclass.jobcompare6300.db.JobEntity;

public class JobCompareAdapter extends RecyclerView.Adapter<JobCompareAdapter.ViewHolder> {

	private final Context context;
	private final JobEntity job1;
	private final JobEntity job2;
	private final Map<String, Integer> compareResults;
	private final List<String> mKeys = new ArrayList<>();


	// Constructor
	public JobCompareAdapter(Context context, JobEntity job1, JobEntity job2, Map<String, Integer> compareResults) {
		this.context = context;
		this.job1 = job1;
		this.job2 = job2;
		this.compareResults = compareResults;
		mKeys.addAll(compareResults.keySet());
	}


	@NonNull
	@Override
	public JobCompareAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		// to inflate the layout for each item of recycler view.
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_job_compare_card, parent, false);
		return new JobCompareAdapter.ViewHolder(view);
	}


	@Override public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
		if (position == 0) {
			holder.tvJobAttributeName.setText(JobComparator.ATTRIBUTE);
			holder.tvJobAttributeName.setTextSize(24.0f);
			holder.tvJob1Attribute.setText(JobComparator.JOB_1);
			holder.tvJob1Attribute.setTextSize(24.0f);
			holder.tvJob2Attribute.setText(JobComparator.JOB_2);
			holder.tvJob2Attribute.setTextSize(24.0f);
		}
		else {
			String key = this.mKeys.get(position);
			Integer result = this.compareResults.get(this.mKeys.get(position));
			if (null != result) {
				holder.tvJobAttributeName.setText(key);
				holder.tvJob1Attribute.setText(getAttribute(key, job1));
				holder.tvJob1Attribute.setBackgroundResource(getTextColor(result));
				holder.tvJob2Attribute.setText(getAttribute(key, job2));
				holder.tvJob2Attribute.setBackgroundResource(getTextColor(result * -1));
			}
		}
	}


	private int getTextColor(Integer result) {
		if (result > 0) {
			return R.color.green;
		}
		if (result < 0) {
			return R.color.red;
		}
		return R.color.white;
	}


	private String getAttribute(String key, JobEntity job) {
		if (JobComparator.TITLE.equals(key)) {
			return job.getTitle();
		}
		else if (JobComparator.COMPANY.equals(key)) {
			return job.getCompany();
		}
		else if (JobComparator.LOCATION.equals(key)) {
			return job.getLocation();
		}
		else if (JobComparator.YEARLY_ADJUSTED_SALARY.equals(key)) {
			return "" + job.getYearlyAdjustedSalary();
		}
		else if (JobComparator.YEARLY_ADJUSTED_BONUS.equals(key)) {
			return "" + job.getYearlyAdjustedBonus();
		}
		else if (JobComparator.RSUA.equals(key)) {
			return "" + job.getRsua();
		}
		else if (JobComparator.RELOC_STIPEND.equals(key)) {
			return "" + job.getRelocStipend();
		}
		else if (JobComparator.PC_HOLIDAYS.equals(key)) {
			return "" + job.getPcHolidays();
		}
		else if (JobComparator.JOB_SCORE.equals(key)) {
			return "" + job.getJobScore();
		}
		else if (JobComparator.IS_CURRENT_JOB.equals(key)){
			return job.isCurrentJobString();
		} //Feng added for current job indicator in the comparison results
		return "";
	}


	@Override
	public int getItemCount() {
		return compareResults.size();
	}


	// View holder class for initializing of your views such as TextView and Imageview
	public static class ViewHolder extends RecyclerView.ViewHolder {
		private final TextView tvJobAttributeName;
		private final TextView tvJob1Attribute;
		private final TextView tvJob2Attribute;


		public ViewHolder(@NonNull View itemView) {
			super(itemView);
			tvJobAttributeName = itemView.findViewById(R.id.tvJobAttributeName);
			tvJob1Attribute = itemView.findViewById(R.id.tvJob1Attribute);
			tvJob2Attribute = itemView.findViewById(R.id.tvJob2Attribute);
		}
	}
}
