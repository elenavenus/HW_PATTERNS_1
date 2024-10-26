import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Selenide.*;

class WebTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);

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

        SelenideElement billButton = form.find(".button_theme_alfa-on-white");
        billButton.click();

        dateInput.sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        dateInput.setValue(secondMeetingDate);
        billButton.click();

        SelenideElement replanButton = $("[data-test-id=replan-notification] button");
        replanButton.click();

        $$("[data-test-id=success-notification] div.notification__content").filter(Condition.visible)
                .get(0).shouldHave(Condition.exactText("Встреча успешно запланирована на " + secondMeetingDate));

    }
}