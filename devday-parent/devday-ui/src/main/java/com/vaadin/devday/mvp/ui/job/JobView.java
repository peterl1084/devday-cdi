package com.vaadin.devday.mvp.ui.job;

import java.util.List;

import com.vaadin.devday.mvp.ui.ApplicationView;
import com.vaadin.devday.service.job.Job;

public interface JobView extends ApplicationView<JobViewPresenter> {

	void populateJobs(List<Job> jobs);
}
