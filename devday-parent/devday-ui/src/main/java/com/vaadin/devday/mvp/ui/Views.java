package com.vaadin.devday.mvp.ui;

import com.vaadin.devday.mvp.ui.customer.CustomerViewBean;
import com.vaadin.devday.mvp.ui.job.JobViewBean;
import com.vaadin.navigator.View;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;

public enum Views {
	CUSTOMER("Customers", FontAwesome.USERS, "", CustomerViewBean.class),
	JOB("Jobs", FontAwesome.ROCKET, "job", JobViewBean.class);

	private final String name;
	private final Resource icon;
	private final String id;
	private final Class<? extends View> type;

	private Views(String name, Resource icon, String id, Class<? extends View> type) {
		this.name = name;
		this.icon = icon;
		this.id = id;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public Resource getIcon() {
		return icon;
	}

	public String getId() {
		return id;
	}

	public Class<? extends View> getType() {
		return type;
	}
}
