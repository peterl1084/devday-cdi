package com.vaadin.devday.mvp.ui;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;

import com.vaadin.testbench.ElementQuery;
import com.vaadin.testbench.commands.TestBenchCommands;
import com.vaadin.testbench.elementsbase.AbstractElement;

public abstract class PageObject {
	public static final String APP_URL = "http://localhost:8080/devday-deploy";

	private WebDriver driver;
	private SearchContext searchContext;

	private TestBenchCommands commands;

	private TestUI ui;

	public PageObject(WebDriver driver, SearchContext searchContext, TestBenchCommands commands, TestUI ui) {
		this.driver = driver;
		this.searchContext = searchContext;
		this.commands = commands;
		this.ui = ui;
	}

	protected TestUI getUI() {
		return ui;
	}

	protected WebDriver getDriver() {
		return driver;
	}

	protected <T extends AbstractElement> ElementQuery<T> $(Class<T> clazz) {
		return new ElementQuery<T>(clazz).context(searchContext);
	}

	protected void waitForVaadin() {
		commands.waitForVaadin();
	}

	protected void idle(int seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException e) {
		}
	}

	protected TestBenchCommands getTestBenchCommands() {
		return commands;
	}
}
