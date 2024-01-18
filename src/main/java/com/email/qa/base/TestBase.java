package com.email.qa.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import com.email.qa.util.TestUtil;
import com.email.qa.util.WebEventListener;
// Import the necessary libraries.
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.CapabilityType;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestBase {

	public static WebDriver driver;
	public static Properties prop;
	public static EventFiringWebDriver e_driver;
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

		if (browserName.equals("chrome")) {
			WebDriverManager.chromedriver().setup();
			ChromeOptions chromeOptions = new ChromeOptions();
			
			chromeOptions.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			chromeOptions.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);

			driver = new ChromeDriver();
		} else if (browserName.equals("firefox")) {

			// TODO implement firefox here later
		} else if (browserName.equals("edge")) {

			// Setup WebDriverManager to automatically download and configure the Edge
			// driver
			WebDriverManager.edgedriver().setup();
			// Create EdgeOptions instance
			EdgeOptions edgeOptions = new EdgeOptions();
			// Set desired capabilities for Edge browser
			edgeOptions.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			edgeOptions.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);

			// Create EdgeDriver instance
			driver = new EdgeDriver(edgeOptions);
		}

		// Listener class to give console output of the action performed on the browser
		e_driver = new EventFiringWebDriver(driver);
		eventListener = new WebEventListener();
		e_driver.register(eventListener);
		driver = e_driver;

		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(TestUtil.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(TestUtil.IMPLICIT_WAIT, TimeUnit.SECONDS);

		driver.get(prop.getProperty("url"));

	}

}
