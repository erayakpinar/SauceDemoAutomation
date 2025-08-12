package steps;

import com.thoughtworks.gauge.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pages.LoginPage;
import utils.DriverFactory;

public class LogoutSteps {

    WebDriver driver = DriverFactory.getDriver();
    LoginPage loginPage = new LoginPage(driver);

    @Step("Kullanıcı <username> ve <password> bilgileri ile giriş yapar 2")
    public void login(String username, String password) {
        driver.get("https://www.saucedemo.com/");
        loginPage.login(username, password);
    }

    @Step("Kullanıcı menü tuşuna basar")
    public void menuTusunaBas(){
        By menuTusu = By.id("react-burger-menu-btn");

        driver.findElement(menuTusu).click();

    }

    @Step("Kullanıcı logout yazısına tıklar")
    public void logoutYazisinaTiklar(){
        By logoutTusu = By.id("logout_sidebar_link");
        driver.findElement(logoutTusu).click();
    }

}
