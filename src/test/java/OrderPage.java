package ru.netology.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class OrderPage {
    private final WebDriver driver;

    public OrderPage(WebDriver driver) {
        this.driver = driver;
    }

    public void fillName(String name) {
        driver.findElement(By.cssSelector("[data-test-id=name] input"))
                .sendKeys(name);
    }

    public void fillPhone(String phone) {
        driver.findElement(By.cssSelector("[data-test-id=phone] input"))
                .sendKeys(phone);
    }

    public void checkAgreement() {
        driver.findElement(By.cssSelector("[data-test-id=agreement]"))
                .click();
    }

    public void submit() {
        driver.findElement(By.cssSelector("[data-test-id=submit]"))
                .click();
    }

    public String getSuccessMessage() {
        return driver.findElement(By.cssSelector("[data-test-id=success-message]"))
                .getText();
    }
}