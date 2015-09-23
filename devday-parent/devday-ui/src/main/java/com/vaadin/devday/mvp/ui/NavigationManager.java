package com.vaadin.devday.mvp.ui;

import java.util.Arrays;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import com.vaadin.cdi.CDIViewProvider;
import com.vaadin.cdi.NormalUIScoped;
import com.vaadin.devday.mvp.ui.menu.NavigationEvent;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.Navigator.UriFragmentManager;
import com.vaadin.server.Page;
import com.vaadin.server.Page.UriFragmentChangedEvent;
import com.vaadin.ui.UI;

@NormalUIScoped
public class NavigationManager {

	@Inject
	private CDIViewProvider viewProvider;

	@Inject
	private javax.enterprise.event.Event<NavigationEvent> eventPublisher;

	@Inject
	private ApplicationViewDisplay viewDisplay;

	private Navigator navigator;

	@PostConstruct
	protected void initialize() {
		navigator = new Navigator(UI.getCurrent(), new UriFragmentHandler(UI.getCurrent().getPage()), viewDisplay);
		navigator.addProvider(viewProvider);
	}

	public void navigateTo(Views view) {
		eventPublisher.fire(new NavigationEvent(view));
	}

	protected void onNavigationEvent(@Observes NavigationEvent event) {
		navigator.navigateTo(event.getTarget().getId());
	}

	private class UriFragmentHandler extends UriFragmentManager {

		public UriFragmentHandler(Page page) {
			super(page);
		}

		@Override
		public void uriFragmentChanged(UriFragmentChangedEvent event) {
			Optional<Views> targetView = Arrays.asList(Views.values()).stream()
					.filter(v -> v.getId().equals(getState())).findFirst();
			if (targetView.isPresent()) {
				navigateTo(targetView.get());
			} else {
				// fallback
				navigateTo(Views.CUSTOMER);
			}
		}
	}
}
