package com.vaadin.devday.mvp.ui.job;

import java.util.List;
import java.util.stream.IntStream;

import com.vaadin.cdi.CDIView;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.devday.mvp.ui.AbstractView;
import com.vaadin.devday.mvp.ui.PresenterBinding;
import com.vaadin.devday.service.job.Job;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

@CDIView("job")
@PresenterBinding(presenter = JobViewPresenter.class)
public class JobViewBean extends AbstractView<JobViewPresenter>implements JobView, View {

	private Table jobGrid;

	public JobViewBean() {
		setSizeFull();

		VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		layout.setSpacing(true);
		layout.setSizeFull();

		jobGrid = buildJobTable();
		jobGrid.setSizeFull();

		Button addJob = new Button("Add Job", e -> getPresenter().onAddJobClicked());

		layout.addComponents(jobGrid, addJob);
		layout.setExpandRatio(jobGrid, 1);
		setCompositionRoot(layout);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		getPresenter().onViewEnter();
	}

	@Override
	public void populateJobs(List<Job> jobs) {
		getUI().access(() -> {
			Object selectedRow = jobGrid.getValue();
			IntStream.range(0, jobs.size()).forEach(i -> {
				Job job = jobs.get(i);

				Item item = jobGrid.getContainerDataSource().getItem(job);
				if (item == null) {
					item = jobGrid.getContainerDataSource().addItem(job);
				}

				item.getItemProperty("name").setValue(job.getName());
				ProgressBar progressBar = (ProgressBar) item.getItemProperty("progress").getValue();
				if (progressBar == null) {
					progressBar = new ProgressBar((float) job.getProgress());
					progressBar.setWidth(100, Unit.PERCENTAGE);
				}
				item.getItemProperty("progress").setValue(progressBar);
				progressBar.setValue((float) job.getProgress());
			});
			jobGrid.setValue(selectedRow);
		});
	}

	private Table buildJobTable() {
		Table grid = new Table();
		grid.setSelectable(true);
		grid.setSizeFull();

		IndexedContainer container = new IndexedContainer();
		container.addContainerProperty("name", String.class, null);
		container.addContainerProperty("progress", ProgressBar.class, null);

		grid.setContainerDataSource(container);

		return grid;
	}
}
