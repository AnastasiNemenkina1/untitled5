package ru.netology;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import ru.netology.page.OrderPage;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class OrderTest {
    private WebDriver driver;
    private OrderPage orderPage;

    static {
        try {
            Runtime.getRuntime().exec("taskkill /F /IM java.exe");
            Thread.sleep(2000); // Ожидание завершения процессов
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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

        // Для отладки можно временно отключить headless
        // options.addArguments("--headless=new");

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        orderPage = new OrderPage(driver);
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @DisplayName("Успешная отправка формы с валидными данными")
    void shouldSubmitValidForm() {
        try {
            // Логирование перед началом теста
            System.out.println("\n=== Начало теста ===");
            System.out.println("1. Открываем страницу...");

            driver.get("http://localhost:7777");
            System.out.println("Текущий URL: " + driver.getCurrentUrl());
            System.out.println("Заголовок страницы: " + driver.getTitle());

            // Логирование HTML (первые 500 символов)
            String pageSource = driver.getPageSource();
            System.out.println("HTML страницы (фрагмент):\n" +
                    pageSource.substring(0, Math.min(pageSource.length(), 500)));

            // Заполнение формы
            System.out.println("\n2. Заполняем форму...");
            orderPage.fillName("Иванов Иван");
            orderPage.fillPhone("+79211234567");
            orderPage.checkAgreement();

            System.out.println("\n3. Отправляем форму...");
            orderPage.submit();

            // Проверка результата
            System.out.println("\n4. Проверяем результат...");
            assertTrue(orderPage.isSuccessNotificationVisible(),
                    "Должно отображаться уведомление об успешной отправке");

            System.out.println("=== Тест успешно завершен ===");
        } catch (Exception e) {
            System.out.println("\n!!! ОШИБКА В ТЕСТЕ !!!");
            System.out.println("Тип ошибки: " + e.getClass().getName());
            System.out.println("Сообщение: " + e.getMessage());

            // Дополнительная информация для отладки
            if (driver != null) {
                System.out.println("Текущий URL при ошибке: " + driver.getCurrentUrl());
                System.out.println("Снимок DOM при ошибке:\n" +
                        driver.findElement(By.tagName("body")).getAttribute("outerHTML"));
            }
            throw e;
        }
    }
}