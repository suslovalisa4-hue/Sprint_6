package constants;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class OrderPage {
    private WebDriver driver;

    // Локаторы для баннера куки
    private static final By COOKIE_BANNER_BUTTON = By.className("App_CookieButton__3cvqF");

    private static final By OrderButtonHeader = By.xpath("//*[@id='root']/div/div[1]/div[1]/div[2]/button[1]");
    private static final By OrderButtonDown = By.xpath("//*[@id='root']/div/div/div[4]/div[2]/div[5]/button");

    // Локаторы для инпутов формы создания заказа
    private static final By NAME_INPUT = By.xpath("//input[@placeholder='* Имя']");
    private static final By SURNAME_INPUT = By.xpath("//input[@placeholder='* Фамилия']");
    private static final By ADDRESS_INPUT = By.xpath("//input[@placeholder='* Адрес: куда привезти заказ']");
    private static final By METRO_INPUT = By.xpath("//input[@placeholder='* Станция метро']");
    private static final By TELEPHONE_INPUT = By.xpath("//input[@placeholder='* Телефон: на него позвонит курьер']");

    // Кнопка далее
    private static final By NEXT_BUTTON = By.xpath("//button[text()='Далее']");

    // Локаторы для второй страницы создания заказа
    private static final By DATE_INPUT = By.xpath("//input[@placeholder='* Когда привезти самокат']");
    private static final By RENTAL_PERIOD = By.className("Dropdown-placeholder");
    private static final By BLACK_COLOR_CHECKBOX = By.id("black");
    private static final By GREY_COLOR_CHECKBOX = By.id("grey");
    private static final By COMMENT_INPUT = By.xpath("//input[@placeholder='Комментарий для курьера']");

    // Кнопки завершения заказа
    private static final By FINAL_ORDER_BUTTON = By.xpath("//button[text()='Заказать' and contains(@class, 'Button_Middle')]");
    private static final By YES_BUTTON = By.xpath("//button[text()='Да']");
    private static final By SUCCESS_MESSAGE = By.xpath("//div[contains(@class, 'Order_ModalHeader')]");

    public OrderPage(WebDriver driver) {
        this.driver = driver;
    }

    public void closeCookieBanner() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.elementToBeClickable(COOKIE_BANNER_BUTTON)).click();
        } catch (Exception e) {
            // Баннера нет или уже закрыт - продолжаем
        }
    }

    // Метод нажатия на кнопку наверху страницы
    public void clickOrderButtonHeader() {
        driver.findElement(OrderButtonHeader).click();
    }

    // Метод нажатия на кнопку внизу страницы
    public void clickOrderButtonDown() {
        driver.findElement(OrderButtonDown).click();
    }

    // Заполнение первой страницы заказа
    public void fillingInTheFirstPageOfTheOrder(String name, String surname, String address, int metro, String telephone) {
        driver.findElement(NAME_INPUT).sendKeys(name);
        driver.findElement(SURNAME_INPUT).sendKeys(surname);
        driver.findElement(ADDRESS_INPUT).sendKeys(address);

        // Выбор станции метро
        driver.findElement(METRO_INPUT).click();

        // Получение всех станций метро и выбор по индексу
        By allMetroStation = By.xpath("//*[@id='root']/div/div[2]/div[2]/div[4]/div/div[2]/ul/li");
        List<WebElement> stations = driver.findElements(allMetroStation);
        stations.get(metro - 1).click();

        driver.findElement(TELEPHONE_INPUT).sendKeys(telephone);
        driver.findElement(NEXT_BUTTON).click();
    }

    // Заполнение второй страницы заказа
    public void fillingInTheSecondPageOfTheOrder(String date, int rentalDays, String color, String comment) {
        // Заполнение даты
        driver.findElement(DATE_INPUT).sendKeys(date);
        driver.findElement(DATE_INPUT).sendKeys(Keys.ENTER);

        // Выбор срока аренды
        driver.findElement(RENTAL_PERIOD).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("Dropdown-option")));

        List<WebElement> rentalOptions = driver.findElements(By.className("Dropdown-option"));
        if (rentalDays >= 1 && rentalDays <= rentalOptions.size()) {
            rentalOptions.get(rentalDays - 1).click();
        }

        // Выбор цвета
        if ("черный".equalsIgnoreCase(color) || "black".equalsIgnoreCase(color)) {
            driver.findElement(BLACK_COLOR_CHECKBOX).click();
        } else if ("серый".equalsIgnoreCase(color) || "grey".equalsIgnoreCase(color)) {
            driver.findElement(GREY_COLOR_CHECKBOX).click();
        }

        // Заполнение комментария
        driver.findElement(COMMENT_INPUT).sendKeys(comment);

        // Завершение заказа
        driver.findElement(FINAL_ORDER_BUTTON).click();
        wait.until(ExpectedConditions.elementToBeClickable(YES_BUTTON)).click();
    }

    public boolean successfullyText() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(SUCCESS_MESSAGE));
            String successText = driver.findElement(SUCCESS_MESSAGE).getText();
            return successText.contains("Заказ оформлен");
        } catch (Exception e) {
            return false;
        }
    }
}
