package ru.netology;

import com.codeborne.selenide.Configuration;
import com.github.javafaker.Faker;
import com.github.javafaker.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

public class MeetingDateChange {


    private Faker faker;

    @BeforeEach
    void setUpAll() {
        faker = new Faker(new Locale("ru"));
    }

    @Test
    public void shouldSendForm() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        FormInfo info = DataGenerator.Form.generateInfo("ru");
        $("[data-test-id='city'] input").val(info.getCity());
        $("div[class='popup__content'] span").click();
        String planningDate = generateDate(4);
        $("[data-test-id='date'] input[class='input__control']").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input[class='input__control']").setValue(planningDate).pressTab();
        $("[data-test-id='name'] input").val(info.getName());
        $(byName("phone")).setValue(info.getPhone());
        $(byClassName("checkbox__box")).click();
        $(withText("Запланировать")).click();
        $("[data-test-id='success-notification']").shouldHave(text("Успешно!"));//, Duration.ofSeconds(15));
        $(".notification__content")//.shouldBe(visible)//, Duration.ofSeconds(15))
                .shouldHave(exactText("Встреча успешно запланирована на " + planningDate));
        String planningDate2 = generateDate(5);
        $("[data-test-id='date'] input[class='input__control']").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input[class='input__control']").setValue(planningDate2).pressTab();
        $(withText("Запланировать")).click();
        $("[data-test-id='replan-notification'] div[class='notification__title']").shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(text("Необходимо подтверждение"));
        $(withText("Перепланировать")).shouldBe(visible).click();
        $(".notification__content").shouldBe(visible)
                .shouldHave(exactText("Встреча успешно запланирована на " + planningDate2));

    }

    public String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }


}



