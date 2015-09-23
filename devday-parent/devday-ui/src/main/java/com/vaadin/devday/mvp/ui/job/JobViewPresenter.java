package com.vaadin.devday.mvp.ui.job;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Logger;

import javax.ejb.EJB;

import com.vaadin.cdi.ViewScoped;
import com.vaadin.devday.mvp.ui.AbstractPresenter;
import com.vaadin.devday.service.job.JobService;

@ViewScoped
public class JobViewPresenter extends AbstractPresenter<JobView> {

	@EJB
	private JobService jobService;

	private ExecutorService pollingExecutor;
	private Future<?> runningPoller;

	public JobViewPresenter() {
		pollingExecutor = Executors.newSingleThreadExecutor();
	}

	@Override
	public void onViewEnter() {
		super.onViewEnter();
		getView().populateJobs(jobService.getPendingJobs());
		runningPoller = pollingExecutor.submit(new JobPoller());
	}

	@Override
	public void onViewExit() {
		runningPoller.cancel(true);
	}

	public void onDeleteClicked(Object itemId) {
		System.out.println(itemId);
	}

	public void onAddJobClicked() {
		getView().populateJobs(jobService.startNewJob());
	}

	private void refreshJobListing() {
		getView().populateJobs(jobService.getPendingJobs());
	}

	private class JobPoller implements Runnable {

		@Override
		public void run() {
			Logger.getLogger(JobPoller.class.getSimpleName()).info("Job poller started");

			while (!Thread.currentThread().isInterrupted()) {
				try {
					refreshJobListing();
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					break;
				}
			}

			Logger.getLogger(JobPoller.class.getSimpleName()).info("Job poller exited");
		}
	}
}
