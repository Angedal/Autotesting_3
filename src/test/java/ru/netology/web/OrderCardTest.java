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
    void shouldTestAlfaBankCard_AllFieldsCorrect() {
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
    void shouldTestAlfaBankCard_NameWithHyphen() {
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
    void shouldTestAlfaBankCard_WrongName() {

        WebElement name = driver.findElement(By.cssSelector("[data-test-id='name'] input"));
        name.sendKeys("fdfdfdf");

        WebElement phone = driver.findElement(By.cssSelector("[data-test-id='phone'] input"));
        phone.sendKeys("+79008855152");

        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button__content")).click();

        String textOfSub = driver.findElement(By.cssSelector("[data-test-id='name'] .input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", textOfSub);
    }

    @Test
    void shouldTestAlfaBankCard_WrongPhone() {

        WebElement name = driver.findElement(By.cssSelector("[data-test-id='name'] input"));
        name.sendKeys("Иванов-Петров Александр");

        WebElement phone = driver.findElement(By.cssSelector("[data-test-id='phone'] input"));
        phone.sendKeys("+79008855");

        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button__content")).click();

        String textOfSub = driver.findElement(By.cssSelector("[data-test-id='phone'] .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", textOfSub);
    }

    @Test
    void shouldTestAlfaBankCard_NoAgreement() {

        WebElement name = driver.findElement(By.cssSelector("[data-test-id='name'] input"));
        name.sendKeys("Иванов Иван");

        WebElement phone = driver.findElement(By.cssSelector("[data-test-id='phone'] input"));
        phone.sendKeys("+79278240000");

        driver.findElement(By.className("button__content")).click();

        WebElement invalidCheckboxClass = driver.findElement(By.cssSelector("[data-test-id='agreement']"));

        assertEquals(invalidCheckboxClass.getAttribute("class"), "checkbox checkbox_size_m checkbox_theme_alfa-on-white input_invalid" );
    }

    @Test
    void shouldTestAlfaBankCard_NoName() {

        WebElement name = driver.findElement(By.cssSelector("[data-test-id='name'] input"));
        name.sendKeys("");

        WebElement phone = driver.findElement(By.cssSelector("[data-test-id='phone'] input"));
        phone.sendKeys("+79278240000");

        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button__content")).click();

        String textOfSub = driver.findElement(By.cssSelector("[data-test-id='name'] .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", textOfSub);
    }

    @Test
    void shouldTestAlfaBankCard_NoPhone() {

        WebElement name = driver.findElement(By.cssSelector("[data-test-id='name'] input"));
        name.sendKeys("Иванов Иван");

        WebElement phone = driver.findElement(By.cssSelector("[data-test-id='phone'] input"));
        phone.sendKeys("");

        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button__content")).click();

        String textOfSub = driver.findElement(By.cssSelector("[data-test-id='phone'] .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", textOfSub);
    }


}
