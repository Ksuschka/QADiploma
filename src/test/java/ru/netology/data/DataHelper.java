package ru.netology.data;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.util.Locale;

public class DataHelper {
    private static Faker faker = new Faker(new Locale("en"));

    private DataHelper() {
    }

    public static String getFirstCardNumber() {
        return "4444 4444 4444 4441";
    }

    public static String getFirstCardStatus() {
        return "APPROVED";
    }

    public static String getSecondCardNumber() {
        return "4444 4444 4444 4442";
    }

    public static String getSecondCardStatus() {
        return "DECLINED";
    }

    public static String getInvalidCardNumber() {
        return faker.business().creditCardNumber();
    }

    public static String getZeroValueCardNumber() {
        return "0000 0000 0000 0000";
    }

    public static String getCardNumberUnderLimit() {
        return "4444";
    }

    public static String getCardNumberOverLimit() {
        return "4444 4444 4444 4441 5";
    }

    public static String getTextValueCardNumber() {
        return "тест";
    }

    public static String getCardNumberEmpty() {
        return "";
    }

    public static String getValidMonth() {
        LocalDate localDate = LocalDate.now();
        return String.format("%02d", localDate.getMonthValue());
    }

    public static String getInvalidMonth() {
        return "33";
    }

    public static String getZeroValueMonth() {
        return "00";
    }

    public static String getPastMonth() {
        LocalDate localDate = LocalDate.now().minusMonths(1);
        return String.format("%02d", localDate.getMonthValue());
    }

    public static String getIncompleteMonth() {
        return "5";
    }

    public static String getTextValueMonth() {
        return "тест";
    }

    public static String getMonthEmpty() {
        return "";
    }

    public static String getValidYear() {
        LocalDate localDate = LocalDate.now();
        return String.format("%ty", localDate);
    }

    public static String getOverFiveYears() {
        LocalDate localDate = LocalDate.now();
        return String.format("%ty", localDate.plusYears(6));
    }
    public static String getInvalidYear() {
        LocalDate localDate = LocalDate.now();
        return String.format("%ty", localDate.minusYears(1));
    }

    public static String getIncompleteYear() {
        return "5";
    }

    public static String getTextValueYear() {
        return "тест";
    }

    public static String getZeroValueYear() {
        return "00";
    }
    public static String getYearEmpty() {
        return "";
    }

    public static String getValidHolder() {
        return faker.name().firstName() + " " + faker.name().lastName().replaceAll("[^A-Za-z]", "");
    }

    public static String getInvalidHolder() {
        Faker faker = new Faker(new Locale("ru"));
        return faker.name().fullName();
    }

    public static String getOverLimitHolder() {
        return "Testtttttttttttttttttttttttttttt";
    }

    public static String getUnderLimitHolder() {
        return "R";
    }

    public static String getHolderNumbers() {
        return "102030";
    }

    public static String getHolderHTMLTag() {
        return "<test>";
    }

    public static String getHolderEmpty() {
        return "";
    }

    public static String getValidCvc() {
        return faker.numerify("###");
    }

    public static String getInvalidCvc() {
        return faker.numerify("##");
    }

    public static String getZeroCvv() {
        return "000";
    }

    public static String getTextValueCvc() {
        return "тест";
    }

    public static String getCvvEmpty() {
        return "";
    }

}