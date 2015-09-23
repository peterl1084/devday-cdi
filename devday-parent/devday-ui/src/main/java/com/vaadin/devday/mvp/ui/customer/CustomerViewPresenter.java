package com.vaadin.devday.mvp.ui.customer;

import static com.vaadin.devday.mvp.ui.customer.event.CustomerEvent.EventType.ADD;
import static com.vaadin.devday.mvp.ui.customer.event.CustomerEvent.EventType.EDIT;
import static com.vaadin.devday.mvp.ui.customer.event.CustomerEvent.EventType.REMOVE;
import static com.vaadin.devday.mvp.ui.customer.event.CustomerEvent.EventType.SAVED;

import javax.ejb.EJB;
import javax.enterprise.event.Observes;

import com.vaadin.cdi.ViewScoped;
import com.vaadin.devday.mvp.ui.AbstractPresenter;
import com.vaadin.devday.mvp.ui.customer.event.CustomerEvent;
import com.vaadin.devday.mvp.ui.customer.event.EventType;
import com.vaadin.devday.service.customer.Customer;
import com.vaadin.devday.service.customer.CustomerService;

@ViewScoped
public class CustomerViewPresenter extends AbstractPresenter<CustomerView> {

	@EJB
	private CustomerService customerService;

	@Override
	protected void onPresenterReady() {
		super.onPresenterReady();

		getView().populateCustomers(customerService.getCustomers());
	}

	public void onAddEvent(@Observes @EventType(type = ADD) CustomerEvent event) {
		getView().openCustomerEditor(new Customer());
	}

	public void onEditEvent(@Observes @EventType(type = EDIT) CustomerEvent event) {
		getView().openCustomerEditor(event.getTarget());
	}

	public void onRemoveCustomerClicked(@Observes @EventType(type = REMOVE) CustomerEvent event) {
		customerService.delete(event.getTarget());
		getView().populateCustomers(customerService.getCustomers());
		getView().showRemovedNotification();
	}

	public void onCustomerSaved(@Observes @EventType(type = SAVED) CustomerEvent event) {
		getView().populateCustomers(customerService.getCustomers());
	}

	public void onTableItemSelected(Customer selectedItem) {
		getView().setRemoveButtonEnabled(selectedItem != null);
	}
}
