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
    private final WebDriverWait wait;

    // Локатор баннера Куки
    private final By cookieConfirmButton = By.id("rcc-confirm-cookie");

    // Локатор верхней кнопки "Заказать" в шапке сайта
    private final By topOrderButton = By.xpath(".//div[contains(@class, 'Header_Nav__')]/button[text()='Заказать']");

    // Локатор нижней кнопки "Заказать" в центре страницы
    private final By bottomOrderButton = By.xpath(".//div[contains(@class, 'Home_ThirdPart')]//button[text()='Заказать']");

    // Конструктор класса
    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    // Локатор стрелочек вопросов в разделе "Вопросы о важном" (индекс от 0-7)
    private By getQuestionSelector(int index) {
        return By.id("accordion__heading-" + index);
    }

    // Локатор текста ответов (индекс от 0-7)
    private By getAnswerSelector(int index) {
        return By.id("accordion__panel-" + index);
    }

    // Вспомогательные методы (Утилиты для UI)
    private void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }
    // Метод для закрытия плашки куки
    public void acceptCookies() {
        try {
            driver.findElement(cookieConfirmButton).click();
        } catch (Exception e) {
            // Если плашка не появилась или уже закрыта, просто пропускаем шаг
        }
    }

    // Скролл и клик по вопросу в FAQ
    public void clickQuestion(int index) {
        WebElement questionElement = driver.findElement(getQuestionSelector(index));
        scrollToElement(questionElement);
        wait.until(ExpectedConditions.elementToBeClickable(questionElement)).click();
    }

    // Получение текста вопроса
    public String getQuestionText(int index) {
        return driver.findElement(getQuestionSelector(index)).getText();
    }

    // Получение текста ответа после клика по стрелке
     public String getFaqAnswerText(int index) {
         return wait.until(ExpectedConditions.visibilityOfElementLocated(getAnswerSelector(index))).getText();
     }

     // Метод кликает по кнопке "Заказать"
     public void clickTopOrderButton() {
         wait.until(ExpectedConditions.elementToBeClickable(topOrderButton)).click();
     }

     // Метод скролит и кликает по нижней кнопке "Заказать"
     public void clickBottomOrderButton() {
         WebElement bottomBtn = wait.until(ExpectedConditions.presenceOfElementLocated(bottomOrderButton));
         scrollToElement(bottomBtn);
         wait.until(ExpectedConditions.elementToBeClickable(bottomBtn)).click();
     }
}
