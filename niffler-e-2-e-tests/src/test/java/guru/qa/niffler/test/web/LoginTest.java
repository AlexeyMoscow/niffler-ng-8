package guru.qa.niffler.test.web;

import com.codeborne.selenide.SelenideDriver;
import guru.qa.niffler.jupiter.annotation.meta.User;
import guru.qa.niffler.jupiter.annotation.meta.WebTest;
import guru.qa.niffler.jupiter.extension.BrowserExtension;
import guru.qa.niffler.model.Browser;
import guru.qa.niffler.model.UserJson;
import guru.qa.niffler.page.LoginPage;
import guru.qa.niffler.test.web.utils.BrowserConverterUtils;
import guru.qa.niffler.test.web.utils.RandomDataUtils;
import guru.qa.niffler.test.web.utils.SelenideUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.List;


@WebTest
public class LoginTest {
    @RegisterExtension
    private static final BrowserExtension browserExtension = new BrowserExtension();
    private final SelenideDriver chrome = new SelenideDriver(SelenideUtils.chromeConfig);

    @User
    @Test
    void mainPageShouldBeDisplayedAfterSuccessLogin(UserJson user) {
        browserExtension.drivers().add(chrome);
        chrome.open(LoginPage.URL);
        new LoginPage(chrome).doLogin(user.username(), user.testData().password())
                .checkMainPageIsOpened();
    }

   //тест с параллельным запуской 2х браузеров
    @ParameterizedTest
    @EnumSource(Browser.class)
    void userShouldStayOnLoginPageAfterLoginWithBadCredentials(@ConvertWith(BrowserConverterUtils.class) SelenideDriver driver) {

        browserExtension.drivers().add(driver);
        driver.open(LoginPage.URL);
        new LoginPage(driver).setUsername(RandomDataUtils.randomUsername())
                .setPassword("12345")
                .clickLoginBtn()
                .checkError("Неверные учетные данные пользователя")
                .checkLogoText();
    }
}
