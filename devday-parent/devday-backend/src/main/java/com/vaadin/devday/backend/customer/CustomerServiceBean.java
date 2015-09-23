package com.vaadin.devday.backend.customer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;

import com.vaadin.devday.service.customer.Customer;
import com.vaadin.devday.service.customer.CustomerService;

@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
public class CustomerServiceBean implements CustomerService {
	private List<Customer> customers;

	public CustomerServiceBean() {
		customers = new ArrayList<>();
	}

	@Override
	@Lock(LockType.WRITE)
	public void store(Customer customer) {
		if (customers.contains(customer)) {
			Customer existingCustomer = customers.get(customers.indexOf(customer));
			existingCustomer.setDataFrom(customer);
		} else {
			customers.add(customer);
		}
	}

	@Override
	@Lock(LockType.WRITE)
	public void delete(Customer customer) {
		customers.remove(customer);
	}

	@Override
	@Lock(LockType.READ)
	public List<Customer> getCustomers() {
		return customers.stream().map(c -> new Customer(c)).collect(Collectors.toList());
	}
}
