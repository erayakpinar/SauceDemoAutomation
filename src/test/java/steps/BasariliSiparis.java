package steps;

import com.thoughtworks.gauge.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pages.LoginPage;
import utils.DriverFactory;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasariliSiparis {
    private static final Logger logger = LoggerFactory.getLogger(BasariliSiparis.class);

    WebDriver driver = DriverFactory.getDriver();
    LoginPage loginPage = new LoginPage(driver);

    @Step("Kullanıcı <username> ve <password> bilgileri ile giriş yapar 3")
    public void login(String username, String password) {
        driver.get("https://www.saucedemo.com/");
        loginPage.login(username, password);
    }

    @Step("Kullanıcı sepete rastgele ürün ekler")
    public void rastgeleEkle() {
        try {
            driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
            Thread.sleep(1000);
            driver.findElement(By.id("add-to-cart-sauce-labs-bike-light")).click();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Step("Kullanıcı sepet butonuna tıklar")
    public void sepetTikla() {
        try {
            Thread.sleep(1000);
            driver.findElement(By.id("shopping_cart_container")).click();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Step("Kullanıcı checkout butonuna tiklar")
    public void checkoutTikla() {
        try {
            Thread.sleep(1000);
            driver.findElement(By.id("checkout")).click();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Step("Kullanıcı <Firstname>, <Lastname> ve <ZipPostalCode> bilgileri ile Continue tuşuna basar.")
    public void formDoldur(String Firstname, String Lastname, String ZipPostalCode) {
        try {
            Thread.sleep(1000);
            driver.findElement(By.id("first-name")).sendKeys(Firstname);
            Thread.sleep(1000);
            driver.findElement(By.id("last-name")).sendKeys(Lastname);
            Thread.sleep(1000);
            driver.findElement(By.id("postal-code")).sendKeys(ZipPostalCode);
            Thread.sleep(1000);
            driver.findElement(By.id("continue")).click();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Step("Kullanıcı finish butonuna tiklar")
    public void finishTikla() {
        try {
            Thread.sleep(1000);
            driver.findElement(By.id("finish")).click();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Step("Kullanıcı başarılı sipariş mesajını görür")
    public void mesajDogrulama() {
        try {
            WebElement successMessage = driver.findElement(
                    By.xpath("//h2[normalize-space()='Thank you for your order!']")
            );

            if (successMessage.isDisplayed()) {
                logger.info("Başarılı sipariş mesajı görüldü, test geçti.");
            } else {
                throw new AssertionError("Başarılı sipariş mesajı görünmedi!");
            }

        } catch (NoSuchElementException e) {
            throw new AssertionError("Başarılı sipariş mesajı bulunamadı!", e);
        }
        finally {
            driver.quit();
        }
    }
}
