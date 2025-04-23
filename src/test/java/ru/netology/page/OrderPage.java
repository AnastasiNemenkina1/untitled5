package ru.netology.page;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class OrderPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By nameLocator = By.cssSelector("[data-test-id=name] input");
    private final By phoneLocator = By.cssSelector("[data-test-id=phone] input");
    private final By cityLocator = By.cssSelector("[data-test-id=city] input");
    private final By agreementLocator = By.cssSelector("[data-test-id=agreement] .checkbox__box");
    private final By submitButton = By.cssSelector("[data-test-id=submit]");
    private final By successMessage = By.cssSelector("[data-test-id=success-notification] .notification__content");
    private final By nameError = By.cssSelector("[data-test-id=name].input_invalid .input__sub");
    private final By phoneError = By.cssSelector("[data-test-id=phone].input_invalid .input__sub");
    private final By agreementError = By.cssSelector("[data-test-id=agreement].input_invalid");

    public OrderPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void fillName(String name) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(nameLocator));
        field.clear();
        field.sendKeys(name);
    }

    public void fillPhone(String phone) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(phoneLocator));
        field.clear();
        field.sendKeys(phone);
    }

    public void fillCity(String city) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(cityLocator));
        field.clear();
        field.sendKeys(city);
    }

    public void checkAgreement() {
        WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(agreementLocator));
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
    }

    public void submit() {
        wait.until(ExpectedConditions.elementToBeClickable(submitButton)).click();
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
        return wait.until(ExpectedConditions.visibilityOfElementLocated(agreementError))
                .getAttribute("validationMessage");
    }
}