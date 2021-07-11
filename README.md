# Page Object model Test automation framework using Selenium with Java, TestNG and Maven-
This is a sample project is for the test automation assignment using Selenium(Java), TestNG and Maven and this project will cover below functionalities 

1. Log into any email account and check if there is an email.
2. Verify that the subject and body of the email contains a predefined string.
3. Parameterize (data-driven) the script to handle multiple email accounts.
The test script should compile and run from the command line and within IDE.

### Prerequisite | Java
1. Java JDK is installed on your system
2. Eclipse is available

### Prerequisite | TestNG
1. TestNG plugin is added in eclipse

### Prerequisite | Maven
Maven should be available on your system, if not please do as below
1. Download Maven from https://maven.apache.org/download.cgi
2. Unzip maven to choice of your directory
3. Add the directory path to PATH environment variable

Check if maven is successfully installed using "mvn -version" in command prompt, this command should return your command version

### Steps to clone project
1. git clone https://github.com/NandanSharma/KaplanEmailTestAutomation.git
2. cd KaplanEmailTestAutomation

### Steps to run project via Command Prompt
mvn clean test -DtestNGxmlName=testng.xml

### Steps to run project via Eclipse IDE 
1.	Open Eclipse IDE -> File -> Import
2.	In Import Wizard select “Existing Maven Projects”
3.	Select the directory where you have cloned the project
4.	Select pom.xml using checkbox under projects
5.	Click on Finish.
6.	Wait for some time for eclipse to download all dependencies from Maven Repository which are in POM.xml 
7.	Right click on testing.xml and select Run As-> TestNG Suite.

