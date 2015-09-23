package com.vaadin.devday.backend.job;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;

import com.vaadin.devday.service.job.Job;
import com.vaadin.devday.service.job.JobService;

@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
public class JobServiceBean implements JobService {

	private List<JobProcess> jobs;
	private int jobIndex;

	public JobServiceBean() {
		jobs = new ArrayList<JobProcess>();
	}

	@Override
	@Lock(LockType.WRITE)
	public List<Job> startNewJob() {
		Random random = new Random();
		JobProcess process = new JobProcess("Job " + ++jobIndex, random.nextInt(60));
		jobs.add(process);

		return getPendingJobs();
	}

	@Override
	@Lock(LockType.READ)
	public List<Job> getPendingJobs() {
		return jobs.stream().map(job -> new Job(job.getId(), job.getName(), job.getProgress()))
				.collect(Collectors.toList());
	}

	@Override
	@Lock(LockType.WRITE)
	public void clearJob(Job job) {

	}
}
