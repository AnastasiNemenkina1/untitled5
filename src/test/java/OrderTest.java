package ru.netology;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import ru.netology.page.OrderPage;
import java.time.Duration;
import static org.junit.jupiter.api.Assertions.*;

class OrderTest {
    private static WebDriver driver;
    private static OrderPage orderPage;

    @BeforeAll
    static void setupAll() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments(
                "--start-maximized",
                "--remote-allow-origins=*",
                "--disable-dev-shm-usage",
                "--no-sandbox"
        );
        driver = new ChromeDriver(options);
        orderPage = new OrderPage(driver);
    }

    @BeforeEach
    void setup() {
        driver.get("http://localhost:9999");
    }

    // Позитивный тест
    @Test
    @DisplayName("Успешная отправка формы с валидными данными")
    void shouldSubmitValidForm() {
        orderPage.fillName("Иванов-Петров Иван");
        orderPage.fillPhone("+79270000000");
        orderPage.fillCity("Москва");
        orderPage.checkAgreement();
        orderPage.submit();

        String actual = orderPage.getSuccessMessage();
        assertTrue(actual.contains("Успешно"),
                "Фактический результат: " + actual);
    }

    // Тесты валидации имени
    @Test
    @DisplayName("Ошибка при вводе имени латиницей")
    void shouldShowErrorForLatinName() {
        orderPage.fillName("Ivanov Ivan");
        orderPage.submit();
        assertEquals("Допустимы только русские буквы, пробелы и дефисы",
                orderPage.getNameError());
    }

    @Test
    @DisplayName("Ошибка при пустом поле имени")
    void shouldShowErrorForEmptyName() {
        orderPage.fillName("");
        orderPage.submit();
        assertEquals("Поле обязательно для заполнения",
                orderPage.getNameError());
    }

    // Тесты валидации телефона
    @Test
    @DisplayName("Ошибка при отсутствии + в телефоне")
    void shouldShowErrorForPhoneWithoutPlus() {
        orderPage.fillPhone("79270000000");
        orderPage.submit();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678",
                orderPage.getPhoneError());
    }

    @Test
    @DisplayName("Ошибка при коротком номере телефона")
    void shouldShowErrorForShortPhone() {
        orderPage.fillPhone("+79270000");
        orderPage.submit();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678",
                orderPage.getPhoneError());
    }

    // Тест согласия
    @Test
    @DisplayName("Ошибка при отсутствии согласия")
    void shouldShowErrorForUncheckedAgreement() {
        orderPage.fillName("Иванов Иван");
        orderPage.fillPhone("+79270000000");
        orderPage.fillCity("Москва");
        // Чекбокс не отмечаем
        orderPage.submit();
        assertTrue(orderPage.getAgreementError().contains("Необходимо согласие"));
    }

    @AfterAll
    static void tearDown() {
        driver.quit();
    }
}