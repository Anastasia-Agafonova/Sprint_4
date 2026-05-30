package pageobject;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OrderPage {
private final WebDriver driver;

// локаторы: Шаг 1 "Для кого самокат"
// Поле ввода "Имя"
private final By firstNameField = By.xpath(".//input[@placeholder='* Имя']");
// Поле ввода "Фамилия"
private final By lastNameField = By.xpath(".//input[@placeholder='* Фамилия']");
// Поле ввода "Адрес"
private final By addressField = By.xpath(".//input[@placeholder='* Адрес: куда привезти заказ']");
// Поле ввода "Станция метро"
private final By metroStationField = By.xpath(".//input[@placeholder='* Станция метро']");
// Строка из выпадающего списка метро
private final By metroSelectOption = By.className("select-search__row"); // Выбор первого совпадения
// Поле ввода "Телефон"
private final By phoneField = By.xpath(".//input[@placeholder='* Телефон: на него позвонит курьер']");
// Кнопка перехода к следующему шагу "Далее"
private final By nextButton = By.xpath(".//button[text()='Далее']");

// локаторы: Шаг 2 "Про аренду"
// Поле ввода даты Когда привезти самокат
private final By dateField = By.xpath(".//input[@placeholder='* Когда привезти самокат']");
// Выпадающий список Срок аренды
private final By rentalPeriodDropdown = By.className("Dropdown-placeholder");
// Чекбокс выбора черного цвета
private final By blackColorCheckbox = By.id("black");
// Чекбокс выбора черного цвета
private final By greyColorCheckbox = By.id("grey");
// Поле ввода Комментарий для курьера
private final By commentField = By.xpath(".//input[@placeholder='Комментарий для курьера']");
// кнопка отправки формы
private final By orderSubmitButton = By.xpath(".//div[contains(@class, 'Order_Buttons__')]//button[text()='Заказать']");
// кнопка подтверждения действия "Да"
private final By confirmYesButton = By.xpath(".//button[text()='Да']");
// Локатор для заголовка всплывающего окна успешного заказа
private final By successPopupHeader = By.xpath(".//div[contains(@class, 'Order_ModalHeader__3FDaJ') and contains(text(), 'Заказ оформлен')]");

// Динамический выбор конкретного срока аренды
private By getPeriodOptionLocator(String period) {
    return By.xpath(".//div[@class='Dropdown-option' and text()='" + period + "']");
}

public OrderPage(WebDriver driver) {
   this.driver = driver;
    }

// Заполнение данных клиента
public void fillCustomerInfo(String firstName, String lastName, String address, String metro, String phone) {
   driver.findElement(firstNameField).sendKeys(firstName);
   driver.findElement(lastNameField).sendKeys(lastName);
   driver.findElement(addressField).sendKeys(address);

    WebElement metroInput = driver.findElement(metroStationField);
    metroInput.click();
    metroInput.sendKeys(metro);
    new WebDriverWait(driver, Duration.ofSeconds(3))
            .until(ExpectedConditions.visibilityOfElementLocated(metroSelectOption));
    WebElement metroOption = driver.findElement(metroSelectOption);
    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", metroOption);
    metroOption.click();

    driver.findElement(phoneField).sendKeys(phone);
    new WebDriverWait(driver, Duration.ofSeconds(3))
            .until(ExpectedConditions.elementToBeClickable(nextButton));
    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(nextButton));
    }

// Заполнение деталей аренды
public void fillRentalDetails(String date, String period, String color, String comment) {
    new WebDriverWait(driver, Duration.ofSeconds(3))
            .until(ExpectedConditions.visibilityOfElementLocated(dateField));

    driver.findElement(dateField).sendKeys(date);
    driver.findElement(dateField).sendKeys(Keys.ENTER);
// Закрываем календарь кликом по фону страницы
    driver.findElement(By.tagName("body")).click();

    driver.findElement(rentalPeriodDropdown).click();
    By periodLocator = getPeriodOptionLocator(period);
    new WebDriverWait(driver, Duration.ofSeconds(3))
            .until(ExpectedConditions.elementToBeClickable(periodLocator));
    driver.findElement(periodLocator).click();

        if ("черный".equalsIgnoreCase(color)) {
            driver.findElement(blackColorCheckbox).click();
        } else if ("серый".equalsIgnoreCase(color)) {
            driver.findElement(greyColorCheckbox).click();
        }
    driver.findElement(commentField).sendKeys(comment);

// Кнопка "Заказать" — скролл + ожидание + обычный клик
    WebElement submitBtn = driver.findElement(orderSubmitButton);
    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", submitBtn);
    new WebDriverWait(driver, Duration.ofSeconds(3))
            .until(ExpectedConditions.elementToBeClickable(submitBtn));
    submitBtn.click();

// Кнопка подтверждения "Да" в модальном окне
    new WebDriverWait(driver, Duration.ofSeconds(3))
            .until(ExpectedConditions.elementToBeClickable(confirmYesButton));
    WebElement yesBtn = driver.findElement(confirmYesButton);
    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", yesBtn);
    yesBtn.click(); // При баге тест упадет здесь с ElementClickInterceptedException
    }
}

