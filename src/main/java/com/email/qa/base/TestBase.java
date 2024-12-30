package com.email.qa.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.events.WebDriverListener;
import com.email.qa.util.TestUtil;
import com.email.qa.util.WebEventListener;
// Import the necessary libraries.
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.CapabilityType;

import io.github.bonigarcia.wdm.WebDriverManager;

public abstract class TestBase {

	public static WebDriver driver;
	public static Properties prop;
	public static WebEventListener eventListener;

	public TestBase() {
		try {
			prop = new Properties();
			FileInputStream ip = new FileInputStream(System.getProperty("user.dir") +
					"\\src\\main\\java\\com\\email\\qa\\config\\config.properties");
			prop.load(ip);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void initialization() {

		String browserName = prop.getProperty("browser");
		boolean headlessMode = Boolean.parseBoolean(prop.getProperty("headless", "false"));

		if (browserName.equals("chrome")) {
			WebDriverManager.chromedriver().setup();
			ChromeOptions chromeOptions = new ChromeOptions();

			chromeOptions.setAcceptInsecureCerts(true);

			driver = new ChromeDriver(chromeOptions);
		} else if (browserName.equals("firefox")) {

			// TODO implement firefox here later
		} else if (browserName.equals("edge")) {

			// Setup WebDriverManager to automatically download and configure the Edge
			// driver
			WebDriverManager.edgedriver().setup();
			// Create EdgeOptions instance
			EdgeOptions edgeOptions = new EdgeOptions();
			// Set desired capabilities for Edge browser
			if (headlessMode) {
				edgeOptions.addArguments("--headless=new"); // Use --headless=new for Edge versions 109.0.1518.55 and above.
			}
			edgeOptions.setAcceptInsecureCerts(true);

			// Create EdgeDriver instance
			driver = new EdgeDriver(edgeOptions);
		}

		// Listener class to give console output of the action performed on the browser
		eventListener = new WebEventListener();
		WebDriverListener listener = new WebEventListener();
		driver = new EventFiringDecorator(listener).decorate(driver);

		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(TestUtil.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(TestUtil.IMPLICIT_WAIT, TimeUnit.SECONDS);

		driver.get(prop.getProperty("url"));

	}
}
