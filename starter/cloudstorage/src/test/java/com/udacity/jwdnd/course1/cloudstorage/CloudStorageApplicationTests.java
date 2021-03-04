package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private static WebDriver driver;
	private String baseURL ;

	private SignupPage signupPage = new SignupPage(driver);
	private LoginPage loginPage = new LoginPage(driver);
	private HomePage homePage = new HomePage(driver);
	private ResultPage resultPage = new ResultPage(driver);

	String firstName = "Donat";
	String lastName = "Script";
	String username = "DonatScript";
	String password = "123456";

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
	}

	@BeforeEach
	public void beforeEach() throws InterruptedException {
		baseURL = "http://localhost:"+ this.port;
	}

	@AfterAll
	static void AfterAll()	 {
		if (driver != null) {
			driver.quit();
		}
	}

	@Test
	@Order(1)
	public void getSignupPage() throws InterruptedException {
		driver.get(baseURL  + "/signup");
		signupPage.signup(firstName,lastName,username,password);
		Assertions.assertTrue(signupPage.isAlertDisplayed());
	}

	@Test
	@Order(2)
	public void getLoginPage() throws InterruptedException {
		driver.get(baseURL  + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
		loginPage.login(username,password);
		Assertions.assertEquals("Home", driver.getTitle());
	}

	@Test
	@Order(10)
	public void homePageIsNotAccessible() throws InterruptedException {
		homePage.logout();
		Assertions.	assertNotEquals("Home", driver.getTitle());
		Assertions.assertNotEquals(baseURL + "/home",driver.getCurrentUrl());
	}

	@Test
	@Order(9)
	public void userNotAbleToAccessAfterLogout() throws InterruptedException {
		homePage.logout();
		Assertions.assertTrue(loginPage.isAlertDisplayed());

		driver.get(baseURL + "/home");
		Assertions.assertEquals("Home", driver.getTitle());
		Assertions.assertEquals(baseURL +"/home",driver.getCurrentUrl());
	}

	@Test
	@Order(3)
	public void testCreateNote() throws InterruptedException {
		driver.get(baseURL + "/home");
		String noteTitle = "Java Web Development";
		String noteDescription = "Selenium Test is going on";
		homePage.clickNavTabNote();
		homePage.clickAddNoteButton();
		homePage.createNote(noteTitle,noteDescription);
		Thread.sleep(1000);
		resultPage.clickAnchor();

		WebDriverWait wait = new WebDriverWait(driver, 5);
		List<WebElement> userTable = wait.until(webDriver -> driver.findElements(By.cssSelector("#userTable > tbody > tr > th")));
		for(WebElement titleElement: userTable){
			if(titleElement.getText().equals(noteTitle)){
				Assertions.assertEquals(noteTitle,titleElement.getText());
			}
		}
	}

	@Test
	@Order(4)
	public void testUpdateNote() throws InterruptedException {
		homePage.clickNavTabNote();
		WebDriverWait wait = new WebDriverWait(driver, 5);
		WebElement editElement = wait.until(webDriver ->
			driver.findElement(By.cssSelector("#userTable > tbody > tr > td > button"))
		);
		Thread.sleep(1000);
		editElement.click();
		String noteTitle = "Udacity";
		String noteDescription = "Testing is pretty cool part";
		homePage.createNote(noteTitle,noteDescription);;
		Thread.sleep(1000);
		resultPage.clickAnchor();

		wait = new WebDriverWait(driver, 5);
		List<WebElement> userTable = wait.until(webDriver -> driver.findElements(By.cssSelector("#userTable > tbody > tr > th")));
		for(WebElement titleElement: userTable){
			if(titleElement.getText().equals(noteTitle)){
				Assertions.assertEquals(noteTitle,titleElement.getText());
			}
		}
	}

	@Test
	@Order(5)
	public void testDeleteNote() throws InterruptedException {
		homePage.clickNavTabNote();
		WebDriverWait wait = new WebDriverWait(driver, 5);
		WebElement deleteElement = wait.until(webDriver ->
				driver.findElement(By.cssSelector("#userTable > tbody > tr > td > a"))
		);
		Thread.sleep(1000);
		Assertions.assertTrue(deleteElement.isDisplayed());
		deleteElement.click();
		Thread.sleep(1000);
		resultPage.clickAnchor();
		Thread.sleep(1000);
		homePage.clickNavTabNote();
		Thread.sleep(1000);
		wait = new WebDriverWait(driver, 5);
		WebElement tableBody = wait.until(webDriver ->
				driver.findElement(By.cssSelector("#userTable > tbody"))
		);
		Assertions.assertEquals(0, tableBody.getSize().height);
	}

	@Test
	@Order(6)
	public void testCreateCredential() throws InterruptedException {
		String credentialUrl = "https://udacity.com";
		String credentialUsername = "Donat";
		String credentialPassword = "123456";
		homePage.clickNavTabCredential();
		homePage.clickAddCredentialButton();
		homePage.createCredential(credentialUrl,credentialUsername,credentialPassword);
		Thread.sleep(1000);
		resultPage.clickAnchor();

		WebDriverWait wait = new WebDriverWait(driver, 5);
		List<WebElement> credentialTable = wait.until(webDriver ->
				driver.findElements(By.cssSelector("#credentialTable > tbody > tr > th"))
		);
		for(WebElement element: credentialTable) {
			if (element.getText().equals(credentialUrl)) {
				Assertions.assertEquals(credentialUrl,element.getText());
			}
		}
	}

	@Test
	@Order(7)
	public void testUpdateCredential() throws InterruptedException {
		homePage.clickNavTabCredential();
		WebDriverWait wait = new WebDriverWait(driver, 5);
		WebElement editElement = wait.until(webDriver ->
				driver.findElement(By.cssSelector("#credentialTable > tbody > tr > td > button"))
		);
		Thread.sleep(1000);
		editElement.click();
		String credentialUrl = "https://google.com";
		String credentialUsername = "Admin";
		String credentialPassword = "Jasmin@123";
		homePage.createCredential(credentialUrl,credentialUsername,credentialPassword);
		Thread.sleep(1000);
		resultPage.clickAnchor();

		homePage.clickNavTabCredential();
		wait = new WebDriverWait(driver, 5);
		List<WebElement> userTable = wait.until(webDriver -> driver.findElements(By.cssSelector("#credentialTable > tbody > tr > th")));
		for(WebElement titleElement: userTable){
			if(titleElement.getText().equals(credentialUrl)){
				Assertions.assertEquals(credentialUrl,titleElement.getText());
			}
		}
	}

	@Test
	@Order(8)
	public void testDeleteCredential() throws InterruptedException {
		homePage.clickNavTabCredential();
		WebDriverWait wait = new WebDriverWait(driver, 3);
		WebElement deleteElement = wait.until(webDriver ->
			driver.findElement(By.cssSelector("#credentialTable > tbody > tr > td > a"))
		);
		Thread.sleep(1000);
		Assertions.assertTrue(deleteElement.isDisplayed());
		deleteElement.click();
		Thread.sleep(1000);
		resultPage.clickAnchor();
		Thread.sleep(1000);
		homePage.clickNavTabCredential();
		Thread.sleep(1000);
		wait = new WebDriverWait(driver, 5);
		WebElement tableBody = wait.until(webDriver ->
				driver.findElement(By.cssSelector("#credentialTable > tbody"))
		);
		Assertions.assertEquals(0, tableBody.getSize().height);
	}
}
