package com.vaadin.devday.mvp.ui;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import com.vaadin.cdi.UIScoped;
import com.vaadin.devday.mvp.ui.menu.ApplicationMenu;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Panel;

@UIScoped
public class ApplicationViewDisplay implements ViewDisplay {

	private HorizontalLayout layout;

	@Inject
	private Instance<ApplicationMenu> menuInstantiator;
	private ApplicationMenu menu;

	private Panel viewArea;

	public ApplicationViewDisplay() {
		layout = new HorizontalLayout();
		layout.setSizeFull();

		viewArea = new Panel();
		viewArea.setSizeFull();
	}

	@PostConstruct
	protected void init() {
		if (!menuInstantiator.isUnsatisfied() && !menuInstantiator.isAmbiguous()) {
			menu = menuInstantiator.get();
			layout.addComponent((Component) menu);
		}

		layout.addComponent(viewArea);

		if (menu != null) {
			layout.setExpandRatio((Component) menu, 20);
			layout.setExpandRatio(viewArea, 80);
		}
	}

	@Override
	public void showView(View view) {
		viewArea.setContent((Component) view);
	}

	public Layout getComponentContainer() {
		return layout;
	}
}
