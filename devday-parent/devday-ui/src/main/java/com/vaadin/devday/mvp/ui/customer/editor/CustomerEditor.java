package com.vaadin.devday.mvp.ui.customer.editor;

import com.vaadin.devday.mvp.ui.ApplicationView;

public interface CustomerEditor extends ApplicationView<CustomerEditorPresenter> {

	void showSaveFailed();

	void showSaveSucceeded();

	void close();

	void commitFormChanges();
}
