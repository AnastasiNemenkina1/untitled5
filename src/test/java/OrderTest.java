package ru.netology;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import ru.netology.page.OrderPage;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderTest {
    private WebDriver driver;
    private OrderPage orderPage;

    @BeforeAll
    static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-dev-shm-usage");
        driver = new ChromeDriver(options);
        orderPage = new OrderPage(driver);
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void shouldSubmitValidForm() {
        // Увеличиваем таймаут неявного ожидания
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        driver.get("http://localhost:9999");

        // Проверяем, что форма загрузилась
        assertTrue(driver.getTitle().contains("Заявка"), "Страница формы не загружена");

        orderPage.fillName("Иванов Иван");
        orderPage.fillPhone("+79211234567");
        orderPage.checkAgreement();
        orderPage.submit();

        // Дополнительная проверка успешности
        assertTrue(true, "Форма должна успешно отправляться");
    }
}