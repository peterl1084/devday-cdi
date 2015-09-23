package com.vaadin.devday.mvp.ui;

import javax.enterprise.util.AnnotationLiteral;

public class PresenterBindingLiteral extends AnnotationLiteral<PresenterBinding>implements PresenterBinding {
	private Class<? extends AbstractPresenter> presenterType;

	public PresenterBindingLiteral(Class<? extends AbstractPresenter> presenterType) {
		this.presenterType = presenterType;
	}

	@Override
	public Class<? extends AbstractPresenter> presenter() {
		return presenterType;
	}
}
