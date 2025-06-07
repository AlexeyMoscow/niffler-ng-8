package guru.qa.niffler.test.web;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideDriver;
import guru.qa.niffler.jupiter.annotation.meta.User;
import guru.qa.niffler.jupiter.annotation.meta.WebTest;
import guru.qa.niffler.jupiter.extension.BrowserExtension;
import guru.qa.niffler.model.UserJson;
import guru.qa.niffler.page.LoginPage;
import guru.qa.niffler.test.web.utils.SelenideUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import java.util.List;


@WebTest
public class LoginTest {
    @RegisterExtension
    private final BrowserExtension browserExtension = new BrowserExtension();
    private final SelenideDriver chrome = new SelenideDriver(SelenideUtils.chromeConfig);

    @User()
    @Test
    void mainPageShouldBeDisplayedAfterSuccessLogin(UserJson user) {
        browserExtension.drivers().add(chrome);
        chrome.open(LoginPage.URL);
        new LoginPage(chrome).doLogin(user.username(), user.testData().password())
                .checkMainPageIsOpened();
    }

    @User()
    @Test
    void userShouldStayOnLoginPageAfterLoginWithBadCredentials(UserJson user) {
        SelenideDriver firefox = new SelenideDriver(SelenideUtils.firefoxConfig);

        browserExtension.drivers().addAll(List.of(chrome, firefox));
        chrome.open(LoginPage.URL);
        firefox.open(LoginPage.URL);
        new LoginPage(chrome).setUsername(user.username())
                .setPassword(user.testData().password() + "12")
                .clickLoginBtn()
                .checkError("Неверные учетные данные пользователя");

        firefox.$(".logo-section__text").shouldHave(Condition.text("Niffler"));
    }
}
