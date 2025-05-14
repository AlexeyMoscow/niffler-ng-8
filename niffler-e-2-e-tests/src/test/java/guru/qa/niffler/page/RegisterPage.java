package guru.qa.niffler.page;

import com.codeborne.selenide.SelenideElement;
import guru.qa.niffler.config.Config;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

public class RegisterPage {
    public static final String URL = Config.getInstance().authUrl() + "register";

    private final SelenideElement usernameInput = $("input[name='username']");
    private final SelenideElement passwordInput = $("input[name='password']");
    private final SelenideElement submitPasswordInput = $("input[name='passwordSubmit']");
    private final SelenideElement signUpBtn = $("button[type='submit']");
    private final SelenideElement signInBtn = $(".form_sign-in");
    private final SelenideElement errorMessage = $(".form__error");

    @Step("Set username: '{0}'")
    public RegisterPage setUserName(String username) {
        usernameInput.setValue(username);
        return this;
    }

    @Step("Set password: '{0}'")
    public RegisterPage setPassword(String password) {
        passwordInput.setValue(password);
        return this;
    }

    @Step("Submit password: '{0}'")
    public RegisterPage setPasswordSubmit(String password) {
        submitPasswordInput.setValue(password);
        return this;
    }

    @Step("Click sign up button")
    public RegisterPage submitRegistration() {
        signUpBtn.click();
        return this;
    }

    @Step("Click Sign in button")
    public RegisterPage clickSignInBtn() {
        signInBtn.click();
        return this;
    }

    @Step("Register with username - '{0}', password - '{1}'")
    public LoginPage doRegister(String username, String password, String submitPassword) {
        usernameInput.setValue(username);
        passwordInput.setValue(password);
        submitPasswordInput.setValue(submitPassword);
        signUpBtn.click();
        signInBtn.click();
        return new LoginPage();
    }

    @Step("Check error message with text '{errorText}'")
    public RegisterPage checkErrorMessage(String errorText) {
        errorMessage.shouldBe(visible).shouldHave(exactText(errorText));
        return this;
    }
}
