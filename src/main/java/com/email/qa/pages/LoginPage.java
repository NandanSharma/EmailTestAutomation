package com.email.qa.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.email.qa.base.TestBase;

public class LoginPage extends TestBase {

	
	//Object Repo OR Page Factory
	@FindBy(id="username")
	WebElement username;
	
	@FindBy(id="password")
	WebElement password;
	
	@FindBy(xpath="//button[@type='submit']")
	WebElement loginBtn;
		
	//Initialization of all objects with PageFactory
	public LoginPage() {
		PageFactory.initElements(driver, this);
	}
	
	//Actions:
	
	public String validateLoginPageTitle() {
		return driver.getTitle();		
	}
	
	public Inbox logIn(String uname, String pwd) {
		username.sendKeys(uname);
		password.sendKeys(pwd);
		loginBtn.click();
		
		//after login, script will land in Inbox page, hence returning Inbox page class object
		return new Inbox();
	}
	
}
