package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

public class LoginPage {

    private SelenideElement loginField = $("[data-test-id=login] input");
    private SelenideElement passwordField = $("[data-test-id=password] input");
    private SelenideElement loginButton = $("[data-test-id=action-login]");
    private SelenideElement errorNotification = $("[data-test-id='error-notification']");
    private SelenideElement blockMessage = $("[data-test-id='error-notification']");

    public void verifyErrorNotificVisible() {
        errorNotification.shouldBe(visible);
    }

    public void getMessageIfSystemLocked() {
        blockMessage.shouldBe(visible).shouldHave(text("Превышено количество попыток ввода пароля! Учётная запись временно заблокирована!"));
    }

    public void getErrorIfInvalidData() {
        errorNotification.shouldBe(visible).shouldHave(text("Ошибка! Неверно указан логин или пароль"));
    }

    public void invalidData(DataHelper.AuthInfo info) {
        loginField.doubleClick();
        loginField.sendKeys(Keys.DELETE);
        loginField.setValue(info.getLogin());
        passwordField.doubleClick();
        passwordField.sendKeys(Keys.DELETE);
        passwordField.setValue(info.getPassword());
        loginButton.click();
    }

    public VerificationPage validLogin(DataHelper.AuthInfo info) {
        loginField.setValue(info.getLogin());
        passwordField.setValue(info.getPassword());
        loginButton.click();
        return page(VerificationPage.class);
    }
}


