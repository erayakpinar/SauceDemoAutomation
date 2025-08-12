package steps;

import com.thoughtworks.gauge.Step;
import org.openqa.selenium.WebDriver;
import pages.LoginPage;
import utils.DriverFactory;

public class LoginSteps {
    WebDriver driver = DriverFactory.getDriver();
    LoginPage loginPage = new LoginPage(driver);

    @Step("Kullanıcı <username> ve <password> bilgileri ile giriş yapar")
    public void login(String username, String password) {
        driver.get("https://www.saucedemo.com/");
        loginPage.login(username, password);
    }
}
