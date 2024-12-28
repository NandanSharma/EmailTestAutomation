package com.email.qa.pages;

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

public class Inbox extends TestBase{
	
	WebDriverWait customWait;
	
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
		
		@FindBy(xpath="//div[@style='--index:0;']")
		WebElement lst_firstMessages;

		@FindBy(xpath="//div[@data-testid='message-content:body']")
		WebElement lbl_MessageBody;
		
	//Initialization of all objects with PageFactory
		public Inbox() {
			PageFactory.initElements(driver, this);			
			customWait = new WebDriverWait(driver, 120);
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
			return lbl_NoMessages.getText(); //method not used
		}
		
		public void searchEmailbySubject(String searchString) throws InterruptedException {
			//btn_Inbox.click();
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
		String emailBody = "";
		ArrayList<ArrayList<String>> emailDetails = new ArrayList<>();
		ArrayList<String> subjects = new ArrayList<>();
		ArrayList<String> bodies = new ArrayList<>();

		customWait.until(ExpectedConditions.visibilityOfAllElements(lbl_Subjects));


		for (WebElement subjectElement : lbl_Subjects) {
			subjects.add(subjectElement.getText());
			subjectElement.click(); // Click on the subject to open the message

			customWait.until(ExpectedConditions.visibilityOfAllElements(lbl_MessageBody));
			lbl_MessageBody.click();
			Thread.sleep(10000);
			List<WebElement> emailIframe = driver.findElements(By.tagName("iframe"));
			// Switch to the iframe
			for(WebElement iframe : emailIframe) {
				System.out.println(iframe.getAttribute("outerHTML"));
				System.out.println(iframe.getAttribute("innerHTML"));

				/*driver.switchTo().frame(iframe);
				if(driver.findElements(By.xpath("//iframe[@title='Email content']")).size() > 0) {
					break;
				}*/
			}

			customWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//iframe[@title='Email content']")));
			List<WebElement> childElements = driver.findElements(By.xpath(".//*"));
			StringBuilder emailBodyBuilder = new StringBuilder();

			for (WebElement element : childElements) {
				emailBodyBuilder.append(element.getText());
			}

			emailBody = emailBodyBuilder.toString();
			driver.switchTo().defaultContent(); // Switch back to default content

			bodies.add(emailBody);
			emailBody = "";
		}

		emailDetails.add(subjects);
		emailDetails.add(bodies);

		return emailDetails;
	}

}
