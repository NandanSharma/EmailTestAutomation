package com.email.qa.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
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
		@FindBy(id="global_search")
		WebElement txtBox_Search;
		
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
		
		@FindBy(xpath="//div[@data-testid='message-content:body']//div")
		WebElement lbl_MessageBody;
		
	//Initialization of all objects with PageFactory
		public Inbox() {
			PageFactory.initElements(driver, this);			
			customWait = new WebDriverWait(driver, 120);
		}
		
    //Actions:
		
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
		
		public void searchEmailbySubject(String searchSring) throws InterruptedException {
			//btn_Inbox.click();
			customWait.until(ExpectedConditions.visibilityOfAllElements(lbl_Subjects));
			txtBox_Search.sendKeys(searchSring + Keys.ENTER);
			Thread.sleep(3000);
			customWait.until(ExpectedConditions.visibilityOfAllElements(lbl_Subjects));
		}
		
		public void waitTillInboxPageLoads() {
			customWait.until(ExpectedConditions.visibilityOf(btn_Inbox));
		}
		
		
		public ArrayList<ArrayList<String>> getSubjecAndBodyOfEmail() throws InterruptedException {
			
			String emailBody = "";	
			
			ArrayList<ArrayList<String>> arrSubBody = new ArrayList();
			
			ArrayList<String> arrSubject = new ArrayList<String>();
			ArrayList<String> arrBody = new ArrayList<String>();
			customWait.until(ExpectedConditions.visibilityOfAllElements(lbl_Subjects));
			Thread.sleep(5000); //sometimes proton mail is slow in completely loading the page, EC was not working hence added this.
			for(WebElement lbl_Subject:lbl_Subjects) {
				
				arrSubject.add(lbl_Subject.getText());				
				lbl_Subject.click();//click on the subject to open the message
				customWait.until(ExpectedConditions.visibilityOf(lbl_MessageBody));
				Thread.sleep(5000);		//sometimes proton mail is slow in completely loading the page, EC was not working hence added this.		      
				List<WebElement> childs = lbl_MessageBody.findElements(By.xpath(".//*"));
				//System.out.println("count of child:" + childs.size());
				for(WebElement e: childs ) //traversing through child elements of email body to get all the text
				{
					emailBody = emailBody + e.getText();
				}				
				
				arrBody.add(emailBody);	
				emailBody = "";
			}			
			
			//return the list items		
			
			arrSubBody.add(arrSubject);
			arrSubBody.add(arrBody);
								
			return arrSubBody;
		}
}
