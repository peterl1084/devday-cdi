package com.vaadin.devday.backend.job;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

/**
 * JobProcess represents the backend's point of view of a running job.
 * 
 * @author Peter / Vaadin
 */
public class JobProcess {
	private final String name;
	private final long startTime;
	private final long duration;
	private final String id;

	private boolean finished;

	public JobProcess(String name, long durationSeconds) {
		id = UUID.randomUUID().toString();

		this.name = name;
		this.duration = durationSeconds * 1000;

		startTime = System.currentTimeMillis();
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public long getStartTime() {
		return startTime;
	}

	public long getDuration() {
		return duration;
	}

	public double getProgress() {
		if (finished) {
			return 1.0;
		} else {
			long runningTime = System.currentTimeMillis() - startTime;

			double currentState = BigDecimal.valueOf(runningTime)
					.divide(BigDecimal.valueOf(duration), 2, RoundingMode.HALF_UP).doubleValue();

			if (currentState >= 1.0) {
				finished = true;
			}

			return currentState;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj instanceof JobProcess) {
			return this.id.equals(((JobProcess) obj).id);
		}

		return false;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}
}
