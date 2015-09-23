package com.vaadin.devday.mvp.ui.menu;

import com.vaadin.devday.mvp.ui.ApplicationView;
import com.vaadin.devday.mvp.ui.Views;

public interface ApplicationMenu extends ApplicationView<ApplicationMenuPresenter> {

	void setMenuTitle(String title);

	void addMenuItem(Views view);

	void markMenuItemActive(Views view);
}
