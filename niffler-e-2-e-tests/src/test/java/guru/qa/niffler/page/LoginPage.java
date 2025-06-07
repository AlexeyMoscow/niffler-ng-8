package guru.qa.niffler.page;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideDriver;
import com.codeborne.selenide.SelenideElement;
import guru.qa.niffler.config.Config;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
  public static final String URL = Config.getInstance().authUrl() + "login";

  private final SelenideElement usernameInput;
  private final SelenideElement passwordInput;
  private final SelenideElement submitBtn;
  private final SelenideElement createAccountBtn;
  private final SelenideElement errorMessage;

  public LoginPage(SelenideDriver driver) {
    this.usernameInput = driver.$("input[name='username']");
    this.passwordInput = driver.$("input[name='password']");
    this.submitBtn = driver.$("button[type='submit']");
    this.createAccountBtn = driver.$("a[href='/register']");
    this.errorMessage = driver.$(".form__error");
  }

  public LoginPage() {
    this.usernameInput = $("input[name='username']");
    this.passwordInput = $("input[name='password']");
    this.submitBtn = $("button[type='submit']");
    this.createAccountBtn = $("a[href='/register']");
    this.errorMessage = $(".form__error");
  }

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
