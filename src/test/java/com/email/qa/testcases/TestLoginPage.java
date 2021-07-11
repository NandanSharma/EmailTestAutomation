package com.email.qa.testcases;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.email.qa.base.TestBase;
import com.email.qa.pages.Inbox;
import com.email.qa.pages.LoginPage;

public class TestLoginPage extends TestBase{

	LoginPage loginpage;
	Inbox objInbox;
	//constructor
	public TestLoginPage() {
		//calling constructor of TestBase class to init prop
		super();
	}
	
	@BeforeMethod
	public void setup() {
		//calling the initialization method in TestBase class to perform the setup
		initialization();
		loginpage = new LoginPage(); 
	}
	
	
	@Test(priority = 0)
	public void loginPageTitleTest() {
		String title = loginpage.validateLoginPageTitle();
		Assert.assertEquals(title, "Proton Account","Login page title is not matching");
	}
	
	@Test(priority = 1)
	public void loginTest() {
		objInbox = loginpage.logIn(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	@AfterMethod
	public void teardown() {
		driver.quit();
	}
	
	
		
}
