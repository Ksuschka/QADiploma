package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class PaymentPage {
    private SelenideElement heading = $$("h3").find(text("Оплата по карте"));
    private SelenideElement fieldCardNumber = $("[placeholder='0000 0000 0000 0000']");
    private SelenideElement fieldMonth = $("[placeholder='08']");
    private SelenideElement fieldYear = $("[placeholder='22']");
    private SelenideElement fieldHolder = $$("[class='input__control']").get(3);
    private SelenideElement fieldCvc = $("[placeholder='999']");
    private SelenideElement buttonContinue = $$("button").find(exactText("Продолжить"));

    private SelenideElement operationApproved = $(".icon_name_ok");
    private SelenideElement operationRefusal = $(".icon_name_error");
    private SelenideElement wrongFormat = $$(".input__sub").find(exactText("Неверный формат"));
    private SelenideElement incorrectCardExpirationDate = $$(".input__sub").find(exactText("Неверно указан срок действия карты"));
    private SelenideElement cardExpired = $$(".input__sub").find(exactText("Истёк срок действия карты"));
    private SelenideElement incorrectData = $$(".input__sub").find(exactText("Указаны некоректные данные"));
    private SelenideElement fieldRequired = $$(".input__sub").find(exactText("Поле обязательно для заполнения"));



    public void enterData(String cardNumber, String month, String year, String holder, String cvc) {
        fieldCardNumber.setValue(cardNumber);
        fieldMonth.setValue(month);
        fieldYear.setValue(year);
        fieldHolder.setValue(holder);
        fieldCvc.setValue(cvc);
        buttonContinue.click();
    }

    public void operationApprovedVisible() {
        operationApproved.shouldBe(visible, Duration.ofSeconds(20));
    }

    public void operationRefusalVisible() {
        operationRefusal.shouldBe(visible, Duration.ofSeconds(15));
    }

    public void messageWrongFormat() {
        wrongFormat.shouldBe(visible);
    }

    public void messageIncorrectCardExpirationDate() {
        incorrectCardExpirationDate.shouldBe(visible);
    }

    public void messageCardExpired() {
        cardExpired.shouldBe(visible);
    }

    public void messageIncorrectData() {
        incorrectData.shouldBe(visible);
    }

    public void messageFieldRequired() {
        fieldRequired.shouldBe(visible);
    }
}
