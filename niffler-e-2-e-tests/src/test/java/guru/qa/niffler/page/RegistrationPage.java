package guru.qa.niffler.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class RegistrationPage {

    private final SelenideElement registerForm = $("#register-form");
    private final SelenideElement usernameInput = $("input[name='username']");
    private final SelenideElement passwordInput = $("input[name='password']");
    private final SelenideElement passwordSubmit = $("input[name='passwordSubmit']");

    private final SelenideElement sighUpButton = $("button[class='form__submit']");
    private final SelenideElement congratulationMessage = $(".form__paragraph_success");

    public RegistrationPage createUser(String username, String password, String confirmPassword) {
        usernameInput.shouldBe(visible).setValue(username);
        passwordInput.shouldBe(visible).setValue(password);
        passwordSubmit.shouldBe(visible).setValue(confirmPassword);
        sighUpButton.click();
        return this;
    }

    public void verifyUserCreated() {
        congratulationMessage.shouldHave(text("Congratulations"));
    }

    public void verifyErrorText(String errorText) {
        registerForm.shouldHave(text(errorText));
    }
}
