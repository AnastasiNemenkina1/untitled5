package ru.netology.page;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class OrderPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Локаторы элементов
    private final By nameLocator = By.cssSelector("[data-test-id=name] input");
    private final By phoneLocator = By.cssSelector("[data-test-id=phone] input");
    private final By cityLocator = By.cssSelector("[data-test-id=city] input");
    private final By agreementLocator = By.cssSelector("[data-test-id=agreement] .checkbox__box");
    private final By submitButton = By.cssSelector("[data-test-id=submit]");
    private final By successMessage = By.cssSelector("[data-test-id=success-notification] .notification__content");
    private final By nameError = By.cssSelector("[data-test-id=name].input_invalid .input__sub");
    private final By phoneError = By.cssSelector("[data-test-id=phone].input_invalid .input__sub");
    private final By agreementError = By.cssSelector("[data-test-id=agreement].input_invalid");

    public OrderPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void fillName(String name) {
        driver.findElement(nameLocator).clear();
        driver.findElement(nameLocator).sendKeys(name);
    }

    public void fillPhone(String phone) {
        driver.findElement(phoneLocator).clear();
        driver.findElement(phoneLocator).sendKeys(phone);
    }

    public void fillCity(String city) {
        driver.findElement(cityLocator).clear();
        driver.findElement(cityLocator).sendKeys(city);
    }

    public void checkAgreement() {
        if (!driver.findElement(agreementLocator).isSelected()) {
            driver.findElement(agreementLocator).click();
        }
    }

    public void submit() {
        driver.findElement(submitButton).click();
    }

    public String getSuccessMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage)).getText();
    }

    public String getNameError() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(nameError)).getText();
    }

    public String getPhoneError() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(phoneError)).getText();
    }

    public String getAgreementError() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(agreementError)).getText();
    }
}