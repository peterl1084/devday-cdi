package com.vaadin.devday.mvp.ui.customer.editor;

import javax.ejb.EJB;
import javax.inject.Inject;

import com.vaadin.devday.mvp.ui.AbstractPresenter;
import com.vaadin.devday.mvp.ui.customer.event.CustomerEvent;
import com.vaadin.devday.mvp.ui.customer.event.EventType;
import com.vaadin.devday.service.customer.Customer;
import com.vaadin.devday.service.customer.CustomerService;

public class CustomerEditorPresenter extends AbstractPresenter<CustomerEditor> {

	@EJB
	private CustomerService customerService;

	@Inject
	@EventType(type = CustomerEvent.EventType.SAVED)
	private javax.enterprise.event.Event<CustomerEvent> customerEventSource;

	public void onCancelClicked() {
	}

	public void onCommit(Customer customer) throws BackendCommitFailedException {
		try {
			customerService.store(customer);
			getView().showSaveSucceeded();
			getView().close();
			customerEventSource.fire(new CustomerEvent(CustomerEvent.EventType.SAVED, customer));
		} catch (Exception e) {
			throw new BackendCommitFailedException(e);
		}
	}

	public void onFormSaveClicked() {
		getView().commitFormChanges();
		getView().close();
	}

	public class BackendCommitFailedException extends Exception {

		public BackendCommitFailedException(Throwable t) {
			super(t);
		}
	}
}
