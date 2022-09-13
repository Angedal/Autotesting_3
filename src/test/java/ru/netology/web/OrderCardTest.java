package ru.netology.web;

import io.github.bonigarcia.wdm.WebDriverManager;
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
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderCardTest {
    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldTestWithCorrectFields() {
        WebElement name = driver.findElement(By.cssSelector("[data-test-id='name'] input"));
        name.sendKeys("Иванов Иван");

        WebElement phone = driver.findElement(By.cssSelector("[data-test-id='phone'] input"));
        phone.sendKeys("+79280000000");

        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.className("button__content")).click();

        String textOfSuccess = driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", textOfSuccess.trim());
    }

    @Test
    void shouldTestWithNameAndHyphen() {
        WebElement name = driver.findElement(By.cssSelector("[data-test-id='name'] input"));
        name.sendKeys("Иванов-Петров");

        WebElement phone = driver.findElement(By.cssSelector("[data-test-id='phone'] input"));
        phone.sendKeys("+79008884152");

        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.className("button__content")).click();

        String textOfSuccess = driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", textOfSuccess.trim());
    }

    @Test
    void shouldTestWithWrongName() {

        WebElement name = driver.findElement(By.cssSelector("[data-test-id='name'] input"));
        name.sendKeys("fdfdfdf");

        WebElement phone = driver.findElement(By.cssSelector("[data-test-id='phone'] input"));
        phone.sendKeys("+79008855152");

        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.className("button__content")).click();

        String textOfSub = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", textOfSub);
    }

    @Test
    void shouldTestWithWrongPhone() {

        WebElement name = driver.findElement(By.cssSelector("[data-test-id='name'] input"));
        name.sendKeys("Иванов-Петров Александр");

        WebElement phone = driver.findElement(By.cssSelector("[data-test-id='phone'] input"));
        phone.sendKeys("+79008855");

        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.className("button__content")).click();

        String textOfSub = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", textOfSub);
    }

    @Test
    void shouldTestWithoutAgreement() {

        WebElement name = driver.findElement(By.cssSelector("[data-test-id='name'] input"));
        name.sendKeys("Иванов Иван");

        WebElement phone = driver.findElement(By.cssSelector("[data-test-id='phone'] input"));
        phone.sendKeys("+79278240000");

        driver.findElement(By.className("button__content")).click();

        WebElement invalidCheckboxClass = driver.findElement(By.cssSelector("[data-test-id='agreement']"));
        assertTrue(invalidCheckboxClass.getAttribute("class").contains("input_invalid"));
    }

    @Test
    void shouldTestWithoutName() {

        WebElement name = driver.findElement(By.cssSelector("[data-test-id='name'] input"));
        name.sendKeys("");

        WebElement phone = driver.findElement(By.cssSelector("[data-test-id='phone'] input"));
        phone.sendKeys("+79278240000");

        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.className("button__content")).click();

        String textOfSub = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", textOfSub);
    }

    @Test
    void shouldTestWithoutPhone() {

        WebElement name = driver.findElement(By.cssSelector("[data-test-id='name'] input"));
        name.sendKeys("Иванов Иван");

        WebElement phone = driver.findElement(By.cssSelector("[data-test-id='phone'] input"));
        phone.sendKeys("");

        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.className("button__content")).click();

        String textOfSub = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", textOfSub);
    }
}