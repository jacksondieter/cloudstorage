package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.File;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;

	public String baseURL;
	private WebDriverWait wait;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
		baseURL = "http://localhost:" + this.port;
		this.wait = new WebDriverWait(driver, 2);
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
		if(this.wait != null){
			wait = null;
		}
	}

	@Test
	public void getLoginPage() {
		driver.get(baseURL + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doMockSignUp(String firstName, String lastName, String userName, String password){
		// Create a dummy account for logging in later.

		// Visit the sign-up page.
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		driver.get(baseURL + "/signup");
		webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));
		
		// Fill out credentials
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.click();
		inputFirstName.sendKeys(firstName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.click();
		inputLastName.sendKeys(lastName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.click();
		inputUsername.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.click();
		inputPassword.sendKeys(password);

		// Attempt to sign up.
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
		WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
		buttonSignUp.click();

		/* Check that the signup was successful.
		// You may have to modify the element "success-msg" and the sign-up 
		// success message below depening on the rest of your code.
		*/
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonLogin")));
		Assertions.assertTrue(driver.findElement(By.id("successMsg")).getText().contains("You successfully signed up!"));
	}

	
	
	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doLogIn(String userName, String password)
	{
		// Log in to our dummy account.
		driver.get(baseURL + "/login");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement loginUserName = driver.findElement(By.id("inputUsername"));
		loginUserName.click();
		loginUserName.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement loginPassword = driver.findElement(By.id("inputPassword"));
		loginPassword.click();
		loginPassword.sendKeys(password);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonLogin")));
		WebElement loginButton = driver.findElement(By.id("buttonLogin"));
		loginButton.click();

		webDriverWait.until(ExpectedConditions.titleContains("Home"));

	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling redirecting users 
	 * back to the login page after a succesful sign up.
	 * Read more about the requirement in the rubric: 
	 * https://review.udacity.com/#!/rubrics/2724/view 
	 */
	@Test
	public void testRedirection() {
		// Create a test account
		doMockSignUp("Redirection","Test","RT","123");

		// Check if we have been redirected to the login page.
		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling bad URLs 
	 * gracefully, for example with a custom error page.
	 * 
	 * Read more about custom error pages at: 
	 * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
	 */
	@Test
	public void testBadUrl() {
		// Create a test account
		doMockSignUp("URL","Test","UT","123");
		doLogIn("UT", "123");
		
		// Try to access a random made-up URL.
		driver.get(baseURL + "/some-random-page");
		Assertions.assertFalse(driver.getPageSource().contains("Whitelabel Error Page"));
	}


	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling uploading large files (>1MB),
	 * gracefully in your code. 
	 * 
	 * Read more about file size limits here: 
	 * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload Limits" section.
	 */
	@Test
	public void testLargeUpload() {
		// Create a test account
		doMockSignUp("Large File","Test","LFT","123");
		doLogIn("LFT", "123");

		// Try to upload an arbitrary large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		String fileName = "upload5m.zip";

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
		WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
		fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

		WebElement uploadButton = driver.findElement(By.id("uploadButton"));
		uploadButton.click();
		try {
			webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Large File upload failed");
		}
		Assertions.assertFalse(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));

	}

	private void signupPageActions(String username,String password){
		wait = new WebDriverWait(driver, 2);
		driver.get(baseURL + "/signup");
		wait.until(ExpectedConditions.titleContains("Sign Up"));
		SignupPage signuppage = new SignupPage(driver);
		signuppage.signupUser("URL","Test",username,password);
		wait.until(ExpectedConditions.titleContains("Login"));
		LoginPage loginPage = new LoginPage(driver);
		Assertions.assertTrue(loginPage.getSuccessAlert().contains("You successfully signed up!"));
	}

	private void loginPageActions(String username, String password){
		driver.get(baseURL + "/login");
		wait.until(ExpectedConditions.titleContains("Login"));
		LoginPage loginPage = new LoginPage(driver);
		loginPage.loginUser(username,password);
		wait.until(ExpectedConditions.titleContains("Home"));
	}

	@Test
	public void testNotesCreation(){
		String username = "NOTE";
		String password = "123";
		String title= "New note";
		String description ="Description for new note";
		String titleToEdit= "Edited note";
		String descriptionToEdit ="Description for edited note";
		signupPageActions(username,password);
		loginPageActions(username,password);
		HomePage homePage = new HomePage(driver);
		ResultPage resultPage = new ResultPage(driver);

		homePage.navigateToNote();
		wait.until(ExpectedConditions.visibilityOfElementLocated((By.id(homePage.noteTable))));
		homePage.addNoteClick();
		wait.until(ExpectedConditions.visibilityOfElementLocated((By.id(homePage.noteModal))));
		homePage.addNote(title,description);
		wait.until(ExpectedConditions.titleContains("Result"));
		resultPage.navigateToHomeFromSuccess();
		wait.until(ExpectedConditions.titleContains("Home"));

		homePage.navigateToNote();
		wait.until(ExpectedConditions.visibilityOfElementLocated((By.id(homePage.noteTable))));
		homePage.editNoteClick();
		wait.until(ExpectedConditions.visibilityOfElementLocated((By.id(homePage.noteModal))));
		homePage.addNote(titleToEdit,descriptionToEdit);
		wait.until(ExpectedConditions.titleContains("Result"));
		resultPage.navigateToHomeFromSuccess();
		wait.until(ExpectedConditions.titleContains("Home"));

		homePage.navigateToNote();
		wait.until(ExpectedConditions.visibilityOfElementLocated((By.id(homePage.noteTable))));
		homePage.deleteNoteClick();
		wait.until(ExpectedConditions.titleContains("Result"));
		resultPage.navigateToHomeFromSuccess();
		wait.until(ExpectedConditions.titleContains("Home"));
	}

	@Test
	public void testCredentialCreation(){
		String username = "CRED";
		String password = "123";
		String url= "http://example.com";
		String usernameCredential = "doe";
		String passwordCredential ="JohnDoe";
		String url2= "http://example.org";
		String usernameCredential2 = "jane";
		String passwordCredential2 ="JaneDoe";
		signupPageActions(username,password);
		loginPageActions(username,password);
		HomePage homePage = new HomePage(driver);
		ResultPage resultPage = new ResultPage(driver);

		homePage.navigateToCredentials();
		wait.until(ExpectedConditions.visibilityOfElementLocated((By.id(homePage.credentialTable))));
		homePage.addCredentialClick();
		wait.until(ExpectedConditions.visibilityOfElementLocated((By.id(homePage.credentialModal))));
		homePage.addCredential(url,usernameCredential,passwordCredential);
		wait.until(ExpectedConditions.titleContains("Result"));
		resultPage.navigateToHomeFromSuccess();
		wait.until(ExpectedConditions.titleContains("Home"));

		homePage.navigateToCredentials();
		wait.until(ExpectedConditions.visibilityOfElementLocated((By.id(homePage.credentialTable))));
		homePage.editCredentialClick();
		wait.until(ExpectedConditions.visibilityOfElementLocated((By.id(homePage.credentialModal))));
		homePage.addCredential(url2,usernameCredential2,passwordCredential2);
		wait.until(ExpectedConditions.titleContains("Result"));
		resultPage.navigateToHomeFromSuccess();
		wait.until(ExpectedConditions.titleContains("Home"));

		homePage.navigateToCredentials();
		wait.until(ExpectedConditions.visibilityOfElementLocated((By.id(homePage.credentialTable))));
		homePage.deleteCredentialClick();
		wait.until(ExpectedConditions.titleContains("Result"));
		resultPage.navigateToHomeFromSuccess();
		wait.until(ExpectedConditions.titleContains("Home"));
	}



}
