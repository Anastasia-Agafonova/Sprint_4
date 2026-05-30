package pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;


public class WebDriverFactory {
    private static final boolean HEADLESS_MODE = false;
    public static WebDriver createDriver(String browserName) {
    switch (browserName.toLowerCase()) {
    case "chrome":
    ChromeOptions options = new ChromeOptions();
    options.addArguments("--no-sandbox", "--disable-dev-shm-usage");
        if (HEADLESS_MODE) {
            options.addArguments("--headless=new");
        }
    return new ChromeDriver(options);

    case "firefox":
    FirefoxOptions ffOptions = new FirefoxOptions();
        if (HEADLESS_MODE) {
            ffOptions.addArguments("--headless");
        }
            return new FirefoxDriver(ffOptions);

            default:
                throw new IllegalArgumentException("Браузер '" + browserName + "' не поддерживается. Используйте 'chrome' или 'firefox'.");
        }
    }
    }

