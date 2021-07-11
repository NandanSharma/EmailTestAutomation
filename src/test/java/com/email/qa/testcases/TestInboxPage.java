package com.email.qa.testcases;

import java.util.ArrayList;
import org.openqa.selenium.NoSuchElementException;
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

public class TestInboxPage extends TestBase {

	LoginPage loginpage;
	Inbox objInbox;
	TestUtil objTestUtil = new TestUtil();
	String emailNumberOnly;
	String sheetName = "emailSearch";
	SoftAssert softAssert = new SoftAssert();

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
		Assert.assertEquals(title, "Inbox | " + prop.getProperty("username") + " | ProtonMail",
				"Inbox page title is not matching");
	}

	@Test(priority = 1)
	public void verifyIfEmailExistsTest() throws InterruptedException {
		objInbox.waitTillInboxPageLoads();
		try {
			String totalEmails = objInbox.validateIfEmailExist();
			emailNumberOnly = totalEmails.replaceAll("[^0-9]", "");
			Assert.assertNotNull(totalEmails);
		} catch (NoSuchElementException e) {
			Assert.assertTrue(false, "Your inbox do not contain any emails");
		}
	}

	@DataProvider
	public Object[][] getEmailTestData(){
		
		Object data[][] =objTestUtil.getTestData(sheetName);
		
		return data;
		
	}

	@Test(priority = 2, dataProvider = "getEmailTestData")
	public void verifySubjectAndBodyTest(String strSubjectToFind, String strBodyToFind) throws InterruptedException {

		ArrayList<ArrayList<String>> arrSubBody = new ArrayList();
		ArrayList<String> arrSubject = new ArrayList<String>();
		ArrayList<String> arrBody = new ArrayList<String>();
		String strSubject = "";
		String strBody = "";
//		String strSubjectToFind = "Email Assignment"; // TODO parameter this
//		String strBodyToFind = "AdaFWsf"; // TODO parameter this

		objInbox.searchEmailbySubject(strSubjectToFind); 

		arrSubBody = objInbox.getSubjecAndBodyOfEmail();
		arrSubject = arrSubBody.get(0);
		arrBody = arrSubBody.get(1);

		for (int msgCount = 0; msgCount < arrSubject.size(); msgCount++) {
			strSubject = arrSubject.get(msgCount);
			strBody = arrBody.get(msgCount);
			int emailCountnter = msgCount +1;
			
			//verify			
			
			if (strSubject.contains(strSubjectToFind) && strBody.contains(strBodyToFind)) {
				System.out.println("Email " + emailCountnter + " contains " + strSubjectToFind + " in Subject and "
						+ strBodyToFind + " in Body");
				softAssert.assertTrue(strSubject.contains(strSubjectToFind) && strBody.contains(strBodyToFind), "Email " + emailCountnter + " contains " + strSubjectToFind + " in Subject and "
						+ strBodyToFind + " in Body");
				
			} else if (strSubject.contains(strSubjectToFind) || strBody.contains(strBodyToFind)) {
				if (strSubject.contains(strSubjectToFind)) {
					System.out.println("Email " + emailCountnter + " contains " + strSubjectToFind + " in Subject but "
							+ strBodyToFind + " is not present in Body");
					softAssert.assertTrue(strSubject.contains(strSubjectToFind) && strBody.contains(strBodyToFind), "Email " + emailCountnter + " contains " + strSubjectToFind + " in Subject but "
							+ strBodyToFind + " is not present in Body");
					
				}
				if (strBody.contains(strBodyToFind)) {
					System.out.println("Email " + emailCountnter + " do not contain " + strSubjectToFind
							+ " in Subject but " + strBodyToFind + " is present in Body");
					softAssert.assertTrue(strSubject.contains(strSubjectToFind) && strBody.contains(strBodyToFind), "Email " + emailCountnter + " do not contain " + strSubjectToFind
							+ " in Subject but " + strBodyToFind + " is present in Body");
					
				}
			} else {
				System.out.println("Email " + emailCountnter + " do not contain " + strSubjectToFind + " in Subject and "
						+ strBodyToFind + " is not present in Body");				
				softAssert.assertTrue(strSubject.contains(strSubjectToFind) && strBody.contains(strBodyToFind), "Email " + emailCountnter + " do not contain " + strSubjectToFind + " in Subject and "
						+ strBodyToFind + " is not present in Body");
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
