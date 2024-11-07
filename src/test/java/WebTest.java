import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Selenide.*;

class WebTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @BeforeAll
    static void setUpAll(){
        SelenideLogger.addListener("allure",new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll(){
        SelenideLogger.removeListener("allure");
    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {
        //подготовка данных
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        //первочнальный поиск элементов и вставка значений
        SelenideElement form = $("form");

        SelenideElement cityInput = form.find("[data-test-id=city] input");
        cityInput.setValue(validUser.getCity());

        SelenideElement dateInput = form.find("[data-test-id=date] input");
        dateInput.sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        dateInput.setValue(firstMeetingDate);

        SelenideElement nameAndSurnameInput = form
                .find("[data-test-id=name] input");
        nameAndSurnameInput.setValue(validUser.getName() + " " + validUser.getSurname());

        SelenideElement phoneInput = form
                .find("[data-test-id=phone] input");
        phoneInput.setValue(validUser.getPhone());

        SelenideElement agreementInput = form
                .find("[data-test-id=agreement] span");
        agreementInput.click();

        //Проверка 1 - проверяем первичное бронирование
        SelenideElement billButton = form.find(".button_theme_alfa-on-white");
        billButton.click();

        $("[data-test-id=success-notification] div.notification__content")
                .should(Condition.visible)
                .shouldHave(Condition.exactText("Встреча успешно запланирована на " + firstMeetingDate));
        //Проверка 2 - меняем дату и проверяем на наличие уведомления о перепланировке
        dateInput.sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        dateInput.setValue(secondMeetingDate);
        billButton.click();
        SelenideElement replanButton = $("[data-test-id=replan-notification] button").should(Condition.visible);
        //Проверка 3 - нажимаем кнопку перепланирования и проверяем на наличие уведомления
        //об успешном бронировании на новую дату
        replanButton.click();

        $("[data-test-id=success-notification] div.notification__content")
                .should(Condition.visible)
                .shouldHave(Condition.exactText("Встреча успешно запланирована на " + secondMeetingDate));

    }
}