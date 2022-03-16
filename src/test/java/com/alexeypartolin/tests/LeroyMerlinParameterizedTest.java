package com.alexeypartolin.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

// Parameterized tests for leroymerlin.kz

public class LeroyMerlinParameterizedTest extends TestBase {

    @ValueSource(strings = {"Электротовары", "Плитка"})
    @ParameterizedTest(name = "Проверка перехода в категорию второго уровня из выпадающего меню по элементу меню \"{0}\"")
    void validationLoginTest(String category) {
        $(".catalog-menu__btn").hover();
        $$(".catalog-menu__list li a").findBy(text(category)).click();
        $(".catalog-category-page__hl").shouldHave(text(category));
    }

    @CsvSource(value = {
            "РУС | incorrectEmail | Некорректный e-mail",
            "КАЗ | incorrectEmail | Қате е-mail"
    }, delimiter = '|')
    @ParameterizedTest(name = "Проверка локализации валидации поля ввода e-mail по данным \"{0}\"")
    void localisationLoginValidationTest(String localisation, String email, String validationMessage) {
        $(".header-panel__lang").hover().$$(".header-panel__lang-item").findBy(text(localisation)).click();
        $(".header-panel__usr-btn").click();
        $("input[name='USER_LOGIN']").setValue(email);
        $("#USER_LOGIN-error").shouldHave(text(validationMessage));
    }

    static Stream<Arguments> localisationLoginValidationTest() {
        return Stream.of(
                Arguments.of("test@test.com", "testPassword", "Введен неверный e-mail или пароль"),
                Arguments.of("", "testPassword", "Некорректный e-mail")
        );
    }


    @MethodSource(value = "localisationLoginValidationTest")
    @ParameterizedTest(name = "Проверка валидации логина и пароля")
    void LoginValidationTest(String email, String password, String validationMessage) {
        $(".header-panel__usr-btn").click();
        $("input[name='USER_LOGIN']").setValue(email);
        $("input[name='USER_PASSWORD']").setValue(password).pressEnter();
        $(".auth form").shouldHave(text(validationMessage));
    }



}
