package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;
import pages.LoginPage;
import responseObject.ResponseToken;
import responseObject.ResponseUser;
import requestObject.RequestUser;
import services.AccountService;

import java.time.Duration;

public class CreateUserBETest {

    public AccountService accountService;
    public RequestUser requestBody;
    public WebDriver driver;
    public String userId;
    public String token;

    @Test
    public void testMethod() {
        System.out.println("====== STEP 1: Create account ======");
        createAccount();

        System.out.println("====== STEP 2: Generate token ======");
        generateToken();

        System.out.println("====== STEP 3: Get user details ======");
        validateAccountBE();

        System.out.println("====== STEP 4: Delete user ======");
        deleteAccountBE();

        System.out.println("====== STEP 5: Login user ======");
        loginApplication();

        System.out.println("====== STEP 6: Get user details ======");
        validateAccountBE();


    }

    public void createAccount() {
        requestBody = new RequestUser("src/test/resources/createUser.json");
        accountService = new AccountService();
        ResponseUser responseBody = accountService.createAccount(requestBody);
        userId = responseBody.getUserId();
    }

    public void generateToken() {
        ResponseToken responseBody = accountService.generateToken(requestBody);
        token = responseBody.getToken();
    }

    public void validateAccountBE() {
        accountService.validateAccountBE(token, userId);
    }

    public void loginApplication() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-search-engine-choice-screen");
        driver = new ChromeDriver(options);
        driver.get("https://demoqa.com/login");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginIntoApplication(requestBody);
        loginPage.validateLoginError();
    }

    public void deleteAccountBE() {
        accountService.deleteAccountBE(token, userId);
    }
}
