import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

public class TestSeleniumLitecart {

    private static final String SITE = "https://demo.litecart.net/";
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void setUp() {}

    @AfterEach
    public void tearDown() {
        if (driver != null) driver.quit();
    }

    @Test
    public void testFirefox() {
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        runTest();
    }

    @Test
    public void testEdge() {
        System.setProperty("webdriver.edge.driver", "C:/webdrivers/msedgedriver.exe");
        driver = new EdgeDriver();
        runTest();
    }

    private void runTest() {
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        driver.get(SITE);

        // Ждём загрузки
        wait.until(d -> "complete".equals(
                ((JavascriptExecutor) d).executeScript("return document.readyState")
        ));

        // Находим элемент
        WebElement purpleDuckLink = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.partialLinkText("Purple Duck"))
        );

        // клик через JAVASCRIPT
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", purpleDuckLink);

        // Проверка
        wait.until(ExpectedConditions.titleIs("Purple Duck | Rubber Ducks | My Store"));
    }
}