package com.vaadin.devday.mvp.ui;

import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.cdi.CDIUI;
import com.vaadin.cdi.server.VaadinCDIServlet;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

@Theme("valo")
@CDIUI("")
@Push
public class VaadinUI extends UI {

	@Inject
	private NavigationManager navigationManager;

	@Inject
	private ApplicationViewDisplay viewDisplay;

	@Override
	protected void init(VaadinRequest request) {
		setContent(viewDisplay.getComponentContainer());
		navigationManager.navigateTo(Views.CUSTOMER);
	}

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = VaadinUI.class)
	public static class ApplicationServlet extends VaadinCDIServlet {

	}
}
