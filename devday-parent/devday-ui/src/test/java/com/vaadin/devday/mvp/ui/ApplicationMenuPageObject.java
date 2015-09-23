package com.vaadin.devday.mvp.ui;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;

import com.vaadin.testbench.commands.TestBenchCommands;
import com.vaadin.testbench.elements.CssLayoutElement;

public class ApplicationMenuPageObject extends PageObject {

	public ApplicationMenuPageObject(WebDriver driver, SearchContext searchContext, TestBenchCommands commands,
			TestUI ui) {
		super(driver, searchContext, commands, ui);
	}

	public void clickMainMenuItem(Views view) {
		CssLayoutElement menuItem = $(CssLayoutElement.class).id(view.getId());
		menuItem.click();
	}
}
