package pageobject;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class OrderPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Локаторы: Шаг 1 "Для кого самокат"
    private final By firstNameField = By.xpath(".//input[@placeholder='* Имя']");
    private final By lastNameField = By.xpath(".//input[@placeholder='* Фамилия']");
    private final By addressField = By.xpath(".//input[@placeholder='* Адрес: куда привезти заказ']");
    private final By metroStationField = By.xpath(".//input[@placeholder='* Станция метро']");
    private final By metroSelectOption = By.className("select-search__row"); // Выбор первого совпадения
    private final By phoneField = By.xpath(".//input[@placeholder='* Телефон: на него позвонит курьер']");
    private final By nextButton = By.xpath(".//button[text()='Далее']");

    // Локаторы: Шаг 2 "Про аренду"
    private final By dateField = By.xpath(".//input[@placeholder='* Когда привезти самокат']");
    private final By rentalPeriodDropdown = By.className("Dropdown-placeholder");
    private final By blackColorCheckbox = By.id("black");
    private final By greyColorCheckbox = By.id("grey");
    private final By commentField = By.xpath(".//input[@placeholder='Комментарий для курьера']");
    private final By orderSubmitButton = By.xpath(".//div[contains(@class, 'Order_Buttons__')]//button[text()='Заказать']");
    private final By confirmYesButton = By.xpath(".//button[text()='Да']");
    private final By successPopupHeader = By.xpath(".//div[contains(@class, 'Order_ModalHeader__3FDaJ') and contains(text(), 'Заказ оформлен')]");

    // Конструктор
    public OrderPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(3));
    }

    // Вспомогательный метод для выбора срока аренды
    private By getPeriodOptionLocator(String period) {
        return By.xpath(".//div[@class='Dropdown-option' and text()='" + period + "']");
    }

    // Вспомогательные методы (Утилиты для UI)
    private void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
    }

    private void clickViaJs(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    private void selectScooterColor(String color) {
        if ("черный".equalsIgnoreCase(color)) {
            driver.findElement(blackColorCheckbox).click();
        } else if ("серый".equalsIgnoreCase(color)) {
            driver.findElement(greyColorCheckbox).click();
        }
    }

    // Заполнение данных клиента (ФИО, адрес, метро, телефон)
    public void fillCustomerInfo(String firstName, String lastName, String address, String metro, String phone) {
        driver.findElement(firstNameField).sendKeys(firstName);
        driver.findElement(lastNameField).sendKeys(lastName);
        driver.findElement(addressField).sendKeys(address);

        WebElement metroInput = driver.findElement(metroStationField);
        metroInput.click();
        metroInput.sendKeys(metro);

        WebElement metroOption = wait.until(ExpectedConditions.visibilityOfElementLocated(metroSelectOption));
        scrollToElement(metroOption);
        metroOption.click();

        driver.findElement(phoneField).sendKeys(phone);
        WebElement nextBtn = wait.until(ExpectedConditions.elementToBeClickable(nextButton));
        clickViaJs(nextBtn);
    }

    // Заполнение деталей аренды (дата, срок, цвет, комментарий)
    public void fillRentalDetails(String date, String period, String color, String comment) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(dateField));
        driver.findElement(dateField).sendKeys(date);
        driver.findElement(dateField).sendKeys(Keys.ENTER);
        driver.findElement(By.tagName("body")).click();

        driver.findElement(rentalPeriodDropdown).click();
        By periodLocator = getPeriodOptionLocator(period);
        wait.until(ExpectedConditions.elementToBeClickable(periodLocator)).click();

        selectScooterColor(color);
        driver.findElement(commentField).sendKeys(comment);

        WebElement submitBtn = driver.findElement(orderSubmitButton);
        scrollToElement(submitBtn);
        wait.until(ExpectedConditions.elementToBeClickable(submitBtn)).click();

        WebElement yesBtn = wait.until(ExpectedConditions.elementToBeClickable(confirmYesButton));
        scrollToElement(yesBtn);
        yesBtn.click(); // При баге кнопка не нажмется - тест упадет здесь
    }

    public boolean isSuccessPopupDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(successPopupHeader)).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }
}

