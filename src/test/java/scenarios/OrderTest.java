package scenarios;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pageobject.MainPage;
import pageobject.OrderPage;
import pageobject.WebDriverFactory;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class OrderTest {
    private WebDriver driver;

    private final String buttonLocation; // bottom или top
    private final String firstName;
    private final String lastName;
    private final String address;
    private final String metro;
    private final String phone;
    private final String date;
    private final String period;
    private final String color;
    private final String comment;

    public OrderTest(String buttonLocation, String firstName, String lastName, String address,
                            String metro, String phone, String date, String period, String color, String comment) {
        this.buttonLocation = buttonLocation;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metro = metro;
        this.phone = phone;
        this.date = date;
        this.period = period;
        this.color = color;
        this.comment = comment;
    }
// Параметризация: 2 разных набора данных, запускающих точки входа (верхняя и нижняя кнопки)
   @Parameterized.Parameters
    public static Object[][] getOrderData() {
         return new Object[][]{
            {"top", "Арсений", "Соколов", "Москва, Чистопрудный бульвар, д. 1А", "Чистые пруды", "+79991212121", "20.05.2026", "сутки", "серый", "Позвонить за пол часа"},
            {"bottom", "Анастасия", "Агафонова", "Москва, ул. Мясницкая, д. 3", "Лубянка", "89991122334", "22.05.2026", "двое суток", "черный", ""}
            };
        }
    @Before
    public void setUp() {
        driver = WebDriverFactory.createDriver("chrome");
        driver.manage().window().maximize();
        driver.get("https://qa-scooter.praktikum-services.ru/");
    }
    @Test
    public void testOrderScooterFlow () {
        MainPage mainPage = new MainPage(driver);
        OrderPage orderPage = new OrderPage(driver);

        mainPage.acceptCookies();

        if ("top".equals(buttonLocation)) {
            mainPage.clickTopOrderButton();
        } else {
            mainPage.clickBottomOrderButton();
        }
// Выполнение последовательных шагов заказа
        orderPage.fillCustomerInfo(firstName, lastName, address, metro, phone);
        orderPage.fillRentalDetails(date, period, color, comment);

// Проверка успешного создания заказа
        assertTrue("Окно успешного заказа не появилось!", orderPage.isSuccessPopupDisplayed());
    }
    @After
    public void tearDown () {
        if (driver != null) {
            driver.quit();
        }
    }
}
