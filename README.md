#Selenium framework with Page Factory (using Page Object model)

#Pre-requisite:
Maven and eclipse/intellij runtime should be Java 7 or above

Supports Windows and OSX platforms.

#Steps:
1. Import the project in eclipse/intellij as Maven project
2. run mvn install from terminal/command line. which will also trigger the tests.

Two ways to run the testng test.
1. Go to project directory and open terminal/command prompt "mvn test -DsuiteXmlFile=Testng_searchBooksFirefox.xml"
2. From the editor right click on "Testng_searchBooksFirefox.xml" Run As -> TestNG Suite

To create new test class on top of this further, please read the Design_Document.docx

Troubleshooting: Please ensure there is a fresh workspace, since existing classpath might intervene and cause could load main class issues.

#Results:
After the Test successfully ran refer.
1. Logging.log for logger details. which will have each step executions.
2. test-output/eCommerceAutomationReport.html for extent report dashboard. The test-output folder also contains testng reports.
