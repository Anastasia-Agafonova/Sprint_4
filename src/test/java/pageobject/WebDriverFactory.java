package pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class WebDriverFactory {
    public static WebDriver createDriver(String browserName) {
    switch (browserName.toLowerCase()) {
    case "chrome":
    return new ChromeDriver();
    case "firefox":
    return new FirefoxDriver();
    default:
    throw new IllegalArgumentException("Браузер '" + browserName + "' не поддерживается. Используйте 'chrome' или 'firefox'.");
        }
    }
}
