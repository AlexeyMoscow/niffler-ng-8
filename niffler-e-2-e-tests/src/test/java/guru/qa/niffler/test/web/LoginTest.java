package guru.qa.niffler.test.web;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.jupiter.annotation.meta.User;
import guru.qa.niffler.jupiter.annotation.meta.WebTest;
import guru.qa.niffler.jupiter.extension.TestMethodContextExtension;
import guru.qa.niffler.model.UserJson;
import guru.qa.niffler.page.LoginPage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;


@WebTest
public class LoginTest {

    @User()
    @Test
    void mainPageShouldBeDisplayedAfterSuccessLogin(UserJson user) {
        ExtensionContext ctx = TestMethodContextExtension.context();
        Selenide.open(LoginPage.URL, LoginPage.class)
                .doLogin(user.username(), user.testData().password())
                .checkMainPageIsOpened();
    }
    @User()
    @Test
    void userShouldStayOnLoginPageAfterLoginWithBadCredentials(UserJson user) {
        Selenide.open(LoginPage.URL, LoginPage.class)
                .setUsername(user.username())
                .setPassword(user.testData().password() + "12")
                .clickLoginBtn()
                .checkError("Неверные учетные данные пользователя");
    }
}
