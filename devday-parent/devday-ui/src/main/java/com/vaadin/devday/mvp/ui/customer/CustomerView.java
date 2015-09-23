package com.vaadin.devday.mvp.ui.customer;

import java.util.List;

import com.vaadin.devday.mvp.ui.ApplicationView;
import com.vaadin.devday.service.customer.Customer;

public interface CustomerView extends ApplicationView<CustomerViewPresenter> {

	void populateCustomers(List<Customer> customers);

	void openCustomerEditor(Customer customer);

	void showRemovedNotification();

	void setRemoveButtonEnabled(boolean enabled);
}
