package com.vaadin.devday.mvp.ui.pageobject;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;

import com.vaadin.devday.mvp.ui.PageObject;
import com.vaadin.devday.mvp.ui.TestUI;
import com.vaadin.testbench.commands.TestBenchCommands;
import com.vaadin.testbench.elements.ButtonElement;
import com.vaadin.testbench.elements.TextFieldElement;

public class CustomerEditorPageObject extends PageObject {

	public CustomerEditorPageObject(WebDriver driver, SearchContext searchContext, TestBenchCommands commands,
			TestUI ui) {
		super(driver, searchContext, commands, ui);
	}

	public void fillFirstNameField(String value) {
		$(TextFieldElement.class).id("field-firstname").sendKeys(value);
	}

	public void fillLastNameField(String value) {
		$(TextFieldElement.class).id("field-lastname").sendKeys(value);
	}

	public void clickSave() {
		$(ButtonElement.class).caption("Save").first().click();
	}
}
