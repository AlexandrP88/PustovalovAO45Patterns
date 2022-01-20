package ru.netology;

import com.codeborne.selenide.Configuration;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;


import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

public class MeetingDateChange {


    @Test
    public void shouldSendForm() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        FormInfo info = DataGenerator.Form.generateInfo("ru");
        $("[data-test-id='city'] input").val(info.getCity());
        String planningDate = DataGenerator.generateDate(4);
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate).pressTab();
        $("[data-test-id='name'] input").val(info.getName());
        $(byName("phone")).setValue(info.getPhone());
        $(byClassName("checkbox__box")).click();
        $(withText("Запланировать")).click();
        $("[data-test-id='success-notification']").shouldHave(text("Успешно!"));//, Duration.ofSeconds(15));
        $(".notification__content")//.shouldBe(visible)//, Duration.ofSeconds(15))
                .shouldHave(exactText("Встреча успешно запланирована на " + planningDate));
        String planningDate2 = DataGenerator.generateDate(5);
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(planningDate2).pressTab();
        $(withText("Запланировать")).click();
        $("[data-test-id='replan-notification'] div[class='notification__title']").shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(text("Необходимо подтверждение"));
        $(withText("Перепланировать")).shouldBe(visible).click();
        $(".notification__content").shouldBe(visible)
                .shouldHave(exactText("Встреча успешно запланирована на " + planningDate2));

    }
}



