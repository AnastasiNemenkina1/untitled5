package ru.netology;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import ru.netology.pages.OrderPage;
import io.github.bonigarcia.wdm.WebDriverManager;

import static org.junit.jupiter.api.Assertions.*;

public class OrderTest {
    private WebDriver driver;
    private OrderPage orderPage;

    @BeforeAll
    static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setup() {
        driver = new ChromeDriver();
        orderPage = new OrderPage(driver);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    @DisplayName("Успешная отправка формы")
    void shouldSubmitForm() {
        orderPage.fillName("Анастасия Гаврина");
        orderPage.fillPhone("+79292621111");
        orderPage.checkAgreement();
        orderPage.submit();

        assertEquals("Ваша заявка успешно отправлена!", orderPage.getSuccessMessage());
    }

    @Test
    @DisplayName("Ошибка при неверном имени")
    void shouldShowErrorForInvalidName() {
        orderPage.fillName("Anastasia Gavrina");
        orderPage.fillPhone("+79292621111");
        orderPage.checkAgreement();
        orderPage.submit();

        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.",
                orderPage.getErrorMessage());
    }

    @Test
    @DisplayName("Ошибка при неверном телефоне")
    void shouldShowErrorForInvalidPhone() {
        orderPage.fillName("Анастасия Гаврина");
        orderPage.fillPhone("+79292621111");
        orderPage.checkAgreement();
        orderPage.submit();

        assertEquals("Телефон указан неверно. Должно быть 11 цифр после +.",
                orderPage.getErrorMessage());
    }

    @Test
    @DisplayName("Ошибка при отсутствии согласия")
    void shouldShowErrorWithoutAgreement() {
        orderPage.fillName("Анастасия Гаврина");
        orderPage.fillPhone("+79292621111");
        orderPage.submit();

        assertEquals("Я соглашаюсь с условиями обработки и использования моих персональных данных",
                orderPage.getErrorMessage());
    }
}
@ParameterizedTest
@CsvSource({
        "Анастасия Гаврина, +79292621111, true",
        "Gavrina, +79292621111, false",
        "Гаврина-Неменкина, +79292621111, true"
})
void testNameValidation(String name, String phone, boolean valid) {
    orderPage.fillName(name);
    orderPage.fillPhone(phone);
    orderPage.checkAgreement();
    orderPage.submit();

    if (valid) {
        assertEquals("Ваша заявка успешно отправлена!", orderPage.getSuccessMessage());
    } else {
        assertTrue(orderPage.getErrorMessage().contains("Имя и Фамилия указаные неверно"));
    }
}