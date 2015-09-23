package com.vaadin.devday.service.job;

import java.util.List;

import javax.ejb.Local;

@Local
public interface JobService {

	/**
	 * Starts new job with given name
	 */
	List<Job> startNewJob();

	/**
	 * @return List of currently pending jobs
	 */
	List<Job> getPendingJobs();

	/**
	 * Removes given job from the list of executed jobs.
	 * 
	 * @param job
	 */
	void clearJob(Job job);
}
