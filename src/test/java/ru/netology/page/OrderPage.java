package ru.netology.page;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;

public class OrderPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Селекторы
    private final By nameField = By.cssSelector("[data-test-id=name] input");
    private final By phoneField = By.cssSelector("[data-test-id=phone] input");
    private final By agreementCheckbox = By.cssSelector("[data-test-id=agreement]");
    private final By submitButton = By.cssSelector("[data-test-id=submit]");
    private final By successNotification = By.cssSelector("[data-test-id=success-notification]");

    public OrderPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void fillName(String name) {
        System.out.println("Заполняем поле 'Имя': " + name);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(nameField));
        element.clear();
        element.sendKeys(name);
    }

    public void fillPhone(String phone) {
        System.out.println("Заполняем поле 'Телефон': " + phone);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(phoneField));
        element.clear();
        element.sendKeys(phone);
    }

    public void checkAgreement() {
        System.out.println("Отмечаем чекбокс согласия");
        WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(agreementCheckbox));
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
    }

    public void submit() {
        System.out.println("Нажимаем кнопку отправки");
        wait.until(ExpectedConditions.elementToBeClickable(submitButton)).click();
    }

    public boolean isSuccessNotificationVisible() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(successNotification)).isDisplayed();
        } catch (TimeoutException e) {
            System.out.println("Уведомление об успехе не появилось");
            return false;
        }
    }
}