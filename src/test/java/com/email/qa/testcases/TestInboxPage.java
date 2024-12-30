package com.email.qa.testcases;

import java.util.ArrayList;
import java.util.regex.Pattern;

import org.openqa.selenium.NoSuchElementException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.email.qa.base.TestBase;
import com.email.qa.pages.Inbox;
import com.email.qa.pages.LoginPage;
import com.email.qa.util.TestUtil;

import static org.testng.Assert.assertTrue;

public class TestInboxPage extends TestBase {

	LoginPage loginpage;
	Inbox objInbox;
	TestUtil objTestUtil = new TestUtil();
	String emailNumberOnly;
	String sheetName = "emailSearch";
	SoftAssert softAssert = new SoftAssert();

	private static final Logger LOGGER = LoggerFactory.getLogger(TestInboxPage.class);

	// constructor
	public TestInboxPage() {
		// calling constructor of TestBase class to init prop
		super();
	}

	@BeforeMethod
	public void setup() {
		// calling the initialization method in TestBase class to perform the setup
		initialization();
		loginpage = new LoginPage();
		objInbox = loginpage.logIn(prop.getProperty("username"), prop.getProperty("password"));

	}

	@Test(priority = 0)
	public void verifyInboxPageTitleTest() throws InterruptedException {
		objInbox.waitTillInboxPageLoads();
		String title = objInbox.validateInboxPageTitle();
		String regex = "^\\(\\d+\\) Inbox \\| %s \\| Proton Mail$".formatted(prop.getProperty("username"));
		/*Assert.assertEquals(title, "Inbox | " + prop.getProperty("username") + " | Proton Mail",
				"Inbox page title is not matching");*/
		Assert.assertTrue(Pattern.matches(regex, title), "Inbox page title is not matching");
	}

	@Test(priority = 1)
	public void verifyIfEmailExistsTest() throws InterruptedException {
		objInbox.waitTillInboxPageLoads();
		try {
			String totalEmails = objInbox.validateIfEmailExist();
			emailNumberOnly = totalEmails.replaceAll("[^0-9]", "");
			Assert.assertNotNull(totalEmails);
		} catch (NoSuchElementException e) {
			assertTrue(false, "Your inbox do not contain any emails");
		}
	}

	@DataProvider
	public Object[][] getEmailTestData(){
		
		Object data[][] =objTestUtil.getTestData(sheetName);
		
		return data;
		
	}

	@Test(priority = 2, dataProvider = "getEmailTestData")
	public void verifySubjectAndBodyTest(String strSubjectToFind, String strBodyToFind) throws InterruptedException {

		objInbox.searchEmailbySubject(strSubjectToFind); 

		ArrayList<ArrayList<String>> emails = objInbox.getSubjecAndBodyOfEmail();
		ArrayList<String> subjects = emails.get(0);
		ArrayList<String> bodies = emails.get(1);

		for (int i = 0; i < subjects.size(); i++) {
			boolean subjectMatch = subjects.get(i).contains(strSubjectToFind);
			boolean bodyMatch = bodies.get(i).contains(strBodyToFind);

			String message = String.format("Email %d contains '%s' in Subject and '%s' in Body", i + 1, strSubjectToFind, strBodyToFind);
			if (subjectMatch && bodyMatch) {
				LOGGER.info(message);
				softAssert.assertTrue(true, message);
			} else {
				LOGGER.info("Email " + (i+1) + " does not contain both " + strSubjectToFind + " and " + strBodyToFind);
				softAssert.assertTrue(false, message);
			}
			softAssert.assertAll();
			Thread.sleep(2000);
		}

	}

	@AfterMethod
	public void teardown() {
		driver.quit();
	}
}
