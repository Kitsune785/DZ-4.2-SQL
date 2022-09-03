package ru.netology.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.SQLHelper.cleanBase;

public class BankLoginTest {

    @AfterAll
    static void cleaningUp() {
        cleanBase();
    }

    @Test
    void shouldLoginOk() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.verifyVerificationPageVisibl();
        var verificationCode = SQLHelper.getVerificationCode();
        verificationPage.validVerify(verificationCode.getCode());
    }

    @Test
    void shouldShowIncorrectUser() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.generateRandomUser();
        loginPage.validLogin(authInfo);
        loginPage.verifyErrorNotificVisible();
    }

        @Test
    void shouldShowIncorrectVerificationCode() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.verifyVerificationPageVisibl();
        var verificationCode = DataHelper.generateRandomVerificationCode();
        verificationPage.verify(verificationCode.getCode());
        verificationPage.verifyErrorNotificVisible();
    }

    @Test
    public void shouldBlockWrongPasswordThreeTimes() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getWrongAuthInfo();
        loginPage.invalidData(authInfo);
        loginPage.getErrorIfInvalidData();
        loginPage.invalidData(authInfo);
        loginPage.getErrorIfInvalidData();
        loginPage.invalidData(authInfo);
        loginPage.getErrorIfInvalidData();
        loginPage.invalidData(authInfo);
        loginPage.getMessageIfSystemLocked();
    }
}

