package com.vaadin.devday.mvp.ui.pageobject;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;

import com.vaadin.devday.mvp.ui.NavigablePageObject;
import com.vaadin.devday.mvp.ui.TestUI;
import com.vaadin.testbench.commands.TestBenchCommands;

public abstract class JobsViewPageObject extends NavigablePageObject {

	public JobsViewPageObject(WebDriver driver, SearchContext searchContext, TestBenchCommands commands, TestUI ui) {
		super(driver, searchContext, commands, ui);
	}
}
