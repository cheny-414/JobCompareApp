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

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import edu.gatech.seclass.jobcompare6300.R;
import edu.gatech.seclass.jobcompare6300.db.JobEntity;

public class JobOffersAdapter extends RecyclerView.Adapter<JobOffersAdapter.ViewHolder> {

	private final Context context;
	private final List<JobEntity> jobEntities;
	private final OnItemCheckListener itemCheckListener;

	interface OnItemCheckListener {
		void onItemCheck(JobEntity item);

		void onItemUncheck(JobEntity item);

		void onItemSelected(JobEntity item);
	}


	// Constructor
	public JobOffersAdapter(Context context, List<JobEntity> jobEntities, OnItemCheckListener itemCheckListener) {
		this.context = context;
		this.jobEntities = jobEntities;
		this.itemCheckListener = itemCheckListener;
	}


	@NonNull
	@Override
	public JobOffersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		// to inflate the layout for each item of recycler view.
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_job_offers_card, parent, false);
		return new JobOffersAdapter.ViewHolder(view);
	}


	@Override public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
		JobEntity jobEntity = jobEntities.get(position);
		holder.tvTitle.setText(jobEntity.getTitle());
		holder.tvCompany.setText(jobEntity.getCompany());
		String jobScore = "Score: " + String.format(java.util.Locale.US, "%.1f", jobEntity.getJobScore());
		holder.tvJobScore.setText(jobScore);
		holder.ivJobType.setImageResource(jobEntity.isCurrentJob() ? android.R.drawable.btn_star_big_on :
				android.R.drawable.btn_star_big_off);
		holder.cvJobCard.setOnClickListener(v -> this.itemCheckListener.onItemSelected(jobEntity));
		holder.cvJobCard.setOnLongClickListener(v -> {
			holder.cvJobCard.setChecked(!holder.cvJobCard.isChecked());
			if (holder.cvJobCard.isChecked()) {
				this.itemCheckListener.onItemCheck(jobEntity);
			}
			else {
				this.itemCheckListener.onItemUncheck(jobEntity);
			}
			return true;
		});
	}


	@Override
	public int getItemCount() {
		return jobEntities.size();
	}


	// View holder class for initializing of your views such as TextView and Imageview
	public static class ViewHolder extends RecyclerView.ViewHolder {
		private final MaterialCardView cvJobCard;
		private final ImageView ivJobType;
		private final TextView tvTitle;
		private final TextView tvCompany;
		private final TextView tvJobScore;


		public ViewHolder(@NonNull View itemView) {
			super(itemView);
			cvJobCard = itemView.findViewById(R.id.cvJobCard);
			ivJobType = itemView.findViewById(R.id.ivJobType);
			tvTitle = itemView.findViewById(R.id.tvTitle);
			tvCompany = itemView.findViewById(R.id.tvCompany);
			tvJobScore = itemView.findViewById(R.id.tvJobScore);
		}
	}
}
