package com.vaadin.devday.mvp.ui;

import java.util.logging.Logger;

/**
 * AbstractPresenter is the base class of all presenter components. Presenter's
 * role is to govern the view and control the complex UI logic based on
 * notifications presenter receives from its view.
 * 
 * @author Peter / Vaadin
 *
 * @param <V>
 *            type of the view this presenter governs
 */
public abstract class AbstractPresenter<V extends ApplicationView> {

	private V view;

	protected V getView() {
		return view;
	}

	/**
	 * Notifies the presenter that its view is initialized so that presenter can
	 * start its own initialization if required.
	 * 
	 * @param view
	 */
	protected final void init(V view) {
		Logger.getLogger(getClass().getSimpleName()).info("Presenter init");
		this.view = view;
		onPresenterReady();
	}

	/**
	 * Extending classes should overwrite this method in order to perform logic
	 * after presenter has finished initializing.
	 */
	protected void onPresenterReady() {
		Logger.getLogger(getClass().getSimpleName()).info("Presenter ready");
	}

	/**
	 * Extending classes should overwrite this method to react to the event when
	 * user has navigated into the view that this presenter governs.
	 */
	public void onViewEnter() {
		Logger.getLogger(getClass().getSimpleName()).info("View entered");
	}

	public void onViewExit() {

	}
}
