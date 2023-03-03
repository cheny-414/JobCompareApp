package edu.gatech.seclass.jobcompare6300;

import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import edu.gatech.seclass.jobcompare6300.db.DatabaseManager;
import edu.gatech.seclass.jobcompare6300.db.JobCompareSettings;
import edu.gatech.seclass.jobcompare6300.db.JobEntity;
import edu.gatech.seclass.jobcompare6300.db.dao.JobCompareSettingsDao;
import edu.gatech.seclass.jobcompare6300.db.dao.JobEntityDao;

/**
 * Created by Puneeth Reddy on 2/26/2023.
 Initial version
 */

public class ApplicationController {
	private static ApplicationController instance = null;
	private final List<JobEntity> jobs = new ArrayList<>();
	private final JobCompareSettings jobCompareSettings;
	private final org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());


	private ApplicationController() {
		JobEntityDao jobOfferDao = DatabaseManager.getInstance().getDb().getJobEntityDao();
		JobCompareSettingsDao jobCompareSettingsDao = DatabaseManager.getInstance().getDb().getJobCompareSettingsDao();
		jobCompareSettings = jobCompareSettingsDao.loadCompareSettings();
		jobs.addAll(jobOfferDao.loadJobOffers());
	}


	public static ApplicationController getInstance() {
		if (instance == null) {
			instance = new ApplicationController();
		}
		return instance;
	}


	public List<JobEntity> getJobs() {
		return jobs;
	}


	public JobCompareSettings getJobCompareSettings() {
		return jobCompareSettings;
	}


	public void addJob(JobEntity jobEntity) {
		if (jobEntity.isCurrentJob()) {
			JobEntity currentJob = getCurrentJob();
			if (null != currentJob) {
				logger.info("Current job already exists" + currentJob.toString());
				throw new IllegalArgumentException("Cannot have two current jobs");
			}
		}
		setAdditionalAttributes(jobEntity);
		int createJob = createJobInDb(jobEntity);
		if (createJob == 1) {
			this.jobs.add(jobEntity);
			logger.info("Added job " + jobEntity.toString());
		}
	}


	private void setAdditionalAttributes(JobEntity jobEntity) {
		float change = 100.0f / jobEntity.getCostIndex();
		jobEntity.setYearlyAdjustedSalary(jobEntity.getYearlySalary() * change);
		jobEntity.setYearlyAdjustedBonus(jobEntity.getYearlyBonus() * change);
		float jobScore = JobComparator.calculateJobScore(jobEntity, jobCompareSettings);
		jobEntity.setJobScore(jobScore);
	}


	public void removeJob(JobEntity jobEntity) {
		int deleteJob = deleteJobInDb(jobEntity);
		if (deleteJob == 1) {
			this.jobs.remove(jobEntity);
			logger.info("Removed job " + jobEntity.toString());
		}
	}


	public JobEntity getJobById(int jobId) {
		Optional<JobEntity> currentJob = this.jobs.stream().filter(e -> e.getId() == jobId).findFirst();
		return currentJob.orElse(null);
	}


	public JobEntity getCurrentJob() {
		Optional<JobEntity> currentJob = this.jobs.stream().filter(JobEntity::isCurrentJob).findFirst();
		return currentJob.orElse(null);
	}


	public int createJobCompareSettingsInDb(JobCompareSettings compareSettings) {
		JobCompareSettingsDao jobCompareSettingsDao = DatabaseManager.getInstance().getDb().getJobCompareSettingsDao();
		try {
			return jobCompareSettingsDao.create(compareSettings);
		}
		catch (SQLException e) {
			logger.error(e.getMessage());
		}
		return -1;
	}


	public int updateJobCompareSettingsInDb(JobCompareSettings compareSettings) {
		JobCompareSettingsDao jobCompareSettingsDao = DatabaseManager.getInstance().getDb().getJobCompareSettingsDao();
		try {
			return jobCompareSettingsDao.update(compareSettings);
		}
		catch (SQLException e) {
			logger.error(e.getMessage());
		}
		return -1;
	}


	private int createJobInDb(JobEntity currentJob) {
		JobEntityDao jobEntityDao = DatabaseManager.getInstance().getDb().getJobEntityDao();
		try {
			return jobEntityDao.create(currentJob);
		}
		catch (SQLException e) {
			logger.error(e.getMessage());
		}
		return -1;
	}


	private int deleteJobInDb(JobEntity currentJob) {
		JobEntityDao jobEntityDao = DatabaseManager.getInstance().getDb().getJobEntityDao();
		try {
			return jobEntityDao.delete(currentJob);
		}
		catch (SQLException e) {
			logger.error(e.getMessage());
		}
		return -1;
	}


	public int updateJob(JobEntity jobEntity) {
		setAdditionalAttributes(jobEntity);
		return updateJobInDb(jobEntity);
	}


	private int updateJobInDb(JobEntity jobEntity) {
		JobEntityDao jobEntityDao = DatabaseManager.getInstance().getDb().getJobEntityDao();
		try {
			return jobEntityDao.update(jobEntity);
		}
		catch (SQLException e) {
			logger.error(e.getMessage());
		}
		return -1;
	}


	public void computeJobScores() {
		for (JobEntity jobEntity : this.jobs) {
			float jobScore = JobComparator.calculateJobScore(jobEntity, jobCompareSettings);
			jobEntity.setJobScore(jobScore);
			int updatedJob = updateJob(jobEntity);
			if (updatedJob == 1) {
				logger.info("Updated job " + jobEntity);
			}
		}
	}


	public List<JobEntity> sortJobsByScore() {
		JobEntity currentJob = getCurrentJob();
		List<JobEntity> allJobs = new ArrayList<>(this.getJobs());
		allJobs.remove(currentJob);
		allJobs.sort(Comparator.comparingDouble(JobEntity::getJobScore).reversed());
		if (null != currentJob) {
			allJobs.add(0, currentJob);
		}
		return allJobs;
	}

}
