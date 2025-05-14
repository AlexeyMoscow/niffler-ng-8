package guru.qa.niffler.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import guru.qa.niffler.config.Config;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
  public static final String URL = Config.getInstance().authUrl() + "login";

  private final SelenideElement usernameInput = $("input[name='username']");
  private final SelenideElement passwordInput = $("input[name='password']");
  private final SelenideElement submitBtn = $("button[type='submit']");
  private final SelenideElement createAccountBtn = $("a[href='/register']");
  private final SelenideElement errorMessage = $(".form__error");

  @Step("Log in with credentials: username - {0}, password - {1}")
  public MainPage doLogin(String username, String password) {
    usernameInput.setValue(username);
    passwordInput.setValue(password);
    submitBtn.click();
    return new MainPage();
  }

  @Step("Go to creating new account")
  public RegisterPage goToCreateNewAccount() {
    createAccountBtn.click();
    return new RegisterPage();
  }

  @Step("Set username")
  public LoginPage setUsername(String username) {
    usernameInput.setValue(username);
    return this;
  }

  @Step("Set password")
  public LoginPage setPassword(String password) {
    passwordInput.setValue(password);
    return this;
  }

  @Step("Click Log in button")
  public LoginPage clickLoginBtn() {
    submitBtn.click();
    return this;
  }

  @Step("Check error")
  public LoginPage checkError(String errorText) {
    errorMessage.shouldBe(visible).shouldHave(exactText(errorText));
    return this;
  }
}
