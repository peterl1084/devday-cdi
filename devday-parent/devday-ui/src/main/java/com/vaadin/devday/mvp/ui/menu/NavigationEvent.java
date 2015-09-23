package com.vaadin.devday.mvp.ui.menu;

import com.vaadin.devday.mvp.ui.Views;

public class NavigationEvent {

	private final Views target;

	public NavigationEvent(Views target) {
		this.target = target;
	}

	public Views getTarget() {
		return target;
	}
}
