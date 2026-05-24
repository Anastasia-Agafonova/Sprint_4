package pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public class MainPage {
    private final WebDriver driver;

//локатор баннера Куки
private final By cookieConfirmButton = By.id("rcc-confirm-cookie");

// локатор верхней кнопки "Заказать" в шапке сайта
 private final By topOrderButton = By.xpath(".//div[contains(@class, 'Header_Nav__')]/button[text()='Заказать']");

// локатор нижней кнопки "Заказать"  в центре страницы
private final By bottomOrderButton = By.xpath(".//div[contains(@class, 'Home_ThirdPart')]//button[text()='Заказать']");

// локатор стрелочек вопросов в разделе "Вопросы о важном" (индекс от 0-7)
private By getQuestionSelector(int index) {
    return By.id("accordion__heading-" + index);
}
// локатор текста ответов (индекс от 0-7)
private By getAnswerSelector(int index) {
        return By.id("accordion__panel-" + index);
}
// конструктор класса
    public MainPage(WebDriver driver) {
    this.driver = driver;
    }
// метод для закрытия плашки куки
public void acceptCookies() {
        try {
            driver.findElement(cookieConfirmButton).click();
        } catch (Exception e) {
            // Если плашка не появилась или уже закрыта, просто пропускаем шаг
        }
    }
// скролл и клик по вопросу в FAQ
    public void clickQuestion(int index) {
        WebElement element = driver.findElement(getQuestionSelector(index));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.elementToBeClickable(element));
        element.click();
    }
// получение текста ответа после клика по стрелке
 public String getFaqAnswerText(int index) {
     new WebDriverWait(driver, Duration.ofSeconds(3))
             .until(ExpectedConditions.visibilityOfElementLocated(getAnswerSelector(index)));
    return driver.findElement(getAnswerSelector(index)).getText();
     }
// метод кликает по кнопке "Заказать"
public void clickTopOrderButton() {
    driver.findElement(topOrderButton).click();
     }

// метод скролит и кликает по нижней кнопке "Заказать"
public void clickBottomOrderButton() {
    new WebDriverWait(driver, Duration.ofSeconds(5))
            .until(ExpectedConditions.presenceOfElementLocated(bottomOrderButton));
    WebElement element = driver.findElement(bottomOrderButton);
    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    new WebDriverWait(driver, Duration.ofSeconds(3))
            .until(ExpectedConditions.elementToBeClickable(element));
    element.click();
   }
}
