package com.email.qa.pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.email.qa.base.TestBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Inbox extends TestBase{
	
	WebDriverWait customWait;
	private static final Logger LOGGER = LoggerFactory.getLogger(Inbox.class);
	
	//Object Repo OR Page Factory
		@FindBy(xpath="//input[@data-testid='search-keyword']")
		WebElement txtBox_Search;

		@FindBy(xpath="//input[@data-testid='input-input-element']")
		WebElement txtBox_SearchPopup;
		
		@FindBy(xpath="//button[@type='submit']")
		WebElement btn_Search;
		
		@FindBy(xpath="//span[contains(text(),'Inbox')]")
		WebElement btn_Inbox;
		
		@FindBy(xpath="//p/strong[contains(text(),'conversations')]")
		WebElement lbl_conversations;
		
		@FindBy(xpath="//h3[contains(text(),'No messages found')]")
		WebElement lbl_NoMessages;
		
		@FindBy(xpath="//span[@data-testid='message-column:subject']")
		List<WebElement> lbl_Subjects;

		
	//Initialization of all objects with PageFactory
		public Inbox() {
			PageFactory.initElements(driver, this);			
			customWait = new WebDriverWait(driver, Duration.ofSeconds(120));
		}
		
    //Actions for webelements:
		
		public String validateInboxPageTitle() {
			return driver.getTitle();		
		}

		public String validateIfEmailExist() {
			btn_Inbox.click();
			return lbl_conversations.getText();
		}
		
		public String validateIfNoEmail() {
			return lbl_NoMessages.getText(); //method not used in class
		}
		
		public void searchEmailbySubject(String searchString) throws InterruptedException {
			btn_Inbox.click();
			Thread.sleep(5000);
			customWait.until(ExpectedConditions.visibilityOfAllElements(lbl_Subjects));
			customWait.until(ExpectedConditions.elementToBeClickable(txtBox_Search));
			txtBox_Search.click();
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", txtBox_SearchPopup);
			txtBox_SearchPopup.sendKeys(searchString + Keys.ENTER);
			Thread.sleep(3000);
			customWait.until(ExpectedConditions.visibilityOfAllElements(lbl_Subjects));
		}
		
		public void waitTillInboxPageLoads() {
			customWait.until(ExpectedConditions.visibilityOf(btn_Inbox));
		}


	public ArrayList<ArrayList<String>> getSubjecAndBodyOfEmail() throws InterruptedException {

		ArrayList<ArrayList<String>> emailDetails = new ArrayList<>();
		ArrayList<String> subjects = new ArrayList<>();
		ArrayList<String> bodies = new ArrayList<>();
		StringBuilder emailBodyBuilder = new StringBuilder();

		customWait.until(ExpectedConditions.visibilityOfAllElements(lbl_Subjects));


		for (WebElement subjectElement : lbl_Subjects) {
			subjects.add(subjectElement.getText());
			subjectElement.click(); // Click on the subject to open the message

			Thread.sleep(10000);

			//Switch to frame when available
			customWait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.xpath("//iframe[@title='Email content']")));

			//read all child elements from it
			List<WebElement> childElements = driver.findElements(By.xpath(".//*"));
			LOGGER.debug("Child elements of the frame: {}",childElements);
			for (WebElement element : childElements) {
				emailBodyBuilder.append(element.getText());
			}

			driver.switchTo().defaultContent(); // Switch back to default content
			bodies.add(emailBodyBuilder.toString());
		}

		emailDetails.add(subjects);
		emailDetails.add(bodies);

		return emailDetails;
	}

}
