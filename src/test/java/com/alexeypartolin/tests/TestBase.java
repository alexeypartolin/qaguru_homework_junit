package com.alexeypartolin.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class TestBase {
    @BeforeAll
    static void beforeAll() {
        //Configuration.browser = "firefox";
        Configuration.browserSize = "1920x1080";
    }

    @BeforeEach
    void beforeEach() {
        Selenide.open("https://leroymerlin.kz/");
    }

    @AfterEach
    void afterEach() {
        Selenide.closeWebDriver();
    }
}
