package com.vaadin.devday.mvp.ui;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

import com.vaadin.testbench.TestBench;
import com.vaadin.testbench.TestBenchTestCase;

public abstract class DevDayTestCase extends TestBenchTestCase {

	public enum Browser {
		FIREFOX {
			@Override
			public WebDriver buildDriver() {
				return new FirefoxDriver();
			}
		},
		CHROME

		{
			@Override
			public WebDriver buildDriver() {
				return new ChromeDriver();
			}
		},
		PHANTOMJS {
			@Override
			public WebDriver buildDriver() {
				return new PhantomJSDriver();
			}
		};

		public abstract WebDriver buildDriver();
	}

	private static final String APP_URL = "http://localhost:8080/devday-deploy/";
	private TestUI testUI;

	@Before
	public void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver", "/Applications/chromedriver");
		useBrowser(Browser.FIREFOX);
		getDriver().manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		getDriver().get(APP_URL);
		testUI = new TestUI(getDriver(), getContext(), testBench());
	}

	private void useBrowser(Browser browser) {
		setDriver(TestBench.createDriver(browser.buildDriver()));
	}

	protected TestUI getTestUI() {
		return testUI;
	}

	@After
	public void tearDown() throws Exception {
		getDriver().quit();
	}

	protected void waitForVaadin() {
		testBench().waitForVaadin();
	}

	protected void idle(int seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException ignore) {
		}
	}
}
