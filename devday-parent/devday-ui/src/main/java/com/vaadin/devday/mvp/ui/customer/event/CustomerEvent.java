package com.vaadin.devday.mvp.ui.customer.event;

import com.vaadin.devday.service.customer.Customer;
import com.vaadin.event.Action;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;

public class CustomerEvent extends Action {
	private final Customer target;
	private EventType eventType;

	public CustomerEvent(EventType eventType, Customer customer) {
		super(eventType.getName(), eventType.getIcon());
		this.eventType = eventType;
		target = customer;
	}

	public EventType getEventType() {
		return eventType;
	}

	public Customer getTarget() {
		return target;
	}

	public static enum EventType {
		ADD("Add", FontAwesome.PLUS),
		EDIT("Edit", FontAwesome.WRENCH),
		REMOVE("Remove", FontAwesome.TRASH_O),
		SAVED("Saved", FontAwesome.SAVE);

		private final String name;
		private final Resource icon;

		private EventType(String name, Resource icon) {
			this.name = name;
			this.icon = icon;
		}

		public String getName() {
			return name;
		}

		public Resource getIcon() {
			return icon;
		}
	}
}
