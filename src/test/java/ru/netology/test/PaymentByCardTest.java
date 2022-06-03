package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.*;
import ru.netology.data.DataBaseHelper;
import ru.netology.data.DataHelper;
import ru.netology.page.MainPage;
import ru.netology.page.PaymentPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaymentByCardTest {

    PaymentPage paymentPage;

    @BeforeAll
    static void setUpAll() {

        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {

        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    void shouldOpenWeb() {
        DataBaseHelper.clearTables();
        val mainPage = open("http://localhost:8080", MainPage.class);
        paymentPage = mainPage.payByCard();

    }


    @Test
    @DisplayName("Отправка заявки с валидными данными")
    public void shouldApplicationValidData() {
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val holder = DataHelper.getValidHolder();
        val cvc = DataHelper.getValidCvc();
        paymentPage.enterData(cardNumber, month, year, holder, cvc);
        paymentPage.operationApprovedVisible();
        val expected = DataHelper.getFirstCardStatus();
        val actual = DataBaseHelper.getStatusPaymentByCard();
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Отправка заявки с невалидным номером карты")
    public void shouldApplicationInvalidData() {
        val cardNumber = DataHelper.getSecondCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val holder = DataHelper.getValidHolder();
        val cvc = DataHelper.getValidCvc();
        paymentPage.enterData(cardNumber, month, year, holder, cvc);
        paymentPage.operationRefusalVisible();
        val expected = DataHelper.getSecondCardStatus();
        val actual = DataBaseHelper.getStatusPaymentByCard();
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Отправка заявки с не заполненными полями")
    public void shouldFieldIsSempty () {
        val cardNumber = DataHelper.getCardNumberEmpty();
        val month = DataHelper.getMonthEmpty();
        val year = DataHelper.getYearEmpty();
        val holder = DataHelper.getHolderEmpty();
        val cvc = DataHelper.getCvvEmpty();
        paymentPage.enterData(cardNumber, month, year, holder, cvc);
        paymentPage.messageFieldRequired();
    }

    @Test
    @DisplayName("Отправка заявки с несуществующим номером карты")
    public void shouldInvalidCardNumber() {
        val cardNumber = DataHelper.getInvalidCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val holder = DataHelper.getValidHolder();
        val cvc = DataHelper.getValidCvc();
        paymentPage.enterData(cardNumber, month, year, holder, cvc);
        paymentPage.operationRefusalVisible();
    }

    @Test
    @DisplayName("Отправка заявки с номером карты - 0000 0000 0000 0000")
    public void shouldZeroValueCardNumber() {
        val cardNumber = DataHelper.getZeroValueCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val holder = DataHelper.getValidHolder();
        val cvc = DataHelper.getValidCvc();
        paymentPage.enterData(cardNumber, month, year, holder, cvc);
        paymentPage.operationRefusalVisible();
    }

    @Test
    @DisplayName("Отправка заявки с неполным номером карты")
    public void shouldCardNumberUnderLimit() {
        val cardNumber = DataHelper.getCardNumberUnderLimit();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val holder = DataHelper.getValidHolder();
        val cvc = DataHelper.getValidCvc();
        paymentPage.enterData(cardNumber, month, year, holder, cvc);
        paymentPage.messageWrongFormat();
    }

    @Test
    @DisplayName("Отправка заявки при вводе 17 цифр в поле Номер карты")
    public void shouldCardNumberOverLimit() {
        val cardNumber = DataHelper.getCardNumberOverLimit();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val holder = DataHelper.getValidHolder();
        val cvc = DataHelper.getValidCvc();
        paymentPage.enterData(cardNumber, month, year, holder, cvc);
        paymentPage.operationApprovedVisible();
    }

    @Test
    @DisplayName("Отправка заявки при вводе текста в поле Номер карты")
    public void shouldTextValueCardNumber() {
        val cardNumber = DataHelper.getTextValueCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val holder = DataHelper.getValidHolder();
        val cvc = DataHelper.getValidCvc();
        paymentPage.enterData(cardNumber, month, year, holder, cvc);
        paymentPage.messageWrongFormat();
    }

    @Test
    @DisplayName("Отправка заявки с пустым полем Номер карты")
    public void shouldCardNumberEmpty() {
        val cardNumber = DataHelper.getCardNumberEmpty();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val holder = DataHelper.getValidHolder();
        val cvc = DataHelper.getValidCvc();
        paymentPage.enterData(cardNumber, month, year, holder, cvc);
        paymentPage.messageWrongFormat();
    }

    @Test
    @DisplayName("Отправка заявки с несуществующим месяцем")
    public void shouldInvalidMonth() {
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getInvalidMonth();
        val year = DataHelper.getValidYear();
        val holder = DataHelper.getValidHolder();
        val cvc = DataHelper.getValidCvc();
        paymentPage.enterData(cardNumber, month, year, holder, cvc);
        paymentPage.messageIncorrectCardExpirationDate();
    }

    @Test
    @DisplayName("Отправка заявки при вводе нулевого месяца")
    public void shouldZeroValueMonth() {
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getZeroValueMonth();
        val year = DataHelper.getValidYear();
        val holder = DataHelper.getValidHolder();
        val cvc = DataHelper.getValidCvc();
        paymentPage.enterData(cardNumber, month, year, holder, cvc);
        paymentPage.messageIncorrectCardExpirationDate();
    }

    @Test
    @DisplayName("Отправка заявки при вводе прошедшего месяца")
    public void shouldPastMonth() {
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getPastMonth();
        val year = DataHelper.getValidYear();
        val holder = DataHelper.getValidHolder();
        val cvc = DataHelper.getValidCvc();
        paymentPage.enterData(cardNumber, month, year, holder, cvc);
        paymentPage.messageIncorrectCardExpirationDate();
    }


    @Test
    @DisplayName("Отправка заявки при вводе одной цифры в поле Месяц")
    public void shouldIncompleteMonth() {
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getIncompleteMonth();
        val year = DataHelper.getValidYear();
        val holder = DataHelper.getValidHolder();
        val cvc = DataHelper.getValidCvc();
        paymentPage.enterData(cardNumber, month, year, holder, cvc);
        paymentPage.messageWrongFormat();
    }

    @Test
    @DisplayName("Отправка заявки при вводе текста в поле Месяц")
    public void shouldTextValueMonth() {
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getTextValueMonth();
        val year = DataHelper.getValidYear();
        val holder = DataHelper.getValidHolder();
        val cvc = DataHelper.getValidCvc();
        paymentPage.enterData(cardNumber, month, year, holder, cvc);
        paymentPage.messageWrongFormat();
    }

    @Test
    @DisplayName("Отправка заявки с пустым полем Месяц")
    public void shouldMonthEmpty() {
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getMonthEmpty();
        val year = DataHelper.getValidYear();
        val holder = DataHelper.getValidHolder();
        val cvc = DataHelper.getValidCvc();
        paymentPage.enterData(cardNumber, month, year, holder, cvc);
        paymentPage.messageWrongFormat();
    }

    @Test
    @DisplayName("Отправка заявки с указанием прошедшего Года")
    public void shouldInvalidYear() {
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getInvalidYear();
        val holder = DataHelper.getValidHolder();
        val cvc = DataHelper.getValidCvc();
        paymentPage.enterData(cardNumber, month, year, holder, cvc);
        paymentPage.messageCardExpired();
    }
    @Test
    @DisplayName("Отправка заявки с указанием свыше пяти лет")
    public void shouldOverFiveYears() {
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getOverFiveYears();
        val holder = DataHelper.getValidHolder();
        val cvc = DataHelper.getValidCvc();
        paymentPage.enterData(cardNumber, month, year, holder, cvc);
        paymentPage.messageIncorrectCardExpirationDate();
    }


    @Test
    @DisplayName("Отправка заявки при вводе нулей в поле Год")
    public void shouldZeroValueYear() {
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getZeroValueYear();
        val holder = DataHelper.getValidHolder();
        val cvc = DataHelper.getValidCvc();
        paymentPage.enterData(cardNumber, month, year, holder, cvc);
        paymentPage.messageCardExpired();
    }


    @Test
    @DisplayName("Отправка заявки при вводе одной цифры в поле Год")
    public void shouldIncompleteYear() {
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getIncompleteYear();
        val holder = DataHelper.getValidHolder();
        val cvc = DataHelper.getValidCvc();
        paymentPage.enterData(cardNumber, month, year, holder, cvc);
        paymentPage.messageWrongFormat();
    }

    @Test
    @DisplayName("Отправка заявки при вводе текста в поле Год")
    public void shouldTextValueYear() {
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getTextValueYear();
        val holder = DataHelper.getValidHolder();
        val cvc = DataHelper.getValidCvc();
        paymentPage.enterData(cardNumber, month, year, holder, cvc);
        paymentPage.messageWrongFormat();
    }

    @Test
    @DisplayName("Отправка заявки с пустым полем Год")
    public void shouldYearEmpty() {
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getYearEmpty();
        val holder = DataHelper.getValidHolder();
        val cvc = DataHelper.getValidCvc();
        paymentPage.enterData(cardNumber, month, year, holder, cvc);
        paymentPage.messageWrongFormat();
    }

    @Test
    @DisplayName("Отправка заявки с ФИО на русском языке в поле Владелец")
    public void shouldInvalidHolder() {
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val holder = DataHelper.getInvalidHolder();
        val cvc = DataHelper.getValidCvc();
        paymentPage.enterData(cardNumber, month, year, holder, cvc);
        paymentPage.messageWrongFormat();
    }

    @Test
    @DisplayName("Отправка заявки при вводе 33 буквы в поле Владелец")
    public void shouldOverLimitHolder() {
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val holder = DataHelper.getOverLimitHolder();
        val cvc = DataHelper.getValidCvc();
        paymentPage.enterData(cardNumber, month, year, holder, cvc);
        paymentPage.messageWrongFormat();
    }

    @Test
    @DisplayName("Отправка заявки при вводе одной буквы в поле Владелец")
    public void shouldUnderLimitHolder() {
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val holder = DataHelper.getUnderLimitHolder();
        val cvc = DataHelper.getValidCvc();
        paymentPage.enterData(cardNumber, month, year, holder, cvc);
        paymentPage.messageWrongFormat();
    }

    @Test
    @DisplayName("Отправка заявки при вводе цифр в поле Владелец")
    public void shouldHolderNumbers() {
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val holder = DataHelper.getHolderNumbers();
        val cvc = DataHelper.getValidCvc();
        paymentPage.enterData(cardNumber, month, year, holder, cvc);
        paymentPage.messageWrongFormat();
    }

    @Test
    @DisplayName("Отправка заявки при вводе HTML-тега в поле Владелец")
    public void shouldHolderHTMLTag() {
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val holder = DataHelper.getHolderHTMLTag();
        val cvc = DataHelper.getValidCvc();
        paymentPage.enterData(cardNumber, month, year, holder, cvc);
        paymentPage.messageWrongFormat();
    }

    @Test
    @DisplayName("Отправка заявки с пустым полем Владелец")
    public void shouldHolderEmpty() {
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val holder = DataHelper.getHolderEmpty();
        val cvc = DataHelper.getValidCvc();
        paymentPage.enterData(cardNumber, month, year, holder, cvc);
        paymentPage.messageFieldRequired();
    }

    @Test
    @DisplayName("Отправка заявки при вводе нулей в поле CVC")
    public void shouldZeroCvv() {
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val holder = DataHelper.getValidHolder();
        val cvc = DataHelper.getZeroCvv();
        paymentPage.enterData(cardNumber, month, year, holder, cvc);
        paymentPage.messageIncorrectData();
    }


    @Test
    @DisplayName("Отправка заявки при вводе двух цифр в поле CVC")
    public void shouldInvalidCvc() {
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val holder = DataHelper.getValidHolder();
        val cvc = DataHelper.getInvalidCvc();
        paymentPage.enterData(cardNumber, month, year, holder, cvc);
        paymentPage.messageWrongFormat();
    }

    @Test
    @DisplayName("Отправка заявки при вводе текста в поле CVC")
    public void shouldTextValueCvc() {
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val holder = DataHelper.getValidHolder();
        val cvc = DataHelper.getTextValueCvc();
        paymentPage.enterData(cardNumber, month, year, holder, cvc);
        paymentPage.messageWrongFormat();
    }

    @Test
    @DisplayName("Отправка заявки с пустым полем CVC")
    public void shouldCvvEmpty() {
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val holder = DataHelper.getValidHolder();
        val cvc = DataHelper.getCvvEmpty();
        paymentPage.enterData(cardNumber, month, year, holder, cvc);
        paymentPage.messageWrongFormat();
    }

}
