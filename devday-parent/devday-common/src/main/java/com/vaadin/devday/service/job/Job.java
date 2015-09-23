package com.vaadin.devday.service.job;

import java.io.Serializable;

/**
 * Job represents a simple serializable POJO that describes job entry with
 * current progress status.
 * 
 * @author Peter / Vaadin
 *
 */
public class Job implements Serializable {
	private final static int FINISH_LINE = 1;

	private String id;
	private String name;
	private double progress;

	public Job() {
		// for EJB serialization
	}

	public Job(String id, String name, double progress) {
		this.id = id;
		this.name = name;
		this.progress = progress;

		if (this.progress > FINISH_LINE) {
			this.progress = FINISH_LINE;
		}
	}

	public String getName() {
		return name;
	}

	public double getProgress() {
		return progress;
	}

	public boolean isFinished() {
		return getProgress() == FINISH_LINE;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj instanceof Job) {
			return this.id.equals(((Job) obj).id);
		}

		return false;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}
}
