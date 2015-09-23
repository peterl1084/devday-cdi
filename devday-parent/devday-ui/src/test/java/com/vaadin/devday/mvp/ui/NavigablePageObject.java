package com.vaadin.devday.mvp.ui;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;

import com.vaadin.testbench.commands.TestBenchCommands;

public abstract class NavigablePageObject extends PageObject {

	public NavigablePageObject(WebDriver driver, SearchContext searchContext, TestBenchCommands commands, TestUI ui) {
		super(driver, searchContext, commands, ui);
	}

	public void navigateInto() {
		((JavascriptExecutor) getDriver()).executeScript("window.location.hash='#!" + getView().getId() + "'");
		waitForVaadin();
	}

	protected abstract Views getView();
}
