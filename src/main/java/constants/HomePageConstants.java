package constants;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;


public class HomePageConstants {
    //Локаторы стрелочек открыть/закрыть
    public static final By Question_1 = By.id("accordion__heading-0");
    public static final By Question_2 = By.id("accordion__heading-1");
    public static final By Question_3 = By.id("accordion__heading-2");
    public static final By Question_4 = By.id("accordion__heading-3");
    public static final By Question_5 = By.id("accordion__heading-4");
    public static final By Question_6 = By.id("accordion__heading-5");
    public static final By Question_7 = By.id("accordion__heading-6");
    public static final By Question_8 = By.id("accordion__heading-7");

    //Локаторы ответов на вопросы
    private final static By Answer_1 = By.xpath("/html/body/div/div/div/div[5]/div[2]/div/div[1]/div[2]/p");
    private final static By Answer_2 = By.xpath("/html/body/div/div/div/div[5]/div[2]/div/div[2]/div[2]/p");
    private final static By Answer_3 = By.xpath("/html/body/div/div/div/div[5]/div[2]/div/div[3]/div[2]/p");
    private final static By Answer_4 = By.xpath("/html/body/div/div/div/div[5]/div[2]/div/div[4]/div[2]/p");
    private final static By Answer_5 = By.xpath("/html/body/div/div/div/div[5]/div[2]/div/div[5]/div[2]/p");
    private final static By Answer_6 = By.xpath("/html/body/div/div/div/div[5]/div[2]/div/div[6]/div[2]/p");
    private final static By Answer_7 = By.xpath("/html/body/div/div/div/div[5]/div[2]/div/div[7]/div[2]/p");
    private final static By Answer_8 = By.xpath("/html/body/div/div/div/div[5]/div[2]/div/div[8]/div[2]/p");

    private final WebDriver driver;
    private final Map<By, By> QUESTION_ANSWER_MAP = new HashMap<>();
    private final Map<String, By> questionTextToLocator = new HashMap<>();

    public HomePageConstants(WebDriver driver) {
        this.driver = driver;
        initializeMaps();
    }

    private void initializeMaps() {
        // Инициализируем QUESTION_ANSWER_MAP
        QUESTION_ANSWER_MAP.put(Question_1, Answer_1);
        QUESTION_ANSWER_MAP.put(Question_2, Answer_2);
        QUESTION_ANSWER_MAP.put(Question_3, Answer_3);
        QUESTION_ANSWER_MAP.put(Question_4, Answer_4);
        QUESTION_ANSWER_MAP.put(Question_5, Answer_5);
        QUESTION_ANSWER_MAP.put(Question_6, Answer_6);
        QUESTION_ANSWER_MAP.put(Question_7, Answer_7);
        QUESTION_ANSWER_MAP.put(Question_8, Answer_8);

        // Инициализируем questionTextToLocator
        questionTextToLocator.put("Сколько это стоит? И как оплатить?", Question_1);
        questionTextToLocator.put("Хочу сразу несколько самокатов! Так можно?", Question_2);
        questionTextToLocator.put("Как рассчитывается время аренды?", Question_3);
        questionTextToLocator.put("Можно ли заказать самокат прямо на сегодня?", Question_4);
        questionTextToLocator.put("Можно ли продлить заказ или вернуть самокат раньше?", Question_5);
        questionTextToLocator.put("Вы привозите зарядку вместе с самокатом?", Question_6);
        questionTextToLocator.put("Можно ли отменить заказ?", Question_7);
        questionTextToLocator.put("Я жизу за МКАДом, привезёте?", Question_8);
    }

    public void scrollHome() {
        scrollToElement(Question_1);
    }

    public String getAnswerForQuestion(String questionText) {
        By questionLocator = questionTextToLocator.get(questionText);
        if (questionLocator == null) {
            throw new IllegalArgumentException("Вопрос не найден: " + questionText);
        }

        // Скроллим к вопросу
        scrollToElement(questionLocator);

        // Получаем ответ
        return getAnswer(questionLocator);
    }

    public String getAnswer(By question) {
        // Кликаем на вопрос
        driver.findElement(question).click();

        // Ждем появления ответа
        By answerLocator = QUESTION_ANSWER_MAP.get(question);
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(answerLocator));

        // Получаем текст ответа
        return driver.findElement(answerLocator).getText();
    }

    private void scrollToElement(By locator) {
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView(true);",
                        driver.findElement(locator));
    }
}
