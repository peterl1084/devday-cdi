package com.vaadin.devday.service.customer;

import java.util.List;

import javax.ejb.Local;

@Local
public interface CustomerService {

	void store(Customer customer);

	void delete(Customer customer);

	List<Customer> getCustomers();
}
