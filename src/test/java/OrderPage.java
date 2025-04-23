package ru.netology.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class OrderPage {
    private final WebDriver driver;

    private final By nameInput = By.cssSelector("[data-test-id=name] input");
    private final By phoneInput = By.cssSelector("[data-test-id=phone] input");
    private final By agreementCheckbox = By.cssSelector("[data-test-id=agreement]");
    private final By submitButton = By.cssSelector("[data-test-id=submit]");
    private final By successMessage = By.cssSelector("[data-test-id=success-message]");
    private final By errorMessage = By.cssSelector("[data-test-id=error-message]");

    public OrderPage(WebDriver driver) {
        this.driver = driver;
    }

    public void fillName(String name) {
        driver.findElement(nameInput).sendKeys(name);
    }

    public void fillPhone(String phone) {
        driver.findElement(phoneInput).sendKeys(phone);
    }

    public void checkAgreement() {
        driver.findElement(agreementCheckbox).click();
    }

    public void submit() {
        driver.findElement(submitButton).click();
    }

    public String getSuccessMessage() {
        return driver.findElement(successMessage).getText().trim();
    }

    public String getErrorMessage() {
        return driver.findElement(errorMessage).getText().trim();
    }
}
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public void submit() {
    new WebDriverWait(driver, Duration.ofSeconds(5))
            .until(ExpectedConditions.elementToBeClickable(submitButton))
            .click();
