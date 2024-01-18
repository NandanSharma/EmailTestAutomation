package com.email.qa.base;

import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.CapabilityType;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Test {

    public static void main(String[] args) {
        // Setup WebDriverManager to automatically download and configure the Edge driver
        WebDriverManager.edgedriver().setup();

        // Create EdgeOptions instance
        EdgeOptions edgeOptions = new EdgeOptions();

        // Set desired capabilities for Edge browser
        edgeOptions.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        edgeOptions.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);

        // Create EdgeDriver instance
        EdgeDriver edgeDriver = new EdgeDriver(edgeOptions);

        // Navigate to a website
        edgeDriver.get("https://www.google.com");

        // Perform actions on the website using the EdgeDriver instance

        // Close the Edge browser
        edgeDriver.quit();
    }
}
