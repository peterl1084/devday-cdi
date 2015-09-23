package com.vaadin.devday.mvp.ui.customer.event;

import javax.enterprise.util.AnnotationLiteral;

public class EventTypeLiteral extends AnnotationLiteral<EventType>implements EventType {

	private final com.vaadin.devday.mvp.ui.customer.event.CustomerEvent.EventType type;

	public EventTypeLiteral(com.vaadin.devday.mvp.ui.customer.event.CustomerEvent.EventType type) {
		this.type = type;
	}

	@Override
	public com.vaadin.devday.mvp.ui.customer.event.CustomerEvent.EventType type() {
		return type;
	}
}
