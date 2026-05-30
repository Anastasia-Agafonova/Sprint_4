package scenarios;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import pageobject.MainPage;
import pageobject.WebDriverFactory;


import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class FaqTest {
private WebDriver driver;
private final int index;
private final String expectedAnswer;
private final String expectedQuestion;
private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru/";

public FaqTest(int index, String expectedQuestion, String expectedAnswer) {
    this.index = index;
    this.expectedQuestion = expectedQuestion;
    this.expectedAnswer = expectedAnswer;
    }
@Parameterized.Parameters
public static Object[] [] getTestData() {
    return new Object[][] {
     {0, "Сколько это стоит? И как оплатить?", "Сутки — 400 рублей. Оплата курьеру — наличными или картой."},
     {1, "Хочу сразу несколько самокатов! Так можно?", "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете оформить несколько заказов — один за другим."},
     {2, "Как рассчитывается время аренды?", "Допустим, вы оформляете заказ на 10 мая. Вариантов времени три: в течение дня с 00:00 до 23:59, в первой половине дня с 00:00 до 16:00, во второй половине дня с 16:00 до 23:59."},
     {3, "Можно ли заказать самокат прямо на сегодня?", "Только при наличии товара на складе. Обычно мы привозим самокаты назад в течение суток после окончания аренды."},
     {4, "Можно ли продлить заказ или вернуть самокат раньше?", "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010."},
     {5, "Вы привозите зарядку вместе с самокатом?", "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится."},
     {6, "Можно ли отменить заказ?", "Да, пока самокат не завезли курьеры. Чтобы отменить заказ, зайдите в Личный кабинет и нажмите кнопку «Отменить заказ»."},
     {7, "Я жизу за МКАДом, привезёте?", "Да, мы привозим самокаты за МКАД, но в пределах доступных зон. Проверить зону можно на карте на главной странице."}
    };
}
@Before
    public void setUp() {
    driver = WebDriverFactory.createDriver("chrome");
    driver.manage().window().maximize();
    driver.get(BASE_URL);
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
