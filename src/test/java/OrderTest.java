package ru.netology;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import ru.netology.pages.OrderPage;
import io.github.bonigarcia.wdm.WebDriverManager;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {
    private WebDriver driver;
    private OrderPage orderPage;

    @BeforeAll
    static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
        orderPage = new OrderPage(driver);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @DisplayName("Успешная отправка формы")
    void shouldSubmitValidForm() {
        orderPage.fillName("Иванов Иван");
        orderPage.fillPhone("+79270000000");
        orderPage.checkAgreement();
        orderPage.submit();

        assertEquals("Ваша заявка успешно отправлена!",
                orderPage.getSuccessMessage());
    }
}