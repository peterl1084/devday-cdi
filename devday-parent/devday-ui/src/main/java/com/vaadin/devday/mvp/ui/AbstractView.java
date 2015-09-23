package com.vaadin.devday.mvp.ui;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import com.vaadin.ui.CustomComponent;

/**
 * AbstractView is the base class of all MVP views. It takes care of finding
 * appropriate presenter component for the view.
 * 
 * @param
 * 			<P>
 *            type of the presenter this view uses.
 * 
 * @author Peter / Vaadin
 */
public abstract class AbstractView<P extends AbstractPresenter> extends CustomComponent {

	@Inject
	@Any
	private Instance<AbstractPresenter<?>> presenterInstantiator;

	private P presenter;

	@PostConstruct
	protected final void init() {
		Logger.getLogger(getClass().getSimpleName()).info("View init");
		if (getClass().isAnnotationPresent(PresenterBinding.class)) {
			PresenterBinding binding = getClass().getAnnotation(PresenterBinding.class);
			Class<? extends AbstractPresenter> presenterType = binding.presenter();

			presenter = (P) presenterInstantiator.select(presenterType).get();
			presenter.init((ApplicationView) this);
		} else {
			throw new RuntimeException(
					getClass().getSimpleName() + " does not define required @PresenterBinding annotation");
		}

		onViewReady();
	}

	@Override
	public void detach() {
		getPresenter().onViewExit();
		super.detach();
	}

	protected void onViewReady() {
		Logger.getLogger(getClass().getSimpleName()).info("View ready");
	}

	protected P getPresenter() {
		return presenter;
	}
}
