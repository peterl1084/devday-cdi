package com.vaadin.devday.mvp.ui.pageobject;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;

import com.vaadin.devday.mvp.ui.TestUI;
import com.vaadin.devday.mvp.ui.Views;
import com.vaadin.devday.service.customer.Customer;
import com.vaadin.testbench.commands.TestBenchCommands;
import com.vaadin.testbench.elements.ButtonElement;
import com.vaadin.testbench.elements.TableElement;

public class CustomersViewPageObject extends JobsViewPageObject {

	public CustomersViewPageObject(WebDriver driver, SearchContext context, TestBenchCommands commands, TestUI ui) {
		super(driver, context, commands, ui);
	}

	@Override
	protected Views getView() {
		return Views.CUSTOMER;
	}

	public CustomerEditorPageObject clickAddButton() {
		ButtonElement addButton = $(ButtonElement.class).id("customer.add");
		addButton.click();
		return getUI().getStaticViewElement(CustomerEditorPageObject.class);
	}

	public void clickRemoveButton() {
		ButtonElement addButton = $(ButtonElement.class).id("customer.remove");
		addButton.click();
	}

	public void addUser(Customer customer) {
		idle(1);
		CustomerEditorPageObject editor = clickAddButton();
		idle(1);
		editor.fillFirstNameField(customer.getFirstName());
		idle(1);
		editor.fillLastNameField(customer.getLastName());
		idle(1);
		editor.clickSave();
		idle(1);
	}

	public void selectTableRowByIndex(int i) {
		TableElement userTable = $(TableElement.class).first();
		userTable.getRow(i).click();
	}

	public void selectTableRowByIndexAndClickRemove(int i) {
		idle(1);
		selectTableRowByIndex(i);
		idle(1);
		clickRemoveButton();
		idle(1);
	}
}
