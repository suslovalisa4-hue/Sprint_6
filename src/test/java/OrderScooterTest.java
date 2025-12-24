import constants.OrderPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderScooterTest {
    private WebDriver driver;
    private OrderPage orderPage;

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox", "--disable-dev-shm-usage");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://qa-scooter.praktikum-services.ru/");

        orderPage = new OrderPage(driver);
        orderPage.closeCookieBanner();
    }

    @ParameterizedTest(name = "Заказ через верхнюю кнопку: {0} {1}")
    @CsvSource({
            "Иван,Петров, Москва, 3, +79159437196, черный, 16.12.2025, 7, Позвоните за 30 минут",
            "Роман, Максимов, Москва, 15, +79107961232, серый, 18.10.2025, 5, ''"
    })
    void testOrderScooterThroughHeaderButtonCsv(
            String name, String surname, String address, int metro,
            String phoneNumber, String color, String date, int rentalDays, String comment) {

        orderPage.clickOrderButtonHeader();
        orderPage.fillingInTheFirstPageOfTheOrder(name, surname, address, metro, phoneNumber);
        orderPage.fillingInTheSecondPageOfTheOrder(date, rentalDays, color, comment);
        assertTrue(orderPage.successfullyText());
    }

    @ParameterizedTest(name = "Заказ через нижнюю кнопку: {0} {1}")
    @CsvSource({
            "Петя, Иванов, Москва, 5, +79155834567, черный, 16.12.2025, 2, Не звонить",
            "Сергей, Сидоров, Москва, 8, +79031112233, серый, 18.12.2025, 4, ''"
    })
    void testOrderScooterThroughDownButtonCsv(
            String name, String surname, String address, int metro,
            String phoneNumber, String color, String date, int rentalDays, String comment) {

        orderPage.clickOrderButtonDown();
        orderPage.fillingInTheFirstPageOfTheOrder(name, surname, address, metro, phoneNumber);
        orderPage.fillingInTheSecondPageOfTheOrder(date, rentalDays, color, comment);
        assertTrue(orderPage.successfullyText());
    }

    @AfterEach
    void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}



