package ru.netology.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class OrderPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Селекторы
    private final By nameField = By.cssSelector("[data-test-id=name] input");
    private final By phoneField = By.cssSelector("[data-test-id=phone] input");
    private final By agreementCheckbox = By.cssSelector("[data-test-id=agreement]");
    private final By submitButton = By.cssSelector("[data-test-id=submit]");

    public OrderPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void fillName(String name) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(nameField)).sendKeys(name);
    }

    public void fillPhone(String phone) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(phoneField)).sendKeys(phone);
    }

    public void checkAgreement() {
        wait.until(ExpectedConditions.elementToBeClickable(agreementCheckbox)).click();
    }

    public void submit() {
        wait.until(ExpectedConditions.elementToBeClickable(submitButton)).click();
    }
}