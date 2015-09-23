package com.vaadin.devday.mvp.ui.cases;

import org.junit.Assert;
import org.junit.Test;

import com.vaadin.devday.mvp.ui.DevDayTestCase;
import com.vaadin.devday.mvp.ui.pageobject.CustomersViewPageObject;
import com.vaadin.devday.service.customer.Customer;
import com.vaadin.testbench.elements.NotificationElement;
import com.vaadin.testbench.elements.TableElement;
import com.vaadin.testbench.elements.TableRowElement;

public class CustomerViewTestCase extends DevDayTestCase {
	private Customer a;
	private Customer b;

	@Test
	public void testUserCreationAndRemoval() throws Exception {
		CustomersViewPageObject customerView = getTestUI().navigateToThroughURIFragment(CustomersViewPageObject.class);

		a = new Customer("Peter", "Lehto");
		b = new Customer("Artur", "Signell");

		for (int i = 0; i < 10; i++) {
			createAndRemoveUsers(customerView);
		}

		idle(1);
	}

	protected void createAndRemoveUsers(CustomersViewPageObject customersView) throws InterruptedException {
		customersView.addUser(a);
		assertDataSavedNotification();

		customersView.addUser(b);
		assertDataSavedNotification();

		assertUserInIndex(0, a);
		assertUserInIndex(1, b);

		customersView.selectTableRowByIndexAndClickRemove(1);
		assertDataRemovedNotification();

		customersView.selectTableRowByIndexAndClickRemove(0);
		assertDataRemovedNotification();

		assertNoIndexInTable(0);
	}

	private void assertNoIndexInTable(int i) {
		TableElement userTable = $(TableElement.class).first();

		try {
			TableRowElement row = userTable.getRow(1);
			Assert.fail();
		} catch (Exception e) {
			// Element should not be found, there should be an exception
		}
	}

	private void assertDataSavedNotification() throws InterruptedException {
		NotificationElement saved = $(NotificationElement.class).first();
		Assert.assertEquals("Data saved!", saved.getCaption());
		saved.click();
		idle(1);
	}

	protected void assertDataRemovedNotification() throws InterruptedException {
		NotificationElement removed = $(NotificationElement.class).first();
		Assert.assertEquals("Data removed!", removed.getCaption());
		removed.click();
		idle(1);
	}

	private void assertUserInIndex(int i, Customer user) {
		TableElement userTable = $(TableElement.class).first();
		Assert.assertEquals(user.getFirstName(), userTable.getRow(i).getCell(0).getText());
		Assert.assertEquals(user.getLastName(), userTable.getRow(i).getCell(1).getText());
	}
}
