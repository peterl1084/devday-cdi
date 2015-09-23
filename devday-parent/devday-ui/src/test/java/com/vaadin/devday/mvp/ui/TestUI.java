package com.vaadin.devday.mvp.ui;

import java.lang.reflect.Constructor;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;

import com.vaadin.testbench.commands.TestBenchCommands;

public class TestUI {
	private WebDriver driver;
	private SearchContext context;
	private TestBenchCommands testBench;

	public TestUI(WebDriver driver, SearchContext context, TestBenchCommands testBench) {
		this.driver = driver;
		this.context = context;
		this.testBench = testBench;
	}

	public <T extends NavigablePageObject> T navigateToThroughURIFragment(Class<T> viewType) {
		try {
			T viewObject = instantiateNavigablePageObject(viewType);
			viewObject.navigateInto();
			return viewObject;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public <T extends NavigablePageObject> T navigateToThroughMenu(Class<T> viewType) {
		try {
			T viewObject = instantiateNavigablePageObject(viewType);

			ApplicationMenuPageObject mainMenu = getStaticViewElement(ApplicationMenuPageObject.class);
			mainMenu.clickMainMenuItem(viewObject.getView());
			idle(1);
			return viewObject;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected <T extends NavigablePageObject> T instantiateNavigablePageObject(Class<T> viewType) throws Exception {
		Constructor<T> constructor = viewType.getConstructor(WebDriver.class, SearchContext.class,
				TestBenchCommands.class, TestUI.class);
		T viewObject = constructor.newInstance(driver, context, testBench, this);
		return viewObject;
	}

	public <T extends PageObject> T getStaticViewElement(Class<T> staticObjectType) {
		try {
			Constructor<T> constructor = staticObjectType.getConstructor(WebDriver.class, SearchContext.class,
					TestBenchCommands.class, TestUI.class);
			return constructor.newInstance(driver, context, testBench, this);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected void idle(int seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException e) {
		}
	}
}
