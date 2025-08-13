package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverFactory {
    private static WebDriver driver;

    private DriverFactory() {}

    public static WebDriver getDriver() {
        if (driver == null) {
            WebDriverManager.chromedriver().setup();

            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized");
            options.addArguments("--disable-notifications"); // Bildirimleri kapatır
            options.addArguments("--disable-popup-blocking"); // Popup engelleyici kapatılır
            options.addArguments("--disable-save-password-bubble"); // Şifre kaydetme balonunu kapatır

            // Çalıntı şifre uyarısını kapatmak için Chrome Preferences
            options.addArguments("disable-features=PasswordLeakDetection");

            driver = new ChromeDriver(options);
        }
        return driver;
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
