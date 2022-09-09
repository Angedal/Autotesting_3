package ru.netology.web;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderCardTest {
    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        System.setProperty("webdriver.chrome.driver", "driver/win/chromedriver.exe");
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver();
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldTestAlfaBankCard_1() {
        driver.get("http://localhost:7777");

        WebElement name = driver.findElement(By.cssSelector("[data-test-id='name'] input"));
        name.sendKeys("Иванов Иван");

        WebElement phone = driver.findElement(By.cssSelector("[data-test-id='phone'] input"));
        phone.sendKeys("+79280000000");

        driver.findElement(By.className("checkbox__box")).click();

        driver.findElement(By.className("button__content")).click();

        String textOfSuccess = driver.findElement(By.className("paragraph")).getText();

        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", textOfSuccess.trim());
    }

    @Test
    void shouldTestAlfaBankCard_2() {
        driver.get("http://localhost:7777");

        WebElement name = driver.findElement(By.cssSelector("[data-test-id='name'] input"));
        name.sendKeys("Иванов-Петров");

        WebElement phone = driver.findElement(By.cssSelector("[data-test-id='phone'] input"));
        phone.sendKeys("+79008884152");

        driver.findElement(By.className("checkbox__box")).click();

        driver.findElement(By.className("button__content")).click();

        String textOfSuccess = driver.findElement(By.className("paragraph")).getText();

        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", textOfSuccess.trim());
    }

    @Test
    void shouldTestAlfaBankCard_3() {
        driver.get("http://localhost:7777");

        WebElement name = driver.findElement(By.cssSelector("[data-test-id='name'] input"));
        name.sendKeys("Иванов-Петров Александр");

        WebElement phone = driver.findElement(By.cssSelector("[data-test-id='phone'] input"));
        phone.sendKeys("+79008855152");

        driver.findElement(By.className("checkbox__box")).click();

        driver.findElement(By.className("button__content")).click();

        String textOfSuccess = driver.findElement(By.className("paragraph")).getText();

        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", textOfSuccess.trim());
    }
}