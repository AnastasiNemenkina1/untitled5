package ru.netology;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.netology.page.OrderPage;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

class OrderTest {
    private static WebDriver driver;
    private static OrderPage orderPage;
    private static WebDriverWait wait;

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
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        orderPage = new OrderPage(driver, wait);
    }

    @Test
    void shouldSubmitValidForm() {
        driver.get("http://localhost:9999");

        orderPage.fillName("Иванов Иван");
        orderPage.fillPhone("+79270000000");
        orderPage.fillCity("Москва");
        orderPage.checkAgreement();
        orderPage.submit();

        String successText = orderPage.getSuccessMessage();
        assertTrue(successText.contains("Успешно"));
    }

    @Test
    void shouldShowErrorForInvalidName() {
        driver.get("http://localhost:9999");
        orderPage.fillName("Ivanov Ivan"); // Английские буквы
        orderPage.fillPhone("+79270000000");
        orderPage.fillCity("Москва");
        orderPage.checkAgreement();
        orderPage.submit();

        String errorText = orderPage.getNameError();
        assertTrue(errorText.contains("Допустимы только русские буквы"));
    }

    @Test
    void shouldShowErrorForInvalidPhone() {
        driver.get("http://localhost:9999");
        orderPage.fillName("Иванов Иван");
        orderPage.fillPhone("79270000000"); // Нет +
        orderPage.fillCity("Москва");
        orderPage.checkAgreement();
        orderPage.submit();

        String errorText = orderPage.getPhoneError();
        assertTrue(errorText.contains("Телефон указан неверно"));
    }

    @Test
    void shouldShowErrorForUncheckedAgreement() {
        driver.get("http://localhost:9999");
        orderPage.fillName("Иванов Иван");
        orderPage.fillPhone("+79270000000");
        orderPage.fillCity("Москва");
        // Чекбокс не отмечаем специально
        orderPage.submit();

        String errorText = orderPage.getAgreementError();
        assertTrue(errorText.contains("Необходимо согласие"));
    }

    @AfterAll
    static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}