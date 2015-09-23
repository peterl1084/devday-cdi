package com.vaadin.devday.mvp.ui.menu;

import java.util.Arrays;

import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import com.vaadin.cdi.UIScoped;
import com.vaadin.devday.mvp.ui.AbstractPresenter;
import com.vaadin.devday.mvp.ui.Views;

@UIScoped
public class ApplicationMenuPresenter extends AbstractPresenter<ApplicationMenu> {

	@Inject
	private Event<NavigationEvent> navigationEventSource;

	@Override
	protected void onPresenterReady() {
		super.onPresenterReady();

		Arrays.asList(Views.values()).forEach(menuitem -> getView().addMenuItem(menuitem));
		getView().setMenuTitle("Vaadin - MVP example");
	}

	public void onMenuItemClicked(Views view) {
		navigationEventSource.fire(new NavigationEvent(view));
	}

	protected void onNavigationEvent(@Observes NavigationEvent event) {
		getView().markMenuItemActive(event.getTarget());
	}
}
