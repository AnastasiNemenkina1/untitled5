package ru.netology;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.*;
import org.openqa.selenium.support.ui.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class FirefoxOrderCardTest {
    private WebDriver driver;

    @BeforeAll
    static void setupAll() {
        WebDriverManager.firefoxdriver().setup();
        System.setProperty("webdriver.firefox.logfile", "geckodriver.log");
    }

    @BeforeEach
    void setup() {
        try {
            FirefoxOptions options = new FirefoxOptions();
            options.setBinary("C:\\Program Files\\Mozilla Firefox\\firefox.exe");
            options.addArguments("--headless");
            options.addArguments("--width=1920");
            options.addArguments("--height=1080");


            // options.setBinary("C:\\Program Files\\Mozilla Firefox\\firefox.exe");

            System.out.println("Initializing Firefox driver...");
            driver = new FirefoxDriver(options);
            System.out.println("Firefox driver initialized successfully");

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
            driver.get("http://localhost:9999");
        } catch (Exception e) {
            System.err.println("Error initializing WebDriver: " + e.getMessage());
            throw e;
        }
    }

    @AfterEach
    void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void shouldSubmitFormWithValidData() {
        // Тест успешной отправки формы
        WebElement nameInput = waitForElement("[data-test-id=name] input");
        nameInput.sendKeys("Гаврина Анастасия");

        WebElement phoneInput = driver.findElement(By.cssSelector("[data-test-id=phone] input"));
        phoneInput.sendKeys("+79290253222");

        WebElement agreementCheckbox = driver.findElement(By.cssSelector("[data-test-id=agreement]"));
        agreementCheckbox.click();

        WebElement submitButton = driver.findElement(By.cssSelector("button.button"));
        submitButton.click();

        WebElement successMessage = waitForElement("[data-test-id=order-success]");
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.",
                successMessage.getText().trim());
    }

    @Test
    void shouldValidateNameField() {
        // Тест валидации поля имени
        WebElement nameInput = waitForElement("[data-test-id=name] input");
        nameInput.sendKeys("Gavrina Anastasia");

        WebElement phoneInput = driver.findElement(By.cssSelector("[data-test-id=phone] input"));
        phoneInput.sendKeys("+79290253222");

        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button.button")).click();

        WebElement errorMessage = waitForElement("[data-test-id=name].input_invalid .input__sub");
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.",
                errorMessage.getText().trim());
    }

    @Test
    void shouldValidatePhoneField() {
        // Тест валидации поля телефона
        WebElement nameInput = waitForElement("[data-test-id=name] input");
        nameInput.sendKeys("Гаврина Анастасия");

        WebElement phoneInput = driver.findElement(By.cssSelector("[data-test-id=phone] input"));
        phoneInput.sendKeys("+79290253222"); // Неправильный номер (10 цифр)

        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button.button")).click();

        WebElement errorMessage = waitForElement("[data-test-id=phone].input_invalid .input__sub");
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79290253222.",
                errorMessage.getText().trim());
    }

    @Test
    void shouldValidateAgreementCheckbox() {
        // Тест валидации чекбокса согласия
        WebElement nameInput = waitForElement("[data-test-id=name] input");
        nameInput.sendKeys("Гаврина Анастасия");

        WebElement phoneInput = driver.findElement(By.cssSelector("[data-test-id=phone] input"));
        phoneInput.sendKeys("+79290253222");

        // Не ставим галочку согласия
        driver.findElement(By.cssSelector("button.button")).click();

        WebElement checkbox = waitForElement("[data-test-id=agreement].input_invalid");
        assertTrue(checkbox.isDisplayed());
    }

    private WebElement waitForElement(String selector) {
        // Универсальный метод ожидания элемента
        return new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(selector)));
    }
}