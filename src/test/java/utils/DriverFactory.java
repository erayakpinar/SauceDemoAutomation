package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Map;

public class DriverFactory {
    private static WebDriver driver;

    private DriverFactory() {}

    public static WebDriver getDriver() {
        if (driver == null) {
            WebDriverManager.chromedriver().setup();

            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized");
            options.addArguments("--disable-notifications");
            options.addArguments("--disable-popup-blocking");
            options.addArguments("--disable-save-password-bubble");
            options.addArguments("--disable-features=PasswordLeakDetection");
            options.addArguments("--disable-infobars");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            // Benzersiz port ve user data directory - session çakışmasını önler
            long timestamp = System.currentTimeMillis();
            int port = 9222 + (int)(timestamp % 1000); // Benzersiz port
            options.addArguments("--remote-debugging-port=" + port);

            String userDataDir = System.getProperty("java.io.tmpdir") +
                    "/chrome_test_" + timestamp;
            options.addArguments("--user-data-dir=" + userDataDir);

            // Ek stabilite için
            options.addArguments("--disable-background-timer-throttling");
            options.addArguments("--disable-renderer-backgrounding");
            options.addArguments("--disable-backgrounding-occluded-windows");

            // Şifre yöneticisi ve otomatik doldurma popup'larını kapatır
            options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation", "enable-logging"});
            options.setExperimentalOption("prefs", Map.of(
                    "credentials_enable_service", false,
                    "profile.password_manager_enabled", false
            ));

            // İsteğe bağlı: misafir modunda başlatmak popup'ı engellemek için
            // options.addArguments("--guest");

            driver = new ChromeDriver(options);
        }
        return driver;
    }

    public static void quitDriver() {
        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception e) {
                // Sessizce devam et
            } finally {
                driver = null;
            }
        }
    }

    // Tüm Chrome süreçlerini temizle (Windows için)
    public static void killChromeProcesses() {
        try {
            if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                Runtime.getRuntime().exec("taskkill /f /im chrome.exe");
                Runtime.getRuntime().exec("taskkill /f /im chromedriver.exe");
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            // Sessizce devam et
        }
    }
}