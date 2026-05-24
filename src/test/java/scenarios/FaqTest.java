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
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class FaqTest {
private WebDriver driver;
private final int index;
private final String expectedAnswer;

public FaqTest(int index, String expectedAnswer) {
    this.index = index;
    this.expectedAnswer = expectedAnswer;
    }
@Parameterized.Parameters
public static Object[] [] getTestData() {
    return new Object[][] {
     {0, "Сутки — 400 рублей. Оплата курьеру — наличными или картой."},
     {1, "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете оформить несколько заказов — один за другим."},
     {2, "Допустим, вы оформляете заказ на 10 мая. Вариантов времени три: в течение дня с 00:00 до 23:59, в первой половине дня с 00:00 до 16:00, во второй половине дня с 16:00 до 23:59."},
     {3, "Только при наличии товара на складе. Обычно мы привозим самокаты назад в течение суток после окончания аренды."},
     {4, "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010."},
     {5, "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится."},
     {6, "Да, пока самокат не завезли курьеры. Чтобы отменить заказ, зайдите в Личный кабинет и нажмите кнопку «Отменить заказ»."},
     {7, "Да, мы привозим самокаты за МКАД, но в пределах доступных зон. Проверить зону можно на карте на главной странице."}
    };
}
@Before
    public void setUp() {
    ChromeOptions options = new ChromeOptions();
    options.addArguments("--no-sandbox", "--headless", "--disable-dev-shm-usage");
    driver = new ChromeDriver(options);
    driver.manage().window().maximize();
    driver.get("https://qa-scooter.praktikum-services.ru/");
}
@Test
    public void  testDropdownTextAppears() {
    MainPage mainPage = new MainPage(driver);
    mainPage.acceptCookies();
    mainPage.clickQuestion(index);
    String actualAnswer = mainPage.getFaqAnswerText(index);
    assertEquals("Текст ответа под стрелкой не совпадает с ожидаемым!", expectedAnswer, actualAnswer);
}
@After
    public void tearDown() {
    if (driver != null) {
     driver.quit();
        }
    }
}
